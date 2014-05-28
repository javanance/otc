package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EVolType {
	LV ("LOCAL_VOL"),
	SV("SURFACE_VOL"),
	IV("IMPLIED_VOL"),
	HV("HISTORICAL_VOL")
	;


	private String name;
	private EVolType(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	public static List<EVolType> getList(){
		return  Arrays.asList(EVolType.values());
	}
}
