package cn.me.xdf.model.letter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.me.xdf.model.base.IdEntity;

@SuppressWarnings("serial")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_NTP_LETTER_CONNECT")
public class ConnectLetter extends IdEntity {
	
	private PrivateLetter privateLetter;
	
	private RelationLetter relationLetter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "privateLetterId")
	public PrivateLetter getPrivateLetter() {
		return privateLetter;
	}

	public void setPrivateLetter(PrivateLetter privateLetter) {
		this.privateLetter = privateLetter;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "relationLetterId")
	public RelationLetter getRelationLetter() {
		return relationLetter;
	}

	public void setRelationLetter(RelationLetter relationLetter) {
		this.relationLetter = relationLetter;
	}
	
}
