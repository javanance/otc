package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EExoticOptionType {
	SingleBarrier (0),
	DoubleBarrier (1),
	Digital(2),
	Asian(3),
	PlainVanilla(4),
	LookBack(5),
	Compound(6),
	Chooser(7)
	;


	private int kisCode;
	private EExoticOptionType(int kisCode){
		this.kisCode =kisCode;
	}
	
	public int getKisCode(){
		return kisCode;
	}
	
	public static List<EExoticOptionType> getList(){
		return Arrays.asList(EExoticOptionType.values());
	}
}
