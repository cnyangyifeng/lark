package com.kuxue.service.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.page.Pagination;
import com.kuxue.model.base.Constant;
import com.kuxue.model.log.LogApp;
import com.kuxue.model.log.LogLogout;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.service.AccountService;
import com.kuxue.service.BaseService;
import com.kuxue.service.SysOrgPersonService;
import com.kuxue.utils.DateUtil;
import com.kuxue.view.model.VLogData;

@Service
@Transactional(readOnly = false)
public class LogAppService extends BaseService{

	@Autowired
	private SysOrgPersonService sysOrgPersonService;
	
	@SuppressWarnings("unchecked")
	@Override
	public  Class<LogApp> getEntityClass() {
		return LogApp.class;
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
			LogApp logApp = get(ids[i]);
			logData.setContent(logApp.getContent().trim());
			if(logApp.getMethod().equals(Constant.DB_UPDATE)){
				logData.setLogType("操作日志（修改）");
			}else if(logApp.getMethod().equals(Constant.DB_DELETE)){
				logData.setLogType("操作日志（删除）");
			}else{
				logData.setLogType("操作日志（插入）");
			}
			logData.setModelId(logApp.getModelId());
			logData.setModelName(logApp.getModelName());
			logData.setTime(DateUtil.convertDateToString(logApp.getTime(), "yyyy-MM-dd HH:mm:ss") );
			SysOrgPerson sysOrgPerson = sysOrgPersonService.load(logApp.getPersonId()) ;
			logData.setUserDept(sysOrgPerson.getHbmParent()==null?"":sysOrgPerson.getHbmParent().getFdName());
			logData.setUserName(sysOrgPerson.getFdName());
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
		Finder finder = Finder.create("select la.fdId id  from IXDF_NTP_LOGAPP la where la.fdId in( select l.fdId from IXDF_NTP_LOGAPP l , SYS_ORG_ELEMENT o where l.personId=o.fdId and o.fd_name like '%"+key+"%' ) ");
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
			LogApp logApp = get((String)maps.get(i).get("ID"));
			logData.setContent("");
			if(logApp.getMethod().equals(Constant.DB_UPDATE)){
				logData.setLogType("操作日志（修改）");
			}else if(logApp.getMethod().equals(Constant.DB_DELETE)){
				logData.setLogType("操作日志（删除）");
			}else{
				logData.setLogType("操作日志（插入）");
			}
			logData.setModelId(logApp.getModelId());
			logData.setModelName(logApp.getModelName());
			logData.setTime(DateUtil.convertDateToString(logApp.getTime(), "yyyy-MM-dd HH:mm:ss") );
			SysOrgPerson sysOrgPerson = sysOrgPersonService.load(logApp.getPersonId()) ;
			logData.setUserDept(sysOrgPerson.getHbmParent()==null?"":sysOrgPerson.getHbmParent().getFdName());
			logData.setUserName(sysOrgPerson.getFdName());
			vLogDatas.add(logData);
		}
		return vLogDatas;
	}
}
