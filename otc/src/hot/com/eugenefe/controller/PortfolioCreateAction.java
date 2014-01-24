package com.eugenefe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

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

@Name("portfolioCreateAction")
@Scope(ScopeType.CONVERSATION)
public class PortfolioCreateAction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;

	@In
	private EntityManager entityManager;
	@In
	private FullPortfolioInit fullPortfolioInit;
	
	@In(create = true)
	private PortfolioHome portfolioHome;

	private Portfolio parentPortfolio;

	private boolean ignoreCompare;
	private String newPortName;
	
	private DualListModel<String> ports;


	public PortfolioCreateAction() {
		System.out.println("Construction PortfolioCreateionAction");
	}

	// --------------------------------Getter and Setter ---------------
	public FullPortfolioInit getFullPortfolioInit() {
		return fullPortfolioInit;
	}

	public void setFullPortfolioInit(FullPortfolioInit fullPortfolioInit) {
		this.fullPortfolioInit = fullPortfolioInit;
	}

	public PortfolioHome getPortfolioHome() {
		return portfolioHome;
	}

	public void setPortfolioHome(PortfolioHome portfolioHome) {
		this.portfolioHome = portfolioHome;
	}

	public boolean isIgnoreCompare() {
		return ignoreCompare;
	}

	public void setIgnoreCompare(boolean ignoreCompare) {
		this.ignoreCompare = ignoreCompare;
	}

	public String getNewPortName() {
		return newPortName;
	}

	public void setNewPortName(String newPortName) {
		this.newPortName = newPortName;
	}

	public DualListModel<String> getPorts() {
		return ports;
	}

	public void setPorts(DualListModel<String> ports) {
		this.ports = ports;
	}

	public Portfolio getParentPortfolio() {
		return parentPortfolio;
	}

	public void setParentPortfolio(Portfolio parentPortfolio) {
		this.parentPortfolio = parentPortfolio;
	}
	
	// ----------------------Event Listener------------------

	@Begin(join = true)
	public String onFlowProcess(FlowEvent event) {
		log.info("Current wizard step:" + event.getOldStep());
		log.info("Next step:" + event.getNewStep());

		// log.info("Next step1 #0,#1:" , event.getNewStep(),
		// fullPortfolios.size());
		// log.info("Next step2 #0,#1:" , event.getNewStep(), userDefined);
		for (Portfolio aa : fullPortfolioInit.getFullPorts()) {
			// log.info("Next step3 #0,#1:" , event.getNewStep());
			if (newPortName != null && aa.getPortId().equals(newPortName)) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error : Portfolio Name is Duplicated", newPortName));
				// // FacesMessage msg = new
				// FacesMessage("Portfolio Name Error", "asdfadf" +
				// newPortName);
				return event.getOldStep();
			}
		}
		if (event.getOldStep() != null && event.getOldStep().equals("tabLoadPortfolio")) {
			loadPickList();
		}
		if (event.getOldStep() != null && event.getOldStep().equals("tabPickPortfolio")) {
			// loadPickList();
		}
		log.info("Next step4 #0,#1:");
		// log.info("Next step5 #0,#1:" , myWizard.getWizard().getStep());
		return event.getNewStep();
	}

	public void loadPickList() {
		List<String> portsSource = new ArrayList<String>();
		List<String> portsTarget = new ArrayList<String>();
		if (!ignoreCompare) {
			for (Portfolio bb : parentPortfolio.getChildPortfolios()) {
				portsTarget.add(bb.getPortName());
			}
		}
		for (Portfolio aa : fullPortfolioInit.getFullPorts()) {
			if (!portsTarget.contains(aa.getPortName())) {
				portsSource.add(aa.getPortName());
			}
			// log.info("pickList:#0", aa.getPortName());
		}
		ports = new DualListModel<String>(portsSource, portsTarget);
	}

	// ------------------- helper method-------------

	@End
	public void save() {
		portfolioHome.getInstance().setPortId(newPortName);
		log.info("Save1 :#0", portfolioHome.getPortfolioPortId());
		portfolioHome.persist();
		portfolioHome.clearInstance();
		log.info("Save3");

		newPortName = null;
	}
	
//	public void save(){
//		Portfolio temp = new Portfolio();
//		temp.setPortId(newPortName);
//		temp.set
//		entityManager.persist(temp);
//	}

	// @In(create=true)
	// private NewPortfolioWizard myWizard;
	//
	// public NewPortfolioWizard getMyWizard() {
	// return myWizard;
	// }
	//
	// public void setMyWizard(NewPortfolioWizard myWizard) {
	// this.myWizard = myWizard;
	// }

}
