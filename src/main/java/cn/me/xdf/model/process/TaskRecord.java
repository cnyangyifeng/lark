package cn.me.xdf.model.process;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.base.IAttMain;
import cn.me.xdf.model.base.IdEntity;

/**
 * 
 * 作业记录
 * 
 * @author zuoyi
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_TASK_RECORD")
public class TaskRecord extends IdEntity implements IAttMain{
	
	/**
	 * 所属作业题Id
	 */
	private String fdTaskId;
	
	/**
	 * 分数
	 */
	private Double fdScore;
	
	/**
	 * 评分人Id
	 */
	private String fdAppraiserId;
	
	/**
	 * 评语
	 */
	private String fdComment;
	
	/**
	 * 通过状态
	 */
	private Boolean through;
	
	/**
	 * 作业交互状态（00：未答；01：答完；02：驳回；03：未通过；04：通过;05:已批改）
	 */
	private String fdStatus;
	
	/**
	 * 在线作答类型作业的答案
	 */
	private String fdAnswer;
	
	/**
	 * 操作时间(此处为导师操作时间)
	 */
	private Date fdCreateTime;
	

	/**
	 * 上传的作业
	 */
	private List<AttMain> attMains;
	
	@Transient
	public List<AttMain> getAttMains() {
		return attMains;
	}

	public void setAttMains(List<AttMain> attMains) {
		this.attMains = attMains;
	}
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getFdAnswer() {
		return fdAnswer;
	}

	public void setFdAnswer(String fdAnswer) {
		this.fdAnswer = fdAnswer;
	}

	public String getFdTaskId() {
		return fdTaskId;
	}

	public void setFdTaskId(String fdTaskId) {
		this.fdTaskId = fdTaskId;
	}

	public Double getFdScore() {
		return fdScore;
	}

	public void setFdScore(Double fdScore) {
		this.fdScore = fdScore;
	}

	public String getFdAppraiserId() {
		return fdAppraiserId;
	}

	public void setFdAppraiserId(String fdAppraiserId) {
		this.fdAppraiserId = fdAppraiserId;
	}

	public String getFdComment() {
		return fdComment;
	}

	public void setFdComment(String fdComment) {
		this.fdComment = fdComment;
	}

	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getThrough() {
		return through;
	}

	public void setThrough(Boolean through) {
		this.through = through;
	}
	
	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}
	
	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}
}
