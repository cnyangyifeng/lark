package cn.me.xdf.model.material;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.IAttMain;
import cn.me.xdf.model.base.IdEntity;

/**
 * 
 * 作业实体的定义
 * 
 * @author zuoyi
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_TASK")
public class Task extends IdEntity implements IAttMain{

	/**
	 * 所属作业包
	 */
	private MaterialInfo taskPackage;
	/**
	 * 作业名称
	 */
	private String fdName;
	/**
	 * 作业作答方式：01上传作业，02在线作答
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

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdMaterialId")
	public MaterialInfo getTaskPackage() {
		return taskPackage;
	}

	public void setTaskPackage(MaterialInfo taskPackage) {
		this.taskPackage = taskPackage;
	}
	
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

	@Lob
	@Basic(fetch = FetchType.LAZY)
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
	
}
