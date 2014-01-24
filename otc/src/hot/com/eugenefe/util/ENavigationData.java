package com.eugenefe.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;


public enum ENavigationData {
//	 Bond("Product","com.eugenefe.entity.Bond")
//	,Etf("Product","com.eugenefe.entity.Etf")
//	,Futures("Product","com.eugenefe.entity.Futures")
//	,FxCash ("Product","com.eugenefe.entity.FxCash")
////	,IrcBucket("com.eugenefe.entity.IrcBucket")
//
//	,Hifive("HiFive","com.eugenefe.entity.Hifive")
//	,HifiveUnderlying("HiFive","com.eugenefe.entity.HifiveUnderlying")
//	,HifiveStrike("HiFive","com.eugenefe.entity.HifiveStrike")
//	,HifiveSwapIr("HiFive","com.eugenefe.entity.HifiveSwapIr")
//	
//	,IrCurve(EMenuGroup.Master, "com.eugenefe.entity.IrCurve")
//	,IntRate(EMenuGroup.Master,"com.eugenefe.entity.IntRate")
//	,Counterparty(EMenuGroup.Master,"com.eugenefe.entity.Counterparty")
//	,Employee(EMenuGroup.Master,"com.eugenefe.entity.Employee")
//	,Bizunit(EMenuGroup.Master,"com.eugenefe.entity.Bizunit")
//	
//	,BondHis(EMenuGroup.History,"com.eugenefe.entity.BondHis")
//	,EtfHis(EMenuGroup.History,"com.eugenefe.entity.EtfHis")
//	,FuturesHis(EMenuGroup.History,"com.eugenefe.entity.FuturesHis")
//	
//	,IntRateHis(EMenuGroup.History,  "com.eugenefe.entity.IntRateHis")
//	,FxRateHis(EMenuGroup.History,"com.eugenefe.entity.FxRateHis")
//	,VolatilityHis(EMenuGroup.History,"com.eugenefe.entity.VolatilityHis")
//	
//	,PositionRisk(EMenuGroup.Position,  "com.eugenefe.entity.PositionRisk")
//	,PositionReturn(EMenuGroup.Position,  "com.eugenefe.entity.PositionReturn")
//	,Portfolio(EMenuGroup.Portfolio,"com.eugenefe.entity.Portfolio")
//	,PortfolioReturn(EMenuGroup.Portfolio,"com.eugenefe.entity.PortfolioReturn")
//	
//	,Pricing(EMenuGroup.Pricing, "com.eugenefe.entity.Pricing")
//	,Pricer(EMenuGroup.Pricing, "com.eugenefe.entity.Pricer")
//	,PricingMethod(EMenuGroup.Pricing, "com.eugenefe.entity.PricingMethod")
//	,PricingUnderlying(EMenuGroup.Pricing, "com.eugenefe.entity.PricingUnderlying")
//	;

	
//	private String group;
//	private String qualifiedName;
//	
//	private ENavigationData(String qualifiedName) {
//		this.qualifiedName = qualifiedName;
//	}
//	private ENavigationData(String group,String qualifiedName) {
//		this.group = group;
//		this.qualifiedName = qualifiedName;
//	}
//	public String getQualifiedName() {
//		return this.qualifiedName;
//	}
//	
//	public String getShortName(){
//		return this.name();
//	}
//	public String getGroup(){
//		return this.group;
//	}
	
	/* Bond(EMenuGroup.Product	,"com.eugenefe.entity.Bond")
	,Etf(EMenuGroup.Product		,"com.eugenefe.entity.Etf")
	,Futures(EMenuGroup.Product	,"com.eugenefe.entity.Futures")
	,FxCash (EMenuGroup.Product,"com.eugenefe.entity.FxCash")
//	,IrcBucket("com.eugenefe.entity.IrcBucket")

	,Hifive(EMenuGroup.HiFive,				"com.eugenefe.entity.Hifive")
	,HifiveUnderlying(EMenuGroup.HiFive,	"com.eugenefe.entity.HifiveUnderlying")
	,HifiveStrike(EMenuGroup.HiFive,		"com.eugenefe.entity.HifiveStrike")
	,HifiveSwapIr(EMenuGroup.HiFive,		"com.eugenefe.entity.HifiveSwapIr")
	
	,IrCurve(EMenuGroup.Master, 		"com.eugenefe.entity.IrCurve")
	,IntRate(EMenuGroup.Master,			"com.eugenefe.entity.IntRate")
	,Counterparty(EMenuGroup.Master,	"com.eugenefe.entity.Counterparty")
	,Employee(EMenuGroup.Master,		"com.eugenefe.entity.Employee")
	,Bizunit(EMenuGroup.Master,			"com.eugenefe.entity.Bizunit")
	
	,BondHis(EMenuGroup.History,		"com.eugenefe.entity.BondHis")
	,EtfHis(EMenuGroup.History,			"com.eugenefe.entity.EtfHis")
	,FuturesHis(EMenuGroup.History,		"com.eugenefe.entity.FuturesHis")
	
	,IntRateHis(EMenuGroup.History,  	"com.eugenefe.entity.IntRateHis")
	,FxRateHis(EMenuGroup.History,		"com.eugenefe.entity.FxRateHis")
	,VolatilityHis(EMenuGroup.History,	"com.eugenefe.entity.VolatilityHis")
	
	,PositionRisk(EMenuGroup.Position,  	"com.eugenefe.entity.PositionRisk")
	,PositionReturn(EMenuGroup.Position,  	"com.eugenefe.entity.PositionReturn")
	,Portfolio(EMenuGroup.Portfolio,		"com.eugenefe.entity.Portfolio")
	,PortfolioReturn(EMenuGroup.Portfolio,	"com.eugenefe.entity.PortfolioReturn")
	
	,Pricing(EMenuGroup.Pricing, 			"com.eugenefe.entity.Pricing")
	,Pricer(EMenuGroup.Pricing, 			"com.eugenefe.entity.Pricer")
	,PricingMethod(EMenuGroup.Pricing, 		"com.eugenefe.entity.PricingMethod")
	,PricingUnderlying(EMenuGroup.Pricing, 	"com.eugenefe.entity.PricingUnderlying")
	;*/
	
	
	 Bond(EMenuGroup.Product		)
		,Etf(EMenuGroup.Product		)
		,Futures(EMenuGroup.Product	)
		,FxCash (EMenuGroup.Product)
//		,IrcBucket("com.eugenefe.entity.IrcBucket")

		,Hifive(EMenuGroup.HiFive)
		,HifiveUnderlying(EMenuGroup.HiFive)
		,HifiveStrike(EMenuGroup.HiFive)
		,HifiveSwapIr(EMenuGroup.HiFive)
		
		,IrCurve(EMenuGroup.Master)
		,IntRate(EMenuGroup.Master)
		,Counterparty(EMenuGroup.Master)
		,Employee(EMenuGroup.Master)
		,Bizunit(EMenuGroup.Master)
		
		,BondHis(EMenuGroup.History)
		,EtfHis(EMenuGroup.History)
		,FuturesHis(EMenuGroup.History)
		
		,IntRateHis(EMenuGroup.History)
		,FxRateHis(EMenuGroup.History)
		,VolatilityHis(EMenuGroup.History)
		
		,PositionRisk(EMenuGroup.Position)
		,PositionReturn(EMenuGroup.Position)
		,Portfolio(EMenuGroup.Portfolio)
		,PortfolioReturn(EMenuGroup.Portfolio)
		
		,Pricing(EMenuGroup.Pricing)
		,Pricer(EMenuGroup.Pricing)
		,PricingMethod(EMenuGroup.Pricing)
		,PricingUnderlying(EMenuGroup.Pricing)
		;
	 
	private final static  String packageName ="com.eugenefe.entity.";
	private EMenuGroup group;
//	private String qualifiedName;
	
//	private ENavigationData(String qualifiedName) {
//		this.qualifiedName = qualifiedName;
//	}
//	private ENavigationData(EMenuGroup group,String qualifiedName) {
//		this.group = group;
//		this.qualifiedName = qualifiedName;
//	}
	private ENavigationData(EMenuGroup group) {
		this.group = group;
	}
	
	public String getQualifiedName() {
//		return this.qualifiedName;
//		return "com.eugenefe.entity."+ this.name();
		return packageName + this.name();
	}
	
	public String getShortName(){
		return this.name();
	}
	public EMenuGroup getGroup(){
		return this.group;
	}
	
	public static List<ENavigationData> getElement(EMenuGroup group){
		List<ENavigationData> rtn = new ArrayList<ENavigationData>();
		for(ENavigationData aa: ENavigationData.values()){
			if(group.equals(aa.getGroup())){
				rtn.add(aa);
			}
		}
		return rtn;
	}
}
