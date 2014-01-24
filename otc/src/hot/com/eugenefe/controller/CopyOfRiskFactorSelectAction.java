package com.eugenefe.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.util.MarketVariableType;
import com.eugenefe.util.NamedQuery;

@Name("rfSelectActionOld")
@Scope(ScopeType.CONVERSATION)
public class CopyOfRiskFactorSelectAction {
	@Logger
	private Log log;
	@In
	private EntityManager entityManager;

	@In
	private List<MarketVariable> riskFactors = new ArrayList<MarketVariable>();

//	@Out(scope = ScopeType.CONVERSATION, required = false)
	private MarketVariable selectedRiskFactor;
	
//	@Out(scope=ScopeType.CONVERSATION)
	@In
	private LazyDataModel<MarketVariable> lazyRiskFactors;


	public CopyOfRiskFactorSelectAction() {
		System.out.println("Construction RiskFactorSelectionActionOld");
	}
	public List<MarketVariable> getRiskFactors() {
		return riskFactors;
	}
	public void setRiskFactors(List<MarketVariable> riskFactors) {
		this.riskFactors = riskFactors;
	}
	public MarketVariable getSelectedRiskFactor() {
		return selectedRiskFactor;
	}
	public void setSelectedRiskFactor(MarketVariable selectedRiskFactor) {
		this.selectedRiskFactor = selectedRiskFactor;
	}
	public LazyDataModel<MarketVariable> getLazyRiskFactors() {
		return lazyRiskFactors;
	}
	public void setLazyRiskFactors(LazyDataModel<MarketVariable> lazyRiskFactors) {
		this.lazyRiskFactors = lazyRiskFactors;
	}


//	@Factory(value = "lazyRiskFactors")
	public void initModel() {
		riskFactors = entityManager.createQuery(NamedQuery.RiskFactors.getQuery()).getResultList();
		// log.info("product size:#0", products.size());
		lazyRiskFactors = new LazyModelMarketVariable(riskFactors);
	}
	
//	@Factory(value = "lazyProducts", scope = ScopeType.CONVERSATION)
//	public LazyDataModel<MarketVariable> initModel() {
//		products = entityManager.createQuery(NamedQuery.MarketVariables.getQuery()).getResultList();
//		// log.info("product size:#0", products.size());
//		lazyProducts = new LazyModelMarketVariable(products);
//		return lazyProducts;
//	}

	

	

	public void onRowSelect(SelectEvent event) {
		log.info("On Row Selection : #0", selectedRiskFactor.getMvId());

//		System.out.println("in the redirect1"+fc.getViewRoot().getViewId()+EView.MarketVariableSelect.url);

//		if (EView.Product.url.equals(fc.getViewRoot().getViewId())) {
//			fc.getApplication().getNavigationHandler()
//					.handleNavigation(fc, "null", EView.ProductSelect.url+"?faces-redirect=true");
//		}

		// Events.instance().raiseEvent("selectProduct",(MarketVariable)event.getObject());
		Events.instance().raiseEvent("selectProduct", selectedRiskFactor);

	}

	public void initLayout() {
		System.out.println("initLayout");
		// RequestContext requestContext = RequestContext.getCurrentInstance();
		// System.out.println("context" +
		// requestContext.toString()+":"+requestContext.isAjaxRequest());
		// requestContext.execute("innerLayout1.hide('north')");
		// requestContext.execute("innerLayout1.hide('east')");
		// requestContext.update("layoutFullpage");
		System.out.println("initLayout1");
	}

	
	
//***************************************************************

}
