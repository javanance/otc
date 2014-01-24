package com.eugenefe.converter;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Log;

import com.eugenefe.controller.ProductSelectAction;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.PortfolioReturn;
import com.eugenefe.session.PortfolioReturnBssdList;

@Name("marketVariableConverterSeam")
//@Scope(ScopeType.CONVERSATION)
@Converter
@BypassInterceptors
//@FacesConverter(forClass=com.eugenefe.converter.MarketVariableConverter.class, value="mvConverter")
//@FacesConverter(forClass=com.eugenefe.converter.MarketVariableConverter.class, value="mvConverter")
public class MarketVariableConverter implements javax.faces.convert.Converter{
//	@Logger
//	private Log log;
//
//	@In(create = true)
//	private ProductSelectAction productSelectAction;
//	
//	public MarketVariableConverter(){
//		
//	}

//	@Override
//	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
//		log.info("In the Converter");
//		if (submittedValue.trim().equals("")) {
//            return null;
//        } 
//		else {
//            try {
//            	log.info("In the Converter1");
//                for (MarketVariable aa : productSelectAction.getProducts()) {
//                    if (aa.getMvId().equals(submittedValue) ) {
////                    	if (aa.getMvId().contains(submittedValue) || aa.getMvName().contains(submittedValue)) {	
//                    	log.info("In the Converter2");
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
//
//	@Override
//	 public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
//		if (value == null || value.equals("")) {
//            return "";
//        } else {
//            return String.valueOf(((MarketVariable) value).getMvId());
//        }
//	}
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		return new MarketVariable("AAAA");
	}

	@Override
	 public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		return "AAAA";
	}

}
