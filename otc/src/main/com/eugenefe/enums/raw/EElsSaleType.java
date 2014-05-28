package com.eugenefe.enums.raw;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EElsSaleType {
	 PUB_BR("공모(지점)","01")
	,PUB_SEC("공모(기관)","02")
	,PRI_BR("사모(지점)","03")
	,PRI_ELF("사모(공모ELF)","04")
	,PRI_BR_ELF("사모(지점사모ELF)","05")
	,PRI_TR_ELF("사모(투신ELF)","06")
	,PRI_BANK("사모(은행)","07")
	,PRI_INS("사모(보험)","08")
	,PRI_CORP("사모(기타법인)","09")
	,PUB_RETIRE("공모(퇴직연금)","10")
	,PUB_RETIRE_OTHER("공모(퇴직연금-타사)","11")
	,PRI_PROP_ELS("사모(프로포즈ELS)","12")
	;
	private String value;
	private String code;
	  
	private EElsSaleType(String value, String code){
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
