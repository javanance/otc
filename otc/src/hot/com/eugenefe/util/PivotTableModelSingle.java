package com.eugenefe.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primefaces.model.SelectableDataModel;

import com.eugenefe.entity.IrCurve;
import com.eugenefe.enums.EMaturity;

public  class PivotTableModelSingle<T>  {
	private T data;
	private Map<T, T> contentMap;
	
	public PivotTableModelSingle(T data, Map<T, T> contentMap) {  
        this.data = data;  
        this.contentMap = contentMap;  
    }
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Map<T, T> getContentMap() {
		return contentMap;
	}
	public void setContentMap(Map<T, T> contentMap) {
		this.contentMap = contentMap;
	}
	
//	@Override
//	public boolean equals(PivotTableModel<T, E> other){
//		return this.data.equals(other.data);
//	}
}
