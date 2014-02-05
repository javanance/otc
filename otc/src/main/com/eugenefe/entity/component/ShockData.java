package com.eugenefe.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.eugenefe.enums.EShockType;

@Embeddable
public class ShockData {
	private EShockType shockType;
	private double shockValue;
	
	public ShockData() {
	}

	public ShockData(EShockType shockType, double shockValue) {
		super();
		this.shockType = shockType;
		this.shockValue = shockValue;
	}

	@Column(name="SHOCK_TYPE")
	@Enumerated
	public EShockType getShockType() {
		return shockType;
	}
	public void setShockType(EShockType shockType) {
		this.shockType = shockType;
	}
	
	@Column(name="SHOCK_VALUE")
	public double getShockValue() {
		return shockValue;
	}
	public void setShockValue(double shockValue) {
		this.shockValue = shockValue;
	}
	
	

}
