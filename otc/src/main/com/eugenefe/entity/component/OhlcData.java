package com.eugenefe.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OhlcData {
	private Date baseDate;
	private double openPrice;
	private double highPrice;
	private double lowPrice;
	private double closePrice;
	private double volume;
	private double volumeAmount;
	public OhlcData(Date baseDate, double openPrice, double highPrice, double lowPrice, double closePrice,
			double volume, double volumeAmount) {
		super();
		this.baseDate = baseDate;
		this.openPrice = openPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closePrice = closePrice;
		this.volume = volume;
		this.volumeAmount = volumeAmount;
	}
	
	@Column(name="BSSD")
	public Date getBaseDate() {
		return baseDate;
	}
	public void setBaseDate(Date baseDate) {
		this.baseDate = baseDate;
	}
	@Column(name="OPEN_PRICE")
	public double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	
	@Column(name="HIGH_PRICE")
	public double getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(double highPrice) {
		this.highPrice = highPrice;
	}
	
	@Column(name="LOW_PRICE")
	public double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(double lowPrice) {
		this.lowPrice = lowPrice;
	}
	
	@Column(name="CLOSE_PRICE")
	public double getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}
	
	@Column(name="VOLUME")
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	@Column(name="VOLUME_AMT")
	public double getVolumeAmount() {
		return volumeAmount;
	}
	public void setVolumeAmount(double volumeAmount) {
		this.volumeAmount = volumeAmount;
	}

}
