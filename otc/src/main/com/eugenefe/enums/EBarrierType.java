package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EBarrierType {
	 UpAndOut(1, "01")
	 ,UpAndIn(2, "10")
	 ,DownAndOut(3, "17")
	 ,DownAndIn(4, "10")
	 

	;


	private int kisCode;
	private String intCode;
	private EBarrierType(int kisCode, String intCode){
		this.kisCode =kisCode;
		this.intCode = intCode;
	}
	
	public int getKisCode(){
		return kisCode;
	}
	
	public String getIntCode() {
		return intCode;
	}

	public static List<EBarrierType> getList(){
		return Arrays.asList(EBarrierType.values());
	}
	
	public static EBarrierType getEnum(String intCode){
		for(EBarrierType aa : EBarrierType.values()){
			if( aa.getIntCode().equals(intCode)){
//			if( aa.getIntCodeList().contains(intCode)){	
				return aa;
			}
		}
		return null;
	}	
}
