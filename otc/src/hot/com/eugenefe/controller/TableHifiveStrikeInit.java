package com.eugenefe.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.primefaces.component.message.Message;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;

import com.eugenefe.converter.LazyModelMarketVariable;
import com.eugenefe.converter.LazyModelVcvHis;
import com.eugenefe.entity.Hifive;
import com.eugenefe.entity.HifiveStrike;
import com.eugenefe.entity.HifiveUnderlying;
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

@Name("tableHifiveStrikeInit")
@Scope(ScopeType.CONVERSATION)
public class TableHifiveStrikeInit {
	@Logger
	private Log log;

	@In
	private Session session;
	@In
	private Map<String, String> messages;
	
	@In(value="#{tableHifiveInit.selectHifive}")
	private Hifive selectHifive;

	private List<HifiveStrike> strikeList;
	public List<HifiveStrike> getStrikeList() {
		return strikeList;
	}
	public void setStrikeList(List<HifiveStrike> strikeList) {
		this.strikeList = strikeList;
	}

	public TableHifiveStrikeInit() {
		System.out.println("Construction TableHifiveStrikeInit");
	}
	
	private List<String> pivotTableHeader;
	
//	private List<PivotTableModel<String, String, HifiveStrike>> pivotTableContent;
//	private List<PivotTableModel<String, String, HifiveStrike>> filterPivotTableContent;
	private List<PivotTableModel<String, String, String>> pivotTableContent;
	private List<PivotTableModel<String, String, String>> filterPivotTableContent;
	
	public List<String> getPivotTableHeader() {
		return pivotTableHeader;
	}
	public void setPivotTableHeader(List<String> pivotTableHeader) {
		this.pivotTableHeader = pivotTableHeader;
	}
//	public List<PivotTableModel<String, String, HifiveStrike>> getPivotTableContent() {
//		return pivotTableContent;
//	}
//	public void setPivotTableContent(List<PivotTableModel<String, String, HifiveStrike>> pivotTableContent) {
//		this.pivotTableContent = pivotTableContent;
//	}
//	public List<PivotTableModel<String, String, HifiveStrike>> getFilterPivotTableContent() {
//		return filterPivotTableContent;
//	}
//	public void setFilterPivotTableContent(List<PivotTableModel<String, String, HifiveStrike>> filterPivotTableContent) {
//		this.filterPivotTableContent = filterPivotTableContent;
//	}
	
	// *******************************************
	@Create
	public void create() {
		log.info("Hifive Strike Creation:#0");
		loadHifiveUnderlying();
		loadPivotTable1();
	}

	
	public List<PivotTableModel<String, String, String>> getPivotTableContent() {
		return pivotTableContent;
	}
	public void setPivotTableContent(List<PivotTableModel<String, String, String>> pivotTableContent) {
		this.pivotTableContent = pivotTableContent;
	}
	public List<PivotTableModel<String, String, String>> getFilterPivotTableContent() {
		return filterPivotTableContent;
	}
	public void setFilterPivotTableContent(List<PivotTableModel<String, String, String>> filterPivotTableContent) {
		this.filterPivotTableContent = filterPivotTableContent;
	}
	public void loadHifiveUnderlying() {
//		log.info("HiFive:#{basedateBean.bssd}");
//		Filter filter = session.enableFilter("filterBtwnDate")
//				.setParameter("stBssd", basedateBean.getStBssd())
//				.setParameter("endBssd", basedateBean.getEndBssd());
//		Filter filterBssd = session.enableFilter("filterCurrentDate").setParameter("bssd", basedateBean.getBssd());

		strikeList = session.createQuery("from HifiveStrike").list();
	}
	

	
	/*public void loadPivotTable() {
		log.info("Hifive Strike :#0,#1", selectHifive.getProdId(),selectHifive.getHifiveStrikes().size());
		pivotTableContent = new ArrayList<PivotTableModel<String, String, HifiveStrike>>();

		// use Set to remove Maturity duplication and convert into List
		Set<String> tempHeaderSet = new HashSet<String>();

		// log.info("Load Pivot Table : #0, #1,#2");
		Map<String, HifiveStrike> tempContent = new HashMap<String, HifiveStrike>();
//		Map<String, String> tempContent = new HashMap<String, String>();
//		String temp=null ;
		
		for (HifiveStrike strike : selectHifive.getHifiveStrikes()) {
			log.info("Strike : #0,#1", strike.getId().getProdId(), strike.getId().getStrikeSerial());
//			temp =strike.getId().getProdId();
			tempHeaderSet.add(strike.getStrikeDate());
			tempContent.put(strike.getStrikeDate(), strike);
		}
		log.info("Strike : #0,#1", tempContent.size());
		if( tempContent.size()>1){
			pivotTableContent.add(new PivotTableModel<String, String, HifiveStrike>(selectHifive.getProdId(),tempContent));
		}

		filterPivotTableContent = pivotTableContent;

		pivotTableHeader = new ArrayList<String>(tempHeaderSet);
		Collections.sort(pivotTableHeader);
		
		log.info("pivotTable : #0,#1", pivotTableHeader.size(),pivotTableContent.size());
		// Collections.sort(pivotTableHeader, new ComparatorEMaturity());
	}*/
	
	public void loadPivotTable1() {
		log.info("Hifive Strike :#0,#1", selectHifive.getProdId(),selectHifive.getHifiveStrikes().size());
		pivotTableContent = new ArrayList<PivotTableModel<String, String, String>>();

		// use Set to remove Maturity duplication and convert into List
		Set<String> tempHeaderSet = new HashSet<String>();

		// log.info("Load Pivot Table : #0, #1,#2");
		Map<String, String> tempContent0 = new HashMap<String, String>();
		Map<String, String> tempContent1 = new HashMap<String, String>();
		Map<String, String> tempContent2 = new HashMap<String, String>();
		Map<String, String> tempContent3 = new HashMap<String, String>();
		Map<String, String> tempContent4 = new HashMap<String, String>();
		Map<String, String> tempContent5 = new HashMap<String, String>();
		Map<String, String> tempContent6 = new HashMap<String, String>();
		Map<String, String> tempContent7 = new HashMap<String, String>();
		Map<String, String> tempContent8 = new HashMap<String, String>();
		Map<String, String> tempContent9 = new HashMap<String, String>();
//		Map<String, String> tempContent = new HashMap<String, String>();
		String temp=null ;
		
		
		for (HifiveStrike strike : selectHifive.getHifiveStrikes()) {
			log.info("Strike : #0,#1", strike.getId().getProdId(), strike.getId().getStrikeSerial());
			temp =messages.get("strikeSerial") + strike.getId().getStrikeSerial();
			tempHeaderSet.add(temp);
			
			tempContent0.put(temp, strike.getStrikeDate());
			tempContent1.put(temp, strike.getPaymentDate());
			tempContent2.put(temp, strike.getStrikePrice().toString());
			tempContent3.put(temp, strike.getPayoffAmt().toString());
			tempContent4.put(temp, strike.getDblJumpStrike().toString());
			tempContent5.put(temp, strike.getDblJumpPayoff().toString());
			tempContent6.put(temp, strike.getTplJumpStrike().toString());
			tempContent7.put(temp, strike.getTplJumpPayoff().toString());
			tempContent8.put(temp, strike.getCouponRate().toString());
			tempContent9.put(temp, strike.getUpBarrier().toString());
//			tempContent1.put(strike.getStrikeDate(), strike.getPaymentDate());
//			tempContent2.put(strike.getStrikeDate(), strike.getStrikePrice().toString());
//			tempContent3.put(strike.getStrikeDate(), strike.getPayoffAmt().toString());
//			tempContent4.put(strike.getStrikeDate(), strike.getDblJumpStrike().toString());
//			tempContent5.put(strike.getStrikeDate(), strike.getDblJumpPayoff().toString());
//			tempContent6.put(strike.getStrikeDate(), strike.getTplJumpStrike().toString());
//			tempContent7.put(strike.getStrikeDate(), strike.getTplJumpPayoff().toString());
//			tempContent8.put(strike.getStrikeDate(), strike.getCouponRate().toString());
//			tempContent9.put(strike.getStrikeDate(), strike.getUpBarrier().toString());
		}
		log.info("Strike : #0,#1", tempContent0.size());
		if( tempContent0.size()>1){
			pivotTableContent.add(new PivotTableModel<String, String, String>(messages.get("strikeDate"),tempContent0));
			pivotTableContent.add(new PivotTableModel<String, String, String>(messages.get("paymentDate"),tempContent1));
			pivotTableContent.add(new PivotTableModel<String, String, String>(messages.get("strikePrice"),tempContent2));
			pivotTableContent.add(new PivotTableModel<String, String, String>(messages.get("payoffAmt"),tempContent3));
			pivotTableContent.add(new PivotTableModel<String, String, String>(messages.get("dblJumpStrike"),tempContent4));
			pivotTableContent.add(new PivotTableModel<String, String, String>(messages.get("dblJumpPayoff"),tempContent5));
			pivotTableContent.add(new PivotTableModel<String, String, String>(messages.get("tplJumpStrike"),tempContent6));
			pivotTableContent.add(new PivotTableModel<String, String, String>(messages.get("tplJumpPayoff"),tempContent7));
			pivotTableContent.add(new PivotTableModel<String, String, String>(messages.get("couponRate"),tempContent8));
			pivotTableContent.add(new PivotTableModel<String, String, String>(messages.get("upBarrier"),tempContent9));
		}

//		filterPivotTableContent = pivotTableContent;

		pivotTableHeader = new ArrayList<String>(tempHeaderSet);
		Collections.sort(pivotTableHeader);
		
		log.info("pivotTable : #0,#1", pivotTableHeader.size(),pivotTableContent.size());
		// Collections.sort(pivotTableHeader, new ComparatorEMaturity());
	}
}
