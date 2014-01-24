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

@Name("mvSelectAction")
@Scope(ScopeType.CONVERSATION)
public class MarketVariableSelectAction {
	@Logger
	private Log log;
	@In
	private EntityManager entityManager;

	// @Out(scope=ScopeType.CONVERSATION)
	private List<MarketVariable> marketVariables;

	private List<MarketVariable> riskFactors = new ArrayList<MarketVariable>();

	@Out(scope = ScopeType.CONVERSATION, required = false)
	private MarketVariable selectedMarketVariable;
	
	@Out(scope=ScopeType.CONVERSATION)
	private LazyDataModel<MarketVariable> lazyMarketVariables;


	public MarketVariableSelectAction() {
		System.out.println("Construction MarketVariableSelectionAction");
	}

	
	public List<MarketVariable> getMarketVariables() {
		return marketVariables;
	}

	public void setMarketVariables(List<MarketVariable> marketVariables) {
		this.marketVariables = marketVariables;
	}

	public List<MarketVariable> getRiskFactors() {
		return riskFactors;
	}

	public void setRiskFactors(List<MarketVariable> riskFactors) {
		this.riskFactors = riskFactors;
	}

	public MarketVariable getSelectedMarketVariable() {
		return selectedMarketVariable;
	}

	public void setSelectedMarketVariable(MarketVariable selectedMarketVariable) {
		this.selectedMarketVariable = selectedMarketVariable;
	}

	public LazyDataModel<MarketVariable> getLazyMarketVariables() {
		return lazyMarketVariables;
	}

	public void setLazyMarketVariables(LazyDataModel<MarketVariable> lazyMarketVariables) {
		this.lazyMarketVariables = lazyMarketVariables;
	}

	@Factory(value = "lazyMarketVariables")
	public void initModel() {
		marketVariables = entityManager.createQuery(NamedQuery.MarketVariables.getQuery()).getResultList();
		// log.info("product size:#0", products.size());
		lazyMarketVariables = new LazyModelMarketVariable(marketVariables);
	}
	
//	@Factory(value = "lazyProducts", scope = ScopeType.CONVERSATION)
//	public LazyDataModel<MarketVariable> initModel() {
//		products = entityManager.createQuery(NamedQuery.MarketVariables.getQuery()).getResultList();
//		// log.info("product size:#0", products.size());
//		lazyProducts = new LazyModelMarketVariable(products);
//		return lazyProducts;
//	}

	public void onViewFullMarketVariables() {
		log.info("In the Full Product : #0" , marketVariables.size());
		lazyMarketVariables = new LazyModelMarketVariable(marketVariables);
	}

	public void onViewRiskFactor() {
//		String qr = "select a from MarketVariable a where a.riskFactorYN ='Y'";
//		riskFactors =entityManager.createQuery(qr).getResultList();
		riskFactors =entityManager.createQuery(NamedQuery.RiskFactors.getQuery()).getResultList();

		lazyMarketVariables = new LazyModelMarketVariable(riskFactors);
		log.info("In the riskFactors1: #0" , lazyMarketVariables.getRowCount());
	}

	public void onRowSelect(SelectEvent event) {
		log.info("On Row Selection : #0", selectedMarketVariable.getMvId());

		FacesContext fc = FacesContext.getCurrentInstance();
		
//		System.out.println("in the redirect1"+fc.getViewRoot().getViewId()+EView.MarketVariableSelect.url);

//		if (EView.Product.url.equals(fc.getViewRoot().getViewId())) {
//			fc.getApplication().getNavigationHandler()
//					.handleNavigation(fc, "null", EView.ProductSelect.url+"?faces-redirect=true");
//		}

		// Events.instance().raiseEvent("selectProduct",(MarketVariable)event.getObject());
		Events.instance().raiseEvent("selectProduct", selectedMarketVariable);
//		String eventName = "selectProduct_"+ selectedProduct.mvType;
//		Events.instance().raiseEvent(eventName, selectedProduct);
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedMarketVariable.getPositions() == null || selectedMarketVariable.getPositions().size() > 0) {
			requestContext.execute("wgInnerLayout.show('north')");
		} else {
			requestContext.execute("wgInnerLayout.hide('north')");
		}

//		if (selectedProduct.mvType == ProductType.BOND || selectedProduct.mvType == ProductType.STOCK
//				|| selectedProduct.mvType == ProductType.INT_RATE) {
		if (selectedMarketVariable.getMvType() == MarketVariableType.BOND || selectedMarketVariable.getMvType() == MarketVariableType.STOCK ) {	
			requestContext.execute("wgInnerLayout.show('east')");
		} else {
			requestContext.execute("wgInnerLayout.hide('east')");
		}
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
