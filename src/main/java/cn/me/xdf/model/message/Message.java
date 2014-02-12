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
 * 消息实体的定义
 * 
 * @author zuoyi
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_MESSAGE")
public class Message extends IdEntity{
	
	/**
	 * 消息类型：01评论，02备课心情，03系统消息，04评论回复
	 */
	private String fdType;
	
	/**
	 * 业务名称
	 */
	private String fdModelName;
	
	/**
	 * 业务Id
	 */
	private String fdModelId;
	
	/**
	 * 消息内容
	 */
	private String fdContent;
	
	/**
	 * 是否匿名
	 */
	private Boolean isAnonymous;
	
	/**
	 * 操作员（发消息用户）
	 */
	private SysOrgPerson fdUser;
	
	/**
	 * 操作时间
	 */
	private Date fdCreateTime;
	
	/**
	 * 楼层号
	 */
	private Integer fdFloorNo;


	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	@Column(length = 2000)
	public String getFdContent() {
		return fdContent;
	}

	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
	}

	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(Boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
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

	public Integer getFdFloorNo() {
		return fdFloorNo;
	}

	public void setFdFloorNo(Integer fdFloorNo) {
		this.fdFloorNo = fdFloorNo;
	}
	
	
	
	
}
