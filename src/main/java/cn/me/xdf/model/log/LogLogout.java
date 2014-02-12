package cn.me.xdf.model.log;

import java.util.Date;
/**
 * 
 * 登出日志
 * 
 * @author zhaoq
 * 
 */
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.IdEntity;
import cn.me.xdf.model.organization.SysOrgPerson;
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_LOGLOGOUT")
public class LogLogout extends BaseLog{
	
	/**
	 * 登出人
	 */
	private SysOrgPerson person;
	
	/**
	 * 登出时间
	 */
	private Date time;
	
	/**
	 * 登出ip
	 */
	private String ip;
	
	/**
	 * 登出SessionId
	 */
	private String sessionId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdPersonId")
	public SysOrgPerson getPerson() {
		return person;
	}

	public void setPerson(SysOrgPerson person) {
		this.person = person;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
	
	
}
