package com.eugenefe.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

import com.eugenefe.entity.HifiveStrike;
import com.eugenefe.entity.IntRate;
import com.eugenefe.entity.IrCurve;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.util.PivotTableModel;

@Name("tableHifiveStrikePivotInit")
@Scope(ScopeType.CONVERSATION)
public class TableHifiveStrikePivotInit {
	@Logger
	private Log log;

	// @In
	// private EntityManager entityManager;
	@In
	private Session session;
	@In
	private Map<String, String> messages;


	private List<HifiveStrike> strikeList = new ArrayList<HifiveStrike>();

	private List<String> pivotTableHeader;
	
	private List<PivotTableModel<String, String, HifiveStrike>> pivotTableContent;
	private List<PivotTableModel<String, String, HifiveStrike>> filterPivotTableContent;

	public TableHifiveStrikePivotInit() {
		System.out.println("Construction TableHifiveStrikePivotInit");
	}

	// *******************************************
	@Create
	public void create() {
		loadHifivestrike();
		loadPivotTable();
//		dataTable =(DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent("tabViewIrCurve:formTermStructure:tableTermStructure");
	}

	public void loadHifivestrike() {
		log.info("Ir Curve con:#{basedateBean.stBssd}, #{basedateBean.endBssd}");
//		Filter filter = session.enableFilter("filterBtwnDate")
//				.setParameter("stBssd", basedateBean.getStBssd())
//				.setParameter("endBssd", basedateBean.getEndBssd());

		strikeList = session.createQuery("from HifiveUnderlying").list();
	}

	public void loadPivotTable() {
		pivotTableContent = new ArrayList<PivotTableModel<String, String, HifiveStrike>>();

		// use Set to remove Maturity duplication and convert into List
		Set<String> tempHeaderSet = new HashSet<String>();

		// log.info("Load Pivot Table : #0, #1,#2");
		Map<String, HifiveStrike> tempContent = new HashMap<String, HifiveStrike>();
//		Map<String, String> tempContent = new HashMap<String, String>();
		String temp=null ;
		for (HifiveStrike strike : strikeList) {
			temp =strike.getId().getProdId();
			tempHeaderSet.add(strike.getStrikeDate());
			tempContent.put(strike.getStrikeDate(), strike);
		}
		pivotTableContent.add(new PivotTableModel<String, String, HifiveStrike>(temp,tempContent));


		filterPivotTableContent = pivotTableContent;

		pivotTableHeader = new ArrayList<String>(tempHeaderSet);
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
	
	
	
}
