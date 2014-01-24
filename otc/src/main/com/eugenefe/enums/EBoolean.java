package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EBoolean {
	Y ("Y",true),
	N ("N",false)
	;

	private String name;
	private boolean value;
	private EBoolean(String name, boolean value){
		this.name = name;
		this.value = value;
	}
	
	public String getName(){
		return this.name;
	}
	public boolean getValue(){
		return value;
	}
}
