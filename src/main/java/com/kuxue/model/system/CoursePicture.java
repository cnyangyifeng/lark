package com.kuxue.model.system;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.kuxue.model.base.IdEntity;

@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_COURSE_PICTURE")
public class CoursePicture extends IdEntity{
	/**
	 * 图片ID
	 */
	private String fdPictureId;
	
	/**
	 * 课程或系列ID
	 */
	private String fdCourseId;

	public String getFdPictureId() {
		return fdPictureId;
	}

	public void setFdPictureId(String fdPictureId) {
		this.fdPictureId = fdPictureId;
	}

	public String getFdCourseId() {
		return fdCourseId;
	}

	public void setFdCourseId(String fdCourseId) {
		this.fdCourseId = fdCourseId;
	}
	
	
}
