package cn.me.xdf.service.material;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.common.json.JsonUtils;
import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.common.utils.array.ArrayUtils;
import cn.me.xdf.common.utils.array.SortType;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.material.ExamQuestion;
import cn.me.xdf.model.material.MaterialAuth;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.model.organization.User;
import cn.me.xdf.model.score.ScoreStatistics;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.BaseService;
import cn.me.xdf.service.SysOrgPersonService;
import cn.me.xdf.service.score.ScoreStatisticsService;
import cn.me.xdf.utils.DateUtil;
import cn.me.xdf.utils.ShiroUtils;
import cn.me.xdf.view.model.VMaterialData;

/**
 * 
 * 资源service
 * 
 * @author
 * 
 */
@Service
@Transactional(readOnly = true)
public class MaterialService extends BaseService {

	@Autowired
	private AccountService accountService;

	@Autowired
	private MaterialAuthService materialAuthService;
	
	@Autowired
	private ScoreStatisticsService scoreStatisticsService;
	
	@Autowired
	private SysOrgPersonService sysOrgPersonService;


	@SuppressWarnings("unchecked")
	@Override
	public Class<MaterialInfo> getEntityClass() {
		return MaterialInfo.class;
	}
	public SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd h:m:s a");
	/**
	 * 根据fdtype 和 modelId 拼接导出的list
	 * @param modelIds
	 * @param fdType
	 * @return
	 */
	public List<VMaterialData> findExportMaterialList(String[] modelIds,String fdType){
		List<VMaterialData> list = new ArrayList<VMaterialData>();
		for (String modelId : modelIds) {
			VMaterialData data = new VMaterialData();
			MaterialInfo info = this.load(modelId);
			data.setFdDownloads(info.getFdDownloads()==null?0:info.getFdDownloads());
			data.setFdLauds(info.getFdLauds()==null?0:info.getFdLauds());
			data.setFdPlays(info.getFdPlays()==null?0:info.getFdPlays());
			data.setFdName(info.getFdName());
			ScoreStatistics score = scoreStatisticsService.findScoreStatisticsByModelNameAndModelId(MaterialInfo.class.getName(), modelId);
			if(score!=null){
				data.setScore(score.getFdAverage()==null?0:score.getFdAverage());
			}else{
				data.setScore(0.0);
			}
			String time = sdf.format(info.getFdCreateTime());
			data.setFdCreateTime(time);
			if(info.getFdType().equals(Constant.MATERIAL_TYPE_VIDEO)){
				data.setFdType("视频");
			}else if(info.getFdType().equals(Constant.MATERIAL_TYPE_DOC)){
				data.setFdType("文档");
			}else if(info.getFdType().equals(Constant.MATERIAL_TYPE_PPT)){
				data.setFdType("幻灯片");
			}else{
				data.setFdType("素材");
			}
			list.add(data);
		}
		return list;
	}
	/**
	 * （按页）导出时 封装list 
	 * @param list
	 * @return
	 */
	public List<VMaterialData> findExportMaterialByPageList(List list){
		List<VMaterialData> matreiallist = new ArrayList<VMaterialData>();
		for (Object obj : list) {
		  VMaterialData data = new VMaterialData();
		  Map map = (Map) obj;
		  MaterialInfo info = this.load((String)map.get("FDID"));
		  data.setFdDownloads(info.getFdDownloads()==null?0:info.getFdDownloads());
		  data.setFdLauds(info.getFdLauds()==null?0:info.getFdLauds());
		  data.setFdPlays(info.getFdPlays()==null?0:info.getFdPlays());
		  data.setFdName(info.getFdName());
		  ScoreStatistics score = scoreStatisticsService
				  .findScoreStatisticsByModelNameAndModelId(MaterialInfo.class.getName(), info.getFdId());
			if(score!=null){
				data.setScore(score.getFdAverage()==null?0:score.getFdAverage());
			}else{
				data.setScore(0.0);
			}
			String time = sdf.format(info.getFdCreateTime());
			data.setFdCreateTime(time);
			if(info.getFdType().equals(Constant.MATERIAL_TYPE_VIDEO)){
				data.setFdType("视频");
			}else if(info.getFdType().equals(Constant.MATERIAL_TYPE_DOC)){
				data.setFdType("文档");
			}else if(info.getFdType().equals(Constant.MATERIAL_TYPE_PPT)){
				data.setFdType("幻灯片");
			}else{
				data.setFdType("素材");
			}
			matreiallist.add(data);
		}
		return matreiallist;
	}

	/**
	 * 根据素材id获取素材权限信息
	 * @param MaterialId
	 *            yuhz
	 * @return
	 */
	public List<Map> findAuthInfoByMaterialId(String MaterialId) {
		// 获取课程ID
		List<MaterialAuth> auths = materialAuthService.findByProperty(
				"material.fdId", MaterialId);
		List<Map> list = new ArrayList<Map>();
		User user = null;
		for (int i = 0; i < auths.size(); i++) {
			MaterialAuth materialAuth = auths.get(i);
			SysOrgPerson person = materialAuth.getFdUser();
			Map map = new HashMap();
			map.put("id", person.getFdId());
			map.put("index", i);
			map.put("imgUrl", person.getPoto());
			map.put("name", person.getRealName());
			map.put("mail", person.getFdEmail());
			map.put("org", "");
			map.put("department", person.getDeptName());
			map.put("tissuePreparation", materialAuth.getIsReader());
			map.put("editingCourse", materialAuth.getIsEditer());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 根据素材id获取素材权限信息
	 * @param MaterialId
	 *            yuhz
	 * @return
	 */
	public String findFilePathByMaterialId(String attmid) {
//		Finder finder = Finder
//				.create("from AttMain attm ");
//		finder.append("where attm.fdId = :attmid");
//		finder.setParam("attmid", attmid);
		AttMain attm = super.get(AttMain.class, attmid);
//		List<MaterialAuth> list = super.find(finder);
//		for (MaterialAuth materialAuth : list) {
//			delete(materialAuth.getFdId());
//		}
		return attm.getFdFilePath();
	}
	/**
	 * 编辑视频素材
	 * 
	 * @param material
	 * @param fdId
	 */
	@Transactional(readOnly = false)
	public void updateMaterial(MaterialInfo material, String fdId) {
		MaterialInfo info = this.get(fdId);
		info.setFdAuthorDescription(material.getFdAuthorDescription());
		info.setFdAuthor(material.getFdAuthor());
		info.setFdDescription(material.getFdDescription());
		info.setIsPublish(material.getIsPublish());
		info.setFdLink(material.getFdLink());
		info.setFdName(material.getFdName());
		info.setAuthList(material.getAuthList());
		info.setQuestions(material.getQuestions());
		this.update(info);
	}

	/**
	 * 保存素材的相关权限
	 * 
	 * @param kingUser
	 * @param materialId
	 */
	@Transactional(readOnly = false)
	public void saveMaterAuth(String kingUser, String materialId,String creatorId) {
		if (StringUtil.isNotBlank(kingUser)) {
			List<Map> list = JsonUtils.readObjectByJson(kingUser, List.class);
			MaterialInfo info = this.get(materialId);
			// 删除素材的权限
			if (StringUtil.isNotBlank(materialId)
					&& StringUtil.isNotEmpty(materialId)) {
				materialAuthService.deleMaterialAuthByMaterialId(materialId);
			}
			for (Map map : list) {
				String personid =map.get("id").toString();
				if(personid.equals(creatorId)){
					continue;
				}
				MaterialAuth auth = new MaterialAuth();
				auth.setMaterial(info);
				SysOrgPerson fdUser = accountService.get((String) map.get("id"));
				auth.setFdUser(fdUser);
				auth.setIsReader((Boolean) map.get("tissuePreparation"));
				auth.setIsEditer((Boolean) map.get("editingCourse"));
				materialAuthService.save(auth);
			}
		}
	}

	/**
	 * 查看可使用的资源（分页操作）
	 * @param fdType 资源类型
	 * @param pageNo 页码
	 * @param pageSize 每页几条数据
	 * @param fdName 不为空是表示搜索
	 * @param order 排序规则
	 * @author yuhuizhe
	 * @return
	 */
	  @Transactional(readOnly = false)
		public Pagination findMaterialList(String fdType,Integer pageNo, Integer pageSize,String fdName,String order){
			Finder finder = Finder.create("select * from ( select info.*,score.fdaverage,att.flag,att.filenetid,att.playcode,");
			
			if(ShiroUtils.isAdmin()){
				finder.append(" '1'");
			} else {
				finder.append(" case when info.fdCreatorId = '"+ShiroUtils.getUser().getId()+"' then '1'");
				finder.append(" else (case when temp.fdmaterialid is null then '0' else '1' end ) end");
				
			}
			finder.append(" as authflag,");
			
			finder.append(" sysperson.fd_name as creatorName");
			
			if(Constant.MATERIAL_TYPE_TEST.equals(fdType)){//测试统计
				finder.append(" ,a.questionNum,a.fdtotalnum");
			}
			if(Constant.MATERIAL_TYPE_JOBPACKAGE.equals(fdType)){//作业类统计
				finder.append(" ,t.tasknum,t.fullmarks ");
			}
			finder.append(" from IXDF_NTP_MATERIAL info ");
			finder.append(" left join IXDF_NTP_SCORE_STATISTICS score on info.FDID = score.fdModelId and score.fdmodelname = '"+MaterialInfo.class.getName()+"' ");
			//附件
			finder.append(" left join ixdf_ntp_att_main att on info.FDID = att.fdModelId and att.fdmodelname = '"+MaterialInfo.class.getName()+"' ");
		    //可编辑的
			finder.append(" left join (select ma.fdmaterialid from IXDF_NTP_MATERIAL_AUTH ma");
			finder.append(" where ma.isediter='Y' and ma.fduserid='"+ShiroUtils.getUser().getId()+"') temp");
			finder.append(" on info.FDID = temp.fdmaterialid");
			//找出创建者
			finder.append(" left join (select person.fdid,person.fd_name from Sys_Org_Element person ) sysperson on sysperson.fdid = info.fdcreatorid");
			 
				       
			if(Constant.MATERIAL_TYPE_TEST.equals(fdType)){
				finder.append(" left join ( ");
				finder.append(" select count(*) as questionNum,sum(fdstandardscore) as fdtotalnum,fdmaterialid from IXDF_NTP_EXAM_QUESTION group by fdmaterialid) a ");
				finder.append(" on a.fdmaterialid=info.fdid  ");
			}
			if(Constant.MATERIAL_TYPE_JOBPACKAGE.equals(fdType)){
				finder.append(" left join ( ");
				finder.append(" select count(*) as tasknum,sum(fdstandardscore) as fullmarks,fdmaterialid from ixdf_ntp_task group by fdmaterialid) t ");
				finder.append(" on t.fdmaterialid=info.fdid  ");
			}
			finder.append(" where info.FDTYPE=:fdType and info.isAvailable='Y' ");
			if(!ShiroUtils.isAdmin()){
			    finder.append(" and ( info.fdCreatorId='"+ShiroUtils.getUser().getId()+"' or info.ispublish='Y' ");
				finder.append(" or exists ( select auth.fdid from IXDF_NTP_MATERIAL_AUTH auth where auth.fdmaterialId = info.fdid ");
				finder.append(" and ( auth.isEditer='Y' or auth.isreader='Y') and auth.FDUSERID='"+ShiroUtils.getUser().getId()+"')  )");
			}
			finder.setParam("fdType", fdType);
			if(StringUtil.isNotBlank(fdName)&&StringUtil.isNotEmpty(fdName)){
				finder.append(" and info.FDNAME like :fdName");
				finder.setParam("fdName", '%' + fdName + '%');
			}
			if(StringUtil.isNotBlank(order)&&StringUtil.isNotEmpty(order)){
				if(order.equalsIgnoreCase("fdName")){
					finder.append(" order by info.fdName ");
				}
				if(order.equalsIgnoreCase("FDCREATETIME")){
					finder.append(" order by info.FDCREATETIME desc ");
				}
				if(order.equalsIgnoreCase("FDSCORE")){
					finder.append(" order by nvl(score.fdaverage,0) desc ");
				}
			}
			//WG 添加别名
			finder.append(" ) YYY ");
			Pagination page = getPageBySql(finder, pageNo, pageSize);
			return page;
		}
	  
	  /**
		 * 根据输入关键字查询(全部下载素材的时候)
		 * @return
		 */
		@Transactional(readOnly = false)
		public Pagination findMaterialByKey(String fdType, String key, Integer pageNo, Integer pageSize){
			Finder finder = Finder.create("select info.* from IXDF_NTP_MATERIAL info ");
			finder.append(" where info.FDTYPE=:fdType and info.isAvailable='Y'");
			finder.setParam("fdType", fdType);
			if(StringUtil.isNotBlank(key)&&StringUtil.isNotEmpty(key)){
				finder.append(" and info.FDNAME like :fdName");
				finder.setParam("fdName", '%' + key + '%');
			}
			if(!ShiroUtils.isAdmin()){
			    finder.append(" and ( info.fdCreatorId='"+ShiroUtils.getUser().getId()+"' or info.ispublish='Y' ");
				finder.append(" or exists ( select auth.fdid from IXDF_NTP_MATERIAL_AUTH auth where auth.fdmaterialId = info.fdid ");
				finder.append(" and ( auth.isEditer='Y' or auth.isreader='Y') and auth.FDUSERID='"+ShiroUtils.getUser().getId()+"')  )");
			}
			Pagination page = getPageBySql(finder, pageNo, pageSize);
			return page;
		}


	/**
	 * 查看当前用户可用的资源
	 */
	@SuppressWarnings("unchecked")
	public List<MaterialInfo> findCanUsed() {
		Finder finder = Finder
				.create("from MaterialAuth anth, MaterialInfo info ");
		finder.append("where (info.isPublish='true') or (anth.isReader='true' and anth.fdUser.fdId=:userId and info.fdId=anth.material.fdId )");
		finder.setParam("userId", ShiroUtils.getUser().getId());
		return super.find(finder);
	}

	/**
	 * 修改资源权限
	 */
	public void updateMaterialAuth(String materialAuthId,
			List<MaterialAuth> materialAuths) {
		// 删除所有相关的权限信息
		materialAuthService.deleMaterialAuthByMaterialId(materialAuthId);
		// 插入权限信息
		for (MaterialAuth materialAuth : materialAuths) {
			materialAuthService.save(materialAuth);
		}
	}

	/**
	 * 模糊查询资源
	 */
	public List<Map> getMaterialsTop10Bykey(String key, String type) {

		Finder finder = Finder
				.create("select info.FDID as id , info.FDNAME as name , info.fdCreatorId as creator , info.fdCreateTime as createtime from IXDF_NTP_MATERIAL info ");
		if(!ShiroUtils.isAdmin()){
			finder.append("left join IXDF_NTP_MATERIAL_AUTH auth ");
			finder.append(" on info.FDID=auth.FDMATERIALID ");
		}
		finder.append(" where info.FDTYPE=:fdType and info.ISAVAILABLE='Y' and lower(info.FDNAME) like :key ");
		if(!ShiroUtils.isAdmin()){
			finder.append(" and ( (auth.FDUSERID='" + ShiroUtils.getUser().getId()
					+ "' and auth.ISREADER='Y' ) ");
			finder.append("  or info.ISPUBLISH='Y' or info.FDCREATORID = :user) ");
			finder.setParam("user", ShiroUtils.getUser().getId());
		}
			finder.setParam("key", "%" + key + "%");
			finder.setParam("fdType", type);
			finder.append(" order by info.fdCreateTime ");
		List<Map> list = (List<Map>) (getPageBySql(finder, 1, 10).getList());
		if (list == null) {
			return null;
		}
		List<Map> maps = new ArrayList<Map>();
		for (Map map1 : list) {
			Map map = new HashMap();
			map.put("id", map1.get("id"));
			map.put("name", map1.get("name"));
			map.put("creator","");
			if(map1.get("creator")!=null){
				SysOrgPerson person = sysOrgPersonService.get((String)map1.get("creator"));
				if(person!=null){
					map.put("creator",person.getFdName());
				}
			}
			map.put("createtime", map1.get("createtime"));
			maps.add(map);
		}

		return maps;
	}
	
	
	public List<Map> getExamQuestionByMaterId(MaterialInfo info){
		 if(info.getQuestions()==null||info.getQuestions().size()==0){
			 return null;
		 }
		 List<ExamQuestion> examQuestions = info.getQuestions();
		 ArrayUtils.sortListByProperty(examQuestions, "fdOrder", SortType.HIGHT);
		/* Collections.sort(examQuestions, new Comparator<ExamQuestion>() {  
	          public int compare(ExamQuestion a, ExamQuestion b) {  
	        	  try {
	        		  int one = a.getFdOrder();  
		              int two = b.getFdOrder ();   
		              return one- two ;  
				  } catch (Exception e) {
						return -1;
				  }
	            }  
	     });*/
		 List<Map> list = new ArrayList<Map>();
		 for (ExamQuestion examQuestion : examQuestions) {
			 Map map = new HashMap();
			 map.put("id", examQuestion.getFdId());
			 map.put("subject", examQuestion.getFdSubject());
			 map.put("score", examQuestion.getFdStandardScore());
			 map.put("index", examQuestion.getFdOrder());
			 list.add(map);
		}
		 return list;
	}
	
	public List<Map> getExamQuestionSrcByMaterId(MaterialInfo info){
		 List<ExamQuestion> examQuestions = info.getQuestions();
		 List<Map> list = new ArrayList<Map>();
		 for (ExamQuestion examQuestion : examQuestions) {
			 Map map = new HashMap();
			 map.put("id", examQuestion.getFdId());
			 String subject = examQuestion.getFdSubject();
			 String[] s = subject.split("#");
			 String res = "";
			 for (int i = 0; i < s.length; i++) {
				if (i % 2 ==0) {
					res = res + s[i];
				}else{
					res = res + "____";
				}
			 }
			 map.put("subject", res);
			 map.put("score", examQuestion.getFdStandardScore());
			 list.add(map);
		}
		 return list;
	}
	
	public Map getTotalSorce(String materialId){
		StringBuffer finder = new StringBuffer("select count(*) as num,");
		finder.append("       sum(q.fdstandardscore) as totalscore ");
		finder.append("  from IXDF_NTP_EXAM_QUESTION q ");
		finder.append("  where q.fdMaterialId=:materialId ");
		finder.append(" group by q.fdmaterialid ");
		finder.append("union all ");
		finder.append("select count(*) as num,\n");
		finder.append("       sum(t.fdstandardscore) as totalscore ");
		finder.append("  from ixdf_ntp_task t ");
		finder.append("  where t.fdMaterialId=:materialId ");
		finder.append(" group by t.fdmaterialid");
		Map map = new HashMap();
		map.put("materialId", materialId);
		List<Map> maps = findBySQL(finder.toString(), Map.class, map);
		Map returnMap = new HashMap();
		returnMap.put("num",maps.size()==0?0:new Integer(maps.get(0).get("NUM").toString()));
		returnMap.put("totalscore", maps.size()==0?0.0:new Double(maps.get(0).get("TOTALSCORE").toString()));
		return returnMap;
	}

}


