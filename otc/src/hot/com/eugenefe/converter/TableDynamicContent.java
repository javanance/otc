package com.eugenefe.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TableDynamicContent  {
	private String stringValue;
	private double numberValue;
	
	public TableDynamicContent(String stringValue, double numberValue) {
		super();
		this.stringValue = stringValue;
		this.numberValue = numberValue;
	}
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	public double getNumberValue() {
		return numberValue;
	}
	public void setNumberValue(double numberValue) {
		this.numberValue = numberValue;
	}
}
