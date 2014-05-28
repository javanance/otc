package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EPricingObject {
	 DEFAULT ("defalut") 
	,VALUATION ("valuation")
	,SENSITIVITY ("sensitivity")
	,VAR("valueAtRisk")
	,SIMULATION("simulation")

	;

	private String name; 
	private EPricingObject(String name){
		this.name =name;
	}

	public String getName() {
		return name;
	}
	public static List<EPricingObject> getList(){
		return  Arrays.asList(EPricingObject.values());
	}
}
