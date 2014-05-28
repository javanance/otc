package com.eugenefe.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.eugenefe.enums.raw.EElsMaturityType;
import com.eugenefe.enums.raw.EElsOptionType;
import com.eugenefe.enums.raw.EElsProdType;
import com.eugenefe.enums.raw.EElsSaleType;
import com.eugenefe.enums.raw.EElsUnderlyingType;

@Embeddable
public class CategoryEnumProperty {
	private EElsProdType elsProdType;
	private EElsOptionType optType;
	private EElsMaturityType elsMaturityType;
	private EElsUnderlyingType underlyingType;
	private EElsSaleType elsSaleType;
	
	
	public CategoryEnumProperty() {
	}

	@Column(name="OFBD_DRV_PRDT_DVSN_CD")
	@Enumerated(EnumType.STRING)
	public EElsProdType getElsProdType() {
		return elsProdType;
	}


	public void setElsProdType(EElsProdType elsProdType) {
		this.elsProdType = elsProdType;
	}

	@Column(name="ELS_OPT_DVSN_CD")
	@Enumerated(EnumType.STRING)
	public EElsOptionType getOptType() {
		return optType;
	}


	public void setOptType(EElsOptionType optType) {
		this.optType = optType;
	}


	@Column(name="ELS_PRDT_EXPD_DVSN_CD")
	@Enumerated(EnumType.STRING)
	public EElsMaturityType getElsMaturityType() {
		return elsMaturityType;
	}


	public void setElsMaturityType(EElsMaturityType elsMaturityType) {
		this.elsMaturityType = elsMaturityType;
	}

	@Column(name="ELS_IDX_CD")
	@Enumerated(EnumType.STRING)
	public EElsUnderlyingType getUnderlyingType() {
		return underlyingType;
	}


	public void setUnderlyingType(EElsUnderlyingType underlyingType) {
		this.underlyingType = underlyingType;
	}

	@Column(name="ELS_SALE_TYPE_CD")
	@Enumerated(EnumType.STRING)
	public EElsSaleType getElsSaleType() {
		return elsSaleType;
	}

	public void setElsSaleType(EElsSaleType elsSaleType) {
		this.elsSaleType = elsSaleType;
	}
}
