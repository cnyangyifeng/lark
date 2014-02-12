package cn.me.xdf.model.course;

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
 * 课程权限实体的定义
 * 
 * @author zuoyi
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_COURSE_AUTH")
public class CourseAuth extends IdEntity{

	/**
	 * 课程
	 */
	private CourseInfo course;
	
	/**
	 * 用户
	 */
	private SysOrgPerson fdUser;
	
	/**
	 * 是否可授权学习
	 */
	private Boolean isAuthStudy;
	
	/**
	 * 是否可编辑
	 */
	private Boolean isEditer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdCourseId")
	public CourseInfo getCourse() {
		return course;
	}

	public void setCourse(CourseInfo course) {
		this.course = course;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdUserId")	
	public SysOrgPerson getFdUser() {
		return fdUser;
	}

	public void setFdUser(SysOrgPerson fdUser) {
		this.fdUser = fdUser;
	}

	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsAuthStudy() {
		return isAuthStudy;
	}

	public void setIsAuthStudy(Boolean isAuthStudy) {
		this.isAuthStudy = isAuthStudy;
	}

	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsEditer() {
		return isEditer;
	}

	public void setIsEditer(Boolean isEditer) {
		this.isEditer = isEditer;
	}
	
}
