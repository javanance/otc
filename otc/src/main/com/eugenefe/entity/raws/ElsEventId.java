package com.eugenefe.entity.raws;

// Generated 2014. 4. 7 ¿ÀÈÄ 8:01:40 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;



@Embeddable
public class ElsEventId implements java.io.Serializable {
	private String pdno;
	private String elsEvntDvsnCd;
	private long elsEvntSeq;

	public ElsEventId() {
	}

	public ElsEventId(String pdno, String elsEvntDvsnCd, long elsEvntSeq) {
		this.pdno = pdno;
		this.elsEvntDvsnCd = elsEvntDvsnCd;
		this.elsEvntSeq = elsEvntSeq;
	}

	@Column(name = "PDNO", nullable = false, length = 12)
	public String getPdno() {
		return this.pdno;
	}

	public void setPdno(String pdno) {
		this.pdno = pdno;
	}

	@Column(name = "ELS_EVNT_DVSN_CD", nullable = false, length = 2)
	public String getElsEvntDvsnCd() {
		return this.elsEvntDvsnCd;
	}

	public void setElsEvntDvsnCd(String elsEvntDvsnCd) {
		this.elsEvntDvsnCd = elsEvntDvsnCd;
	}

	@Column(name = "ELS_EVNT_SEQ", nullable = false, precision = 16, scale = 0)
	public long getElsEvntSeq() {
		return this.elsEvntSeq;
	}

	public void setElsEvntSeq(long elsEvntSeq) {
		this.elsEvntSeq = elsEvntSeq;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ElsEventId))
			return false;
		ElsEventId castOther = (ElsEventId) other;
		return ((this.getPdno() == castOther.getPdno()) || (this.getPdno() != null && castOther.getPdno() != null && this
				.getPdno().equals(castOther.getPdno())))
				&& ((this.getElsEvntDvsnCd() == castOther.getElsEvntDvsnCd()) || (this.getElsEvntDvsnCd() != null
						&& castOther.getElsEvntDvsnCd() != null && this.getElsEvntDvsnCd().equals(
						castOther.getElsEvntDvsnCd()))) 
					&& (this.getElsEvntSeq() == castOther.getElsEvntSeq());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPdno() == null ? 0 : this.getPdno().hashCode());
		result = 37 * result + (getElsEvntDvsnCd() == null ? 0 : this.getElsEvntDvsnCd().hashCode());
		result = 37 * result + (int) this.getElsEvntSeq();
		return result;
	}
}
