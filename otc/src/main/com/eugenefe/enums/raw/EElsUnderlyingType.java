package com.eugenefe.enums.raw;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EElsUnderlyingType {
	 INDEX("주가지수","01")
	,WTI("GS WTI ER INDEX","02")
	,USDKRW("원달러 현물 환율","03")
	,FX("환율","04")
	,CD91("91일CD금리","05")
	,IR("금리","06")
	,STOCK("개별주식","07")
	,COMMODITY("일반상품","08")
	,CREDIT("신용","09")
	,HYBRID("Hybrid","10")
	,FOREIGN_STOCK("해외주식","11")
	,FOREIGN_INDEX("해외지수","12")
	,MULIT_EX_STOCK("해외+국내","13")
	,INDEX_STOCK("지수+주식","14")
	,KOSDAQ50("KOSDAQ50","91")
	,STOCK2("개별주식","92")
	,COMMODITY2("일반상품","93")
	,CREDIT2("신용","94")
	,HIBRID2("Hybrid","95")
	,FOREIGN_STOCK2("해외주식","96")
	,FOREIGN_INDEX2("해외지수","97")
	,MULIT_EX_STOCK2("해외+국내","98")
	,INDEX_STOCK2("국내+주식","99")
	;
	private String value;
	private String code;
	  
	private EElsUnderlyingType(String value, String code){
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
