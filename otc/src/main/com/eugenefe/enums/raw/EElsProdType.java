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
	,WRT_ST("WRT(�ֽ�)","03")
	,WRT_OTC("WRT(�Ļ�)","04")
	,OTC("OTC","05")
	,ELS_OTC("����Ļ����ELS","06")
	,DLS_OTC("����Ļ����DLS","07")
	,WRT_OTC_ST("����Ļ����WRT(�ֽ�)","08")
	,WRT_OTC_OTC("����Ļ����WRT(�Ļ�)","09")
	,ELB("ELB(�Ļ����ջ�ä)","10")
	,DLB("DLB(�Ļ����ջ�ä)","11")
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
