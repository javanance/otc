package com.eugenefe.util;

import java.util.ArrayList;
import java.util.List;

public enum MarketVariableType {
	BOND(
			"BOND"
			,"com.eugenefe.entity.Bond"
			,true
			,false
			,"IR"
			,"select a from  BondHis a where a.id.bondId=#{selectedMarketVariable.mvId}" +
					" and a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd} "	
	)
	,IR_LEG(
			"IR_LEG"
			,"com.eugenefe.entity.Bond"
			,true
			,false
			,"IR"
			,"select a from  BondHis a where a.id.bondId=#{selectedMarketVariable.mvId}" +
					" and a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd} "	
	)
	,STOCK(
			"STOCK"
			,"com.eugenefe.entity.Stock"
			,true
			,true
			,"EQ"
			," select a from StockHis a where a.id.stockId=#{selectedMarketVariable.mvId}" +
					" and a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd} "		
	)
	,FUTURES(
			"FUTURES"
			,"com.eugenefe.entity.Futures"
			,true
			,true
			,"NONE"
			,"select a from  FuturesHis a where a.id.futuresId=#{selectedMarketVariable.mvId}" +
					" and a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd} "	
	)
	,OPTION(
			"OPTION"
			,"com.eugenefe.entity.Options"
			,true
			,false
			,"NONE"
			,"select a from  OptionHis a where a.id.optionId=#{selectedMarketVariable.mvId}" +
					" and a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd} "	
			)
	,ETF(
			"ETF"
			,"com.eugenefe.entity.Etf"
			,true
			,true
			,"NONE"
			,"select a from  EtfHis a where a.id.etfId=#{selectedMarketVariable.mvId}" +
					" and a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd} "	
	)
	,ELW(
			"ELW"
			,"com.eugenefe.entity.Options"
			,true
			,false
			,"NONE"
			,"select a from  OptionHis a where a.id.optionId=#{selectedMarketVariable.mvId}" +
					" and a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd} "	
	)
	,ELS(
			"ELS"
			,"com.eugenefe.entity.HiFive"
			,true
			,false
			,"NONE"
			,"select a from BondHis a where a.id.bondId=#{selectedMarketVariable.mvId}" +
					" and a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd} "	
	)
	,ELS_SWAP(
			"ELS_SWAP"
			,"com.eugenefe.entity.HiFive"
			,true
			,false
			,"NONE"
			,"select a from BondHis a where a.id.bondId=#{selectedMarketVariable.mvId}" +
					" and a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd} "	
	)
	,SWAP(
			"SWAP"
			,"com.eugenefe.entity.Synthetic"
			,true
			,false
			,"NONE"
			,"select a from  BondHis a where a.id.bondId=#{selectedMarketVariable.mvId}" +
					" and a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd} "	
	)
	,DEPOSIT(
			"DEPOSIT"
			,"com.eugenefe.entity.Bond"
			,true
			,false
			,"NONE"
			,"select a from  BondHis a where a.id.bondId=#{selectedMarketVariable.mvId}" +
					" and a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd} "	
	)
	
	,FX_RATE(
			"FX_RATE"
			,"com.eugenefe.entity.FxCash"
			,false
			,true
			,"FX"
			,"select a from  FxRateHis a where a.id.fxId=#{selectedMarketVariable.mvId}" +
					" and a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd} "	
	)
	,INT_RATE(
			"INT_RATE"
			,"com.eugenefe.entity.IntRate"
			,false
			,true
			,"IR"
			,"select a from  IntRateHis a where a.id.irId=#{selectedMarketVariable.mvId}" +
					" and a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd} "	
	)
	,S_INDEX(
			"S_INDEX"
			,"com.eugenefe.entity.StockIndex"
			,false
			,true
			,"EQ"
			,"select a from  StockIndexHis a where a.id.stIndexId=#{selectedMarketVariable.mvId}" +
					" and a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd} "	
	)
	,COMMODITY(
			"COMMODITY"
			,""
			,true
			,false
			,"NONE"
			,""
	)
	,SYNTHETIC( 
			"SYNTHETIC"
			,"com.eugenefe.entity.Synthetics"
			,true
			,false
			,"NONE"
			,"select a from Synthetic a"
	)		
	;
	
	private String type;
	private String classPath;
	private boolean isProduct; 
	private boolean underlying;
	private String rfType;
	private String query;
	
	private MarketVariableType(String type, String classPath,boolean isProduct, boolean underlying,String rfType, String query) {;
		this.type = type;
		this.classPath =classPath;
		this.isProduct = isProduct;
		this.underlying =underlying;
		this.rfType =rfType;
		this.query =query;
	}
	
	public String getType() {
		return this.type;
	}
	public String getClassPath() {
		return classPath;
	}
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	public boolean isProduct() {
		return isProduct;
	}
	public boolean isUnderlying() {
		return underlying;
	}
	public String getQuery(){
//		return this.query + " and a.id.bssd > #{basedateBean.stBssd} " +
//							" and a.id.bssd <=#{basedateBean.endBssd} " ;	
		return this.query;
	}
	public String getRfType() {
		return rfType;
	}
	
	public static List<MarketVariableType> getProductTypes(){
		List<MarketVariableType> rst = new ArrayList<MarketVariableType>();
		for(MarketVariableType aa : MarketVariableType.values()){
			if(aa.isProduct){
				rst.add(aa);
			}
		}
		return rst;
	}
}
