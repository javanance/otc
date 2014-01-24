package com.eugenefe.converter;

import java.lang.reflect.Field;
import java.util.Comparator;

import org.primefaces.model.SortOrder;

import com.eugenefe.entity.Position;
import com.eugenefe.entity.PositionReturn;

public class LazySorterPosition implements Comparator<Position> {

    private String sortField;
    
    private SortOrder sortOrder;
    
    public LazySorterPosition(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(Position pos1, Position pos2) {
    	
    	try {
        	Object value1 = Position.class.getField(this.sortField).get(pos1);
            Object value2 = Position.class.getField(this.sortField).get(pos2);

            int value = ((Comparable)value1).compareTo(value2);
            
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
