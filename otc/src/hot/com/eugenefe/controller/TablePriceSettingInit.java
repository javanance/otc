package com.eugenefe.controller;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;

import com.eugenefe.entity.PricingMaster;
//import org.jboss.seam.framework.Query;

@Name("tablePriceSettingInit")
@Scope(ScopeType.CONVERSATION)
public class TablePriceSettingInit {
	@Logger
	private Log log;
	@In
	private Session session;
	
//	@In
//	private FacesMessages facesMessages;
	@In
	private StatusMessages statusMessages;
	@In
	private Map<String, String> messages;
	
	
	private List<PricingMaster> priceSettingList ;
	private List<PricingMaster> filterPrSettingList ;
	public PricingMaster selectPrSetting;
	
	public List<PricingMaster> getPriceSettingList() {
		return priceSettingList;
	}
	public void setPriceSettingList(List<PricingMaster> priceSettingList) {
		this.priceSettingList = priceSettingList;
	}
	public List<PricingMaster> getFilterPrSettingList() {
		return filterPrSettingList;
	}
	public void setFilterPrSettingList(List<PricingMaster> filterPrSettingList) {
		this.filterPrSettingList = filterPrSettingList;
	}
	public PricingMaster getSelectPrSetting() {
		return selectPrSetting;
	}
	public void setSelectPrSetting(PricingMaster selectPrSetting) {
		this.selectPrSetting = selectPrSetting;
	}

	
	
	public TablePriceSettingInit() {
		System.out.println("Construction TablePriceSettingInit");
	}

// *******************************************
	@Create
//	@Begin(join=true)
	public void create() {
		loadPriceSetting();
	}
	
	public void  loadPriceSetting(){
		priceSettingList = session.createQuery("from PricingMaster ").list();
		
	}
	
	public void save(){
		
	}
	

}
