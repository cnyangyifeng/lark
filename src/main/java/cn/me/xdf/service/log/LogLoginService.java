package cn.me.xdf.service.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.model.log.LogLogin;
import cn.me.xdf.model.log.LogOnline;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.service.BaseService;
import cn.me.xdf.utils.DateUtil;
import cn.me.xdf.utils.ShiroUtils;
import cn.me.xdf.view.model.VLogData;


@Service
@Transactional(readOnly = false)
public class LogLoginService extends BaseService{

	@Autowired
	private LogOnlineService logOnlineService;
	
	@SuppressWarnings("unchecked")
	@Override
	public  Class<LogLogin> getEntityClass() {
		return LogLogin.class;
	}

	public SysOrgPerson getPersonBySessionId(String sesstionId){
	 	LogLogin login = this.findUniqueByProperty("sessionId", sesstionId);
	 	if(login==null){
	 		return null;
	 	}
	 	return login.getPerson();
	}
	
	public void saveAndUpdateOnine(LogLogin logLogin){
		save(logLogin);
		LogOnline logOnline = logOnlineService.loginToSaveOrUpdate(logLogin.getPerson(), logLogin.getTime(), logLogin.getIp(), true);
	}
	
	/**
	 * 获取用户最后一次登录时间
	 * 
	 * @return
	 */
	public String getNewLoginDate(String userId){
		Finder finder = Finder.create("");		
		finder.append("from LogLogin l where l.person.fdId=:userid order by l.time desc");
		finder.setParam("userid", userId);
		List<LogLogin> logins = (List<LogLogin>)getPage(finder, 1,10).getList();
		if(logins==null||logins.size()<=1){
			return "0";
		}else{
			Date date = logins.get(1).getTime();
			return ""+ DateUtil.getIntervalDate(DateUtil.convertDateToString(date), "yyyy/MM/dd hh:mm aa");
		}
		
	}
	
//	/**
//	 * 统计在线天数
//	 * 
//	 * @return
//	 */
//	private int getOnlineDay(){
//		Finder finder = Finder.create("");		
//		finder.append("select count( distinct to_char(l.time,'yyyy-mm-dd')) from IXDF_NTP_LOGLOGIN l ");
//		finder.append("where l.fdpersonid='"+ShiroUtils.getUser().getId()+"' ");
//		List<Object> list = findBySQL(finder.getOrigHql(), null, null);
//		return new Integer(list.get(0).toString());
//	}

	/**
	 * 获取导出数据
	 * 
	 * @param ids
	 * @return
	 */
	public List<VLogData> findVLogData(String [] ids){
		List<VLogData> vLogDatas = new ArrayList<VLogData>();
		for (int i = 0; i < ids.length; i++) {
			VLogData logData = new VLogData();
			LogLogin logLogin = get(ids[i]);
			logData.setContent("");
			logData.setLogType("登录");
			logData.setModelId("");
			logData.setModelName("");
			logData.setTime(DateUtil.convertDateToString(logLogin.getTime(), "yyyy-MM-dd HH:mm:ss") );
			logData.setUserDept(logLogin.getPerson().getHbmParent()==null?"":logLogin.getPerson().getHbmParent().getFdName());
			logData.setUserName(logLogin.getPerson().getFdName());
			vLogDatas.add(logData);
		}
		return vLogDatas;
	}
	
	/**
	 * 获取导出数据Pagination
	 * @param key
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination findVLogDataPagination(String key, int  pageNo, int pageSize){
		Finder finder = Finder.create("select la.fdId id from IXDF_NTP_LOGLOGIN la where la.fdId in( select l.fdId from IXDF_NTP_LOGLOGIN l , SYS_ORG_ELEMENT o where l.fdpersonid=o.fdId and o.fd_name like '%"+key+"%' )  ");
		return getPageBySql(finder, pageNo, pageSize);
	}
	

	/**
	 * 获取导出数据
	 * 
	 * @param pagination
	 * @return
	 */
	public List<VLogData> findVLogDataByPagination(Pagination pagination){
		List<Map> maps = (List<Map>) pagination.getList();
		List<VLogData> vLogDatas = new ArrayList<VLogData>();
		for (int i = 0; i < maps.size(); i++) {
			VLogData logData = new VLogData();
			LogLogin logLogin = get((String)maps.get(i).get("ID"));
			logData.setContent("");
			logData.setLogType("登录");
			logData.setModelId("");
			logData.setModelName("");
			logData.setTime(DateUtil.convertDateToString(logLogin.getTime(), "yyyy-MM-dd HH:mm:ss") );
			logData.setUserDept(logLogin.getPerson().getHbmParent()==null?"":logLogin.getPerson().getHbmParent().getFdName());
			logData.setUserName(logLogin.getPerson().getFdName());
			vLogDatas.add(logData);
		}
		return vLogDatas;
	}

}
	

