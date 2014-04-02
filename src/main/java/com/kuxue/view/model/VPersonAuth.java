package com.kuxue.view.model;

public class VPersonAuth {
	/**
	 * 人员名称
	 */
	private String fdName;

	/**
	 * 人员部门
	 */
	private String fdDept;
	
	private String teacherEmail;
	
	private String adviserEmail;
	/**
	 * 指导老师
	 */
	private String adviserName;
	/**
	 * 部门
	 */
	private String adviserDept;
	
	/**
	 * 授权时间
	 */
    private String fdCreateTime;
    
    
	public String getTeacherEmail() {
		return teacherEmail;
	}
	public void setTeacherEmail(String teacherEmail) {
		this.teacherEmail = teacherEmail;
	}
	public String getAdviserEmail() {
		return adviserEmail;
	}
	public void setAdviserEmail(String adviserEmail) {
		this.adviserEmail = adviserEmail;
	}
	public String getAdviserName() {
		return adviserName;
	}
	public void setAdviserName(String adviserName) {
		this.adviserName = adviserName;
	}
	public String getAdviserDept() {
		return adviserDept;
	}
	public void setAdviserDept(String adviserDept) {
		this.adviserDept = adviserDept;
	}
	public String getFdCreateTime() {
		return fdCreateTime;
	}
	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdDept() {
		return fdDept;
	}

	public void setFdDept(String fdDept) {
		this.fdDept = fdDept;
	}
	
	
}
