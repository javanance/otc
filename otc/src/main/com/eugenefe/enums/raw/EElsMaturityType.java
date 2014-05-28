package com.eugenefe.enums.raw;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EElsMaturityType {
	M01 ("1����","08"),
	M02 ("2����", "09"),
	M03	("3�����̳�", "01"),
	M06 ("6�����̳�", "02"),
	M18 ("18����", "10"),
	Y01	("1���̳�", "03"),
	Y02	("2���̳�", "04"),
	Y03 ("3���̳�", "05"),
	Y05 ("5���̳� ", "06"),
	Y05$("5���ʰ� ", "07"),
	;
	
	private String value;
	private String code;
	  
	private EElsMaturityType(String value, String code){
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
