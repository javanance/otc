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
import com.eugenefe.entity.Position;
import com.eugenefe.util.MarketVariableType;
import com.eugenefe.util.NamedQuery;

@Name("tablePositionAction")
@Scope(ScopeType.CONVERSATION)
public class TablePositionAction {
	
	@Logger
	private Log log;
	@In
	private EntityManager entityManager;

	@In
	private MarketVariable selectedMarketVariable;

	@Out(required=false)
	private List<Position> positions;
	
	private String query;
	
	public TablePositionAction() {
		System.out.println("Construction TablePositionAction");
	}

	public MarketVariable getSelectedMarketVariable() {
		return selectedMarketVariable;
	}

	public void setSelectedMarketVariable(MarketVariable selectedMarketVariable) {
		this.selectedMarketVariable = selectedMarketVariable;
	}
	
	public void onMvSelection() {
		positions = selectedMarketVariable.getPositions();
		Events.instance().raiseEvent("evtProductSelection", selectedMarketVariable);
		
//		if(selectedMarketVariable.getProductYN().equals("Y")){
//			log.info("Position : #0", positions.size());
//		}
//		Events.instance().raiseEvent("evtForCreateChart", selectedProduct, marketVariableHisList);
	}
	
//	public void onStartDateChange(SelectEvent event){
//		if(selectedMarketVariable != null){
//			String query =selectedMarketVariable.getMvType().getQuery();
//			mvHisList = entityManager.createQuery(query).getResultList();
//		}
//	}

}
