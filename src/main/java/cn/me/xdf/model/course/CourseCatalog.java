package cn.me.xdf.model.course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.base.IdEntity;

import java.util.Date;

/**
 * 课程章节实体的定义
 *
 * @author zuoyi
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_COURSE_CATALOG")
public class CourseCatalog extends IdEntity {

    /**
     * 所属课程
     */
    private CourseInfo courseInfo;

    /**
     * 上级章节
     */
    private CourseCatalog hbmParent;

    /**
     * 章节名称
     */
    private String fdName;

    /**
     * 章节描述
     */
    private String fdDescription;

    /**
     * 备注
     */
    private String fdRemark;

    /**
     * 总节数
     */
    private Integer fdTotalPart;

    /**
     * 总内容项
     */
    private Integer fdTotalContent;

    /**
     * 学习时长
     */
    private String fdStudyTime;

    /**
     * 章节序号
     */
    private Integer fdNo;

    /**
     * 总序号
     */
    private Integer fdTotalNo;

    /**
     * 过关条件（%）
     */
    private Double fdPassCondition;

    /**
     * 素材类型：
     * 01视频
     * 02音频
     * 03图片
     * 04文档
     * 05幻灯片
     * 06网页(链接)
     * 07富文本
     * 08测试
     * 09测评
     * 10作业
     * 11日程
     */
    private String fdMaterialType;

    /**
     * 章节类型 0表示章，1表示节
     */
    private Integer fdType;

    public Integer getFdType() {
        return fdType;
    }

    public void setFdType(Integer fdType) {
        this.fdType = fdType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fdCourseId")
    @JsonIgnore
    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fdParentId")
    public CourseCatalog getHbmParent() {
        return hbmParent;
    }

    public void setHbmParent(CourseCatalog hbmParent) {
        this.hbmParent = hbmParent;
    }

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    @Column(length = 2000)
    public String getFdDescription() {
        return fdDescription;
    }

    public void setFdDescription(String fdDescription) {
        this.fdDescription = fdDescription;
    }

    @Column(length = 2000)
    public String getFdRemark() {
        return fdRemark;
    }

    public void setFdRemark(String fdRemark) {
        this.fdRemark = fdRemark;
    }

    public Integer getFdTotalPart() {
        return fdTotalPart;
    }

    public void setFdTotalPart(Integer fdTotalPart) {
        this.fdTotalPart = fdTotalPart;
    }

    public Integer getFdTotalContent() {
        return fdTotalContent;
    }

    public void setFdTotalContent(Integer fdTotalContent) {
        this.fdTotalContent = fdTotalContent;
    }

    public String getFdStudyTime() {
        return fdStudyTime;
    }

    public void setFdStudyTime(String fdStudyTime) {
        this.fdStudyTime = fdStudyTime;
    }

    public Integer getFdNo() {
        return fdNo;
    }

    public void setFdNo(Integer fdNo) {
        this.fdNo = fdNo;
    }

    public Integer getFdTotalNo() {
        return fdTotalNo;
    }

    public void setFdTotalNo(Integer fdTotalNo) {
        this.fdTotalNo = fdTotalNo;
    }

    public Double getFdPassCondition() {
        return fdPassCondition;
    }

    public void setFdPassCondition(Double fdPassCondition) {
        this.fdPassCondition = fdPassCondition;
    }

    public String getFdMaterialType() {
        return fdMaterialType;
    }

    public void setFdMaterialType(String fdMaterialType) {
        this.fdMaterialType = fdMaterialType;
    }


    /**
     * *******************************************************************************************************************
     * /* NOT DB PROPERTY
     * /*
     * /********************************************************************************************************************
     */

    public Boolean through;

    /**
     * 是否通过
     *
     * @return
     */
    @Transient
    @org.hibernate.annotations.Type(type="yes_no")
    public Boolean getThrough() {
        return through;
    }

    public void setThrough(Boolean through) {
        this.through = through;
    }

    @JsonIgnore
    @Transient
    public String getMaterialType() {
        String materialType = getFdMaterialType();
        if (Constant.MATERIAL_TYPE_VIDEO.equals(materialType)) {
            return "video";
        }
        if (Constant.MATERIAL_TYPE_AUDIO.equals(materialType)) {
            return "audio";
        }
        if (Constant.MATERIAL_TYPE_DOC.equals(materialType)) {
            return "doc";
        }
        if (Constant.MATERIAL_TYPE_PPT.equals(materialType)) {
            return "ppt";
        }
        if (Constant.MATERIAL_TYPE_PIC.equals(materialType)) {
            return "img";
        }
        if (Constant.MATERIAL_TYPE_TEST.equals(materialType)) {
            return "exam";
        }
        if (Constant.MATERIAL_TYPE_JOBPACKAGE.equals(materialType)) {
            return "task";
        }
        if (Constant.MATERIAL_TYPE_RICHTEXT.equals(materialType)) {
            return "txt";
        }
        if (Constant.MATERIAL_TYPE_SCHEDULE.equals(materialType)) {
            return "calendar";
        }
        return "none";
    }

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    @Transient
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Transient
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
