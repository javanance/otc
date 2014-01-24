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

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.Position;
import com.eugenefe.entity.PositionReturn;
import com.eugenefe.entity.VcvMatrixHis;

//@Name("lazyModelVcvHis")
//@Scope(ScopeType.CONVERSATION)
public class LazyModelVcvHis  extends LazyDataModel<VcvMatrixHis>{
	
//	@Logger
//	private Log log;
	
	private List<VcvMatrixHis> datasource;  
    
    
	public LazyModelVcvHis(List<VcvMatrixHis> datasource) {  
        this.datasource = datasource;  
    }  
      
    @Override  
    public VcvMatrixHis getRowData(String rowKey) {  
        for(VcvMatrixHis aa : datasource) {  
            if(aa.getId().equals(rowKey))  
                return aa;  
        }  
        return null;  
    }  
  
    @Override  
    public Object getRowKey(VcvMatrixHis vcvHis) {  
        return vcvHis.getId();  
    }  
    
    
    @Override  
    public List<VcvMatrixHis> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {  
        List<VcvMatrixHis> data = new ArrayList<VcvMatrixHis>();  
                
        //filter  
        System.out.println("Before in the filter Market :"+ filters.keySet().size());
        for(VcvMatrixHis aa : datasource) {  
//        	System.out.println("In the Loop");
        	boolean match = true;  
            for(String it: filters.keySet()){	
            	try {  
                    String filterProperty = it;
                    String filterValue = filters.get(filterProperty);  
                    String fieldValue = String.valueOf(aa.getClass().getField(filterProperty).get(aa));  
  
//                    if(filterValue == null || fieldValue.startsWith(filterValue)) {
                    if(filterValue == null || fieldValue.contains(filterValue)) {	
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
        //sort  
//        System.out.println("Before in the sort");
        if(sortField != null) {  
//        	System.out.println("in the sort1 :"+ data.size()+sortField +":" + sortOrder.toString());
        	Collections.sort(data, new LazySorterVcvMatrixHis(sortField, sortOrder));  
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
