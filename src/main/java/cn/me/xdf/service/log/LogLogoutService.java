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
import cn.me.xdf.common.utils.Identities;
import cn.me.xdf.model.log.LogLogin;
import cn.me.xdf.model.log.LogLogout;
import cn.me.xdf.service.BaseService;
import cn.me.xdf.utils.DateUtil;
import cn.me.xdf.view.model.VLogData;

@Service
@Transactional(readOnly = false)
public class LogLogoutService extends BaseService{


	@Autowired
	private LogOnlineService logOnlineService;
	
	@SuppressWarnings("unchecked")
	@Override
	public  Class<LogLogout> getEntityClass() {
		return LogLogout.class;
	}

	public void saveAndUpdateOnine(LogLogout logLogout){
		String sql = "insert into ixdf_ntp_loglogout values(?,"+null+",?,?,?,?)";
	   	executeSql(sql, Identities.generateID(),logLogout.getIp(),logLogout.getSessionId(),new Date(),logLogout.getPerson().getFdId());
		//清除Loglogin
	   	String sqlLogin = "update IXDF_NTP_LOGLOGIN l set l.sessionId=null where l.sessionId ='"+logLogout.getSessionId()+"' and l.FDPERSONID='"+logLogout.getPerson().getFdId()+"' ";
	   	executeSql(sqlLogin);
	   	logOnlineService.logoutToSaveOrUpdate(logLogout.getPerson().getFdId(), null, logLogout.getIp(), false);
	}
	
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
			LogLogout logLogout = get(ids[i]);
			logData.setContent("");
			logData.setLogType("登录");
			logData.setModelId("");
			logData.setModelName("");
			logData.setTime(DateUtil.convertDateToString(logLogout.getTime(), "yyyy-MM-dd HH:mm:ss") );
			logData.setUserDept(logLogout.getPerson().getHbmParent()==null?"":logLogout.getPerson().getHbmParent().getFdName());
			logData.setUserName(logLogout.getPerson().getFdName());
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
		Finder finder = Finder.create("select la.fdId id  from IXDF_NTP_LOGLOGOUT la where  la.fdId in( select l.fdId from IXDF_NTP_LOGLOGOUT l , SYS_ORG_ELEMENT o where l.fdpersonid=o.fdId and o.fd_name like '%"+key+"%' )  ");
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
			LogLogout logLogout = get((String)maps.get(i).get("ID"));
			logData.setContent("");
			logData.setLogType("登录");
			logData.setModelId("");
			logData.setModelName("");
			logData.setTime(DateUtil.convertDateToString(logLogout.getTime(), "yyyy-MM-dd HH:mm:ss") );
			logData.setUserDept(logLogout.getPerson().getHbmParent()==null?"":logLogout.getPerson().getHbmParent().getFdName());
			logData.setUserName(logLogout.getPerson().getFdName());
			vLogDatas.add(logData);
		}
		return vLogDatas;
	}
	
}
