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
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import sun.reflect.Reflection;

import com.eugenefe.entity.Bizunit;
import com.eugenefe.entity.MvType;
import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.PortfolioDefine;
import com.eugenefe.entity.Position;
import com.eugenefe.entity.PricingDll;
import com.eugenefe.entity.PricingMapping;
import com.eugenefe.entity.PricingMappingIdNew;
import com.eugenefe.entity.PricingMaster;
import com.eugenefe.enums.EEquation;
import com.eugenefe.enums.EIrCurveType;
import com.eugenefe.enums.EPricingObject;
import com.eugenefe.enums.EVolType;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;

//import org.jboss.seam.framework.Query;

@Name("tablePricerSettingNewInit")
@Scope(ScopeType.CONVERSATION)
public class TablePricerSettingNewInit {
	@Logger		private Log log;
	@In			private Map<String, String> messages;
	@In			private Session session;
	
	
	private List<PricingMapping> prMappingList = new ArrayList<PricingMapping>();
	private List<PricingMapping> selectMappingList = new ArrayList<PricingMapping>();
	
	private TreeNode superNode;
	private TreeNode rootNode ;
	
	private TreeNode prObjSuperNode;
	private TreeNode prObjRootNode ;

	private List<PricingMaster> prMasterList = new ArrayList<PricingMaster>();
	
	
	private PricingMapping editPricingMapping;
	private PricingMapping selectPricingMapping;
	
	private MvType selectMvType;
	private boolean newPrMapping;
	private boolean editPrMapping;
	
	
	public boolean isEditPrMapping() {
		return editPrMapping;
	}
	public void setEditPrMapping(boolean editPrMapping) {
		this.editPrMapping = editPrMapping;
	}
	public boolean isNewPrMapping() {
		return newPrMapping;
	}
	public void setNewPrMapping(boolean newPrMapping) {
		this.newPrMapping = newPrMapping;
	}
	public MvType getSelectMvType() {
		return selectMvType;
	}
	public void setSelectMvType(MvType selectMvType) {
		this.selectMvType = selectMvType;
	}
	public PricingMapping getSelectPricingMapping() {
		return selectPricingMapping;
	}
	public void setSelectPricingMapping(PricingMapping selectPricingMapping) {
		this.selectPricingMapping = selectPricingMapping;
	}
	public PricingMapping getEditPricingMapping() {
		return editPricingMapping;
	}
	public void setEditPricingMapping(PricingMapping editPricingMapping) {
		this.editPricingMapping = editPricingMapping;
	}
//	private String pricingObject;
	private EPricingObject pricingObject;
	private String prodType;
	private String dllName;
	private String discountType;
	private String  volType;
	private Long simNum;
	private Long latticeNum;
	
	
	
	public List<PricingMapping> getSelectMappingList() {
		return selectMappingList;
	}
	public void setSelectMappingList(List<PricingMapping> selectMappingList) {
		this.selectMappingList = selectMappingList;
	}
	public EPricingObject getPricingObject() {
		return pricingObject;
	}
	public void setPricingObject(EPricingObject pricingObject) {
		this.pricingObject = pricingObject;
	}
	//	public String getPricingObject() {
//		return pricingObject;
//	}
//	public void setPricingObject(String pricingObject) {
//		this.pricingObject = pricingObject;
//	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getDllName() {
		return dllName;
	}
	public void setDllName(String dllName) {
		this.dllName = dllName;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getVolType() {
		return volType;
	}
	public void setVolType(String volType) {
		this.volType = volType;
	}
	public Long getSimNum() {
		return simNum;
	}
	public void setSimNum(Long simNum) {
		this.simNum = simNum;
	}
	public Long getLatticeNum() {
		return latticeNum;
	}
	public void setLatticeNum(Long latticeNum) {
		this.latticeNum = latticeNum;
	}
	public TreeNode getPrObjSuperNode() {
		return prObjSuperNode;
	}
	public void setPrObjSuperNode(TreeNode prObjSuperNode) {
		this.prObjSuperNode = prObjSuperNode;
	}
	public TreeNode getPrObjRootNode() {
		return prObjRootNode;
	}
	public void setPrObjRootNode(TreeNode prObjRootNode) {
		this.prObjRootNode = prObjRootNode;
	}
	public TreeNode getSuperNode() {
		return superNode;
	}
	public void setSuperNode(TreeNode superNode) {
		this.superNode = superNode;
	}

	public TreeNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public List<PricingMapping> getPrMappingList() {
		return prMappingList;
	}

	public void setPrMappingList(List<PricingMapping> prMappingList) {
		this.prMappingList = prMappingList;
	}

	public TablePricerSettingNewInit() {
//		System.out.println("Construction TablePricerSettingInit");
	}

	// **********************************************************************
	@Create
	public void create() {
		prMasterList = session.createCriteria(PricingMaster.class).list();
		prMappingList = session.createCriteria(PricingMapping.class).list();

		superNode = new DefaultTreeNode("SUPER", null);
		rootNode = new DefaultTreeNode("ALL", superNode);
		TreeNode stDown = new DefaultTreeNode("STEP_DOWN", rootNode);
		TreeNode mthl = new DefaultTreeNode("MONTHLy_ST", stDown);
		TreeNode otc = new DefaultTreeNode("OTC_OPTION", rootNode);
		
		prObjSuperNode = new DefaultTreeNode("SUPER", null);
		prObjRootNode = new DefaultTreeNode("ALL", prObjSuperNode);
//		for(EPricingObject aa : prObjList){
//			TreeNode var = new DefaultTreeNode(aa.getName(), prObjRootNode);
//		}

		selectPricingMapping = new PricingMapping();
		
	}

//	public void nodeSelect(NodeSelectEvent event) {
//		String temp =(String)event.getTreeNode().getData();
//		for(PricingMapping aa : prMappingList){
//			if(aa.getId().getMvType().equals(temp)){
//				selectMappingList.add(aa);
//			}
//		}
//	}

	@Observer(value="mvTypeSelect")
	public void onSelectMvType(MvType selMvType, List<MvType> descendants){
		log.info("MVTYPE IN Pricer:#0,#1", selMvType.getMvTypeName(), descendants.size());
		selectMvType = selMvType;
		selectMappingList.clear();
		
		for(PricingMapping aa : prMappingList){
			if(descendants.contains(aa.getId().getMvType())){
				selectMappingList.add(aa);
			}
//			if(aa.getId().getMvType().equals(mvType)){
//				selectMappingList.add(aa);
//			}
		}
		log.info("Observer: #0", selectPricingMapping);
		defaultPanel();
	}
	
	public void rowSelect(){
//		pricingObject = selectPricingMapping.getId().getPrObject().getName();
//		newPrMapping = false;
		editPrMapping = true;
		pricingObject = selectPricingMapping.getId().getPrObject();
		
//		dllName = selectPricingMapping.getPricingDll().getDllName();
//		discountType = selectPricingMapping.getIrCurveType().getName();
//		volType = selectPricingMapping.getVolType().getName();
//		simNum = selectPricingMapping.getSimulationNum();
//		latticeNum = selectPricingMapping.getLatticeNum();
		
	}

	
	public void  defaultPanel(){
		
		editPrMapping = false;
		pricingObject = null;
		selectPricingMapping = new PricingMapping();
		selectPricingMapping.setDllId("selectOne");
//		selectPricingMapping.setPricingDll(new PricingDll("selectOne"));
//		selectPricingMapping.setId(new PricingMappingIdNew(EPricingObject.DEFAULT, selectMvType==null? new MvType():selectMvType));
		selectPricingMapping.setIrCurveType(EIrCurveType.RF);
		selectPricingMapping.setVolType(EVolType.LV);
		selectPricingMapping.setSimulationNum(null);
		selectPricingMapping.setLatticeNum(null);
		log.info("reset: #0,#1", selectPricingMapping.getDllId(), selectPricingMapping.getIrCurveType());
		
//		pricingObject = EPricingObject.DEFAULT;
		dllName =  "";
		discountType =  "";
		volType =  "";
		simNum =  new Long(0);
		latticeNum =  new Long(0);

	}
	
	public void save(){
		String temp ;
		
		if(selectMvType==null){
			log.info("InTheSave2");
		}else if (pricingObject==null) {
			log.info("InTheSave3");
		}else{
			
//			if(selectMappingList.isEmpty()){
//				PricingDll tempDll = (PricingDll)session.get(PricingDll.class, selectPricingMapping.getDllId());
//				selectPricingMapping.setPricingDll(tempDll);
//				selectMappingList.add(selectPricingMapping);
//				
//			}else{
//			}
			PricingDll tempDll = (PricingDll)session.get(PricingDll.class, selectPricingMapping.getDllId());
			
			for(PricingMapping aa : selectMappingList){
				if(aa.getId().getPrObject().equals(pricingObject)){
					aa.setPricingDll(tempDll);
					aa.setDllId(tempDll.getDllName());
					session.saveOrUpdate(aa);
					session.flush();
					return;
				}
			}
			
			selectPricingMapping.setId(new PricingMappingIdNew(pricingObject, selectMvType));
			selectPricingMapping.setPricingDll(tempDll);
			selectPricingMapping.getId().setMvType(selectMvType);
			
//			for(PricingMapping aa : selectMappingList){
//				if(aa.equals(selectPricingMapping)){
//					selectMappingList.add(selectPricingMapping);
//				}
//			}
			if(!selectMappingList.contains(selectPricingMapping)){
				selectMappingList.add(selectPricingMapping);
				prMappingList.add(selectPricingMapping);
			}
			
			log.info("InTheSave");
			session.saveOrUpdate(selectPricingMapping);
			session.flush();
//			pricingObject=null;
			
		}

	}
	public void delete(){
		
	}
	public void mapToProduct(){
		
	}
	
}
