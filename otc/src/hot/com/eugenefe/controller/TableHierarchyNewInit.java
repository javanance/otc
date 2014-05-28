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
import org.primefaces.model.LazyDataModel;

import com.eugenefe.entity.Hierarchy;
import com.eugenefe.entity.HierarchyProperty;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.PortfolioDefine;
import com.eugenefe.entity.Position;
import com.eugenefe.entity.component.PropertyEquation;
import com.eugenefe.entity.component.PropertyEquationNew;
import com.eugenefe.enums.EBoolean;
import com.eugenefe.enums.EEquation;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;
import com.eugenefe.util.DynamicColumnModel;
//import org.jboss.seam.framework.Query;

@Name("tableHierarchyNewInit")
@Scope(ScopeType.CONVERSATION)
public class TableHierarchyNewInit {
	@Logger 	private Log log;
	@In			private Session session;
	@In			private StatusMessages statusMessages;
	@In			private Map<String, String> messages;	
	

	private Hierarchy selectHierarchy;
	private PropertyEquationNew selectHierDefine;

	private String hierId;
//	private String level;
	private int lvl;
	private String propGroup;
	private String propName;
	private EEquation equation;
	private String condition1;
	private String condition2;
	
	private int index =0;
	private List<Hierarchy> userHierarchyList = new ArrayList<Hierarchy>();
	private List<Hierarchy> sysHierarchyList= new ArrayList<Hierarchy>();
	private List<Hierarchy> hierarchyList;

	private List<EEquation> equationList = new ArrayList<EEquation>();
	private Map<String, List<DynamicColumnModel>> columModelMap ;
	private Map<String, List<String>> columnMap ;

	
	public Map<String, List<String>> getColumnMap() {
		return columnMap;
	}
	public void setColumnMap(Map<String, List<String>> columnMap) {
		this.columnMap = columnMap;
	}
	public String getPropGroup() {
		return propGroup;
	}
	public void setPropGroup(String propGroup) {
		this.propGroup = propGroup;
	}
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public Map<String, List<DynamicColumnModel>> getColumModelMap() {
		return columModelMap;
	}
	public void setColumModelMap(Map<String, List<DynamicColumnModel>> columModelMap) {
		this.columModelMap = columModelMap;
	}
	private List<Position> posList = new ArrayList<Position>();

	public List<Position> getPosList() {
		return posList;
	}
	public void setPosList(List<Position> posList) {
		this.posList = posList;
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
	
	public PropertyEquationNew getSelectHierDefine() {
		return selectHierDefine;
	}
	public void setSelectHierDefine(PropertyEquationNew selectHierDefine) {
		this.selectHierDefine = selectHierDefine;
	}
	public Hierarchy getSelectHierarchy() {
		return selectHierarchy;
	}
	public void setSelectHierarchy(Hierarchy selectHierarchy) {
		this.selectHierarchy = selectHierarchy;
	}
	
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
	public TableHierarchyNewInit() {
	}
	
	@Create
	public void create(){
		hierarchyList = session.createCriteria(Hierarchy.class).list();
		for(Hierarchy aa : hierarchyList){
			if(aa.getChangeable()==null || aa.getChangeable().getValue()){
				userHierarchyList.add(aa);
			}else{
				sysHierarchyList.add(aa);
			}
		}
		log.info("In the Create : #0", hierarchyList.size());
		columModelMap  = Position.getColumnModelMap();
		columnMap  = Position.getColumnMap();
		equationList = Arrays.asList(EEquation.values());
		
		for(Map.Entry<String, List<String>> entry : columnMap.entrySet()){
			log.info("column model : #0, #1", entry.getKey(), entry.getValue().size());
			for(String aa : entry.getValue()){
				log.info("column model : #0", aa);
			}
		}
		
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
		log.info("Update0 : #0, #1,#2", listSize, getLvl());
		if(getLvl()<1){
			statusMessages.addFromResourceBundle(Severity.WARN, "greaterThanZero");
			log.info("Update1 : #0, #1,#2", selectHierDefine.getEquation(), selectHierDefine.getCondition1());
		}
		else if(getLvl()>0 && getLvl()<= listSize){
			selectHierDefine.setPropGroup(propGroup);
			selectHierDefine.setPropName(propName);
			selectHierDefine.setEquation(equation);
			selectHierDefine.setCondition1(condition1);
			selectHierDefine.setCondition2(condition2);
			
//			selectHierDefine=selectHierarchy.getHierDefineIndexList().get(getLvl()-1);
			log.info("Update2 : #0, #1,#2", selectHierDefine.getEquation(), selectHierDefine.getCondition1());
		}
		else{
			PropertyEquationNew tempEquation = new PropertyEquationNew();
			
			tempEquation.setPropGroup(propGroup);
			tempEquation.setPropName(propName);
			tempEquation.setEquation(equation);
			tempEquation.setCondition1(condition1);
			tempEquation.setCondition2(condition2);
			
			selectHierarchy.getHierDefineIndexList().add(listSize, tempEquation);
			selectHierDefine =tempEquation;
			log.info("Update3 : #0, #1,#2", selectHierDefine.getEquation(), selectHierDefine.getCondition1());
		}
	}

	
	public void selectHierarchy() {
		hierId= selectHierarchy.getHierarchyId();
		log.info("select Hier: #0", hierId);
	}
	
	public void hierarcyDefineSelect(){
		hierId= selectHierarchy.getHierarchyId();
		lvl = selectHierarchy.getHierDefineIndexList().indexOf(selectHierDefine)+1;

//		log.info("DefineSelect:#0, #1", selectHierDefine.getProperty().getTableId());
		propGroup = selectHierDefine.getPropGroup();
		propName = selectHierDefine.getPropName();
		equation = selectHierDefine.getEquation();
		condition1 = selectHierDefine.getCondition1();
		condition2 = selectHierDefine.getCondition2();
	}

	
//****************************	
	
	public void saveHierDetail(){
//		for(HierarchyDetail aa : selectHierarchy.getHierarchyDetailList()){
//		for(PropertyEquationNew aa : selectHierarchy.getHierDefineIndexList()){
//			session.saveOrUpdate(aa);
//		}
		session.saveOrUpdate(selectHierarchy);
		session.flush();
	}
	
	
	Map<String, List<PortfolioDefine>> portfolioDefineMap = new HashMap<String, List<PortfolioDefine>>();
	Map<String, List<String>> subPortfolioMap = new HashMap<String, List<String>>();
	private Map<String, List<Method>> propPathMap = new HashMap<String, List<Method>>();


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
		
		Map<Portfolio,List<PropertyEquationNew> > definePortMap = new HashMap<Portfolio,List<PropertyEquationNew>>();
		String tempValue ; 

		for (Position pos : posList) {
			outbreak=false;
			List<PropertyEquationNew> defineList = new ArrayList<PropertyEquationNew>();
			for (PropertyEquationNew aa : selectHierarchy.getHierDefineIndexList()) {
//				log.info("In:#0, #1", pos.getPosId(), aa.getPropName());
				tempValue ="null";
				navi = pos;
				try {
						Method posMethod = Position.class.getDeclaredMethod(aa.getPropName());
						navi = posMethod.invoke(navi);
//						log.info("Navi:#0, #1, #2", pos.getPosId(),String.valueOf(navi), posMethod.getName());
						tempValue =String.valueOf(navi);
				} 
				catch (Exception e) {
						tempValue= "XXX";
				}
				
				
				if(!aa.getEquation().check(tempValue, aa.getCondition1(), aa.getCondition2())){
					outbreak=true;
					break;
				}else{
					PropertyEquationNew temp = createPropertyEquation(aa, tempValue);
					defineList.add(selectHierarchy.getHierDefineIndexList().indexOf(aa), temp);
				}
			}

//			log.info("Define:#0, #1, #2", pos.getPosId(), defineList.get(0).getCondition1(), defineList.get(1).getCondition1());

				
			if(outbreak){
				log.info("OUT");
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
					index= index+1;
					port = new Portfolio();
					setPortInfo(port, null, defineList);
					
					setParentPortfolio(definePortMap, defineList, port);
					
					port.getPortDetailList().add(pos);
					
					definePortMap.put(port,defineList);
					
					log.info("New Port: #0,#1,#2", port.getPortId(), port.getLevel1(), port.getLevel2());
				}
			}
		}
			
		Transaction tx = session.getTransaction();
		tx.begin();
		
		
//		for(Portfolio zz : selectHierarchy.getPortfolioList()){
//			log.info("Session1:#0", session.contains(zz));
//			session.delete(zz);
//		}
//		selectHierarchy.getPortfolioList().clear();
////		selectHierarchy.setChangeable(EBoolean.N);
//		session.flush();

		
		for(Portfolio port : definePortMap.keySet()){
			log.info("POrt: #0, #1,#2,#3", port.getPortId(), port.getParentPortfolio()==  null ? "": port.getParentPortfolio().getPortId());
//			tempHierarchy.getPortfolioList().add(port);
			session.saveOrUpdate(port);
			selectHierarchy.getPortfolioList().add(port);
		}
		
//		session.save(tempHierarchy);
		
		log.info("Port:aaa");
		selectHierarchy.setChangeable(EBoolean.N);
		selectHierarchy.setUseable(EBoolean.Y);
		session.saveOrUpdate(selectHierarchy);
		session.flush();
		tx.commit();
	}
	
	
	boolean rootFlag =true;
	
	/*public void makePortfolio4() {		
		posList =session.createCriteria(Position.class).list();
		index =0;
		boolean outbreak=false;

		Map<Portfolio,List<PropertyEquationNew>> definePortMap = new HashMap<Portfolio,List<PropertyEquationNew>>();
		

		for (Position pos : posList) {
			outbreak=false;
			List<PropertyEquationNew> defineList = new ArrayList<PropertyEquationNew>();
			
			for(PropertyEquationNew aa : selectHierarchy.getHierDefineIndexList()){
//				log.info("Pos Prop1:#0, #1", aa.getHierProperty().getPropId(), pos.getPropMap().get(aa.getHierProperty()) );
//				if(!aa.getEquation().equals(EEquation.DISTINCT)){
//					rootFlag = true;
//				}
				
				if(!aa.getEquation().check(	, aa.getCondition1(), aa.getCondition2())){
					outbreak=true;
					break;
				}else{
					String tempValue =pos.getPropMap().get(aa.getProperty());
					
					PropertyEquation temp = createPropertyEquation(aa, tempValue);
					defineList.add(selectHierarchy.getHierDefineIndexList().indexOf(aa), temp);
				}
			}
			
			if(outbreak){
				log.info("OUT");
			}else{
				Portfolio port = getPortfolioFrom(definePortMap, defineList);
				
	//			DB or alreadY made Portfolio
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
	}*/
	
	
	
	private PropertyEquationNew createPropertyEquation(PropertyEquationNew propEquation, String value){
		PropertyEquationNew tempEquation = new PropertyEquationNew();

		tempEquation.setPropGroup(propEquation.getPropGroup());
		tempEquation.setPropName(propEquation.getPropName());
		
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
	
	private void setParentPortfolio(Map<Portfolio, List<PropertyEquationNew>> definePortMap, List<PropertyEquationNew> defineList , Portfolio child){
		int totalSize = defineList.size();
		
		List<PropertyEquationNew> tempList = new ArrayList<PropertyEquationNew>();
		
		for(int i=0; i<totalSize-1; i++){
			tempList.add(i, defineList.get(i));
		}
		
		Portfolio parent = getPortfolioFrom(definePortMap, tempList);
		log.info("Set Parent:#0, #1",index);
		
		if(parent == null){
			index = index+1;
			parent = new Portfolio();
			
			setPortInfo(parent, null, tempList);
			child.setParentPortfolio(parent);
			log.info("Parent:#0,#1,#2",index, child.getPortId(), parent.getPortId());

			definePortMap.put(parent,tempList);
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
	
	private Portfolio getPortfolioFrom(Map<Portfolio,List<PropertyEquationNew>> definePortMap, List<PropertyEquationNew> defineList){
		Portfolio port = getPortfolioFromDB(defineList);
		
		if(port == null){
			for(Map.Entry<Portfolio, List<PropertyEquationNew>> entry : definePortMap.entrySet()){
				if(entry.getValue().equals(defineList)){
//				if(list.containsAll(defineList) && defineList.containsAll(list)){
					return entry.getKey();
				}
			}
			return null;
		}else{
			return port;
		}
	}
	
	private Portfolio getPortfolioFromDB( List<PropertyEquationNew> defineList){
		for(Portfolio port : selectHierarchy.getPortfolioList()){
			if(port.getPortDefineIndexList().equals(defineList)){
				return port;
			}	
		}
//		return new Portfolio();
		return null;
	}
	
	private void setPortInfo(Portfolio newPort, Portfolio parentPort , List<PropertyEquationNew> defineList){
		String tempName ="";
		newPort.setPortId(selectHierarchy.getHierarchyId() + "_" + index);
		
		newPort.setHierarchy(selectHierarchy);
		newPort.setParentPortfolio(parentPort);
		newPort.setPortType("HIER");
		newPort.setWeight(new BigDecimal(1));
		
		newPort.setPortDefineIndexList(defineList);
//		for(PropertyEquationNew aa : defineList){
//			log.info("listZ:#0, #1", defineList.size(), aa.getCondition1());
//		}
		
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
