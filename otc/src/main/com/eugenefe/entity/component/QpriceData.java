package com.eugenefe.entity.component;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class QpriceData implements Cloneable{
	private String bssd;
	private BigDecimal openPrice;
	private BigDecimal highPrice;
	private BigDecimal lowPrice;
	private BigDecimal closePrice;
	private BigDecimal volume;
	private BigDecimal openInterest;
	
	public QpriceData() {
	}
	
	public QpriceData(String bssd, BigDecimal closePrice) {
		this.bssd = bssd;
		this.closePrice = closePrice;
	}

	public QpriceData(String bssd, BigDecimal openPrice, BigDecimal highPrice, BigDecimal lowPrice,
			BigDecimal closePrice, BigDecimal volume, BigDecimal openInterest) {
		this.bssd = bssd;
		this.openPrice = openPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closePrice = closePrice;
		this.volume = volume;
		this.openInterest = openInterest;
	}

	@Transient
//	@Column(name="BSSD" , insertable=false, updatable=false)
	public String getBssd() {
		return bssd;
	}
	public void setBssd(String bssd) {
		this.bssd = bssd;
	}

	@Column(name="OPEN_PRICE")
	public BigDecimal getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(BigDecimal openPrice) {
		this.openPrice = openPrice;
	}
	
	@Column(name="HIGH_PRICE")
	public BigDecimal getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(BigDecimal highPrice) {
		this.highPrice = highPrice;
	}
	
	@Column(name="LOW_PRICE")
	public BigDecimal getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(BigDecimal lowPrice) {
		this.lowPrice = lowPrice;
	}
	
	@Column(name="CLOSE_PRICE")
	public BigDecimal getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(BigDecimal closePrice) {
		this.closePrice = closePrice;
	}

	@Transient
//	@Column(name="VOLUME")
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	@Transient
//	@Column(name="OPEN_INTEREST")
	public BigDecimal getOpenInterest() {
		return openInterest;
	}

	public void setOpenInterest(BigDecimal openInterest) {
		this.openInterest = openInterest;
	}


	

}
