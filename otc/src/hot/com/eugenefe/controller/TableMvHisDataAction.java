package com.eugenefe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Log;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import com.eugenefe.converter.LazyModelMarketVariable;
import com.eugenefe.entity.IMarketVariableHis;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.util.MarketVariableType;
import com.eugenefe.util.NamedQuery;

@Name("tableMvHisDataAction")
@Scope(ScopeType.CONVERSATION)
public class TableMvHisDataAction {

	@Logger
	private Log log;
	@In
	private EntityManager entityManager;
	
	
//	private List<MarketVariable> marketVariables;

	@In(required = false)
	@Out(required = false)
	// @In
	// @Out
	private MarketVariable selectedMarketVariable;

	@Out(required = false)
	// @Out
	private List<IMarketVariableHis> mvHisList;

	private String query;

	public TableMvHisDataAction() {
		System.out.println("Construction TableMvHisDataAction");
	}

	public MarketVariable getSelectedMarketVariable() {
		return selectedMarketVariable;
	}

	public void setSelectedMarketVariable(MarketVariable selectedMarketVariable) {
		this.selectedMarketVariable = selectedMarketVariable;
	}

	@Observer(value = "evtDateChange_/view/v101ViewHistoryData.xhtml")
	public void onMvSelection() {
		log.info("In the Event on DataChange");
		
		if (selectedMarketVariable != null) {
			query = selectedMarketVariable.getMvType().getQuery();
			mvHisList = entityManager.createQuery(query).getResultList();
			Events.instance().raiseEvent("evtForCreateChart",selectedMarketVariable, mvHisList);
		}
	}

}
