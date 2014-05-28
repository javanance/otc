package com.eugenefe.enums.raw;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EElsSaleType {
	 PUB_BR("����(����)","01")
	,PUB_SEC("����(���)","02")
	,PRI_BR("���(����)","03")
	,PRI_ELF("���(����ELF)","04")
	,PRI_BR_ELF("���(�������ELF)","05")
	,PRI_TR_ELF("���(����ELF)","06")
	,PRI_BANK("���(����)","07")
	,PRI_INS("���(����)","08")
	,PRI_CORP("���(��Ÿ����)","09")
	,PUB_RETIRE("����(��������)","10")
	,PUB_RETIRE_OTHER("����(��������-Ÿ��)","11")
	,PRI_PROP_ELS("���(��������ELS)","12")
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
