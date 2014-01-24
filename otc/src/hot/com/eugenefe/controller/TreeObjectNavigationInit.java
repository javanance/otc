package com.eugenefe.controller;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Log;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.eugenefe.converter.TableDynamicColumn;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;
import com.eugenefe.util.EColumnType;
import com.eugenefe.util.ENavigationData;
//import org.jboss.seam.framework.Query;

@Name("treeObjectNavigationInit")
@Scope(ScopeType.CONVERSATION)
public class TreeObjectNavigationInit {
	@Logger
	private Log log;

	@In
	private Map<String, String> messages;

	@RequestParameter
	private String navigation;

	private TreeNode rootNode;
	private TreeNode superNode;

	@Out
	private List<TableDynamicColumn> initPivotTableHeader;

	public TreeObjectNavigationInit() {
		System.out.println("Construction TreeObjectNavigationInit");
	}

	// *******************************************
	@Create
	public void create() {
		Class klass;
		superNode = new DefaultTreeNode("superRoot", null);
		superNode.setExpanded(true);

		dupl = new HashSet<Class>();

		try {
			log.info("Navigation:#0", navigation);
			klass = Class.forName(ENavigationData.valueOf(navigation).getQualifiedName());
			classStack.add(klass.getName());
			
			rootNode = new DefaultTreeNode(new TableDynamicColumn(klass.getSimpleName(), klass.getSimpleName(),
					EColumnType.String, null, 0, false, 1, true, "left"), superNode);
			rootNode.setExpanded(true);

			recursiveTableCoulumn(klass, rootNode);
			initTree(rootNode);

			// for(TreeNode nn : rootNode.getChildren()){
			// log.info("TreeNode :#0,#1",((TableDynamicColumn)
			// nn.getData()).getColumnId(), ((TableDynamicColumn)
			// nn.getData()).getColumnName());
			// }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Factory(value = "initPivotTableHeader", scope = ScopeType.CONVERSATION)
	public void initTree(TreeNode rootNode) {
		initPivotTableHeader = new ArrayList<TableDynamicColumn>();
		TableDynamicColumn temp;
		TableDynamicColumn subTemp;

		for (TreeNode node : rootNode.getChildren()) {
			temp = ((TableDynamicColumn) node.getData());
			if (temp.isInitialzied()&& !temp.getColumnType().isCollection()) {
				node.setSelected(true);
				if (node.isLeaf()) {
					initPivotTableHeader.add(temp);
				}
				for (TreeNode subNode : node.getChildren()) {
					subTemp = (TableDynamicColumn) subNode.getData();
					if (subTemp.isInitialzied() ) {
						subNode.setSelected(true);
						// subNode.setExpanded(true);
						if (subNode.isLeaf()&& !temp.getColumnType().isCollection()) {
							node.setExpanded(true);
							initPivotTableHeader.add(((TableDynamicColumn) subNode.getData()));
						}
					}
				}
			}
		}
	}
	@Factory(value = "initPivotTableHeader", scope = ScopeType.CONVERSATION)
	private void initHeader(List<TreeNode> childrenNodes){
		initPivotTableHeader = new ArrayList<TableDynamicColumn>();
		initDetailTab = new ArrayList<TableDynamicColumn>();
		initDetailModelHeaderMap = new HashMap<String, List<TableDynamicColumn>>();
		List<TableDynamicColumn> tempList ;
		
		TableDynamicColumn temp;
		TableDynamicColumn subTemp;
		 
		
		TableDynamicColumn tempHeader;		
		for (TreeNode node : childrenNodes) {
			tempHeader = ((TableDynamicColumn) node.getData());
			if(tempHeader.getColumnType().isCollection()){
				initDetailTab.add(tempHeader);
//				for(TreeNode subNode : node.getChildren()){
					tempList = new ArrayList<TableDynamicColumn>();
					initRec(tempList,node.getChildren());
					initDetailModelHeaderMap.put(tempHeader.getColumnId(), tempList);
//				}
			}
			else if (node.isLeaf() ){
					initPivotTableHeader.add(tempHeader);
			}
		}	
		
	}
	
	private void initRec(List<TableDynamicColumn> list, List<TreeNode> nodes){
		TableDynamicColumn tempHeader;		
		for(TreeNode node : nodes) {
			tempHeader = ((TableDynamicColumn) node.getData());

			switch (tempHeader.getColumnType()){
			case Entity : 
				initRec(list, node.getChildren());
			case List:
				break;
			
			case Map:
				break;
			default :
				list.add(new TableDynamicColumn(tempHeader.getColumnId().replace(tempHeader.getColumnId()+"_", "")
							, tempHeader.getColumnName(), tempHeader.getColumnType(), tempHeader, tempHeader.getColumnLevel(), false
							, tempHeader.getColumnOrder(), false, tempHeader.getAlignFormat()));
					
			}	

		}
	}
	
//	@Out(value="xxx", scope=ScopeType.CONVERSATION)
	private Map<String, List<TableDynamicColumn>> initDetailModelHeaderMap;
//	@Out(value="yyy" , scope=ScopeType.CONVERSATION)
	private List<TableDynamicColumn> initDetailTab;
	public Map<String, List<TableDynamicColumn>> getInitDetailModelHeaderMap() {
		return initDetailModelHeaderMap;
	}
	public void setInitDetailModelHeaderMap(Map<String, List<TableDynamicColumn>> initDetailModelHeaderMap) {
		this.initDetailModelHeaderMap = initDetailModelHeaderMap;
	}
	public List<TableDynamicColumn> getInitDetailTab() {
		return initDetailTab;
	}
	public void setInitDetailTab(List<TableDynamicColumn> initDetailTab) {
		this.initDetailTab = initDetailTab;
	}

	private List<String> classStack = new ArrayList<String>();
	private boolean isInCollection=false ;
	private int collectionNodeLevel =1;
	
	private void recursiveTableCoulumn(Class klazz, TreeNode node) {
		Class<?> methodRtnKlazz;
		Class<?> genericTypeKlazz;
		double orderScale;
		int idx=0;
		String temp = "";
		String tempParent;
		
		Method[] tempMethod = klazz.getDeclaredMethods();
		sortMethod(tempMethod);
		
//		Class rtnType;
//		Class mapClass, listClass;
//
//		try {
//			mapClass = Class.forName("java.util.Map");
//			listClass = Class.forName("java.util.List");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
		
		TableDynamicColumn parentColumn = (TableDynamicColumn) node.getData();
		TableDynamicColumn tempColumn;
		
//		if (parentColumn.getParentColumnId() != null) {
		if(parentColumn.getParentColumn()!=null){
			tempParent = parentColumn.getColumnId() + "_";
//			tempParent = parentColumn.getColumnId() ;
		} else {
			tempParent = "";
		}

		for (Method mtd : tempMethod) {
			if (mtd.isAnnotationPresent(AnnoMethodTree.class)) {
				if( parentColumn.getColumnLevel() <= collectionNodeLevel){
					isInCollection = false;
				}
				
				methodRtnKlazz = mtd.getReturnType();
				genericTypeKlazz = null;
//				log.info("Method ZZ:#0, #1,#2,#3", mtd.getName(), parentColumn.isInTheCollection(),classStack);

				
				// Map, List 에서는 어떠한 재귀적인 Map, List 를 Node 로 추가하지 않는다
				if (methodRtnKlazz.getName().contains("Map")) {
					if(isInCollection){
						continue;
					}
					isInCollection = true;
					collectionNodeLevel= Math.min(collectionNodeLevel, parentColumn.getColumnLevel());
//					if(parentColumn.getColumnLevel()> collectionNodeLevel){
//						collectionNodeLevel = parentColumn.getColumnLevel(); 
//					}
					genericTypeKlazz = (Class) ((ParameterizedType) mtd.getGenericReturnType())
							.getActualTypeArguments()[1];
				} else if (methodRtnKlazz.getName().contains("List")) {
					if(isInCollection){
						continue;
					}
					isInCollection = true;
					collectionNodeLevel= Math.min(collectionNodeLevel, parentColumn.getColumnLevel());
//					collectionNodeLevel = parentColumn.getColumnLevel();
					genericTypeKlazz = (Class) ((ParameterizedType) mtd.getGenericReturnType())
							.getActualTypeArguments()[0];
				}

				if (genericTypeKlazz != null) {
					methodRtnKlazz = genericTypeKlazz;
				}
//				log.info("Before Continue :#0,#1", mtd.getName(),methodRtnKlazz.getName());
				
				if(classStack.contains(methodRtnKlazz.getName())&& classStack.size()>1){
//					log.info("IN Continue :#0,#1,#2", methodRtnKlazz.getName(),classStack, classStack.size());
//					classStack.remove(klazz.getName());	
					continue;
				}
//				log.info("Method ZZ1:#0, #1", mtd.getName());
				temp = tempParent + mtd.getName();
				AnnoMethodTree anno = mtd.getAnnotation(AnnoMethodTree.class);
				orderScale = Math.pow(0.1, parentColumn.getColumnLevel() + 1);
				tempColumn = new TableDynamicColumn(temp, messages.get(mtd.getName()),anno.type()
						, parentColumn, parentColumn.getColumnLevel() + 1	,isInCollection
						, parentColumn.getColumnOrder()+ anno.order() * orderScale, anno.init(), anno.align());
				
				
//				if(listClass.equals(mtd.getReturnType())){
//					rtnType= (Class)((ParameterizedType)mtd.getGenericReturnType()).getActualTypeArguments()[0];
//					tempColumn.setReturnType(methodRtnKlazz.getName());
//				}
//				else if(mapClass.equals(mtd.getReturnType())){
//					rtnType= (Class)((ParameterizedType)mtd.getGenericReturnType()).getActualTypeArguments()[1];
//					tempColumn.setReturnType(methodRtnKlazz.getName());
//				}
				tempColumn.setReturnType(methodRtnKlazz.getSimpleName());
				
//				log.info("XXXXX:#0", tempColumn.getReturnType());
				
				TreeNode childNode = new DefaultTreeNode(tempColumn, node);
//						TreeNode childNode = new DefaultTreeNode(new TableDynamicColumn(temp, messages.get(mtd.getName())		
//								,anno.type(), parentColumn, parentColumn.getColumnLevel() + 1
//								,isInCollection
//								,parentColumn.getColumnOrder()+ anno.order() * orderScale, anno.init(), anno.align()), node);

				if (methodRtnKlazz.isAnnotationPresent(AnnoNavigationFilter.class)) {	
					classStack.add(methodRtnKlazz.getName());
//					log.info("Method ZZzz:#0, #1,#2,#3", mtd.getName(), collectionNodeLevel,classStack);
					recursiveTableCoulumn(methodRtnKlazz, childNode);
					classStack.remove(methodRtnKlazz.getName());		
				}
			}
		}
//		classStack.remove(klazz.getName());
	}

	private void sortMethod(Method[] methods) {
		Arrays.sort(methods, new Comparator<Method>() {

			@Override
			public int compare(Method mtd1, Method mtd2) {
				AnnoMethodTree mtdOrder1 = mtd1.getAnnotation(AnnoMethodTree.class);
				AnnoMethodTree mtdOrder2 = mtd2.getAnnotation(AnnoMethodTree.class);
				if (mtdOrder1 != null && mtdOrder2 != null) {
					return mtdOrder1.order() - mtdOrder2.order();
				} else if (mtdOrder1 != null && mtdOrder2 == null) {
					return -1;
				} else if (mtdOrder1 == null && mtdOrder2 != null) {
					return 1;
				}
				return mtd1.getName().compareTo(mtd2.getName());
			}
		});
	}

	// ***********************************Getter and Setter*********************
	public TreeNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public TreeNode getSuperNode() {
		return superNode;
	}

	public void setSuperNode(TreeNode superNode) {
		this.superNode = superNode;
	}

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	public List<TableDynamicColumn> getInitPivotTableHeader() {
		return initPivotTableHeader;
	}

	public void setInitPivotTableHeader(List<TableDynamicColumn> initPivotTableHeader) {
		this.initPivotTableHeader = initPivotTableHeader;
	}

	private Set<Class> dupl;
	private int collectionDepth;
	
	private void recursiveTableCoulumnOld(Class klazz, TreeNode node) {
		Class<?> methodRtnKlazz;
		Class<?> genericTypeKlazz;
		double orderScale;
		String temp = "";
		String tempParent;
		
//		boolean flag = classStack.contains(klazz.getName()); 
//		if(!flag){
//			classStack.add(klazz.getName());
//		}

		Method[] tempMethod = klazz.getDeclaredMethods();
		sortMethod(tempMethod);

		TableDynamicColumn parentColumn = (TableDynamicColumn) node.getData();
		
//		if (parentColumn.getParentColumnId() != null) {
			if(parentColumn.getParentColumn()!=null){	
			tempParent = parentColumn.getColumnId() + "_";
		} else {
			tempParent = "";
		}

		for (Method mtd : tempMethod) {
			if (mtd.isAnnotationPresent(AnnoMethodTree.class)) {
				if( parentColumn.getColumnLevel() <= collectionNodeLevel){
					isInCollection = false;
				}
				
				methodRtnKlazz = mtd.getReturnType();
				genericTypeKlazz = null;
//				log.info("Method ZZ:#0, #1,#2,#3", mtd.getName(), parentColumn.isInTheCollection(),classStack);

				
				
				
				// Map, List 에서는 어떠한 재귀적인 Map, List 를 Node 로 추가하지 않는다
				if (methodRtnKlazz.getName().contains("Map")) {
					if(isInCollection){
						continue;
					}
					isInCollection = true;
					collectionNodeLevel= Math.min(collectionNodeLevel, parentColumn.getColumnLevel());
//					if(parentColumn.getColumnLevel()> collectionNodeLevel){
//						collectionNodeLevel = parentColumn.getColumnLevel(); 
//					}
					genericTypeKlazz = (Class) ((ParameterizedType) mtd.getGenericReturnType())
							.getActualTypeArguments()[1];
				} else if (methodRtnKlazz.getName().contains("List")) {
					if(isInCollection){
						continue;
					}
					isInCollection = true;
					collectionNodeLevel= Math.min(collectionNodeLevel, parentColumn.getColumnLevel());
//					collectionNodeLevel = parentColumn.getColumnLevel();
					genericTypeKlazz = (Class) ((ParameterizedType) mtd.getGenericReturnType())
							.getActualTypeArguments()[0];
				}

				if (genericTypeKlazz != null) {
					methodRtnKlazz = genericTypeKlazz;
				}
				log.info("Before Continue :#0,#1", mtd.getName(),methodRtnKlazz.getName());
				
				if(classStack.contains(methodRtnKlazz.getName())){
					log.info("IN Continue :#0,#1", methodRtnKlazz.getName(),classStack);
//					classStack.remove(klazz.getName());	
					continue;
				}
//				log.info("Method ZZ1:#0, #1", mtd.getName());
				temp = tempParent + mtd.getName();
				AnnoMethodTree anno = mtd.getAnnotation(AnnoMethodTree.class);
				orderScale = Math.pow(0.1, parentColumn.getColumnLevel() + 1);

				TreeNode childNode = new DefaultTreeNode(new TableDynamicColumn(temp, messages.get(mtd.getName())
								,anno.type(), parentColumn, parentColumn.getColumnLevel() + 1
								,isInCollection
								,parentColumn.getColumnOrder()+ anno.order() * orderScale, anno.init(), anno.align()), node);

//				if(methodRtnKlazz.isAnnotationPresent(AnnoNavigationFilter.class)){
//				}
//					if (methodRtnKlazz.isAnnotationPresent(AnnoNavigationFilter.class) && !flag && !classStack.contains(methodRtnKlazz.getName())) {
						if (methodRtnKlazz.isAnnotationPresent(AnnoNavigationFilter.class)) {	
//						if (methodRtnKlazz.isAnnotationPresent(AnnoNavigationFilter.class)) {	
							classStack.add(methodRtnKlazz.getName());
							log.info("Method ZZzz:#0, #1,#2,#3", mtd.getName(), collectionNodeLevel,classStack);
						recursiveTableCoulumn(methodRtnKlazz, childNode);
						classStack.remove(methodRtnKlazz.getName());		
					}
			}
		}
//		classStack.remove(klazz.getName());
	}
}
