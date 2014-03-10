package com.eugenefe.util;

import javax.ejb.Startup;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

//@Name("eViewNew")
//@Scope(ScopeType.SESSION)
public enum EViewNew {
	 Home("home", 									false, 	false, 	false, 	false,	false, 	false)
//	,MarketVariable("v100MarketVariable")
	,ViewHistoryData("v101ViewHistoryData"		, 	true, 	true, 	true, 	false,	false, 	false)
//	,ViewRfHistoryData("v102ViewRfHistoryData")
//	,ViewProdHistoryData("v103ViewProdHistoryData")
//	,MarketVariableSelect("v100MarketVariable")
	,ProductDashBoard("v110ProductInit", 			true, 	true, 	true, 	false,	false, 	false)
	,ProductSelect("v110Product", 					true, 	true, 	true, 	false,	false, 	false)
//	,RiskFactor("v120RiskFactor")
	,VcvMatrix("v130RfVcvMatrix", 					true, 	true, 	true, 	false,	false, 	false)
	,ForexMatrix("v140ForexMatrix", 				true, 	true, 	true, 	false,	false, 	false)
	,v130("v130RfVcvMatrix", 						false, 	false, 	false, 	false,	false, 	false)
	,v140("v140IrCurve", 							true, 	false, 	false, 	false,	false, 	true)
	,v145("v145IrCurveHis", 						true, 	false, 	false, 	false,	false, 	true)
	,v146("v146ForexMatrix", 						true, 	false, 	false, 	false,	false, 	false)
	,RiskComponent("v200RiskComponent", 			true, 	true, 	true, 	false,	false, 	false)
	,RiskAnalysis("v300ReturnRisk", 				true, 	true, 	true, 	false,	false, 	false)
	,WhatIf("v400WhatIf", 							true, 	true, 	true, 	false,	false, 	false)
	,StressTest("v500StressTest", 					true, 	true, 	true, 	false,	false, 	false)
	,BackTest("v550BackTest", 						true, 	true, 	true, 	false, 	false,	false)
	,Pricer("v600Pricer", 							true, 	true, 	true, 	false, 	false,	false)
	,v600("v600Price", 								true, 	true,	true, 	false, 	false,	false)
	,v650("v650PriceSimulation",					true, 	false, 	false, 	true, 	false, 	false)
	,v691("v691ScenarioSetting", 					true, 	false, 	false, 	false, 	false,	true)
	,v694("v694PortfolioSetting",					true,	false, 	false, 	true, 	false, 	false)
	,v695("v695HierarchySetting",					true,	false, 	false, 	true, 	false, 	false)
	,v696("v696StructuredNote", 					false,	false, 	false, 	false, 	false, 	true)
	,v6961("v6961InterestOption", 					false,	false, 	false, 	false, 	false, 	false)
	,v697("v697CreditDerivatives",					false,	false, 	false, 	false, 	false, 	true)
	,v698("v698EquitySwap",							false,	false, 	false, 	false, 	false, 	true)
	,v699("v699ElsDls",								false,	false, 	false, 	false, 	false, 	true)
	,v800("v800Settings", 							false, 	false, 	false,	false, 	false, 	false)
	,LimitManagement("v700Limit", true, true, true, false,false, false)
	,v900("v900DataNavigation", 					true, 	false, 	false,	false, 	false,	true)
	;
	
	public final String url;
	public boolean renderLeft;
	public boolean renderRight;
	public boolean renderBottom;
	public boolean renderInnerTop;
	public boolean renderInnerRight;
	public boolean renderInnerBottom;

	private EViewNew(String url, boolean left, boolean right, boolean bottom, boolean innerTop, boolean innerRight, boolean innerBottom) {
		this.url = url;
		this.renderLeft=left;
		this.renderRight =right;
		this.renderBottom = bottom;
		this.renderInnerTop = innerTop;
		this.renderInnerRight= innerRight;
		this.renderInnerBottom = innerBottom;
	}

	public String getUrl() {
		return "/view/"+ url;
	}

	public boolean isRenderRight() {
		return renderRight;
	}
	public boolean isRenderLeft() {
		return renderLeft;
	}
	public boolean isRenderBottom() {
		return renderBottom;
	}

	public boolean isRenderInnerRight() {
		return renderInnerRight;
	}
	public boolean isRenderInnerBottom() {
		return renderInnerBottom;
	}

	public boolean isRenderInnerTop() {
		return renderInnerTop;
	}
}
