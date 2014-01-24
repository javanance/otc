package com.eugenefe.converter;

import java.lang.reflect.Field;
import java.util.Comparator;

import org.primefaces.model.SortOrder;

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.Position;
import com.eugenefe.entity.PositionReturn;

public class LazySorterMarketVariable implements Comparator<MarketVariable> {

    private String sortField;
    
    private SortOrder sortOrder;
    
    public LazySorterMarketVariable(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(MarketVariable mv1, MarketVariable mv2) {
    	
    	try {
    		Field field1 = MarketVariable.class.getDeclaredField(this.sortField);
    		Field field2 = MarketVariable.class.getDeclaredField(this.sortField);
    		field1.setAccessible(true);
    		field2.setAccessible(true);
    		Object value1 = field1.get(mv1);
    		Object value2 = field2.get(mv2);
//        	Object value1 = MarketVariable.class.getField(this.sortField).get(mv1);
//            Object value2 = MarketVariable.class.getField(this.sortField).get(mv2);

            int value = ((Comparable)value1).compareTo(value2);
            
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
