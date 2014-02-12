package cn.me.xdf.view.model;

import java.util.List;

public class VExamQuestion {
	/**
	 * 试题类型
	 */
	private String fdType;
	
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
	private Integer fdStandardScore;
	
	/**
	 * 选项
	 */
	private List<VExamOpinion> opinions;
	
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

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getFdSubject() {
		return fdSubject;
	}

	public void setFdSubject(String fdSubject) {
		this.fdSubject = fdSubject;
	}

	public String getFdQuestion() {
		return fdQuestion;
	}

	public void setFdQuestion(String fdQuestion) {
		this.fdQuestion = fdQuestion;
	}

	public Integer getFdStandardScore() {
		return fdStandardScore;
	}

	public void setFdStandardScore(Integer fdStandardScore) {
		this.fdStandardScore = fdStandardScore;
	}

	public List<VExamOpinion> getOpinions() {
		return opinions;
	}

	public void setOpinions(List<VExamOpinion> opinions) {
		this.opinions = opinions;
	}
	
	
}
