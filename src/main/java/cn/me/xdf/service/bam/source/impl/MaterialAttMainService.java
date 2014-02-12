package cn.me.xdf.service.bam.source.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import cn.me.xdf.model.bam.BamCourse;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.model.process.SourceNote;
import cn.me.xdf.service.SimpleService;
import cn.me.xdf.service.bam.BamCourseService;
import cn.me.xdf.service.bam.process.SourceNodeService;
import cn.me.xdf.service.bam.source.ISourceService;
import cn.me.xdf.service.base.AttMainService;
import cn.me.xdf.service.material.MaterialDiscussInfoService;
import cn.me.xdf.service.material.MaterialService;
import cn.me.xdf.service.score.ScoreService;
import cn.me.xdf.utils.ShiroUtils;


/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-31
 * Time: 下午5:02
 * To change this template use File | Settings | File Templates.
 */
@Service("materialAttmainService")
public class MaterialAttMainService extends SimpleService implements ISourceService {
    @Autowired
    private SourceNodeService sourceNodeService;

    @Autowired
    private AttMainService attMainService;

    @Autowired
    private MaterialService materialService;


    @Autowired
    private MaterialDiscussInfoService materialDiscussInfoService;

    @Autowired
    private BamCourseService bamCourseService;

    @Autowired
    private ScoreService scoreService;

    @Override
    public Object findSourceByMaterials(BamCourse bamCourse, CourseCatalog catalog) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object saveSourceNode(WebRequest request) {
        String catalogId = request.getParameter("catalogId");
        String bamId = request.getParameter("bamId");
        String materialInfoId = request.getParameter("fdid");
        BamCourse bamCourse = bamCourseService.get(BamCourse.class, bamId);
        SourceNote sourceNode = new SourceNote();//保存学习素材记录
        sourceNode.setFdCourseId(bamCourse.getCourseId());
        sourceNode.setFdCatalogId(catalogId);
        sourceNode.setFdUserId(ShiroUtils.getUser().getId());
        sourceNode.setFdOperationDate(new Date());
        sourceNode.setFdMaterialId(materialInfoId);
        sourceNode.setIsStudy(true);
        return sourceNodeService.saveSourceNode(sourceNode);
    }

    @Override
    public Object findSubInfoByMaterial(WebRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object findMaterialDetailInfo(BamCourse bamCourse, CourseCatalog catalog, String fdid) {
        Map map = new HashMap();
        List<MaterialInfo> material = bamCourse.getMaterialByCatalog(catalog);
        List listMedia = new ArrayList();
        Map defaultMedia = new HashMap();
        boolean status = false;
        if (material != null) {
            for (int i = 0; i < material.size(); i++) {
                Map listm = new HashMap();
                MaterialInfo minfo = material.get(i);
                AttMain attMain = attMainService.getByModelIdAndModelName(minfo.getFdId(), MaterialInfo.class.getName());
                listm.put("id", minfo.getFdId());//素材id
                listm.put("name", minfo.getFdName());//素材名称
                listm.put("intro", minfo.getFdDescription());//素材描述
                listm.put("isPass", minfo.getThrough());//素材描述
                listm.put("txt", minfo.getRichContent());//富文本内容
                String fdLink = minfo.getFdLink();
            	if (attMain != null) {
                    listm.put("url", attMain.getFdId());//附件id
                }
                listMedia.add(listm);
                //defaultMedia 默认当前还没学习的内容
                if (i == 0) {
                    defaultMedia.put("id", minfo.getFdId());//素材id
                    defaultMedia.put("name", minfo.getFdName());//素材名称
                    defaultMedia.put("intro", minfo.getFdDescription());//素材描述
                    //////////////富文本 素材的 富文本
                    defaultMedia.put("txt", minfo.getRichContent());//富文本内容
                    if(StringUtil.isNotBlank(fdLink)){
                    	String fd[] = fdLink.split("\\?vid=");
                        String link = fd[1].split("&")[0];
                        defaultMedia.put("code", getFdLinkMap(link));//播放地址
                    }else {
                    	if (attMain != null) {
                            defaultMedia.put("code",  attMain.getCode());
                        }else{
                        	defaultMedia.put("code",  "");
                        }
                    }
                    if (attMain != null) {
                    	defaultMedia.put("url", attMain.getFdId());//附件id
                    	defaultMedia.put("fileNetId", attMain.getFileNetId());//FileNetId
                    }else{
                    	defaultMedia.put("fileNetId", "");//FileNetId
                    	defaultMedia.put("url", "");//附件id
                    }
                    defaultMedia.put("isPass", minfo.getThrough());
                    ///////////////////////////////////
                    Map scorem = new HashMap();
                    scorem.put("average", 0);
                    scorem.put("total", 0);
                    scorem.put("five", 0);
                    scorem.put("four", 0);
                    scorem.put("three", 0);
                    scorem.put("two", 0);
                    scorem.put("one", 0);
                    defaultMedia.put("rating", scorem);
                    Map memap = new HashMap();
                    memap.put("id", minfo.getFdId());
                    defaultMedia.put("mediaComment", memap);

                    status = true;
                }
                if (StringUtil.isNotEmpty(fdid) && minfo.getFdId().equals(fdid)) {
                    defaultMedia.put("id", minfo.getFdId());//素材id
                    defaultMedia.put("name", minfo.getFdName());//素材名称
                    defaultMedia.put("intro", minfo.getFdDescription());//素材描述
                    defaultMedia.put("txt", minfo.getRichContent());//富文本内容
                    if(StringUtil.isNotBlank(fdLink)){
                    	String fd[] = fdLink.split("\\?vid=");
                        String link = fd[1].split("&")[0];
                        defaultMedia.put("code", getFdLinkMap(link));//播放地址
                    }else {
                       if (attMain != null) {
                           defaultMedia.put("code",  attMain.getCode());
                       }else{
                       	defaultMedia.put("code",  "");
                       }
                    }
                    if (attMain != null) {
                    	defaultMedia.put("url", attMain.getFdId());//附件id
                    	defaultMedia.put("fileNetId", attMain.getFileNetId());//FileNetId
                    }else{
                    	defaultMedia.put("fileNetId", "");//FileNetId
                    	defaultMedia.put("url", "");//附件id
                    }
                    defaultMedia.put("isPass", minfo.getThrough());
                    Map memap = new HashMap();
                    memap.put("id", minfo.getFdId());
                    defaultMedia.put("mediaComment", memap);
                    status = false;
                    continue;
                } 
                if (!minfo.getThrough() && status) {
                    defaultMedia.put("id", minfo.getFdId());//素材id
                    defaultMedia.put("name", minfo.getFdName());//素材名称
                    defaultMedia.put("intro", minfo.getFdDescription());//素材描述
                    defaultMedia.put("txt", minfo.getRichContent());//富文本内容
                    if(StringUtil.isNotBlank(fdLink)){
                    	String fd[] = fdLink.split("\\?vid=");
                        String link = fd[1].split("&")[0];
                        defaultMedia.put("code", getFdLinkMap(link));//播放地址
                    }else {
                       if (attMain != null) {
                    	   defaultMedia.put("code", attMain.getCode());
                       }else{
                       	defaultMedia.put("code",  "");
                       }
                    }
                    if (attMain != null) {
                    	defaultMedia.put("url", attMain.getFdId());//附件id
                    	defaultMedia.put("fileNetId", attMain.getFileNetId());//FileNetId
                    }else{
                    	defaultMedia.put("fileNetId", "");//FileNetId
                    	defaultMedia.put("url", "");//附件id
                    }
                    defaultMedia.put("isPass", minfo.getThrough());
                    Map memap = new HashMap();
                    memap.put("id", minfo.getFdId());
                    defaultMedia.put("mediaComment", memap);
                    status = false;
                }
            }
        }
        ////存储播放次数
        String materialInfoId = (String) defaultMedia.get("id");
        MaterialInfo info = materialService.load(materialInfoId);
        defaultMedia.put("canDownload", info.getIsDownload());//是否允许下载
        defaultMedia.put("downloadCount", info.getFdDownloads() == null ? 0 : info.getFdDownloads());//下载次数
        defaultMedia.put("readCount", info.getFdPlays() == null ? 0 : info.getFdPlays());//播放次数
        defaultMedia.put("mePraised", materialDiscussInfoService.isCanLaud(materialInfoId));//当前用户是否赞过
        defaultMedia.put("praiseCount", info.getFdLauds() == null ? 0 : info.getFdLauds());//赞的次数
    	materialDiscussInfoService.updateMaterialDiscussInfo(Constant.MATERIALDISCUSSINFO_TYPE_PLAY, info.getFdId());
        map.put("listMedia", listMedia);
        map.put("defaultMedia", defaultMedia);
        return map;
    }
    
    private Map<String, String> getFdLinkMap(String playCode) {
        Map<String, String> map = new HashMap<String, String>();
        // 文档、幻灯片
        map.put("type", "video");
        map.put("playCode", playCode);
        return map;
    }

	@Override
	public Object reCalculateMaterial(String catalogId, String materialId) {
		// TODO Auto-generated method stub
		return null;
	}
}
