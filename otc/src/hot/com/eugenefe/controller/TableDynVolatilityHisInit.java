package com.eugenefe.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.core.ConversationIdGenerator;
import org.jboss.seam.core.Events;
//import org.jboss.seam.framework.Query;
import org.jboss.seam.log.Log;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;

import com.eugenefe.converter.LazyModelMarketVariable;
import com.eugenefe.converter.LazyModelVcvHis;
import com.eugenefe.converter.TableDynamicColumn;
import com.eugenefe.converter.TableDynamicContent;
import com.eugenefe.entity.IntRate;
import com.eugenefe.entity.IntRateHis;
import com.eugenefe.entity.IrCurve;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.VcvMatrix;
import com.eugenefe.entity.VcvMatrixHis;
import com.eugenefe.entity.Volatility;
import com.eugenefe.entity.VolatilityHis;
import com.eugenefe.entity.VolatilityHisId;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.util.ColumnModel;
import com.eugenefe.util.ComponentReflection;
import com.eugenefe.util.CrossTableModelOld;
import com.eugenefe.util.FlagBean;
import com.eugenefe.util.MarketVariableType;
import com.eugenefe.util.NamedQuery;
import com.eugenefe.util.PivotTableModel;
import com.lowagie.text.Header;

@Name("tableDynVolatiltiyHisInit")
@Scope(ScopeType.CONVERSATION)
public class TableDynVolatilityHisInit {
/*	@Logger
	private Log log;

	@In
	private Session session;

	@In(value = "#{basedateBean}")
	private BaseDateBean basedateBean;

	private List<VolatilityHis> volatilityHisList;

	private List<TableDynamicColumn> pivotTableHeader;
	private List<PivotTableModel<VolatilityHisId, TableDynamicColumn, TableDynamicContent>> pivotTableContent;
	private List<PivotTableModel<VolatilityHisId, TableDynamicColumn, TableDynamicContent>> filterPivotTableContent;

	public TableDynVolatilityHisInit() {
		System.out.println("Construction TableDynVolatilityHisInit");
	}

	// *******************************************
	// @Observer("changeBssd_/view/v130RfVcvMatrix.xhtml")
	@Create
	public void create() {
		// vcvId ="SMA_1DAY";
		initPivotTableHeader();

		loadVolatilityHis();
		loadTable();
	}

	// ******************************************
	public void initPivotTableHeader() {
		pivotTableHeader = new ArrayList<TableDynamicColumn>();

		// List<String> rst = new ArrayList<String>();
		Map<Field, Field> rst = new HashMap<Field, Field>();

		Map<Class, List<Field>> mapRst = new HashMap<Class, List<Field>>();

		Class<VolatilityHis> klass = VolatilityHis.class;
		Class<IntRateHis> klazz = IntRateHis.class;
		String temp = new String();
		Class klz;
		try {
			klz = Class.forName("com.eugenefe.entity.VolatilityHis");

			// klz = Class.forName("VolatilityHis");
			// ComponentReflection.recursiveProperties(klass, mapRst);

//			TreeNode rootNode = ComponentReflection.getPropertyTree(klz);
			TreeNode rootNode = ComponentReflection.getMethodTree(klz);
			log.info("New Header1: #0", rootNode.getChildCount());

			for (TreeNode node : rootNode.getChildren()) {
				// temp = ComponentReflection.getRecursiveName(node, rootNode);
				temp = ComponentReflection.getRecursiveMethodName(node, rootNode);
				log.info("New Header: #0", ComponentReflection.getRecursiveMethodName(node, rootNode));
				pivotTableHeader.add(new TableDynamicColumn(temp, temp));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadVolatilityHis() {
		Filter filter = session.enableFilter("filterBtwnDate").setParameter("stBssd", basedateBean.getStBssd())
				.setParameter("endBssd", basedateBean.getEndBssd());
		// Filter filterBssd =
		// session.enableFilter("filterCurrentDate").setParameter("bssd",
		// basedateBean.getBssd());

		volatilityHisList = session.createQuery("from VolatilityHis").list();
	}

	public void loadTable() {
		pivotTableContent = new ArrayList<PivotTableModel<VolatilityHisId, TableDynamicColumn, TableDynamicContent>>();
		// Collections.sort(pivotTableHeader);
		Object navi;

		for (VolatilityHis volHis : volatilityHisList) {
			Map<TableDynamicColumn, TableDynamicContent> tempContentMap = new HashMap<TableDynamicColumn, TableDynamicContent>();
			for (TableDynamicColumn header : pivotTableHeader) {
				navi = volHis;
				// log.info("header Prep:#0,#1", header.getColProperties());
				try {
					for (String prop : header.getColProperty()) {

						// Field temp = navi.getClass().getDeclaredField(prop);
						// temp.setAccessible(true);
						// navi = temp.get(navi);
						Method method = navi.getClass().getDeclaredMethod(prop);
						navi = method.invoke(navi);

					}
					tempContentMap.put(header, new TableDynamicContent(String.valueOf(navi), 0));
				} catch (Exception e) {

				}
			}
			pivotTableContent.add(new PivotTableModel<VolatilityHisId, TableDynamicColumn, TableDynamicContent>(volHis
					.getId(), tempContentMap));
		}
		// log.info("In the Vcv Matrix:#0,#1", pivotTableContent.size());
	}

	// ***************************************************************
	public void resetTable() {
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("tabViewVcv:formVcvHis:tableVcvHis");
		if (dataTable != null) {
			// log.info("In the dataTable");
			dataTable.setValueExpression("sortBy", null);
			// dataTable.setValueExpression("filterBy", null);
			dataTable.setFirst(0);
			dataTable.reset();
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

	public List<VolatilityHis> getVolatilityHisList() {
		return volatilityHisList;
	}

	public void setVolatilityHisList(List<VolatilityHis> volatilityHisList) {
		this.volatilityHisList = volatilityHisList;
	}

	public List<TableDynamicColumn> getPivotTableHeader() {
		return pivotTableHeader;
	}

	public void setPivotTableHeader(List<TableDynamicColumn> pivotTableHeader) {
		this.pivotTableHeader = pivotTableHeader;
	}

	public List<PivotTableModel<VolatilityHisId, TableDynamicColumn, TableDynamicContent>> getPivotTableContent() {
		return pivotTableContent;
	}

	public void setPivotTableContent(
			List<PivotTableModel<VolatilityHisId, TableDynamicColumn, TableDynamicContent>> pivotTableContent) {
		this.pivotTableContent = pivotTableContent;
	}

	public List<PivotTableModel<VolatilityHisId, TableDynamicColumn, TableDynamicContent>> getFilterPivotTableContent() {
		return filterPivotTableContent;
	}

	public void setFilterPivotTableContent(
			List<PivotTableModel<VolatilityHisId, TableDynamicColumn, TableDynamicContent>> filterPivotTableContent) {
		this.filterPivotTableContent = filterPivotTableContent;
	}*/

	// ****************Getter and Setter***********************

}
