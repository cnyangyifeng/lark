package cn.me.xdf.common.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;
import cn.me.xdf.common.upload.UploadUtils;
import cn.me.xdf.common.utils.ResourceBundleReader;
import cn.me.xdf.common.web.Constants;
import cn.me.xdf.model.base.AttMain;

/**
 * 导出excel文件
 * 
 * @author xiaobin
 * 
 */
public abstract class AbsExportExcel{

	public static final String TEMPLATE_EXCEL = "excel";

	/**
	 * 导出excel文件
	 * 
	 * @param list
	 * 
	 * @param tempFileName
	 * 
	 * @param response
	 */
	public static void exportExcel(List<?> list, String tempFileName,
			HttpServletResponse response,String expFileName) {
		ServletOutputStream outStream = null;
		java.io.BufferedOutputStream bos = null;
		try {
			outStream = response.getOutputStream();
			bos = new java.io.BufferedOutputStream(outStream);
			response.setContentType("application/x-download");
			tempFileName = URLEncoder.encode(tempFileName, "utf-8");
			expFileName = URLEncoder.encode(expFileName, "utf-8");
			response.setHeader("Content-Disposition", "attachment;"
					+ "filename=" + expFileName);
			HashMap<String, List<?>> map = new HashMap<String, List<?>>();
			map.put("resultList", list);
			XLSTransformer transformer = new XLSTransformer();

			String modelFileName = getExcelTemplate()
					+ System.getProperty("file.separator") + tempFileName;
			transformer.transformXLS(new FileInputStream(modelFileName), map)
					.write(bos);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if (bos != null)
					bos.close();
				if (outStream != null)
					outStream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 获取模板路径
	 * 
	 * @return
	 */
	public static String getExcelTemplate() {
		String fileSep = System.getProperty("file.separator");
		File classesDir = new File(AbsExportExcel.class.getResource("/")
				.getFile());
		// WEB-INF
		File webinfDir = classesDir.getParentFile();
		// 项目跟目录
		String path = webinfDir.getPath() + fileSep + "template" + fileSep
				+ "excel";
		return path.replaceAll("%20", " ");
	}

	public static void main(String[] args) {
		System.out.println(getExcelTemplate());
	}
	
	/**
	 * 导出excel文件(获得Attmain对象)
	 * 
	 * @param list
	 * 
	 * @param tempFileName
	 * 
	 * @param response
	 */
	public static AttMain exportExcels(List<?> list, String tempFileName) {
		try {
			HashMap<String, List<?>> map = new HashMap<String, List<?>>();
			map.put("resultList", list);
			XLSTransformer transformer = new XLSTransformer();
			String modelFileName = getExcelTemplate()
					+ System.getProperty("file.separator") + tempFileName;
			
			String filename = UploadUtils.generateFilename(Constants.UPLOAD_PATH, "xls");
		    ResourceBundle bundle = ResourceBundleReader.getBundle();
		    String destPath = bundle.getString("upload_path");
		    transformer.transformXLS(modelFileName, map, (destPath + filename)); 
			AttMain attMain = new AttMain();
			attMain.setFdFilePath((destPath + filename));
			attMain.setFdFileName(tempFileName);
			return attMain;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
	

}
