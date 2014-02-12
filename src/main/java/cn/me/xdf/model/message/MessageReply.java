package cn.me.xdf.model.message;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.IdEntity;
import cn.me.xdf.model.organization.SysOrgPerson;

/**
 * 
 * 消息回复实体的定义
 * 
 * @author zuoyi
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_MESSAGE_REPLY")
public class MessageReply extends IdEntity{

	/**
	 * 消息
	 */
	private Message message;
	
	/**
	 * 回复内容
	 */
	private String fdContent;
	
	/**
	 * 所属楼层号
	 */
	private String fdFloorNo;
	
	/**
	 * 回复类型：01顶，02踩，03文字
	 */
	private String fdType;
	
	/**
	 * 操作员（发回复的用户）
	 */
	private SysOrgPerson fdUser;
	
	/**
	 * 操作时间
	 */
	private Date fdCreateTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdMessageId")
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Column(length = 2000)
	public String getFdContent() {
		return fdContent;
	}

	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
	}

	public String getFdFloorNo() {
		return fdFloorNo;
	}

	public void setFdFloorNo(String fdFloorNo) {
		this.fdFloorNo = fdFloorNo;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdUserId")	
	public SysOrgPerson getFdUser() {
		return fdUser;
	}

	public void setFdUser(SysOrgPerson fdUser) {
		this.fdUser = fdUser;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}
	
}
