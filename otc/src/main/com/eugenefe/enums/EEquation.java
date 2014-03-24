package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EEquation {
	 DISTINCT ("distinct" , 0){
			public boolean check(Object aa, String cond1, String cond2){
				return true;
			}
	 }
	,CONTAINS ("contains" , 1){
		public boolean check(Object aa, String cond1, String cond2){
			return String.valueOf(aa).contains(cond1);
		}
	}
	,START_WITH ("startWith", 1){
		public boolean check(Object aa, String cond1, String cond2){
			System.out.println("StratWith:"+ String.valueOf(aa).startsWith(cond1) +":" + String.valueOf(aa));
			if(String.valueOf(aa)==null){
				System.out.println("StratWith1:"+ String.valueOf(aa));
				return false;
			}
			return String.valueOf(aa).startsWith(cond1);
		}
	}
	,END_WITH("endWith", 1){
		public boolean check(Object aa, String cond1, String cond2){
			return String.valueOf(aa).endsWith(cond1);
		}
	}
	,EQUAL("equal", 1){
		public boolean check(Object aa, String cond1, String cond2){
			return String.valueOf(aa).equals(cond1);
		}
	}
	,GE("greaterThanAndEqual", 1){
		public boolean check(Object aa, String cond1, String cond2){
			if(String.valueOf(aa).compareTo(cond1) >=0 ){
				return true;
			}
			return false;
		}
	}
	,GT("greaterThan", 1){
		public boolean check(Object aa, String cond1, String cond2){
			if(String.valueOf(aa).compareTo(cond1) >0 ){
				return true;
			}
			return false;
		}
	}	
	,LE("lessThanAndEqual", 1){
		public boolean check(Object aa, String cond1, String cond2){
			if(String.valueOf(aa).compareTo(cond1) <=0 ){
				return true;
			}
			return false;
		}
	}	
	,LT("lessThan", 1){
		public boolean check(Object aa, String cond1, String cond2){
			if(String.valueOf(aa).compareTo(cond1) < 0 ){
				return true;
			}
			return false;
		}
	}	
	
	,BW("between", 2){
		public boolean check(Object aa, String cond1, String cond2){
			if(String.valueOf(aa).compareTo(cond1) >=0  && String.valueOf(aa).compareTo(cond2) <=0){
				return true;
			}
			return false;
		}
	}	
	,GELT("GTAndLT", 2){
		public boolean check(Object aa, String cond1, String cond2){
			if(String.valueOf(aa).compareTo(cond1) >=0  && String.valueOf(aa).compareTo(cond2) <0){
				return true;
			}
			return false;
		}
	}
	,GTLE("GTandLE", 2){
		public boolean check(Object aa, String cond1, String cond2){
			if(String.valueOf(aa).compareTo(cond1) >0  && String.valueOf(aa).compareTo(cond2) <=0){
				return true;
			}
			return false;
		}
	}
	,GTLT("GTandLT", 2){
		public boolean check(Object aa, String cond1, String cond2){
			if(String.valueOf(aa).compareTo(cond1) >0  && String.valueOf(aa).compareTo(cond2) <0){
				return true;
			}
			return false;
		}
	}
	;


//	private int kisCode;
	private String value;
	private int operandSize;
	private EEquation(String value, int operandSize){
		this.value =value;
		this.operandSize = operandSize;
	}
//	private EEquation(int kisCode, boolean value){
//		this.kisCode =kisCode;
//		this.value = value;
//	}
//	
//	public int getKisCode(){
//		return kisCode;
//	}
//	public boolean getValue(){
//		return value;
//	}
	public String getValue() {
		return value;
	}
	public int getOperandSize(){
		return operandSize;
	}

	public boolean check(Object aa, String cond1, String cond2){
		return false;
	}
}
