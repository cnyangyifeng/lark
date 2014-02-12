package cn.me.xdf.model.process;

import java.util.Date;
import java.util.Set;

import cn.me.xdf.model.base.IdEntity;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-28
 * Time: 下午4:00
 * To change this template use File | Settings | File Templates.
 * 素材记录
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "IXDF_NTP_SOURCE_NOTE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SourceNote extends IdEntity{
	/**
     * 所属课程Id
     */
    private String fdCourseId;
	
	/**
     * 所属节Id
     */
    private String fdCatalogId;
    
    /**
	 * 所属素材Id
	 */
	private String fdMaterialId;
	
	/**
	 * 用户Id
	 */
	private String fdUserId;

	/**
	 * 学习素材状态
	 */
	private Boolean isStudy;
	
	/**
	 * 操作时间
	 */
	private Date fdOperationDate;
	
	/**
	 * 分数
	 */
	private Double fdScore;
	
	/**
	 * 考试时长
	 */
	private Integer fdExamTime;
	
	/**
	 * 评分人Id
	 */
	private String fdAppraiserId;
	
	/**
	 * 评语
	 */
	private String fdComment;
	
	/**
	 * 作业交互状态（00：未答；01：答完；02：驳回；03：未通过；04：通过）
	 */
	private String fdStatus;
	
	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	/**
	 * 答题结果
	 */
	private Set<AnswerRecord> answerRecords;
	
	/**
	 * 作业结果
	 */
	private Set<TaskRecord> taskRecords;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "fdSourceNodeId")
	public Set<AnswerRecord> getAnswerRecords() {
		return answerRecords;
	}

	public void setAnswerRecords(Set<AnswerRecord> answerRecords) {
		this.answerRecords = answerRecords;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "fdSourceNodeId")
	public Set<TaskRecord> getTaskRecords() {
		return taskRecords;
	}

	public void setTaskRecords(Set<TaskRecord> taskRecords) {
		this.taskRecords = taskRecords;
	}

	public String getFdCourseId() {
		return fdCourseId;
	}

	public void setFdCourseId(String fdCourseId) {
		this.fdCourseId = fdCourseId;
	}

	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsStudy() {
		return isStudy;
	}

	public void setIsStudy(Boolean isStudy) {
		this.isStudy = isStudy;
	}

	public Date getFdOperationDate() {
		return fdOperationDate;
	}

	public void setFdOperationDate(Date fdOperationDate) {
		this.fdOperationDate = fdOperationDate;
	}

	public Double getFdScore() {
		return fdScore;
	}

	public void setFdScore(Double fdScore) {
		this.fdScore = fdScore;
	}

	public Integer getFdExamTime() {
		return fdExamTime;
	}

	public void setFdExamTime(Integer fdExamTime) {
		this.fdExamTime = fdExamTime;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getFdComment() {
		return fdComment;
	}

	public void setFdComment(String fdComment) {
		this.fdComment = fdComment;
	}

	public String getFdCatalogId() {
		return fdCatalogId;
	}

	public void setFdCatalogId(String fdCatalogId) {
		this.fdCatalogId = fdCatalogId;
	}

	public String getFdMaterialId() {
		return fdMaterialId;
	}

	public void setFdMaterialId(String fdMaterialId) {
		this.fdMaterialId = fdMaterialId;
	}

	public String getFdUserId() {
		return fdUserId;
	}

	public void setFdUserId(String fdUserId) {
		this.fdUserId = fdUserId;
	}

	public String getFdAppraiserId() {
		return fdAppraiserId;
	}

	public void setFdAppraiserId(String fdAppraiserId) {
		this.fdAppraiserId = fdAppraiserId;
	}

}
