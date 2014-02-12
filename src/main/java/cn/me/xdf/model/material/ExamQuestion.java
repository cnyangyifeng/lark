package cn.me.xdf.model.material;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.IdEntity;
/**
 * 
 * 试题实体的定义
 * 
 * @author zuoyi
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_EXAM_QUESTION")
public class ExamQuestion extends IdEntity{

	/**
	 * 所属试卷
	 */
	private MaterialInfo exam;
	
	/**
	 * 试题类型：1单选，2多选，3填空
	 */
	private Integer fdType;
	
	/**
	 * 题干描述
	 */
	private String fdSubject;
	
	/**
	 * 答案
	 */
	private String fdQuestion;
	
	/**
	 * 标准分
	 */
	private Double fdStandardScore;
	
	/**
	 * 排序号
	 */
	private Integer fdOrder;

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}
	
	/**
	 * 选项
	 */
	private List<ExamOpinion> opinions;
	
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "question")
	public List<ExamOpinion> getOpinions() {
		return opinions;
	}

	public void setOpinions(List<ExamOpinion> opinions) {
		this.opinions = opinions;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdMaterialId")
	public MaterialInfo getExam() {
		return exam;
	}

	public void setExam(MaterialInfo exam) {
		this.exam = exam;
	}

	public Integer getFdType() {
		return fdType;
	}

	public void setFdType(Integer fdType) {
		this.fdType = fdType;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getFdSubject() {
		return fdSubject;
	}

	public void setFdSubject(String fdSubject) {
		this.fdSubject = fdSubject;
	}

	@Column(length = 2000)
	public String getFdQuestion() {
		return fdQuestion;
	}

	public void setFdQuestion(String fdQuestion) {
		this.fdQuestion = fdQuestion;
	}

	public Double getFdStandardScore() {
		return fdStandardScore;
	}

	public void setFdStandardScore(Double fdStandardScore) {
		this.fdStandardScore = fdStandardScore;
	}
}
