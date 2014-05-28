package com.eugenefe.entity.raws;

// Generated 2014. 4. 7 ���� 8:01:40 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Pdif095p0 generated by hbm2java
 */
@Entity
@Table(name = "PDIF095P0")
public class Pdif095p0 implements java.io.Serializable {

	private Pdif095p0Id id;
	private Pdif004m0 elsMaster;
	private BigDecimal elsBssAsstEvntPric;
	private BigDecimal bssAsstRt;
	private String bastEvntStsfYn;
	private Long elsBastPrdtSeq;
	private String oprtStfno;
	private String oprtTmno;
	private String oprtDtlDtime;
	private String oprtTrCd;
	private BigDecimal elsBastRchgBspr;
	private BigDecimal elsBastRchgEvluRt;
	private String lastChngPgmName;
	private Date lastChngDtime;
	private Pdif094p0 elsEvent;
	
	@Transient
//	@ManyToOne
//	@JoinColumns({
//		@JoinColumn(name = "PDNO" , insertable=false, updatable=false)
//		,@JoinColumn(name = "PRDT_TYPE_CD" , insertable=false, updatable=false)
//		,@JoinColumn(name = "ELS_EVNT_DVSN_CD", insertable=false, updatable=false)
//		,@JoinColumn(name = "ELS_EVNT_SEQ", insertable=false, updatable=false)
//	})
	public Pdif094p0 getElsEvent() {
		return elsEvent;
	}

	public void setElsEvent(Pdif094p0 elsEvent) {
		this.elsEvent = elsEvent;
	}

	public Pdif095p0() {
	}

	public Pdif095p0(Pdif095p0Id id) {
		this.id = id;
	}

	public Pdif095p0(Pdif095p0Id id, BigDecimal elsBssAsstEvntPric, BigDecimal bssAsstRt, String bastEvntStsfYn,
			Long elsBastPrdtSeq, String oprtStfno, String oprtTmno, String oprtDtlDtime, String oprtTrCd,
			BigDecimal elsBastRchgBspr, BigDecimal elsBastRchgEvluRt, String lastChngPgmName, Date lastChngDtime) {
		this.id = id;
		this.elsBssAsstEvntPric = elsBssAsstEvntPric;
		this.bssAsstRt = bssAsstRt;
		this.bastEvntStsfYn = bastEvntStsfYn;
		this.elsBastPrdtSeq = elsBastPrdtSeq;
		this.oprtStfno = oprtStfno;
		this.oprtTmno = oprtTmno;
		this.oprtDtlDtime = oprtDtlDtime;
		this.oprtTrCd = oprtTrCd;
		this.elsBastRchgBspr = elsBastRchgBspr;
		this.elsBastRchgEvluRt = elsBastRchgEvluRt;
		this.lastChngPgmName = lastChngPgmName;
		this.lastChngDtime = lastChngDtime;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "pdno", column = @Column(name = "PDNO", nullable = false, length = 12)),
			@AttributeOverride(name = "prdtTypeCd", column = @Column(name = "PRDT_TYPE_CD", nullable = false, length = 3)),
			@AttributeOverride(name = "elsEvntDvsnCd", column = @Column(name = "ELS_EVNT_DVSN_CD", nullable = false, length = 2)),
			@AttributeOverride(name = "elsEvntSeq", column = @Column(name = "ELS_EVNT_SEQ", nullable = false, precision = 16, scale = 0)),
			@AttributeOverride(name = "elsBastPdno", column = @Column(name = "ELS_BAST_PDNO", nullable = false, length = 12)),
			@AttributeOverride(name = "elsBastPrdtTypeCd", column = @Column(name = "ELS_BAST_PRDT_TYPE_CD", nullable = false, length = 3)) })
	public Pdif095p0Id getId() {
		return this.id;
	}

	public void setId(Pdif095p0Id id) {
		this.id = id;
	}
	@Transient
//	@ManyToOne
//	@JoinColumn(name="PDNO", insertable=false, updatable=false)
	public Pdif004m0 getElsMaster() {
		return elsMaster;
	}

	public void setElsMaster(Pdif004m0 elsMaster) {
		this.elsMaster = elsMaster;
	}

	@Column(name = "ELS_BSS_ASST_EVNT_PRIC", precision = 21, scale = 8)
	public BigDecimal getElsBssAsstEvntPric() {
		return this.elsBssAsstEvntPric;
	}

	public void setElsBssAsstEvntPric(BigDecimal elsBssAsstEvntPric) {
		this.elsBssAsstEvntPric = elsBssAsstEvntPric;
	}

	@Column(name = "BSS_ASST_RT", precision = 21, scale = 8)
	public BigDecimal getBssAsstRt() {
		return this.bssAsstRt;
	}

	public void setBssAsstRt(BigDecimal bssAsstRt) {
		this.bssAsstRt = bssAsstRt;
	}

	@Column(name = "BAST_EVNT_STSF_YN", length = 1)
	public String getBastEvntStsfYn() {
		return this.bastEvntStsfYn;
	}

	public void setBastEvntStsfYn(String bastEvntStsfYn) {
		this.bastEvntStsfYn = bastEvntStsfYn;
	}

	@Column(name = "ELS_BAST_PRDT_SEQ", precision = 16, scale = 0)
	public Long getElsBastPrdtSeq() {
		return this.elsBastPrdtSeq;
	}

	public void setElsBastPrdtSeq(Long elsBastPrdtSeq) {
		this.elsBastPrdtSeq = elsBastPrdtSeq;
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

	@Column(name = "ELS_BAST_RCHG_BSPR", precision = 21, scale = 8)
	public BigDecimal getElsBastRchgBspr() {
		return this.elsBastRchgBspr;
	}

	public void setElsBastRchgBspr(BigDecimal elsBastRchgBspr) {
		this.elsBastRchgBspr = elsBastRchgBspr;
	}

	@Column(name = "ELS_BAST_RCHG_EVLU_RT", precision = 21, scale = 8)
	public BigDecimal getElsBastRchgEvluRt() {
		return this.elsBastRchgEvluRt;
	}

	public void setElsBastRchgEvluRt(BigDecimal elsBastRchgEvluRt) {
		this.elsBastRchgEvluRt = elsBastRchgEvluRt;
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