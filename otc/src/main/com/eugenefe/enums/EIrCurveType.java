package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EIrCurveType {
	RF ("RISKFREE"),
	IRS("IRS"),
	CR("CREDIT_CURVE")
	;

	private String name;
	private EIrCurveType(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public static List<EIrCurveType> getList(){
		return  Arrays.asList(EIrCurveType.values());
	}
}
