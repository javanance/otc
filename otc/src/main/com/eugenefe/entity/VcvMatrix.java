package com.eugenefe.entity;

// Generated Apr 10, 2013 4:09:22 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * VcvMatrix generated by hbm2java
 */
@Entity
@Table(name = "VCV_MATRIX")
public class VcvMatrix implements java.io.Serializable {

	private String vcvId;
	private String vcvType;
	private Long samplingSize;
	private Long timeHorizon;
	private BigDecimal decayFactor;
	private boolean isZeroMean;
	private boolean isPriceVol;
//	private Set<VcvMatrixHis> vcvMatrixHises = new HashSet<VcvMatrixHis>(0);
//	private Set<ScenarioVar> scenarioVars = new HashSet<ScenarioVar>(0);

	public VcvMatrix() {
	}

//	public VcvMatrix(String vcvId) {
//		this.vcvId = vcvId;
//	}

//	public VcvMatrix(String vcvId, String vcvType, Long samplingSize, Long timeHorizon, BigDecimal decayFactor,
//			Character zeroMean, Character priceVol, Set<VcvMatrixHis> vcvMatrixHises, Set<ScenarioVar> scenarioVars) {
//		this.vcvId = vcvId;
//		this.vcvType = vcvType;
//		this.samplingSize = samplingSize;
//		this.timeHorizon = timeHorizon;
//		this.decayFactor = decayFactor;
//		this.zeroMean = zeroMean;
//		this.priceVol = priceVol;
//		this.vcvMatrixHises = vcvMatrixHises;
//		this.scenarioVars = scenarioVars;
//	}

	@Id
	@Column(name = "VCV_ID", unique = true, nullable = false, length = 20)
	@NotNull
	@Size(max = 20)
	public String getVcvId() {
		return this.vcvId;
	}

	public void setVcvId(String vcvId) {
		this.vcvId = vcvId;
	}

	@Column(name = "VCV_TYPE", length = 10)
	@Size(max = 10)
	public String getVcvType() {
		return this.vcvType;
	}

	public void setVcvType(String vcvType) {
		this.vcvType = vcvType;
	}

	@Column(name = "SAMPLING_SIZE", precision = 10, scale = 0)
	public Long getSamplingSize() {
		return this.samplingSize;
	}

	public void setSamplingSize(Long samplingSize) {
		this.samplingSize = samplingSize;
	}

	@Column(name = "TIME_HORIZON", precision = 10, scale = 0)
	public Long getTimeHorizon() {
		return this.timeHorizon;
	}

	public void setTimeHorizon(Long timeHorizon) {
		this.timeHorizon = timeHorizon;
	}

	@Column(name = "DECAY_FACTOR", precision = 10, scale = 4)
	public BigDecimal getDecayFactor() {
		return this.decayFactor;
	}

	public void setDecayFactor(BigDecimal decayFactor) {
		this.decayFactor = decayFactor;
	}

	@org.hibernate.annotations.Type(type="yes_no")
	@Column(name = "ZERO_MEAN")
	public boolean isZeroMean() {
		return this.isZeroMean;
	}

	public void setZeroMean(boolean isZeroMean) {
		this.isZeroMean = isZeroMean;
	}

	@org.hibernate.annotations.Type(type="yes_no")
	@Column(name = "PRICE_VOL")
	public boolean isPriceVol() {
		return this.isPriceVol;
	}

	public void setPriceVol(boolean isPriceVol) {
		this.isPriceVol = isPriceVol;
	}

//	@Column(name = "ZERO_MEAN", length = 1)
//	public Character getZeroMean() {
//		return this.zeroMean;
//	}
//
//	public void setZeroMean(Character zeroMean) {
//		this.zeroMean = zeroMean;
//	}
//
//	@Column(name = "PRICE_VOL", length = 1)
//	public Character getPriceVol() {
//		return this.priceVol;
//	}
//
//	public void setPriceVol(Character priceVol) {
//		this.priceVol = priceVol;
//	}
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vcvMatrix")
//	public Set<VcvMatrixHis> getVcvMatrixHises() {
//		return this.vcvMatrixHises;
//	}
//
//	public void setVcvMatrixHises(Set<VcvMatrixHis> vcvMatrixHises) {
//		this.vcvMatrixHises = vcvMatrixHises;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vcvMatrix")
//	public Set<ScenarioVar> getScenarioVars() {
//		return this.scenarioVars;
//	}
//
//	public void setScenarioVars(Set<ScenarioVar> scenarioVars) {
//		this.scenarioVars = scenarioVars;
//	}

}
