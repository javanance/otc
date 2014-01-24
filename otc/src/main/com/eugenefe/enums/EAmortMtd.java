package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.util.CashFlow;
import com.eugenefe.util.FnCalendar;

//import com.eugene.element.FnCalendar;
//import com.eugene.entity.CashFlow;

public enum EAmortMtd {
	BULLET,
	EQUAL_AMORT,
	EQUAL_AMORT_FIRST,
	EQUAL_AMORT_LAST,
	ANNUITY;


	public List<CashFlow> getPrinCashFlow(String fnProdId, FnCalendar baseDate,
			FnCalendar issueDate,FnCalendar prinStartDate, FnCalendar maturityDate, double prinAmt){
		List<CashFlow> cashFlows = new ArrayList<CashFlow>();
		CashFlow cf = new CashFlow(baseDate,fnProdId);
		cashFlows.add(cf);
		
		return cashFlows;
	}
}
