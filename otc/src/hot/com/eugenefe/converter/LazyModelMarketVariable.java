package com.eugenefe.converter;

import java.lang.reflect.Field;
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

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.Position;
import com.eugenefe.entity.PositionReturn;

//@Name("lazyModelMarketVariable")
//@Scope(ScopeType.CONVERSATION)
public class LazyModelMarketVariable  extends LazyDataModel<MarketVariable>{
	
//	@Logger
//	private Log log;
	
	private List<MarketVariable> datasource;  
    
    
	public LazyModelMarketVariable(List<MarketVariable> datasource) {  
        this.datasource = datasource;  
    }  
      
    @Override  
    public MarketVariable getRowData(String rowKey) {  
        for(MarketVariable aa : datasource) {  
            if(aa.getMvId().equals(rowKey))  
                return aa;  
        }  
        return null;  
    }  
  
    @Override  
    public Object getRowKey(MarketVariable marketVariable) {  
        return marketVariable.getMvId();  
    }  
    
    
    @Override  
    public List<MarketVariable> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {  
        List<MarketVariable> data = new ArrayList<MarketVariable>();  
                
        //filter  
//        System.out.println("Before in the filter Market :"+ filters.keySet().size());
        for(MarketVariable aa : datasource) {  
//        	System.out.println("In the Loop");
        	boolean match = true;
        	
            for(String it: filters.keySet()){	
            	try {  
//                    String filterProperty = it;
                    Field filterField = aa.getClass().getDeclaredField(it);
                    filterField.setAccessible(true);
                    
                    String fieldValue = String.valueOf(filterField.get(aa));
                    String filterValue = filters.get(it);  
//                    String fieldValue = String.valueOf(aa.getClass().getField(filterProperty).get(aa));  
  
//                    System.out.println("LazyFilter:" + filterValue +"_" + fieldValue);
                    
                    if(filterValue == null || fieldValue.toLowerCase().contains(filterValue.toLowerCase())) {	
                        match = true;  
//                        System.out.println("LazyFilter:match" + filterValue +"_" + fieldValue);
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
        //sort  
//        System.out.println("Before in the sort");
        if(sortField != null) {  
//        	System.out.println("in the sort1 :"+ data.size()+sortField +":" + sortOrder.toString());
        	Collections.sort(data, new LazySorterMarketVariable(sortField, sortOrder));  
//            System.out.println("in the sort2");
        }  
//        System.out.println("After in the sort");
        //rowCount  
        int dataSize = data.size();  
        this.setRowCount(dataSize);  
  
        //paginate  
        if(dataSize > pageSize) {  
//        	System.out.println("in the pagination" + dataSize);
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
    
    /*@Override
    public void setRowIndex(int rowIndex) {
           if (getPageSize() == 0) {
                rowIndex = -11;
            }
            super.setRowIndex(rowIndex);
    }*/
    @Override
    public void setRowIndex(int rowIndex) {
        /*
         * The following is in ancestor (LazyDataModel):
         * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
         */
        if (rowIndex == -1 || getPageSize() == 0) {
//        	System.out.println("Row Index : " + rowIndex +":"+ getPageSize());
            super.setRowIndex(-1);
        }
        else{
//        	System.out.println("Row Index1 : " + rowIndex +":"+ getPageSize());
            super.setRowIndex(rowIndex % getPageSize());
        }    
    }
}
