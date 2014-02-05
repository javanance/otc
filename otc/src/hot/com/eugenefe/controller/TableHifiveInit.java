package com.eugenefe.controller;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.management.RuntimeErrorException;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Events;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;
import org.mvel2.ast.ForNode;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.FilterEvent;

import com.eugenefe.entity.Counterparty;
import com.eugenefe.entity.Futures;
import com.eugenefe.entity.Hifive;
import com.eugenefe.entity.HifiveStrike;
import com.eugenefe.entity.HifiveStrikeId;
import com.eugenefe.entity.HifiveUnderlying;
import com.eugenefe.entity.HifiveUnderlyingId;
import com.eugenefe.entity.IntRate;
import com.eugenefe.entity.IrcBucket;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.MarketVariableJoin;
import com.eugenefe.entity.MarketVariableNew;
import com.eugenefe.entity.PricingMaster;
import com.eugenefe.entity.PricingMasterId;
import com.eugenefe.entity.PricingUnderlyings;
import com.eugenefe.entity.PricingUnderlyingsId;
import com.eugenefe.entity.ProductGreeks;
import com.eugenefe.entity.ProductGreeksId;
import com.eugenefe.entity.ProductReturn;
import com.eugenefe.entity.ProductReturnId;
import com.eugenefe.entity.Stock;
import com.eugenefe.entity.StockIndex;
import com.eugenefe.entity.component.HisData;
import com.eugenefe.entity.component.PriceData;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.pricer.hifive.IHiFiveMc;
import com.eugenefe.pricevo.KisHifive;
//import org.jboss.seam.framework.Query;
import com.eugenefe.session.CounterpartyList;
import com.eugenefe.session.StockList;
import com.eugenefe.util.FnCalendar;
import com.eugenefe.util.MarketVariableType;
import com.sun.corba.se.impl.orbutil.closure.Future;

@Name("tableHifiveInit")
@Scope(ScopeType.CONVERSATION)
public class TableHifiveInit {
	@Logger
	private Log log;
	@In
	private Session session;
	
	@In("#{basedateBean}")
	private BaseDateBean basedateBean;
	
	public BaseDateBean getBasedateBean() {
		return basedateBean;
	}
	public void setBasedateBean(BaseDateBean basedateBean) {
		this.basedateBean = basedateBean;
	}

	//	@In
//	private FacesMessages facesMessages;
	@In
	private StatusMessages statusMessages;
	@In
	private Map<String, String> messages;

	@In("#{counterpartyList}")
	private CounterpartyList counterpartyList;
//	@In("#{stockList}")
//	@In
//	private StockList stockList;
	
	
//	@In
//	private UnderlyingList underlyingList;

	private List<Stock> underlyingStocks;
	public List<Stock> getUnderlyingStocks() {
		return underlyingStocks;
	}
	public void setUnderlyingStocks(List<Stock> underlyingStocks) {
		this.underlyingStocks = underlyingStocks;
	}

	private List<Hifive> hifiveList;
	private List<Hifive> filterHifiveList;
	private Hifive selectHifive;
	private HifiveStrike selectStrike;
	
	public HifiveStrike getSelectStrike() {
		return selectStrike;
	}
	public void setSelectStrike(HifiveStrike selectStrike) {
		this.selectStrike = selectStrike;
	}
	public HifiveUnderlying getSelectUnder() {
		return selectUnder;
	}
	public void setSelectUnder(HifiveUnderlying selectUnder) {
		this.selectUnder = selectUnder;
	}

	private HifiveUnderlying selectUnder;

	private List<Hifive> selectHifiveList;
	
	private List<String> allCntPartyIdList =new ArrayList<String>();
	private List<String> allUnderlyingIdList =new ArrayList<String>();
	
	public List<Hifive> getHifiveList() {
		return hifiveList;
	}
	public void setHifiveList(List<Hifive> hifiveList) {
		this.hifiveList = hifiveList;
	}

	public List<Hifive> getFilterHifiveList() {
		return filterHifiveList;
	}
	public void setFilterHifiveList(List<Hifive> filterHifiveList) {
		this.filterHifiveList = filterHifiveList;
	}
	
	public Hifive getSelectHifive() {
		return selectHifive;
	}

	public void setSelectHifive(Hifive selectHifive) {
		this.selectHifive = selectHifive;
	}
	
	public List<Hifive> getSelectHifiveList() {
		return selectHifiveList;
	}

	public void setSelectHifiveList(List<Hifive> selectHifiveList) {
		this.selectHifiveList = selectHifiveList;
	}
	public List<String> getAllCntPartyIdList() {
		if(allCntPartyIdList==null || allCntPartyIdList.isEmpty()){
			allCntPartyIdList = new ArrayList<String>();
			for(Counterparty aa : counterpartyList.getResultList()){
				allCntPartyIdList.add(aa.getCounterpartyId());
			}
		}
		return allCntPartyIdList;
	}
	
	public void setAllCntPartyIdList(List<String> allCntPartyIdList) {
		this.allCntPartyIdList = allCntPartyIdList;
	}
	public List<String> getAllUnderlyingIdList() {
		if(allUnderlyingIdList ==null || allUnderlyingIdList.isEmpty()){
			allUnderlyingIdList = new ArrayList<String>();
			for(Stock aa : underlyingStocks){
				allUnderlyingIdList.add(aa.getStockId());
			}
		}
		return allUnderlyingIdList;
	}
	public void setAllUnderlyingIdList(List<String> allUnderlyingIdList) {
		this.allUnderlyingIdList = allUnderlyingIdList;
	}
	
	private Map<MarketVariableType, List<String>> underlyingIdMap = new HashMap<MarketVariableType, List<String>>();
	public Map<MarketVariableType, List<String>> getUnderlyingIdMap() {
		return underlyingIdMap;
	}
	public void setUnderlyingIdMap(Map<MarketVariableType, List<String>> underlyingIdMap) {
		this.underlyingIdMap = underlyingIdMap;
	}
	
	
	private Map<MarketVariableType, List<? extends MarketVariableJoin>> underlyingMap = new HashMap<MarketVariableType, List<? extends MarketVariableJoin>>();
	public Map<MarketVariableType, List<? extends MarketVariableJoin>> getUnderlyingMap() {
		return underlyingMap;
	}
	public void setUnderlyingMap(Map<MarketVariableType, List<? extends MarketVariableJoin>> underlyingMap) {
		this.underlyingMap = underlyingMap;
	}
	
	
	public TableHifiveInit() {
		System.out.println("Construction TableHifiveInit");
	}

// *******************************************
	@Create
//	@Begin(join=true)
	public void create() {
		log.info("HiFive Creation:#0");
		loadHiFive();
	}
	
	public void loadHiFive() {
//		log.info("HiFive:#{basedateBean.bssd}");
//		Filter filter = session.enableFilter("filterBtwnDate")
//				.setParameter("stBssd", basedateBean.getStBssd())
//				.setParameter("endBssd", basedateBean.getEndBssd());
//		Filter filterBssd = session.enableFilter("filterCurrentDate").setParameter("bssd", basedateBean.getBssd());
		session.setFlushMode(FlushMode.MANUAL);
		
		String qr= "from Hifive a order by a.prodId";
		hifiveList = session.createQuery(qr).list();
		
		
//		Criteria crit = session.createCriteria(Hifive.class);
//		hifiveList = crit.list();
		
//		hifiveList = new ArrayList<Hifive>();
//		hifiveList.add((Hifive)session.get(Hifive.class, "HIFIVE_01"));
				
		
		String qr2= "from MarketVariableNew a where a.underlyingYN = 'Y' order by a.mvType, a.mvId";
		underlyingList = session.createQuery(qr2).list();
		
		for(MarketVariableNew aa : underlyingList){
			underlyingIdList.add(aa.getMvId());
		}
		
//		StockIndex rst = session.createCriteria(StockIndex.class, "SPX").uniqueResult();
//		StockIndex rst = (StockIndex)session.get(StockIndex.class, "SPX");
//		log.info("priceMap00000 : #0, #1",rst, rst.getPriceMap().size());
//		for( Map.Entry<String, PriceData> entry : rst.getPriceMap().entrySet()){
//			log.info("priceMap00 : #0, #1", entry.getKey(), entry.getValue());
//		}
//		IntRate rst = (IntRate)session.get(IntRate.class, "1010000_M03");
//		log.info("IntRate : #0", rst.getAaaMap().get("20140128"));
		
		/*Query qr = session.createQuery("from Stock a where a.useAsUnderlying = :param");
		qr.setParameter("param", true);
		underlyingStocks = qr.list();*/
		
//		System.out.println("SizeAAA" + hifiveList.size());
//		int listSize = hifiveList.size();
//		if(listSize < 21){
//			for( int i=0; i< 21- listSize; i++){
//				Hifive temp = new Hifive();
//				temp.setProdId("temp"+ i);
//				temp.setHifiveType("-");
//				temp.setProdName("");
//				hifiveList.add(temp);
//			}
//		}
		
		selectHifive = hifiveList.get(0);
//		log.info("selected:1111 #0, #1", selectHifive.getProdId());
	}
	
//	@Begin(join=true)
	public void addHifive(){
		int cnt = 1;
		String tempProdName;
//		log.info("Select:00 #0", selectHifive);
		String tempProdId = selectHifive.getProdId() + "_" + cnt;
		List<String> prodIdList = new ArrayList<String>();

		for(Hifive aa : hifiveList){
//			log.info("In the ProdIdList:#0, #1", aa.getProdId());
			prodIdList.add(aa.getProdId());
		}
		
		while(prodIdList.contains(tempProdId)){
			cnt = cnt+1;
			tempProdId= selectHifive.getProdId() + "_" + cnt;
		}
		tempProdName = selectHifive.getProdName()+ "_" + cnt;
		

		Hifive temp1 = (Hifive)selectHifive.clone();
		temp1.setVirtual(true);
		temp1.setProdId(tempProdId);
		temp1.setProdName(tempProdName);
//		temp1.setCounterparty(null);
		
		List<HifiveUnderlying> tempUList = new ArrayList<HifiveUnderlying>();
		for(HifiveUnderlying aa : selectHifive.getHifiveUnderlyings()){
			HifiveUnderlying tempUnder = new HifiveUnderlying();
			tempUnder =(HifiveUnderlying) aa.clone();
			tempUnder.setId(new HifiveUnderlyingId(tempProdId, aa.getUnderlyingId()));

//			tempUnder.setProdId(tempProdId);
//			tempUnder.setUnderlyingId(aa.getUnderlyingId());
			tempUList.add(tempUnder);
		}
		if(tempUList.size()==0){
			HifiveUnderlying tempU = new HifiveUnderlying();
			tempU.setId(new HifiveUnderlyingId(tempProdId, messages.get("inputUnderlying")));
			
//			tempU.setProdId(tempProdId);
			tempU.setUnderlyingId(messages.get("inputUnderlying"));
			tempU.setUnderType(MarketVariableType.STOCK);
			tempUList.add(tempU);
		}
		
		List<HifiveStrike> tempSList = new ArrayList<HifiveStrike>();
		for(HifiveStrike aa : selectHifive.getHifiveStrikes()){
			HifiveStrike tempStrike = new HifiveStrike();
			tempStrike =(HifiveStrike) aa.clone();
			tempStrike.setId(new HifiveStrikeId(tempProdId, aa.getId().getStrikeSerial()));
			tempSList.add(tempStrike);
		}
		
		if(tempSList.size()==0){
			HifiveStrike tempS = new HifiveStrike();
			tempS.setId(new HifiveStrikeId(tempProdId, 1));
			tempSList.add(tempS);
		}

		List<PricingMaster> tempPrList = new ArrayList<PricingMaster>();
		List<PricingUnderlyings> tempPrUnderAllList = new ArrayList<PricingUnderlyings>();
		
		log.info("In Add Hifive11 : #0", selectHifive.getProdId());
		for(PricingMaster aa : selectHifive.getPriceSetting()){
			log.info("In Add Hifive111 : #0", selectHifive.getProdId());
			PricingMaster tempPrSetting = new PricingMaster();

			tempPrSetting =(PricingMaster) aa.clone();
			tempPrSetting.setId(new PricingMasterId(aa.getId().getPricingObjId(), tempProdId));
			
			PricingUnderlyings tempPrUnderSetting = new PricingUnderlyings();
			List<PricingUnderlyings> tempPrUnderList = new ArrayList<PricingUnderlyings>();
			
			for(PricingUnderlyings bb : aa.getPrUnderlyingList()){
				tempPrUnderSetting =(PricingUnderlyings)bb.clone();
				tempPrUnderSetting.setId(new PricingUnderlyingsId(bb.getId().getPricingObjId(), tempProdId, bb.getId().getUnderlyingId()));
				tempPrUnderList.add(tempPrUnderSetting);
				
				tempPrUnderAllList.add(tempPrUnderSetting);
			}
			tempPrSetting.setPrUnderlyingList(tempPrUnderList);
			tempPrList.add(tempPrSetting);
		}
		temp1.setHifiveUnderlyings(tempUList);		
		temp1.setHifiveStrikes(tempSList);
		temp1.setPriceSetting(tempPrList);
		temp1.setPrUnderSetting(tempPrUnderAllList);


		temp1.setProdReturn(null);
		temp1.setProdGreeks(null);
		
//		log.info("AAAA");
//		session.get(Hifive.class, "HIFIVE_03");
//		log.info("BBB");
		
		hifiveList.add(temp1);
		
		if(filterHifiveList!=null){
			filterHifiveList.add(temp1);
		}

		/*underlyingMap.put(MarketVariableType.STOCK, underlyingStocks);
		List<String> rst = new ArrayList<String>();
		for(Stock aa : underlyingStocks){
			rst.add(aa.getMvId());
		}
		underlyingIdMap.put(MarketVariableType.STOCK, rst);*/
		
		
//		facesMessages.add("#{messages.insertHifive}");
//		facesMessages.add("AAA",messages.get("insertHifive"));
//		facesMessages.add(Severity.ERROR, messages.get("insertHifive"));
//		facesMessages.addFromResourceBundle(Severity.INFO,"insertHifive");
//		facesMessages.addFromResourceBundle("deleteHifive", selectHifive.getProdId());
		statusMessages.addFromResourceBundle("insertHifive.successMessages");

		selectHifive= temp1;
		log.info("In Add Hifive #0", selectHifive.getProdId());
		
	}
	
	public void removeHifive(){
//		save();
		Transaction tx = session.beginTransaction();
		if(selectHifive.isVirtual()){
			session.delete(selectHifive);
			
//			selectHifive.getHifiveStrikes().clear();
//			selectHifive.getHifiveUnderlyings().clear();
			hifiveList.remove(selectHifive);
			if(filterHifiveList!=null){
				filterHifiveList.remove(selectHifive);
			}
			statusMessages.addFromResourceBundle("deleteVirtualHifive");
		}else {
			statusMessages.addFromResourceBundle("deleteRealHifive");
		}
		tx.commit();
	}	
	public void addUnderlying(){
		if(selectHifive.isVirtual()){
			HifiveUnderlying temp = new HifiveUnderlying();
			HifiveUnderlyingId tempId = new HifiveUnderlyingId();
			
			tempId.setProdId(selectHifive.getProdId());
			tempId.setUnderlyingId(messages.get("inputUnderlying"));
			temp.setId(tempId);
			
			temp.setUnderType(MarketVariableType.STOCK);
//			temp.setProdId(selectHifive.getProdId());
			temp.setUnderlyingId(messages.get("inputUnderlying"));
//			allUnderlyingIdList = underlyingIdMap.get(MarketVariableType.STOCK);
			
			selectHifive.getHifiveUnderlyings().add(temp);
			statusMessages.addFromResourceBundle("insertHifiveStrike.success");
		}else{
			statusMessages.addFromResourceBundle("insertHifive.forRealProduct");
		}		
	}

	public void removeUnderlying(HifiveUnderlying selection){
		log.info("SelectionUnderlying:#0", selection);
		if(selectHifive.isVirtual()){
			if(selectHifive.getHifiveUnderlyings().size()>1){
				
			selectHifive.getHifiveUnderlyings().remove(selection);
			session.delete(selection);
			session.flush();
			statusMessages.addFromResourceBundle("removeHifiveUnderlying.success");
			}
			else{
				statusMessages.addFromResourceBundle("removeHifiveUnderlying.forMinimumSize");
			}
		}else{
			statusMessages.addFromResourceBundle("removeHifiveUnderlying.forRealProduct");
		}
	}
	
	public void addStrike(){
		if(selectHifive.isVirtual()){
			HifiveStrike temp = new HifiveStrike();
			temp = (HifiveStrike)selectHifive.getHifiveStrikes().get(0).clone();
			HifiveStrikeId tempId = new HifiveStrikeId();
			tempId.setProdId(selectHifive.getProdId());
			tempId.setStrikeSerial(selectHifive.getHifiveStrikes().size()+1);
			temp.setId(tempId);
			selectHifive.getHifiveStrikes().add(temp);
			statusMessages.addFromResourceBundle("insertHifiveStrike.success");
		}else{
			statusMessages.addFromResourceBundle("insertHifive.forRealProduct");
		}
	}
	
	public void removeStrike(HifiveStrike selection){
		log.info("SelectionStrike:#0", selection);
		log.info("SelectionStrike:#0", selection.getId().getProdId());
		String prodId = selectHifive.getProdId();
		long serial = 1;
		if(selectHifive.isVirtual()){
			if(selectHifive.getHifiveStrikes().size()>1){
		
			selectHifive.getHifiveStrikes().remove(selection);
			session.delete(selection);
			session.flush();
			
//			List<HifiveStrike> tempList =new ArrayList<HifiveStrike>();
//			for(HifiveStrike aa : selectHifive.getHifiveStrikes()){
////				selectHifive.getHifiveStrikes().remove(aa);
//				session.delete(aa);
//				
//				HifiveStrike temp =(HifiveStrike) aa.clone();
//				temp.setId(new HifiveStrikeId(prodId, serial));
//				tempList.add(temp);
//				serial =serial +1;
//				
////				selectHifive.getHifiveStrikes().add(temp);
////				session.saveOrUpdate(selectHifive);
////				session.saveOrUpdate(temp);
//			}
//			session.flush();
//			selectHifive.getHifiveStrikes().clear();
//			selectHifive.setHifiveStrikes(tempList);
			
			session.saveOrUpdate(selectHifive);
			session.flush();
			statusMessages.addFromResourceBundle("removeHifiveStrike.success");
			}
			else{
				statusMessages.addFromResourceBundle("removeHifive.forMinmumSize");
			}
		}else{
			statusMessages.addFromResourceBundle("removeHifive.forRealProduct");
		}
	}
	
//	public void onSelect(SelectEvent event){
//		log.info("OnSelect:#0", (Hifive)(event.getObject()));
//		Events.instance().raiseEvent("evtHifiveSelect", (Hifive)(event.getObject()));
//		
//	}
	
	public void resetSelection(){
//		log.info("FilterEvent:{},{}", filterEvent.toString(), filterHifiveList);
		if(filterHifiveList!=null){
			if(!filterHifiveList.isEmpty() &&
					( selectHifive==null ||  !filterHifiveList.contains(selectHifive))){
					selectHifive= filterHifiveList.get(0);
			}	
		}else{
			if(!hifiveList.isEmpty() &&  
					( selectHifive==null ||  !hifiveList.contains(selectHifive)) ){
				selectHifive= hifiveList.get(0);
			}
		}
	}
	
	
	public List<String> completeCntparty(String auto){
		List<String> rst = new ArrayList<String>();
		for(String aa : getAllCntPartyIdList()){
			if(aa.toUpperCase().contains(auto.toUpperCase())){
				rst.add(aa);
			}
		}
		return rst;
	}
	
	public List<String> completeUnderlying(String auto){
		List<String> rst = new ArrayList<String>();
		log.info("SizeUnder :#0", getAllUnderlyingIdList().size());
		for(String aa : getAllUnderlyingIdList()){
			if(aa.toUpperCase().contains(auto.toUpperCase())){
				rst.add(aa);
			}
		}
		return rst;
	}
	
	/*public List<Stock> completeUnderlying(String auto){
		List<Stock> rst = new ArrayList<Stock>();
		for(Stock aa : underlyingStocks){
			if(aa.isUseAsUnderlying() 
						&&  aa.getStockId().toUpperCase().contains(auto.toUpperCase())){
				rst.add(aa);
			}
		}
//		log.info("Under: #0,#1", stockList.getResultList().size(), rst.size());
		return rst;
	}*/
	/*public List<Stock> completeUnderlying(String auto){
		List<Stock> rst = new ArrayList<Stock>();
		for(Stock aa : stockList.getResultList()){
			if(aa.isUseAsUnderlying() 
						&&  aa.getStockId().toUpperCase().contains(auto.toUpperCase())){
				rst.add(aa);
			}
		}
		log.info("Under: #0,#1", stockList.getResultList().size(), rst.size());
		return rst;
	}*/
	
	public void resetCntparty(SelectEvent event){
		Hifive hifive =(Hifive)((DataTable)event.getComponent().getParent().getParent().getParent()).getRowData();
//		log.info("Reset : #0,#1", event.getComponent().getParent().getId(), hifive.getProdId());
		selectHifive = hifive;

		for(Counterparty aa : counterpartyList.getResultList()){
			if(aa.getCounterpartyId().equals(event.getObject().toString())){
				log.info("Reset111 : #0,#1", aa.getCounterpartyId() ,selectHifive.getProdId());
				selectHifive.setCounterparty(aa);
			}
		}
	}
	
//	public void resetUnderlying(SelectEvent event){
////		HifiveUnderlying underlying =(HifiveUnderlying)((DataTable)event.getComponent().getParent().getParent().getParent()).getRowData();
//		
//		for(HifiveUnderlying aa : selectHifive.getHifiveUnderlyings()){
//			for(Stock bb : underlyingStocks){
//				if(bb.getStockId().equals(aa.getUnderlyingId())){
//					aa.setId(new HifiveUnderlyingId(selectHifive.getProdId(),aa.getUnderlyingId()));
//					aa.setUnderlying(bb);
//				}
//			}
//		}
//	}	
	
	public void resetUnderlying(HifiveUnderlying underlying){
//		HifiveUnderlying temp =(HifiveUnderlying) underlying.clone();
		
		for(MarketVariableNew mvNew: underlyingList){
			log.info("RestU: #0,#1", mvNew.getMvId(), underlying.getUnderlyingId());
			if(mvNew.getMvId().equals(underlying.getUnderlyingId())){
				log.info("RestUU: #0,#1", mvNew.getMvId(), underlying.getUnderlyingId());
				underlying.getId().setUnderlyingId(mvNew.getMvId());
				underlying.setUnderlying(mvNew);
				break;
			}
		}
		
		
		for(PricingMaster aa : selectHifive.getPriceSetting()){
			PricingUnderlyings  tempPrUnder = new PricingUnderlyings();
			tempPrUnder.setId(new PricingUnderlyingsId(aa.getId().getPricingObjId(), selectHifive.getProdId(), underlying.getUnderlyingId()));
			aa.getPrUnderlyingList().add(tempPrUnder);
			selectHifive.getPrUnderSetting().add(tempPrUnder);
			log.info("ResetU:#0, #1,#2", tempPrUnder.getId().getPricingObjId()
					, tempPrUnder.getId().getProdId(),  tempPrUnder.getId().getUnderlyingId());
		}
	}	
	private List<MarketVariableNew> underlyingList = new ArrayList<MarketVariableNew>();
	public List<MarketVariableNew> getUnderlyingList() {
		return underlyingList;
	}
	public void setUnderlyingList(List<MarketVariableNew> underlyingList) {
		this.underlyingList = underlyingList;
	}

	private List<String> underlyingIdList = new ArrayList<String>();
	public List<String> getUnderlyingIdList() {
		return underlyingIdList;
	}
	public void setUnderlyingIdList(List<String> underlyingIdList) {
		this.underlyingIdList = underlyingIdList;
	}

	/*private Map<MarketVariableType, List<String>> underlyingMap;
	public Map<MarketVariableType, List<String>> getUnderlyingMap() {
		return underlyingMap;
	}
	public void setUnderlyingMap(Map<MarketVariableType, List<String>> underlyingMap) {
		this.underlyingMap = underlyingMap;
	}*/
	
	/*public void loadUnderlying(MarketVariableType uType){
		List<String> rst = new ArrayList<String>();
		if(underlyingIdMap.get(uType)==null || underlyingIdMap.get(uType).isEmpty() ){
			Criteria crit = session.createCriteria(uType.getClassPath());
			//		crit.add(Restrictions.eq("useAsUnderlying", true));
			
			List<? extends MarketVariableJoin> mvList = crit.list();
			for(MarketVariableJoin aa : mvList){
				rst.add(aa.getMvId());
			}
			underlyingIdMap.put(uType, rst);
			underlyingMap.put(uType, mvList);
		}else{
			rst = underlyingIdMap.get(uType); 
		}
		allUnderlyingIdList= rst;
	}*/
	
//	public void onRowEdit(RowEditEvent evt){
//		statusMessages.add("Row Edit : #0", ((Hifive)evt.getObject()).getProdId());
//	}
	
	public List<Counterparty> completeCpartyObj(String auto){
		log.info("ObjectChange :#0", auto);
		List<Counterparty> rst = new ArrayList<Counterparty>();
		for(Counterparty aa : counterpartyList.getResultList()){
			if(aa.getCounterpartyId().toUpperCase().contains(auto.toUpperCase())){
				rst.add(aa);
			}
		}
		return rst;
	}
	private Counterparty xxcounterparty;

	public Counterparty getXxcounterparty() {
		return xxcounterparty;
	}
	public void setXxcounterparty(Counterparty xxcounterparty) {
		this.xxcounterparty = xxcounterparty;
	}
	
/*	public void onCpartyChange(CellEditEvent event){
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		DataTable tb = (DataTable)FacesContext.getCurrentInstance()
					.getViewRoot().findComponent("formHifiveEdit:tableHifive");
		tb.setRowIndex(event.getRowIndex());
		selectHifive = (Hifive)tb.getSelection();
		log.info("RowIndex:#0,#1", event.getRowIndex(), tb.getRowIndex(), ((Hifive)tb.getSelection()).getProdId());
		
//		for(Counterparty aa : allCntPartyList.getResultList()){
//			log.info("Cnt :#0, #1", aa.getCounterpartyId(), allCntPartyList.getResultList().size());
//		}
		for(Counterparty aa : allCntPartyList.getResultList()){
			if(aa.getCounterpartyId().equals(newValue.toString())){
				log.info("Reset1 : #0,#1", aa.getCounterpartyId() ,selectHifive.getProdId());
				selectHifive.setCounterparty(aa);
			}
		}
		String facet  = event.getColumn().getFacet("header").getAttributes().get("value").toString();
//		statusMessages.addFromResourceBundle("cellEdit", event.getOldValue(), event.getNewValue(), facet);
		statusMessages.add(Severity.WARN,"Changed to #0 from #1 on Column:#2", event.getNewValue(),event.getOldValue(), facet);
		
	}*/
//	public void resetFilter(){
//		DataTable dataTable =(DataTable)FacesContext.getCurrentInstance().getViewRoot()
//				.findComponent("formHifiveEdit:tableHifive");
//		if(dataTable!=null){
//			dataTable.reset();
//		}
//	}
//	public void resetSelection(ActionEvent event){
//	log.info("ActionEvent:#0, #1", event.getSource()	);
//	
//}

	public void save(){
		boolean flag=true;
		Transaction tx = session.beginTransaction();
		log.info("in the save Dirty : #0,#1", session.isDirty(), session.getFlushMode());

		try{
			for(Hifive aa : hifiveList){
				if(aa.isVirtual()){
					flag=true;
					for(HifiveUnderlying bb : aa.getHifiveUnderlyings()){
						if(bb.getUnderlyingId().equals(messages.get("inputUnderlying"))){
							flag=false;
							statusMessages.addFromResourceBundle(Severity.ERROR,"error.setUnderlyingAsset", aa.getProdId());
							break;
						}
					}
					if(flag){
						session.saveOrUpdate(aa);
						log.info("in the save :#0,#1", aa.getProdId(), aa.getHifiveUnderlyings().get(0).getUnderlyingId());
						
					}
				}
			}
			log.info("SAVE:");
			session.flush();
			tx.commit();
			log.info("SAVE1:");
		}catch(RuntimeException e){
			try{
				tx.rollback();
			}catch(RuntimeErrorException ee){
				
			}throw e;
		}
	}
	
	public void simulate(){
		List<ProductReturn> prodReturnList = new ArrayList<ProductReturn>();
		List<ProductGreeks> prodGreekList = new ArrayList<ProductGreeks>();
		
		String bssd = basedateBean.getBssd();
		for( PricingMaster aa : selectHifive.getPriceSetting()){
			if(aa.getPricingDll().getDllId().equals("HIFIVE_MC")){
				KisHifive kisHifve = new KisHifive();
				kisHifve.load(selectHifive, basedateBean.getBaseDate());
				
				double price = kisHifve.getPrice();
//				double rho = kisHifve.getRho();
//				double theta = kisHifve.getTheta();
				
				log.info("PricingResult : #0, #1,#2", price);
				
				ProductReturnId returnId = new ProductReturnId(bssd, aa.getId().getPricingObjId(),aa.getId().getProdId());
				ProductReturn prodReturn = new ProductReturn(returnId);
				
				prodReturn.setPresValue(new BigDecimal(price));
//				prodReturn.setRho(new BigDecimal(rho));
//				prodReturn.setTheta(new BigDecimal(theta));
				prodReturnList.add(prodReturn);

//				Map<String, Double> delta = kisHifve.getDelta();
//				Map<String, Double> gamma = kisHifve.getGamma();
//				Map<String, Double> vega = kisHifve.getVega();
//				for(PricingUnderlyings bb : aa.getPrUnderlyingList()){
//					String underId = bb.getId().getUnderlyingId();
//					ProductGreeksId greekId = new ProductGreeksId(bssd, bb.getId().getPricingObjId()
//													, bb.getId().getProdId(), underId); 
//					ProductGreeks prodGreeks = new ProductGreeks(greekId);
//					prodGreeks.setDelta(new BigDecimal(delta.get(underId)));
//					prodGreeks.setGamma(new BigDecimal(gamma.get(underId)));
//					prodGreeks.setVega(new BigDecimal(vega.get(underId)));
//					
//					prodGreekList.add(prodGreeks);
//				}
			}
		}
		selectHifive.setProdReturn(prodReturnList);
		selectHifive.setProdGreeks(prodGreekList);
	}
	
	public void simulate1(){
		int nStock = selectHifive.getHifiveUnderlyings().size();
		int index=0;
		log.info("Start:#0", nStock);
		
		double[] S0 = new double[nStock]; 
		double[] div = new double[nStock];
		int[] bUpHitted = new int[nStock];  		// IN:  Knock-out Barrier Hitting 여부

		for(HifiveUnderlying aa : selectHifive.getHifiveUnderlyings()){
			S0[index]= aa.getBasePrice().doubleValue();
			div[index]=aa.getDividend().doubleValue();
			bUpHitted[index]=aa.getUpHitted().getKisCode();
			log.info("Underlying :#0, #1, #2", S0[index], div[index], bUpHitted[index]);
			index = index +1;
		}
		
		int nStrike = selectHifive.getHifiveStrikes().size();
		int[] dates= new int[nStrike]; 			    // IN:  중간 평가일까지의 일수 
		int[] pay_dates= new int[nStrike]; 			// IN:  중간 지급일까지의 일수 
		
		double[] X1= new double[nStrike];
		double[] X2= new double[nStrike];
		double[] X3= new double[nStrike];
		
		double[] upBarrier= new double[nStrike];		// IN:  Knock-out Barrier
		
		double[] amt1= new double[nStrike];			// IN:  행사가격1에 해당되는 중도상환 금액
		double[] amt2= new double[nStrike];			// IN:  행사가격2에 해당되는 중도상환 금액
		double[] amt3= new double[nStrike];			// IN:  행사가격3에 해당되는 중도상환 금액
		double[] coupon= new double[nStrike+1];			// IN:  추가 지급 쿠폰
		
		double[] p = new double[nStrike+2];		// OUT: 중도상환 확률
		
		
		
		index =0;
		Date baseDate = basedateBean.getBaseDate();
		FnCalendar fnBase = FnCalendar.getInstance(baseDate);
		
		for(HifiveStrike aa : selectHifive.getHifiveStrikes()){
			dates[index] = aa.getFnStrikeDate().differDays(fnBase);
			pay_dates[index] = aa.getFnPaymentDate().differDays(fnBase);
			
			X1[index] = aa.getStrikePrice().doubleValue();
			X2[index] = aa.getDblJumpStrike().doubleValue();
			X3[index] = aa.getTplJumpStrike().doubleValue();
			
			amt1[index] = aa.getPayoffAmt().doubleValue();
			amt2[index] = aa.getDblJumpPayoff().doubleValue();
			amt3[index] = aa.getTplJumpPayoff().doubleValue();
			
			upBarrier[index] = aa.getUpBarrier().doubleValue();
			coupon[index] =aa.getCouponRate().doubleValue();
			
			log.info("Strike :#0,#1,#2,#3,#4", dates[index], X1[index], amt1[index], coupon[index]);
			index = index +1;
		}
		
		coupon[index] = selectHifive.getCouponRateLast().doubleValue();
		
		for(int j=0; j<nStrike; j++){
			p[j] =0;
		}
				
//		int nt = selectHifive.getPriceSetting().get(0).getDiscountIrc().getIrcBucketMap().size();
		int nt = 2;
		
		double[] t = new double[nt];
		double[] rf= new double[nt*nStock];
//		double[] rf1= new double[nt];
//		double[] rf2= new double[nt];
		double[] rd = new double[nt];

		for(int k=0; k<nt; k++){
			t[k]=k+1;
//			rf1[k] = 0.02;
//			rf2[k] = 0.02;
			rd[k] = 0.02;
		}
		
		for(int k=0; k<nt*nStock; k++){
			rf[k]= 0.02;
		}
		

		
		index =0;
		for(Map.Entry<EMaturity, IntRate> aa : selectHifive.getPriceSetting().get(0)
												.getDiscountIrc().getIrcBucketMap().entrySet()){
//			t[index]= aa.getKey();
//			rd[index] =aa.getValue().getIntRate().get(currentDate);
			
			for( int i=0; i< nt ; i++){
//				rf[i + index] =aa.getValue().getIntRate().get(currentDate);
			}
		}
		
		int n_vol = 2;
		double[] t_vol = new double[n_vol];
		double[] vol= new double[n_vol*nStock];
		double[] vol1= new double[n_vol];
		double[] vol2= new double[n_vol];
		double[] corr= new double[nStock*nStock];
//		double[][] rho= new double[n_vol][n_vol];
		
		for(int k=0; k<nt; k++){
			t_vol[k]=k+1;
			vol1[k] = 0.2;
			vol2[k] = 0.2;
		}
		for(int h=0; h< n_vol*nStock; h++){
			vol[h] = 0.2;
		}
		for(int h=0; h < nStock*nStock; h++){
			corr[h] = 0.9;
			log.info("Underlying3 :#0, #1, #2", h, nStock , nStock*nStock);
		}
		corr[0]=1;
		corr[3]=1;

		double downBarrier =0.55;		// IN:  Knock-in Barrier

		int upBarrierFlag = 0;		    // IN:  upBarrier 체크 방법(0:동시, 1:각각)
		int downBarrierFlag = 1;		// IN:  downBarrier 유무(0:없음, 1:있음)
		
		int bDownHitted =0;  		// IN:  Knock-in Barrier Hitting 여부
		int nTrials =1000;			// IN:  시행회수 
		double max_loss= 1;		// IN:  최대손실
		
		for( int j=0 ; j<nStock; j++){
			double MC_delta = IHiFiveMc.INSTANCE.HiFive_MC_Delta(j+1,S0, t, rf, rd, nt, t_vol, vol, n_vol, corr, div, nStock, X1, X2, X3,
				upBarrier, downBarrier, amt1, amt2, amt3, coupon, dates, pay_dates, nStrike, upBarrierFlag, 
				downBarrierFlag, bUpHitted, bDownHitted, nTrials, max_loss);
				
			System.out.println("HiFiveMC_delta:"+j+"_"+MC_delta);
		}
		
		double MC_vega = IHiFiveMc.INSTANCE.HiFive_MC_Vega(1,S0, t, rf, rd, nt, t_vol, vol, n_vol, corr, div, nStock, X1, X2, X3,
				upBarrier, downBarrier, amt1, amt2, amt3, coupon, dates, pay_dates, nStrike, upBarrierFlag, 
				downBarrierFlag, bUpHitted, bDownHitted, nTrials, max_loss);
		
		double MC_rho = IHiFiveMc.INSTANCE.HiFive_MC_Rho(S0, t, rf, rd, nt, t_vol, vol, n_vol, corr, div, nStock, X1, X2, X3,
				upBarrier, downBarrier, amt1, amt2, amt3, coupon, dates, pay_dates, nStrike, upBarrierFlag, 
				downBarrierFlag, bUpHitted, bDownHitted, nTrials, max_loss);
				
		
		log.info("Result: #0, #1,#2,#3", MC_vega, MC_rho);
	}
}
