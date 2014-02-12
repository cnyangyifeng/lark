package cn.me.xdf.view.model;

public class VTask {
	/**
	 * 作业名称
	 */
	private String fdName;
	/**
	 * 作业作答方式:上传作业，在线作答
	 */
	private String fdType;
	
	/**
	 * 作业简介
	 */
	private String fdSubject;
	
	/**
	 * 标准分
	 */
	private Double fdStandardScore;
	
	/**
	 * 排序号
	 */
	private Integer fdOrder;

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

	public String getFdSubject() {
		return fdSubject;
	}

	public void setFdSubject(String fdSubject) {
		this.fdSubject = fdSubject;
	}

	public Double getFdStandardScore() {
		return fdStandardScore;
	}

	public void setFdStandardScore(Double fdStandardScore) {
		this.fdStandardScore = fdStandardScore;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}
	
	
}
