package com.eugenefe.controller;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.hibernate.Query;
import org.hibernate.Session;
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
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.eugenefe.converter.TableDynamicColumn;
import com.eugenefe.entity.PortfolioDefine;
import com.eugenefe.entity.Position;
import com.eugenefe.enums.EEquation;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;
import com.eugenefe.util.EColumnType;
import com.eugenefe.util.ENavigationData;

//import org.jboss.seam.framework.Query;

@Name("treePositionNavigationInit")
@Scope(ScopeType.CONVERSATION)
public class TreePositionNavigationInit {
	@Logger	private Log log;
	@In		private Map<String, String> messages;
	@In		private Session session;
	
	private TreeNode rootNode;
	private TreeNode superNode;
	
	private TreeNode targetSuperNode;
	private TreeNode targetRootNode;
//	private List<PortfolioDefine> pfDefineList = new ArrayList<PortfolioDefine>();
	private List<PortfolioDefine> pfDefineList;
	
	public List<PortfolioDefine> getPfDefineList() {
		return pfDefineList;
	}
	public void setPfDefineList(List<PortfolioDefine> pfDefineList) {
		this.pfDefineList = pfDefineList;
	}	

	public TreeNode getTargetSuperNode() {
		return targetSuperNode;
	}

	public void setTargetSuperNode(TreeNode targetSuperNode) {
		this.targetSuperNode = targetSuperNode;
	}

	public TreeNode getTargetRootNode() {
		return targetRootNode;
	}

	public void setTargetRootNode(TreeNode targetRootNode) {
		this.targetRootNode = targetRootNode;
	}

	private String portPrefix;
	public String getPortPrefix() {
		return portPrefix;
	}
	public void setPortPrefix(String portPrefix) {
		this.portPrefix = portPrefix;
	}

	public TreePositionNavigationInit() {
		System.out.println("Construction TreePositionNavigationInit");
	}

// *******************************************
	@Create
	public void create() {
		Class klass;
		superNode = new DefaultTreeNode("superRoot", null);
		superNode.setExpanded(true);
		
		targetSuperNode = new DefaultTreeNode("targetRoot", null);
		targetRootNode = new DefaultTreeNode(new TableDynamicColumn("Target", "Target",
				EColumnType.String, null, 0, false, 1, true, "left"), targetSuperNode);
		targetRootNode.setExpanded(true);
		dupl = new HashSet<Class>();

		klass = Position.class;
		classStack.add(klass.getName());

//		rootNode = new DefaultTreeNode(new TableDynamicColumn(klass.getSimpleName(), klass.getSimpleName(),
//				EColumnType.String, null, 0, false, 1, true, "left"), superNode);
		rootNode = new DefaultTreeNode(new TableDynamicColumn("Source", "Source",
				EColumnType.String, null, 0, false, 1, true, "left"), superNode);
		rootNode.setExpanded(true);

		recursiveTableCoulumn(klass, rootNode);
		initTree(rootNode);

		// for(TreeNode nn : rootNode.getChildren()){
		// log.info("TreeNode :#0,#1",((TableDynamicColumn)
		// nn.getData()).getColumnId(), ((TableDynamicColumn)
		// nn.getData()).getColumnName());
		// }
//		aaa();


	}

	// @Factory(value = "initPivotTableHeader", scope = ScopeType.CONVERSATION)
	public void initTree(TreeNode rootNode) {

		TableDynamicColumn temp;
		TableDynamicColumn subTemp;

		for (TreeNode node : rootNode.getChildren()) {
			temp = ((TableDynamicColumn) node.getData());
			if (temp.isInitialzied() && !temp.getColumnType().isCollection()) {
				node.setSelected(false);
				if (node.isLeaf()) {

				}
				for (TreeNode subNode : node.getChildren()) {
					subTemp = (TableDynamicColumn) subNode.getData();
					if (subTemp.isInitialzied()) {
						subNode.setSelected(false);
						// subNode.setExpanded(true);
						if (subNode.isLeaf() && !temp.getColumnType().isCollection()) {
							node.setExpanded(false);

						}
					}
				}
			}
		}
	}

	private void initRec(List<TableDynamicColumn> list, List<TreeNode> nodes) {
		TableDynamicColumn tempHeader;
		for (TreeNode node : nodes) {
			tempHeader = ((TableDynamicColumn) node.getData());

			switch (tempHeader.getColumnType()) {
			case Entity:
				initRec(list, node.getChildren());
			case List:
				break;

			case Map:
				break;
			default:
				list.add(new TableDynamicColumn(tempHeader.getColumnId().replace(tempHeader.getColumnId() + "_", ""),
						tempHeader.getColumnName(), tempHeader.getColumnType(), tempHeader,
						tempHeader.getColumnLevel(), false, tempHeader.getColumnOrder(), false, tempHeader
								.getAlignFormat()));

			}

		}
	}

	// @Out(value="xxx", scope=ScopeType.CONVERSATION)
	private Map<String, List<TableDynamicColumn>> initDetailModelHeaderMap;
	// @Out(value="yyy" , scope=ScopeType.CONVERSATION)
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
	private boolean isInCollection = false;
	private int collectionNodeLevel = 1;
	private Map<String, List<String>> propMap = new HashMap<String, List<String>>();
	
	private void recursiveTableCoulumn(Class klazz, TreeNode node) {
		Class<?> methodRtnKlazz;
		Class<?> genericTypeKlazz;
		double orderScale;
		int idx = 0;
		String temp = "";
		String tempParent;

		Method[] tempMethod = klazz.getDeclaredMethods();
		sortMethod(tempMethod);

		// Class rtnType;
		// Class mapClass, listClass;
		//
		// try {
		// mapClass = Class.forName("java.util.Map");
		// listClass = Class.forName("java.util.List");
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// }

		TableDynamicColumn parentColumn = (TableDynamicColumn) node.getData();
		TableDynamicColumn tempColumn;

		// if (parentColumn.getParentColumnId() != null) {
		if (parentColumn.getParentColumn() != null) {
			tempParent = parentColumn.getColumnId() + "_";
			// tempParent = parentColumn.getColumnId() ;
		} else {
			tempParent = "";
		}

		for (Method mtd : tempMethod) {
			if (mtd.isAnnotationPresent(AnnoMethodTree.class)) {
				if (parentColumn.getColumnLevel() <= collectionNodeLevel) {
					isInCollection = false;
				}

				methodRtnKlazz = mtd.getReturnType();
				genericTypeKlazz = null;
				// log.info("Method ZZ:#0, #1,#2,#3", mtd.getName(),
				// parentColumn.isInTheCollection(),classStack);

				// Map, List 에서는 어떠한 재귀적인 Map, List 를 Node 로 추가하지 않는다
				if (methodRtnKlazz.getName().contains("Map")) {
					if (isInCollection) {
						continue;
					}
					isInCollection = true;
					collectionNodeLevel = Math.min(collectionNodeLevel, parentColumn.getColumnLevel());
					// if(parentColumn.getColumnLevel()> collectionNodeLevel){
					// collectionNodeLevel = parentColumn.getColumnLevel();
					// }
					genericTypeKlazz = (Class) ((ParameterizedType) mtd.getGenericReturnType())
							.getActualTypeArguments()[1];
				} else if (methodRtnKlazz.getName().contains("List")) {
					if (isInCollection) {
						continue;
					}
					isInCollection = true;
					collectionNodeLevel = Math.min(collectionNodeLevel, parentColumn.getColumnLevel());
					// collectionNodeLevel = parentColumn.getColumnLevel();
					genericTypeKlazz = (Class) ((ParameterizedType) mtd.getGenericReturnType())
							.getActualTypeArguments()[0];
				}

				if (genericTypeKlazz != null) {
					methodRtnKlazz = genericTypeKlazz;
				}
				// log.info("Before Continue :#0,#1",
				// mtd.getName(),methodRtnKlazz.getName());

				if (classStack.contains(methodRtnKlazz.getName()) && classStack.size() > 1) {
					// log.info("IN Continue :#0,#1,#2",
					// methodRtnKlazz.getName(),classStack, classStack.size());
					// classStack.remove(klazz.getName());
					continue;
				}
				// log.info("Method ZZ1:#0, #1", mtd.getName());
				temp = tempParent + mtd.getName();
				AnnoMethodTree anno = mtd.getAnnotation(AnnoMethodTree.class);
				orderScale = Math.pow(0.1, parentColumn.getColumnLevel() + 1);
				tempColumn = new TableDynamicColumn(temp, messages.get(mtd.getName()), anno.type(), parentColumn,
						parentColumn.getColumnLevel() + 1, isInCollection, parentColumn.getColumnOrder() + anno.order()
								* orderScale, anno.init(), anno.align());

				// if(listClass.equals(mtd.getReturnType())){
				// rtnType=
				// (Class)((ParameterizedType)mtd.getGenericReturnType()).getActualTypeArguments()[0];
				// tempColumn.setReturnType(methodRtnKlazz.getName());
				// }
				// else if(mapClass.equals(mtd.getReturnType())){
				// rtnType=
				// (Class)((ParameterizedType)mtd.getGenericReturnType()).getActualTypeArguments()[1];
				// tempColumn.setReturnType(methodRtnKlazz.getName());
				// }
				tempColumn.setReturnType(methodRtnKlazz.getSimpleName());

				// log.info("XXXXX:#0", tempColumn.getReturnType());

				TreeNode childNode = new DefaultTreeNode(tempColumn, node);
				// TreeNode childNode = new DefaultTreeNode(new
				// TableDynamicColumn(temp, messages.get(mtd.getName())
				// ,anno.type(), parentColumn, parentColumn.getColumnLevel() + 1
				// ,isInCollection
				// ,parentColumn.getColumnOrder()+ anno.order() * orderScale,
				// anno.init(), anno.align()), node);

				if (methodRtnKlazz.isAnnotationPresent(AnnoNavigationFilter.class)) {
					classStack.add(methodRtnKlazz.getName());
					// log.info("Method ZZzz:#0, #1,#2,#3", mtd.getName(),
					// collectionNodeLevel,classStack);
					recursiveTableCoulumn(methodRtnKlazz, childNode);
					classStack.remove(methodRtnKlazz.getName());
				}
			}
		}
		// classStack.remove(klazz.getName());
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

	private Set<Class> dupl;
	private int collectionDepth;

	private void recursiveTableCoulumnOld(Class klazz, TreeNode node) {
		Class<?> methodRtnKlazz;
		Class<?> genericTypeKlazz;
		double orderScale;
		String temp = "";
		String tempParent;

		// boolean flag = classStack.contains(klazz.getName());
		// if(!flag){
		// classStack.add(klazz.getName());
		// }

		Method[] tempMethod = klazz.getDeclaredMethods();
		sortMethod(tempMethod);

		TableDynamicColumn parentColumn = (TableDynamicColumn) node.getData();

		// if (parentColumn.getParentColumnId() != null) {
		if (parentColumn.getParentColumn() != null) {
			tempParent = parentColumn.getColumnId() + "_";
		} else {
			tempParent = "";
		}

		for (Method mtd : tempMethod) {
			if (mtd.isAnnotationPresent(AnnoMethodTree.class)) {
				if (parentColumn.getColumnLevel() <= collectionNodeLevel) {
					isInCollection = false;
				}

				methodRtnKlazz = mtd.getReturnType();
				genericTypeKlazz = null;
				// log.info("Method ZZ:#0, #1,#2,#3", mtd.getName(),
				// parentColumn.isInTheCollection(),classStack);

				// Map, List 에서는 어떠한 재귀적인 Map, List 를 Node 로 추가하지 않는다
				if (methodRtnKlazz.getName().contains("Map")) {
					if (isInCollection) {
						continue;
					}
					isInCollection = true;
					collectionNodeLevel = Math.min(collectionNodeLevel, parentColumn.getColumnLevel());
					// if(parentColumn.getColumnLevel()> collectionNodeLevel){
					// collectionNodeLevel = parentColumn.getColumnLevel();
					// }
					genericTypeKlazz = (Class) ((ParameterizedType) mtd.getGenericReturnType())
							.getActualTypeArguments()[1];
				} else if (methodRtnKlazz.getName().contains("List")) {
					if (isInCollection) {
						continue;
					}
					isInCollection = true;
					collectionNodeLevel = Math.min(collectionNodeLevel, parentColumn.getColumnLevel());
					// collectionNodeLevel = parentColumn.getColumnLevel();
					genericTypeKlazz = (Class) ((ParameterizedType) mtd.getGenericReturnType())
							.getActualTypeArguments()[0];
				}

				if (genericTypeKlazz != null) {
					methodRtnKlazz = genericTypeKlazz;
				}
				log.info("Before Continue :#0,#1", mtd.getName(), methodRtnKlazz.getName());

				if (classStack.contains(methodRtnKlazz.getName())) {
					log.info("IN Continue :#0,#1", methodRtnKlazz.getName(), classStack);
					// classStack.remove(klazz.getName());
					continue;
				}
				// log.info("Method ZZ1:#0, #1", mtd.getName());
				temp = tempParent + mtd.getName();
				AnnoMethodTree anno = mtd.getAnnotation(AnnoMethodTree.class);
				orderScale = Math.pow(0.1, parentColumn.getColumnLevel() + 1);

				TreeNode childNode = new DefaultTreeNode(new TableDynamicColumn(temp, messages.get(mtd.getName()),
						anno.type(), parentColumn, parentColumn.getColumnLevel() + 1, isInCollection,
						parentColumn.getColumnOrder() + anno.order() * orderScale, anno.init(), anno.align()), node);

				// if(methodRtnKlazz.isAnnotationPresent(AnnoNavigationFilter.class)){
				// }
				// if
				// (methodRtnKlazz.isAnnotationPresent(AnnoNavigationFilter.class)
				// && !flag && !classStack.contains(methodRtnKlazz.getName())) {
				if (methodRtnKlazz.isAnnotationPresent(AnnoNavigationFilter.class)) {
					// if
					// (methodRtnKlazz.isAnnotationPresent(AnnoNavigationFilter.class))
					// {
					classStack.add(methodRtnKlazz.getName());
					log.info("Method ZZzz:#0, #1,#2,#3", mtd.getName(), collectionNodeLevel, classStack);
					recursiveTableCoulumn(methodRtnKlazz, childNode);
					classStack.remove(methodRtnKlazz.getName());
				}
			}
		}
		// classStack.remove(klazz.getName());
	}
	

	public void onDrop1(TreeDragDropEvent event){
		log.info("AAA :#0,#1", targetRootNode.getChildCount() );
		TreeNode dragNode = event.getDragNode();
		TableDynamicColumn zz = (TableDynamicColumn)dragNode.getData();
		if(event.getDropNode().getData() instanceof String){
//			event.getDragNode().setParent(targetRootNode);
			for(TreeNode aa : targetSuperNode.getChildren()){
				if(!aa.equals(targetRootNode)){
					log.info("A :#0,#1", ((TableDynamicColumn)aa.getData()).getColumnName() );
					aa.setParent(targetRootNode);
					targetRootNode.getChildren().add(aa);
					targetSuperNode.getChildren().remove(aa);
				}
			}
			log.info("AAA :#0,#1", targetRootNode.getChildCount() );
		}else{
			TreeNode targetNode = event.getDragNode();
			TableDynamicColumn target = (TableDynamicColumn)event.getDropNode().getData();
			if( !targetNode.equals(targetRootNode)){
				dragNode.getParent().getChildren().remove(dragNode);
				dragNode.setParent(targetRootNode);
				targetRootNode.getChildren().add(dragNode);
			}
			log.info("AAA :#0,#1,#2", zz.getColumnName(), target.getColumnName(), targetRootNode.getChildren());
		}
		
		updateTable();
	}
	public void update(){
		log.info("AAA1111 :#0,#1,#2", targetRootNode.getChildren());
	}
	
	
	private void aaa(){
		
		log.info("Create :"	);
		pfDefineList = new ArrayList<PortfolioDefine>();
		PortfolioDefine temp = new PortfolioDefine();
		temp.setLevel("Level_" + 1);
		temp.setPropPath("aaaa");
//		temp.setPropColumn(target.getColumnName());
//		temp.setPropTable(target.getParentColumn().getColumnName());
		temp.setPortPrefix("BBBB");
//		temp.setProp(target);
		pfDefineList.add(temp);
		log.info("Create :#0", temp	);
	}
	public void updateTable(){
		pfDefineList = new ArrayList<PortfolioDefine>();
//		pfDefineList.clear();
		TableDynamicColumn target ; 
		PortfolioDefine temp ;
		int index =0;
		for(TreeNode aa : targetRootNode.getChildren()){
			target = (TableDynamicColumn)aa.getData();
		
			log.info("Target:#0,#1", target.getColumnId());
			
			index =index+1;
			target = (TableDynamicColumn)aa.getData();
			temp = new PortfolioDefine();
			temp.setLevel("Level_" + index);
			temp.setPropPath(target.getColumnId());
//			temp.setPropColumn(target.getColumnName());
//			temp.setPropTable(target.getParentColumn().getColumnName());
			temp.setPortPrefix(portPrefix);
//			temp.setProp(target);
			pfDefineList.add(temp);
		}
		log.info("Target:#0,#1 asdfa", pfDefineList.size());
	}
	public void makePortfolio(){
		List<Position> posList = new ArrayList<Position>(); 
//		Query query = session.createQuery("from Position a where a.basedate = :param");
//		query.setParameter("param", "20140201");
//		posList = query.list();
		posList = session.createQuery("from Position").list();
		
		Class<?> rtnType;

		Map<String, String> tempContentMap;

		Map<String, List<Map<String, String>>> tempListContentMap;
		Map<String, String> tempMapContentMap;
		List<Map<String, String>> tempMapList;

		Object navi;
		navi = Position.class;
		
		for(Position aa :posList){
			log.info("Position", aa.getPosId());
		}
		
		/*for(Position pos : posList){
			boolean flag = true;
			for(PortfolioDefine aa : pfDefineList){
//				TableDynamicColumn header = aa.getProp();
				try {
					for (String prop : header.getFullColumns()) {
						Method temp = navi.getClass().getDeclaredMethod(prop);

						rtnType = temp.getReturnType();

						navi = temp.invoke(navi);
						
			
					}
				} catch (Exception e) {
					log.info("Method Call Exception :#0", header.getFullColumns());
				}
				
				flag =check(aa.getEquation(), aa.getCondition1(), aa.getCondition2(), navi);
				if(!flag){
					break;
				}
			}
		}*/
	}
	
	private boolean check(EEquation eq, String cond1, String cond2, Object navi){
		switch (eq) {
		case DISTINCT:
		
		case EQUAL:
			if(navi instanceof String ){
				return String.valueOf(navi).equals(cond1);
			}
			else if(navi instanceof BigDecimal){
				return false; 
			}
			return false;
			

		default:
			return false;
		}
	}
	public void clearTarget(){
		
	}
}
