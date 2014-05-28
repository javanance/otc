package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EDigitalType {
	 CashOrNothing(1),
	 AssetOrNothing(2)
	;


	private int kisCode;
	private EDigitalType(int kisCode){
		this.kisCode =kisCode;
	}
	
	public int getKisCode(){
		return kisCode;
	}
	
	public static List<EDigitalType> getList(){
		return Arrays.asList(EDigitalType.values());
	}
}
