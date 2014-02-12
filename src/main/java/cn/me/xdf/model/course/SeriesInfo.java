package cn.me.xdf.model.course;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.annotaion.AttMainMachine;
import cn.me.xdf.annotaion.AttValues;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.base.IAttMain;
import cn.me.xdf.model.base.IdEntity;
import cn.me.xdf.model.organization.SysOrgPerson;

/**
 * 
 * 系列实体的定义
 * 
 * @author zuoyi
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_SERIES")
@AttMainMachine(modelName="cn.me.xdf.model.course.SeriesInfo", value = { @AttValues(fild = "attMain") })
public class SeriesInfo extends IdEntity implements IAttMain{

	/**
	 * 上级系列
	 */
	private SeriesInfo hbmParent;
	
	/**
	 * 系列名称
	 */
	private String fdName;
	
	/**
	 * 系列描述
	 */
	private String fdDescription;
	
	/**
	 * 是否有效
	 */
	private Boolean isAvailable;
	/**
	 * 创建时间
	 */
	private Date fdCreateTime;
	
	/**
	 * 创建者
	 */
	private SysOrgPerson creator;
	/**
	 * 对应封面存储的附件
	 */
	private AttMain attMain;
	/**
	 * 
	 * 是否发布
	 */
	private	 Boolean isPublish;
	/**
	 * 排序号
	 */
	private  Integer fdSeriesNo;
	/**
	 * 
	 * 作者
	 */
	private String fdAuthor;
	
	public String getFdAuthorId() {
		return fdAuthorId;
	}

	public void setFdAuthorId(String fdAuthorId) {
		this.fdAuthorId = fdAuthorId;
	}

	/**
	 * 
	 * 作者Id
	 */
	private String fdAuthorId;
	
	/**
	 * 
	 * 
	 */
	private String fdAuthorDescription;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdParentId")
	public SeriesInfo getHbmParent() {
		return hbmParent;
	}

	public void setHbmParent(SeriesInfo hbmParent) {
		this.hbmParent = hbmParent;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	@Column(length = 2000)
	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	@Transient
	public AttMain getAttMain() {
		return attMain;
	}

	public void setAttMain(AttMain attMain) {
		this.attMain = attMain;
	}

	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdCreatorId")
	public SysOrgPerson getCreator() {
		return creator;
	}

	public void setCreator(SysOrgPerson creator) {
		this.creator = creator;
	}

	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}

	public Integer getFdSeriesNo() {
		return fdSeriesNo;
	}

	public void setFdSeriesNo(Integer fdSeriesNo) {
		this.fdSeriesNo = fdSeriesNo;
	}

	public String getFdAuthor() {
		return fdAuthor;
	}

	public void setFdAuthor(String fdAuthor) {
		this.fdAuthor = fdAuthor;
	}
	@Column(length = 2000)
	public String getFdAuthorDescription() {
		return fdAuthorDescription;
	}

	public void setFdAuthorDescription(String fdAuthorDescription) {
		this.fdAuthorDescription = fdAuthorDescription;
	}

	
	
}
