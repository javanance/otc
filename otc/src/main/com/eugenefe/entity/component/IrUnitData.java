package com.eugenefe.entity.component;

import java.util.Date;

import com.eugenefe.enums.ECompound;
import com.eugenefe.enums.EDayCount;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.util.EColumnType;

public class IrUnitData {
	private Date stDate;
	private Date endDate; 
	private EMaturity maturity;
	private ECompound compound;
	private EDayCount dayCount;
	private double intRate;
}
