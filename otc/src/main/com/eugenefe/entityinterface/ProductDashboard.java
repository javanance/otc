package com.eugenefe.entityinterface;

import java.math.BigDecimal;

import com.eugenefe.util.MarketVariableType;

public class ProductDashboard {
//	private String mvType;
	private MarketVariableType mvType;
	private long posCnt;
	private BigDecimal posAmt;
	
	public ProductDashboard(){
		
	}
	public ProductDashboard(MarketVariableType mvType) {
		this.mvType = mvType;
	}
	public ProductDashboard(MarketVariableType mvType, long posCnt) {
		this.mvType = mvType;
		this.posCnt = posCnt;
	}
	public ProductDashboard(MarketVariableType mvType,  BigDecimal posAmt) {
		this.mvType = mvType;
		this.posAmt = posAmt;
	}
	public ProductDashboard(MarketVariableType mvType, long posCnt, BigDecimal posAmt) {
		this.mvType = mvType;
		this.posCnt = posCnt;
		this.posAmt = posAmt;
	}


	public MarketVariableType getMvType() {
		return mvType;
	}

	public void setMvType(MarketVariableType mvType) {
		this.mvType = mvType;
	}

	public long getPosCnt() {
		return posCnt;
	}

	public void setPosCnt(long posCnt) {
		this.posCnt = posCnt;
	}

	public BigDecimal getPosAmt() {
		return posAmt;
	}

	public void setPosAmt(BigDecimal posAmt) {
		this.posAmt = posAmt;
	}
	

}
