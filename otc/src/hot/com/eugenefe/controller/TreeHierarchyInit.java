package com.eugenefe.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.FlushModeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.core.Events;
import org.jboss.seam.framework.CurrentDate;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;

import com.eugenefe.converter.LazyModelMarketVariable;
import com.eugenefe.converter.TableDynamicColumn;
import com.eugenefe.entity.Bizunit;
import com.eugenefe.entity.Counterparty;
import com.eugenefe.entity.HierarchyNode;
import com.eugenefe.entity.Hifive;
import com.eugenefe.entity.ITree;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.MarketVariableJoin;
import com.eugenefe.entity.MvType;
import com.eugenefe.entity.Scenario;
import com.eugenefe.entity.ScenarioDetail;
import com.eugenefe.entity.component.ShockData;
import com.eugenefe.pricevo.KisHifive;
import com.eugenefe.util.EColumnType;
import com.eugenefe.util.ENavigationData;
import com.eugenefe.util.FnCalendar;
import com.eugenefe.util.MarketVariableType;
import com.eugenefe.util.NamedQuery;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@Name("treeHierarchyInit")
// @Scope(ScopeType.SESSION)
@Scope(ScopeType.CONVERSATION)
public class TreeHierarchyInit implements Serializable {

	@Logger	private Log log;
	@In 	private Session session;
	@In		private StatusMessages statusMessages;
//	@In private BaseDateBean basedateBean;
	
	private List<HierarchyNode> hierNodeList;
//	private List<Scenario> selectScenarioList;
//	private List<ScenarioDetail> sceDetailList = new ArrayList<ScenarioDetail>();
//	private List<ScenarioDetail> sceDetailFilter = new ArrayList<ScenarioDetail>();
	
//	private Map<MarketVariableJoin, ShockData> sceDetailMap =new HashMap<MarketVariableJoin, ShockData>() ;
//	private Map<MarketVariableJoin, ShockData> sceDetialFilterMap = new HashMap<MarketVariableJoin, ShockData>();

	
	private TreeNode superNode;
	private TreeNode rootNode;
	private TreeNode selectNode;
	private TreeNode[] selectNodes;
	
//	private Map<Scenario, Map<MarketVariableJoin, ShockData>> selectData 
//		= new HashMap<Scenario, Map<MarketVariableJoin,ShockData>>();
	
	public TreeHierarchyInit() {
		System.out.println("Construction TreeHierarchyInit");
	}
	
	private List<Bizunit> bizunitList = new ArrayList<Bizunit>();
	private List<MvType> mvTypeList = new ArrayList<MvType>();
	private List<Counterparty> cPartyList = new ArrayList<Counterparty>();
	
	
	public List<Bizunit> getBizunitList() {
		return bizunitList;
	}

	public void setBizunitList(List<Bizunit> bizunitList) {
		this.bizunitList = bizunitList;
	}

	public List<MvType> getMvTypeList() {
		return mvTypeList;
	}

	public void setMvTypeList(List<MvType> mvTypeList) {
		this.mvTypeList = mvTypeList;
	}

	public List<Counterparty> getcPartyList() {
		return cPartyList;
	}

	public void setcPartyList(List<Counterparty> cPartyList) {
		this.cPartyList = cPartyList;
	}

	//*******************************************************************	
	@Create
	public void loadScenario(){
		hierNodeList = session.createCriteria(HierarchyNode.class).list();
		mvTypeList = session.createCriteria(MvType.class).list();
		bizunitList = session.createCriteria(Bizunit.class).list();
		
		superNode = new DefaultTreeNode("root", null);
//		rootNode = new DefaultTreeNode("root", null);
		
		loadTreeAA();
		loadTreeBizUnit();
		
	}
//	public void onNodeSelect(NodeSelectEvent event){
//		log.info("NodeSelect:#0, #1", event.getTreeNode().getData(), selectNodes.length);
//		updateSceDetailMap();
//	}
//	public void onNodeUnselect(NodeUnselectEvent event){
//		log.info("NodeUnselect:#0, #1", event.getTreeNode().getData(), selectNodes.length);
//		updateSceDetailMap();
//	}
	
	public void update(){
		loadScenario();
	}
//****************Getter and Setter************
	
	public List<HierarchyNode> getHierNodeList() {
		return hierNodeList;
	}

	public void setHierNodeList(List<HierarchyNode> hierNodeList) {
		this.hierNodeList = hierNodeList;
	}

	public TreeNode getSuperNode() {
		return superNode;
	}

	public void setSuperNode(TreeNode superNode) {
		this.superNode = superNode;
	}

	public TreeNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public TreeNode getSelectNode() {
		return selectNode;
	}

	public void setSelectNode(TreeNode selectNode) {
		this.selectNode = selectNode;
	}

	public TreeNode[] getSelectNodes() {
		return selectNodes;
	}

	public void setSelectNodes(TreeNode[] selectNodes) {
		this.selectNodes = selectNodes;
	}
	

//*****************************************************************************************	
	private void loadTreeWithLink(){
		List<String> hLevelList = new ArrayList<String>();
		for(HierarchyNode hNode : hierNodeList){
			if(!hLevelList.contains(hNode.getTableId())){
				hLevelList.add(hNode.getTableId());
			}
		}
		
		rootNode = new DefaultTreeNode("root", null);
		
		TreeNode tempNode ;
		for(String aa : hLevelList){
			tempNode = new DefaultTreeNode(aa, rootNode);
		}
	}
	private void loadTreeAA(){
		TreeNode rootNode = new DefaultTreeNode("MV_TYPE", superNode);
		for(MvType aa : mvTypeList){
			if(aa.getParentMvType()==null || aa.getParentMvType().getMvType()==""){
				TreeNode subRootNode = new DefaultTreeNode(aa.getMvType(), rootNode);
				recursiveTree(aa, subRootNode);
			}
		}
	}
 
	private void loadTreeBizUnit(){
		TreeNode rootNodeBiz = new DefaultTreeNode("ORG", superNode);
		for(Bizunit aa : bizunitList){
//			if(aa.getParentBizunit()==null ){
			if(aa.getOrgId().equals("B0001") ){	
				TreeNode subRootNode = new DefaultTreeNode(aa.getOrgName(), rootNodeBiz);
				recursiveTree(aa, subRootNode);
			}
		}
	}
	private void recursiveTree(ITree parent, TreeNode parentNode){
		TreeNode temp;
		for(ITree child : parent.getChildren()){
			temp = new DefaultTreeNode(child.getId(), parentNode);
			recursiveTree(child, temp);
		}
	}
	
	private void loadTree(){
		rootNode = new DefaultTreeNode("root", null);
		List<String> uppers = new ArrayList<String>();
		Map<String,TreeNode> upNode = new HashMap<String, TreeNode>();
		
		TreeNode temp ;
		for(HierarchyNode aa : hierNodeList){
			if(aa.getNodeGroup()!= null && !uppers.contains(aa.getNodeGroup())){
				uppers.add(aa.getNodeGroup());
				temp = new DefaultTreeNode("nodeGroup", aa.getNodeGroup(), rootNode);
				temp.setExpanded(true);
				upNode.put(aa.getNodeGroup(),temp);
			}
		}
	}
}
