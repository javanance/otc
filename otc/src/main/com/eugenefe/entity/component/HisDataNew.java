package com.eugenefe.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class HisDataNew {
	private Double basePrice;
	private Double openPrice;
	private Double highPrice;
	private Double lowPrice;
	private Double closePrice;
	private Double volume;
	private Double volumeAmount;
	

	public HisDataNew() {
	}
	public HisDataNew(Double basePrice, Double openPrice, Double highPrice, Double lowPrice, Double closePrice,
			Double volume, Double volumeAmount) {
		this.basePrice = basePrice;
		this.openPrice = openPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closePrice = closePrice;
		this.volume = volume;
		this.volumeAmount = volumeAmount;
	}

	
	@Column(name="BASE_PRICE")	
	public Double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	@Transient
	public Double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}
	
	@Transient
	public Double getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}
	
	@Transient
	public Double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}
	
	
	@Column(name="INT_RATE")
	public Double getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}
	
	@Transient
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	
	@Transient
	public Double getVolumeAmount() {
		return volumeAmount;
	}
	public void setVolumeAmount(Double volumeAmount) {
		this.volumeAmount = volumeAmount;
	}

}
