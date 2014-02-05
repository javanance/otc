package com.eugenefe.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public interface IMarketVariable extends Serializable{
	
	public String getBssd();
//	public String getMvId();
//	public String getMvName();
//	public String getMvType();
	
	public double getCurrentPrice(String bssd);
}
