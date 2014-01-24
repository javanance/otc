package com.eugenefe.controller;

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

import com.eugenefe.converter.LazyModelMarketVariable;
import com.eugenefe.converter.LazyModelVcvHis;
import com.eugenefe.entity.IntRate;
import com.eugenefe.entity.IrCurve;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.VcvMatrix;
import com.eugenefe.entity.VcvMatrixHis;
import com.eugenefe.entity.Volatility;
import com.eugenefe.entity.VolatilityHis;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.util.ColumnModel;
import com.eugenefe.util.CrossTableModelOld;
import com.eugenefe.util.FlagBean;
import com.eugenefe.util.MarketVariableType;
import com.eugenefe.util.NamedQuery;
import com.eugenefe.util.PivotTableModel;

@Name("tableRfVcvMatrixInit")
@Scope(ScopeType.CONVERSATION)
public class TableRfVcvMatrixInit {
	@Logger
	private Log log;

	@In
	private Session session;

	@In(value="#{basedateBean}")
	private BaseDateBean basedateBean;

	@In(value = "#{flagBean.flagVcvRfType}")
	private String flagVcvRfType;

	@In(value="#{flagBean.vcvMethod}")
	private String selectedVolId ;
	private Volatility selectedVol;

	private List<MarketVariable> riskFactorList;
	private List<Volatility> volatilityList;

	private List<MarketVariable> pivotTableHeader;
	private List<PivotTableModel<MarketVariable, MarketVariable, VolatilityHis>> pivotTableContent;
	private List<PivotTableModel<MarketVariable, MarketVariable, VolatilityHis>> filterPivotTableContent;

	private List<VolatilityHis> volatilityHisList;
	private LazyDataModel<VolatilityHis> lazyModelVolHis;

	public TableRfVcvMatrixInit() {
		System.out.println("Construction TableRfVcvMatrixInit");
	}

	// *******************************************
	// @Observer("changeBssd_/view/v130RfVcvMatrix.xhtml")
	@Create
	public void create() {
		// vcvId ="SMA_1DAY";
		initPivotTableHeader();

		loadVolatility();
		loadVcvMatrix();
	}
	@Observer("evtBaseDateChange_/view/v130RfVcvMatrix.xhtml")
	public void onBaseDateChange(){
		log.info("VCV event Observer:#0");

//		TODO : Check Performance tradeOff with filtering VolatilityHisList 
//		session.clear();
//		initPivotTableHeader();
//		loadVolatility();
		loadVcvMatrix();
		
	}
	@Observer("evtPickRiskFactors")
	public void onPickRiskFactors(List<MarketVariable> selRf) {
		pivotTableHeader = selRf;
		loadVcvMatrix();
	}
	
//******************************************	
	public void initPivotTableHeader() {
		pivotTableHeader = new ArrayList<MarketVariable>();
		riskFactorList = session.createQuery("from MarketVariable a where a.riskFactorYN ='Y'").list();
		
		for (MarketVariable bb : riskFactorList) {
			if (bb.getMvType().getRfType().equals(flagVcvRfType)) {
				pivotTableHeader.add(bb);
			}
		}
		log.info("initPivotTableHeader Prep:#0", pivotTableHeader.size());
	}

	public void loadVolatility() {
		log.info("VCV:#{basedateBean.bssd}");
		Filter filter = session.enableFilter("filterBtwnDate")
				.setParameter("stBssd", basedateBean.getStBssd())
				.setParameter("endBssd", basedateBean.getEndBssd());
//		Filter filterBssd = session.enableFilter("filterCurrentDate").setParameter("bssd", basedateBean.getBssd());

		volatilityList = session.createQuery("from Volatility").list();
	}
	
	
	public void loadVcvMatrix() {
		pivotTableContent = new ArrayList<PivotTableModel<MarketVariable,MarketVariable,VolatilityHis>>();
		Collections.sort(pivotTableHeader);
		for (Volatility vol : volatilityList) {
			if (vol.getVolId().equals(selectedVolId)) {
				selectedVol = vol;
			}
		}
		for (MarketVariable rf : pivotTableHeader) {
			Map<MarketVariable, VolatilityHis> tempContentMap = new HashMap<MarketVariable, VolatilityHis>();
			for (VolatilityHis volHis : selectedVol.getVolatilityHisList()) {
				if (volHis.getId().getBssd().equals(basedateBean.getBssd())
						&& volHis.getRiskFactor().equals(rf)) {
					tempContentMap.put(volHis.getRefRiskFactor(), volHis);
				}
			}
			pivotTableContent.add(new PivotTableModel<MarketVariable,MarketVariable,VolatilityHis>(rf,tempContentMap));
		}
		log.info("In the Vcv Matrix:#0,#1", selectedVol.getVolId(),pivotTableContent.size());
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
	
// ****************Getter and Setter***********************
	public Volatility getSelectedVol() {
		return selectedVol;
	}

	public void setSelectedVol(Volatility selectedVol) {
		this.selectedVol = selectedVol;
	}

	public String getSelectedVolId() {
		return selectedVolId;
	}

	public void setSelectedVolId(String selectedVolId) {
		this.selectedVolId = selectedVolId;
	}

	public List<MarketVariable> getPivotTableHeader() {
		return pivotTableHeader;
	}

	public void setPivotTableHeader(List<MarketVariable> pivotTableHeader) {
		this.pivotTableHeader = pivotTableHeader;
	}

	public List<PivotTableModel<MarketVariable, MarketVariable, VolatilityHis>> getPivotTableContent() {
		return pivotTableContent;
	}

	public void setPivotTableContent(List<PivotTableModel<MarketVariable, MarketVariable, VolatilityHis>> pivotTableContent) {
		this.pivotTableContent = pivotTableContent;
	}

	public List<PivotTableModel<MarketVariable, MarketVariable, VolatilityHis>> getFilterPivotTableContent() {
		return filterPivotTableContent;
	}

	public void setFilterPivotTableContent(
			List<PivotTableModel<MarketVariable, MarketVariable, VolatilityHis>> filterPivotTableContent) {
		this.filterPivotTableContent = filterPivotTableContent;
	}

	public List<Volatility> getVolatilityList() {
		return volatilityList;
	}

	public void setVolatilityList(List<Volatility> volatilityList) {
		this.volatilityList = volatilityList;
	}

	public List<MarketVariable> getRiskFactorList() {
		return riskFactorList;
	}

	public void setRiskFactorList(List<MarketVariable> riskFactorList) {
		this.riskFactorList = riskFactorList;
	}

	public List<VolatilityHis> getVolatilityHisList() {
		return volatilityHisList;
	}

	public void setVolatilityHisList(List<VolatilityHis> volatilityHisList) {
		this.volatilityHisList = volatilityHisList;
	}


	

}
