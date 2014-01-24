package com.eugenefe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.facelets.FaceletContext;
import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Log;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import com.eugenefe.converter.LazyModelMarketVariable;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.util.MarketVariableType;
import com.eugenefe.util.MarketVariableTypeOptionAction;
import com.eugenefe.util.NamedQuery;

@Name("tableLazyMarketVariableInit")
@Scope(ScopeType.CONVERSATION)
public class TableLazyMarketVariableInit {
	@Logger
	private Log log;
	
	@In
	private EntityManager entityManager;
	
	@RequestParameter  
	private String flagDataTable; 
	
	@In(create=true)
	private MarketVariableTypeOptionAction mvTypeOptionAction;
	
	
	@Out
	private SelectItem[] currentMvTypeOption;
	
	
//	@Out(scope=ScopeType.CONVERSATION)
	@Out
	private LazyDataModel<MarketVariable> lazyModelMarketVariable;
	
//	@Out
//	private MarketVariable selectedMarketVariable;
	
	private List<MarketVariable> marketVariables;

	public TableLazyMarketVariableInit() {
		System.out.println("Construction MarketVariableInit");
	}
	
//**********************Getter and Setter *******************
	public SelectItem[] getCurrentMvTypeOption() {
		return currentMvTypeOption;
	}

	public void setCurrentMvTypeOption(SelectItem[] currentMvTypeOption) {
		this.currentMvTypeOption = currentMvTypeOption;
	}

	public LazyDataModel<MarketVariable> getLazyModelMarketVariable() {
		return lazyModelMarketVariable;
	}

	public void setLazyModelMarketVariable(LazyDataModel<MarketVariable> lazyModelMarketVariable) {
		this.lazyModelMarketVariable = lazyModelMarketVariable;
	}
	public String getFlagDataTable() {
		return flagDataTable;
	}
	public void setFlagDataTable(String flagDataTable) {
		this.flagDataTable = flagDataTable;
	}

	//	*****************************************
	
	@Factory(value = "lazyModelMarketVariable")
	public void initMarketVariable(){
		 
//		FaceletContext faceletContext = (FaceletContext) FacesContext.getCurrentInstance().getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
//		String flagDataTable = (String) faceletContext.getAttribute("flagDataTable");

//		String aaa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("flagDataTable");
//		log.info("flag : #0, #1,#2 ", flagDataTable1,flagDataTable, aaa);
		
		if(flagDataTable!=null && flagDataTable.equals("Rf")){
			onViewRiskFactor();
		}
		else if(flagDataTable!=null && flagDataTable.equals("Prod")){
			onViewProduct();
		}
		else{
			onViewFullMarketVariables();
		}
//		selectedMarketVariable = marketVariables.get(0);
//		dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formLazyMarketVariables:tableLazyMarketVariables");
	}

	public void onViewFullMarketVariables() {
		marketVariables = entityManager.createQuery(NamedQuery.AllMarketVariables.getQuery()).getResultList();
		lazyModelMarketVariable = new LazyModelMarketVariable(marketVariables);
		currentMvTypeOption = mvTypeOptionAction.getAllMvTypeOption();
		
		log.info("All flag : #0, #1,#2 ", currentMvTypeOption.length, flagDataTable);
	}

	public void onViewRiskFactor() {
		marketVariables =entityManager.createQuery(NamedQuery.RiskFactors.getQuery()).getResultList();
		lazyModelMarketVariable = new LazyModelMarketVariable(marketVariables);
		currentMvTypeOption = mvTypeOptionAction.getMvTypeOption();
		log.info("Rf flag : #0, #1,#2 ", currentMvTypeOption.length, flagDataTable);
	}
	
	
	public void onViewProduct() {
		marketVariables =entityManager.createQuery(NamedQuery.Products.getQuery()).getResultList();
		lazyModelMarketVariable = new LazyModelMarketVariable(marketVariables);
		currentMvTypeOption = mvTypeOptionAction.getProductTypeOption();
		log.info("Prod flag : #0, #1,#2 ", currentMvTypeOption.length, flagDataTable);
	}

	
//TODO : remove String Constant using UI Component Binding 	
//***************************************************************
	public void resetTable() {
	    DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
	            .findComponent("formLazyMarketVariables:tableLazyMarketVariables");
	    if (dataTable != null) {
	    	dataTable.setValueExpression("sortBy", null);
	    	dataTable.setFirst(0);
	        dataTable.reset();
	    }
	}

	private DataTable dataTable;

	public DataTable getDataTable() {
		return dataTable;
	}
	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

}
