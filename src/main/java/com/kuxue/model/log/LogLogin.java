package com.kuxue.model.log;

import java.util.Date;

/**
 * 
 * 登录日志
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

import com.kuxue.model.base.IdEntity;
import com.kuxue.model.organization.SysOrgPerson;
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_LOGLOGIN")
public class LogLogin extends BaseLog{

	/**
	 * 登录人
	 */
	private SysOrgPerson person;
	
	/**
	 * 登录时间（带时分秒）
	 */
	private Date time;
	
	/**
	 * 登录ip
	 */
	private String ip;
	
	/**
	 * 登录SessionId
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
