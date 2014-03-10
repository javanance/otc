package com.eugenefe.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.primefaces.event.CellEditEvent;

import sun.reflect.Reflection;

import com.eugenefe.entity.Bizunit;
import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.PortfolioDefine;
import com.eugenefe.entity.Position;
import com.eugenefe.enums.EEquation;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;

//import org.jboss.seam.framework.Query;

@Name("tablePositionDefineInit")
@Scope(ScopeType.CONVERSATION)
public class TablePositionDefineInit {
	@Logger		private Log log;
	@In			private Map<String, String> messages;
	@In			private Session session;

	List<Position> posList = new ArrayList<Position>();
	private List<PortfolioDefine> pfDefineList;
	private Map<String, List<String>> propMap = new HashMap<String, List<String>>();
	private Map<Method, List<Method>> propMethodMap = new HashMap<Method, List<Method>>();

	private Map<String, List<Method>> propPathMap = new HashMap<String, List<Method>>();

	private PortfolioDefine selectPortfolioDefine;

	private String portPrefix;
	private String level;
	private String propTable;
	private String propColumn;
	private EEquation equation;
	private String condition1;
	private String condition2;

	private List<EEquation> equationList = new ArrayList<EEquation>();

	public TablePositionDefineInit() {
		System.out.println("Construction TablePositionDefineInit");
	}

	// **********************************************************************
	@Create
	public void create() {
		Class klass = Position.class;
		Class upperKlass = Portfolio.class;
		Method upperMethod = null;
		try {
			upperMethod = upperKlass.getDeclaredMethod("getPosition");
		} catch (Exception e) {

		}

		recursiveTableCoulumn(klass, upperMethod);
		propPath(klass, null);
		pfDefineList = new ArrayList<PortfolioDefine>();
		level = "Level_1";

		equationList = Arrays.asList(EEquation.values());
		// selectPortfolioDefine = new PortfolioDefine();
		// selectPortfolioDefine.setLevel(level);

		for (Map.Entry<String, List<Method>> entry : propPathMap.entrySet()) {
			log.info("entry : #0,#1", entry.getKey(), entry.getValue().size());
			for (Method aa : entry.getValue()) {
				log.info("entry List : #0,#1", aa.getName());
			}
		}

		posList = session.createCriteria(Position.class).list();
	}

	public void updateTable() {
		for (PortfolioDefine aa : pfDefineList) {
			aa.setPortPrefix(portPrefix);
		}
	}

	public void addPortfolioDefine() {
		log.info("Size:#0", pfDefineList.size());
		for (PortfolioDefine aa : pfDefineList) {
			if (aa.getLevel().equals(level)) {
				setPortfolioDefine(aa);
				return;
			}
		}

		PortfolioDefine tempDefine = new PortfolioDefine();
		setPortfolioDefine(tempDefine);
		pfDefineList.add(tempDefine);
		int index = pfDefineList.size() + 1;
		level = "Level_" + index;

		log.info("Size1:#0", pfDefineList.size());
	}

	public void makePortfolio() {
		makePortfolio1(pfDefineList);
	}

	// public void makePortfolioBatch(){
	// for(Map<String, List<PortfolioDefine>> entry : p){
	// makePortfolio1(pfDefineList);
	// }
	// }

	public void rowSelect() {
		level = selectPortfolioDefine.getLevel();
		propTable = selectPortfolioDefine.getPropTable();
		propColumn = selectPortfolioDefine.getPropColumn();
		equation = selectPortfolioDefine.getEquation();
		condition1 = selectPortfolioDefine.getCondition1();
		condition2 = selectPortfolioDefine.getCondition2();
	}

	public void clearTable() {
		level = "Level_1";
		pfDefineList.clear();
	}

	// public void cellEdit(CellEditEvent event){
	// log.info("Cell Edit :#0, #1", event.getRowIndex(),
	// event.getColumn().getClientId());
	// log.info("Cell Edit :#0, #1", event.getOldValue(), event.getNewValue());
	// propList.clear();
	// if(event.getColumn().getClientId().contains("PropTable")){
	// propList = propMap.get(event.getNewValue().toString());
	// }
	//
	// for(String aa : propList){
	// log.info("Cell Edit for:#0, #1", aa, selectPortfolioDefine.getLevel());
	// }
	// }

	// ***********************************Getter and Setter*********************

	public List<PortfolioDefine> getPfDefineList() {
		return pfDefineList;
	}

	public void setPfDefineList(List<PortfolioDefine> pfDefineList) {
		this.pfDefineList = pfDefineList;
	}

	public Map<String, List<String>> getPropMap() {
		return propMap;
	}

	public void setPropMap(Map<String, List<String>> propMap) {
		this.propMap = propMap;
	}

	public Map<Method, List<Method>> getPropMethodMap() {
		return propMethodMap;
	}

	public void setPropMethodMap(Map<Method, List<Method>> propMethodMap) {
		this.propMethodMap = propMethodMap;
	}

	public PortfolioDefine getSelectPortfolioDefine() {
		return selectPortfolioDefine;
	}

	public void setSelectPortfolioDefine(PortfolioDefine selectPortfolioDefine) {
		this.selectPortfolioDefine = selectPortfolioDefine;
	}

	public String getPortPrefix() {
		return portPrefix;
	}

	public void setPortPrefix(String portPrefix) {
		this.portPrefix = portPrefix;
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

	public List<EEquation> getEquationList() {
		return equationList;
	}

	public void setEquationList(List<EEquation> equationList) {
		this.equationList = equationList;
	}

	// ********************Private Method***********************

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
		// propMap.put(messages.get(tempParent), childProp);

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

	private void setPortfolioDefine(PortfolioDefine pfDefine) {
		pfDefine.setPortPrefix(portPrefix);
		pfDefine.setLevel(level);
		pfDefine.setPropTable(propTable);
		pfDefine.setPropColumn(propColumn);
		if(equation != null){
			pfDefine.setEquation(EEquation.DISTINCT);
		}else{
			pfDefine.setEquation(equation);
		}
		if (equation != null && equation.getOperandSize() == 2) {
			pfDefine.setCondition1(condition1);
			pfDefine.setCondition2(condition2);
		} else if (equation != null && equation.getOperandSize() == 1) {
			pfDefine.setCondition1(condition1);
		}
	}

	Map<String, List<PortfolioDefine>> portfolioDefineMap = new HashMap<String, List<PortfolioDefine>>();
	Map<String, List<String>> subPortfolioMap = new HashMap<String, List<String>>();
	
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
