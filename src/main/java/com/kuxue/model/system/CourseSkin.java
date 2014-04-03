package com.kuxue.model.system;

import java.util.Date;

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
@Table(name = "IXDF_NTP_COURSE_SKIN")
public class CourseSkin  extends IdEntity{
	/**
	 * 皮肤名称
	 */
	private String fdName;
	
	/**
	 * 皮肤路径
	 */
	private String fdSkinPath;
	
	/**
	 * 是否默认皮肤
	 */
	private boolean fdDefaultSkin;
	
	/**
	 * 	 * 类型：01表示课程皮肤，02表示系列课程皮肤，03表示个人主页皮肤
	 */
	private String fdType;
	
	/**
	 * 创建时间
	 */
	private Date fdCreateTime;
	
	/**
	 * 创建者
	 */
	private SysOrgPerson creator;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdSkinPath() {
		return fdSkinPath;
	}

	public void setFdSkinPath(String fdSkinPath) {
		this.fdSkinPath = fdSkinPath;
	}

	@org.hibernate.annotations.Type(type="yes_no")
	public boolean getFdDefaultSkin() {
		return fdDefaultSkin;
	}

	public void setFdDefaultSkin(boolean fdDefaultSkin) {
		this.fdDefaultSkin = fdDefaultSkin;
	}
	
	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdCreatorId")
	public SysOrgPerson getCreator() {
		return creator;
	}

	public void setCreator(SysOrgPerson creator) {
		this.creator = creator;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}
	
}
