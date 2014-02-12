package cn.me.xdf.view.model;

import java.util.List;

public class VCourseAuthList {
	/**
	 * 课程标题
	 */
	private String fdTitle;
	/**
	 * 新教师
	 */
    private List<VPersonAuth> personAuth;
    
	public String getFdTitle() {
		return fdTitle;
	}
	public void setFdTitle(String fdTitle) {
		this.fdTitle = fdTitle;
	}
	public List<VPersonAuth> getPersonAuth() {
		return personAuth;
	}
	public void setPersonAuth(List<VPersonAuth> personAuth) {
		this.personAuth = personAuth;
	}
    
}
