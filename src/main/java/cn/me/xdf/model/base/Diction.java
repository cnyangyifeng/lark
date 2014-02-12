package cn.me.xdf.model.base;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



/**
 * @author jiaxj 项目
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "IXDF_OTP_DICTION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Diction extends IdEntity {

	private Integer fdSeqNum;
	private String fdName;
	private Boolean fdStatus;
	private String fdDescription;
	private String fdCreatorId;
	private Date fdDateCreated;

	/**
	 * 1:项目2:课程3:分项 4:阶段
	 */
	private Integer fdType;

	public Diction() {
	}

	public Diction(String fdId) {
		this.fdId = fdId;
	}

	/**
	 * 
	 * 1:项目2:课程3:阶段4:分项
	 */
	public Integer getFdType() {
		return fdType;
	}

	/**
	 * 1:项目2:课程3:阶段4:分项
	 * 
	 * @param fdType
	 */
	public void setFdType(Integer fdType) {
		this.fdType = fdType;
	}

	/**
	 * 
	 * 序号
	 */
	public Integer getFdSeqNum() {
		return fdSeqNum;
	}

	public void setFdSeqNum(Integer fdSeqNum) {
		this.fdSeqNum = fdSeqNum;
	}

	/**
	 * 
	 * 名称
	 */
	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 状态
	 * 
	 * @return
	 */
	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(Boolean fdStatus) {
		this.fdStatus = fdStatus;
	}

	/**
	 * 
	 * 描述
	 */
	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	/**
	 * 
	 * 创建者
	 */
	public String getFdCreatorId() {
		return fdCreatorId;
	}

	public void setFdCreatorId(String fdCreatorId) {
		this.fdCreatorId = fdCreatorId;
	}

	/**
	 * 
	 * 创建时间
	 */
	public Date getFdDateCreated() {
		return fdDateCreated;
	}

	public void setFdDateCreated(Date fdDateCreated) {
		this.fdDateCreated = fdDateCreated;
	}

	@Override
	public int hashCode() {
		final int prime = 11;
		int result = 1;
		result = prime * result + getFdId().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (!getClass().equals(object.getClass())) {
			return false;
		}
		final Diction other = (Diction) object;

		if (!this.fdId.equals(other.fdId)) {
			return false;
		}
		return true;
	}

	private List<Diction> childrens;

	@Transient
	public List<Diction> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Diction> childrens) {
		this.childrens = childrens;
	}
	
	private boolean hasChecked;

	@Transient
	@org.hibernate.annotations.Type(type="yes_no")
	public boolean getHasChecked() {
		return hasChecked;
	}

	public void setHasChecked(boolean hasChecked) {
		this.hasChecked = hasChecked;
	}
	
	
}
