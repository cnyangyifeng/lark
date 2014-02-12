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
 * 系列课程关系实体的定义
 * 
 * @author zuoyi
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_SERIES_COURSE")
public class SeriesCourses extends IdEntity{

	/**
	 * 系列
	 */
	private SeriesInfo series;
	
	/**
	 * 课程
	 */
	private CourseInfo courses;
	
	/**
	 * 课程序号
	 */
	private Integer fdCourseNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdSeriesId")
	public SeriesInfo getSeries() {
		return series;
	}

	public void setSeries(SeriesInfo series) {
		this.series = series;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdCourseId")
	public CourseInfo getCourses() {
		return courses;
	}

	public void setCourses(CourseInfo courses) {
		this.courses = courses;
	}

	public Integer getFdCourseNo() {
		return fdCourseNo;
	}

	public void setFdCourseNo(Integer fdCourseNo) {
		this.fdCourseNo = fdCourseNo;
	}
	
}
