package com.eugenefe.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Log;

import com.eugenefe.entity.MarketVariable;

@Name("rfPickListConverter")
@Scope(ScopeType.SESSION)
@Converter
@BypassInterceptors
@FacesConverter(forClass=com.eugenefe.entity.MarketVariable.class)
//@FacesConverter(forClass=com.eugenefe.entity.MarketVariable.class, value="rfPickListConverter")
//@FacesConverter("_rf")
public class RiskFactorPickListConverter implements javax.faces.convert.Converter{
//	@Logger
	private Log log;

//	@In
	private List<MarketVariable> allRiskFactors;
	
	public RiskFactorPickListConverter(){
		System.out.println("Construction RiskFactorPickListConverter");
	}

	public List<MarketVariable> getAllRiskFactors() {
		return allRiskFactors;
	}

	public void setAllRiskFactors(List<MarketVariable> allRiskFactors) {
		this.allRiskFactors = allRiskFactors;
	}

//	@Override
//	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
//		log.info("In the Converter");
//		if (submittedValue.trim().equals("")) {
//            return null;
//        } 
//		else {
//            try {
//            	log.info("In the Converter1");
//                for (MarketVariable aa : allRiskFactors) {
//                    if (aa.getMvId().equals(submittedValue) ) {
//                    	return aa;
//                    }
//                }
//                
//            } catch(NumberFormatException exception) {
//                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Portfolio"));
//            }
//            return null;
//        }
//	}

	@Override
	 public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((MarketVariable) value).getMvId());
        }
	}

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
//		log.info("Get");
		return new MarketVariable("AAAA");
	}

//	@Override
//	 public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
//		return "AAAA";
//	}

}
