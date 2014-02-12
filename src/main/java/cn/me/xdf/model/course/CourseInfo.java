package cn.me.xdf.model.course;

import java.util.Date;
import java.util.List;


import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import cn.me.xdf.annotaion.AttMainMachine;
import cn.me.xdf.annotaion.AttValues;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.base.IAttMain;
import cn.me.xdf.model.base.IdEntity;
import cn.me.xdf.model.material.MaterialAuth;
import cn.me.xdf.model.organization.SysOrgPerson;

/**
 * 
 * 课程实体的定义
 * 
 * @author zuoyi
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_COURSE")
@AttMainMachine(modelName="cn.me.xdf.model.course.CourseInfo", value = { @AttValues(fild = "attMains") })
public class CourseInfo extends IdEntity implements IAttMain{
	
	/**
	 * 课程标题
	 */
	private String fdTitle;
	
	/**
	 * 课程副标题
	 */
	private String fdSubTitle;
	
	/**
	 * 课程定价
	 */
	private Double fdPrice;
	/**
	 * 课程分类
	 */
	private CourseCategory fdCategory;
	
	/**
	 * 课程摘要
	 */
	private String fdSummary;
	
	/**
	 * 学习目标
	 */
	private String fdLearnAim;
	
	/**
	 * 建议群体
	 */
	private String fdProposalsGroup;
	
	/**
	 * 课程要求
	 */
	private String fdDemand;
	
	/**
	 * 课程封面
	 */
	private List<AttMain> attMains;
	
	/**
	 * 课程皮肤
	 */
	private String fdSkin;
	
	/**
	 * 是否公开
	 */
	private Boolean isPublish;
	
	/**
	 * 课程密码
	 */
	private String fdPassword;
	
	/**
	 * 是否必修课
	 */
	private Boolean isCompulsoryCourse;
	
	/**
	 * 创建时间
	 */
	private Date fdCreateTime;
	
	/**
	 * 创建者
	 */
	private SysOrgPerson creator;
	
	/**
	 * 作者
	 */
	private String fdAuthor;
	
	/**
	 * 
	 * 作者Id
	 */
	private String fdAuthorId;
	
	public String getFdAuthorId() {
		return fdAuthorId;
	}

	public void setFdAuthorId(String fdAuthorId) {
		this.fdAuthorId = fdAuthorId;
	}

	/**
	 * 作者描述
	 */
	private String fdAuthorDescription;
	
	/**
	 * 总节数
	 */
	private Integer fdTotalPart;
	
	/**
	 * 学习顺序(true:顺序；false:无序)
	 */
	private Boolean isOrder;
	
	/**
	 * 是否有效
	 */
	private Boolean isAvailable;
	
	/**
	 * 状态：00草稿，01发布
	 */
	private String fdStatus;
	
	public String getFdTitle() {
		return fdTitle;
	}
	
	public void setFdTitle(String fdTitle) {
		this.fdTitle = fdTitle;
	}

	public String getFdSubTitle() {
		return fdSubTitle;
	}

	public void setFdSubTitle(String fdSubTitle) {
		this.fdSubTitle = fdSubTitle;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdCategoryId")
	public CourseCategory getFdCategory() {
		return fdCategory;
	}

	public void setFdCategory(CourseCategory fdCategory) {
		this.fdCategory = fdCategory;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getFdSummary() {
		return fdSummary;
	}

	public void setFdSummary(String fdSummary) {
		this.fdSummary = fdSummary;
	}

	@Column(length = 2000)
	public String getFdLearnAim() {
		return fdLearnAim;
	}

	public void setFdLearnAim(String fdLearnAim) {
		this.fdLearnAim = fdLearnAim;
	}

	public String getFdProposalsGroup() {
		return fdProposalsGroup;
	}

	public void setFdProposalsGroup(String fdProposalsGroup) {
		this.fdProposalsGroup = fdProposalsGroup;
	}

	@Column(length = 1000)
	public String getFdDemand() {
		return fdDemand;
	}

	public void setFdDemand(String fdDemand) {
		this.fdDemand = fdDemand;
	}

	@Transient
	public List<AttMain> getAttMains() {
		return attMains;
	}

	public void setAttMains(List<AttMain> attMains) {
		this.attMains = attMains;
	}

	public String getFdSkin() {
		return fdSkin;
	}

	

	public void setFdSkin(String fdSkin) {
		this.fdSkin = fdSkin;
	}
	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}

	public String getFdPassword() {
		return fdPassword;
	}

	public void setFdPassword(String fdPassword) {
		this.fdPassword = fdPassword;
	}
	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsCompulsoryCourse() {
		return isCompulsoryCourse;
	}

	public void setIsCompulsoryCourse(Boolean isCompulsoryCourse) {
		this.isCompulsoryCourse = isCompulsoryCourse;
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

	public String getFdAuthor() {
		return fdAuthor;
	}

	public void setFdAuthor(String fdAuthor) {
		this.fdAuthor = fdAuthor;
	}

	@Column(length = 2000)
	public String getFdAuthorDescription() {
		return fdAuthorDescription;
	}

	public void setFdAuthorDescription(String fdAuthorDescription) {
		this.fdAuthorDescription = fdAuthorDescription;
	}

	public Integer getFdTotalPart() {
		return fdTotalPart;
	}

	public void setFdTotalPart(Integer fdTotalPart) {
		this.fdTotalPart = fdTotalPart;
	}
	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(Boolean isOrder) {
		this.isOrder = isOrder;
	}
	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	public Double getFdPrice() {
		return fdPrice;
	}

	public void setFdPrice(Double fdPrice) {
		this.fdPrice = fdPrice;
	}
	
}
