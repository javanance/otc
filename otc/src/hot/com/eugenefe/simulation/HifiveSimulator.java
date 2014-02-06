package com.eugenefe.simulation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.eugenefe.controller.BaseDateBean;
import com.eugenefe.entity.Hifive;
import com.eugenefe.entity.HifiveUnderlying;
import com.eugenefe.entity.IntRate;
import com.eugenefe.entity.MarketVariableJoin;
import com.eugenefe.entity.Scenario;
import com.eugenefe.entity.ScenarioDetail;
import com.eugenefe.entity.component.ShockData;
import com.eugenefe.pricevo.KisHifive;
//import org.hibernate.annotations.Filter;
//import org.jboss.seam.framework.Query;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;


@Name("hifiveSimulator")
@Scope(ScopeType.CONVERSATION)
public class HifiveSimulator {
	@Logger	private Log log;
	@In 	private Session session;
//	@In private BaseDateBean basedateBean;
	
	private List<Scenario> scenarioList;
	private List<Scenario> selectScenarioList;
	private List<ScenarioDetail> sceDetailList = new ArrayList<ScenarioDetail>();
	private List<ScenarioDetail> sceDetailFilter = new ArrayList<ScenarioDetail>();
	
	@In("#{tableHifiveInit.selectHifive}")
	private Hifive selectHifive;
	private KisHifive kisHifive;
//	private InternalHifive innerHifive;
	
	private TreeNode rootNode;
	private TreeNode[] selectNodes;
	
	public HifiveSimulator() {
		System.out.println("Construction HifiveSimulator");
	}
//*****************************************************************************
	@Create
	@Observer("evtUpdateScenario")
	public void loadScenario(){
		scenarioList = session.createCriteria(Scenario.class).list();
		loadTree();
		onNodeSelectFilter();
		
	}
	
	public void onNodeSelectFilter(){
		sceDetailFilter.clear();
//		log.info("MarketList0:#0,#1", sceDetailList.size());
		updateSceDetailList();
		
		log.info("MarketList1:#0,#1", sceDetailList.size());
		
		for(MarketVariableJoin bb : selectHifive.getMvList()){
			for(ScenarioDetail aa : sceDetailList){
				if(bb.getMvId().equals(aa.getId().getMvId())){
//					log.info("MarketList2:#0,#1", bb.getMvId(), aa.getMarketVariable().getMvId());
					sceDetailFilter.add(aa);
//					break;
				}
			}
			
		}
//		log.info("NodeSelect :#0, #1", selectHifive.getMvList().size(), sceDetailList.size());
	}
	
//	@Observer("evtHifiveSelect")
//	public void onHifiveSelect(Hifive _hifive){
//		log.info("OnSelect1:#0", _hifive);
//		this.selectHifive = _hifive;
//		onNodeSelectFilter();
//	}
	
	public void onHifiveSelect(SelectEvent event){
		this.selectHifive = (Hifive)event.getObject();
		onNodeSelectFilter();
	}

	public void onNodeDelete(){
		for(TreeNode node : selectNodes){
			String nodeId = (String)node.getData();
			Scenario sce = (Scenario)session.get(Scenario.class, nodeId);
			if(sce !=null){
				session.delete(sce);
			}
		}
		selectNodes = null;
		session.flush();
		
		loadScenario();
	}
	
	public void onNodeSelect(){
		updateSceDetailList();
	}
	
	public void onNodeUnelect(){
		updateSceDetailList();
	}

	public void onNodeSelect(NodeSelectEvent event){
		log.info("NodeSelect:#0, #1", event.getTreeNode().getData(), selectNodes.length);
		sceDetailList.clear();
		for(TreeNode node : selectNodes){
			String nodeId = (String)node.getData();
			for(Scenario sce : scenarioList){
//				log.info("Node Detail1 : #0, #1", nodeId, sce.getScenarioId());
				if(sce.getScenarioId().equals(nodeId)){
					sceDetailList.addAll(sce.getSceDetailList());
//					break;
				}
			}
		}
//		log.info("Node Detail : #0, #1", sceDetailList.size(), scenarioList.size());
	}
	public void onNodeUnselect(NodeUnselectEvent event){
		log.info("NodeUnselect:#0, #1", event.getTreeNode().getData(), selectNodes.length);
		sceDetailList.clear();
		for(TreeNode node : selectNodes){
			String nodeId = (String)node.getData();
			for(Scenario sce : scenarioList){
//				log.info("Node Detail1 : #0, #1", nodeId, sce.getScenarioId());
				if(sce.getScenarioId().equals(nodeId)){
					sceDetailList.addAll(sce.getSceDetailList());
//					break;
				}
			}
		}
		log.info("Node Detail : #0, #1", sceDetailList.size(), scenarioList.size());
	}
	
//	@Observer("evtUpdateScenario")
	public void update(List<Scenario> list){
		log.info("in the event:#0", list.size());
		loadScenario();
		loadTree();
		
	}
//****************Getter and Setter************	

	public List<Scenario> getScenarioList() {
		return scenarioList;
	}

	public void setScenarioList(List<Scenario> scenarioList) {
		this.scenarioList = scenarioList;
	}

	public List<Scenario> getSelectScenarioList() {
		return selectScenarioList;
	}

	public void setSelectScenarioList(List<Scenario> selectScenarioList) {
		this.selectScenarioList = selectScenarioList;
	}

	public List<ScenarioDetail> getSceDetailList() {
		return sceDetailList;
	}
	public void setSceDetailList(List<ScenarioDetail> sceDetailList) {
		this.sceDetailList = sceDetailList;
	}
	
	public List<ScenarioDetail> getSceDetailFilter() {
		return sceDetailFilter;
	}
	public void setSceDetailFilter(List<ScenarioDetail> sceDetailFilter) {
		this.sceDetailFilter = sceDetailFilter;
	}
	
	
	public Hifive getSelectHifive() {
		return selectHifive;
	}

	public void setSelectHifive(Hifive selectHifive) {
		this.selectHifive = selectHifive;
	}

	public KisHifive getKisHifive() {
		return kisHifive;
	}

	public void setKisHifive(KisHifive kisHifive) {
		this.kisHifive = kisHifive;
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
		List<TreeNode> tempList = new ArrayList<TreeNode>();
		if(selectNodes!=null && selectNodes.length>0){
			 tempList= Arrays.asList(selectNodes);		
		}
		TreeNode temp ;
//		log.info("AAAAAAAAAAAAAAAAAAAAAAAAAA");
		for(Scenario aa : scenarioList){
			if(aa.getScenarioSet()!= null && !uppers.contains(aa.getScenarioSet())){
				uppers.add(aa.getScenarioSet());
				temp = new DefaultTreeNode("scenarioSet", aa.getScenarioSet(), rootNode);
				temp.setExpanded(true);
				temp.setSelected(tempList.contains(temp));
				upNode.put(aa.getScenarioSet(),temp);
			}
		}
//		log.info("AAAAAAAAAAAAbbbbbbbbbbbbbbbbb");
		for(Scenario aa : scenarioList){
			if(aa.getScenarioSet()== null){
				temp = new DefaultTreeNode("scenario",aa.getScenarioId(), rootNode);
				temp.setSelected(tempList.contains(temp));
			}else{
				temp = new DefaultTreeNode("scenario",aa.getScenarioId(), upNode.get(aa.getScenarioSet()));
				temp.setSelected(tempList.contains(temp));
			}
		}
		
	}

	private void updateSceDetailList(){
		sceDetailList.clear();
//		log.info("selectnode:" );
		if(selectNodes == null){
		}else{
//			log.info("selectnode:#0" , selectNodes.length);
			for(TreeNode node : selectNodes){
				String nodeId = (String)node.getData();
				for(Scenario sce : scenarioList){
					if(sce.getScenarioId().equals(nodeId)){
						sceDetailList.addAll(sce.getSceDetailList());
						break;
					}
				}
			}
		}
	}	
}
