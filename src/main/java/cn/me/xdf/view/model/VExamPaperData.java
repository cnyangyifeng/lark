package cn.me.xdf.view.model;

import java.util.List;

public class VExamPaperData {
	/**
	 * 测试名称
	 */
	private String fdName;
	
	/**
	 * 测试题描述
	 */
	private String fdDescription;
	/**
	 * 作者
	 */
	private String fdAuthor;
	
	/**
	 * 作者描述 
	 */
	private String fdAuthorDescription;
	/**
	 * 权限类型（加密或者公开）
	 */
	private String authCategory;
	/**
	 * 创建者名字
	 */
	private String creatorName;
	/**
	 * 创建时间
	 */
	private String fdCreateTime;
	/**
	 * 标准学习时长（为分钟形式）
	 */
	private Integer fdStudyTime;
	/**
	 * 总共多少题
	 */
    private Integer totalExam;
    /**
     * 总共多少分
     */
    private Integer totalScore;
    /**
     * 及格分
     */
    private Integer passScore;
    
    /**
	 * 权限人员
	 */
	private List<VTaskPaperAuth> taskPaperAuth;
	
	/**
	 * 试题
	 */
	private List<VExamQuestion> questions;
	

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	public String getFdAuthor() {
		return fdAuthor;
	}

	public void setFdAuthor(String fdAuthor) {
		this.fdAuthor = fdAuthor;
	}

	public String getFdAuthorDescription() {
		return fdAuthorDescription;
	}

	public void setFdAuthorDescription(String fdAuthorDescription) {
		this.fdAuthorDescription = fdAuthorDescription;
	}

	public String getAuthCategory() {
		return authCategory;
	}

	public void setAuthCategory(String authCategory) {
		this.authCategory = authCategory;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public Integer getFdStudyTime() {
		return fdStudyTime;
	}

	public void setFdStudyTime(Integer fdStudyTime) {
		this.fdStudyTime = fdStudyTime;
	}

	public Integer getTotalExam() {
		return totalExam;
	}

	public void setTotalExam(Integer totalExam) {
		this.totalExam = totalExam;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getPassScore() {
		return passScore;
	}

	public void setPassScore(Integer passScore) {
		this.passScore = passScore;
	}

	public List<VTaskPaperAuth> getTaskPaperAuth() {
		return taskPaperAuth;
	}

	public void setTaskPaperAuth(List<VTaskPaperAuth> taskPaperAuth) {
		this.taskPaperAuth = taskPaperAuth;
	}

	public List<VExamQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<VExamQuestion> questions) {
		this.questions = questions;
	}
	
	

}
