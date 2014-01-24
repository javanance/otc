package com.eugenefe.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Root;

import org.apache.derby.tools.sysinfo;
import org.primefaces.component.tree.Tree;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.eugenefe.entity.IPortfolio;
import com.eugenefe.entity.IrCurve;
import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.VolatilityHis;

public class ComponentReflection {

	public static TreeNode getPropertyTree(Class klazz) {
		// Map<Field, Class> rst = new HashMap<Field, Class>();
		Map<Class, List<Field>> rst = new HashMap<Class, List<Field>>();

		TreeNode superNode = new DefaultTreeNode("superRoot", null);
		TreeNode rootNode = new DefaultTreeNode(klazz.getName(), superNode);
		recursiveProperties(klazz, rst);
		addPropertyNode(rst, klazz, rootNode);
		initTree(rootNode);

		// List<Field> fieldRst = new ArrayList<Field>();
		// fieldRst = recursiveProperties(klazz);
		// for(Field ff : fieldRst){
		// System.out.println("Field Recu "+ ff.getName()+ "_" +
		// ff.getType().getName());
		// }
		return rootNode;
	}

	public static String getRecursivePropertyName(TreeNode node, TreeNode rootNode) {
		if (node.getParent().equals(rootNode)) {
			return ((Field) node.getData()).getName();

		} else {
			return getRecursivePropertyName(node.getParent(), rootNode) + "." + ((Field) node.getData()).getName();
		}
	}

	public static void recursiveProperties(Class klazz, Map<Class, List<Field>> rst) {
		Class fieldKlazz;
		List<Field> tempList = new ArrayList<Field>();

		for (Field field : klazz.getDeclaredFields()) {
			// System.out.println("Annotation 0: "+
			// field.getName()+"_"+field.getAnnotations().length +"_"+
			// field.getAnnotation(VolatilityHis.class));
			for (Annotation anno : field.getDeclaredAnnotations()) {
				System.out.println("Annotation : " + field.getName() + "_" + anno.toString());
			}
			tempList.add(field);

			fieldKlazz = field.getType();
			if (!fieldKlazz.isPrimitive()) {
				if (fieldKlazz.getPackage().getName().contains("com.eugenefe.entity")
						|| fieldKlazz.getPackage().getName().contains("com.eugenefe.controller")) {
					recursiveProperties(fieldKlazz, rst);
				}
			}
		}
		rst.put(klazz, tempList);
	}

	private static void addPropertyNode(Map<Class, List<Field>> rst, Class klazz, TreeNode node) {
		// System.out.println("Klazz:" + klazz.getName());
		if (rst.get(klazz) != null) {
			for (Field ff : rst.get(klazz)) {
				TreeNode childNode = new DefaultTreeNode(ff, node);
				// childNode.setExpanded(true);
				addPropertyNode(rst, ff.getType(), childNode);
			}
		}
	}

	// ****************************Method Reflection*******************
	public static TreeNode getMethodTree(Class klazz) {
		Map<Class, List<Method>> rst = new HashMap<Class, List<Method>>();

		TreeNode superNode = new DefaultTreeNode("superRoot", null);
		TreeNode rootNode = new DefaultTreeNode(klazz, superNode);
		// superNode.setExpanded(true);
		rootNode.setExpanded(true);
		// rootNode.setSelectable(false);
		recursiveMethod(klazz, rst);

		addMethodNode(rst, klazz, rootNode);
		initTree(rootNode);

		return superNode;
	}

	public static String getRecursiveMethodName(TreeNode node, TreeNode rootNode) {
		if (node.getParent().equals(rootNode)) {
			return ((Method) node.getData()).getName();

		} else {
			return getRecursiveMethodName(node.getParent(), rootNode) + "_" + ((Method) node.getData()).getName();
		}
	}

	private static void addMethodNode(Map<Class, List<Method>> rst, Class klazz, TreeNode node) {
		// System.out.println("Klazz:" + klazz.getName());
		if (rst.get(klazz) != null) {
			for (Method mm : rst.get(klazz)) {
				TreeNode childNode = new DefaultTreeNode(mm, node);
				// childNode.setExpanded(true);
				addMethodNode(rst, mm.getReturnType(), childNode);
			}
		}
	}

	public static void recursiveMethod(Class klazz, Map<Class, List<Method>> rst) {
		Class methodRtnKlazz;

		List<Method> tempList = new ArrayList<Method>();
		Method[] tempMethod = klazz.getDeclaredMethods();
		
//		AnnoMethodOrder a;
//		Class c = IrCurve.class;
//		for(Method mm : c.getMethods()){
////			log.info("Annotation 1: #0, #1,#2", mm.getName());
//			if( mm.isAnnotationPresent(AnnoMethodOrder.class)){
//				a = mm.getAnnotation(AnnoMethodOrder.class);
////				log.info("Annotation : #0, #1,#2", mm.getName(), a.value());
//				System.out.println(String.format("%s's type is '%s'", mm.getName(), a.value()));
//			}
//		}
		
		sortMethod(tempMethod);
		// for (Method mtd : klazz.getDeclaredMethods()) {
		for (Method mtd : tempMethod) {
//			System.out.println("Method:" + mtd.getName());
			if (mtd.getName().startsWith("get") || mtd.getName().startsWith("is")) {
				// System.out.println("Annotation 0: " + mtd.getName() + "_" +
				// mtd.getAnnotations().length );
				tempList.add(mtd);
				methodRtnKlazz = mtd.getReturnType();
				if (!methodRtnKlazz.isPrimitive()) {
					if(methodRtnKlazz.isAnnotationPresent(AnnoNavigationFilter.class)){
						recursiveMethod(methodRtnKlazz, rst);
					}
//					if (methodRtnKlazz.getPackage().getName().contains("com.eugenefe.entity")
//							|| methodRtnKlazz.getPackage().getName().contains("com.eugenefe.controller")) {
//						recursiveMethod(methodRtnKlazz, rst);
//					}
				}
			}
			rst.put(klazz, tempList);
		}

	}

	private static void initTree(TreeNode rootNode) {
		String temp;
		for (TreeNode node : rootNode.getChildren()) {
			temp = ComponentReflection.getRecursiveMethodName(node, rootNode);
			if (node.isLeaf()) {
				node.setSelected(true);
				// node.setExpanded(true);
			} else if (temp.contains("Id")) {
				node.setSelected(true);
				node.setExpanded(true);
				for (TreeNode subNode : node.getChildren()) {
					subNode.setSelected(true);
					// subNode.setExpanded(true);
				}
			}
		}
	}

	public static void sortMethod(Method[] methods) {
		Arrays.sort(methods, new Comparator<Method>() {

			@Override
			public int compare(Method mtd1, Method mtd2) {
//				System.out.println("In Method Sort"+ mtd1.getName()+":"+ mtd2.getName());
				AnnoMethodTree mtdOrder1 = mtd1.getAnnotation(AnnoMethodTree.class);
				AnnoMethodTree mtdOrder2 = mtd2.getAnnotation(AnnoMethodTree.class);
				if (mtdOrder1 != null && mtdOrder2 != null) {
//					System.out.println("In sort1");
					return mtdOrder1.order() - mtdOrder2.order();
				} else if (mtdOrder1 != null && mtdOrder2 == null) {
//					System.out.println("In sort2");
					return -1;
				} else if (mtdOrder1 == null && mtdOrder2 != null) {
//					System.out.println("In sort3");
					return 1;
				}
//				System.out.println("In sort4");
				return mtd1.getName().compareTo(mtd2.getName());
			}
		});
//		for(Method mm : methods){
//			System.out.println("Sorted : " + mm.getName());
//		}
	}

	// public static List<Field> recursiveProperties(Class klazz) {
	// Class fieldKlazz;
	// List<Field> tempList = new ArrayList<Field>();
	//
	// for (Field field : klazz.getDeclaredFields()) {
	// tempList.add(field);
	//
	// fieldKlazz = field.getType();
	// if (!fieldKlazz.isPrimitive()) {
	// if (fieldKlazz.getPackage().getName().contains("com.eugenefe.entity")
	// || fieldKlazz.getPackage().getName().contains("com.eugenefe.controller"))
	// {
	// tempList.addAll(recursiveProperties(fieldKlazz));
	// }
	// }
	// }
	// return tempList;
	// }
}
