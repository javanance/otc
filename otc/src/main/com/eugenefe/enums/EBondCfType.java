package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EBondCfType {
	BULLET,
	DISCOUNT,
	FIXED,
	FLOATING,
	FX_AMORT,
	FL_AMORT;
	
	public static List<EBondCfType> getList(){
		return  Arrays.asList(EBondCfType.values());
	}
}
