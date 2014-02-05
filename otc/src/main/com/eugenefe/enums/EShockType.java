package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.entity.component.PriceData;
import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EShockType {
	ADD
	{ 	public double getScenarioValue( double base, double shockValue){
			return base + shockValue;
		}
		public PriceData getScenarioPriceData( PriceData base, double shockValue){
//			TODO
			return null;
		}
	}
	,
	ASIS
	{ 	public double getScenarioValue( double base, double shockValue){
			return shockValue;
		}
		public PriceData getScenarioPriceData( PriceData base, double shockValue){
//			TODO
			return null;
		}
	},
	
	MULTI
	{ 	public double getScenarioValue( double base, double shockValue){
			return base * shockValue;
		}
		public PriceData getScenarioPriceData( PriceData base, double shockValue){
//			TODO
			return null;
		}
	};

	
	public double getScenarioValue( double base, double shockValue){
		return base * shockValue;
	}
	
	public PriceData getScenarioPriceData( PriceData base, double shockValue){
		return null;
	}
}
