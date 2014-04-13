package com.kuxue.action.common;

import com.kuxue.common.utils.InputStreamZipper;
import com.kuxue.common.utils.excel.AbsExportExcel;
import com.kuxue.service.plugin.AttMainPlugin;
import gui.ava.html.image.generator.HtmlImageGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.io.StreamUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.kuxue.common.download.DownloadHelper;
import com.kuxue.common.json.JsonUtils;
import com.kuxue.common.page.Pagination;
import com.kuxue.common.page.SimplePage;
import com.kuxue.common.upload.FileModel;
import com.kuxue.common.upload.FileRepository;
import com.kuxue.common.upload.UploadUtils;
import com.kuxue.model.bam.BamCourse;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.course.CourseInfo;
import com.kuxue.model.material.Task;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.model.process.TaskRecord;
import com.kuxue.service.AccountService;
import com.kuxue.service.adviser.AdviserService;
import com.kuxue.service.bam.BamCourseService;
import com.kuxue.service.base.AttMainService;
import com.kuxue.service.course.CertificateService;
import com.kuxue.service.course.CourseService;
import com.kuxue.service.material.MaterialDiscussInfoService;
import com.kuxue.service.material.MaterialService;
import com.kuxue.utils.DateUtil;

/**
 * @author xiaobin
 */
@Controller
@RequestMapping("/common/file")
public class FileController {

	@Autowired
	private MaterialService materialService;

	@Autowired
	private AdviserService adviserService;

	@Autowired
	private MaterialDiscussInfoService materialDiscussInfoService;

	@Autowired
	private CertificateService certificateService;

	private static final Logger log = LoggerFactory
			.getLogger(FileController.class);

	/**
	 * 上传文件
	 */
	@RequestMapping("/o_upload")
	@ResponseBody
	public FileModel execute(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		commonsMultipartResolver.setDefaultEncoding("utf-8");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("Filedata");
		FileModel fileModel = fileRepository.storeByExt(file);
		AttMain attMain = fileModel.toAttMain();
		attMain = attMainService.saveOnInit(attMain);
		fileModel.setAttId(attMain.getFdId());
		return fileModel;
	}

	/**
	 * 富文本编辑器上传图片
	 */
	@RequestMapping("/KEditor_uploadImg")
	public String uploadImg(HttpServletRequest request,
			HttpServletResponse response) {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		commonsMultipartResolver.setDefaultEncoding("utf-8");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("imgFile");
		FileModel fileModel = fileRepository.storeByExt(file);
		AttMain attMain = fileModel.toAttMain();
		// 设置modelId与modelName,否则定时任务会清理掉
		attMain.setFdModelId("");
		attMain.setFdModelName("");
		attMain = attMainService.saveOnInit(attMain);
		Map map = new HashMap();
		map.put("error", 0);
		map.put("url", request.getContextPath() + "/common/file/image/"
				+ attMain.getFdId());
		try {
			response.getWriter().println(JsonUtils.writeObjectToJson(map));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 文件下载
	 * 
	 * @param id
	 *            (对应AttMain的主键)
	 * @return
	 */
	@RequestMapping("/download/{id}")
	@ResponseBody
	public DownloadHelper download(@PathVariable("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		DownloadHelper dh = new DownloadHelper();
		dh.setRequest(request);
		AttMain attMain = attMainService.get(AttMain.class, id);
		if (attMain != null) {
			ByteArrayOutputStream bos = AttMainPlugin.getDocByAttId(attMain);
			dh.setInputStream(bos);
			dh.setFileName(attMain.getFdFileName());
		}else{
			String fileNetName=request.getParameter("fileNetName");
			ByteArrayOutputStream bos = AttMainPlugin.getDocByIdAndName(id, fileNetName);
			dh.setInputStream(bos);
			dh.setFileName(fileNetName);
		}
		return dh;
	}

	/**
	 * 文件下载（打包）
	 * 
	 * @param ids
	 * @param zipname
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadZipsByArrayIds/{ids}/{zipname}")
	public String downloadZipsByArrayIds(@PathVariable("ids") String ids,
			@PathVariable("zipname") String zipname,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		DownloadHelper dh = new DownloadHelper();
		dh.setRequest(request);
		List<AttMain> attMains = attMainService.getAttsByIds(StringUtils.split(
				ids, ','));
		String agent = request.getHeader("USER-AGENT");
		downloadAttMain(attMains, agent, zipname, response);
		return null;
	}

	/**
	 * 文件下载（打包）
	 * 
	 * @param ids
	 * @param zipname
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/downloadZip/{ids}/{zipname}")
	public String downloadZips(@PathVariable("ids") String[] ids,
			@PathVariable("zipname") String zipname,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		DownloadHelper dh = new DownloadHelper();
		dh.setRequest(request);
		List<AttMain> attMains = attMainService.getAttsByIds(ids);
		String agent = request.getHeader("USER-AGENT");
		downloadAttMain(attMains, agent, zipname, response);
		return null;
	}

	/**
	 * 文件下载 （打包）
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/downloadZip/{modelId}/{zipname}")
	public String downloadZip(@PathVariable("modelId") String modelId,
			@PathVariable("zipname") String zipname,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		DownloadHelper dh = new DownloadHelper();
		dh.setRequest(request);
		List<AttMain> attMains = attMainService.getAttsByModelId(modelId);
		String agent = request.getHeader("USER-AGENT");
		downloadAttMain(attMains, agent, zipname, response);
		return null;
	}

	/**
	 * 指导老师批量下载作业附件
	 */
	@RequestMapping("/allDownloadTaskZip/{fdType}/{zipname}")
	public String allDownloadTaskZip(@PathVariable("fdType") String fdType,
			@PathVariable("zipname") String zipname,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		DownloadHelper dh = new DownloadHelper();
		dh.setRequest(request);
		List<AttMain> attMainList = new ArrayList<AttMain>();
		String keyword = request.getParameter("keyword");
		Pagination page = adviserService.findAdivserCouserList(fdType, 1,
				SimplePage.DEF_COUNT, keyword, "FDCREATETIME");
		if (page.getTotalCount() > 0) {
			List list = page.getList();
			for (int i = 0; i < list.size(); i++) {
				Map pageMap = (Map) list.get(i);
				List<AttMain> attMains = adviserService
						.findNotesAtts((String) pageMap.get("FDID"));
				for (AttMain attMain : attMains) {
					attMainList.add(attMain);
				}
			}
		}
		for (int index = 2; index <= page.getTotalPage(); index++) {
			Pagination pagetemp = adviserService.findAdivserCouserList(fdType,
					index, SimplePage.DEF_COUNT, keyword, "FDCREATETIME");
			if (pagetemp.getTotalCount() > 0) {
				List list = pagetemp.getList();
				for (int i = 0; i < list.size(); i++) {
					Map pageMap = (Map) list.get(i);
					List<AttMain> attMains = adviserService
							.findNotesAtts((String) pageMap.get("FDID"));
					for (AttMain attMain : attMains) {
						attMainList.add(attMain);
					}
				}
			}
		}
		String agent = request.getHeader("USER-AGENT");
		downloadAttMain(attMainList, agent, zipname, response);
		return null;
	}

	/**
	 * 按文件modelId进行批量Download(打包) yuhz
	 * 
	 * @param
	 * @param zipname
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/batchDownloadZip/{modelIds}/{zipname}")
	public String batchDownloadZip(@PathVariable("modelIds") String[] modelIds,
			@PathVariable("zipname") String zipname,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		DownloadHelper dh = new DownloadHelper();
		dh.setRequest(request);
		List<AttMain> attMainList = new ArrayList<AttMain>();
		for (int i = 0; i < modelIds.length; i++) {
			List<AttMain> attMains = attMainService
					.getAttsByModelId(modelIds[i]);
			if (attMains != null && attMains.size() > 0) {
				for (AttMain attMain : attMains) {
					attMainList.add(attMain);
				}
			}
		}
		String agent = request.getHeader("USER-AGENT");
		downloadAttMain(attMainList, agent, zipname, response);
		return null;
	}

	/**
	 * 根据素材类型进行附件全部下载 yuhz
	 * 
	 * @param fdType
	 * @param zipname
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/allDownloadZip/{fdType}/{zipname}")
	public String allDownloadZip(@PathVariable("fdType") String fdType,
			@PathVariable("zipname") String zipname,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		DownloadHelper dh = new DownloadHelper();
		String key = request.getParameter("key");
		dh.setRequest(request);
		List<AttMain> attMainList = new ArrayList<AttMain>();
		Pagination page = materialService.findMaterialByKey(fdType, key, 1,
				SimplePage.DEF_COUNT);
		if (page.getTotalCount() > 0) {
			List list = page.getList();
			for (int i = 0; i < list.size(); i++) {
				Map pageMap = (Map) list.get(i);
				List<AttMain> attMains = attMainService
						.getAttsByModelId((String) pageMap.get("FDID"));
				if (attMains != null && attMains.size() > 0) {
					for (AttMain attMain : attMains) {
						attMainList.add(attMain);
					}
				}
			}
		}
		for (int index = 2; index <= page.getTotalPage(); index++) {
			Pagination pagetemp = materialService.findMaterialByKey(fdType,
					key, index, SimplePage.DEF_COUNT);
			if (pagetemp.getTotalCount() > 0) {
				List list = pagetemp.getList();
				for (int i = 0; i < list.size(); i++) {
					Map pageMap = (Map) list.get(i);
					List<AttMain> attMains = attMainService
							.getAttsByModelId((String) pageMap.get("FDID"));
					if (attMains != null && attMains.size() > 0) {
						for (AttMain attMain : attMains) {
							attMainList.add(attMain);
						}
					}
				}
			}
		}
		String agent = request.getHeader("USER-AGENT");
		downloadAttMain(attMainList, agent, zipname, response);
		return null;
	}

	private void downloadAttMain(List<AttMain> attMains, String agent,
			String zipname, HttpServletResponse response)
			throws UnsupportedEncodingException {
		if (attMains != null && !attMains.isEmpty()) {

			String temp = "";
			// 设置文件头，文件名称或编码格式
			if (null != agent && -1 != agent.indexOf("MSIE")) {// IE
				temp = java.net.URLEncoder.encode(zipname, "UTF-8");
			} else {
				temp = new String(zipname.getBytes("UTF-8"), "ISO8859-1");
			}

			List<InputStreamZipper.InputStreamEntry> fileEntrys = new ArrayList<InputStreamZipper.InputStreamEntry>();
			response.setContentType("application/x-download;charset=UTF-8");
			response.addHeader("Content-disposition", "filename=" + temp
					+ ".zip");
			for (AttMain attMain : attMains) {
				// File file = new File(attMain.getFdFilePath());
				if (StringUtil.isNotBlank(attMain.getFileNetId())) {
					log.info("开始读取Filenet数据:" + attMain.getFdFileName());
					ByteArrayOutputStream bos = AttMainPlugin
							.getDocByAttId(attMain);
					InputStream stream = new ByteArrayInputStream(
							bos.toByteArray());
					fileEntrys.add(new InputStreamZipper.InputStreamEntry(
							getParentFolder(attMain), "", stream, attMain
									.getFdFileName()));
				}
			}
			try {
				// 模板一般都在windows下编辑，所以默认编码为GBK
				InputStreamZipper.zip(response.getOutputStream(), fileEntrys,
						"GBK");
			} catch (IOException e) {
				log.error("export db error!", e);
			}
		}
	}

	private String getParentFolder(AttMain attmain){
		if(TaskRecord.class.getName().equals(attmain.getFdModelName())){
			TaskRecord record = materialService.load(TaskRecord.class,attmain.getFdModelId());
			if(record==null){
				return attmain.getFdFileName();
			}
			Task task = materialService.load(Task.class,record.getFdTaskId());
			if(task==null){
				return attmain.getFdFileName();
			}
			return task.getFdName();
		}
		return attmain.getFdFileName();
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		return BooleanUtils.toString(attMainService.deleteAttMain(id) != null,
				"true", "false");
	}

	/**
	 * 查看图片
	 * 
	 * @param id
	 *            对应业务表主键，也就是AttMain的modelId
	 * @param request
	 * @param response
	 */
	@RequestMapping("/image/{id}")
	public void image(@PathVariable("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Cache-Control", "max-age=2592000");

		AttMain attMain = attMainService.get(id);

		// response.setContentType(attMain.getFdContentType());
		response.setContentType("image/jpeg");
		if (attMain.getFdSize() != null) {
			response.addHeader("Content-Length",
					String.valueOf(attMain.getFdSize()));
		}
		OutputStream out;
		try {
			out = response.getOutputStream();
			InputStream is = new FileInputStream(attMain.getFdFilePath());
			byte buffer[] = new byte[1024];
			while (is.read(buffer, 0, 1024) != -1) {
				out.write(buffer);
			}
			out.flush();
			StreamUtil.close(is);
			StreamUtil.close(out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private FileRepository fileRepository;

	@Autowired
	private AttMainService attMainService;

	@Autowired
	public void setFileRepository(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	@Autowired
	private BamCourseService bamCourseService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private CourseService courseService;

	/**
	 * html转图片，并且下载
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/downloadImg")
	public void downloadImg(HttpServletRequest request,
			HttpServletResponse response) {
		String bamId = request.getParameter("bamId");
		BamCourse bamCourse = bamCourseService.get(BamCourse.class, bamId);
		CourseInfo courseInfo = courseService.get(bamCourse.getCourseInfo()
				.getFdId());
		SysOrgPerson person = courseInfo.getCreator();
		SysOrgPerson orgPerson = accountService.get(bamCourse.getPreTeachId());
		String rootUrl = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";
		String imgUrl = "";
		if (orgPerson.getPoto().indexOf("http") > -1) {
			imgUrl = orgPerson.getPoto();
		} else {
			imgUrl = rootUrl + orgPerson.getPoto();
		}
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("userImg", imgUrl);
			param.put("userName", orgPerson.getFdName());
			param.put("time", DateUtil.convertDateToString(
					bamCourse.getEndDate(), "yyyy/MM/dd"));
			param.put("courseName", courseInfo.getFdTitle());
			String aId = courseInfo.getFdAuthorId();
			if(StringUtil.isEmpty(aId)){
				param.put("dep", person.getDeptName());
			}else{
				SysOrgPerson person2 = accountService.get(aId);
				param.put("dep", person2.getDeptName());
			}
			param.put("userEName", orgPerson.getLoginName());
			String html = certificateService.getCertificateHtml("html", param);
			HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
			imageGenerator.loadHtml(html);
			response.setHeader("Content-type", "text/html;charset=utf-8");
			String imgName = java.net.URLEncoder.encode(courseInfo.getFdTitle()+"-"+orgPerson.getFdName()+"-"+DateUtil.getCurrentDate()+".png", "UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ imgName);
			OutputStream out;
			response.addHeader("Content-Length", String.valueOf(imageGenerator.getSize()));
			out = response.getOutputStream();
			ImageIO.write(imageGenerator.getBufferedImage(),"png",out);
			out.flush();
			StreamUtil.close(out);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 文件下载
	 * 
	 * @param id
	 *            (对应AttMain的主键)
	 * @return
	 */
	@RequestMapping("/downloadExample/{type}")
	@ResponseBody
	public DownloadHelper downloadExample(@PathVariable("type") String type,
			HttpServletRequest request, HttpServletResponse response) {
		AbsExportExcel.exportExcel(new ArrayList(), type+".xls", response ,"课程授权示例.xls");
		return null;
	}

}
