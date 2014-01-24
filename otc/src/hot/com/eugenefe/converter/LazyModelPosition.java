package com.eugenefe.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.eugenefe.entity.Position;
import com.eugenefe.entity.PositionReturn;

//@Name("lazyModelPosition")
//@Scope(ScopeType.CONVERSATION)
public class LazyModelPosition  extends LazyDataModel<Position>{
	
//	@Logger
//	private Log log;
	
	private List<Position> datasource;  
    
    
	public LazyModelPosition(List<Position> datasource) {  
        this.datasource = datasource;  
    }  
      
    @Override  
    public Position getRowData(String rowKey) {  
        for(Position aa : datasource) {  
            if(aa.getPosId().equals(rowKey))  
                return aa;  
        }  
  
        return null;  
    }  
  
    @Override  
    public Object getRowKey(Position posReturn) {  
        return posReturn.getPosId();  
    }  
  
    @Override  
    public List<Position> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {  
        List<Position> data = new ArrayList<Position>();  
                
        //filter  
        System.out.println("Before in the filter Position :"+ filters.keySet().size());
        for(Position aa : datasource) {  
            boolean match = true;  
            for(String it: filters.keySet()){	
            	try {  
                    String filterProperty = it;
                    String filterValue = filters.get(filterProperty);  
                    String fieldValue = String.valueOf(aa.getClass().getField(filterProperty).get(aa));  
  
                    if(filterValue == null || fieldValue.startsWith(filterValue)) {  
                        match = true;  
                    }  
                    else {  
                        match = false;  
                        break;  
                    }  
                } catch(Exception e) {  
                    match = false;  
                }   
            }  
  
            if(match) {  
                data.add(aa);  
            }  
        }  
        System.out.println("Before in the sort");
        //sort  
        if(sortField != null) {  
        	System.out.println("in the sort1 :"+ data.size()+sortField +":" + sortOrder.toString());
        	Collections.sort(data, new LazySorterPosition(sortField, sortOrder));  
            System.out.println("in the sort2");
        }  
        System.out.println("After in the sort");
        //rowCount  
        int dataSize = data.size();  
        this.setRowCount(dataSize);  
  
        //paginate  
        if(dataSize > pageSize) {  
        	System.out.println("in the pagination");
            try {  
                return data.subList(first, first + pageSize);  
            }  
            catch(IndexOutOfBoundsException e) {  
                return data.subList(first, first + (dataSize % pageSize));  
            }  
        }  
        else {  
            return data;  
        }  
    }

}
