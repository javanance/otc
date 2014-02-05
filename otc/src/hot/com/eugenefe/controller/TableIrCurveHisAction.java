package com.eugenefe.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Log;
import org.primefaces.component.behavior.ajax.AjaxBehavior;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.FilterEvent;
import org.primefaces.model.LazyDataModel;

import com.eugenefe.converter.LazyModelMarketVariable;
import com.eugenefe.entity.Basedate;
import com.eugenefe.entity.IMarketVariableHis;
import com.eugenefe.entity.IntRate;
import com.eugenefe.entity.IntRateHis;
import com.eugenefe.entity.IrCurve;
import com.eugenefe.entity.IrcBucket;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.VcvMatrixHis;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.util.ColumnModel;
import com.eugenefe.util.CrossTableModelOld;
import com.eugenefe.util.PivotTableModel;
import com.eugenefe.util.MarketVariableType;
import com.eugenefe.util.NamedQuery;

@Name("tableIrCurveHisAction")
@Scope(ScopeType.CONVERSATION)
public class TableIrCurveHisAction {

	@Logger
	private Log log;
	@In
	private EntityManager entityManager;

	// private List<MarketVariable> marketVariables;

	@In(required = false)
	@Out(required = false)
	// @In
	// @Out
	private IrCurve selectedIrCurve;
	private List<IntRateHis> intRateHis = new ArrayList<IntRateHis>();
	// private List<IntRateHis> filteredIntRateHis = new
	// ArrayList<IntRateHis>();
	@Out(required = false)
	private List<IntRateHis> filteredIntRateHis;

	private List<ColumnModel> ircTsHeader;
	private List<CrossTableModelOld> pivotTable;
	private List<CrossTableModelOld> pivotTableByCurve;

	@Out(required = false)
	private List<PivotTableModel<IrCurve,EMaturity,IntRate>> selectedTableModel;
	
//	private List<CrossTableModel> selectedTableModel;

	public List<PivotTableModel<IrCurve, EMaturity,IntRate>> getSelectedTableModel() {
		return selectedTableModel;
	}

	public void setSelectedTableModel(List<PivotTableModel<IrCurve, EMaturity,IntRate>> selectedTableModel) {
		this.selectedTableModel = selectedTableModel;
	}


	public List<IntRateHis> getIntRateHis() {
		return intRateHis;
	}

	
	public void setIntRateHis(List<IntRateHis> intRateHis) {
		this.intRateHis = intRateHis;
	}

	public List<IntRateHis> getFilteredIntRateHis() {
		return filteredIntRateHis;
	}

	public void setFilteredIntRateHis(List<IntRateHis> filteredIntRateHis) {
		this.filteredIntRateHis = filteredIntRateHis;
	}

	public List<ColumnModel> getIrcTsHeader() {
		return ircTsHeader;
	}

	public void setIrcTsHeader(List<ColumnModel> ircTsHeader) {
		this.ircTsHeader = ircTsHeader;
	}

	public List<CrossTableModelOld> getPivotTable() {
		return pivotTable;
	}

	public void setPivotTable(List<CrossTableModelOld> pivotTable) {
		this.pivotTable = pivotTable;
	}

	public List<CrossTableModelOld> getPivotTableByCurve() {
		return pivotTableByCurve;
	}

	public void setPivotTableByCurve(List<CrossTableModelOld> pivotTableByCurve) {
		this.pivotTableByCurve = pivotTableByCurve;
	}

//	public List<CrossTableModel> getSelectedTableModel() {
//		return selectedTableModel;
//	}
//
//	public void setSelectedTableModel(List<CrossTableModel> selectedTableModel) {
//		this.selectedTableModel = selectedTableModel;
//	}

	// ***************************************************************
	public TableIrCurveHisAction() {
		System.out.println("Construction TableIrCurveHisAction");
	}

	// @Create
	// public void create(){
	//
	//
	// }

	@Observer("evtReloadIrCurve")
	public void onIrcSelection() {
		resetTable();
		ircTsHeader = new ArrayList<ColumnModel>();
		pivotTable = new ArrayList<CrossTableModelOld>();
		intRateHis = new ArrayList<IntRateHis>();
		filteredIntRateHis = new ArrayList<IntRateHis>();

		// log.info("bucket1:#0,#1", selectedIrCurve.getIrcBucketList().size(),intRateHis.size());
		List<Basedate> baseDates = new ArrayList<Basedate>();
		List<EMaturity> maturityList = new ArrayList<EMaturity>();

		if(selectedIrCurve==null){
			selectedIrCurve = new IrCurve();
		}
		/*for (IrcBucket aa : selectedIrCurve.getIrcBucketMap().) {
			// if( !maturityList.contains(aa.getMaturityId())){
			maturityList.add(aa.getMaturityId());
			// }
			// ircTsHeader.add(new ColumnModel(aa.getMaturityId().name(),
			// aa.getMaturityId().name()));

			intRateHis.addAll(aa.getIntRate().getIntRateHisList());
			filteredIntRateHis.addAll(aa.getIntRate().getIntRateHisList());
			for (IntRateHis bb : aa.getIntRate().getIntRateHisList()) {
				if (!baseDates.contains(bb.getBasedate())) {
					baseDates.add(bb.getBasedate());
				}
			}
		}*/

		/*for (Basedate zz : baseDates) {
			Map<String, BigDecimal> tempMap = new HashMap<String, BigDecimal>();
			for (IrcBucket aa : selectedIrCurve.getIrcBucketList()) {
				for (IntRateHis bb : aa.getIntRate().getIntRateHisList()) {
					if (zz.equals(bb.getBasedate())) {
						tempMap.put(aa.getMaturityId().name(), bb.getIntRate());
					}
				}
			}
			pivotTable.add(new CrossTableModelOld(zz.getBssd(), tempMap));
		}*/

		Collections.sort(maturityList);
		for (EMaturity aa : maturityList) {
			ircTsHeader.add(new ColumnModel(aa.name(), aa.name()));
		}
		Events.instance().raiseEvent("evtReloadIntRateHis", filteredIntRateHis);
	}

	// ***************************************
	private void resetTable() {
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("tabViewIrCurve:formIrcBucketHis:tableIrcBucketHis");
		if (dataTable != null) {
			dataTable.setValueExpression("sortBy", null);
			// dataTable.setValueExpression("filterBy", null);
			dataTable.setFirst(0);
			dataTable.reset();
		}
	}

	public Map<String, String> onFilter(FilterEvent event) {
		DataTable table = (DataTable) event.getSource();
		log.info("In the Filter:#0,#1,#2", table.getFilters().size(), table.getFilteredValue().size(),
				filteredIntRateHis.size());
		// List<Screenshot> obj = table.getFilteredData();

		// Do your stuff here

		Map<String, String> filters = table.getFilters();
		return filters;
	}
	
	public void onDblClikSelect(SelectEvent event){
		log.info("IN The Double Clik:#0", ((CrossTableModelOld)event.getObject()).getLabel());
//		return "/view/v145IrCurveHis.xhtml";

		try {
//			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedTableModel", (CrossTableModel)event.getObject());
//			FacesContext.getCurrentInstance().getExternalContext().redirect("/himemysqltest/view/v145IrCurveHis.seam?faces-redirect=true");
			FacesContext.getCurrentInstance().getExternalContext().redirect("/himemysqltest/view/v145IrCurveHis.seam");
		} catch (Exception e) {
			// TODO: handle exception
		}
//		/himemysqltest/view/v145IrCurveHis.seam
	}

}
