package com.eugenefe.controller;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.management.RuntimeErrorException;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Events;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;
import org.mvel2.ast.ForNode;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.FilterEvent;

import com.eugenefe.entity.Counterparty;
import com.eugenefe.entity.Futures;
import com.eugenefe.entity.Hierarchy;
import com.eugenefe.entity.HierarchyDetail;
import com.eugenefe.entity.HierarchyDetailId;
import com.eugenefe.entity.Hifive;
import com.eugenefe.entity.HifiveStrike;
import com.eugenefe.entity.HifiveStrikeId;
import com.eugenefe.entity.HifiveUnderlying;
import com.eugenefe.entity.HifiveUnderlyingId;
import com.eugenefe.entity.IntRate;
import com.eugenefe.entity.IrcBucket;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.MarketVariableJoin;
import com.eugenefe.entity.MarketVariableNew;
import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.PortfolioDefine;
import com.eugenefe.entity.Position;
import com.eugenefe.entity.PricingMaster;
import com.eugenefe.entity.PricingMasterId;
import com.eugenefe.entity.PricingUnderlyings;
import com.eugenefe.entity.PricingUnderlyingsId;
import com.eugenefe.entity.ProductGreeks;
import com.eugenefe.entity.ProductGreeksId;
import com.eugenefe.entity.ProductReturn;
import com.eugenefe.entity.ProductReturnId;
import com.eugenefe.entity.Stock;
import com.eugenefe.entity.StockIndex;
import com.eugenefe.entity.component.HisData;
import com.eugenefe.entity.component.PriceData;
import com.eugenefe.enums.EEquation;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.pricer.hifive.IHiFiveMc;
import com.eugenefe.pricevo.KisHifive;
//import org.jboss.seam.framework.Query;
import com.eugenefe.session.CounterpartyList;
import com.eugenefe.session.StockList;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;
import com.eugenefe.util.FnCalendar;
import com.eugenefe.util.MarketVariableType;
import com.sun.corba.se.impl.orbutil.closure.Future;

@Name("tableHierarchyInit")
@Scope(ScopeType.CONVERSATION)
public class TableHierarchyInit {
	@Logger 	private Log log;
	@In			private Session session;
	@In			private StatusMessages statusMessages;
	@In			private Map<String, String> messages;	
	
	private Hierarchy selectHierarchy;
	private HierarchyDetail selectHierDetail;

	private String hierId;
	private String level;
	private String propTable;
	private String propColumn;
	private EEquation equation;
	private String condition1;
	private String condition2;

	private List<EEquation> equationList = new ArrayList<EEquation>();
	private Map<String, List<String>> propMap = new HashMap<String, List<String>>();
	private Map<Method, List<Method>> propMethodMap = new HashMap<Method, List<Method>>();
	List<Position> posList = new ArrayList<Position>();
	
	public Map<String, List<String>> getPropMap() {
		return propMap;
	}
	public void setPropMap(Map<String, List<String>> propMap) {
		this.propMap = propMap;
	}
	public List<Position> getPosList() {
		return posList;
	}
	public void setPosList(List<Position> posList) {
		this.posList = posList;
	}
	public Map<Method, List<Method>> getPropMethodMap() {
		return propMethodMap;
	}
	public void setPropMethodMap(Map<Method, List<Method>> propMethodMap) {
		this.propMethodMap = propMethodMap;
	}
	public List<EEquation> getEquationList() {
		return equationList;
	}
	public void setEquationList(List<EEquation> equationList) {
		this.equationList = equationList;
	}
	public HierarchyDetail getSelectHierDetail() {
		return selectHierDetail;
	}
	public void setSelectHierDetail(HierarchyDetail selectHierDetail) {
		this.selectHierDetail = selectHierDetail;
	}
	public Hierarchy getSelectHierarchy() {
		return selectHierarchy;
	}
	public void setSelectHierarchy(Hierarchy selectHierarchy) {
		this.selectHierarchy = selectHierarchy;
	}
	private List<Hierarchy> userHierarchyList = new ArrayList<Hierarchy>();
	private List<Hierarchy> sysHierarchyList= new ArrayList<Hierarchy>();
	private List<Hierarchy> hierarchyList;
	
	public List<Hierarchy> getHierarchyList() {
		return hierarchyList;
	}
	public void setHierarchyList(List<Hierarchy> hierarchyList) {
		this.hierarchyList = hierarchyList;
	}
	public List<Hierarchy> getUserHierarchyList() {
		return userHierarchyList;
	}
	public void setUserHierarchyList(List<Hierarchy> userHierarchyList) {
		this.userHierarchyList = userHierarchyList;
	}
	public List<Hierarchy> getSysHierarchyList() {
		return sysHierarchyList;
	}
	public void setSysHierarchyList(List<Hierarchy> sysHierarchyList) {
		this.sysHierarchyList = sysHierarchyList;
	}
	
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getPropTable() {
		return propTable;
	}
	public void setPropTable(String propTable) {
		this.propTable = propTable;
	}
	public String getPropColumn() {
		return propColumn;
	}
	public void setPropColumn(String propColumn) {
		this.propColumn = propColumn;
	}
	public EEquation getEquation() {
		return equation;
	}
	public void setEquation(EEquation equation) {
		this.equation = equation;
	}
	public String getCondition1() {
		return condition1;
	}
	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}
	public String getCondition2() {
		return condition2;
	}
	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}
	
	
	public String getHierId() {
		return hierId;
	}
	public void setHierId(String hierId) {
		this.hierId = hierId;
	}
	//**********************************************************************	
	public TableHierarchyInit() {
	}
	
	@Create
	public void create(){
		hierarchyList = session.createCriteria(Hierarchy.class).list();
		for(Hierarchy aa : hierarchyList){
			if(aa.getChangeable().equals("Y")){
				userHierarchyList.add(aa);
			}else{
				sysHierarchyList.add(aa);
			}
		}
		loadCombo();
	}

	public void add(){
		Hierarchy addTemp = new Hierarchy();
		int index =userHierarchyList.size()+1;

		addTemp.setHierarchyId("UH"+index);
		addTemp.setChangeable("Y");
		
		hierarchyList.add(addTemp);
		userHierarchyList.add(addTemp);
	}
	
	public void saveUserHierachy(){
		for(Hierarchy aa : userHierarchyList){
			session.saveOrUpdate(aa);
		}
		session.flush();
	}
	public void remove(){
		
	}
	
	public void addHierarchyDetail(){
		boolean addFlag = true;
		for(HierarchyDetail aa : selectHierarchy.getHierarchyDetailList()){
			if(aa.getId().getHierLevel().equals(level)){
				selectHierDetail=aa;
				addFlag = false;
				break;
			}
		}
		if(addFlag){
			HierarchyDetail temp = new HierarchyDetail();
			temp.setId(new HierarchyDetailId(hierId, level));
			selectHierarchy.getHierarchyDetailList().add(temp);
			selectHierDetail = temp;

		}
			selectHierDetail.setTableId(propTable);
			selectHierDetail.setColumnId(propColumn);
			selectHierDetail.setEquation(equation);
			selectHierDetail.setCondition1(condition1);
			selectHierDetail.setCondition2(condition2);			
		
		

	}
	public void hiearchySelect() {
		hierId= selectHierarchy.getHierarchyId();
	}
	
	public void hiearchyDetailSelect() {
		hierId= selectHierarchy.getHierarchyId();
		level = selectHierDetail.getId().getHierLevel();
		propTable = selectHierDetail.getTableId();
		propColumn = selectHierDetail.getColumnId();
		equation = selectHierDetail.getEquation();
		condition1 = selectHierDetail.getCondition1();
		condition2 = selectHierDetail.getCondition2();
	}
	
//****************************	
	public void loadCombo() {
		Class klass = Position.class;
		Class upperKlass = Portfolio.class;
		Method upperMethod = null;
		try {
			upperMethod = upperKlass.getDeclaredMethod("getPosition");
		} catch (Exception e) {

		}

		recursiveTableCoulumn(klass, upperMethod);
		propPath(klass, null);

		equationList = Arrays.asList(EEquation.values());

//		for (Map.Entry<String, List<Method>> entry : propPathMap.entrySet()) {
//			log.info("entry : #0,#1", entry.getKey(), entry.getValue().size());
//			for (Method aa : entry.getValue()) {
//				log.info("entry List : #0,#1", aa.getName());
//			}
//		}
//		posList = session.createCriteria(Position.class).list();
	}
	
	private void recursiveTableCoulumn(Class klazz, Method parent) {
		Class<?> methodRtnKlazz;
		Class<?> genericTypeKlazz;
		// String temp = "";
		String tempParent;

		if (parent == null) {
			tempParent = "getPosition";
		} else {
			tempParent = parent.getName();
		}

		List<Method> propPathList = new ArrayList<Method>();

		List<String> childProp = new ArrayList<String>();
		Method[] tempMethod = klazz.getDeclaredMethods();

		propMethodMap.put(parent, Arrays.asList(tempMethod));

		for (Method mtd : tempMethod) {
			AnnoMethodTree aa = (AnnoMethodTree) mtd.getAnnotation(AnnoMethodTree.class);
			if (aa != null && aa.aggregatable()) {
				// childProp.add(messages.get(mtd.getName()));
				childProp.add(mtd.getName());
			}
		}
		propMap.put(tempParent, childProp);


		for (Method mtd : tempMethod) {
			methodRtnKlazz = mtd.getReturnType();
			genericTypeKlazz = null;
			AnnoMethodTree aa = (AnnoMethodTree) mtd.getAnnotation(AnnoMethodTree.class);
			if (methodRtnKlazz == klazz) {
				break;
			}
			if (aa != null && aa.aggregatable() && methodRtnKlazz.isAnnotationPresent(AnnoNavigationFilter.class)) {
				recursiveTableCoulumn(methodRtnKlazz, mtd);
			}
		}
	}

	private void propPath(Class klazz, Method parent) {
		Class<?> methodRtnKlazz;
		Class<?> genericTypeKlazz;

		Method[] tempMethod = klazz.getDeclaredMethods();
		List<Method> upperPath = new ArrayList<Method>();
		if (parent != null) {
			upperPath = propPathMap.get(parent.getName());
		}

		for (Method mtd : tempMethod) {
			AnnoMethodTree aa = (AnnoMethodTree) mtd.getAnnotation(AnnoMethodTree.class);
			if (aa != null && aa.aggregatable()) {
				List<Method> propPathList = new ArrayList<Method>();
				for (Method bb : upperPath) {
					// log.info("Method1 : #0,#1", bb);
					propPathList.add(bb);
				}
				propPathList.add(mtd);
				propPathMap.put(mtd.getName(), propPathList);
			}
		}

		for (Method mtd : tempMethod) {
			methodRtnKlazz = mtd.getReturnType();
			genericTypeKlazz = null;
			AnnoMethodTree aa = (AnnoMethodTree) mtd.getAnnotation(AnnoMethodTree.class);

			if (methodRtnKlazz == klazz) {
				break;
			}
			if (aa != null && aa.aggregatable() && methodRtnKlazz.isAnnotationPresent(AnnoNavigationFilter.class)) {
				propPath(methodRtnKlazz, mtd);
			}
		}
	}
	
	public void saveHierDetail(){
		for(HierarchyDetail aa : selectHierarchy.getHierarchyDetailList()){
			session.saveOrUpdate(aa);
		}
		session.flush();
	}
	
	
	Map<String, List<PortfolioDefine>> portfolioDefineMap = new HashMap<String, List<PortfolioDefine>>();
	Map<String, List<String>> subPortfolioMap = new HashMap<String, List<String>>();
	private Map<String, List<Method>> propPathMap = new HashMap<String, List<Method>>();

	
	public String makePortfolio(){

		return "/view/v694PortfolioSetting";
	}
	
	
	private void makePortfolio1(List<PortfolioDefine> portDefineList) {
		portfolioDefineMap.clear();
		Object navi;
		boolean contained;
		int index = 1;
		String portId =null;
		
		
		Map<String, List<Position>> portPositionMap = new HashMap<String, List<Position>>();
		Map<String, Set<String>> aaa = new HashMap<String, Set<String>>();

		List<PortfolioDefine> portList = new ArrayList<PortfolioDefine>();
		Set<String> valueList = new HashSet<String>();

		for (Position pos : posList) {
			String tempPortId ="";
			boolean positionFlag = true;
			contained = false;
			// navi =pos;
			portList = new ArrayList<PortfolioDefine>();

			for (PortfolioDefine aa : portDefineList) {
				navi = pos;
				valueList = new HashSet<String>();
				List<Method> propPathList = propPathMap.get(aa.getPropColumn());

				for (Method mtd : propPathList) {
					try {
						navi = mtd.invoke(navi);
//						log.info("Naiv:#0, #1", String.valueOf(navi), mtd.getName());
					} catch (Exception e) {
					}
				}

				if (!aa.getEquation().check(navi, aa.getCondition1(), aa.getCondition2())) {
					positionFlag = false;
					break;
				}
				if (aa.getEquation().equals(EEquation.DISTINCT)) {
					PortfolioDefine temp = new PortfolioDefine();
					temp.setPortPrefix(aa.getPortPrefix());
					temp.setLevel(aa.getLevel());
					temp.setPropTable(aa.getPropTable());
					temp.setPropColumn(aa.getPropColumn());
					temp.setEquation(EEquation.EQUAL);
					temp.setCondition1(String.valueOf(navi));
					portList.add(temp);
					
					tempPortId =tempPortId + String.valueOf(navi);

					if (aaa.get(aa.getPropColumn()) != null) {
						valueList = aaa.get(aa.getPropColumn());
						valueList.add(String.valueOf(navi));
					} else {
						valueList.add(String.valueOf(navi));
					}
					aaa.put(aa.getPropColumn(), valueList);
				} else {
					portList.add(aa);
				}
//				log.info("Prop Value :#0, #1", aa.getPropColumn(), String.valueOf(navi));
			}

			for (Map.Entry<String, List<PortfolioDefine>> entry : portfolioDefineMap.entrySet()) {
				if (entry.getValue().containsAll(portList)) {
//					log.info("Contains");
					contained = true;
					if (portPositionMap.get(entry.getKey()) == null) {
						List<Position> temp = new ArrayList<Position>();
						temp.add(pos);
						portPositionMap.put(entry.getKey(), temp);
					}
					portPositionMap.get(entry.getKey()).add(pos);
					break;
				} else {
				}
			}
			if (!contained && positionFlag) {
//				portId = getPortPrefix() + "_" + index;
				portId = tempPortId;
				portfolioDefineMap.put(portId, portList);
				
				
				if (portPositionMap.get(portId) == null) {
					List<Position> temp = new ArrayList<Position>();
					temp.add(pos);
					portPositionMap.put(portId, temp);
				}
				portPositionMap.get(portId).add(pos);
				
				for(PortfolioDefine aa : portList){
					log.info("PortList : #0,#1", portId, aa.getCondition1());
				}
				setPortTree(portId, portList);

				index = index + 1;
				
			}
		}

		for (Map.Entry<String, List<PortfolioDefine>> entry : portfolioDefineMap.entrySet()) {
			for (PortfolioDefine pos : entry.getValue()) {
//				log.info("Port:#0,#1,#2", entry.getValue().size(), entry.getKey(), pos.getCondition1());
			}
		}

		for (Map.Entry<String, List<String>> entry : subPortfolioMap.entrySet()) {
			for (String port: entry.getValue()) {
				log.info("Sub Port:#0,#1,#2", entry.getValue().size(), entry.getKey(), port);
			}
		}
		
		for (Map.Entry<String, List<Position>> entry : portPositionMap.entrySet()) {
			for (Position pos : entry.getValue()) {
//				log.info("Port:#0,#1,#2", entry.getValue().size(), entry.getKey(), pos.getName());
			}
		}
	}

	private void setPortTree(String portId, List<PortfolioDefine> pfList) {
		boolean flag=true;
//		log.info("List Size : #0, #1", portId, pfList.size());
		if (pfList != null && pfList.size() > 1) {
			String temp1 = "P_" + portId;
			List<PortfolioDefine> temp = new ArrayList<PortfolioDefine>();
			for (PortfolioDefine aa : pfList) {
				if (!aa.getLevel().equals("Level_" + pfList.size())) {
//					log.info("AAAA:#0,#1", aa.getLevel(), "Level_" + pfList.size());
					temp.add(aa);
				}
			}

			
			for(Map.Entry<String, List<PortfolioDefine>> entry : portfolioDefineMap.entrySet()){
				if (temp.containsAll(entry.getValue()) && entry.getValue().containsAll(temp)){
					temp1 = entry.getKey();
					flag = false;
					break;
				}
				
			}
			if(subPortfolioMap.get(temp1)==null){
				List<String> tempSub = new ArrayList<String>();
				tempSub.add(portId);
				subPortfolioMap.put(temp1, tempSub);
			}else{
				log.info("AAAA:#0,#1", temp1, portId);
				subPortfolioMap.get(temp1).add(portId);
			}

			if(flag){
//				log.info("ABBB:#0,#1", temp1, temp.size());
				portfolioDefineMap.put(temp1, temp);
				setPortTree(temp1, temp);		
			}
		}	
	

	}
}
