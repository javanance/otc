package com.eugenefe.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primefaces.model.SelectableDataModel;

import com.eugenefe.entity.IEntity;
import com.eugenefe.entity.IMarketVariableHis;
import com.eugenefe.entity.IrCurve;
import com.eugenefe.enums.EMaturity;

public  class DynamicTableModel<E extends IEntity>  {
	private E data;
	private Map<String, String> contentMap;
	
	public DynamicTableModel(E data, Map<String, String> contentMap) {  
        this.data = data;  
        this.contentMap = contentMap;  
    }
	
	public E getData() {
		return data;
	}
	public void setData(E data) {
		this.data = data;
	}
	public Map<String, String> getContentMap() {
		return contentMap;
	}
	public void setContentMap(Map<String, String> contentMap) {
		this.contentMap = contentMap;
	}
	
//	@Override
//	public boolean equals(PivotTableModel<T, E> other){
//		return this.data.equals(other.data);
//	}
}
