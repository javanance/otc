package com.eugenefe.converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.derby.tools.sysinfo;
import org.primefaces.model.SortOrder;

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.Position;
import com.eugenefe.entity.PositionReturn;
import com.eugenefe.entity.VcvMatrixHis;
import com.eugenefe.entity.VolatilityHis;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

public class LazySorterDynamicModel implements Comparator<Map<String,String>> {

    private String sortField;
    private SortOrder sortOrder;
    
    
    public LazySorterDynamicModel(String sortField, SortOrder sortOrder) {
    	this.sortField =sortField;
    	this.sortOrder = sortOrder;
//        sortFieldList.add(sortField);
//        sortOrderList.add(sortOrder);
    }
    
//    private List<String> sortFieldList = new ArrayList<String>();
//    private List<SortOrder> sortOrderList = new ArrayList<SortOrder>();
//    public LazySorterVolatilityHis(List<String>  sortFieldList, List<SortOrder> sortOrderList) {
//        this.sortFieldList = sortFieldList;
//        this.sortOrderList = sortOrderList;
//    }
    
    public int compare(Map<String,String> map1, Map<String,String> map2) {
    	System.out.println("In the LazySortDynamicModel");
    	int value = map1.get(sortField).compareTo(map2.get(sortField));
		return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
     }
}
