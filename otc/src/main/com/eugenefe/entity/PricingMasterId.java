package com.eugenefe.entity;

// Generated 2014. 1. 17 ���� 5:07:56 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;

/**
 * PricingMasterId generated by hbm2java
 */
@Embeddable
@AnnoNavigationFilter
public class PricingMasterId implements java.io.Serializable {

	private String pricingObjId;
	private String prodId;

	public PricingMasterId() {
	}

	public PricingMasterId(String pricingObjId, String prodId) {
		this.pricingObjId = pricingObjId;
		this.prodId = prodId;
	}

	@Column(name = "PRICING_OBJ_ID", nullable = false, length = 20)
	@AnnoMethodTree(order=10, init=true)
	public String getPricingObjId() {
		return this.pricingObjId;
	}

	public void setPricingObjId(String pricingObjId) {
		this.pricingObjId = pricingObjId;
	}

	@Column(name = "PROD_ID", nullable = false, length = 20)
	@AnnoMethodTree(order=20, init=true)
	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PricingMasterId))
			return false;
		PricingMasterId castOther = (PricingMasterId) other;

		return ((this.getPricingObjId() == castOther.getPricingObjId()) || (this.getPricingObjId() != null
				&& castOther.getPricingObjId() != null && this.getPricingObjId().equals(castOther.getPricingObjId())))
				&& ((this.getProdId() == castOther.getProdId()) || (this.getProdId() != null
						&& castOther.getProdId() != null && this.getProdId().equals(castOther.getProdId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPricingObjId() == null ? 0 : this.getPricingObjId().hashCode());
		result = 37 * result + (getProdId() == null ? 0 : this.getProdId().hashCode());
		return result;
	}

}