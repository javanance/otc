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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;

import com.eugenefe.converter.LazyModelMarketVariable;
import com.eugenefe.converter.TableDynamicColumn;
import com.eugenefe.entity.Hifive;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.MvType;
import com.eugenefe.util.EColumnType;
import com.eugenefe.util.ENavigationData;
import com.eugenefe.util.FnCalendar;
import com.eugenefe.util.MarketVariableType;
import com.eugenefe.util.NamedQuery;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@Name("treeMvTypeInit")
// @Scope(ScopeType.SESSION)
@Scope(ScopeType.CONVERSATION)
public class TreeMvTypeInit implements Serializable {

	@Logger
	private Log log;

	@In
	private Session session;
	
	@In
	private StatusMessages statusMessages;
	
//	@In
//	private org.jboss.seam.faces.FacesContext facesContext;
//	private FacesMessage facesMessgae;
	
	private List<MvType> mvTypeList=new ArrayList<MvType>();
	private List<MvType> marketVariables;
	private Map<MvType, List<MvType>> mvMap ;

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
			rootNode.setExpanded(true);
			recursiveTreeSet(aa, rootNode);
		}
		
		mvMap = new HashMap<MvType, List<MvType>>();
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
			if(aa.getMvType().equals("STEP_DOWN")){
				tempSD = aa;
			}
		}
		if(tempSD.getChildList().contains(tempType)){
			hifive= session.createQuery("from hifive").list();
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
