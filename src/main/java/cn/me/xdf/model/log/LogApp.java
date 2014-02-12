package cn.me.xdf.model.log;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.IdEntity;

/**
 * 
 * 操作日志
 * 
 * @author zhaoq
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_LOGAPP")
public class LogApp extends BaseLog{

	/**
	 * 操作人
	 */
	private String personId;
	
	/**
	 * 操作时间
	 */
	private Date time;
	
	/**
	 * 操作类型（01:修改,02:删除,03:插入）
	 */
	private String method;
	
	/**
	 * 操作内容
	 */
	private String content;
	
	/**
	 * 对应modelId
	 */
	private String modelId;
	
	/**
	 * 对应modelId
	 */
	private String modelName;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}


	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	@Lob
    @Basic(fetch = FetchType.LAZY)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	

	
	
}
