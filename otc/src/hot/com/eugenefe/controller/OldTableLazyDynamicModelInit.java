package com.eugenefe.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.eugenefe.converter.LazyModelDynamic;
import com.eugenefe.converter.TableDynamicColumn;
import com.eugenefe.entity.IrCurve;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.ComponentReflection;
import com.eugenefe.util.ENavigationData;
//import org.jboss.seam.framework.Query;

@Name("oldtableLazyDynamicModelInit")
@Scope(ScopeType.CONVERSATION)
public class OldTableLazyDynamicModelInit<T> {
/*	@Logger
	private Log log;

	@In
	private Session session;

	@In(value = "#{basedateBean}")
	private BaseDateBean basedateBean;
	
	@In
	private Map<String,String> messages;


	// @In(required=false, value="#{flagBean.navigationData}")
	// private ENavigationData navigationData;

	@RequestParameter
	private String navigation;

	private String savedNavigation;

	public String getSavedNavigation() {
		return savedNavigation;
	}

	public void setSavedNavigation(String savedNavigation) {
		this.savedNavigation = savedNavigation;
	}

	// @In(value= "#{treeObjectNavigationInit.rootNode}")
	private TreeNode rootNode;
	private TreeNode superNode;

	private TreeNode[] selectedNodes;

	private List<T> dynamicModelList;

	private T selectedDynamicModel;

	private List<TableDynamicColumn> pivotTableHeader;
	// private List<PivotTableModel<T, TableDynamicColumn, TableDynamicContent>>
	// pivotTableContent;

	@DataModel
	private List<Map<String, String>> pivotTableContent;
	private List<Map<String, String>> filterPivotTableContent;
	// private List<PivotTableModelNew<T, TableDynamicColumn,
	// TableDynamicContent>> filterPivotTableContent;
	// private List<PivotTableModelNew<T, String, TableDynamicContent>>
	// filterPivotTableContent;

	private LazyModelDynamic lazyModelDynamic;
	private String defSortField = "getId";
	
	public String getDefSortField() {
		return defSortField;
	}
	public void setDefSortField(String defSortField) {
		this.defSortField = defSortField;
	}

	public OldTableLazyDynamicModelInit() {
		System.out.println("Construction TableLazyDynamicModelInit");
	}

	// *******************************************
	@Create
	@Begin(join = true)
	public void create() {
		if (navigation != null) {
			savedNavigation = navigation;
		}
		zzz();
		initPivotTableHeader();

		loadDynamicModel();
		loadTable();
		dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("formDynamicModel:tableDynamicModel");
//		dataTable.setLazy(true);
		String aaaa = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		log.info("aaabbb:#0", aaaa);
		for (UIColumn aa : dataTable.getColumns()) {
			if (aa.getFilterBy() != null) {
				log.info("BBBB:#0", aa.getFilterBy().toString());
			}
		}
	}

	public void columeChange() {
		log.info("In the Column Change");
//		resetTable();
		initPivotTableHeader();
//		loadDynamicModel();
		loadTable();
	}

	// @Observer("evtDynamicModelInitialize")
	// @Begin(join = true)
	// public void create(TreeNode superNode) {
	// log.info("RootNode size:#0", superNode.getChildCount());
	// this.superNode = superNode;
	// this.rootNode = superNode.getChildren().get(0);
	//
	// // this.rootNode = rootNode;
	// initPivotTableHeader();
	// loadDynamicModel();
	// loadTable();
	// }

	// ******************************************
	public void initPivotTableHeader() {
		pivotTableHeader = new ArrayList<TableDynamicColumn>();
		String temp = new String();
		String subTemp = new String();
		
		System.out.println("In ColumnHead" + messages.size());
		
		
		if (selectedNodes == null || selectedNodes.length == 0) {
			initSelection();
//			 log.info("in the Initial SelectionNode size: #0", selectedNodes.length);
		}
		for (TreeNode node : selectedNodes) {
//			log.info("in the SelectionNode size: #0", ((Method)node.getData()).getName());
			if (node.isLeaf()) {
				if (node.getParent() != null && !node.getParent().equals(superNode)) {
					temp = ComponentReflection.getRecursiveMethodName(node, rootNode);
					// log.info("New Header: #0",
					// ComponentReflection.getRecursiveMethodName(node,
					// rootNode));

					pivotTableHeader.add(new TableDynamicColumn(temp,  messages.get(temp)));
				}
			}

		}
		
//		AnnoMethodOrder a;
//		Class c = IrCurve.class;
//		for(Method mm : c.getMethods()){
//			log.info("Annotation 1: #0, #1,#2", mm.getName());
//			if( mm.isAnnotationPresent(AnnoMethodOrder.class)){
//				a = mm.getAnnotation(AnnoMethodOrder.class);
//				log.info("Annotation : #0, #1,#2", mm.getName(), a.value());
////				System.out.println(String.format("%s's type is '%s'", mm.getName(), a.value()));
//			}
//		}
	}

	public void loadDynamicModel() {
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
		// pivotTableContent = new ArrayList<PivotTableModel<T,
		// TableDynamicColumn, TableDynamicContent>>();
		pivotTableContent = new ArrayList<Map<String, String>>();
		// Collections.sort(pivotTableHeader);
		Object navi;

		for (T dyn : dynamicModelList) {

			// Map<TableDynamicColumn, TableDynamicContent> tempContentMap = new
			// HashMap<TableDynamicColumn, TableDynamicContent>();
			Map<String, String> tempContentMap = new HashMap<String, String>();

			for (TableDynamicColumn header : pivotTableHeader) {
				 log.info("Header :#0", header.getColProperties());
				navi = dyn;
				// log.info("header Prep:#0,#1", header.getColProperties());
				try {
					for (String prop : header.getColProperty()) {
						Method temp = navi.getClass().getDeclaredMethod(prop);
						// Field temp = navi.getClass().getDeclaredField(prop);

						// temp.setAccessible(true);
						// navi = temp.get(navi);

						navi = temp.invoke(navi);
					}
					// tempContentMap.put(header, new
					// TableDynamicContent(String.valueOf(navi), 0));
					tempContentMap.put(header.getColProperties(), String.valueOf(navi));
				} catch (Exception e) {

				}
			}
			// pivotTableContent.add(new PivotTableModelNew<T,
			// TableDynamicColumn, TableDynamicContent>(dyn, tempContentMap));
			pivotTableContent.add(tempContentMap);
			filterPivotTableContent = pivotTableContent;

			lazyModelDynamic = new LazyModelDynamic(pivotTableContent);

		}
		// log.info("In the Vcv Matrix:#0,#1", pivotTableContent.size());
	}

	private void initSelection() {
		int cnt = 0;
		String temp = new String();

		for (TreeNode node : rootNode.getChildren()) {
			temp = ComponentReflection.getRecursiveMethodName(node, rootNode);
			if (node.isLeaf()) {
				cnt = cnt + 1;
			} else if (temp.contains("Id")) {
				for (TreeNode subNode : node.getChildren()) {
					cnt = cnt + 1;
				}
			}
		}

		selectedNodes = new DefaultTreeNode[cnt];
		log.info("in the SelectionNode : #0", cnt);
		cnt = 0;

		for (TreeNode node : rootNode.getChildren()) {
			temp = ComponentReflection.getRecursiveMethodName(node, rootNode);
			if (node.isLeaf()) {
				selectedNodes[cnt] = node;
				log.info("in the SelectionNode : #0,#1", cnt, temp);
				cnt = cnt + 1;
			} else if (temp.contains("Id")) {
				for (TreeNode subNode : node.getChildren()) {
					selectedNodes[cnt] = subNode;
					log.info("in the SelectionNode1 : #0,#1", cnt, temp);
					cnt = cnt + 1;
				}
			}
		}
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

	public void zzz() {
		Class klass;
		try {
			klass = Class.forName(ENavigationData.valueOf(savedNavigation).getQualifiedName());

			// rootNode = ComponentReflection.getPropertyTree(klass);
			// rootNode =
			// ComponentReflection.getMethodTree(klass).getChildren().get(0);
			superNode = ComponentReflection.getMethodTree(klass);
			rootNode = superNode.getChildren().get(0);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void recursive(Field field, Field superField, Map<Field, Field> rst) {
		Class fieldKlazz = field.getType();
		// if(!fieldKlazz.isInterface()){
		if (!fieldKlazz.isPrimitive()) {
			// if (fieldKlazz.getPackage() != null ) {
			if (fieldKlazz.getPackage().getName().contains("com.eugenefe.entity")
					|| fieldKlazz.getPackage().getName().contains("com.eugenefe.controller")) {
				for (Field ff : fieldKlazz.getDeclaredFields()) {
					log.info("Pivo Header1 :#0,#1,#2", ff.getName(), ff.getDeclaredAnnotations().length);
					// for(Annotation zz: ff.getDeclaredAnnotations()){
					// log.info("Pivo Header :#0,#1,#2", zz.toString());
					// if(zz.toString().contains("ToMany")){
					// break;
					// }
					// }
					recursive(ff, field, rst);
				}
			} else {
				rst.put(field, superField);
			}
		} else {
			rst.put(field, superField);
		}
		// }
	}

	private void recursive(Class klazz, Map<Field, Class> rst) {
		Class fieldKlazz;

		for (Field field : klazz.getDeclaredFields()) {
			fieldKlazz = field.getType();
			if (field.getName().contains("List") || field.getName().contains("Set") || field.getName().contains("Map")) {
				break;
			}
			if (!fieldKlazz.isPrimitive()) {
				if (fieldKlazz.getPackage().getName().contains("com.eugenefe.entity")
						|| fieldKlazz.getPackage().getName().contains("com.eugenefe.controller")) {
					recursive(fieldKlazz, rst);
				} else {
					rst.put(field, klazz);
				}
			} else {
				rst.put(field, klazz);
			}
		}
	}

	private void recursive(String fieldName, Class fieldKlazz, List<String> rst) {
		// if(!fieldKlazz.isInterface()){
		if (!fieldKlazz.isPrimitive()) {
			// if (fieldKlazz.getPackage() != null ) {
			if (fieldKlazz.getPackage().getName().contains("com.eugenefe.entity")
					|| fieldKlazz.getPackage().getName().contains("com.eugenefe.controller")) {
				for (Field ff : fieldKlazz.getDeclaredFields()) {
					log.info("Pivo Header1 :#0,#1,#2", ff.getName(), ff.getDeclaredAnnotations().length);
					for (Annotation zz : ff.getDeclaredAnnotations()) {
						log.info("Pivo Header :#0,#1,#2", zz.toString());
						if (zz.toString().contains("ToMany")) {
							break;
						}
					}
					recursive(fieldName + "." + ff.getName(), ff.getType(), rst);
				}
			} else {
				rst.add(fieldName);
			}
		} else {
			rst.add(fieldName);
		}
		// }
	}

	// ****************Getter and Setter***********************

	public List<TableDynamicColumn> getPivotTableHeader() {
		return pivotTableHeader;
	}

	public void setPivotTableHeader(List<TableDynamicColumn> pivotTableHeader) {
		this.pivotTableHeader = pivotTableHeader;
	}

	public List<Map<String, String>> getPivotTableContent() {
		return pivotTableContent;
	}

	public void setPivotTableContent(List<Map<String, String>> pivotTableContent) {
		this.pivotTableContent = pivotTableContent;
	}

	public List<Map<String, String>> getFilterPivotTableContent() {

//		FacesContext context = FacesContext.getCurrentInstance();
//		ELContext elContext= context.getELContext();
		dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("formDynamicModel:tableDynamicModel");
//		 log.info("Filter1asdfasdf:#0,#1,#2", dataTable.getId(),dataTable.getMultiSortMeta(), dataTable.getSortBy());
//		 , dataTable.getFilters().size(), filterPivotTableContent.size());
		 
//		for (Map.Entry<String, String> aa : dataTable.getFilters().entrySet()) {
//			log.info("FilterZZ:#0,#1",dataTable.getColumns().size(), aa.getFilterBy());
//			log.info("FilterZZ:#0,#1,#2",aa.getKey(), aa.getValue(), dataTable.getFilterMetadata());
			
//		}
//		ValueExpression filterByVE = context.getApplication().getExpressionFactory().createValueExpression(elContext, "#{tableLazyDynamicModelInit.lazyModelDynamic}", Object.class);
//		log.info("FilterZZ1zzzz:#0,#1,#2", String.valueOf(filterByVE.getValue(elContext)));
//		for (Object aa :  dataTable.getFilterMetadata()) {
//			log.info("FilterZZ:#0,#1,#2",(FilterM));
			
//		}
//		for (UIColumn bb: dataTable.getColumns()) {
////			log.info("FilterZZ:#0,#1",dataTable.getColumns().size(), aa.getFilterBy());
//			log.info("Column : #0,#1, #2", bb.isDynamic(), bb.getFilterBy(), bb.getContainerClientId(context));
//			
//		}
		return filterPivotTableContent;
	}

	public void setFilterPivotTableContent(List<Map<String, String>> filterPivotTableContent) {
		this.filterPivotTableContent = filterPivotTableContent;
	}

	public T getSelectedDynamicModel() {
		return selectedDynamicModel;
	}

	public void setSelectedDynamicModel(T selectedDynamicModel) {
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
	}*/
}
