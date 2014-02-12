package cn.me.xdf.model.material;

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
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.base.IAttMain;
import cn.me.xdf.model.base.IdEntity;
import cn.me.xdf.model.organization.SysOrgPerson;


/**
 * 
 * 素材实体的定义
 * 
 * @author zuoyi
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_MATERIAL")
@Inheritance(strategy = InheritanceType.JOINED)
public class MaterialInfo  extends IdEntity implements IAttMain{
	
	/**
	 * 素材名称
	 */
	private String fdName;
	
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
	 * 10作业包
	 * 11日程
	 */
	private String fdType;

	/**
	 * 素材子类别(用来做预留扩展)
	 */
	private String fdSubCategoryId;
	
	/**
	 * 素材别名
	 */
	private String fdAlias;
	
	/**
	 * 素材链接
	 */
	private String fdLink;
	
	/**
	 * 素材描述
	 */
	private String fdDescription;
	
	/**
	 * 辅助材料
	 */
	private List<AttMain> attMains;
	
	/**
	 * 作者
	 */
	private String fdAuthor;
	
	/**
	 * 作者描述                          R
	 */
	private String fdAuthorDescription;
	
	/**
	 * 是否公开
	 */
	private Boolean isPublish;
	
	/**
	 * 是否可下载
	 */
	private Boolean isDownload;
	
	/**
	 * 下载次数
	 */
	private Integer fdDownloads;
	
	/**
	 * 分数（如果是测试则为通关分数，如果是作业则为标准分数）
	 */
	private Double fdScore;
	
	/**
	 * 标准学习时长（为分钟形式）
	 */
	private Integer fdStudyTime;
	
	/**
	 * 播放次数
	 */
	private Integer fdPlays;
	
	/**
	 * 攒次数
	 */
	private Integer fdLauds;
	
	/**
	 * 完成条件类型：
	 * 01系统自动
	 * 02学员手工结束
	 * 03导师手工结束	 
	 */
	private String fdFinishType;

	/**
	 * 创建时间
	 */
	private Date fdCreateTime;
	
	/**
	 * 创建者
	 */
	private SysOrgPerson creator;
	
	/**
	 * 是否有效
	 */
	private Boolean isAvailable;
	
	/**
	 * 试题
	 */
	private List<ExamQuestion> questions;
	
	/**
	 * 作业
	 */
	private List<Task> tasks;
	
	/**
	 * 富文本内容
	 */
	private String richContent;
	
	/**
	 * 权限
	 */
	private List<MaterialAuth> authList;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getRichContent() {
		return richContent;
	}

	public void setRichContent(String richContent) {
		this.richContent = richContent;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "material")
	public List<MaterialAuth> getAuthList() {
		return authList;
	}

	public void setAuthList(List<MaterialAuth> authList) {
		this.authList = authList;
	}

    @JsonIgnore
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "exam")
	public List<ExamQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<ExamQuestion> questions) {
		this.questions = questions;
	}
	
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "taskPackage")
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getFdName() {
		return fdName;
	}
	
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getFdSubCategoryId() {
		return fdSubCategoryId;
	}

	public void setFdSubCategoryId(String fdSubCategoryId) {
		this.fdSubCategoryId = fdSubCategoryId;
	}

	public String getFdAlias() {
		return fdAlias;
	}

	public void setFdAlias(String fdAlias) {
		this.fdAlias = fdAlias;
	}

	public String getFdLink() {
		return fdLink;
	}

	public void setFdLink(String fdLink) {
		this.fdLink = fdLink;
	}

	@Column(length = 2000)
	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

    @JsonIgnore
	@Transient
	public List<AttMain> getAttMains() {
		return attMains;
	}

	public void setAttMains(List<AttMain> attMains) {
		this.attMains = attMains;
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

	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}

	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsDownload() {
		return isDownload;
	}

	public void setIsDownload(Boolean isDownload) {
		this.isDownload = isDownload;
	}

	public Integer getFdDownloads() {
		return fdDownloads;
	}

	public void setFdDownloads(Integer fdDownloads) {
		this.fdDownloads = fdDownloads;
	}

	public Double getFdScore() {
		return fdScore;
	}

	public void setFdScore(Double fdScore) {
		this.fdScore = fdScore;
	}

	public Integer getFdStudyTime() {
		return fdStudyTime;
	}

	public void setFdStudyTime(Integer fdStudyTime) {
		this.fdStudyTime = fdStudyTime;
	}

	public String getFdFinishType() {
		return fdFinishType;
	}

	public void setFdFinishType(String fdFinishType) {
		this.fdFinishType = fdFinishType;
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

	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Integer getFdPlays() {
		return fdPlays;
	}

	public void setFdPlays(Integer fdPlays) {
		this.fdPlays = fdPlays;
	}

	public Integer getFdLauds() {
		return fdLauds;
	}

	public void setFdLauds(Integer fdLauds) {
		this.fdLauds = fdLauds;
	}

    /**
     * *******************************************************************************************************************
     * /* NOT DB PROPERTY
     * /*
     * /********************************************************************************************************************
     */

    public boolean through;

    /**
     * 是否通过
     *
     * @return
     */
    @Transient
    @org.hibernate.annotations.Type(type="yes_no")
    public boolean getThrough() {
        return through;
    }

    public void setThrough(boolean through) {
        this.through = through;
    }
	
}
