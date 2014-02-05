package com.eugenefe.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Remove;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.core.ConversationIdGenerator;
import org.jboss.seam.core.Events;
//import org.jboss.seam.framework.Query;
import org.jboss.seam.log.Log;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;

import com.eugenefe.converter.LazyModelMarketVariable;
import com.eugenefe.converter.LazyModelVcvHis;
import com.eugenefe.converter.LazyModelVolatilityHis;
import com.eugenefe.entity.IntRate;
import com.eugenefe.entity.IrCurve;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.MarketVariableJoin;
import com.eugenefe.entity.Scenario;
import com.eugenefe.entity.VcvMatrix;
import com.eugenefe.entity.VcvMatrixHis;
import com.eugenefe.entity.Volatility;
import com.eugenefe.entity.VolatilityHis;
import com.eugenefe.entity.VolatilityHisId;
import com.eugenefe.entity.component.ShockData;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.util.ColumnModel;
import com.eugenefe.util.CrossTableModelOld;
import com.eugenefe.util.FlagBean;
import com.eugenefe.util.MarketVariableType;
import com.eugenefe.util.NamedQuery;
import com.eugenefe.util.PivotTableModel;

@Name("tableScenarioDetailInit")
@Scope(ScopeType.CONVERSATION)
public class TableScenarioDetailInit {
	@Logger	private Log log;
	@In	private Session session;

	private Map<MarketVariableJoin, ShockData> selectData = new HashMap<MarketVariableJoin,ShockData>();
	private List<Map.Entry<MarketVariableJoin, ShockData>> selectList
		= new ArrayList<Map.Entry<MarketVariableJoin,ShockData>>();
	
	public Map<MarketVariableJoin, ShockData> getSelectData() {
		return selectData;
	}
	public void setSelectData(Map<MarketVariableJoin, ShockData> selectData) {
		this.selectData = selectData;
	}

	public List<Map.Entry<MarketVariableJoin, ShockData>> getSelectList() {
		selectList = new ArrayList<Map.Entry<MarketVariableJoin,ShockData>>(selectData.entrySet());
		return selectList;
	}


	public void setSelectList(List<Map.Entry<MarketVariableJoin, ShockData>> selectList) {
		this.selectList = selectList;
	}


	public TableScenarioDetailInit() {
		System.out.println("Construction TableScenarioDetail");
	}

	
	public void onNodeSelect(NodeSelectEvent event){
		log.info("NodeSelect:#0, #1", event.getTreeNode().getData());
		Scenario sce ;
//		if(event.getTreeNode().getType().equals("scenarioSet")){
		if(event.getTreeNode().getChildCount()>0){	
//			for(TreeNode node : event.getTreeNode().getChildren()){
//				sce = (Scenario)session.get(Scenario.class, (String)node.getData());
//				selectData.putAll(sce.getSceMap());
//			}
		}else{
			sce = (Scenario)session.get(Scenario.class, (String)event.getTreeNode().getData());
			selectData.putAll(sce.getSceMap());
		}
		
	}
	public void onNodeUnselect(NodeUnselectEvent event){
		Scenario sce ;
//		if(event.getTreeNode().getType().equals("scenarioSet")){
		if(event.getTreeNode().getChildCount()>0){	
//			for(TreeNode node : event.getTreeNode().getChildren()){
//				sce = (Scenario)session.get(Scenario.class, (String)node.getData());
//				selectData.remove(sce);
//			}
		}else{
			sce = (Scenario)session.get(Scenario.class, (String)event.getTreeNode().getData());
			selectData.remove(sce);
		}

	}
}
