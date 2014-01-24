package com.eugenefe.util;

import java.util.ArrayList;
import java.util.List;

public enum RiskMeasure {
	PV 
	(	"presValue"
		,RiskMeasureGroup.VALUATION
	)
	,CLOSE_PRICE
	(	"closePrice"
		,RiskMeasureGroup.VALUATION
	)
	,CURRENT_PRICE
	(	"currentPrice"
		,RiskMeasureGroup.VALUATION
	)
	,VOLUME
	(	"volume"
		,RiskMeasureGroup.TRANSACTION
	)
	,AD
	(	"adjDuration"
		,RiskMeasureGroup.IR_SENSITIVITY
	)
	,MD
	(	"mdDuration"
		,RiskMeasureGroup.IR_SENSITIVITY	
	)
	,BETA
	(   "stockBeta"
		,RiskMeasureGroup.SENSITIVITY
	)
	,VOL
	(	"volatility"
		,RiskMeasureGroup.VOLATILITY
	)
	,IMP_VOL
	(	"impliedVolatility"
		,RiskMeasureGroup.VOLATILITY
	)
	,VAR
	(	"var"
		,RiskMeasureGroup.VAR
	)
	,PERCECNT_MARGINAL_VAR
	(	"percentMarginalVar"
		,RiskMeasureGroup.VAR
	)
	,POS_MARGINAL_VAR
	(	"positionMarginalVar"
		,RiskMeasureGroup.VAR
	)
	,WHOLE_MARGINAL_VAR
	(	"wholeMarginalVar"
		,RiskMeasureGroup.VAR
	)
	,CONTRIBUTION_VAR
	(	"contributionVar"
		,RiskMeasureGroup.VAR
	)
	,EXPECTED_SHORTFALL
	(	"expectedShortfall"
		,RiskMeasureGroup.VAR
	)
	,IR_VAR
	(	"irVar"
		,RiskMeasureGroup.VAR
	)
	,EQ_VAR
	(	"eqVar"
		,RiskMeasureGroup.VAR
	)
	,FX_VAR
	(	"fxVar"
		,RiskMeasureGroup.VAR
	)
	,CO_VAR
	(	"coVar"
		,RiskMeasureGroup.VAR
	)
	,DELTA
	(	"delta"
		,RiskMeasureGroup.GREEKS
	)
	,GAMMA
	(	"gamma"
		,RiskMeasureGroup.GREEKS
	)
	,THETA
	(	"theta"
		,RiskMeasureGroup.GREEKS
	)
	,VEGA
	(	"vega"
		,RiskMeasureGroup.GREEKS
	)
	,RHO
	(	"rho"
		,RiskMeasureGroup.GREEKS
	)
	,VANNA
	(	"vanna"
		,RiskMeasureGroup.GREEKS
	)	
	,DAY_RETURN
	(	"dayReturn"
		,RiskMeasureGroup.RETURN
	)
	,WEEK_RETURN
	(	"weekReturn"
		,RiskMeasureGroup.RETURN
	)
	,BIWEEK_RETURN
	(	"biweekReturn"
		,RiskMeasureGroup.RETURN
	)
	,MONTH_RETURN
	(	"monthReturn"
		,RiskMeasureGroup.RETURN
	)
	,QURT_RETURN
	(	"qurtReturn"
		,RiskMeasureGroup.RETURN
	)
	,FISCAL_RETURN
	(	"fiscalReturn"
		,RiskMeasureGroup.RETURN
	)
	,ANNU_RETURN
	(	"annualReturn"
		,RiskMeasureGroup.RETURN
	)
	;
	
	public final String field;
	public final RiskMeasureGroup measureGroup;
	
	private RiskMeasure(String field, RiskMeasureGroup measureGroup) {
		this.field = field;
		this.measureGroup = measureGroup;
	}
	
	
	public String getField() {
		return field;
	}
	
	public RiskMeasureGroup getMeasureGroup() {
		return measureGroup;
	}
	
	
	public static List<RiskMeasure> getMeasures(RiskMeasureGroup measureGroup){
		List<RiskMeasure> temp =new ArrayList<RiskMeasure>();
		for(RiskMeasure aa: values()){
			if(measureGroup.equals(aa.getMeasureGroup())){
				temp.add(aa);
			}
		}
		return temp;
	}
	
	

}
