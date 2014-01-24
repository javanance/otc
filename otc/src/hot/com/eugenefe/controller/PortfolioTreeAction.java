package com.eugenefe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Log;
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
import com.eugenefe.util.NamedQuery;

@Name("portfolioTreeAction")
@Scope(ScopeType.CONVERSATION)
public class PortfolioTreeAction {


	@Logger
	private Log log;
	
	@In
	private PortfolioTreeInit portfolioTreeInit;
	
//	@In
//	private List<Portfolio> fullPorts;
//	
//	@In(scope=ScopeType.CONVERSATION)
//	private List<Portfolio> fullHiers;
//	
//	@In
//	private TreeNode portfolioRoot;
//	
//	private List<Portfolio> selectedHiers;
//	
//	private List<String> selectedHierarchyIds;
	
	public PortfolioTreeAction(){
		System.out.println("Construction PortfolioTreeAction");
	}
	
//************************Getter and Setter*******************
//	public List<Portfolio> getFullPorts() {
//		return fullPorts;
//	}
//
//	public void setFullPorts(List<Portfolio> fullPorts) {
//		this.fullPorts = fullPorts;
//	}
//
//	public List<Portfolio> getFullHiers() {
//		return fullHiers;
//	}
//
//	public void setFullHiers(List<Portfolio> fullHiers) {
//		this.fullHiers = fullHiers;
//	}
//
//	public TreeNode getPortfolioRoot() {
//		return portfolioRoot;
//	}
//
//	public void setPortfolioRoot(TreeNode portfolioRoot) {
//		this.portfolioRoot = portfolioRoot;
//	}
//	
//
//	public List<Portfolio> getSelectedHiers() {
//		return selectedHiers;
//	}
//
//	public void setSelectedHiers(List<Portfolio> selectedHiers) {
//		this.selectedHiers = selectedHiers;
//	}
//
//	public List<String> getSelectedHierarchyIds() {
//		return selectedHierarchyIds;
//	}
//
//	public void setSelectedHierarchyIds(List<String> selectedHierarchyIds) {
//		this.selectedHierarchyIds = selectedHierarchyIds;
//	}

	//----------------------Event Listener------------------



	//--------------------------Action Listener--------------------
	
	public void expandAll() {
		recursiveExpand(portfolioTreeInit.getPortfolioRoot().getChildren(), true);
	}

	public void collapseAll() {
		recursiveExpand(portfolioTreeInit.getPortfolioRoot().getChildren(), false);
	}
	
// ----------------------------- helper method----------------------------------------
	


	private void recursiveExpand(List<TreeNode> node, boolean isExpand) {
		for (TreeNode aa : node) {
			aa.setExpanded(isExpand);
			recursiveExpand(aa.getChildren(), isExpand);
		}
	}

}
