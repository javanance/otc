package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EBarrierHit {
	Y (0, true),
	N (1, false)
	;


	private int kisCode;
	private boolean value;
	private EBarrierHit(int kisCode, boolean value){
		this.kisCode =kisCode;
		this.value = value;
	}
	
	public int getKisCode(){
		return kisCode;
	}
	public boolean getValue(){
		return value;
	}
}
