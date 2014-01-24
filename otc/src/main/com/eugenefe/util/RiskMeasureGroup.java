package com.eugenefe.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

public enum RiskMeasureGroup {
	 ALL("all") 
	 {
			@Override
			public List<RiskMeasure> getMeasures(){
				return Arrays.asList(RiskMeasure.values());
			}
	}
	,RETURN("return") 
	{
		@Override
		public List<RiskMeasure> getMeasures(){
			List<RiskMeasure> temp = new ArrayList<RiskMeasure>();
			for(RiskMeasure aa : RiskMeasure.values()){
				if(aa.getMeasureGroup().equals(this)){
					temp.add(aa);
				}
			}
			return temp;
		}
	}
	,VALUATION ("valuation") 
	{
		@Override
		public List<RiskMeasure> getMeasures(){
			return null;
		}
	}
	, SENSITIVITY("sensitivity") 
	, VAR("VaR")
	, TRANSACTION("transaction")
	, GREEKS("greeks")
	, IR_SENSITIVITY("irSensitiviy")
	, VOLATILITY("volatility")
	;	
	
	
	public String label;
	
	private RiskMeasureGroup(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
		
	public void setLabel(String label) {
		this.label = label;
	}

	public List<RiskMeasure> getMeasures(){
		return null;
	}
}	


	
	
