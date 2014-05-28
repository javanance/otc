package com.eugenefe.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Log;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;

import com.eugenefe.converter.LazyModelMarketVariable;
import com.eugenefe.converter.TableDynamicColumn;
import com.eugenefe.entity.IMarketVariableHis;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.PortfolioCompo;
import com.eugenefe.entity.PortfolioCompoId;
import com.eugenefe.entity.PortfolioDefineNew;
import com.eugenefe.entity.PortfolioNew;
import com.eugenefe.entity.Position;
import com.eugenefe.entity.Scenario;
import com.eugenefe.enums.EBoolean;
import com.eugenefe.util.MarketVariableType;
import com.eugenefe.util.NamedQuery;

@Name("tablePortfolioNewInit")
@Scope(ScopeType.CONVERSATION)
public class TablePortfolioNewInit {
	
	@Logger		private Log log;
//	@In			private EntityManager entityManager;
	@In 		private Session	session;
	
	private List<PortfolioNew> portList = new ArrayList<PortfolioNew>();
	private List<PortfolioNew> userPortfolioList = new ArrayList<PortfolioNew>();
	private List<PortfolioNew> sysPortfolioList = new ArrayList<PortfolioNew>();
	private PortfolioNew selectPortfolio;
	private PortfolioNew addPortfolio;
	private PortfolioDefineNew selectPortDefine;

//	private TreeNode rootNode;
	private TreeNode sysRootNode;
	private TreeNode userRootNode;
	private TreeNode selectNode;
	private TreeNode parentNode;
	
	

	public PortfolioNew getAddPortfolio() {
		return addPortfolio;
	}
	public void setAddPortfolio(PortfolioNew addPortfolio) {
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
	public List<PortfolioNew> getSysPortfolioList() {
		return sysPortfolioList;
	}
	public void setSysPortfolioList(List<PortfolioNew> sysPortfolioList) {
		this.sysPortfolioList = sysPortfolioList;
	}
	public TreeNode getParentNode() {
		return parentNode;
	}
	public void setParentNode(TreeNode parentNode) {
		this.parentNode = parentNode;
	}
	public TreeNode getSelectNode() {
		return selectNode;
	}
	public void setSelectNode(TreeNode selectNode) {
		this.selectNode = selectNode;
	}

//	public TreeNode getRootNode() {
//		return rootNode;
//	}
//
//	public void setRootNode(TreeNode rootNode) {
//		this.rootNode = rootNode;
//	}

	public PortfolioDefineNew getSelectPortDefine() {
		return selectPortDefine;
	}

	public void setSelectPortDefine(PortfolioDefineNew selectPortDefine) {
		this.selectPortDefine = selectPortDefine;
	}

	public List<PortfolioNew> getPortList() {
		return portList;
	}

	public void setPortList(List<PortfolioNew> portList) {
		this.portList = portList;
	}

	public List<PortfolioNew> getUserPortfolioList() {
		return userPortfolioList;
	}

	public void setUserPortfolioList(List<PortfolioNew> userPortfolioList) {
		this.userPortfolioList = userPortfolioList;
	}

	public PortfolioNew getSelectPortfolio() {
		return selectPortfolio;
	}

	public void setSelectPortfolio(PortfolioNew selectPortfolio) {
		this.selectPortfolio = selectPortfolio;
	}

	public TablePortfolioNewInit() {
	}

	@Create
	public void create(){
		
		sysRootNode = new DefaultTreeNode(new PortfolioNew("SYS_ROOT"), null);
		userRootNode = new DefaultTreeNode(new PortfolioNew("USER_ROOT"), null);
		parentNode = sysRootNode;

		portList = session.createCriteria(PortfolioNew.class).list();
		
		for(PortfolioNew aa : portList){
			if(aa.getChangeable()== null || aa.getChangeable().getValue()){
				userPortfolioList.add(aa);
			}else{
				sysPortfolioList.add(aa);
			}
		}
		
		
		addPortfolio = new PortfolioNew();
		
		for(PortfolioNew aa : userPortfolioList){
			if(aa.getParentPort().isEmpty()){
				recursiveTreeSet(aa, userRootNode);
			}
		}
		for(PortfolioNew aa : sysPortfolioList){
			if(aa.getParentPort().isEmpty()){
				recursiveTreeSet(aa, sysRootNode);
			}
		}
		
//		for(PortfolioNew xx : rootList){
//			log.info("loop:#0", xx.getPortfolioId());
//			recursiveTreeSet(xx, sysRootNode);
//		}		
	}
	public void portfolioSelect(){
		
	}
	
	private void recursiveTreeSet(PortfolioNew data, TreeNode node){
		TreeNode tempNode = new DefaultTreeNode(data, node);
		tempNode.setExpanded(true);
		for(PortfolioCompo aa : data.getSubPortfolioList()){
//			log.info("Rec:#0,#1", aa.getId().getPortfolioId());
//			log.info("Rec1:#0,#1", aa.getChildPortfolio().getPortfolioId());
			
//			recursiveTreeSet(aa.getChildPortfolio(), tempNode);
		}
	}
	

	public void selectNode(NodeSelectEvent event){
		parentNode=null;
		parentNode = new DefaultTreeNode(new PortfolioNew("root"),null);
		
		selectPortfolio= (PortfolioNew)selectNode.getData();
		
		addPortfolio = new PortfolioNew();
		addPortfolio.setChangeable(EBoolean.Y);	
		if(selectPortfolio!=null){
			addPortfolio.setPortfolioId(selectPortfolio.getPortfolioId());
			addPortfolio.setPortfolioName(selectPortfolio.getPortfolioName());
			addPortfolio.setPortfolioType(selectPortfolio.getPortfolioType());
		}
		
//		temp.setSubPortfolioList(selectPortfolio.getSubPortfolioList());

		recursiveTreeSet(selectPortfolio, parentNode);
	}

	public void selectTreeNode(NodeSelectEvent event){
		log.info("Node1: #0,#1", ((PortfolioNew)selectNode.getData()).getPortfolioId(), selectNode.getChildCount());
		
		selectPortfolio= (PortfolioNew)selectNode.getData();
	}
	
	public void savePortfolio(){
		addPortfolio.setChangeable(EBoolean.Y);

		for(PortfolioNew aa : portList){
			if(aa.getPortfolioId().equals(addPortfolio.getPortfolioId())){
				log.info("Update:#0,#1", addPortfolio.getPortfolioId(), aa.getPortfolioName());
				aa.setPortfolioId(addPortfolio.getPortfolioId());
				aa.setPortfolioName(addPortfolio.getPortfolioName());
				aa.setPortfolioType(addPortfolio.getPortfolioType());
				
				session.update(aa);
				session.flush();
				return;
			}
		}
		portList.add(addPortfolio);
		userPortfolioList.add(addPortfolio);
		
		TreeNode upperNode;
		if(selectNode==null){
			upperNode = userRootNode; 
		}else{
			upperNode = selectNode.getParent();
			PortfolioCompo temp = new PortfolioCompo(new PortfolioCompoId(((PortfolioNew)upperNode.getData()).getPortfolioId()
					,addPortfolio.getPortfolioId()));
			temp.setPortWeight(new BigDecimal(1));
			session.save(temp);
		}
		TreeNode tempNode = new DefaultTreeNode(addPortfolio, upperNode);
		
//		PortfolioCompo temp = new PortfolioCompo(new PortfolioCompoId(((PortfolioNew)selectNode.getParent().getData()).getPortfolioId()
//				,addPortfolio.getPortfolioId()));
		
		
	
		session.save(addPortfolio);
		session.flush();
		
	}
	public void unselectPortfolio(){
		selectPortfolio=new PortfolioNew();
	}
	
	public void onDrop(TreeDragDropEvent event){
		TreeNode dragParentNode = event.getDragNode().getParent();
		TreeNode dragNode = event.getDragNode();
		TreeNode dropNode = event.getDropNode();
		
		if(dropNode.equals(userRootNode)){
			
		}
		
//		for(PortfolioNew aa : userPortfolioList){
//			if(aa.getParentPort().isEmpty()){
//				
//			}
//		}
//		TreeNode dragParentNode1 = selectNode.getParent();
//		log.info("check :#0",(PortfolioNew)dragParentNode1.getData());

		PortfolioNew dragPort = (PortfolioNew)dragNode.getData();
		PortfolioNew dropPort = (PortfolioNew)dropNode.getData(); 
		PortfolioNew dragPortParent = (PortfolioNew)dragParentNode.getData();
		
//		PortfolioNew dragPortParent = (PortfolioNew)dragNode.getParent().getData();
		log.info("deletezzz:#0,#1", dragPortParent.getPortfolioId(), dropPort.getPortfolioId());
		for(PortfolioCompo aa : dragPortParent.getSubPortfolioList()){
			log.info("delete 1: #0,#1,#2", aa.getId().getChildPortId(), aa.getId().getPortfolioId(),dragPort.getPortfolioId());
			if(aa.getId().getPortfolioId().equals(dragPort.getPortfolioId())){
				log.info("delete");
				session.delete(aa);
			}
		}
//		dragPortParent.getSubPortfolioList().remove(dragPort);
//		dragPort.getParentPort().remove(dragPortParent);
		
		PortfolioCompo temp = new PortfolioCompo(new PortfolioCompoId(dropPort.getPortfolioId(),dragPort.getPortfolioId()));
		temp.setPortWeight(new BigDecimal(1));

		session.save(temp);
		session.flush();
		
		log.info("Drag:#0, #1", dragPort.getPortfolioId(), dropPort.getPortfolioId());
		
	}

	public void onDrop1(TreeDragDropEvent event){
		PortfolioNew bb;
		TreeNode dragNode = event.getDragNode();
		TreeNode dropNode = event.getDropNode();
		
		PortfolioNew dropParentPort = (PortfolioNew)dropNode.getParent().getData();
		PortfolioNew dragPort = (PortfolioNew)dragNode.getData();
		PortfolioNew dropPort = (PortfolioNew)dropNode.getData();
		
		for(TreeNode  aa :userRootNode.getChildren()){
			bb =(PortfolioNew)aa.getData();
			log.info("AAAA:#0,#1", bb.getPortfolioId(), dropPort.getPortfolioId());
			log.info("bbb:#0,#1", bb.getSubPortfolioList().size(), aa.getChildCount());
			if(!bb.getPortfolioId().equals(dropPort.getPortfolioId())
					&& bb.getSubPortfolioList().size()!= aa.getChildCount()){
				log.info("AAAAaaa:#0", bb.getPortfolioId());
				for(PortfolioCompo zz : bb.getSubPortfolioList()){
					log.info("AAAAaaaxxxxxx:#0,#1", zz.getId().getChildPortId(), bb.getPortfolioId());
					if(zz.getId().getChildPortId().equals(dragPort.getPortfolioId())){
						log.info("AAAAaaaxxcx:#0", dragPort.getPortfolioId());
						session.delete(zz);
						session.flush();
					}
				}

			}
		}

//		ADD drag Node
		PortfolioCompo temp = new PortfolioCompo(new PortfolioCompoId(dropPort.getPortfolioId(),dragPort.getPortfolioId()));
		temp.setPortWeight(new BigDecimal(1));
		dropParentPort.getSubPortfolioList().add(temp);
//		dropPort.getParentPort().add(temp);
		
		session.save(temp);
		session.flush();
		
	}
	public void beforeDrag(){
//	public void beforeDrag(TreeDragDropEvent event){
		log.info("Drag:#0, #1" );
	}

}
