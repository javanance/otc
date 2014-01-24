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

@Name("portfolioBean")
@Scope(ScopeType.CONVERSATION)
public class PortfolioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;
	
	@In(create = true)
	private PortfolioReturnBssdList portfolioReturnBssdList;
	

	private List<Portfolio> fullPortfolios;
	private List<Portfolio> hierarchies;
	private List<Portfolio> selectedHierarchies;

	private List<String> selectedHierarchyIds;
	
	private TreeNode portfolioRoot;
	private TreeNode selectedNode;
	private Portfolio parentPortfolio;
	private List<Portfolio> portfolios;

	private Boolean userDefined;
	private String activeAccordionTab;
	
	private Portfolio selectedPortfolio;
	private List<Portfolio> filterPorts ;

	private String searchString;
	
	public PortfolioBean() {
		System.out.println("In PortfolioBean Creation");
	
	}
// --------------------------------Getter and Setter ---------------

	public List<Portfolio> getFullPortfolios() {
		return fullPortfolios;
	}

	public void setFullPortfolios(List<Portfolio> fullPortfolios) {
		this.fullPortfolios = fullPortfolios;
	}
	
	public List<Portfolio> getHierarchies() {
		return hierarchies;
	}

	public void setHierarchies(List<Portfolio> hierarchies) {
		this.hierarchies = hierarchies;
	}

	public List<Portfolio> getSelectedHierarchies() {
		return selectedHierarchies;
	}

	public void setSelectedHierarchies(List<Portfolio> selectedHierarchies) {
		this.selectedHierarchies = selectedHierarchies;
	}

	public List<String> getSelectedHierarchyIds() {
		return selectedHierarchyIds;
	}

	public void setSelectedHierarchyIds(List<String> selectedHierarchyIds) {
		this.selectedHierarchyIds = selectedHierarchyIds;
	}

	public TreeNode getPortfolioRoot() {
		return portfolioRoot;
	}

	public void setPortfolioRoot(TreeNode portfolioRoot) {
		this.portfolioRoot = portfolioRoot;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

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

	public Boolean getUserDefined() {
		return userDefined;
	}

	public void setUserDefined(Boolean userDefined) {
		this.userDefined = userDefined;
	}

	public String getActiveAccordionTab() {
		return activeAccordionTab;
	}

	public void setActiveAccordionTab(String activeAccordionTab) {
		this.activeAccordionTab = activeAccordionTab;
	}

	public Portfolio getSelectedPortfolio() {
		return selectedPortfolio;
	}

	public void setSelectedPortfolio(Portfolio selectedPortfolio) {
		this.selectedPortfolio = selectedPortfolio;
	}

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

	//----------------------Event Listener------------------
	@Begin(join=true)
	@Observer("changeBssdAllInOne")
	public void init() {
	    log.info("In the Tree0:  #0", portfolioReturnBssdList.getEjbql());
	    
		loadFullPortfolios();
		generateTree(hierarchies);
		
		init(portfolioRoot, portfolios);
		log.info("In the Tree1:  #0", portfolioReturnBssdList.getEjbql());
	}	
	
	public void onChangeHierarchy() {
		log.info("Change Hierarchy : #0");

		selectedHierarchies = new ArrayList<Portfolio>();

		for (Portfolio aa : hierarchies) {
			log.info("Change Hierarchy11 : #0");
			if (selectedHierarchyIds != null && !selectedHierarchyIds.isEmpty()) {
				log.info("Change Hierarchy1 : #0");
				if (selectedHierarchyIds.contains(aa.getPortId())) {
					log.info("Change Hierarchy2 : #0");
					selectedHierarchies.add(aa);
				}
			} else {
				
				selectedHierarchies.add(aa);
			}
		}
		generateTree(selectedHierarchies);
		init(portfolioRoot, portfolios);
	}

	
	public void onNodeSelect(NodeSelectEvent event) {
		searchString = null;
		filterPorts = null;
		pvTemp =0;
				
		parentPortfolio = (Portfolio)event.getTreeNode().getData();
//		parentPortfolio = (Portfolio) selectedNode.getData();
		portfolios = parentPortfolio.getChildPortfolios();
		
		Events.instance().raiseEvent("enterChart", parentPortfolio);
//		log.info("Call Node Select Event: #0, #1, #2");
		log.info("Call Node Select Event: #0, #1, #2", parentPortfolio.getPortId(), portfolios.size());
	}

	public void onTabChange(TabChangeEvent event) {
		activeAccordionTab = event.getTab().getId();
		log.info("Active Accodion Tab : #0", activeAccordionTab);
		if (activeAccordionTab.equals("userDefined")) {
			userDefined = true;
		} else {
			userDefined = false;
		}

		FacesMessage msg = new FacesMessage("Tab Closed", "Closed tab: " + event.getTab().getTitle());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}



	//--------------------------Action Listener--------------------
	
	public void expandAll() {
		recursiveExpand(portfolioRoot.getChildren(), true);
	}

	public void collapseAll() {
		recursiveExpand(portfolioRoot.getChildren(), false);
	}
	
	// ----------------------------- helper method----------------------------------------
	private void init(TreeNode portRoot, List<Portfolio> sub){
		log.info("Observer initPortfolioTree In Table0");
		searchString = null;
		filterPorts =null;
		this.portfolioRoot = portRoot;
		this.portfolios = sub;
		
		if (portfolioRoot.getChildCount() != 0) {
			portfolioRoot.getChildren().get(0).setSelected(true);
			parentPortfolio = (Portfolio) (portfolioRoot.getChildren().get(0).getData());
			portfolios = parentPortfolio.getChildPortfolios();
		}
		log.info("Observer initPortfolioTree1");
	}
	
	public void loadFullPortfolios() {
		log.info("In the port Factory1");
		fullPortfolios = new ArrayList<Portfolio>();
		hierarchies = new ArrayList<Portfolio>();
		for (PortfolioReturn aa : portfolioReturnBssdList.getResultList()) {
			fullPortfolios.add(aa.getPortfolio());
			log.info("In the port Factory2 :#0", fullPortfolios.size());
			if (aa.getPortfolio().getParentPortfolio() == null) {
				hierarchies.add(aa.getPortfolio());
			}
		}
		log.info("In the port Factory2");
	}

	
	public void generateTree(List<Portfolio> rootPorts) {
		portfolios = new ArrayList<Portfolio>();
		Portfolio root = new Portfolio("root", "Root");
		portfolioRoot = new DefaultTreeNode(root, null);
		portfolioRoot.setExpanded(true);

		for (Portfolio bb : rootPorts) {
			TreeNode childNode = new DefaultTreeNode(bb, portfolioRoot);
			childNode.setExpanded(true);
			recursive(fullPortfolios, childNode);
			log.info("Creation of Tree : #0", bb.getPortId());
		}
		if (portfolioRoot.getChildCount() != 0) {
			portfolioRoot.getChildren().get(0).setSelected(true);
			parentPortfolio = (Portfolio) (portfolioRoot.getChildren().get(0).getData());
			portfolios = parentPortfolio.getChildPortfolios();
			log.info("In the Tree Generation1:#0");
		}
		
//		Events.instance().raiseEvent("changeTree", parentPortfolio);

//		log.info("In the Tree Generation:#0", subPortfolios.size());
		log.info("In the Tree Generation:#0");
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
	private void recursiveExpand(List<TreeNode> node, boolean isExpand) {
		for (TreeNode aa : node) {
			aa.setExpanded(isExpand);
			recursiveExpand(aa.getChildren(), isExpand);
		}
	}
	 private boolean ignoreCompare;
	 
	 public boolean isIgnoreCompare() {
		return ignoreCompare;
	}

	public void setIgnoreCompare(boolean ignoreCompare) {
		this.ignoreCompare = ignoreCompare;
	}
	private String newPortName;
	
	public String getNewPortName() {
		return newPortName;
	}

	public void setNewPortName(String newPortName) {
		this.newPortName = newPortName;
	}
	private DualListModel<String> ports ;
	
	public DualListModel<String> getPorts() {
		return ports;
	}

	public void setPorts(DualListModel<String> ports) {
		this.ports = ports;
	}
	
	@Begin(join=true)
	public String onFlowProcess(FlowEvent event) {  
		log.info("Current wizard step:" + event.getOldStep());  
        log.info("Next step:" + event.getNewStep());  
        
    	log.info("Next step1 #0,#1:" , event.getNewStep(), fullPortfolios.size());
    	log.info("Next step2 #0,#1:" , event.getNewStep(), userDefined);
        for(Portfolio aa : fullPortfolios){
//        	log.info("Next step3 #0,#1:" , event.getNewStep());
        	if(newPortName!= null && aa.getPortId().equals(newPortName)){
        		FacesContext facesContext = FacesContext.getCurrentInstance();  
        		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error : Portfolio Name is Duplicated", newPortName));
////        		FacesMessage msg = new FacesMessage("Portfolio Name Error", "asdfadf" + newPortName);
        		return event.getOldStep();
        	}
        }	
        if(event.getOldStep()!=null && event.getOldStep().equals("loadPorts")){
        		loadPickList();
        }
        if(event.getOldStep()!=null && event.getOldStep().equals("pickPorts")){
//    		loadPickList();
        }
        log.info("Next step4 #0,#1:" );
//        log.info("Next step5 #0,#1:" , myWizard.getWizard().getStep());
        return event.getNewStep();  
	}

	public void loadPickList(){
		 List<String> citiesSource = new ArrayList<String>();  
	     List<String> citiesTarget = new ArrayList<String>();
	     if(!ignoreCompare){
	    	 for(Portfolio bb : parentPortfolio.getChildPortfolios()){
	    		 citiesTarget.add(bb.getPortName());
	    	 }
	     }
	     for(Portfolio aa: fullPortfolios){
	    	if(!citiesTarget.contains(aa.getPortName())){
	    		citiesSource.add(aa.getPortName());
	    	}
//	    	 log.info("pickList:#0", aa.getPortName());
	     }
	     ports = new DualListModel<String>(citiesSource, citiesTarget);  
	}
	
	@In(create=true)
	private PortfolioHome portfolioHome;
	
	@End
	public  void save(){
		portfolioHome.getInstance().setPortId(newPortName);
		log.info("Save1 :#0", portfolioHome.getPortfolioPortId());
		portfolioHome.persist();
		portfolioHome.clearInstance();
		log.info("Save3");

		newPortName =null;
		

	}
	
	@End
	public  void delete(){
		portfolioHome.setInstance(parentPortfolio);
		log.info("Delete 1 :#0", portfolioHome.getPortfolioPortId());
//		portfolioHome.remove();
		portfolioHome.clearInstance();
	}
	
	
	private double pvTemp =0;

	public double getPvTemp() {
		pvTemp =0;
		for (PortfolioReturn aa : portfolioReturnBssdList.getResultList()) {
			if(portfolios.contains(aa.getPortfolio())){
				pvTemp = pvTemp + aa.getDailyReturn().doubleValue();
			}
		}
		return pvTemp;
	}

	public void setPvTemp(double pvTemp) {
		this.pvTemp = pvTemp;
	}
	
	public void onTabViewChange(TabChangeEvent event) {
		log.info("Tab View Change :#0", event.getTab().getId());
		if(event.getTab().getId().equals("portfolioTab3")){
			Events.instance().raiseEvent("enterChart", parentPortfolio);
		}
	}
//	@In(create=true)
//	private NewPortfolioWizard myWizard;
//
//	public NewPortfolioWizard getMyWizard() {
//		return myWizard;
//	}
//
//	public void setMyWizard(NewPortfolioWizard myWizard) {
//		this.myWizard = myWizard;
//	}

	
	
}
