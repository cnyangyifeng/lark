package cn.me.xdf.model.letter;

import java.util.Date;

import javax.persistence.Column;
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
 * 私信实体
 * @author 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_LETTER")
public class PrivateLetter extends IdEntity {
	/**
	 * 内容
	 */
	private String body;
	/**
	 * 是否已读(接受者)
	 */
	private boolean isRead;
	/**
	 * 发送人
	 */
	private SysOrgPerson sendUser;
    /**
     * 接受人	
     */
	private SysOrgPerson acceptUser;
	
	/**
	 * 创建时间
	 */
	private Date fdCreateTime;
	
	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	@Column(length = 2000)
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@org.hibernate.annotations.Type(type="yes_no")
	public boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sendUserId")
	public SysOrgPerson getSendUser() {
		return sendUser;
	}
	public void setSendUser(SysOrgPerson sendUser) {
		this.sendUser = sendUser;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "acceptUserId")
	public SysOrgPerson getAcceptUser() {
		return acceptUser;
	}
	public void setAcceptUser(SysOrgPerson acceptUser) {
		this.acceptUser = acceptUser;
	}
	
}
