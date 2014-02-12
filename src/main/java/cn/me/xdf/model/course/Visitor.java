package cn.me.xdf.model.course;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.bam.BamCourse;
import cn.me.xdf.model.base.IdEntity;
import cn.me.xdf.model.organization.SysOrgPerson;

/**
 * 
 * 最近访客实体的定义
 * 
 * @author zhaoq
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_VISITOR")
public class Visitor extends IdEntity {
	
	/**
	 * 用户
	 */
	private SysOrgPerson fdUser;
	
	/**
	 * 来访时间
	 */
	private Date fdTime;
	
	/**
	 * 对应bam
	 */
	private BamCourse bamCourse;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdUserId")
	public SysOrgPerson getFdUser() {
		return fdUser;
	}

	public void setFdUser(SysOrgPerson fdUser) {
		this.fdUser = fdUser;
	}

	public Date getFdTime() {
		return fdTime;
	}

	public void setFdTime(Date fdTime) {
		this.fdTime = fdTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bamCourseId")
	public BamCourse getBamCourse() {
		return bamCourse;
	}

	public void setBamCourse(BamCourse bamCourse) {
		this.bamCourse = bamCourse;
	}

}
