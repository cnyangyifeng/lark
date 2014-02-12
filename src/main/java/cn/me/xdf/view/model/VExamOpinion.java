package cn.me.xdf.view.model;

public class VExamOpinion {
	/**
	 * 选项
	 */
	private String opinion;
	
	/**
	 * 排序号
	 */
	private Integer fdOrder;
	
	/**
	 * 是否是答案（只针对选择）
	 */
	private String isAnswer;

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	public String getIsAnswer() {
		return isAnswer;
	}

	public void setIsAnswer(String isAnswer) {
		this.isAnswer = isAnswer;
	}
	
	
}
