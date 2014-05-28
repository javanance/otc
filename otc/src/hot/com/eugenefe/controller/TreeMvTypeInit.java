package com.eugenefe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Events;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.eugenefe.entity.Hifive;
import com.eugenefe.entity.MvType;

@Name("treeMvTypeInit")
// @Scope(ScopeType.SESSION)
@Scope(ScopeType.CONVERSATION)
public class TreeMvTypeInit implements Serializable {

	@Logger	private Log log;
	@In 	private Session session;
	@In		private StatusMessages statusMessages;
	
//	@In
//	private org.jboss.seam.faces.FacesContext facesContext;
//	private FacesMessage facesMessgae;
	
	private List<MvType> mvTypeList=new ArrayList<MvType>();
	private List<MvType> marketVariables;
	private Map<MvType, List<MvType>> mvMap = new HashMap<MvType, List<MvType>>();

	private TreeNode rootNode;
	private TreeNode superNode;
	private TreeNode selectNode;
	
	
	public TreeMvTypeInit() {
	
	}

	@Create
//	@Begin(flushMode=FlushModeType.MANUAL, join=true)
	public void create() {
//		superNode = new DefaultTreeNode("superRoot", null);
		superNode = new DefaultTreeNode( new MvType("superRoot", "SuperRoot", null), null);
		superNode.setExpanded(true);
		
		loadMvType();
		
		for(MvType aa : mvTypeList){
//			rootNode = new DefaultTreeNode(aa.getMvType(), superNode);
			rootNode = new DefaultTreeNode("root", aa, superNode);
//			rootNode.setExpanded(true);
			recursiveTreeSet(aa, rootNode);
		}
	}
	
	private void recursiveTreeSet(MvType data, TreeNode node){
		TreeNode tempNode ;
		for(MvType aa : data.getChildList()){
//			tempNode = new DefaultTreeNode("sub", aa.getMvType(), node);
			tempNode = new DefaultTreeNode("sub", aa, node);
			tempNode.setExpanded(true);
			if(!aa.getChildList().isEmpty()){
				recursiveTreeSet(aa, tempNode);
			}
		}
	}	
	
	public void loadMvType(){
//		mvTypeList= session.createQuery("from MvType ").list();
		
		Query qr =session.createQuery("from MvType a where a.parentMvType is null ");
//		qr.setParameter("param", "");
		mvTypeList = qr.list();
		
		log.info("MvType: #0", mvTypeList.size());
		
	}

	
	public void onNodeSelect(){
		MvType tempType = (MvType)selectNode.getData();
		List<Hifive> hifive = new ArrayList<Hifive>();
		
		MvType tempSD =null;
		for(MvType aa : mvTypeList){
//TODO :  	
			if(aa.getMvType().equals("STEP_DOWN")){
				tempSD = aa;
			}
		}
//		if(tempSD.getChildList().contains(tempType)){
//			hifive= session.createQuery("from hifive").list();
//		}
		Events.instance().raiseEvent("mvTypeSelect", tempType, getDescendents(tempType));
	}
	
	private List<MvType> getAncestors(MvType mvType){
		
		return null;
	}

	private List<MvType> getDescendents(MvType mvType){
		List<MvType> list = new ArrayList<MvType>();
		addDescendants(mvType, list);
		return list;
	}
	
	private void addDescendants(MvType mvType, List<MvType> list){
		if(mvType != null){
			list.add(mvType);
//			List<MvType>  tempChildren = mvType.getChildList();
//			if(tempChildren!=null){
				for(MvType aa : mvType.getChildList()){
					addDescendants(aa, list);
				}
//			}
		}
	}
//	public void save(){
//		for(MarketVariable aa :dirtySet){
//			log.info("Save:#0,#1", dirtySet.size(), aa.getMvId());
//			session.saveOrUpdate(aa);
//		}
//		session.flush();
//	}

// *******************Getter and Settr*********************************

	public List<MvType> getMvTypeList() {
		return mvTypeList;
	}

	public void setMvTypeList(List<MvType> mvTypeList) {
		this.mvTypeList = mvTypeList;
	}

	public List<MvType> getMarketVariables() {
		return marketVariables;
	}

	public void setMarketVariables(List<MvType> marketVariables) {
		this.marketVariables = marketVariables;
	}

	public Map<MvType, List<MvType>> getMvMap() {
		return mvMap;
	}

	public void setMvMap(Map<MvType, List<MvType>> mvMap) {
		this.mvMap = mvMap;
	}

	public TreeNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public TreeNode getSuperNode() {
		return superNode;
	}

	public void setSuperNode(TreeNode superNode) {
		this.superNode = superNode;
	}

	public TreeNode getSelectNode() {
		return selectNode;
	}

	public void setSelectNode(TreeNode selectNode) {
		this.selectNode = selectNode;
	}	
}
