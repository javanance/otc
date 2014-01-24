package com.eugenefe.util;

public enum NamedQuery {
	PortfolioJoinReturnBssd(
			"select a from Portfolio a " 
			+ "join fetch a.portfolioReturns b "
			+ "where b.id.bssd = #{basedateBean.bssd} " 
			+ "order by a.portId" )
	
	,Position("select a from Position a ")

	,PositionReturn("select a from PositionReturn a where a.id.bssd = #{basedateBean.bssd}")
	
	,Bonds ("select a from Bond a")
	
	,AllMarketVariables ("select a from MarketVariable a ")

	,MarketVariables ("select a from MarketVariable a where a.productYN ='N'")
	
	,Products ("select a from MarketVariable a where a.productYN ='Y'")
	
	,RiskFactors ("select a from MarketVariable a where a.riskFactorYN ='Y' order by a.mvId")
	
	,BondHisList("select a from BondHis a")
	
	,StockHisList("select a from StockHis a")
	
	,StockIndexHisList("select a from StockIndexHis a")
	
	,IrCuverHisList("select a from IrCurveHis a")
	
	,VcvMatrixHisList("select a from VcvMatrixHis a ")
	
//	,VcvMatrixHisBssd("select a from VcvMatrixHis a where a.id.vcvId = #{pickListRfActionaa.vcvId} and a.id.bssd = #{basedateBean.bssd}  ")
	,VcvMatrixHisBssd("select a from VcvMatrixHis a where a.id.vcvId = #{flagBean.vcvMethod} and a.id.bssd = #{basedateBean.bssd} ")
	,VcvMatrixHisBtwn("select a from VcvMatrixHis a where a.id.bssd > #{basedateBean.stBssd} " +
					" and a.id.bssd <=#{basedateBean.endBssd}")
//	,VcvMatrixHisBssd("select a from VcvMatrixHis a where a.id.vcvId = 'SMA_1DAY' and a.id.bssd = #{basedateBean.bssd}  ")
	;
	
	private String query;
	
	private NamedQuery(String query) {
		this.query = query;
	}
	public String getQuery() {
		return this.query;
	}
}
