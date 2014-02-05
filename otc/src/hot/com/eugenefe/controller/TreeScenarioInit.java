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
import com.eugenefe.entity.Hifive;
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

@Name("treeScenarioInit")
// @Scope(ScopeType.SESSION)
@Scope(ScopeType.CONVERSATION)
public class TreeScenarioInit implements Serializable {

	@Logger	private Log log;
	@In 	private Session session;
	@In		private StatusMessages statusMessages;
//	@In private BaseDateBean basedateBean;
	
	private List<Scenario> scenarioList;
//	private List<Scenario> selectScenarioList;
//	private List<ScenarioDetail> sceDetailList = new ArrayList<ScenarioDetail>();
//	private List<ScenarioDetail> sceDetailFilter = new ArrayList<ScenarioDetail>();
	
	private Map<MarketVariableJoin, ShockData> sceDetailMap =new HashMap<MarketVariableJoin, ShockData>() ;
//	private Map<MarketVariableJoin, ShockData> sceDetialFilterMap = new HashMap<MarketVariableJoin, ShockData>();

	
	
//	@In("#{tableHifiveInit.selectHifive}")
//	private Hifive selectHifive;
	
	private TreeNode rootNode;
	private TreeNode selectNode;
	private TreeNode[] selectNodes;
	
	private Map<Scenario, Map<MarketVariableJoin, ShockData>> selectData 
		= new HashMap<Scenario, Map<MarketVariableJoin,ShockData>>();
	
	public TreeScenarioInit() {
		System.out.println("Construction TreeScenarioInit");
	}
	
//*******************************************************************	
	@Create
	public void loadScenario(){
		scenarioList = session.createCriteria(Scenario.class).list();
		loadTree();
	}
	public void onNodeSelect(NodeSelectEvent event){
		log.info("NodeSelect:#0, #1", event.getTreeNode().getData(), selectNodes.length);
		updateSceDetailMap();
	}
	public void onNodeUnselect(NodeUnselectEvent event){
		log.info("NodeUnselect:#0, #1", event.getTreeNode().getData(), selectNodes.length);
		updateSceDetailMap();
	}
	
	public void update(){
		loadScenario();
	}

//****************Getter and Setter************	

	public List<Scenario> getScenarioList() {
		return scenarioList;
	}

	public void setScenarioList(List<Scenario> scenarioList) {
		this.scenarioList = scenarioList;
	}

	public TreeNode getRootNode() {
		return rootNode;
	}
	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public TreeNode[] getSelectNodes() {
		return selectNodes;
	}
	public void setSelectNodes(TreeNode[] selectNodes) {
		this.selectNodes = selectNodes;
	}

//*****************************************************************************************	
	private void loadTree(){
		rootNode = new DefaultTreeNode("root", null);
		List<String> uppers = new ArrayList<String>();
		Map<String,TreeNode> upNode = new HashMap<String, TreeNode>();
		
		TreeNode temp ;
		for(Scenario aa : scenarioList){
			if(aa.getScenarioSet()!= null && !uppers.contains(aa.getScenarioSet())){
				uppers.add(aa.getScenarioSet());
				temp = new DefaultTreeNode("scenarioSet", aa.getScenarioSet(), rootNode);
				temp.setExpanded(true);
				upNode.put(aa.getScenarioSet(),temp);
			}
		}
		for(Scenario aa : scenarioList){
			if(aa.getScenarioSet()== null){
				temp = new DefaultTreeNode("scenario",aa.getScenarioId(), rootNode);
			}else{
				temp = new DefaultTreeNode("scenario",aa.getScenarioId(), upNode.get(aa.getScenarioSet()));
			}
		}
		
	}

		
	private void updateSceDetailMap(){
		selectData.clear();
		for(TreeNode node :selectNodes){
			String nodeId = (String)node.getData();
			Scenario sce = (Scenario)session.get(Scenario.class, nodeId);
			selectData.put(sce, sce.getSceMap());
		}
	}
}
