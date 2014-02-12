package cn.me.xdf.view.model;


public class VTaskPaperAuth {
	
	/**
	 * 用户
	 */
	private String fdName;
	
	/**
	 * 是否可使用
	 */
	private String isReader;
	
	/**
	 * 部门
	 */
	private String dept;
	
	/**
	 * 是否可编辑
	 */
	private String isEditer;

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getIsReader() {
		return isReader;
	}

	public void setIsReader(String isReader) {
		this.isReader = isReader;
	}

	public String getIsEditer() {
		return isEditer;
	}

	public void setIsEditer(String isEditer) {
		this.isEditer = isEditer;
	}
	
	

}
