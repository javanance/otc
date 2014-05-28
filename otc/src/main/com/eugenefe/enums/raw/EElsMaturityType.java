package com.eugenefe.enums.raw;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EElsMaturityType {
	M01 ("1개월","08"),
	M02 ("2개월", "09"),
	M03	("3개월이내", "01"),
	M06 ("6개월이내", "02"),
	M18 ("18개월", "10"),
	Y01	("1년이내", "03"),
	Y02	("2년이내", "04"),
	Y03 ("3년이내", "05"),
	Y05 ("5년이내 ", "06"),
	Y05$("5년초과 ", "07"),
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
