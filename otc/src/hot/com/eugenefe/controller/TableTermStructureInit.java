package com.eugenefe.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.metamodel.source.BindingContext;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.primefaces.component.api.UIOutcomeTarget;
import org.primefaces.component.datatable.DataTable;

import com.eugenefe.entity.IntRate;
import com.eugenefe.entity.IrCurve;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.util.PivotTableModel;

@Name("tableTermStructureInit")
@Scope(ScopeType.CONVERSATION)
public class TableTermStructureInit {
	@Logger
	private Log log;

	// @In
	// private EntityManager entityManager;
	@In
	private Session session;

	@In(create = true)
	private BaseDateBean basedateBean;

	private List<IrCurve> irCurveList = new ArrayList<IrCurve>();

	private List<EMaturity> pivotTableHeader;
	private List<PivotTableModel<IrCurve, EMaturity, IntRate>> pivotTableContent;
	private List<PivotTableModel<IrCurve, EMaturity, IntRate>> filterPivotTableContent;

	public TableTermStructureInit() {
		System.out.println("Construction TableTermStructureInit");
	}

	// *******************************************
	@Create
	public void create() {
		loadIrCurve();
		loadPivotTable();
		dataTable =(DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent("tabViewIrCurve:formTermStructure:tableTermStructure");
	}

	// @Observer(value ={
	// "changeBssd_/view/v140IrCurve.xhtml","changeBssd_/view/v800Setting.xhtml"})
	// @Observer(value = "changeBssd_/view/v140IrCurve.xhtml")
	// public void onBssdChange(String basedate) {
	// bssd = basedate;
	// log.info("In BaseDateSession Change:#0", basedate);
	// session.clear();
	// loadIrCurve();
	// loadPivotTable();
	// Events.instance().raiseEvent("evtReloadTermStructure", bssd,
	// pivotTableContent);
	// }

//	@Begin
//	public void begin(){
//		
//	}
	public void loadIrCurve() {
		log.info("Ir Curve con:#{basedateBean.stBssd}, #{basedateBean.endBssd}");
		Filter filter = session.enableFilter("filterBtwnDate")
				.setParameter("stBssd", basedateBean.getStBssd())
				.setParameter("endBssd", basedateBean.getEndBssd());

		irCurveList = session.createQuery("from IrCurve").list();
	}

	public void loadPivotTable() {
		pivotTableContent = new ArrayList<PivotTableModel<IrCurve, EMaturity, IntRate>>();

		// use Set to remove Maturity duplication and convert into List
		Set<EMaturity> tempHeaderSet = new HashSet<EMaturity>();

		// log.info("Load Pivot Table : #0, #1,#2");
		for (IrCurve irCurve : irCurveList) {
			tempHeaderSet.addAll(irCurve.getIrcBucketMap().keySet());
			pivotTableContent.add(new PivotTableModel<IrCurve, EMaturity, IntRate>(irCurve, irCurve.getIrcBucketMap()));
		}

		filterPivotTableContent = pivotTableContent;

		pivotTableHeader = new ArrayList<EMaturity>(tempHeaderSet);
		Collections.sort(pivotTableHeader);

		// Collections.sort(pivotTableHeader, new ComparatorEMaturity());
	}

	// TODO : remove constant string
	// ***************************************************************
	private void resetTable() {
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("formTermStructure:tableTermStructure");
		if (dataTable != null) {
			// log.info("In the dataTable");
			dataTable.setValueExpression("sortBy", null);
			// dataTable.setValueExpression("filterBy", null);
			dataTable.setFirst(0);
			dataTable.reset();
			
		}
	}


	// ****************Getter and Setter****************************
	public List<IrCurve> getIrCurveList() {
		return irCurveList;
	}

	public void setIrCurveList(List<IrCurve> irCurveList) {
		this.irCurveList = irCurveList;
	}

	public List<EMaturity> getPivotTableHeader() {
		return pivotTableHeader;
	}

	public void setPivotTableHeader(List<EMaturity> pivotTableHeader) {
		this.pivotTableHeader = pivotTableHeader;
	}

	public List<PivotTableModel<IrCurve, EMaturity, IntRate>> getPivotTableContent() {
		return pivotTableContent;
	}

	public void setPivotTableContent(List<PivotTableModel<IrCurve, EMaturity, IntRate>> pivotTableContent) {
		this.pivotTableContent = pivotTableContent;
	}

	public List<PivotTableModel<IrCurve, EMaturity, IntRate>> getFilterPivotTableContent() {
		log.info("Filter1:#0,#1,#2", dataTable.getId(),dataTable.getFilters().size());
//		log.info("Filter1:#0,#1,#2", dataTable.getId(), dataTable.getFilters().size(),this.filterPivotTableContent.size());
		for(String aa: dataTable.getFilters().keySet()){
//			log.info("Filter:#0,#1", aa, dataTable.getFilters().get(aa));
		}
		return filterPivotTableContent;
	}

	public void setFilterPivotTableContent(List<PivotTableModel<IrCurve, EMaturity, IntRate>> filterPivotTableContent) {
		this.filterPivotTableContent = filterPivotTableContent;
	}
	
	
	private DataTable dataTable;

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
	
	
}
