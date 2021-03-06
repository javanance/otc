package com.eugenefe.entity;

// Generated 2014. 1. 17 ���� 5:07:56 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;

/**
 * PricingMappingId generated by hbm2java
 */
@Embeddable
@AnnoNavigationFilter
public class PricingMappingId implements java.io.Serializable {

	private String pricingObjId;
	private String mvType;

	public PricingMappingId() {
	}

	public PricingMappingId(String pricingObjId, String mvType) {
		this.pricingObjId = pricingObjId;
		this.mvType = mvType;
	}

	@Column(name = "PRICING_OBJ_ID", nullable = false, length = 20)
	@AnnoMethodTree(order=10, init=true)
	public String getPricingObjId() {
		return this.pricingObjId;
	}

	public void setPricingObjId(String pricingObjId) {
		this.pricingObjId = pricingObjId;
	}

	@Column(name = "MV_TYPE", nullable = false, length = 20)
	@AnnoMethodTree(order=20, init=true)
	public String getMvType() {
		return this.mvType;
	}

	public void setMvType(String mvType) {
		this.mvType = mvType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PricingMappingId))
			return false;
		PricingMappingId castOther = (PricingMappingId) other;

		return ((this.getPricingObjId() == castOther.getPricingObjId()) || (this.getPricingObjId() != null
				&& castOther.getPricingObjId() != null && this.getPricingObjId().equals(castOther.getPricingObjId())))
				&& ((this.getMvType() == castOther.getMvType()) || (this.getMvType() != null
						&& castOther.getMvType() != null && this.getMvType().equals(castOther.getMvType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPricingObjId() == null ? 0 : this.getPricingObjId().hashCode());
		result = 37 * result + (getMvType() == null ? 0 : this.getMvType().hashCode());
		return result;
	}

}
