package com.eugenefe.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

@Embeddable
public class CategoryProperty {
	private String elsProdType;
	private String optType;
	private String elsMaturityType;
	private String underlyingType;
	private String elsSaleType;
	
	
	public CategoryProperty() {
	}

	@Column(name="OFBD_DRV_PRDT_DVSN_CD")
	public String getElsProdType() {
		return elsProdType;
	}
	public void setElsProdType(String elsProdType) {
		this.elsProdType = elsProdType;
	}
	@Column(name="ELS_OPT_DVSN_CD")
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	
	@Column(name="ELS_PRDT_EXPD_DVSN_CD")
	public String getElsMaturityType() {
		return elsMaturityType;
	}
	public void setElsMaturityType(String elsMaturityType) {
		this.elsMaturityType = elsMaturityType;
	}
	@Column(name="ELS_IDX_CD")
	public String getUnderlyingType() {
		return underlyingType;
	}
	public void setUnderlyingType(String underlyingType) {
		this.underlyingType = underlyingType;
	}
	
	@Column(name="ELS_SALE_TYPE_CD")
	public String getElsSaleType() {
		return elsSaleType;
	}
	public void setElsSaleType(String elsSaleType) {
		this.elsSaleType = elsSaleType;
	}
}
