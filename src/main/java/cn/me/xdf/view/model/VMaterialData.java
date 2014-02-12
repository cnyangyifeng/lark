package cn.me.xdf.view.model;


public class VMaterialData {
	
	/**
	 * 素材名称
	 */
	private String fdName;
	
	/**
	 * 素材类型
	 */
	private String fdType;
	
	/**
	 * 评分
	 */
	private Double score;
	
	/**
	 * 播放次数
	 */
	private Integer fdPlays;
	
	/**
	 * 攒次数
	 */
	private Integer fdLauds;
	
	/**
	 * 下载次数
	 */
	private Integer fdDownloads;
	
	/**
	 * 创建时间
	 */
	private String fdCreateTime;

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

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getFdPlays() {
		return fdPlays;
	}

	public void setFdPlays(Integer fdPlays) {
		this.fdPlays = fdPlays;
	}

	public Integer getFdLauds() {
		return fdLauds;
	}

	public void setFdLauds(Integer fdLauds) {
		this.fdLauds = fdLauds;
	}

	public Integer getFdDownloads() {
		return fdDownloads;
	}

	public void setFdDownloads(Integer fdDownloads) {
		this.fdDownloads = fdDownloads;
	}

	public String getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}
	
	
}
