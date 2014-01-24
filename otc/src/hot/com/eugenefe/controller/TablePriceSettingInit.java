package com.eugenefe.controller;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.jboss.seam.annotations.Scope;
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
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.MarketVariableJoin;
import com.eugenefe.entity.MarketVariableNew;
import com.eugenefe.entity.PricingMaster;
import com.eugenefe.entity.Stock;
//import org.jboss.seam.framework.Query;
import com.eugenefe.session.CounterpartyList;
import com.eugenefe.session.StockList;
import com.eugenefe.util.MarketVariableType;
import com.sun.corba.se.impl.orbutil.closure.Future;

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
