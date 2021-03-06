package com.eugenefe.entity.raws;

// Generated 2014. 4. 7 ���� 8:01:40 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Pdif214p0 generated by hbm2java
 */
@Entity
@Table(name = "PDIF214P0")
public class Pdif214p0 implements java.io.Serializable {

	private Pdif214p0Id id;
	private String srdpElsDfrmFrqcCd;
	private String elsSrdpBassCd;
	private BigDecimal srdpRt;
	private Long srdpAmt;
	private String oprtStfno;
	private String oprtTmno;
	private String oprtDtlDtime;
	private String oprtTrCd;
	private String lastChngPgmName;
	private Date lastChngDtime;

	public Pdif214p0() {
	}

	public Pdif214p0(Pdif214p0Id id) {
		this.id = id;
	}

	public Pdif214p0(Pdif214p0Id id, String srdpElsDfrmFrqcCd, String elsSrdpBassCd, BigDecimal srdpRt, Long srdpAmt,
			String oprtStfno, String oprtTmno, String oprtDtlDtime, String oprtTrCd, String lastChngPgmName,
			Date lastChngDtime) {
		this.id = id;
		this.srdpElsDfrmFrqcCd = srdpElsDfrmFrqcCd;
		this.elsSrdpBassCd = elsSrdpBassCd;
		this.srdpRt = srdpRt;
		this.srdpAmt = srdpAmt;
		this.oprtStfno = oprtStfno;
		this.oprtTmno = oprtTmno;
		this.oprtDtlDtime = oprtDtlDtime;
		this.oprtTrCd = oprtTrCd;
		this.lastChngPgmName = lastChngPgmName;
		this.lastChngDtime = lastChngDtime;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "pdno", column = @Column(name = "PDNO", nullable = false, length = 12)),
			@AttributeOverride(name = "prdtTypeCd", column = @Column(name = "PRDT_TYPE_CD", nullable = false, length = 3)),
			@AttributeOverride(name = "elsEvntDvsnCd", column = @Column(name = "ELS_EVNT_DVSN_CD", nullable = false, length = 2)),
			@AttributeOverride(name = "elsEvntSeq", column = @Column(name = "ELS_EVNT_SEQ", nullable = false, precision = 16, scale = 0)) })
	public Pdif214p0Id getId() {
		return this.id;
	}

	public void setId(Pdif214p0Id id) {
		this.id = id;
	}

	@Column(name = "SRDP_ELS_DFRM_FRQC_CD", length = 2)
	public String getSrdpElsDfrmFrqcCd() {
		return this.srdpElsDfrmFrqcCd;
	}

	public void setSrdpElsDfrmFrqcCd(String srdpElsDfrmFrqcCd) {
		this.srdpElsDfrmFrqcCd = srdpElsDfrmFrqcCd;
	}

	@Column(name = "ELS_SRDP_BASS_CD", length = 2)
	public String getElsSrdpBassCd() {
		return this.elsSrdpBassCd;
	}

	public void setElsSrdpBassCd(String elsSrdpBassCd) {
		this.elsSrdpBassCd = elsSrdpBassCd;
	}

	@Column(name = "SRDP_RT", precision = 21, scale = 8)
	public BigDecimal getSrdpRt() {
		return this.srdpRt;
	}

	public void setSrdpRt(BigDecimal srdpRt) {
		this.srdpRt = srdpRt;
	}

	@Column(name = "SRDP_AMT", precision = 18, scale = 0)
	public Long getSrdpAmt() {
		return this.srdpAmt;
	}

	public void setSrdpAmt(Long srdpAmt) {
		this.srdpAmt = srdpAmt;
	}

	@Column(name = "OPRT_STFNO", length = 6)
	public String getOprtStfno() {
		return this.oprtStfno;
	}

	public void setOprtStfno(String oprtStfno) {
		this.oprtStfno = oprtStfno;
	}

	@Column(name = "OPRT_TMNO", length = 16)
	public String getOprtTmno() {
		return this.oprtTmno;
	}

	public void setOprtTmno(String oprtTmno) {
		this.oprtTmno = oprtTmno;
	}

	@Column(name = "OPRT_DTL_DTIME", length = 17)
	public String getOprtDtlDtime() {
		return this.oprtDtlDtime;
	}

	public void setOprtDtlDtime(String oprtDtlDtime) {
		this.oprtDtlDtime = oprtDtlDtime;
	}

	@Column(name = "OPRT_TR_CD", length = 12)
	public String getOprtTrCd() {
		return this.oprtTrCd;
	}

	public void setOprtTrCd(String oprtTrCd) {
		this.oprtTrCd = oprtTrCd;
	}

	@Column(name = "LAST_CHNG_PGM_NAME", length = 50)
	public String getLastChngPgmName() {
		return this.lastChngPgmName;
	}

	public void setLastChngPgmName(String lastChngPgmName) {
		this.lastChngPgmName = lastChngPgmName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_CHNG_DTIME", length = 7)
	public Date getLastChngDtime() {
		return this.lastChngDtime;
	}

	public void setLastChngDtime(Date lastChngDtime) {
		this.lastChngDtime = lastChngDtime;
	}

}
