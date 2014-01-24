package com.eugenefe.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;

import com.eugenefe.entity.Hifive;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

@Name("growlBean")
@Startup
@Scope(ScopeType.SESSION)
//@Scope(ScopeType.CONVERSATION)
public class GrowlBean {
	
	private String text;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public GrowlBean(){
	}

	public void removeHifive(ActionEvent actionEvent){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Successful", "Delete" + actionEvent.getSource().toString()));
//		context.addMessage(null, new FacesMessage("Successful", "Delete" + ((Hifive)(actionEvent.getSource())).getProdName()));
	}
	
	
	
}
