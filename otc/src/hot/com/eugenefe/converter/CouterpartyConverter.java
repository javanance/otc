package com.eugenefe.converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import com.eugenefe.entity.Counterparty;
import com.eugenefe.session.CounterpartyList;
//import javax.faces.convert.Converter;

@Name("counterpartyConverter")
@Scope(ScopeType.SESSION)
@Converter
@BypassInterceptors
//@FacesConverter("counterpartyConverter")
public class CouterpartyConverter implements javax.faces.convert.Converter {

//	@In
	private CounterpartyList counterpartyList;
	public CounterpartyList getCounterpartyList() {
		return counterpartyList;
	}
	public void setCounterpartyList(CounterpartyList counterpartyList) {
		this.counterpartyList = counterpartyList;
	}

	public CouterpartyConverter() {
		counterpartyList = (CounterpartyList)Component.getInstance("counterpartyList");
	}

//	@Create
//	public void create(){
//		counterpartyList = (CounterpartyList)Component.getInstance("counterpartyList");
//	}
	
	public List<Counterparty> completeCpartyObj(String auto){
//		log.info("ObjectChange :#0", auto);
		System.out.println("ObjChange" + auto);
		List<Counterparty> rst = new ArrayList<Counterparty>();
		for(Counterparty aa : counterpartyList.getResultList()){
			if(aa.getCounterpartyId().toUpperCase().contains(auto.toUpperCase())){
				rst.add(aa);
			}
		}
		return rst;
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue) {
		counterpartyList = (CounterpartyList)Component.getInstance("counterpartyList");
		System.out.println("Counterparty Converter : " +counterpartyList.getResultList().size());
		if(submittedValue.trim().equals("")){
			return null;
		}
		else{
			try{
				for(Counterparty aa : counterpartyList.getResultList()){
					if(aa.getCounterpartyId().equals(submittedValue)){
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
			return ((Counterparty)value).getCounterpartyId();
			
		}
	}

	
	 

}
