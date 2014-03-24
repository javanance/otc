package com.eugenefe.controller;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;

import com.eugenefe.entity.Hierarchy;
import com.eugenefe.entity.HierarchyProperty;
import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.PortfolioDefine;
import com.eugenefe.entity.Position;
import com.eugenefe.entity.component.PropertyEquation;
import com.eugenefe.enums.EBoolean;
import com.eugenefe.enums.EEquation;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;
//import org.jboss.seam.framework.Query;

@Name("tableHierarchyInit")
@Scope(ScopeType.CONVERSATION)
public class TableHierarchyInit {
	@Logger 	private Log log;
	@In			private Session session;
	@In			private StatusMessages statusMessages;
	@In			private Map<String, String> messages;	
	
	private Hierarchy selectHierarchy;
//	private HierarchyDetail selectHierDetail;
	private PropertyEquation selectHierDefine;

	private String hierId;
//	private String level;
	private int lvl;
	private String propTable;
	private String propColumn;
	private EEquation equation;
	private String condition1;
	private String condition2;

	private int index =0;

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
//	public HierarchyDetail getSelectHierDetail() {
//		return selectHierDetail;
//	}
//	public void setSelectHierDetail(HierarchyDetail selectHierDetail) {
//		this.selectHierDetail = selectHierDetail;
//	}
	
	public PropertyEquation getSelectHierDefine() {
		return selectHierDefine;
	}
	public void setSelectHierDefine(PropertyEquation selectHierDefine) {
		this.selectHierDefine = selectHierDefine;
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
	
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
//	public String getLevel() {
//		return "Level_"+lvl;
//	}
//	public void setLevel(String level) {
//		this.level = level;
//	}
	
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
			if(aa.getChangeable().getValue()){
				userHierarchyList.add(aa);
			}else{
				sysHierarchyList.add(aa);
			}
		}
		loadCombo();
	}

	public void add(){
		Hierarchy addTemp = new Hierarchy();
		int index =0;
		for(Hierarchy aa : userHierarchyList){
			 index = Math.max(index, Integer.parseInt(aa.getHierarchyId().substring(2)));
		}
		index = index +1;
		addTemp.setHierarchyId("UH"+index);
		addTemp.setChangeable(EBoolean.Y);

		hierarchyList.add(addTemp);
		userHierarchyList.add(addTemp);
	}
	
	public void saveUserHierachy(){
		for(Hierarchy aa : userHierarchyList){
			session.saveOrUpdate(aa);
		}
		log.info("Save Hierarchy:");
		session.flush();
	}
	
	public void remove(){
		log.info("ISCHANG:#0", selectHierarchy.getChangeable());
//		if(selectHierarchy.getChangeable().getValue()){
			hierarchyList.remove(selectHierarchy);
			userHierarchyList.remove(selectHierarchy);
			
			session.delete(selectHierarchy);
			session.flush();
			selectHierarchy = userHierarchyList.get(0);
//		}
	}
	
	public void addHierarchyDefine(){
		int listSize =selectHierarchy.getHierDefineIndexList().size();
		if(getLvl()<1){
			statusMessages.addFromResourceBundle(Severity.WARN, "greaterThanZero");
		}
		else if(getLvl()>0 && getLvl()<= listSize){
			selectHierDefine=selectHierarchy.getHierDefineIndexList().get(getLvl()-1);
		}
		else{
			PropertyEquation tempEquation = new PropertyEquation();
			
			HierarchyProperty tempHier = (HierarchyProperty)session.get(HierarchyProperty.class, propTable+"_"+propColumn);
			tempEquation.setProperty(tempHier);
			tempEquation.setEquation(equation);
			tempEquation.setCondition1(condition1);
			tempEquation.setCondition2(condition2);
			
			selectHierarchy.getHierDefineIndexList().add(listSize, tempEquation);
			selectHierDefine =tempEquation;
			
		}
	}

	
	public void hiearchySelect() {
		hierId= selectHierarchy.getHierarchyId();
	}
	
	public void hierarcyDefineSelect(){
		hierId= selectHierarchy.getHierarchyId();
		lvl = selectHierarchy.getHierDefineIndexList().indexOf(selectHierDefine)+1;

//		log.info("DefineSelect:#0, #1", selectHierDefine.getProperty().getTableId());
		propTable = selectHierDefine.getProperty().getTableId();
		propColumn = selectHierDefine.getProperty().getColumnId();
		equation = selectHierDefine.getEquation();
		condition1 = selectHierDefine.getCondition1();
		condition2 = selectHierDefine.getCondition2();
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
//		for(HierarchyDetail aa : selectHierarchy.getHierarchyDetailList()){
		for(PropertyEquation aa : selectHierarchy.getHierDefineIndexList()){
			session.saveOrUpdate(aa);
		}
		session.saveOrUpdate(selectHierarchy);
		session.flush();
	}
	
	
	Map<String, List<PortfolioDefine>> portfolioDefineMap = new HashMap<String, List<PortfolioDefine>>();
	Map<String, List<String>> subPortfolioMap = new HashMap<String, List<String>>();
	private Map<String, List<Method>> propPathMap = new HashMap<String, List<Method>>();

	
	public String makePortfolio(){
//		makePortfolio2(selectHierarchy.getHierarchyDetailList());
//		return "aa";
		return "/view/v694PortfolioSetting";
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
	
	
//	public void makePortfolio2(List<HierarchyDetail> portDefineList) {
	public void makePortfolio2() {		
		Object navi;
		index = 0;
		boolean outbreak=false;
		posList =session.createCriteria(Position.class).list();
		
		Map<List<PropertyEquation> , Portfolio> definePortMap = new HashMap<List<PropertyEquation>, Portfolio>();
		for (Position pos : posList) {
			outbreak=false;
			List<PropertyEquation> defineList = new ArrayList<PropertyEquation>();

			for (PropertyEquation aa : selectHierarchy.getHierDefineIndexList()) {
				navi = pos;
				List<Method> propPathList = propPathMap.get(aa.getProperty().getColumnId());
				for (Method mtd : propPathList) {
					try {
						navi = mtd.invoke(navi);
//						log.info("Navi:#0, #1", String.valueOf(navi), mtd.getName());
					} catch (Exception e) {
					}
				}

				if(!aa.getEquation().check(
						pos.getPropMap().get(aa.getProperty()), aa.getCondition1()	, aa.getCondition2())){
					outbreak=true;
					break;
				}else{
					String tempValue =pos.getPropMap().get(aa.getProperty());
					PropertyEquation temp = createPropertyEquation(aa, tempValue);
					defineList.add(selectHierarchy.getHierDefineIndexList().indexOf(aa), temp);
				}
			}
				
			if(outbreak){
				log.info("OUt");
			}else{
				Portfolio port = getPortfolioFrom(definePortMap, defineList);
		
	//			DB or alread made Portfolio
				if(port !=null){
					if(!port.getPortDetailList().contains(pos)){
						port.getPortDetailList().add(pos);
					}
				}
	//			new Portfolio
				else{
					index=index+1;
					port = new Portfolio();
					setPortInfo(port,null, defineList);
					log.info("after SetInfo:#0,#1,#2", index, port.getPortId(), port.getLevel1());
					setParentPortfolio(definePortMap, defineList, port);
					port.getPortDetailList().add(pos);
					
					definePortMap.put(defineList, port);
					log.info("New Port: #0,#1,#2", port.getPortId(), port.getLevel1(), port.getLevel2());
				}
			}
		}
			
		Transaction tx = session.getTransaction();
		tx.begin();
		for(Portfolio port : definePortMap.values()){
			log.info("POrt: #0, #1,#2,#3", port.getPortId());
//				log.info("POrt: #0, #1,#2,#3", port.getPortId(), port.getHierarchy().getHierarchyId(), port.getLevel1(),port.getLevel2());
			session.saveOrUpdate(port);
		}
		session.flush();
		tx.commit();
	}
	
	
	boolean rootFlag =true;
	
	public void makePortfolio4() {		
		posList =session.createCriteria(Position.class).list();
		index =0;
		boolean outbreak=false;
		
		Map<List<PropertyEquation> , Portfolio> definePortMap = new HashMap<List<PropertyEquation>, Portfolio>();
		for (Position pos : posList) {
			outbreak=false;
			List<PropertyEquation> defineList = new ArrayList<PropertyEquation>();
			
			for(PropertyEquation aa : selectHierarchy.getHierDefineIndexList()){
//				log.info("Pos Prop1:#0, #1", aa.getHierProperty().getPropId(), pos.getPropMap().get(aa.getHierProperty()) );
//				if(!aa.getEquation().equals(EEquation.DISTINCT)){
//					rootFlag = true;
//				}
				if(!aa.getEquation().check(
					pos.getPropMap().get(aa.getProperty()), aa.getCondition1()	, aa.getCondition2())){
					outbreak=true;
					break;
				}else{
					String tempValue =pos.getPropMap().get(aa.getProperty());
					
					PropertyEquation temp = createPropertyEquation(aa, tempValue);
					defineList.add(selectHierarchy.getHierDefineIndexList().indexOf(aa), temp);
				}
			}
			
			if(outbreak){
				log.info("OUt");
			}else{
				
			
				Portfolio port = getPortfolioFrom(definePortMap, defineList);
				
	//			DB or alread made Portfolio
				if(port !=null){
					if(!port.getPortDetailList().contains(pos)){
						port.getPortDetailList().add(pos);
					}
				}
	//			new Portfolio
				else{
					
					index=index+1;
					port = new Portfolio();
					setPortInfo(port,null, defineList);
					log.info("after SetInfo:#0,#1,#2", index, port.getPortId(), port.getLevel1());
					setParentPortfolio(definePortMap, defineList, port);
					port.getPortDetailList().add(pos);
					
					definePortMap.put(defineList, port);
					log.info("New Port: #0,#1,#2", port.getPortId(), port.getLevel1(), port.getLevel2());
				}
			}
		}
		
		
		Transaction tx = session.getTransaction();
		tx.begin();
		for(Portfolio port : definePortMap.values()){
			log.info("POrt: #0, #1,#2,#3", port.getPortId());
//			log.info("POrt: #0, #1,#2,#3", port.getPortId(), port.getHierarchy().getHierarchyId(), port.getLevel1(),port.getLevel2());
			session.saveOrUpdate(port);
		}
		session.flush();
		tx.commit();
		
//		return "/view/v694PortfolioSetting";
	}
	
	private PropertyEquation createPropertyEquation(PropertyEquation propEquation, String value){
		PropertyEquation tempEquation = new PropertyEquation();

		tempEquation.setProperty(propEquation.getProperty());
		
		if (propEquation.getEquation().equals(EEquation.DISTINCT)) {
			tempEquation.setEquation(EEquation.EQUAL);
			tempEquation.setCondition1(value);
			tempEquation.setCondition2(null);
//			log.info("Equation :#0,#1", tempEquation.getCondition1(), value);
			
		} else {
			tempEquation.setEquation(propEquation.getEquation());
			tempEquation.setCondition1(propEquation.getCondition1());
			tempEquation.setCondition2(propEquation.getCondition2());
//			log.info("Equation :#0,#1,#2", tempEquation.getCondition1(), hierDetail.getCondition1(), value);
		}
		return tempEquation;
	}
	
	private void setParentPortfolio(Map<List<PropertyEquation>, Portfolio> definePortMap, List<PropertyEquation> defineList , Portfolio child){
		int totalSize = defineList.size();
		
		List<PropertyEquation> tempList = new ArrayList<PropertyEquation>();
		
		for(int i=0; i<totalSize-1; i++){
			tempList.add(i, defineList.get(i));
		}
		
		Portfolio parent = getPortfolioFrom(definePortMap, tempList);
		log.info("Index11:#0",index);
		if(parent ==null){
			index = index+1;
			log.info("Index1:#0,#1",index, child.getPortId());
			parent = new Portfolio();
			setPortInfo(parent, null,tempList);
			child.setParentPortfolio(parent);

			definePortMap.put(tempList, parent);
//			if(rootFlag){
				if(totalSize>1 ){
					setParentPortfolio(definePortMap, tempList, parent);
				}
//			}else{
//				if(totalSize>2 ){
//					setParentPortfolio(definePortMap, tempList, parent);
//				}
//			}
		}else{
			child.setParentPortfolio(parent);	
		}
	}
	
	private Portfolio getPortfolioFrom(Map<List<PropertyEquation>, Portfolio> definePortMap, List<PropertyEquation> defineList){
		Portfolio port = getPortfolioFromDB(defineList);
		
		if(port == null){
			for(List<PropertyEquation> list : definePortMap.keySet()){
				if(list.equals(defineList)){
//				if(list.containsAll(defineList) && defineList.containsAll(list)){
					return definePortMap.get(list);
				}
			}
			return null;
		}else{
			return port;
		}
	}
	
	private Portfolio getPortfolioFromDB( List<PropertyEquation> defineList){
		for(Portfolio port : selectHierarchy.getPortfolioList()){
			if(port.getPortDefineIndexList().equals(defineList)){
				return port;
			}	
		}
//		return new Portfolio();
		return null;
	}
	
	private void setPortInfo(Portfolio newPort, Portfolio parentPort , List<PropertyEquation> defineList){
		String tempName ="";
		newPort.setPortId(selectHierarchy.getHierarchyId() + "_" + index);
		
		newPort.setHierarchy(selectHierarchy);
		newPort.setParentPortfolio(parentPort);
		newPort.setPortType("HIER");
		newPort.setWeight(new BigDecimal(1));
		
		newPort.setPortDefineIndexList(defineList);
		
		switch (defineList.size()) {
		case 5:
			newPort.setLevel5(defineList.get(4).getCondition1());
//			tempName = defineList.get(4).getCondition1(); 
		case 4:
			newPort.setLevel4(defineList.get(3).getCondition1());
//			tempName = defineList.get(3).getCondition1();
//			tempName = "_" +defineList.get(3).getCondition1()+tempName;
		case 3:
			newPort.setLevel3(defineList.get(2).getCondition1());
//			tempName = defineList.get(2).getCondition1();
//			tempName = "_" +defineList.get(2).getCondition1()+tempName;
		case 2:
			newPort.setLevel2(defineList.get(1).getCondition1());
//			tempName = defineList.get(1).getCondition1();
//			tempName = "_" +defineList.get(1).getCondition1()+tempName;
		case 1:
			newPort.setLevel1(defineList.get(0).getCondition1());
//			tempName = defineList.get(0).getCondition1()+tempName;
		default:
			break;
		}
		if(!defineList.isEmpty()){
			tempName = defineList.get(defineList.size()-1).getCondition1();
			if(tempName==null){
				tempName = "NULL";
			}
					
		}

		if(tempName =="" ){
			tempName =selectHierarchy.getHierarchyName();
		}
		newPort.setPortName(tempName);
	}
}
