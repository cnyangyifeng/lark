package cn.me.xdf.model.course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.IdEntity;
import cn.me.xdf.model.material.MaterialInfo;

/**
 * 
 * 课程素材关系实体的定义
 * 
 * @author zuoyi
 * 
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_COURSE_CONTENT")
public class CourseContent extends IdEntity{
	
	/**
	 * 所属章节
	 */
	private CourseCatalog catalog;
	
	/**
	 * 所属素材
	 */
	private MaterialInfo material;
	
	/**
	 * 素材序号
	 */
	private Integer fdMaterialNo;
	
	/**
	 * 是否可下载
	 */
	private Boolean isDownload;
	
	/**
	 * 标准分
	 */
	private Double fdScore;
	
	/**
	 * 备注
	 */
	private String fdRemark;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdCatalogId")
	public CourseCatalog getCatalog() {
		return catalog;
	}

	public void setCatalog(CourseCatalog catalog) {
		this.catalog = catalog;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fdMaterialId")
	public MaterialInfo getMaterial() {
		return material;
	}

	public void setMaterial(MaterialInfo material) {
		this.material = material;
	}

	public Integer getFdMaterialNo() {
		return fdMaterialNo;
	}

	public void setFdMaterialNo(Integer fdMaterialNo) {
		this.fdMaterialNo = fdMaterialNo;
	}

	@org.hibernate.annotations.Type(type="yes_no")
	public Boolean getIsDownload() {
		return isDownload;
	}

	public void setIsDownload(Boolean isDownload) {
		this.isDownload = isDownload;
	}

	public Double getFdScore() {
		return fdScore;
	}

	public void setFdScore(Double fdScore) {
		this.fdScore = fdScore;
	}

	@Column(length = 2000)
	public String getFdRemark() {
		return fdRemark;
	}

	public void setFdRemark(String fdRemark) {
		this.fdRemark = fdRemark;
	}
	
}
