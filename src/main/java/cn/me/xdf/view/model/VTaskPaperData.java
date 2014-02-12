package cn.me.xdf.view.model;

import java.util.List;

public class VTaskPaperData {
	/**
	 * 作业包名称
	 */
	private String taskPaperName;
	
	/**
	 * 作业包描述
	 */
	private String taskPaperDescription;
	/**
	 * 作者
	 */
	private String fdAuthor;
	
	/**
	 * 作者描述 
	 */
	private String fdAuthorDescription;
	/**
	 * 标准学习时长（为分钟形式）
	 */
	private Integer fdStudyTime;
	/**
	 * 总共多少题
	 */
    private Integer totalTask;
    /**
     * 总共多少分
     */
    private Integer totalScore;
    /**
     * 及格分
     */
    private Integer passScore;
    /**
	 * 作业
	 */
	private List<VTask> tasks;
	/**
	 * 权限类型（加密或者公开）
	 */
	private String authCategory;
	/**
	 * 创建者名字
	 */
	private String creatorName;
	/**
	 * 权限人员
	 */
	private List<VTaskPaperAuth> taskPaperAuth;
	/**
	 * 创建时间
	 */
	private String fdCreateTime;
	
	public String getFdCreateTime() {
		return fdCreateTime;
	}
	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}
	public String getTaskPaperName() {
		return taskPaperName;
	}
	public void setTaskPaperName(String taskPaperName) {
		this.taskPaperName = taskPaperName;
	}
	public String getTaskPaperDescription() {
		return taskPaperDescription;
	}
	public void setTaskPaperDescription(String taskPaperDescription) {
		this.taskPaperDescription = taskPaperDescription;
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
	public Integer getFdStudyTime() {
		return fdStudyTime;
	}
	public void setFdStudyTime(Integer fdStudyTime) {
		this.fdStudyTime = fdStudyTime;
	}
	public Integer getTotalTask() {
		return totalTask;
	}
	public void setTotalTask(Integer totalTask) {
		this.totalTask = totalTask;
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
	public List<VTask> getTasks() {
		return tasks;
	}
	public void setTasks(List<VTask> tasks) {
		this.tasks = tasks;
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
	public List<VTaskPaperAuth> getTaskPaperAuth() {
		return taskPaperAuth;
	}
	public void setTaskPaperAuth(List<VTaskPaperAuth> taskPaperAuth) {
		this.taskPaperAuth = taskPaperAuth;
	}
	
	
}
