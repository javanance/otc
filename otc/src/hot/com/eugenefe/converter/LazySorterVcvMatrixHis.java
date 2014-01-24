package com.eugenefe.converter;

import java.lang.reflect.Field;
import java.util.Comparator;

import org.primefaces.model.SortOrder;

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.Position;
import com.eugenefe.entity.PositionReturn;
import com.eugenefe.entity.VcvMatrixHis;

public class LazySorterVcvMatrixHis implements Comparator<VcvMatrixHis> {

    private String sortField;
    
    private SortOrder sortOrder;
    
    public LazySorterVcvMatrixHis(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(VcvMatrixHis pos1, VcvMatrixHis pos2) {
    	
    	try {
        	Object value1 = VcvMatrixHis.class.getField(this.sortField).get(pos1);
            Object value2 = VcvMatrixHis.class.getField(this.sortField).get(pos2);

            int value = ((Comparable)value1).compareTo(value2);
            
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
