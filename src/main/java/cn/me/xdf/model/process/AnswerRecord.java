package cn.me.xdf.model.process;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.IdEntity;


/**
 * 
 * 用户答题记录实体的定义
 * 
 * @author zuoyi
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_ANSWER_RECORD")
public class AnswerRecord extends IdEntity{   
	
	/**
	 * 所属试题Id
	 */
	private String fdQuestionId;

	/**
	 * 答案
	 */
	private String fdAnswer;
	
	public String getFdQuestionId() {
		return fdQuestionId;
	}

	public void setFdQuestionId(String fdQuestionId) {
		this.fdQuestionId = fdQuestionId;
	}

	@Column(length = 2000)
	public String getFdAnswer() {
		return fdAnswer;
	}

	public void setFdAnswer(String fdAnswer) {
		this.fdAnswer = fdAnswer;
	}

}
