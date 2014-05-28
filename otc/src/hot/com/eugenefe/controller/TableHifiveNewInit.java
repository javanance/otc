package com.eugenefe.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.management.RuntimeErrorException;

import org.drools.lang.dsl.DSLMapParser.consequence_key_return;
import org.drools.lang.dsl.DSLMapParser.mapping_file_return;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import com.eugenefe.entity.Counterparty;
import com.eugenefe.entity.Hifive;
import com.eugenefe.entity.HifiveCondition;
import com.eugenefe.entity.HifiveMonthCoupon;
import com.eugenefe.entity.HifiveStrike;
import com.eugenefe.entity.HifiveStrikeId;
import com.eugenefe.entity.HifiveUnderlying;
import com.eugenefe.entity.HifiveUnderlyingId;
import com.eugenefe.entity.MarketVariableJoin;
import com.eugenefe.entity.MarketVariableNew;
import com.eugenefe.entity.PricingMaster;
import com.eugenefe.entity.PricingMasterId;
import com.eugenefe.entity.PricingUnderlyings;
import com.eugenefe.entity.PricingUnderlyingsId;
import com.eugenefe.entity.Stock;
import com.eugenefe.session.CounterpartyList;
import com.eugenefe.util.ColumnModel;
import com.eugenefe.util.DynamicColumnModel;
import com.eugenefe.util.MarketVariableType;
//import org.jboss.seam.framework.Query;

@Name("tableHifiveNewInit")
@Scope(ScopeType.CONVERSATION)
public class TableHifiveNewInit {
	@Logger		private Log log;
	@In			private Session session;
	@In			private StatusMessages statusMessages;
	@In			private Map<String, String> messages;
	// @In private FacesMessages facesMessages;

	// @In("#{counterpartyList}")
	private CounterpartyList counterpartyList;
	private List<Stock> underlyingStocks;	
	private List<String> allCntPartyIdList = new ArrayList<String>();
	private List<String> allUnderlyingIdList = new ArrayList<String>();
	private List<String> underlyingIdList = new ArrayList<String>();	


	private List<Hifive> hifiveList;
	private List<Hifive> selectHifiveList;
	private List<Hifive> filterHifiveList;

	private Hifive selectHifive;
	private HifiveStrike selectStrike;
	private Map.Entry<HifiveStrike, Map<String, Object>> selectStrikeEntry;
	private Map.Entry<HifiveCondition, Map<String, Object>> selectConditionEntry;
	private Map.Entry<HifiveMonthCoupon, Map<String, Object>> selectCouponEntry;
	
	
	public Map.Entry<HifiveMonthCoupon, Map<String, Object>> getSelectCouponEntry() {
		return selectCouponEntry;
	}
	public void setSelectCouponEntry(Map.Entry<HifiveMonthCoupon, Map<String, Object>> selectCouponEntry) {
		this.selectCouponEntry = selectCouponEntry;
	}
	public Map.Entry<HifiveCondition, Map<String, Object>> getSelectConditionEntry() {
		return selectConditionEntry;
	}
	public void setSelectConditionEntry(Map.Entry<HifiveCondition, Map<String, Object>> selectConditionEntry) {
		this.selectConditionEntry = selectConditionEntry;
	}
	public Map.Entry<HifiveStrike, Map<String, Object>> getSelectStrikeEntry() {
		return selectStrikeEntry;
	}
	public void setSelectStrikeEntry(Map.Entry<HifiveStrike, Map<String, Object>> selectStrikeEntry) {
		this.selectStrikeEntry = selectStrikeEntry;
	}

	private HifiveUnderlying selectUnder;

	private List<MarketVariableNew> underlyingList = new ArrayList<MarketVariableNew>();
	private Map<MarketVariableType, List<? extends MarketVariableJoin>> underlyingMap = new HashMap<MarketVariableType, List<? extends MarketVariableJoin>>();	
	private Map<MarketVariableType, List<String>> underlyingIdMap = new HashMap<MarketVariableType, List<String>>();
	private Counterparty xxcounterparty;

	
	private List<String> strikeColumnList = new ArrayList<String>();
	private List<String> selectStrikeColumnList = new ArrayList<String>();
	private List<String> selectStStrikeColumnList = new ArrayList<String>();
	private List<String> selectNoStrikeColumnList = new ArrayList<String>();
	private Map<String, String> strikeColumnMap = new HashMap<String, String>();
	private Map<String, String> selectStrikeColumnMap ;
	
	private List<DynamicColumnModel> strikeColumnModelList= new ArrayList<DynamicColumnModel>();
//	private List<DynamicColumnModel> selectStrikeColumnModelList= new ArrayList<DynamicColumnModel>();
	private List<DynamicColumnModel> selectStrikeColumnModelList;
	private List<DynamicColumnModel> filterStrikeColumnModelList;
	
	public List<DynamicColumnModel> getFilterStrikeColumnModelList() {
		return filterStrikeColumnModelList;
	}
	public void setFilterStrikeColumnModelList(List<DynamicColumnModel> filterStrikeColumnModelList) {
		this.filterStrikeColumnModelList = filterStrikeColumnModelList;
	}
	public List<DynamicColumnModel> getSelectStrikeColumnModelList() {
		return selectStrikeColumnModelList;
	}
	public void setSelectStrikeColumnModelList(List<DynamicColumnModel> selectStrikeColumnModelList) {
		this.selectStrikeColumnModelList = selectStrikeColumnModelList;
	}
	public List<DynamicColumnModel> getStrikeColumnModelList() {
		return strikeColumnModelList;
	}
	public void setStrikeColumnModelList(List<DynamicColumnModel> strikeColumnModelList) {
		this.strikeColumnModelList = strikeColumnModelList;
	}

	private List<String> selectConditionColumnList = new ArrayList<String>();
	private List<String> selectCouponColumnList = new ArrayList<String>();
	
	private Map<HifiveStrike, Map<String, Object>> strikeMap = new TreeMap<HifiveStrike, Map<String, Object>>();
	private List<Map.Entry<HifiveStrike, Map<String, Object>>> strikeList = new ArrayList<Map.Entry<HifiveStrike, Map<String, Object>>>();
	
	private Map<HifiveCondition, Map<String, Object>> conditionMap = new TreeMap<HifiveCondition, Map<String, Object>>();
	private List<Map.Entry<HifiveCondition, Map<String, Object>>> conditionList = new ArrayList<Map.Entry<HifiveCondition, Map<String, Object>>>();
	
	private Map<HifiveMonthCoupon, Map<String, Object>> couponMap = new TreeMap<HifiveMonthCoupon, Map<String, Object>>();
	private List<Map.Entry<HifiveMonthCoupon, Map<String, Object>>> couponList = new ArrayList<Map.Entry<HifiveMonthCoupon, Map<String, Object>>>();	

	
//*********************************************************************	
	
	public List<Stock> getUnderlyingStocks() {
		return underlyingStocks;
	}

	public Map<String, String> getSelectStrikeColumnMap() {
		return selectStrikeColumnMap;
	}

	public void setSelectStrikeColumnMap(Map<String, String> selectStrikeColumnMap) {
		this.selectStrikeColumnMap = selectStrikeColumnMap;
	}

	public Map<String, String> getStrikeColumnMap() {
		return strikeColumnMap;
	}

	public void setStrikeColumnMap(Map<String, String> strikeColumnMap) {
		this.strikeColumnMap = strikeColumnMap;
	}

	public List<String> getSelectStStrikeColumnList() {
		return selectStStrikeColumnList;
	}

	public void setSelectStStrikeColumnList(List<String> selectStStrikeColumnList) {
		this.selectStStrikeColumnList = selectStStrikeColumnList;
	}

	public List<String> getSelectNoStrikeColumnList() {
		return selectNoStrikeColumnList;
	}

	public void setSelectNoStrikeColumnList(List<String> selectNoStrikeColumnList) {
		this.selectNoStrikeColumnList = selectNoStrikeColumnList;
	}

	public List<String> getSelectCouponColumnList() {
		return selectCouponColumnList;
	}

	public void setSelectCouponColumnList(List<String> selectCouponColumnList) {
		this.selectCouponColumnList = selectCouponColumnList;
	}

	public Map<HifiveMonthCoupon, Map<String, Object>> getCouponMap() {
		return couponMap;
	}

	public void setCouponMap(Map<HifiveMonthCoupon, Map<String, Object>> couponMap) {
		this.couponMap = couponMap;
	}

	public List<Map.Entry<HifiveMonthCoupon, Map<String, Object>>> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<Map.Entry<HifiveMonthCoupon, Map<String, Object>>> couponList) {
		this.couponList = couponList;
	}

	public List<String> getSelectConditionColumnList() {
		return selectConditionColumnList;
	}

	public void setSelectConditionColumnList(List<String> selectConditionColumnList) {
		this.selectConditionColumnList = selectConditionColumnList;
	}

	public Map<HifiveCondition, Map<String, Object>> getConditionMap() {
		return conditionMap;
	}

	public void setConditionMap(Map<HifiveCondition, Map<String, Object>> conditionMap) {
		this.conditionMap = conditionMap;
	}

	public List<Map.Entry<HifiveCondition, Map<String, Object>>> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<Map.Entry<HifiveCondition, Map<String, Object>>> conditionList) {
		this.conditionList = conditionList;
	}

	public void setUnderlyingStocks(List<Stock> underlyingStocks) {
		this.underlyingStocks = underlyingStocks;
	}
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
		if (allCntPartyIdList == null || allCntPartyIdList.isEmpty()) {
			allCntPartyIdList = new ArrayList<String>();
			for (Counterparty aa : counterpartyList.getResultList()) {
				allCntPartyIdList.add(aa.getCounterpartyId());
			}
		}
		return allCntPartyIdList;
	}

	public void setAllCntPartyIdList(List<String> allCntPartyIdList) {
		this.allCntPartyIdList = allCntPartyIdList;
	}

	public List<String> getAllUnderlyingIdList() {
		if (allUnderlyingIdList == null || allUnderlyingIdList.isEmpty()) {
			allUnderlyingIdList = new ArrayList<String>();
			for (Stock aa : underlyingStocks) {
				allUnderlyingIdList.add(aa.getStockId());
			}
		}
		return allUnderlyingIdList;
	}

	public void setAllUnderlyingIdList(List<String> allUnderlyingIdList) {
		this.allUnderlyingIdList = allUnderlyingIdList;
	}



	public Map<MarketVariableType, List<String>> getUnderlyingIdMap() {
		return underlyingIdMap;
	}

	public void setUnderlyingIdMap(Map<MarketVariableType, List<String>> underlyingIdMap) {
		this.underlyingIdMap = underlyingIdMap;
	}



	public Map<MarketVariableType, List<? extends MarketVariableJoin>> getUnderlyingMap() {
		return underlyingMap;
	}

	public void setUnderlyingMap(Map<MarketVariableType, List<? extends MarketVariableJoin>> underlyingMap) {
		this.underlyingMap = underlyingMap;
	}

	public TableHifiveNewInit() {
		System.out.println("Construction TableHifiveInit");
	}

	// *******************************************
	@Create
	// @Begin(join=true)
	public void create() {
		log.info("HiFive Creation:#0");
		loadHiFive();
		selectStrikeColumnModelList = new ArrayList<DynamicColumnModel>();
		strikeColumnList = HifiveStrike.getColumnList();
		selectStrikeColumnList = HifiveStrike.getColumnList();
		selectNoStrikeColumnList = HifiveStrike.getNumberColumnList();
		selectStStrikeColumnList = HifiveStrike.getStringColumnList();
		strikeColumnModelList = HifiveStrike.getColumnModelList();

		for(DynamicColumnModel aa : strikeColumnModelList){
//			strikeColumnModelList.add(aa);
			if(aa.isInitialize()){
				selectStrikeColumnModelList.add(aa);
			}
		}
		
		for(String aa : strikeColumnList){
			strikeColumnMap.put(aa, aa+"1");
		}
		
		selectConditionColumnList = HifiveCondition.getColumnList();
		selectCouponColumnList = HifiveMonthCoupon.getColumnList();
		changeStirkeColumn();
	}

	public void loadHiFive() {
		session.setFlushMode(FlushMode.MANUAL);

		hifiveList = session.createCriteria(Hifive.class).list();

		// Criteria crit = session.createCriteria(Hifive.class);
		// hifiveList = crit.list();

		String qr2 = "from MarketVariableNew a where a.underlyingYN = 'Y' order by a.mvType, a.mvId";
		// underlyingList = session.createQuery(qr2).list();

		for (MarketVariableNew aa : underlyingList) {
			underlyingIdList.add(aa.getMvId());
		}
	}

	public void addHifive() {
		int cnt = 1;
		String tempProdName;
		// log.info("Select:00 #0", selectHifive);
		String tempProdId = selectHifive.getProdId() + "_" + cnt;
		List<String> prodIdList = new ArrayList<String>();

		for (Hifive aa : hifiveList) {
			// log.info("In the ProdIdList:#0, #1", aa.getProdId());
			prodIdList.add(aa.getProdId());
		}

		while (prodIdList.contains(tempProdId)) {
			cnt = cnt + 1;
			tempProdId = selectHifive.getProdId() + "_" + cnt;
		}
		tempProdName = selectHifive.getProdName() + "_" + cnt;

		Hifive temp1 = (Hifive) selectHifive.clone();
		temp1.setVirtual(true);
		temp1.setProdId(tempProdId);
		temp1.setProdName(tempProdName);
		// temp1.setCounterparty(null);

		List<HifiveUnderlying> tempUList = new ArrayList<HifiveUnderlying>();
		for (HifiveUnderlying aa : selectHifive.getHifiveUnderlyings()) {
			HifiveUnderlying tempUnder = new HifiveUnderlying();
			tempUnder = (HifiveUnderlying) aa.clone();
			tempUnder.setId(new HifiveUnderlyingId(tempProdId, aa.getUnderlyingId()));

			// tempUnder.setProdId(tempProdId);
			// tempUnder.setUnderlyingId(aa.getUnderlyingId());
			tempUList.add(tempUnder);
		}
		if (tempUList.size() == 0) {
			HifiveUnderlying tempU = new HifiveUnderlying();
			tempU.setId(new HifiveUnderlyingId(tempProdId, messages.get("inputUnderlying")));

			// tempU.setProdId(tempProdId);
			tempU.setUnderlyingId(messages.get("inputUnderlying"));
			tempU.setUnderType(MarketVariableType.STOCK);
			tempUList.add(tempU);
		}

		List<HifiveStrike> tempSList = new ArrayList<HifiveStrike>();
		for (HifiveStrike aa : selectHifive.getHifiveStrikes()) {
			HifiveStrike tempStrike = new HifiveStrike();
			tempStrike = (HifiveStrike) aa.clone();
			tempStrike.setId(new HifiveStrikeId(tempProdId, aa.getId().getStrikeSerial()));
			tempSList.add(tempStrike);
		}

		if (tempSList.size() == 0) {
			HifiveStrike tempS = new HifiveStrike();
			tempS.setId(new HifiveStrikeId(tempProdId, 1));
			tempSList.add(tempS);
		}

		List<PricingMaster> tempPrList = new ArrayList<PricingMaster>();
		List<PricingUnderlyings> tempPrUnderAllList = new ArrayList<PricingUnderlyings>();

		log.info("In Add Hifive11 : #0", selectHifive.getProdId());
		for (PricingMaster aa : selectHifive.getPriceSetting()) {
//			log.info("In Add Hifive111 : #0", selectHifive.getProdId());
			PricingMaster tempPrSetting = new PricingMaster();

			tempPrSetting = (PricingMaster) aa.clone();
			tempPrSetting.setId(new PricingMasterId(aa.getId().getPricingObjId(), tempProdId));

			PricingUnderlyings tempPrUnderSetting = new PricingUnderlyings();
			List<PricingUnderlyings> tempPrUnderList = new ArrayList<PricingUnderlyings>();

			for (PricingUnderlyings bb : aa.getPrUnderlyingList()) {
				tempPrUnderSetting = (PricingUnderlyings) bb.clone();
				tempPrUnderSetting.setId(new PricingUnderlyingsId(bb.getId().getPricingObjId(), tempProdId, bb.getId()
						.getUnderlyingId()));
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

		// log.info("AAAA");
		// session.get(Hifive.class, "HIFIVE_03");
		// log.info("BBB");

		hifiveList.add(temp1);

		if (filterHifiveList != null) {
			filterHifiveList.add(temp1);
		}

		/*
		 * underlyingMap.put(MarketVariableType.STOCK, underlyingStocks);
		 * List<String> rst = new ArrayList<String>(); for(Stock aa :
		 * underlyingStocks){ rst.add(aa.getMvId()); }
		 * underlyingIdMap.put(MarketVariableType.STOCK, rst);
		 */

		// facesMessages.add("#{messages.insertHifive}");
		// facesMessages.add("AAA",messages.get("insertHifive"));
		// facesMessages.add(Severity.ERROR, messages.get("insertHifive"));
		// facesMessages.addFromResourceBundle(Severity.INFO,"insertHifive");
		// facesMessages.addFromResourceBundle("deleteHifive",
		// selectHifive.getProdId());
		statusMessages.addFromResourceBundle("insertHifive.successMessages");

		selectHifive = temp1;
//		log.info("In Add Hifive #0", selectHifive.getProdId());

	}

	public void removeHifive() {
		// save();
		Transaction tx = session.beginTransaction();
		if (selectHifive.isVirtual()) {
			session.delete(selectHifive);

			// selectHifive.getHifiveStrikes().clear();
			// selectHifive.getHifiveUnderlyings().clear();
			hifiveList.remove(selectHifive);
			if (filterHifiveList != null) {
				filterHifiveList.remove(selectHifive);
			}
			statusMessages.addFromResourceBundle("deleteVirtualHifive");
		} else {
			statusMessages.addFromResourceBundle("deleteRealHifive");
		}
		tx.commit();
	}

	public void addUnderlying() {
		if (selectHifive.isVirtual()) {
			HifiveUnderlying temp = new HifiveUnderlying();
			HifiveUnderlyingId tempId = new HifiveUnderlyingId();

			tempId.setProdId(selectHifive.getProdId());
			tempId.setUnderlyingId(messages.get("inputUnderlying"));
			temp.setId(tempId);

			temp.setUnderType(MarketVariableType.STOCK);
			// temp.setProdId(selectHifive.getProdId());
			temp.setUnderlyingId(messages.get("inputUnderlying"));
			// allUnderlyingIdList =
			// underlyingIdMap.get(MarketVariableType.STOCK);

			selectHifive.getHifiveUnderlyings().add(temp);
			statusMessages.addFromResourceBundle("insertHifiveStrike.success");
		} else {
			statusMessages.addFromResourceBundle("insertHifive.forRealProduct");
		}
	}

	public void removeUnderlying(HifiveUnderlying selection) {
		log.info("SelectionUnderlying:#0", selection);
		if (selectHifive.isVirtual()) {
			if (selectHifive.getHifiveUnderlyings().size() > 1) {

				selectHifive.getHifiveUnderlyings().remove(selection);
				session.delete(selection);
				session.flush();
				statusMessages.addFromResourceBundle("removeHifiveUnderlying.success");
			} else {
				statusMessages.addFromResourceBundle("removeHifiveUnderlying.forMinimumSize");
			}
		} else {
			statusMessages.addFromResourceBundle("removeHifiveUnderlying.forRealProduct");
		}
	}

	public void addStrike() {
		if (selectHifive.isVirtual()) {
			HifiveStrike temp = new HifiveStrike();
			temp = (HifiveStrike) selectHifive.getHifiveStrikes().get(0).clone();
			HifiveStrikeId tempId = new HifiveStrikeId();
			tempId.setProdId(selectHifive.getProdId());
			tempId.setStrikeSerial(selectHifive.getHifiveStrikes().size() + 1);
			temp.setId(tempId);
			selectHifive.getHifiveStrikes().add(temp);
			statusMessages.addFromResourceBundle("insertHifiveStrike.success");
		} else {
			statusMessages.addFromResourceBundle("insertHifive.forRealProduct");
		}
	}

	public void removeStrike(HifiveStrike selection) {
		log.info("SelectionStrike:#0", selection);
		log.info("SelectionStrike:#0", selection.getId().getProdId());
		String prodId = selectHifive.getProdId();
		long serial = 1;
		if (selectHifive.isVirtual()) {
			if (selectHifive.getHifiveStrikes().size() > 1) {

				selectHifive.getHifiveStrikes().remove(selection);
				session.delete(selection);
				session.flush();

				// List<HifiveStrike> tempList =new ArrayList<HifiveStrike>();
				// for(HifiveStrike aa : selectHifive.getHifiveStrikes()){
				// // selectHifive.getHifiveStrikes().remove(aa);
				// session.delete(aa);
				//
				// HifiveStrike temp =(HifiveStrike) aa.clone();
				// temp.setId(new HifiveStrikeId(prodId, serial));
				// tempList.add(temp);
				// serial =serial +1;
				//
				// // selectHifive.getHifiveStrikes().add(temp);
				// // session.saveOrUpdate(selectHifive);
				// // session.saveOrUpdate(temp);
				// }
				// session.flush();
				// selectHifive.getHifiveStrikes().clear();
				// selectHifive.setHifiveStrikes(tempList);

				session.saveOrUpdate(selectHifive);
				session.flush();
				statusMessages.addFromResourceBundle("removeHifiveStrike.success");
			} else {
				statusMessages.addFromResourceBundle("removeHifive.forMinmumSize");
			}
		} else {
			statusMessages.addFromResourceBundle("removeHifive.forRealProduct");
		}
	}

	public void resetSelection() {
		// log.info("FilterEvent:{},{}", filterEvent.toString(),
		// filterHifiveList);
		if (filterHifiveList != null) {
			if (!filterHifiveList.isEmpty() && (selectHifive == null || !filterHifiveList.contains(selectHifive))) {
				selectHifive = filterHifiveList.get(0);
			}
		} else {
			if (!hifiveList.isEmpty() && (selectHifive == null || !hifiveList.contains(selectHifive))) {
				selectHifive = hifiveList.get(0);
			}
		}
	}

	public List<String> completeCntparty(String auto) {
		List<String> rst = new ArrayList<String>();
		for (String aa : getAllCntPartyIdList()) {
			if (aa.toUpperCase().contains(auto.toUpperCase())) {
				rst.add(aa);
			}
		}
		return rst;
	}

	public List<String> completeUnderlying(String auto) {
		List<String> rst = new ArrayList<String>();
		log.info("SizeUnder :#0", getAllUnderlyingIdList().size());
		for (String aa : getAllUnderlyingIdList()) {
			if (aa.toUpperCase().contains(auto.toUpperCase())) {
				rst.add(aa);
			}
		}
		return rst;
	}

	public void resetCntparty(SelectEvent event) {
		Hifive hifive = (Hifive) ((DataTable) event.getComponent().getParent().getParent().getParent()).getRowData();
		// log.info("Reset : #0,#1", event.getComponent().getParent().getId(),
		// hifive.getProdId());
		selectHifive = hifive;

		for (Counterparty aa : counterpartyList.getResultList()) {
			if (aa.getCounterpartyId().equals(event.getObject().toString())) {
				log.info("Reset111 : #0,#1", aa.getCounterpartyId(), selectHifive.getProdId());
				selectHifive.setCounterparty(aa);
			}
		}
	}

	public void resetUnderlying(HifiveUnderlying underlying) {
		// HifiveUnderlying temp =(HifiveUnderlying) underlying.clone();

		for (MarketVariableNew mvNew : underlyingList) {
			log.info("RestU: #0,#1", mvNew.getMvId(), underlying.getUnderlyingId());
			if (mvNew.getMvId().equals(underlying.getUnderlyingId())) {
				log.info("RestUU: #0,#1", mvNew.getMvId(), underlying.getUnderlyingId());
				underlying.getId().setUnderlyingId(mvNew.getMvId());
				underlying.setUnderlying(mvNew);
				break;
			}
		}

		for (PricingMaster aa : selectHifive.getPriceSetting()) {
			PricingUnderlyings tempPrUnder = new PricingUnderlyings();
			tempPrUnder.setId(new PricingUnderlyingsId(aa.getId().getPricingObjId(), selectHifive.getProdId(),
					underlying.getUnderlyingId()));
			aa.getPrUnderlyingList().add(tempPrUnder);
			selectHifive.getPrUnderSetting().add(tempPrUnder);
			log.info("ResetU:#0, #1,#2", tempPrUnder.getId().getPricingObjId(), tempPrUnder.getId().getProdId(),
					tempPrUnder.getId().getUnderlyingId());
		}
	}



	public List<MarketVariableNew> getUnderlyingList() {
		return underlyingList;
	}

	public void setUnderlyingList(List<MarketVariableNew> underlyingList) {
		this.underlyingList = underlyingList;
	}

	

	public List<String> getUnderlyingIdList() {
		return underlyingIdList;
	}

	public void setUnderlyingIdList(List<String> underlyingIdList) {
		this.underlyingIdList = underlyingIdList;
	}

	/*
	 * private Map<MarketVariableType, List<String>> underlyingMap; public
	 * Map<MarketVariableType, List<String>> getUnderlyingMap() { return
	 * underlyingMap; } public void setUnderlyingMap(Map<MarketVariableType,
	 * List<String>> underlyingMap) { this.underlyingMap = underlyingMap; }
	 */

	/*
	 * public void loadUnderlying(MarketVariableType uType){ List<String> rst =
	 * new ArrayList<String>(); if(underlyingIdMap.get(uType)==null ||
	 * underlyingIdMap.get(uType).isEmpty() ){ Criteria crit =
	 * session.createCriteria(uType.getClassPath()); //
	 * crit.add(Restrictions.eq("useAsUnderlying", true));
	 * 
	 * List<? extends MarketVariableJoin> mvList = crit.list();
	 * for(MarketVariableJoin aa : mvList){ rst.add(aa.getMvId()); }
	 * underlyingIdMap.put(uType, rst); underlyingMap.put(uType, mvList); }else{
	 * rst = underlyingIdMap.get(uType); } allUnderlyingIdList= rst; }
	 */

	// public void onRowEdit(RowEditEvent evt){
	// statusMessages.add("Row Edit : #0",
	// ((Hifive)evt.getObject()).getProdId());
	// }

	public List<Counterparty> completeCpartyObj(String auto) {
		log.info("ObjectChange :#0", auto);
		List<Counterparty> rst = new ArrayList<Counterparty>();
		for (Counterparty aa : counterpartyList.getResultList()) {
			if (aa.getCounterpartyId().toUpperCase().contains(auto.toUpperCase())) {
				rst.add(aa);
			}
		}
		return rst;
	}

	

	public Counterparty getXxcounterparty() {
		return xxcounterparty;
	}

	public void setXxcounterparty(Counterparty xxcounterparty) {
		this.xxcounterparty = xxcounterparty;
	}

	public void save() {
		boolean flag = true;
		Transaction tx = session.beginTransaction();
		log.info("in the save Dirty : #0,#1", session.isDirty(), session.getFlushMode());

		try {
			for (Hifive aa : hifiveList) {
				if (aa.isVirtual()) {
					flag = true;
					for (HifiveUnderlying bb : aa.getHifiveUnderlyings()) {
						if (bb.getUnderlyingId().equals(messages.get("inputUnderlying"))) {
							flag = false;
							statusMessages.addFromResourceBundle(Severity.ERROR, "error.setUnderlyingAsset",
									aa.getProdId());
							break;
						}
					}
					if (flag) {
						session.saveOrUpdate(aa);
						log.info("in the save :#0,#1", aa.getProdId(), aa.getHifiveUnderlyings().get(0)
								.getUnderlyingId());

					}
				}
			}
			log.info("SAVE:");
			session.flush();
			tx.commit();
			log.info("SAVE1:");
		} catch (RuntimeException e) {
			try {
				tx.rollback();
			} catch (RuntimeErrorException ee) {

			}
			throw e;
		}
	}

	public void onSelectHifive() {
		log.info("select: #0,#1,#2", selectHifive.getProdName(), selectHifive.getHifiveStrikes().size()
				,selectHifive.getHifiveConditionList().size());
		selectUnder=null;
		selectStrikeEntry =null;
		selectConditionEntry =null;
		selectCouponEntry =null;
		loadStrikeMap(selectHifive.getHifiveStrikes());
		loadConditionMap(selectHifive.getHifiveConditionList());
		loadCouponMap(selectHifive.getHifiveCouponList());
	}

	public List<String> getSelectStrikeColumnList() {
		return selectStrikeColumnList;
	}

	public void setSelectStrikeColumnList(List<String> selectStrikeColumnList) {
		this.selectStrikeColumnList = selectStrikeColumnList;
	}


	public List<Map.Entry<HifiveStrike, Map<String, Object>>> getStrikeList() {
		return strikeList;
	}

	public void setStrikeList(List<Map.Entry<HifiveStrike, Map<String, Object>>> strikeList) {
		this.strikeList = strikeList;
	}

	public Map<HifiveStrike, Map<String, Object>> getStrikeMap() {
		return strikeMap;
	}

	public void setStrikeMap(Map<HifiveStrike, Map<String, Object>> strikeMap) {
		this.strikeMap = strikeMap;
	}

	public List<String> getStrikeColumnList() {
		return strikeColumnList;
	}

	public void setStrikeColumnList(List<String> strikeColumnList) {
		this.strikeColumnList = strikeColumnList;
	}

	public void changeStirkeColumn(){
		strikeMap.clear();
		strikeList.clear();
		selectNoStrikeColumnList.clear();
		selectStStrikeColumnList.clear();
//		for(Map.Entry<String, String> aa : selectStrikeColumnMap.entrySet()){
//			if(aa.getValue().equals("String")){
//				selectStStrikeColumnList.add(aa.getKey());
//			}
//			if(aa.getValue().equals("Number")){
//				selectNoStrikeColumnList.add(aa.getKey());
//			}
//		}
		log.info("In the column selection1: #0", selectStrikeColumnModelList.size());
		Collections.sort(selectStrikeColumnModelList);
		for(DynamicColumnModel aa : selectStrikeColumnModelList){
			if(aa.getHeaderType().equals("STRING")){
				selectStStrikeColumnList.add(aa.getHeader());
			}
			if(aa.getHeaderType().equals("NUMBER")){
				selectNoStrikeColumnList.add(aa.getHeader());
			}
		}

		if(selectHifive!= null){
			loadStrikeMap(selectHifive.getHifiveStrikes());
		}
	}
//	public void changeConditionColumn(){
//		strikeMap.clear();
//		strikeList.clear();
//		
//		setStrikeMap(selectHifive.getHifiveStrikes());
//	}
	
	private void loadStrikeMap(List<HifiveStrike> selectStrikeList) {
		strikeMap.clear();
		strikeList.clear();
		Collections.sort(selectStrikeList);
		for (HifiveStrike aa : selectStrikeList) {
//			log.info("Serial: #0", aa.getId().getStrikeSerial());
			loadStrikeMap(aa);
		}
		strikeList.addAll(strikeMap.entrySet());
	}

	private void loadStrikeMap(HifiveStrike selectStrike) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		for (Method mtd : HifiveStrike.class.getDeclaredMethods()) {
			if(strikeColumnList.contains(mtd.getName())){
				try {
					tempMap.put(mtd.getName(), mtd.invoke(selectStrike));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}	
			}
		}
		strikeMap.put(selectStrike, tempMap);
	}

	private void loadConditionMap(List<HifiveCondition> selectConditionsList) {
		conditionMap.clear();
		conditionList.clear();
		Collections.sort(selectConditionsList);
		for (HifiveCondition aa : selectConditionsList) {
			loadConditionMap(aa);
		}
		conditionList.addAll(conditionMap.entrySet());
	}
	private void loadConditionMap(HifiveCondition selectCondition) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		for (Method mtd : HifiveCondition.class.getDeclaredMethods()) {
			if(selectConditionColumnList.contains(mtd.getName())){
				try {
					tempMap.put(mtd.getName(), mtd.invoke(selectCondition));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}	
			}
		}
		conditionMap.put(selectCondition, tempMap);
	}
	
	private void loadCouponMap(List<HifiveMonthCoupon> selectCouponList) {
		couponMap.clear();
		couponList.clear();
		Collections.sort(selectCouponList);
		for (HifiveMonthCoupon aa : selectCouponList) {
			loadCouponMap(aa);
		}
		couponList.addAll(couponMap.entrySet());
	}
	
	private void loadCouponMap(HifiveMonthCoupon selectCoupon) {
		Map<String, Object> tempMap = new HashMap<String, Object>();
		for (Method mtd : HifiveMonthCoupon.class.getDeclaredMethods()) {
			if(selectCouponColumnList.contains(mtd.getName())){
				try {
					tempMap.put(mtd.getName(), mtd.invoke(selectCoupon));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}	
			}
		}
		couponMap.put(selectCoupon, tempMap);
	}
}
