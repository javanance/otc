package com.eugenefe.entity;

// Generated 2014. 1. 28 ���� 9:38:27 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ScenarioHisId generated by hbm2java
 */
@Embeddable
public class ScenarioHisId implements java.io.Serializable {

	private String bssd;
	private String scenarioId;
	private String mvId;

	public ScenarioHisId() {
	}

	public ScenarioHisId(String bssd, String scenarioId, String mvId) {
		this.bssd = bssd;
		this.scenarioId = scenarioId;
		this.mvId = mvId;
	}

	@Column(name = "BSSD", nullable = false, length = 8)
	public String getBssd() {
		return this.bssd;
	}

	public void setBssd(String bssd) {
		this.bssd = bssd;
	}

	@Column(name = "SCENARIO_ID", nullable = false, length = 20)
	public String getScenarioId() {
		return this.scenarioId;
	}

	public void setScenarioId(String scenarioId) {
		this.scenarioId = scenarioId;
	}

	@Column(name = "MV_ID", nullable = false, length = 20)
	public String getMvId() {
		return this.mvId;
	}

	public void setMvId(String mvId) {
		this.mvId = mvId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ScenarioHisId))
			return false;
		ScenarioHisId castOther = (ScenarioHisId) other;

		return ((this.getBssd() == castOther.getBssd()) || (this.getBssd() != null && castOther.getBssd() != null && this
				.getBssd().equals(castOther.getBssd())))
				&& ((this.getScenarioId() == castOther.getScenarioId()) || (this.getScenarioId() != null
						&& castOther.getScenarioId() != null && this.getScenarioId().equals(castOther.getScenarioId())))
				&& ((this.getMvId() == castOther.getMvId()) || (this.getMvId() != null && castOther.getMvId() != null && this
						.getMvId().equals(castOther.getMvId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBssd() == null ? 0 : this.getBssd().hashCode());
		result = 37 * result + (getScenarioId() == null ? 0 : this.getScenarioId().hashCode());
		result = 37 * result + (getMvId() == null ? 0 : this.getMvId().hashCode());
		return result;
	}

}