package com.eugenefe.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.PortfolioCompo;
import com.eugenefe.entity.PortfolioCompoId;

@Name("tablePortfolioInit")
@Scope(ScopeType.CONVERSATION)
public class TablePortfolioInit {
	
	@Logger		private Log log;
//	@In			private EntityManager entityManager;
	@In 		private Session	session;
	
	private List<Portfolio> portList = new ArrayList<Portfolio>();
	private List<Portfolio> userPortfolioList = new ArrayList<Portfolio>();

	private Portfolio selectPortfolio;
	private Portfolio addPortfolio;

//	private TreeNode rootNode;
	private TreeNode sysRootNode;
	private TreeNode userRootNode;
//	private TreeNode dlgNode;
	private TreeNode selectNode;
	private TreeNode dlgSelNode;
	private Portfolio copyToPortfolio;
	private TreeNode[] selectNodeList;
		
//	private TreeNode parentNode;

	public TablePortfolioInit() {
	}
//******************************************************************
	@Create
	public void create(){
		sysRootNode = new DefaultTreeNode("HIER",new Portfolio("SYS_ROOT"), null);
		userRootNode = new DefaultTreeNode("UD", new Portfolio("USER_ROOT"), null);
//		parentNode = sysRootNode;
//		dlgNode = new DefaultTreeNode(new Portfolio("USER_ROOT"), null);
		
		userPortfolioList = session.createCriteria(Portfolio.class).addOrder(Order.asc("portId")).list();
//		userPortfolioList = portList;
		
		addPortfolio = new Portfolio();
		
		for(Portfolio aa : userPortfolioList){
			if(aa.getParentPortfolio()==null){
				recursiveTreeSet(aa, userRootNode);
//				recursiveTreeSet(aa, dlgNode);
			}
		}
	}

	public void selectNode(NodeSelectEvent event){
//		parentNode=null;
//		parentNode = new DefaultTreeNode(new Portfolio("root"),null);

		if(selectPortfolio!=null){
			log.info("Select Node : #0,#1", selectPortfolio.getPortId(), selectNode.toString());	
		}
		selectPortfolio= (Portfolio)selectNode.getData();
		log.info("Select Node2 : #0,#1", selectPortfolio.getPortId(), selectNode);
		
		addPortfolio = new Portfolio();
		
		if(selectPortfolio!=null){
			addPortfolio.setPortId(selectPortfolio.getPortId());
			addPortfolio.setPortName(selectPortfolio.getPortName());
			addPortfolio.setPortType(selectPortfolio.getPortType());
			
//			addPortfolio.setPortPrefix(selectPortfolio.getPortPrefix());
			
			addPortfolio.setReplicatePortfolio(selectPortfolio.getReplicatePortfolio());
			addPortfolio.setChildPortfolios(selectPortfolio.getChildPortfolios());
//			addPortfolio.setPortfolioType(selectPortfolio.getPortfolioType());
		}
//		recursiveTreeSet(selectPortfolio, parentNode);
	}

	public void savePortfolio(){
		for(Portfolio aa : userPortfolioList){
			if(aa.getPortId().equals(addPortfolio.getPortId())){
				log.info("Update:#0,#1", addPortfolio.getPortId(), aa.getPortName());
				aa.setPortId(addPortfolio.getPortId());
				aa.setPortName(addPortfolio.getPortName());
				aa.setPortType(addPortfolio.getPortType());
				
				session.update(aa);
				session.flush();
				return;
			}
		}
//		portList.add(addPortfolio);
		userPortfolioList.add(addPortfolio);
		TreeNode tempNode = new DefaultTreeNode(addPortfolio.getPortType(), addPortfolio, userRootNode);
				
		session.save(addPortfolio);
		session.flush();
	}

	public void onDrop(TreeDragDropEvent event){
		TreeNode dragNode = event.getDragNode();
		TreeNode dropNode = event.getDropNode();
		
		Portfolio dragPort = (Portfolio)dragNode.getData();
		Portfolio dropPort = (Portfolio)dropNode.getData();
		
		if(dropPort.getPortId().equals("USER_ROOT")){
			log.info("in the root:#0", dragPort.getPortId());
			dropPort=null;
		}else{
//			dropPort.getChildPortfolios().add(dragPort);
		}
		
		dragPort.setParentPortfolio(dropPort);
		
		session.saveOrUpdate(dragPort);
		session.flush();

		refreshTree();
	}

	
	public void selectDlgNode(NodeSelectEvent event){
		copyToPortfolio = (Portfolio)event.getTreeNode().getData();
	}

	public void copyPortfolio(){
		if(selectPortfolio.getReplicatePortfolio()!=null){
			selectPortfolio= selectPortfolio.getReplicatePortfolio();
//			log.info("addPort:#0", addPortfolio.getPortId());
		}

		for(Portfolio aa : copyToPortfolio.getChildPortfolios()){
			if(aa.getReplicatePortfolio()!=null 
					&& selectPortfolio.getPortId().equals(aa.getReplicatePortfolio().getPortId())){
				return;
			}
		}
		
		Portfolio temp  = new Portfolio();
		temp.setPortId(copyToPortfolio.getPortId()+"_"+selectPortfolio.getPortId());
		temp.setPortName("*"+selectPortfolio.getPortName());
		temp.setPortType("COPY");

		temp.setWeight(new BigDecimal(1));
		temp.setReplicatePortfolio(selectPortfolio);

		temp.setParentPortfolio(copyToPortfolio);
		copyToPortfolio.getChildPortfolios().add(temp);
		
		userPortfolioList.add(temp);
		TreeNode aaNode = new DefaultTreeNode(temp.getPortType(), temp, dlgSelNode);
		
		session.save(temp);
		session.flush();
	}

	public void delete(){
		session.delete(selectPortfolio);
		session.flush();
		
		refreshTree();
	}
	public void clearPortfolio(){
		addPortfolio =new Portfolio();
		selectPortfolio=null;
	}
	
	
	
//*************************************getter and setter method********************************	

	public List<Portfolio> getUserPortfolioList() {
		return userPortfolioList;
	}
	public void setUserPortfolioList(List<Portfolio> userPortfolioList) {
		this.userPortfolioList = userPortfolioList;
	}
	public Portfolio getSelectPortfolio() {
		return selectPortfolio;
	}
	public void setSelectPortfolio(Portfolio selectPortfolio) {
		this.selectPortfolio = selectPortfolio;
	}
	public Portfolio getAddPortfolio() {
		return addPortfolio;
	}
	public void setAddPortfolio(Portfolio addPortfolio) {
		this.addPortfolio = addPortfolio;
	}
	public TreeNode getSysRootNode() {
		return sysRootNode;
	}
	public void setSysRootNode(TreeNode sysRootNode) {
		this.sysRootNode = sysRootNode;
	}
	public TreeNode getUserRootNode() {
		return userRootNode;
	}
	public void setUserRootNode(TreeNode userRootNode) {
		this.userRootNode = userRootNode;
	}
	public TreeNode getSelectNode() {
		return selectNode;
	}
	public void setSelectNode(TreeNode selectNode) {
		this.selectNode = selectNode;
	}
	public TreeNode getDlgSelNode() {
		return dlgSelNode;
	}
	public void setDlgSelNode(TreeNode dlgSelNode) {
		this.dlgSelNode = dlgSelNode;
	}
	public Portfolio getCopyToPortfolio() {
		return copyToPortfolio;
	}
	public void setCopyToPortfolio(Portfolio copyToPortfolio) {
		this.copyToPortfolio = copyToPortfolio;
	}
	public TreeNode[] getSelectNodeList() {
		return selectNodeList;
	}
	public void setSelectNodeList(TreeNode[] selectNodeList) {
		this.selectNodeList = selectNodeList;
	}
	
	//*************************************private method********************************
	private void refreshTree(){
		userRootNode.getChildren().clear();
		selectNode=null;
		session.clear();
		userPortfolioList = session.createCriteria(Portfolio.class).addOrder(Order.asc("portId")).list();
		for(Portfolio aa : userPortfolioList){
			if(selectPortfolio!=null && selectPortfolio.getPortId().equals(aa.getPortId())){
				selectPortfolio =aa;
				log.info("selectPort: #0,#1", aa.getPortId());
			}
			log.info("ERROR4: #0,#1", aa.getPortName(), aa.getChildPortfolios().size());
			if(aa.getParentPortfolio()==null){
				recursiveTreeSet(aa, userRootNode);
			}			
		}
	}
	private void recursiveTreeSet(Portfolio data, TreeNode node){
//		if(data.getReplicatePortfolio()!=null){
//			data = data.getReplicatePortfolio();
//		}
		TreeNode tempNode = new DefaultTreeNode(data.getPortType(), data, node);
//		tempNode.setExpanded(true);
		for(Portfolio aa : data.getChildPortfolios()){
//			log.info("Rec:#0,#1", aa.getId().getPortId());
			log.info("Rec1:#0,#1", aa.getPortId());
			recursiveTreeSet(aa, tempNode);
		}
	}
	
}
