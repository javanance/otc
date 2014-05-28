package com.eugenefe.enums.raw;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EElsOptionType {
	  KO_CALL("Knock Out Call", "KO", "CALL", "01")
	, CALL_SPREAD("Call Spread", "SP","CALL", "02")
	, DIGITAL("Digital", "DG", "NONE", "03")
	, OTHER("±‚≈∏", "OT", "NONE","04")
	, CALL(	"Call", "PV", "CALL", "05")
	, PUT("Put", "PV",  "PUT", "06")
	, RANGE_ACCRUAL("Range Accrual", "RA", "NONE","08")
	, SPREAD_ACCRUAL("Spread Accrual","RA","NONE","09")
	, AUTO_CALL("Auto-Call", "SD","NONE","10")
	, AUTO_CALL_KO("Auto-Call Knock Out", "SD","NONE","11")
	, AUTO_CALL_BARRIER_SD(	"Auto-Call Barrier Step-Down", "SD","NONE","12")
	, AUTO_CALL_SD_KO("Auto-Call Barrier Step-Down Knock Out", "SD","NONE","13")
	, AUTO_CALL_CP_SU("Auto-Call Coupon Step-Up", "SD", "NONE","14")
	, AUTO_CALL_CLIQUET("Auto-Call Cliquet", "CQ","NONE","15")
	, CLIQUER("Cliquet", "CQ","NONE","16")
	, KO_PUT("Knock Out Put", "KO","PUT","17")
	, MTHLY_CP_BARRIER_SD("Monthly Coupon Auto Call Barrier Step Down", "SD","NONE","18")
	, KO_CALL_PUT("Knock Out Call and Put", "KO", "CP","19")
	, RETURN_SWQP("Return Swap", "RS","NONE","20")
	, CLN("Credit Linked Note", "CR", "NONE","21")
	, AUTO_CALL_BARRIER_SU("Auto Call Barrier Step Up", "SD","NONE","22")
	, PUT_SPREAD("Put Spread", "SP","PUT","23")
	, MTHLY_CP_BARRIER_SU("Monthly Coupon Auto Call Barrier Step Up", "SD","NONE","24")
	, AVG_BARRIER_SU("Average Auto-Call Barrier Step-down",	"SD","NONE","25")
	, AUTO_CALL_BARRIER_CH("Auto-Call Barrier Change", "SD","NONE","26")
	;
	private String desc;
	private String optGroup;
	private String optType;
	private String intCode;

	private EElsOptionType(String desc, String optGroup,String optType, String code) {
		this.desc = desc;
		this.optGroup = optGroup;
		this.optType = optType;
		this.intCode = code;
	}

	public String getDesc() {
		return desc;
	}
	public String getOptGroup() {
		return optGroup;
	}
	
	public String getOptType() {
		return optType;
	}
	public String getIntCode() {
		return intCode;
	}
	
	public static EElsOptionType getEnum(String intCode){
		for(EElsOptionType aa : EElsOptionType.values()){
			if( aa.getIntCode().equals(intCode)){
				return aa;
			}
		}
		return null;
	}
}
