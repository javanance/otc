package com.eugenefe.converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.derby.tools.sysinfo;
import org.primefaces.model.SortOrder;

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.Position;
import com.eugenefe.entity.PositionReturn;
import com.eugenefe.entity.VcvMatrixHis;
import com.eugenefe.entity.VolatilityHis;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

public class LazySorterVolatilityHis implements Comparator<VolatilityHis> {

    private String sortField;
    private SortOrder sortOrder;
    
    
    public LazySorterVolatilityHis(String sortField, SortOrder sortOrder) {
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
    
    public int compare(VolatilityHis pos1, VolatilityHis pos2) {
    	try {
    		Object navi1 = pos1;
            Object navi2 = pos2;
            
			for (String sortProperty : this.sortField.split("\\.")) {
				navi1 = navi1.getClass().getDeclaredField(sortProperty).get(navi1); 
				navi2 = navi2.getClass().getDeclaredField(sortProperty).get(navi2);
			}
//			System.out.println("Sort Navi : "+navi1 +"_"+navi2);
			int value = ((Comparable)navi1).compareTo(navi2);
			
//    	Object value1 = VolatilityHis.class.getField(this.sortField).get(pos1);
//    	Object value2 = VolatilityHis.class.getField(this.sortField).get(pos2);
//		int value = ((Comparable)value1).compareTo(value2);
            
			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
