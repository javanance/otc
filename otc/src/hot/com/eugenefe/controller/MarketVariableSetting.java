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
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.util.EColumnType;
import com.eugenefe.util.ENavigationData;
import com.eugenefe.util.FnCalendar;
import com.eugenefe.util.MarketVariableType;
import com.eugenefe.util.NamedQuery;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@Name("marketVariableSetting")
// @Scope(ScopeType.SESSION)
@Scope(ScopeType.CONVERSATION)
public class MarketVariableSetting implements Serializable {

	@Logger
	private Log log;

	@In
	private Session session;
	
	@In
	private StatusMessages statusMessages;
	
//	@In
//	private org.jboss.seam.faces.FacesContext facesContext;
//	private FacesMessage facesMessgae;
	
	private List<MarketVariableType> mvTypeList=new ArrayList<MarketVariableType>();
	

	public List<MarketVariableType> getMvTypeList() {
		return mvTypeList;
	}

	public void setMvTypeList(List<MarketVariableType> mvTypeList) {
		this.mvTypeList = mvTypeList;
	}

	private TreeNode rootNode;
	private TreeNode superNode;
	private TreeNode selectNode;
	private List<MarketVariable> marketVariables;
	private Map<MarketVariableType, List<MarketVariable>> mvMap ;
	
	public TreeNode getSelectNode() {
		return selectNode;
	}

	public void setSelectNode(TreeNode selectNode) {
		this.selectNode = selectNode;
	}
	
	private Set<MarketVariable> dirtySet;
	private LazyDataModel<MarketVariable> lazyModelMarketVariable;
	public LazyDataModel<MarketVariable> getLazyModelMarketVariable() {
		return lazyModelMarketVariable;
	}

	public void setLazyModelMarketVariable(LazyDataModel<MarketVariable> lazyModelMarketVariable) {
		this.lazyModelMarketVariable = lazyModelMarketVariable;
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

	public Set<MarketVariable> getDirtySet() {
		return dirtySet;
	}

	public void setDirtySet(Set<MarketVariable> dirtySet) {
		this.dirtySet = dirtySet;
	}


	

	public MarketVariableSetting() {
	}

	@Create
//	@Begin(flushMode=FlushModeType.MANUAL, join=true)
	public void create() {
		
		superNode = new DefaultTreeNode("superRoot", null);
		superNode.setExpanded(true);
		
		for(MarketVariableType aa : MarketVariableType.values() ){
			rootNode = new DefaultTreeNode(aa, superNode);
		}
		dirtySet = new HashSet<MarketVariable>();
		
		mvMap = new HashMap<MarketVariableType, List<MarketVariable>>();
		mvTypeList= Arrays.asList(MarketVariableType.values());
	}

	public void addDirty(MarketVariable mv){
//		log.info("Set:#0", mv.getMvId());
		dirtySet.add(mv);
		statusMessages.addFromResourceBundle("saveChangeSetting");
		
	}
	
	public void onNodeSelect(){
//		marketVariables = new ArrayList<MarketVariable>();
		MarketVariableType tempType = (MarketVariableType)selectNode.getData();
		
		if(mvMap.get(tempType)==null || mvMap.get(tempType).size()==0){
			Query qr = session.createQuery("from MarketVariable a where a.mvType=:param");
			qr.setParameter("param",(MarketVariableType)selectNode.getData());
			mvMap.put(tempType, qr.list());
		}
		lazyModelMarketVariable = new LazyModelMarketVariable(mvMap.get(tempType));
	}
	
	public void save(){
		for(MarketVariable aa :dirtySet){
			log.info("Save:#0,#1", dirtySet.size(), aa.getMvId());
			session.saveOrUpdate(aa);
		}
		session.flush();
	}

	// ****************************************************

}
