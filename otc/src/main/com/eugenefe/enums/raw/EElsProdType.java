package com.eugenefe.enums.raw;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EElsProdType {
	 ELS("ELS","01")
	,DLS("DLS","02")
	,WRT_ST("WRT(주식)","03")
	,WRT_OTC("WRT(파생)","04")
	,OTC("OTC","05")
	,ELS_OTC("장외파생계약ELS","06")
	,DLS_OTC("장외파생계약DLS","07")
	,WRT_OTC_ST("장외파생계약WRT(주식)","08")
	,WRT_OTC_OTC("장외파생계약WRT(파생)","09")
	,ELB("ELB(파생결합사채)","10")
	,DLB("DLB(파생결합사채)","11")
	;
	
	private String value;
	private String code;
	  
	private EElsProdType(String value, String code){
		this.value = value;
		this.code =code;
	}

	public String getValue() {
		return value;
	}

	public String getCode() {
		return code;
	}
}
