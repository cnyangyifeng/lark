package cn.me.xdf.model.letter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.IdEntity;
import cn.me.xdf.model.organization.SysOrgPerson;
/**
 * 私信的人员关系
 * @author yuhuizhe
 *
 */
@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_LETTER_RELATION")
public class RelationLetter extends IdEntity {
	/**
	 * 发送人
	 */
	private SysOrgPerson sendUser;
    /**
     * 接受人	
     */
	private SysOrgPerson acceptUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sendUserId")
	public SysOrgPerson getSendUser() {
		return sendUser;
	}
	public void setSendUser(SysOrgPerson sendUser) {
		this.sendUser = sendUser;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "acceptUserId")
	public SysOrgPerson getAcceptUser() {
		return acceptUser;
	}
	public void setAcceptUser(SysOrgPerson acceptUser) {
		this.acceptUser = acceptUser;
	}
	
}


