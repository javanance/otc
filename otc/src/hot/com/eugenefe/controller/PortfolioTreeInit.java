package com.eugenefe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
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

@Name("portfolioTreeInit")
@Scope(ScopeType.CONVERSATION)
public class PortfolioTreeInit {

	@Logger
	private Log log;
	
	@In(create=true)
	private FullPortfolioInit fullPortfolioInit; 
	private TreeNode portfolioRoot;
	
	private List<Portfolio> selectedHiers;
	
	private List<String> selectedHierarchyIds;
	
	public PortfolioTreeInit(){
		System.out.println("Construction PortfolioTreeInit");
	}
	
//************************Getter and Setter*******************

	public FullPortfolioInit getFullPortfolioInit() {
		return fullPortfolioInit;
	}

	public void setFullPortfolioInit(FullPortfolioInit fullPortfolioInit) {
		this.fullPortfolioInit = fullPortfolioInit;
	}
	
	public TreeNode getPortfolioRoot() {
//		log.info("getPortfolioRoot");
		return portfolioRoot;
	}
	public void setPortfolioRoot(TreeNode portfolioRoot) {
		this.portfolioRoot = portfolioRoot;
	}

	public List<Portfolio> getSelectedHiers() {
		return selectedHiers;
	}

	public void setSelectedHiers(List<Portfolio> selectedHiers) {
		this.selectedHiers = selectedHiers;
	}

	public List<String> getSelectedHierarchyIds() {
		return selectedHierarchyIds;
	}

	public void setSelectedHierarchyIds(List<String> selectedHierarchyIds) {
		this.selectedHierarchyIds = selectedHierarchyIds;
	}

//	********************************Post Creation***********************
	@Create
	public void treeInit(){
	    log.info("In the Tree0_Init():  #0");
	    selectedHiers = new ArrayList<Portfolio>();
//	    FullPortfolioInit xxx = fullPortfolioInit;
//	    selectedHiers = xxx.getFullHiers();
	    selectedHiers = fullPortfolioInit.getFullHiers();
		generateTree(selectedHiers);
//		init(portfolioRoot, portfolios);
		log.info("In the Tree1_Init():  #0");
		
	}
	//----------------------Event Listener------------------
	@Begin(join=true)
	@Observer("changeBssd_/view/v300ReturnRisk.xhtml")
//	@Factory(value="portfolioRoot" )
	public void init() {
	    log.info("In the Tree0_Init():  #0");
	    selectedHiers = new ArrayList<Portfolio>();
	    selectedHiers = fullPortfolioInit.getFullHiers();
		generateTree(selectedHiers);
//		init(portfolioRoot, portfolios);
		log.info("In the Tree1_Init():  #0");
	}	
	
 
	public void onChangeHierarchy(ActionEvent actionEvent) {
		log.info("onChangeHierarchy_0 : #0");
		selectedHiers = new ArrayList<Portfolio>();
		
		for (Portfolio aa : fullPortfolioInit.getFullHiers()) {
			log.info("onChangeHierarchy_1 : #0");
			if (selectedHierarchyIds != null && !selectedHierarchyIds.isEmpty()) {
				if (selectedHierarchyIds.contains(aa.getPortId())) {
					selectedHiers.add(aa);
					log.info("onChangeHierarchy_2 : #0");
				}
			} else {
				log.info("onChangeHierarchy_3 : #0");
				selectedHiers.add(aa);
			}
		}
		generateTree(selectedHiers);
//		init(portfolioRoot, portfolios);
	}	
	
// ----------------------------- helper method----------------------------------------
	
	private void init(TreeNode portRoot, List<Portfolio> sub){
		log.info("Observer initPortfolioTree In Table0");
//		searchString = null;
//		filterPorts =null;
		this.portfolioRoot = portRoot;
//		this.portfolios = sub;
		
		if (portfolioRoot.getChildCount() != 0) {
			portfolioRoot.getChildren().get(0).setSelected(true);
//			parentPortfolio = (Portfolio) (portfolioRoot.getChildren().get(0).getData());
//			portfolios = parentPortfolio.getChildPortfolios();
		}
		log.info("Observer initPortfolioTree1");
	}
	
	
	
	
	
	public void generateTree(List<Portfolio> rootPorts) {
//		portfolios = new ArrayList<Portfolio>();
		Portfolio root = new Portfolio("root", "Root");
		portfolioRoot = new DefaultTreeNode(root, null);
		portfolioRoot.setExpanded(true);

		for (Portfolio bb : rootPorts) {
			TreeNode childNode = new DefaultTreeNode(bb, portfolioRoot);
//			childNode.setExpanded(true);
			recursive(fullPortfolioInit.getFullPorts(), childNode);
			log.info("Creation of Tree : #0", bb.getPortId());
		}
		if (portfolioRoot.getChildCount() != 0) {
			portfolioRoot.getChildren().get(0).setSelected(true);
//			parentPortfolio = (Portfolio) (portfolioRoot.getChildren().get(0).getData());
//			portfolios = parentPortfolio.getChildPortfolios();
			log.info("In the Tree Generation1:#0");
		}
		
//		Events.instance().raiseEvent("changeTree", parentPortfolio);
//		log.info("In the Tree Generation:#0", subPortfolios.size());
		log.info("In the Tree Generation_generateTree(ports):#0");
	}
	
	private void recursive(List<Portfolio> port, TreeNode node) {
		List<IPortfolio> tempList = new ArrayList<IPortfolio>();

		String parentId = ((Portfolio) node.getData()).getPortId();
		tempList = getSubPortfolios(parentId, port);
		// log.info("ParentId in SubPort : #0,#1,#2", parentId, tempList.size(),node);

		for (IPortfolio k : tempList) {
			TreeNode childNode = new DefaultTreeNode(k, node);
			childNode.setExpanded(true);
			recursive(port, childNode);
		}
	}

	private List<IPortfolio> getSubPortfolios(String parentId, List<Portfolio> port) {
		List<IPortfolio> returnList = new ArrayList<IPortfolio>();
		for (Portfolio k : port) {
			
			if (k.getParentPortfolio() != null && k.getParentPortfolio().getPortId().equals(parentId)) {
				returnList.add(k);
			}
		}
		return returnList;
	}

	

}
