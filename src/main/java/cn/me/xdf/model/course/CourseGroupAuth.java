package cn.me.xdf.model.course;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.IdEntity;
import cn.me.xdf.model.organization.SysOrgGroup;

/**
 * 
 * 课程群组权限实体的定义
 * 
 * @author zhaoq
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_COURSE_GROUP_AUTH")
public class CourseGroupAuth extends IdEntity{

	/**
	 * 课程
	 */
	private CourseInfo course;
	
	/**
	 * 群组
	 */
	private SysOrgGroup group;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdCourseId")
	public CourseInfo getCourse() {
		return course;
	}

	public void setCourse(CourseInfo course) {
		this.course = course;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdGroupId")
	public SysOrgGroup getGroup() {
		return group;
	}

	public void setGroup(SysOrgGroup group) {
		this.group = group;
	}
	
	
	
}
