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
import com.eugenefe.entity.MvType;
import com.eugenefe.entity.Scenario;
import com.eugenefe.entity.ScenarioDetail;
import com.eugenefe.entity.component.ShockData;
import com.eugenefe.pricevo.KisHifive;
//import org.hibernate.annotations.Filter;
//import org.jboss.seam.framework.Query;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;


@Name("hierPortfolio")
@Scope(ScopeType.CONVERSATION)
public class HierarchicalPortfolio {
	@Logger	private Log log;
	@In 	private Session session;
//	@In private BaseDateBean basedateBean;
	
	
	private TreeNode rootNode;
	private TreeNode superNode;
	private TreeNode[] selectNodes;
	
	private List<MvType> mvTypeList = new ArrayList<MvType>();
	private Map<MvType, List<MvType>> mvTypeMap = new  HashMap<MvType, List<MvType>>();
	public HierarchicalPortfolio() {
		System.out.println("Construction HierarchicalPortfolio");
	}
	
//*****************************************************************************

	@Create
	public void create(){
		mvTypeList = session.createCriteria(MvType.class).list();
		
		loadTree();
	}
	
	private void loadTree(){
		superNode = new DefaultTreeNode( new MvType("superRoot", "SuperRoot", null), null);
		rootNode = new DefaultTreeNode("root", superNode);

		superNode.setExpanded(true);
		
		List<TreeNode> tempList = new ArrayList<TreeNode>();
		TreeNode temp ;
		
		if(selectNodes!=null && selectNodes.length>0){
		}

		for(MvType aa : mvTypeList){
			if(aa.getParentMvType()== null){
				temp = new DefaultTreeNode(aa.getMvType(), rootNode);
			}else{
				
			}

		}
		
		
		for(MvType aa : mvTypeList){
//			rootNode = new DefaultTreeNode(aa.getMvType(), superNode);
			rootNode = new DefaultTreeNode("root", aa, superNode);
			rootNode.setExpanded(true);
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
}
