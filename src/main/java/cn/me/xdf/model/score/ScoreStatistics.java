package cn.me.xdf.model.score;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.IdEntity;

/**
 * 
 * 评分统计实体的定义
 * 
 * @author zuoyi
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_SCORE_STATISTICS")
public class ScoreStatistics extends IdEntity{
	
	/**
	 * 业务名称
	 */
	private String fdModelName;
	
	/**
	 * 业务Id
	 */
	private String fdModelId;
	
	/**
	 * 平均分数
	 */
	private Double fdAverage;
	
	/**
	 * 评分次数
	 */
	private Integer fdScoreNum;
	
	/**
	 * 评5分人次
	 */
	private Integer fdFiveScoreNum;
	
	/**
	 * 评4分人次
	 */
	private Integer fdFourScoreNum;
	
	/**
	 * 评3分人次
	 */
	private Integer fdThreeScoreNum;
	
	/**
	 * 评2分人次
	 */
	private Integer fdTwoScoreNum;
	
	/**
	 * 评1分人次
	 */
	private Integer fdOneScoreNum;

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	public Double getFdAverage() {
		return fdAverage;
	}

	public void setFdAverage(Double fdAverage) {
		this.fdAverage = fdAverage;
	}

	public Integer getFdScoreNum() {
		return fdScoreNum;
	}

	public void setFdScoreNum(Integer fdScoreNum) {
		this.fdScoreNum = fdScoreNum;
	}

	public Integer getFdFiveScoreNum() {
		return fdFiveScoreNum;
	}

	public void setFdFiveScoreNum(Integer fdFiveScoreNum) {
		this.fdFiveScoreNum = fdFiveScoreNum;
	}

	public Integer getFdFourScoreNum() {
		return fdFourScoreNum;
	}

	public void setFdFourScoreNum(Integer fdFourScoreNum) {
		this.fdFourScoreNum = fdFourScoreNum;
	}

	public Integer getFdThreeScoreNum() {
		return fdThreeScoreNum;
	}

	public void setFdThreeScoreNum(Integer fdThreeScoreNum) {
		this.fdThreeScoreNum = fdThreeScoreNum;
	}

	public Integer getFdTwoScoreNum() {
		return fdTwoScoreNum;
	}

	public void setFdTwoScoreNum(Integer fdTwoScoreNum) {
		this.fdTwoScoreNum = fdTwoScoreNum;
	}

	public Integer getFdOneScoreNum() {
		return fdOneScoreNum;
	}

	public void setFdOneScoreNum(Integer fdOneScoreNum) {
		this.fdOneScoreNum = fdOneScoreNum;
	}
	
}
