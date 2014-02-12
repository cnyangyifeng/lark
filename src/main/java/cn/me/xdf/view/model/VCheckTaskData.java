package cn.me.xdf.view.model;

public class VCheckTaskData {

	/**
	 * 用户姓名
	 */
	private String userName;
	
	/**
	 * 用户部门
	 */
	private String userDept;
	
	/**
	 * 用户电话
	 */
	private String userTel;
	
	/**
	 * 用户email
	 */
	private String userEmail;
	
	/**
	 * 导师姓名
	 */
	private String guideName;
	
	/**
	 * 课程名
	 */
	private String courseName;
	
	/**
	 * 当前环节
	 */
	private String currentCatalog;
	
	/**
	 * 作业包总分
	 */
	private Double totalScore;
	
	/**
	 * 及格分
	 */
	private Double passScore;
	
	/**
	 * 老师批分
	 */
	private Double guideScore;
	
	/**
	 * 是否通过
	 */
	private String isPass;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getGuideName() {
		return guideName;
	}

	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCurrentCatalog() {
		return currentCatalog;
	}

	public void setCurrentCatalog(String currentCatalog) {
		this.currentCatalog = currentCatalog;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	public Double getPassScore() {
		return passScore;
	}

	public void setPassScore(Double passScore) {
		this.passScore = passScore;
	}

	public Double getGuideScore() {
		return guideScore;
	}

	public void setGuideScore(Double guideScore) {
		this.guideScore = guideScore;
	}

	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	
}
