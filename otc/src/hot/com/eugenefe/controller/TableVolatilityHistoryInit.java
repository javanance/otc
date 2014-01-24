package com.eugenefe.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Remove;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
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
import com.eugenefe.converter.LazyModelVolatilityHis;
import com.eugenefe.entity.IntRate;
import com.eugenefe.entity.IrCurve;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.VcvMatrix;
import com.eugenefe.entity.VcvMatrixHis;
import com.eugenefe.entity.Volatility;
import com.eugenefe.entity.VolatilityHis;
import com.eugenefe.entity.VolatilityHisId;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.util.ColumnModel;
import com.eugenefe.util.CrossTableModelOld;
import com.eugenefe.util.FlagBean;
import com.eugenefe.util.MarketVariableType;
import com.eugenefe.util.NamedQuery;
import com.eugenefe.util.PivotTableModel;

@Name("tableVolatilityHistoryInit")
@Scope(ScopeType.CONVERSATION)
public class TableVolatilityHistoryInit {
	@Logger
	private Log log;

	@In
	private Session session;

	@In(value = "#{basedateBean}")
	private BaseDateBean basedateBean;

	@In(value = "#{flagBean.vcvMethod}")
	private String selectedVolId;
	private Volatility selectedVol;

	private List<VolatilityHis> volatilityHisList;
	private LazyDataModel<VolatilityHis> lazyModelVolHis;
	
	private VolatilityHis selLazyModelVolHis;
	public VolatilityHis getSelLazyModelVolHis() {
		return selLazyModelVolHis;
	}
	public void setSelLazyModelVolHis(VolatilityHis selLazyModelVolHis) {
		this.selLazyModelVolHis = selLazyModelVolHis;
	}

	// private List<VolatilityHis> filteredLazyModelVolHis;

	

	public TableVolatilityHistoryInit() {
		System.out.println("Construction TableVolatilityHistoryInit");
	}

	// *******************************************
	// @Observer("changeBssd_/view/v130RfVcvMatrix.xhtml")
	@Create
	public void create() {
		loadVolatilityHistory();

	}
//	@Destroy 
//	@Remove
	public void destroy(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Time Out", "AAA"));
		log.info("Vol His Destroy");
	}

	@Observer("evtBaseDateChange_/view/v130RfVcvMatrix.xhtml")
	public void onBaseDateChange() {
		log.info("VCV event Observer:#0");

	}

	@Observer("evtPickRiskFactors")
	public void onPickRiskFactors(List<MarketVariable> selRf) {
	}

	// ******************************************

	public void loadVolatilityHistory() {
		log.info("VCV:#{basedateBean.bssd}");
		Filter filter = session.enableFilter("filterBtwnDate").setParameter("stBssd", basedateBean.getStBssd())
				.setParameter("endBssd", basedateBean.getEndBssd());
		// Filter filterBssd =
		// session.enableFilter("filterCurrentDate").setParameter("bssd",
		// basedateBean.getBssd());

		// volatilityHisList =
		// session.createQuery("from VolatilityHis a where a.id.vcvId = :volId")
		// .setParameter("volId", selectedVolId)
		volatilityHisList = session.createQuery("from VolatilityHis ").list();
		lazyModelVolHis = new LazyModelVolatilityHis(volatilityHisList);

//		log.info("In Vcv:#0,#1");
	}

	// ***************************************************************
	public void resetTable() {
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("tabViewVcv:formLazyVolHis:tableLazyVolHis");
		if (dataTable != null) {
			// log.info("In the dataTable");
			dataTable.setValueExpression("sortBy", null);
			// dataTable.setValueExpression("filterBy", null);
			dataTable.setFirst(0);
			dataTable.reset();
		}
	}

	public void resetLazyTable() {
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("tabViewVcv:formLazyVolHis:tableLazyVolHis");
		if (dataTable != null) {
			// log.info("In the dataTable");
			dataTable.setValueExpression("sortBy", null);
			// dataTable.setValueExpression("filterBy", null);
			dataTable.setFirst(0);
			dataTable.reset();
		}
	}
	
	public void dataTableView(){
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
//				.findComponent("tabViewVcv:formLazyVolHis:tableLazyVolHis");
//		.findComponent("tabViewVcv:formVolHis:tableVolHis");
		.findComponent("tabViewVcv:formDynVolHis:tableDynVolHis");
		
		ELContext context = FacesContext.getCurrentInstance().getELContext();
//		dataTable.getRowData("20130605")
//		log.info("Row Data:#0,#1",((VolatilityHis)(dataTable.getRowData())).getId().volId, dataTable.getRowIndex());
//		log.info("Row Data:#0,#1",((VolatilityHis)(dataTable.getRowData())).getId().volId);
//		log.info("Row Data:#0,#1",dataTable.getRowIndex(), dataTable.getRowIndexVar());

		
//		for(VolatilityHis bb : ((LazyModelVolatilityHis)dataTable.getValue()).getDatasource()){
//			log.info("Row Data:#0,#1",bb.getRiskFactor().getMvId());
//		}
		
		for(org.primefaces.component.api.UIColumn aa : dataTable.getColumns()){
			for(UIComponent bb :aa.getChildren()){
				log.info("Column Name : #0,#1,#2",aa.getFacet("header").getValueExpression("value").getValue(context)
									, bb.getValueExpression("value").getExpressionString());
			}
		}
//		for(org.primefaces.component.api.UIColumn aa : dataTable.getColumns()){
//			log.info("Column1 Name : #0,#1,#2", aa.getClientId(),aa.getHeaderText(), aa.getFacet("header").getValueExpression("value").getValue(context));
////			log.info("Column Name : #0,#1,#2", aa.getClientId(),aa.getHeaderText(), aa.getFacet("header").getChildren().get(0).getAttributes().get("value"));
//			for(UIComponent bb :aa.getChildren()){
////				log.info("Column Name : #0,#1,#2",bb.getClientId(), bb.getValueBinding("value").getExpressionString());
//				log.info("Column Name : #0,#1,#2",bb.getClientId(), bb.getValueExpression("value").getExpressionString());
////				bb.
//			}
//		}
		
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

	public List<VolatilityHis> getVolatilityHisList() {
		return volatilityHisList;
	}

	public void setVolatilityHisList(List<VolatilityHis> volatilityHisList) {
		this.volatilityHisList = volatilityHisList;
	}

	public LazyDataModel<VolatilityHis> getLazyModelVolHis() {
//		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
//				.findComponent("tabViewVcv:formLazyVolHis:tableLazyVolHis");
//		 log.info("Filter1:#0,#1,#2", dataTable.getId(),dataTable.getMultiSortMeta(), dataTable.isLazy());
		return lazyModelVolHis;
	}

	public void setLazyModelVolHis(LazyDataModel<VolatilityHis> lazyModelVolHis) {
		this.lazyModelVolHis = lazyModelVolHis;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	

}
