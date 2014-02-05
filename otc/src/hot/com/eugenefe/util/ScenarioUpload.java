package com.eugenefe.util;

import java.math.BigDecimal;

import com.eugenefe.entity.Scenario;
import com.eugenefe.enums.EShockType;

public class ScenarioUpload implements Cloneable{

	private String scenarioId;
	private String scenarioName;
	private String scenarioType;
	private String scenarioSet;
	
	private String mvId;
//	private EShockType shockType;
	private String shockType;
	private double shockValue;
	

	public ScenarioUpload() {
	}

	public String getScenarioId() {
		return scenarioId;
	}

	public void setScenarioId(String scenarioId) {
		this.scenarioId = scenarioId;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public String getScenarioType() {
		return scenarioType;
	}

	public void setScenarioType(String scenarioType) {
		this.scenarioType = scenarioType;
	}

	public String getScenarioSet() {
		return scenarioSet;
	}

	public void setScenarioSet(String scenarioSet) {
		this.scenarioSet = scenarioSet;
	}

	
//	public EShockType getShockType() {
//		return shockType;
//	}
//	public void setShockType(EShockType shockType) {
//		this.shockType = shockType;
//	}
//	public BigDecimal getShockValue() {
//		return shockValue;
//	}
//	public void setShockValue(BigDecimal shockValue) {
//		this.shockValue = shockValue;
//	}

	public String getShockType() {
		return shockType;
	}

	public void setShockType(String shockType) {
		this.shockType = shockType;
	}

	public double getShockValue() {
		return shockValue;
	}

	public void setShockValue(double shockValue) {
		this.shockValue = shockValue;
	}

	public String getMvId() {
		return mvId;
	}

	public void setMvId(String mvId) {
		this.mvId = mvId;
	}
	
}
