package com.eugenefe.converter;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Log;

import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.PortfolioReturn;
import com.eugenefe.session.PortfolioReturnBssdList;

@Name("portfolioConverter")
@Scope(ScopeType.CONVERSATION)
@Converter
@BypassInterceptors
public class PortfolioConverter implements javax.faces.convert.Converter , Serializable{
	@Logger
	private Log log;

	@In(create = true)
	private PortfolioReturnBssdList portfolioReturnBssdList;
	
	private List<Portfolio> fullPorts;
	
	
	public List<Portfolio> getFullPorts() {
		return fullPorts;
	}
	public void setFullports(List<Portfolio> fullPorts) {
		this.fullPorts = fullPorts;
	}
	public PortfolioConverter(){
		
	}
	@Create
	public void init(){
		log.info("In the Converter111");
		fullPorts = new ArrayList<Portfolio>();
		for (PortfolioReturn aa : portfolioReturnBssdList.getResultList()) {
			fullPorts.add(aa.getPortfolio());
		}	
	}
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		log.info("In the Converter");
		if (submittedValue.trim().equals("")) {
            return null;
        } 
		else {
            try {
            	log.info("In the Converter1");
                for (Portfolio bb : fullPorts) {
                    if (bb.getPortId().equals(submittedValue)) {
                    	log.info("In the Converter2");
                    	return bb;
                    }
                }
                
            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Portfolio"));
            }
        }
		log.info("In the Converter3");
		return null;
	}

	@Override
	 public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Portfolio) value).getPortId());
        }
	}
	

}
