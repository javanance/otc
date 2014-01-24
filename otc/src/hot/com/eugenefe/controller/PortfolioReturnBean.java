package com.eugenefe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import com.eugenefe.entity.IPortfolio;
import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.PortfolioReturn;
import com.eugenefe.session.PortfolioList;
import com.eugenefe.session.PortfolioReturnList;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.sun.xml.internal.ws.wsdl.writer.document.Port;

@Name("portfolioReturnBean")
@Scope(ScopeType.CONVERSATION)
public class PortfolioReturnBean implements Serializable {

	@Logger
	private Log log;

	@In(create = true)
	private PortfolioReturnList portfolioReturnList;
	
	
	private PortfolioReturn selectedPort;
	
	public PortfolioReturn getSelectedPort() {
		return selectedPort;
	}

	public void setSelectedPort(PortfolioReturn selectedPort) {
		this.selectedPort = selectedPort;
	}

	List<PortfolioReturn> portReturns = new ArrayList<PortfolioReturn>();
	
	@Out
	List<Portfolio> portfolios = new ArrayList<Portfolio>();
	
	public PortfolioReturnBean() {
	}

	@Create
	public void init(){
		portReturns = portfolioReturnList.getResultList();
	}

	public List<PortfolioReturn> getPortReturns() {
		return portReturns;
	}

	public void setPortReturns(List<PortfolioReturn> portReturns) {
		this.portReturns = portReturns;
	}

	public List<Portfolio> getPortfolios() {
		portfolios= null;
		for( PortfolioReturn aa: portReturns){
			portfolios.add(aa.getPortfolio());
		}
		return portfolios;
	}

	public void setPortfolios(List<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}
	
	
	
	

}
