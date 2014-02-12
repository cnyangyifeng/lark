package cn.me.xdf.model.course;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.IdEntity;
import cn.me.xdf.model.organization.SysOrgPerson;

/**
 * 课程参与权限实体的定义
 *
 * @author zuoyi
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_COURSE_PARTICI_AUTH", uniqueConstraints = {@UniqueConstraint(columnNames = {
        "fdCourseId", "fdUserId"})})
public class CourseParticipateAuth extends IdEntity {

    /**
     * 课程
     */
    private CourseInfo course;

    /**
     * 用户
     */
    private SysOrgPerson fdUser;

    /**
     * 导师
     */
    private SysOrgPerson fdTeacher;
    
    /**
     * 
     * 授权时间
     */
    private Date fdCreateTime;
    /**
     * 
     * 授权人
     */
    private SysOrgPerson fdAuthorizer;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fdTeacherId")
    public SysOrgPerson getFdTeacher() {
        return fdTeacher;
    }

    public void setFdTeacher(SysOrgPerson fdTeacher) {
        this.fdTeacher = fdTeacher;
    }

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fdAuthorizerId")
	public SysOrgPerson getFdAuthorizer() {
		return fdAuthorizer;
	}

	public void setFdAuthorizer(SysOrgPerson fdAuthorizer) {
		this.fdAuthorizer = fdAuthorizer;
	}
    
	

}
