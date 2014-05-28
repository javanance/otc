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
	,v621("v621Position",							false, 	false,	false, 	false, 	false,	false)
	,v622("v622AssetPosition",						false, 	true,	false, 	false, 	false,	false)
	,v623("v623DebtPosition",						false, 	true,	false, 	false, 	false,	false)
	,v624("v624DebtPositionTree",					false, 	false,	false, 	false, 	false,	false)
	,v650("v650PriceSimulation",					true, 	false, 	false, 	true, 	false, 	false)
	,v690("v690PriceSetting", 						true, 	false, 	false, 	false, 	false,	true)
	,v691("v691ScenarioSetting", 					true, 	false, 	false, 	false, 	false,	true)
	,v694("v694PortfolioSetting",					true,	false, 	false, 	false, 	false, 	false)
	,v6941("v6941PortfolioNewSetting",				true,	false, 	false, 	true, 	false, 	false)
	,v695("v695HierarchySetting",					true,	false, 	false, 	true, 	false, 	false)
	,v660("v660OtcProduct",							true,	false, 	false, 	false, 	false, 	true)
	,v661("v661ElsStepDown",						true,	true, 	false, 	false, 	false, 	true)
	,v662("v662ElsRangeAccrual",					true,	false, 	false, 	false, 	false, 	false)
	,v663("v663ElsCds",							true,	false, 	false, 	false, 	false, 	false)
	,v664("v664ElsReturnSwap",					true,	false, 	false, 	false, 	false, 	false)
	,v665("v665OtcOption",						true,	false, 	false, 	false, 	false, 	false)
	,v666("v666FxForward",						true,	false, 	false, 	false, 	false, 	false)
	,v667("v667Cds",								true,	false, 	false, 	false, 	false, 	false)
	,v668("v668EquitySwap",						true,	false, 	false, 	false, 	false, 	false)
	,v669("v669Swap",								true,	false, 	false, 	false, 	false, 	false)
	,v696("v696StructuredNote", 					false,	false, 	false, 	false, 	false, 	true)
	,v6961("v6961InterestOption", 					false,	false, 	false, 	false, 	false, 	false)
	/*,v697("v697CreditDerivatives",					false,	false, 	false, 	false, 	false, 	true)*/
	,v697("v697CreditDerivatives",					false,	false, 	false, 	false, 	false, 	false)
	,v698("v698EquitySwap",							false,	false, 	false, 	false, 	false, 	true)
	,v699("v699ElsDls",								false,	false, 	false, 	false, 	false, 	true)
	,v800("v800Settings", 							false, 	false, 	false,	false, 	false, 	false)
	,LimitManagement("v700Limit", true, true, true, false,false, false)
	,v900("v900DataNavigation", 					true, 	false, 	false,	false, 	false,	true)
	,vv100("vv100View",			 					false, 	false, 	false,	false, 	false,	false)
	,vv200("vv200View",			 					true, 	false, 	false,	false, 	false,	true)
	,vv300("vv300View",			 					true, 	true, 	false,	false, 	false,	true)
	,vv400("vv400View",			 					true, 	false, 	false,	false, 	false,	true)
	,vv500("vv500View",			 					true, 	true, 	false,	false, 	false,	true)
	,vv600("vv600View",			 					true, 	false, 	false,	false, 	false,	true)	
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
