package com.eugenefe.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Log;
import org.primefaces.component.wizard.Wizard;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;

import com.eugenefe.entity.IPortfolio;
import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.PortfolioReturn;
import com.eugenefe.session.PortfolioHome;
import com.eugenefe.session.PortfolioReturnBssdList;

@Name("portfolioSelectAction")
@Scope(ScopeType.CONVERSATION)
public class PortfolioSelectAction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;
	
	private Portfolio parentPortfolio;
	private List<Portfolio> portfolios;

//	private Portfolio selectedPortfolio;
	private List<Portfolio> filterPorts ;

	private String searchString;
	
	public PortfolioSelectAction() {
		System.out.println("Construction PortfolioSelectAction");
	
	}
// --------------------------------Getter and Setter ---------------
	public Portfolio getParentPortfolio() {
		return parentPortfolio;
	}
	public void setParentPortfolio(Portfolio parentPortfolio) {
		this.parentPortfolio = parentPortfolio;
	}

	public List<Portfolio> getPortfolios() {
		return portfolios;
	}
	public void setPortfolios(List<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}

//	public Portfolio getSelectedPortfolio() {
//		return selectedPortfolio;
//	}
//	public void setSelectedPortfolio(Portfolio selectedPortfolio) {
//		this.selectedPortfolio = selectedPortfolio;
//	}
	
	public List<Portfolio> getFilterPorts() {
		return filterPorts;
	}
	public void setFilterPorts(List<Portfolio> filterPorts) {
		this.filterPorts = filterPorts;
	}

	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	
	
//	***************Event Listener****************
	
	public void onNodeSelect(NodeSelectEvent event) {
		searchString = null;
		filterPorts = null;
//		pvTemp =0;
				
		log.info("Node Selection :#0", ((Portfolio)event.getTreeNode().getData()).getPortId());
		parentPortfolio = (Portfolio)event.getTreeNode().getData();
//		parentPortfolio = (Portfolio) selectedNode.getData();
		portfolios = parentPortfolio.getChildPortfolios();
		
		Events.instance().raiseEvent("enterChart", parentPortfolio);
//		log.info("Call Node Select Event: #0, #1, #2");
		log.info("Call Node Select Event: #0, #1, #2", parentPortfolio.getPortId(), portfolios.size());
	}

//************************Helper Method***************

	
}
