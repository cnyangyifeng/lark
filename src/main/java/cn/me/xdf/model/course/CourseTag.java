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

/**
 * 
 * 课程标签关系实体的定义
 * 
 * @author zuoyi
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_COURSE_TAG")
public class CourseTag extends IdEntity{

	/**
	 * 课程
	 */
	private CourseInfo courses;
	
	/**
	 * 标签
	 */
	private TagInfo tag;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdCourseId")
	public CourseInfo getCourses() {
		return courses;
	}

	public void setCourses(CourseInfo courses) {
		this.courses = courses;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdTagId")
	public TagInfo getTag() {
		return tag;
	}

	public void setTag(TagInfo tag) {
		this.tag = tag;
	}
	
}
