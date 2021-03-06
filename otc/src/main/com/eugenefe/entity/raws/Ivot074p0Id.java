package com.eugenefe.entity.raws;

// Generated 2014. 4. 7 ���� 8:01:40 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Ivot074p0Id generated by hbm2java
 */
@Embeddable
public class Ivot074p0Id implements java.io.Serializable {

	private String evluDt;
	private String pdno;
	private String prdtTypeCd;
	private String trOppnNo;
	private String elsTrDtldTypeCd;
	private int issuTmod;

	public Ivot074p0Id() {
	}

	public Ivot074p0Id(String evluDt, String pdno, String prdtTypeCd, String trOppnNo, String elsTrDtldTypeCd,
			int issuTmod) {
		this.evluDt = evluDt;
		this.pdno = pdno;
		this.prdtTypeCd = prdtTypeCd;
		this.trOppnNo = trOppnNo;
		this.elsTrDtldTypeCd = elsTrDtldTypeCd;
		this.issuTmod = issuTmod;
	}

	@Column(name = "EVLU_DT", nullable = false, length = 8)
	public String getEvluDt() {
		return this.evluDt;
	}

	public void setEvluDt(String evluDt) {
		this.evluDt = evluDt;
	}

	@Column(name = "PDNO", nullable = false, length = 12)
	public String getPdno() {
		return this.pdno;
	}

	public void setPdno(String pdno) {
		this.pdno = pdno;
	}

	@Column(name = "PRDT_TYPE_CD", nullable = false, length = 3)
	public String getPrdtTypeCd() {
		return this.prdtTypeCd;
	}

	public void setPrdtTypeCd(String prdtTypeCd) {
		this.prdtTypeCd = prdtTypeCd;
	}

	@Column(name = "TR_OPPN_NO", nullable = false, length = 12)
	public String getTrOppnNo() {
		return this.trOppnNo;
	}

	public void setTrOppnNo(String trOppnNo) {
		this.trOppnNo = trOppnNo;
	}

	@Column(name = "ELS_TR_DTLD_TYPE_CD", nullable = false, length = 2)
	public String getElsTrDtldTypeCd() {
		return this.elsTrDtldTypeCd;
	}

	public void setElsTrDtldTypeCd(String elsTrDtldTypeCd) {
		this.elsTrDtldTypeCd = elsTrDtldTypeCd;
	}

	@Column(name = "ISSU_TMOD", nullable = false, precision = 9, scale = 0)
	public int getIssuTmod() {
		return this.issuTmod;
	}

	public void setIssuTmod(int issuTmod) {
		this.issuTmod = issuTmod;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Ivot074p0Id))
			return false;
		Ivot074p0Id castOther = (Ivot074p0Id) other;

		return ((this.getEvluDt() == castOther.getEvluDt()) || (this.getEvluDt() != null
				&& castOther.getEvluDt() != null && this.getEvluDt().equals(castOther.getEvluDt())))
				&& ((this.getPdno() == castOther.getPdno()) || (this.getPdno() != null && castOther.getPdno() != null && this
						.getPdno().equals(castOther.getPdno())))
				&& ((this.getPrdtTypeCd() == castOther.getPrdtTypeCd()) || (this.getPrdtTypeCd() != null
						&& castOther.getPrdtTypeCd() != null && this.getPrdtTypeCd().equals(castOther.getPrdtTypeCd())))
				&& ((this.getTrOppnNo() == castOther.getTrOppnNo()) || (this.getTrOppnNo() != null
						&& castOther.getTrOppnNo() != null && this.getTrOppnNo().equals(castOther.getTrOppnNo())))
				&& ((this.getElsTrDtldTypeCd() == castOther.getElsTrDtldTypeCd()) || (this.getElsTrDtldTypeCd() != null
						&& castOther.getElsTrDtldTypeCd() != null && this.getElsTrDtldTypeCd().equals(
						castOther.getElsTrDtldTypeCd()))) && (this.getIssuTmod() == castOther.getIssuTmod());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getEvluDt() == null ? 0 : this.getEvluDt().hashCode());
		result = 37 * result + (getPdno() == null ? 0 : this.getPdno().hashCode());
		result = 37 * result + (getPrdtTypeCd() == null ? 0 : this.getPrdtTypeCd().hashCode());
		result = 37 * result + (getTrOppnNo() == null ? 0 : this.getTrOppnNo().hashCode());
		result = 37 * result + (getElsTrDtldTypeCd() == null ? 0 : this.getElsTrDtldTypeCd().hashCode());
		result = 37 * result + this.getIssuTmod();
		return result;
	}

}
