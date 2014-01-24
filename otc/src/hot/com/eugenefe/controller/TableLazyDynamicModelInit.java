package com.eugenefe.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Log;
import org.mvel2.ast.WithNode.ParmValuePair;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.eugenefe.converter.LazyModelDynamic;
import com.eugenefe.converter.TableDynamicColumn;
import com.eugenefe.entity.IEntity;
import com.eugenefe.entity.IntRate;
import com.eugenefe.entity.IrCurve;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.ComponentReflection;
import com.eugenefe.util.EColumnType;
import com.eugenefe.util.ENavigationData;

//import org.jboss.seam.framework.Query;

@Name("tableLazyDynamicModelInit")
@Scope(ScopeType.CONVERSATION)
public class TableLazyDynamicModelInit<T> {
	@Logger
	private Log log;

	@In
	private Session session;

	@In(value = "#{basedateBean}")
	private BaseDateBean basedateBean;

	@RequestParameter
	private String navigation;

	private String savedNavigation;

	// @In(value= "#{treeObjectNavigationInit.rootNode}")
//	private TreeNode rootNode;
//	private TreeNode superNode;

	private TreeNode[] selectedNodes;

	private List<T> dynamicModelList;

	private Map<String, String> selectedDynamicModel;

	@In("#{initPivotTableHeader}")
	private List<TableDynamicColumn> initPivotTableHeader;
	
//	@In("#{xxx}")
//	private List<TableDynamicColumn> initDetailTab;
//	
//	@In(value= "#{yyy}")
//	private Map<String,List<TableDynamicColumn>> initDetailModelHeaderMap;
//	
	
	private List<TableDynamicColumn> pivotTableHeader;
	// private List<PivotTableModel<T, TableDynamicColumn, TableDynamicContent>>
	// pivotTableContent;

//	 @DataModel
	private List<Map<String, String>> pivotTableContent;
	private List<Map<String, String>> filterPivotTableContent;
	// private List<PivotTableModelNew<T, TableDynamicColumn, TableDynamicContent>> filterPivotTableContent;
	// private List<PivotTableModelNew<T, String, TableDynamicContent>> filterPivotTableContent;

	private LazyModelDynamic lazyModelDynamic;
	private Map<String,LazyModelDynamic> lazyModelDynamicDetail;
	


	public TableLazyDynamicModelInit() {
		System.out.println("Construction TableLazyDynamicModelInit");
	}
	
	private FacesContext context;
	
	public FacesContext getContext() {
		return context;
	}

	public void setContext(FacesContext context) {
		this.context = context;
	}

	// *******************************************
	@Create
	// @Begin(join = true)
	public void create() {
		if (navigation != null) {
			savedNavigation = navigation;
		}
		pivotTableHeader = initPivotTableHeader;
//		detailTab = initDetailTab;
//		detailMdoelHeaderMap = initDetailModelHeaderMap;
//		if(detailTab == null){
//			log.info("In Creation AAAA Null");
//		}
//		log.info("In Creation AAAA:#0, #1", detailTab.size());
		loadDynamicModel();
		loadTable();

//		dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
//				.findComponent("dynTabView:formDynamicModelDetail:tableDynamicModelDetail");
		// dataTable.setLazy(true);
		context = FacesContext.getCurrentInstance();
		String aaaa = FacesContext.getCurrentInstance().getViewRoot().getViewId();
//		log.info("aaabbb:#0", context.getViewRoot().findComponent("dynTabView:formDynamicModelDetail:tableDynamicModelDetail"));
		// for (UIColumn aa : dataTable.getColumns()) {
		// if (aa.getFilterBy() != null) {
		// log.info("BBBB:#0", aa.getFilterBy().toString());
		// }
		// }
		
	}

	public void columeChange() {
		log.info("In the Column Change");
//		dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
//				.findComponent("dynForm:dynTabView:formDynamicModelDetail:tableDynamicModelDetail");
//		context = FacesContext.getCurrentInstance();
//		log.info("tab table:#0,#1", dataTable, context.getViewRoot().findComponent("dynTabView:formDynamicModelDetail:tableDynamicModelDetail"));
//		dataTable.reset();
		// resetTable();
		changePivotTableHeader();
		// loadDynamicModel();
		loadTable();
//		loadDetailTable();
	}
	public void selectDynamicModel(SelectEvent event){
		dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
//				.findComponent("dynForm:dynTabView:formDynamicModelDetail:tableDynamicModelDetail");
		.findComponent(":dynForm:dynTabView:formDynamicModelDetail:tableDynamicModelDetail");
		dataTable.reset();
		
		if(detailTab!=null && detailTab.size()>0){
			log.info("In the Selection :#0,#1,#2", event.getObject(),detailTab.size(),dataTable);
			
			loadDetailTable();
		}
		
	}

	// ******************************************

	private void changePivotTableHeader() {
		pivotTableHeader = new ArrayList<TableDynamicColumn>();
		detailModelHeader = new ArrayList<TableDynamicColumn>();
		detailMdoelHeaderMap = new HashMap<String, List<TableDynamicColumn>>();

		detailTab = new ArrayList<TableDynamicColumn>();
		Set<TableDynamicColumn> tempTab = new HashSet<TableDynamicColumn>(); 

		TableDynamicColumn tempHeader, tempParentHeader;

		if (selectedNodes == null || selectedNodes.length == 0) {
			pivotTableHeader = initPivotTableHeader;
		}
		
//		for(TreeNode aa : selectedNodes){
//			log.info("Pivot0 :#0", ((TableDynamicColumn)aa.getData()).getColumnId());
//		}
		for (TreeNode node : selectedNodes) {
			if(node.getParent().getData().equals("superRoot")){
				continue;
			}
			tempParentHeader = ((TableDynamicColumn) node.getParent().getData());
//			if(tempParentHeader.getColumnType().equals(EColumnType.Map)
//					||tempParentHeader.getColumnType().equals(EColumnType.List)){
			if(tempParentHeader.getColumnType().isCollection()){
				tempTab.add(tempParentHeader);
			}
		}
		detailTab.addAll(tempTab);
//		log.info("in the detailTab size: #0,#1", detailTab.size());

		for (TreeNode node : selectedNodes) {
			tempHeader = ((TableDynamicColumn) node.getData());
			// log.info("in the SelectionNode size: #0,#1", tempHeader.getColumnId(), node.getChildCount());
			if (node.isLeaf() ){
				if(tempHeader.isInTheCollection()) {
					detailModelHeader.add(tempHeader);
				}
				else{
					pivotTableHeader.add(tempHeader);
				}	
			}
		}	

		for(TableDynamicColumn aa : detailTab){
			List<TableDynamicColumn> tempSet = new ArrayList<TableDynamicColumn>();
			for(TableDynamicColumn bb : detailModelHeader){
				log.info("In Collection Header:#0, #1,#2", aa.getColumnId(), bb.getColumnId(), bb.getParentColumn());
				if(bb.getColumnId().startsWith(aa.getColumnId())){
					log.info("In Collection Header:#0, #1,#2");
//					tempSet.add(bb);
					tempSet.add(new TableDynamicColumn(bb.getColumnId().replace(aa.getColumnId()+"_", "")
							, bb.getColumnName(), bb.getColumnType(), aa, bb.getColumnLevel(), false
							, bb.getColumnOrder(), false, bb.getAlignFormat()));
				}
				
			}
			Collections.sort(tempSet);
			detailMdoelHeaderMap.put(aa.getColumnId(), tempSet);
		}

		Collections.sort(pivotTableHeader);
		Collections.sort(detailTab);
		Collections.sort(detailModelHeader);


	}

	private void loadDynamicModel() {
		Filter filter = session.enableFilter("filterBtwnDate").setParameter("stBssd", basedateBean.getStBssd())
				.setParameter("endBssd", basedateBean.getEndBssd());
		// Filter filterBssd =
		// session.enableFilter("filterCurrentDate").setParameter("bssd",
		// basedateBean.getBssd());

		String query = "from " + savedNavigation;
		dynamicModelList = session.createQuery(query).list();
		// log.info("Query Result size:#0", dynamicModelList.size());
	}


	
	public void loadTable() {
		pivotTableContent = new ArrayList<Map<String, String>>();
//		dynamicModelMap = new HashMap<String, T>();
		Object navi;
		Class<?> rtnType;

		Map<String, String> tempContentMap;

		Map<String, List<Map<String, String>>> tempListContentMap;
		Map<String, String> tempMapContentMap;
		List<Map<String, String>> tempMapList;

		for (T dyn : dynamicModelList) {
//			dynamicModelMap.put(dyn.getId(), dyn);
			tempContentMap = new HashMap<String, String>();
			tempListContentMap = new HashMap<String, List<Map<String, String>>>();

			for (TableDynamicColumn header : pivotTableHeader) {
				navi = dyn;
				try {
					for (String prop : header.getFullColumns()) {
						Method temp = navi.getClass().getDeclaredMethod(prop);
						// Field temp = navi.getClass().getDeclaredField(prop);
						// temp.setAccessible(true);
						// navi = temp.get(navi);
						// if(temp!=null){

						rtnType = temp.getReturnType();

						navi = temp.invoke(navi);

						tempContentMap.put(header.getColumnId(), String.valueOf(navi));
						}
				} catch (Exception e) {
					log.info("Method Call Exception :#0", header.getFullColumns());
				}

			}
			tempContentMap.put("getStringId", dyn.toString());
			pivotTableContent.add(tempContentMap);
			filterPivotTableContent = pivotTableContent;
		}
		lazyModelDynamic = new LazyModelDynamic(pivotTableContent);
//		log.info("Load Table:#0", pivotTableContent);
	}

	public void loadDetailTable() {
		detailTableModel = new HashMap<String, List<Map<String,String>>>();
		lazyModelDynamicDetail = new HashMap<String, LazyModelDynamic>();
		T sel = null;
		if(selectedDynamicModel ==null){
			return ;
		}
		
//		if(selectedDynamicModel !=null){
			for(T dyn : dynamicModelList){
				if(dyn.toString().equals(selectedDynamicModel.get("getStringId"))){
					sel = dyn;
					log.info("DetailTab DYN:#0, #1", detailTab, sel);
				}
			}

		Map<String, String> tempContentMap;
		List<Map<String, String>> tempContentMapList;
		Method  mtd;
		Object navi;
		Object naviLoop;
		Class rtnType;

		for(TableDynamicColumn tabHeader: detailTab){
			navi = sel;
			try {
			for(String xx : tabHeader.getFullColumns()){
				mtd =navi.getClass().getMethod(xx);
				rtnType = mtd.getReturnType();
				navi = mtd.invoke(navi);
				log.info("tabHeader: #0,#1,#2", rtnType.getName(), mtd.getName(),tabHeader.getFullColumns());
				
				if(rtnType.getName().contains("List")){
					tempContentMapList = new ArrayList<Map<String,String>>();
					for (Object entry : (List<Object>) navi) {
						tempContentMap = new HashMap<String, String>();
						for( TableDynamicColumn detailHeader : detailMdoelHeaderMap.get(tabHeader.getColumnId())){
							naviLoop = entry;
							log.info("Prop:#0, #1,#2,#3", detailHeader.getFullColumns(), tabHeader.getFullColumns());
							for(String bb: detailHeader.getFullColumns()){
								Method temp = naviLoop.getClass().getDeclaredMethod(bb);
								naviLoop = temp.invoke(naviLoop);
							}
							tempContentMap.put(detailHeader.getColumnId(), String.valueOf(naviLoop));
//							tempContentMap.put("getStringId", entry.toString());
						}
						tempContentMapList.add(tempContentMap);
						log.info("TempContentMap:#0,#1", tempContentMap, tempContentMapList);
					}
					detailTableModel.put(tabHeader.getColumnId(),tempContentMapList);
					lazyModelDynamicDetail.put(tabHeader.getColumnId(), new LazyModelDynamic(tempContentMapList));
				}
				else if(rtnType.getName().contains("Map")){
					tempContentMapList = new ArrayList<Map<String,String>>();
					for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) navi).entrySet()) {	
						tempContentMap = new HashMap<String, String>();
						for( TableDynamicColumn detailHeader : detailMdoelHeaderMap.get(tabHeader.getColumnId())){
							naviLoop = entry.getValue();
							log.info("Prop:#0, #1,#2,#3", detailHeader.getFullColumns(), tabHeader.getFullColumns());
							for(String bb: detailHeader.getFullColumns()){
								Method temp = naviLoop.getClass().getDeclaredMethod(bb);
								naviLoop = temp.invoke(naviLoop);
							}
							tempContentMap.put(detailHeader.getColumnId(), String.valueOf(naviLoop));
//							tempContentMap.put("getStringId", entry.getValue().toString());
						}
						tempContentMapList.add(tempContentMap);
						log.info("TempContentMap:#0,#1", tempContentMap, tempContentMapList);
					}
					detailTableModel.put(tabHeader.getColumnId(),tempContentMapList);
					lazyModelDynamicDetail.put(tabHeader.getColumnId(), new LazyModelDynamic(tempContentMapList));
				}
			}
			} catch (Exception e) {
				log.info("Method Call Exception :#0", detailTab);
			}
		}	
//		log.info("Model2 :#0, #1", detailTableModel, lazyModelDynamicDetail);
//		for(Map.Entry<String, LazyModelDynamic> entry : lazyModelDynamicDetail.entrySet()){
//			log.info("Model20 :#0, #1", entry.getKey(), entry.getValue());
//			for( Map<String, String> map : entry.getValue().getDatasource()){
//				log.info("Model21 :#0, #1", map);
//			}
//		}
		
	}
	
	// ***************************************************************
	public void resetTable() {
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("formDynamicModel:tableDynamicModel");
		if (dataTable != null) {
			// log.info("In the dataTable");
			dataTable.setValueExpression("sortBy", null);
			// dataTable.setValueExpression("filterBy", null);
			dataTable.setFirst(0);
			dataTable.reset();
		}
	}

	// ****************Getter and Setter***********************

	public List<TableDynamicColumn> getPivotTableHeader() {
		return pivotTableHeader;
	}

	public void setPivotTableHeader(List<TableDynamicColumn> pivotTableHeader) {
		this.pivotTableHeader = pivotTableHeader;
	}

	public List<Map<String, String>> getPivotTableContent() {
		Map<String,String> params = context.getExternalContext().getRequestParameterMap();
		String selection = params.get("formDynamicModel:tableDynamicModel" + "_selection");
		 log.info("FilterSelection:#0,#1",selection,params);
		return pivotTableContent;
	}

	public void setPivotTableContent(List<Map<String, String>> pivotTableContent) {
		this.pivotTableContent = pivotTableContent;
	}

	public List<Map<String, String>> getFilterPivotTableContent() {
		Map<String,String> params = context.getExternalContext().getRequestParameterMap();
		String selection = params.get("formDynamicModel:tableDynamicModel" + "_selection");
		 log.info("FilterSelection:#0,#1",selection,params);
		// FacesContext context = FacesContext.getCurrentInstance();
		// ELContext elContext= context.getELContext();
		dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("formDynamicModel:tableDynamicModel");
		// log.info("Filter1asdfasdf:#0,#1,#2",
		// dataTable.getId(),dataTable.getMultiSortMeta(),
		// dataTable.getSortBy());
		// , dataTable.getFilters().size(), filterPivotTableContent.size());

		// for (Map.Entry<String, String> aa :
		// dataTable.getFilters().entrySet()) {
		// log.info("FilterZZ:#0,#1",dataTable.getColumns().size(),
		// aa.getFilterBy());
		// log.info("FilterZZ:#0,#1,#2",aa.getKey(), aa.getValue(),
		// dataTable.getFilterMetadata());

		// }
		// ValueExpression filterByVE =
		// context.getApplication().getExpressionFactory().createValueExpression(elContext,
		// "#{tableLazyDynamicModelInit.lazyModelDynamic}", Object.class);
		// log.info("FilterZZ1zzzz:#0,#1,#2",
		// String.valueOf(filterByVE.getValue(elContext)));
		// for (Object aa : dataTable.getFilterMetadata()) {
		// log.info("FilterZZ:#0,#1,#2",(FilterM));

		// }
		// for (UIColumn bb: dataTable.getColumns()) {
		// // log.info("FilterZZ:#0,#1",dataTable.getColumns().size(),
		// aa.getFilterBy());
		// log.info("Column : #0,#1, #2", bb.isDynamic(), bb.getFilterBy(),
		// bb.getContainerClientId(context));
		//
		// }
		return filterPivotTableContent;
	}

	public void setFilterPivotTableContent(List<Map<String, String>> filterPivotTableContent) {
		this.filterPivotTableContent = filterPivotTableContent;
	}

	public Map<String, String> getSelectedDynamicModel() {
		return selectedDynamicModel;
	}

	public void setSelectedDynamicModel(Map<String, String> selectedDynamicModel) {
		this.selectedDynamicModel = selectedDynamicModel;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	private DataTable dataTable;

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public LazyModelDynamic getLazyModelDynamic() {
		return lazyModelDynamic;
	}

	public void setLazyModelDynamic(LazyModelDynamic lazyModelDynamic) {
		this.lazyModelDynamic = lazyModelDynamic;
	}
	
	private Map<String, List<Map<String, String>>> detailTableModel;

	public Map<String, List<Map<String, String>>> getDetailTableModel() {
		return detailTableModel;
	}

	public void setDetailTableModel(Map<String, List<Map<String, String>>> detailTableModel) {
		this.detailTableModel = detailTableModel;
	}
	private List<TableDynamicColumn> detailTab;
	public List<TableDynamicColumn> getDetailTab() {
		return detailTab;
	}
	public void setDetailTab(List<TableDynamicColumn> detailTab) {
		this.detailTab = detailTab;
	}

	private Map<String, List<TableDynamicColumn>> detailMdoelHeaderMap;
	
	public Map<String, List<TableDynamicColumn>> getDetailMdoelHeaderMap() {
		return detailMdoelHeaderMap;
	}

	public void setDetailMdoelHeaderMap(Map<String, List<TableDynamicColumn>> detailMdoelHeaderMap) {
		this.detailMdoelHeaderMap = detailMdoelHeaderMap;
	}

	private List<TableDynamicColumn> detailModelHeader;

	public List<TableDynamicColumn> getDetailModelHeader() {
		return detailModelHeader;
	}

	public void setDetailModelHeader(List<TableDynamicColumn> detailModelHeader) {
		this.detailModelHeader = detailModelHeader;
	}
	
	public Map<String, LazyModelDynamic> getLazyModelDynamicDetail() {
		return lazyModelDynamicDetail;
	}

	public void setLazyModelDynamicDetail(Map<String, LazyModelDynamic> lazyModelDynamicDetail) {
		this.lazyModelDynamicDetail = lazyModelDynamicDetail;
	}
	
//	private List<Map<String, String>> detailTableModel;
//
//	public List<Map<String, String>> getDetailTableModel() {
//		return detailTableModel;
//	}
//
//	public void setDetailTableModel(List<Map<String, String>> detailTableModel) {
//		this.detailTableModel = detailTableModel;
//	}

//	private Map<String,T> dynamicModelMap;
	
	
	

	public void loadTableOld() {
		pivotTableContent = new ArrayList<Map<String, String>>();
//		dynamicModelMap = new HashMap<String, T>();
		Object navi;
		Class<?> rtnType;
		ParameterizedType pType;
		Type[] type;
		Type t1, t2;
		TypeVariable<Method>[] typVa;

		Map<String, String> tempContentMap;

		Map<String, List<Map<String, String>>> tempListContentMap;
		Map<String, String> tempMapContentMap;
		List<Map<String, String>> tempMapList;

		for (T dyn : dynamicModelList) {
//			dynamicModelMap.put(dyn.getId(), dyn);
			// Map<TableDynamicColumn, TableDynamicContent> tempContentMap = new
			// HashMap<TableDynamicColumn, TableDynamicContent>();
			tempContentMap = new HashMap<String, String>();
			tempListContentMap = new HashMap<String, List<Map<String, String>>>();

			for (TableDynamicColumn header : pivotTableHeader) {
				navi = dyn;
				try {
					for (String prop : header.getFullColumns()) {
						Method temp = navi.getClass().getDeclaredMethod(prop);
						// Field temp = navi.getClass().getDeclaredField(prop);
						// temp.setAccessible(true);
						// navi = temp.get(navi);
						// if(temp!=null){

						rtnType = temp.getReturnType();

						navi = temp.invoke(navi);

						switch (header.getColumnType()) {
						case List:
							tempMapContentMap = new HashMap<String, String>();
							tempMapList = new ArrayList<Map<String, String>>();
							for (Object entry : (List<Object>) rtnType.cast(navi)) {
								for (Method mm : entry.getClass().getDeclaredMethods()) {
									if (mm.isAnnotationPresent(AnnoMethodTree.class)) {
										tempMapContentMap.put(mm.getName(), String.valueOf(mm.invoke(entry)));
									}
									// aa.invoke(entry.getValue());
								}
								tempMapList.add(tempMapContentMap);
							}
							tempListContentMap.put(header.getColumnId(), tempMapList);
							break;
						case Map:
							// type =
							// ((ParameterizedType)temp.getGenericReturnType()).getActualTypeArguments();
							// log.info("REturn Type: #0,  #1", temp.getName(),
							// type);
							// for(Type aa : type){
							// log.info("REturn Type1: #0,  #1", temp.getName(),
							// aa);
							// }
							// t1 = type[0];
							// t2 = type[1];
							// // }
							// log.info("Cast:#0", rtnType.cast(navi));
							//
							// for(Map.Entry<Type, Type> entry : ((Map<Type,
							// Type>)rtnType.cast(navi)).entrySet() ){
							// // log.info("Cast1:#0,#1", entry.getKey(),
							// entry.getValue().getIrId());
							// }
							tempMapContentMap = new HashMap<String, String>();
							tempMapList = new ArrayList<Map<String, String>>();
							for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) navi).entrySet()) {
								for (Method aa : entry.getValue().getClass().getDeclaredMethods()) {
									if (aa.isAnnotationPresent(AnnoMethodTree.class)) {
//										log.info("Cast1:#0,#1", aa.getName(), aa.invoke(entry.getValue()));
										tempMapContentMap
												.put(aa.getName(), String.valueOf(aa.invoke(entry.getValue())));
									}
									// aa.invoke(entry.getValue());
								}
								tempMapList.add(tempMapContentMap);
							}
							tempListContentMap.put(header.getColumnId(), tempMapList);
							break;

						case Number:
							break;

						default:
							tempContentMap.put(header.getColumnId(), String.valueOf(navi));
						}
					}
				} catch (Exception e) {
					log.info("Method Call Exception :#0", header.getFullColumns());
				}

			}
			tempContentMap.put("getStringId", dyn.toString());
			pivotTableContent.add(tempContentMap);
			filterPivotTableContent = pivotTableContent;

			lazyModelDynamic = new LazyModelDynamic(pivotTableContent);

			// detailTableModel = tempListContentMap ;

		}
	}
	

	
	private String defSortField = "getId";

	public String getDefSortField() {
		return defSortField;
	}

	public void setDefSortField(String defSortField) {
		this.defSortField = defSortField;
	}
	public String getSavedNavigation() {
		return savedNavigation;
	}

	public void setSavedNavigation(String savedNavigation) {
		this.savedNavigation = savedNavigation;
	}
	
	
	private Map<String, Object> contents = new HashMap<String, Object>();

	private void convertLoadingData(){
		Object navi;
		Method[] klassMethods = dynamicModelList.get(0).getClass().getDeclaredMethods();

		for (T dyn : dynamicModelList) {
			try {
				for (Method mm : klassMethods) {
					if (mm.isAnnotationPresent(AnnoMethodTree.class) ){ 
						navi = mm.invoke(dyn);
						contents.put(mm.getName(), navi);
					}	
				}
			} catch (Exception e) {
			}

			contents.put("getStringId", dyn.toString());
		}
	}
	
	public void loadDetailTableNew() {
		detailTableModel = new HashMap<String, List<Map<String,String>>>();
		T sel = null;
		if(selectedDynamicModel ==null){
			return ;
		}
		
//		if(selectedDynamicModel !=null){
			for(T dyn : dynamicModelList){
				if(dyn.toString().equals(selectedDynamicModel.get("getStringId"))){
					sel = dyn;
					log.info("DetailTab DYN:#0, #1", detailTab, sel);
				}
			}
		
		
		Map<String, String> tempContentMap;
		List<Map<String, String>> tempContentMapList;
		Method  mtd;
		Object navi;
		Object naviLoop;
		Class rtnType;
		
		
		for (TableDynamicColumn header : detailTab) {
			tempContentMapList = new ArrayList<Map<String,String>>();
			try {
				for(String xx : header.getFullColumns()){
					log.info("XXX: #0,#1", header.getFullColumns(), xx);
//					log.info("XXX: #0,#1,#2", rtnType.getName(), mtd.getName(),xx);
				}
				mtd =sel.getClass().getMethod(header.getColumnId());
				rtnType = mtd.getReturnType();
				navi = mtd.invoke(sel);
				
				
				switch (header.getColumnType()) {
				case List:
					for (Object entry : (List<Object>) navi) {
						tempContentMap = new HashMap<String, String>();
						for(TableDynamicColumn zz: detailMdoelHeaderMap.get(header.getColumnId())){
							naviLoop = entry;
							zz.getFullColumns().remove(header.getColumnId());
							for (String prop : zz.getFullColumns()) {
								log.info("Prop:#0, #1,#2", prop,zz.getColumnId(), zz.getFullColumns());
								Method temp = naviLoop.getClass().getDeclaredMethod(prop);
//								rtnType = temp.getReturnType();
								naviLoop = temp.invoke(naviLoop);
							}
//							tempContentMap.put(header.getColumnId()+"_"+ zz.getColumnId(), String.valueOf(naviLoop));
							tempContentMap.put(zz.getColumnId(), String.valueOf(naviLoop));
						}
						tempContentMapList.add(tempContentMap);
					}	
					detailTableModel.put(header.getColumnId(),tempContentMapList);
					break;
				case Map:
					for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) navi).entrySet()) {
						tempContentMap =new HashMap<String, String>();
						for(TableDynamicColumn zz: detailMdoelHeaderMap.get(header.getColumnId())){
							naviLoop = entry.getValue();
							zz.getFullColumns().remove(header.getColumnId());
							for (String prop : zz.getFullColumns()) {
								log.info("Prop:#0, #1,#2", prop,zz.getColumnId(), zz.getFullColumns());
								Method temp = naviLoop.getClass().getDeclaredMethod(prop);
//								rtnType = temp.getReturnType();
								naviLoop = temp.invoke(naviLoop);
							}
							tempContentMap.put(zz.getColumnId(), String.valueOf(naviLoop));
						}
						tempContentMapList.add(tempContentMap);
					}	
					detailTableModel.put(header.getColumnId(),tempContentMapList);
					default:
				}
//				rtnType = mtd.getGenericReturnType().getClass();
//				rtnType= (Class)((ParameterizedType)mtd.getGenericReturnType()).getActualTypeArguments()[1];
//				log.info("Detail Method: #0,#1" , mtd.getGenericReturnType(), ((ParameterizedType)mtd.getGenericReturnType()).getActualTypeArguments()[0].getClass().cast(navi));
//				log.info("Detail Method: #0,#1,#2" , mtd.getGenericReturnType(),((ParameterizedType)mtd.getGenericReturnType()).getRawType());
//				log.info("Detail Method: #0,#1" , mtd.getGenericReturnType(), ((Class)mtd.getGenericReturnType()).cast(navi));
				
//				switch (header.getColumnType()) {
//				case List:
//					for (Object entry : (List<Object>) navi) {
//						tempContentMap = new HashMap<String, String>();
//						naviLoop =entry;
//						
//							log.info("Prop:#0, #1,#2", prop,zz.getColumnId(), header.getFullColumns());
//							Method temp = navi.getClass().getDeclaredMethod(prop);
//							rtnType = temp.getReturnType();
//							naviLoop = temp.invoke(naviLoop);
//							tempContentMap.put(header.getColumnId()+"_"+ temp.getName(), String.valueOf(temp.invoke(naviLoop)));
//						}
////							for (Method mm : entry.getClass().getDeclaredMethods()) {
////							if (mm.isAnnotationPresent(AnnoMethodTree.class)) {
////								tempContentMap.put(header.getColumnId()+"_"+ mm.getName(), String.valueOf(mm.invoke(entry)));
////							}
//						tempContentMapList.add(tempContentMap);
//					}
//					detailTableModel.put(header.getColumnId(),tempContentMapList);
//					break;
//
//				case Map:
//					for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) navi).entrySet()) {
//						tempContentMap =new HashMap<String, String>();
//						for (Method mm : entry.getValue().getClass().getDeclaredMethods()) {
//							if (mm.isAnnotationPresent(AnnoMethodTree.class) ) {
////								if( mm.getAnnotation(AnnoMethodTree.class).init()){
////									log.info("Cast11:#0,#1,#2", mm.getName(),String.valueOf(mm.invoke(entry.getValue())), ((IntRate)entry.getValue()).getIrId());
//									tempContentMap.put(header.getColumnId()+"_"+ mm.getName(), String.valueOf(mm.invoke(entry.getValue())));
////								}
//							}
//						}
//						tempContentMapList.add(tempContentMap);
////						log.info("Cast11:#0,#1", entry.getKey(), entry.getValue());
//					}
//					detailTableModel.put(header.getColumnId(),tempContentMapList);
//					break;
//				default:
//				}
			
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				log.info("Method Call Exception :#0", header.getFullColumns());
			}
		}	
		log.info("Model2 :#0, #1", detailTableModel);
	}
	
	
	public void loadDetailTableNewNew() {
		detailTableModel = new HashMap<String, List<Map<String,String>>>();
		T sel = null;
		if(selectedDynamicModel ==null){
			return ;
		}
		
//		if(selectedDynamicModel !=null){
			for(T dyn : dynamicModelList){
				if(dyn.toString().equals(selectedDynamicModel.get("getStringId"))){
					sel = dyn;
					log.info("DetailTab DYN:#0, #1", detailTab, sel);
				}
			}
		
		
		Map<String, String> tempContentMap;
		List<Map<String, String>> tempContentMapList;
		Method  mtd;
		Object navi;
		Object naviLoop;
		Class rtnType;
		
		
		for (TableDynamicColumn header : detailTab) {
			tempContentMapList = new ArrayList<Map<String,String>>();
			navi = sel;
			try {
				for(String xx : header.getFullColumns()){
				
				mtd =navi.getClass().getMethod(xx);
				rtnType = mtd.getReturnType();
				navi = mtd.invoke(navi);
				log.info("XXXx: #0,#1,#2", rtnType.getName(), mtd.getName(),header.getFullColumns());
				
				if(rtnType.getName().contains("List")){
					for (Object entry : (List<Object>) navi) {
						tempContentMap = new HashMap<String, String>();
						for(TableDynamicColumn zz: detailMdoelHeaderMap.get(header.getColumnId())){
							naviLoop = entry;
							zz.getFullColumns().remove(header.getColumnId());
							for (String prop : zz.getFullColumns()) {
								log.info("Prop:#0, #1,#2", prop,zz.getColumnId(), zz.getFullColumns());
								Method temp = naviLoop.getClass().getDeclaredMethod(prop);
//								rtnType = temp.getReturnType();
								naviLoop = temp.invoke(naviLoop);
							}
//							tempContentMap.put(header.getColumnId()+"_"+ zz.getColumnId(), String.valueOf(naviLoop));
							tempContentMap.put(zz.getColumnId(), String.valueOf(naviLoop));
						}
						tempContentMapList.add(tempContentMap);
					}	
					detailTableModel.put(header.getColumnId(),tempContentMapList);
				}
				else if (rtnType.getName().contains("Map")){
					for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) navi).entrySet()) {
						tempContentMap =new HashMap<String, String>();
						for(TableDynamicColumn zz: detailMdoelHeaderMap.get(header.getColumnId())){
							naviLoop = entry.getValue();
							zz.getFullColumns().remove(header.getColumnId());
							for (String prop : zz.getFullColumns()) {
								log.info("Prop:#0, #1,#2", prop,zz.getColumnId(), zz.getFullColumns());
								Method temp = naviLoop.getClass().getDeclaredMethod(prop);
//								rtnType = temp.getReturnType();
								naviLoop = temp.invoke(naviLoop);
							}
							tempContentMap.put(zz.getColumnId(), String.valueOf(naviLoop));
						}
						tempContentMapList.add(tempContentMap);
					}	
					detailTableModel.put(header.getColumnId(),tempContentMapList);
				}
				
					
//				switch (header.getColumnType()) {
//				case List:
//					for (Object entry : (List<Object>) navi) {
//						tempContentMap = new HashMap<String, String>();
//						for(TableDynamicColumn zz: detailMdoelHeaderMap.get(header.getColumnId())){
//							naviLoop = entry;
//							zz.getFullColumns().remove(header.getColumnId());
//							for (String prop : zz.getFullColumns()) {
//								log.info("Prop:#0, #1,#2", prop,zz.getColumnId(), zz.getFullColumns());
//								Method temp = naviLoop.getClass().getDeclaredMethod(prop);
////								rtnType = temp.getReturnType();
//								naviLoop = temp.invoke(naviLoop);
//							}
////							tempContentMap.put(header.getColumnId()+"_"+ zz.getColumnId(), String.valueOf(naviLoop));
//							tempContentMap.put(zz.getColumnId(), String.valueOf(naviLoop));
//						}
//						tempContentMapList.add(tempContentMap);
//					}	
//					detailTableModel.put(header.getColumnId(),tempContentMapList);
//					break;
//				case Map:
//					for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) navi).entrySet()) {
//						tempContentMap =new HashMap<String, String>();
//						for(TableDynamicColumn zz: detailMdoelHeaderMap.get(header.getColumnId())){
//							naviLoop = entry.getValue();
//							zz.getFullColumns().remove(header.getColumnId());
//							for (String prop : zz.getFullColumns()) {
//								log.info("Prop:#0, #1,#2", prop,zz.getColumnId(), zz.getFullColumns());
//								Method temp = naviLoop.getClass().getDeclaredMethod(prop);
////								rtnType = temp.getReturnType();
//								naviLoop = temp.invoke(naviLoop);
//							}
//							tempContentMap.put(zz.getColumnId(), String.valueOf(naviLoop));
//						}
//						tempContentMapList.add(tempContentMap);
//					}	
//					detailTableModel.put(header.getColumnId(),tempContentMapList);
//					default:
//				}
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				log.info("Method Call Exception :#0", header.getFullColumns());
			}
			
		}	
		log.info("Model2 :#0, #1", detailTableModel);
	}
}
