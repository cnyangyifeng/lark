package cn.me.xdf.dao.logmanager;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.me.xdf.common.hibernate4.HibernateSimpleDao;
import cn.me.xdf.model.log.LogLogin;
import cn.me.xdf.model.log.LogLogout;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.log.LogLoginService;
import cn.me.xdf.service.log.LogLogoutService;
import cn.me.xdf.utils.ShiroUtils;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;

public class LogSessionDAO extends HibernateSimpleDao implements SessionDAO  {
	
	
		@Autowired
		private LogLoginService logLoginService;
		@Autowired
		private LogLogoutService logLogoutService;
		
		@Autowired
		private AccountService accountService;
	
		private SessionIdGenerator sessionIdGenerator;
	
		private static final Logger log = LoggerFactory.getLogger(LogSessionDAO.class);
	
		private ConcurrentMap<Serializable, Session> sessions;
	
		private Set<Session> online_sessions;
		    
	    public LogSessionDAO() {
	    	this.sessionIdGenerator = new JavaUuidSessionIdGenerator();
	        this.sessions = new ConcurrentHashMap<Serializable, Session>();
	        this.online_sessions = new HashSet<Session>();
	    }
	
	    protected Serializable doCreate(Session session) {
	        Serializable sessionId = generateSessionId(session);
	        assignSessionId(session, sessionId);
	        storeSession(sessionId, session);
	        return sessionId;
	    }
	
	    protected Session storeSession(Serializable id, Session session) {
	        if (id == null) {
	            throw new NullPointerException("id argument cannot be null.");
	        }
	        if(!online_sessions.contains(session)){
	        	if(SecurityUtils.getSubject().isAuthenticated() && SecurityUtils.getSubject().getPrincipal()!=null){
		        	online_sessions.add(session);
		        	LogLogin logLogin = new LogLogin();
		        	logLogin.setIp(session.getHost());
		        	SysOrgPerson person = accountService.load(ShiroUtils.getUser().getId());
//		        	person.setFdId(ShiroUtils.getUser().getId());
		        	logLogin.setPerson(person);
		        	logLogin.setSessionId(session.getId().toString());
		        	Date date = new Date();
		        	logLogin.setTime(date);
	//	        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	//	        	String dateString = formatter.format(date);  
	//	        	logLogin.setCreatDay(dateString);
		        	logLoginService.saveAndUpdateOnine(logLogin);
	        	}
	    	}
	        return sessions.putIfAbsent(id, session);
	    }
	
	    protected Session doReadSession(Serializable sessionId) {
	        return sessions.get(sessionId);
	    }
	
	    public void update(Session session) throws UnknownSessionException {
	        storeSession(session.getId(), session);
	    }
	
	    public void delete(Session session) {
	    	SysOrgPerson person = logLoginService.getPersonBySessionId(session.getId().toString());
	    	if(person!=null){
	    		LogLogout logLogout = new LogLogout();
		    	logLogout.setIp(session.getHost());
	        	logLogout.setPerson(person);
	        	logLogout.setSessionId(session.getId().toString());
	        	logLogout.setTime(new Date());
	        	logLogoutService.saveAndUpdateOnine(logLogout);
	    	}
	    	online_sessions.remove(session);
	        if (session == null) {
	            throw new NullPointerException("session argument cannot be null.");
	        }
	        Serializable id = session.getId();
	        if (id != null) {
	            sessions.remove(id);
	        }
	    }
	
	    public Collection<Session> getActiveSessions() {
	        Collection<Session> values = sessions.values();
	        if (CollectionUtils.isEmpty(values)) {
	            return Collections.emptySet();
	        } else {
	            return Collections.unmodifiableCollection(values);
	        }
	    }
	
		@Override
		public Serializable create(Session session) {
			  Serializable sessionId = doCreate(session);
		        verifySessionId(sessionId);
		        return sessionId;
		}
	
		@Override
		public Session readSession(Serializable sessionId)
				throws UnknownSessionException {
			  Session s = doReadSession(sessionId);
		        if (s == null) {
		            throw new UnknownSessionException("There is no session with id [" + sessionId + "]");
		        }
		        return s;
		}
	
		private void verifySessionId(Serializable sessionId) {
		        if (sessionId == null) {
		            String msg = "sessionId returned from doCreate implementation is null.  Please verify the implementation.";
		        throw new IllegalStateException(msg);
		    }
		}
		
		protected Serializable generateSessionId(Session session) {
		    if (this.sessionIdGenerator == null) {
		            String msg = "sessionIdGenerator attribute has not been configured.";
		        throw new IllegalStateException(msg);
		    }
		    return this.sessionIdGenerator.generateId(session);
		}
		
		protected void assignSessionId(Session session, Serializable sessionId) {
		        ((SimpleSession) session).setId(sessionId);
		}
		
}
