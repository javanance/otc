package com.eugenefe.controller;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("guestPref")
//@Scope(ScopeType.CONVERSATION)
public class GuestPreferences implements Serializable {

//	private String theme = "aristo"; //default
	private String theme ;
	@Create()
	public void init() {
		theme = "sam";
	}    	

	public GuestPreferences(){
	}
	

	public String getTheme() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(params.containsKey("theme")) {
			theme = params.get("theme");
		}
		
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
}
