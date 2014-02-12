package cn.me.xdf.model.material;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * 日程实体的定义
 * 
 * @author zuoyi
 * 
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_SCHEDULE")
public class Schedule extends MaterialInfo{

	/**
	 * 起始时间
	 */
	private Date fdStratTime;
	
	/**
	 * 结束时间
	 */
	private Date fdEndTime;
	
	/**
	 * 地址
	 */
	private String fdAddress;

	public Date getFdStratTime() {
		return fdStratTime;
	}

	public void setFdStratTime(Date fdStratTime) {
		this.fdStratTime = fdStratTime;
	}

	public Date getFdEndTime() {
		return fdEndTime;
	}

	public void setFdEndTime(Date fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	@Column(length = 1000)
	public String getFdAddress() {
		return fdAddress;
	}

	public void setFdAddress(String fdAddress) {
		this.fdAddress = fdAddress;
	}
}
