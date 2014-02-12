package cn.me.xdf.model.log;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.IdEntity;
import cn.me.xdf.model.organization.SysOrgPerson;
/**
 * 
 * 在线用户
 * 
 * @author zhaoq
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_LOGONLINE")
public class LogOnline extends BaseLog{

	/**
	 * 在线记录用户
	 */
	private SysOrgPerson person;
	
	/**
	 * 登录时间
	 */
	private Date loginTime;
	
	/**
	 * ip
	 */
	private String ip;
	
	/**
	 * 登录次数
	 */
	private Integer loginNum;
	
	/**
	 * 是否在线
	 */
	private Boolean isOnline;
	
	/**
	 * 登录天数
	 */
	private Integer loginDay;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdPersonId")
	public SysOrgPerson getPerson() {
		return person;
	}

	public void setPerson(SysOrgPerson person) {
		this.person = person;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}
	
	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	public Integer getLoginDay() {
		return loginDay;
	}

	public void setLoginDay(Integer loginDay) {
		this.loginDay = loginDay;
	}

	
	
	
	
}
