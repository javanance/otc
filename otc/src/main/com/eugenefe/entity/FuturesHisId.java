package com.eugenefe.entity;

// Generated Apr 10, 2013 4:09:22 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;

/**
 * FuturesHisId generated by hbm2java
 */
@Embeddable
@AnnoNavigationFilter
public class FuturesHisId implements java.io.Serializable {

	private String bssd;
	private String futuresId;

	public FuturesHisId() {
	}

	public FuturesHisId(String bssd, String futuresId) {
		this.bssd = bssd;
		this.futuresId = futuresId;
	}

	@Column(name = "BSSD", nullable = false, length = 8)
	@NotNull
	@Size(max = 8)
	@AnnoMethodTree(order=10, init=true)
	public String getBssd() {
		return this.bssd;
	}

	public void setBssd(String bssd) {
		this.bssd = bssd;
	}

	@Column(name = "FUTURES_ID", nullable = false, length = 20)
	@NotNull
	@Size(max = 20)
	@AnnoMethodTree(order=20, init=true)
	public String getFuturesId() {
		return this.futuresId;
	}

	public void setFuturesId(String futuresId) {
		this.futuresId = futuresId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FuturesHisId))
			return false;
		FuturesHisId castOther = (FuturesHisId) other;

		return ((this.getBssd() == castOther.getBssd()) || (this.getBssd() != null && castOther.getBssd() != null && this
				.getBssd().equals(castOther.getBssd())))
				&& ((this.getFuturesId() == castOther.getFuturesId()) || (this.getFuturesId() != null
						&& castOther.getFuturesId() != null && this.getFuturesId().equals(castOther.getFuturesId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBssd() == null ? 0 : this.getBssd().hashCode());
		result = 37 * result + (getFuturesId() == null ? 0 : this.getFuturesId().hashCode());
		return result;
	}

}