package com.eugenefe.entityinterface;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.eugenefe.util.AnnoMethodTree;

@Entity
@Table(name = "FX_CASH")
@PrimaryKeyJoinColumn(name="FX_ID")
public class FxCashSubClass extends MvSubClass{
	private String fxName;
	private String termCurr;
	private String baseCurr;
	private BigDecimal scaleFactor;
	
	public FxCashSubClass() {
	}

	@Column(name = "FX_NAME", length = 50)
	@Size(max = 50)
	@AnnoMethodTree(order=11, init=true)
	public String getFxName() {
		return this.fxName;
	}

	public void setFxName(String fxName) {
		this.fxName = fxName;
	}

	@Column(name = "TERM_CURR", length = 3)
	@Size(max = 3)
	@AnnoMethodTree(order=30, init=true)
	public String getTermCurr() {
		return this.termCurr;
	}

	public void setTermCurr(String termCurr) {
		this.termCurr = termCurr;
	}

	@Column(name = "BASE_CURR", length = 3)
	@Size(max = 3)
	@AnnoMethodTree(order=31, init=true)
	public String getBaseCurr() {
		return this.baseCurr;
	}

	public void setBaseCurr(String baseCurr) {
		this.baseCurr = baseCurr;
	}

	@Column(name = "SCALE_FACTOR", precision = 10, scale = 4)
	@AnnoMethodTree(order=40, init=true)
	public BigDecimal getScaleFactor() {
		return this.scaleFactor;
	}

	public void setScaleFactor(BigDecimal scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

}
