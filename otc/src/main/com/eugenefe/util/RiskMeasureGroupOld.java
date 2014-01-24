package com.eugenefe.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum RiskMeasureGroupOld {
	RETURN(RiskMeasure.DAY_RETURN, RiskMeasure.WEEK_RETURN, RiskMeasure.BIWEEK_RETURN, RiskMeasure.MONTH_RETURN) {
		public List<RiskMeasure> getMeasures() {
			List<RiskMeasure> temp = new ArrayList<RiskMeasure>();
			for(RiskMeasure aa : RiskMeasure.values()){
				if(aa.getMeasureGroup().equals(RETURN)){
					temp.add(aa);
				}
			}
			return temp;
		}
	}
	,VALUATION(RiskMeasure.PV, RiskMeasure.CLOSE_PRICE, RiskMeasure.CURRENT_PRICE)
	, SENSITIVITY(RiskMeasure.BETA)
	, VAR(
			RiskMeasure.VAR, RiskMeasure.PERCECNT_MARGINAL_VAR, RiskMeasure.POS_MARGINAL_VAR,
			RiskMeasure.WHOLE_MARGINAL_VAR, RiskMeasure.CONTRIBUTION_VAR, RiskMeasure.EXPECTED_SHORTFALL)
	, TRANSACTION
	, GREEKS(
			RiskMeasure.DELTA, RiskMeasure.GAMMA, RiskMeasure.THETA, RiskMeasure.VEGA, RiskMeasure.RHO,
			RiskMeasure.VANNA)
	, IR_SENSITIVITY(RiskMeasure.AD, RiskMeasure.MD)
	, VOLATILITY(RiskMeasure.VOL, RiskMeasure.IMP_VOL);

	private List<RiskMeasure> subMeasures;
	

	private RiskMeasureGroupOld(RiskMeasure... subMeasures) {
		this.subMeasures = Arrays.asList(subMeasures);
	}

	
	
	public List<RiskMeasure> getSubMeasures() {
		return subMeasures;
	}


	public List<RiskMeasure> getMeasures() {
		return null;
	}
}
