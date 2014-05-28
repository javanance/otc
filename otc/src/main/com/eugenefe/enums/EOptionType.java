package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eugenefe.enums.raw.EElsOptionType;
import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EOptionType {
	 CALL(1, Arrays.asList("01","02","05"))
	,PUT(2, Arrays.asList("06","17","23"))
	,Bermuda(3, Arrays.asList(""))
	;


	private int kisCode;
//	private String intCode;
	private List<String> intCodeList;
//	private String[] intCodeArray;
	
	private EOptionType(int kisCode,  List<String> intCodeList){
		this.kisCode =kisCode;
		this.intCodeList = intCodeList;
	}
//	private EOptionType(int kisCode, String intCode , List<String> intCodeList){
//		this.kisCode =kisCode;
//		this.intCode = intCode;
//		this.intCodeList = intCodeList;
//	}
//	private EOptionType(int kisCode, String intCode,String[] intCodeArray) {
//		this.kisCode = kisCode;
//		this.intCode = intCode;
//		this.intCodeArray = intCodeArray;
//	}
	public int getKisCode(){
		return kisCode;
	}
	
//	public String getIntCode() {
//		return intCode;
//	}

	public List<String> getIntCodeList() {
		return intCodeList;
	}

	public static List<EOptionType> getList(){
		return Arrays.asList(EOptionType.values());
	}
	
//	public static EOptionType getEnum(String intCode){
//		for(EOptionType aa : EOptionType.values()){
//			if( aa.getIntCode().equals(intCode)){
//				return aa;
//			}
//		}
//		return null;
//	}
	
	public static EOptionType getEnum(String intCode){
		for(EOptionType aa : EOptionType.values()){
			if( aa.getIntCodeList().contains(intCode)){
				return aa;
			}
		}
		return null;
	}
	
}
