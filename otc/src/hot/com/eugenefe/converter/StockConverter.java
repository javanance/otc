package com.eugenefe.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import com.eugenefe.entity.Stock;
import com.eugenefe.session.StockList;

@Name("stockConverter")
@Scope(ScopeType.SESSION)
@Converter
@BypassInterceptors
public class StockConverter implements javax.faces.convert.Converter, Serializable {

	@In("#{stockList}")
	private StockList stockList;
	
	
	public StockConverter() {
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue) {
		if(submittedValue.trim().equals("")){
			return null;
		}
		else{
			try{
				for(Stock aa : stockList.getResultList()){
					if(aa.getStockId().equals(submittedValue)){
						return aa;
					}
				}
			}catch(Exception e){
				
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if(value==null || value.equals("")){
			return "";
		}
		else{
			return ((Stock)value).getStockId();
		}
	}

	
	 

}
