package cn.me.xdf.model.organization;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.IdEntity;



/**
 * 用户对应的项目
 * 
 * @author xiaobin268
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SYS_USER_DICTION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserDiction extends IdEntity{

	private SysOrgPerson sysOrgPerson;

	/**
	 * 对应项目的ID
	 */
	private String dictionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", nullable = true)
	public SysOrgPerson getSysOrgPerson() {
		return sysOrgPerson;
	}

	public void setSysOrgPerson(SysOrgPerson sysOrgPerson) {
		this.sysOrgPerson = sysOrgPerson;
	}

	/**
	 * 对应项目的ID
	 * 
	 * @return
	 */
	public String getDictionId() {
		return dictionId;
	}

	/**
	 * 对应项目的ID
	 * 
	 * @param dictionId
	 */
	public void setDictionId(String dictionId) {
		this.dictionId = dictionId;
	}

}
