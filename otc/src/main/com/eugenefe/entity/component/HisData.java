package com.eugenefe.entity.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class HisData {
	private Double basePrice;
	private Double openPrice;
	private Double highPrice;
	private Double lowPrice;
	private Double closePrice;
	private Double volume;
	private Double volumeAmount;
	

	public HisData() {
	}
	public HisData(Double basePrice, Double openPrice, Double highPrice, Double lowPrice, Double closePrice,
			Double volume, Double volumeAmount) {
		super();
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

	@Column(name="OPEN_PRICE")
	public Double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}
	
	@Column(name="HIGH_PRICE")
	public Double getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}
	
	@Column(name="LOW_PRICE")
	public Double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}
	
	@Column(name="CLOSE_PRICE")
	public Double getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}
	
	@Column(name="VOLUME")
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	@Column(name="VOLUME_AMT")
	public Double getVolumeAmount() {
		return volumeAmount;
	}
	public void setVolumeAmount(Double volumeAmount) {
		this.volumeAmount = volumeAmount;
	}

}
