package com.eugenefe.enums.raw;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EElsUnderlyingType {
	 INDEX("�ְ�����","01")
	,WTI("GS WTI ER INDEX","02")
	,USDKRW("���޷� ���� ȯ��","03")
	,FX("ȯ��","04")
	,CD91("91��CD�ݸ�","05")
	,IR("�ݸ�","06")
	,STOCK("�����ֽ�","07")
	,COMMODITY("�Ϲݻ�ǰ","08")
	,CREDIT("�ſ�","09")
	,HYBRID("Hybrid","10")
	,FOREIGN_STOCK("�ؿ��ֽ�","11")
	,FOREIGN_INDEX("�ؿ�����","12")
	,MULIT_EX_STOCK("�ؿ�+����","13")
	,INDEX_STOCK("����+�ֽ�","14")
	,KOSDAQ50("KOSDAQ50","91")
	,STOCK2("�����ֽ�","92")
	,COMMODITY2("�Ϲݻ�ǰ","93")
	,CREDIT2("�ſ�","94")
	,HIBRID2("Hybrid","95")
	,FOREIGN_STOCK2("�ؿ��ֽ�","96")
	,FOREIGN_INDEX2("�ؿ�����","97")
	,MULIT_EX_STOCK2("�ؿ�+����","98")
	,INDEX_STOCK2("����+�ֽ�","99")
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
