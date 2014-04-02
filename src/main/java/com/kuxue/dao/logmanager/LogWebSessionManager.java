package com.kuxue.dao.logmanager;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.web.SpringUtils;
import com.kuxue.model.log.LogLogin;
import com.kuxue.service.log.LogLoginService;

public class LogWebSessionManager extends DefaultWebSessionManager implements  WebSessionManager {

    public void validateSessions() {  
        super.validateSessions();
        LogLoginService logLoginService = (LogLoginService)SpringUtils.getBean("logLoginService");
        Collection<?> activeSessions = getActiveSessions();  
        
        StringBuffer sql = new StringBuffer();
        sql.append("update IXDF_NTP_LOGONLINE l set l.isOnline='0' where l.isOnline='Y' ");
        StringBuffer sqlLogin = new StringBuffer();
        sqlLogin.append("update IXDF_NTP_LOGLOGIN l set l.sessionId=null");
        String userids = "";
        String sessionids = "";
        if (activeSessions != null && !activeSessions.isEmpty()) {  
            for (Iterator<?> i$ = activeSessions.iterator(); i$.hasNext();) {  
                Session session = (Session) i$.next();
                Finder finder = Finder.create("");
                finder.append("from LogLogin l where l.sessionId = :sessionId order by l.time desc");
                finder.setParam("sessionId", session.getId());
                List<LogLogin> logLogs = (List<LogLogin>) logLoginService.getPage(finder, 1, 1).getList();
                if(logLogs!=null && logLogs.size()>0){
                	String userId = logLogs.get(0).getPerson().getFdId();
                    userids = userids+"'"+userId+"',";
                    sessionids =sessionids+"'"+session.getId()+"',";
                }
            } 
            if(userids.length()>0){
            	userids=userids.substring(0, userids.length()-1);
            	sql.append(" and l.fdpersonid not in ("+userids+")");
            }
            if(userids.length()>0){
            	sessionids=sessionids.substring(0, sessionids.length()-1);
            	sqlLogin.append(" where l.sessionId not in ("+sessionids+") ");
            }
        }
        logLoginService.executeSql(sqlLogin.toString());
        logLoginService.executeSql(sql.toString());
    }  
  
}
