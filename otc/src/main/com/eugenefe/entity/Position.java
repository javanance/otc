package com.eugenefe.entity;

// Generated Apr 16, 2013 7:33:55 PM by Hibernate Tools 4.0.0

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.eugenefe.enums.raw.EElsOptionType;
import com.eugenefe.enums.raw.EElsTrDetailType;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;
import com.eugenefe.util.DynamicColumnModel;

/**
 * Position generated by hbm2java
 */
@Entity
@Table(name = "POSITION", schema="TAKION99")
@AnnoNavigationFilter
public class Position implements IPortfolio, Serializable {
	private String posId;
	private String posName;
	private String posSide;
	private MarketVariable product;
	private Counterparty counterparty;
	private Bizunit fund;
	private EElsTrDetailType txType;
	private EElsOptionType elsOptType;
	private String currency;
	private String settleCurrency;
	private String initTxDate;
	private String lastTxDate;
	private BigDecimal initTxPrice;
	private BigDecimal initFeeAmt;
	private BigDecimal initTxAmt;
	private BigDecimal initTxPar;
	private BigDecimal initHoldingQty;
	private BigDecimal holdingQty;
	private BigDecimal posAmt;
	
	private List<PositionProperty> propertyList = new ArrayList<PositionProperty>();
	private Map<HierarchyProperty, String> propMap = new HashMap<HierarchyProperty, String>();
	
	public Position() {
	}

	public Position(String posId) {
		this.posId = posId;
	}

	public Position(String posId, String posName, String initTxDate, String lastTxDate, BigDecimal initTxPrice,
			BigDecimal initFeeAmt, BigDecimal initTxAmt, BigDecimal holdingQty) {
		this.posId = posId;
		this.posName = posName;
		this.initTxDate = initTxDate;
		this.lastTxDate = lastTxDate;
		this.initTxPrice = initTxPrice;
		this.initFeeAmt = initFeeAmt;
		this.initTxAmt = initTxAmt;
		this.holdingQty = holdingQty;
	}

	@Id
	@Column(name = "POS_ID", nullable = false, length = 50)
	@AnnoMethodTree(order=10, init=true, aggregatable=true)
	public String getPosId() {
		return this.posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

//	@Transient
	@Column(name = "POS_NAME", length = 50)
	@AnnoMethodTree(order=11, init=true, aggregatable=true)
	public String getPosName() {
		return this.posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}
//	@Transient
	@Column(name = "POS_SIDE", length = 20)
	@AnnoMethodTree(order=12, init=true, aggregatable=true)
	public String getPosSide() {
		return posSide;
	}

	public void setPosSide(String posSide) {
		this.posSide = posSide;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROD_ID")
	@AnnoMethodTree(order=20, init=false, aggregatable=true)
	public MarketVariable getProduct() {
//		System.out.println("In the GetProduct");
		return product;
	}

	public void setProduct(MarketVariable product) {
		this.product = product;
	}
	
//	@Transient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTERPARTY_ID")
	@AnnoMethodTree(order=30, init=false, aggregatable=true)
	public Counterparty getCounterparty() {
		return counterparty;
	}

	public void setCounterparty(Counterparty counterparty) {
		this.counterparty = counterparty;
	}


	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUND_ID")
	@AnnoMethodTree(order=40, init=false, aggregatable=true)
	public Bizunit getFund() {
		return fund;
	}

	public void setFund(Bizunit fund) {
		this.fund = fund;
	}

	@Column(name = "TX_TYPE", length = 20)
	@Enumerated(EnumType.STRING)
	@AnnoMethodTree(order=45, init=true, aggregatable=true)
	public EElsTrDetailType getTxType() {
		return txType;
	}

	public void setTxType(EElsTrDetailType txType) {
		this.txType = txType;
	}
	
	@Column(name = "ELS_OPT_TYPE", length = 20)
	@Enumerated(EnumType.STRING)
	public EElsOptionType getElsOptType() {
		return elsOptType;
	}

	public void setElsOptType(EElsOptionType elsOptType) {
		this.elsOptType = elsOptType;
	}

//		@Transient
	@Column(name = "CURR_CD", length = 3)
	@AnnoMethodTree(order=46, init=true, aggregatable=true)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Column(name = "STL_CURR_CD", length = 3)
	public String getSettleCurrency() {
		return settleCurrency;
	}

	public void setSettleCurrency(String settleCurrency) {
		this.settleCurrency = settleCurrency;
	}

//	@Transient
	@Column(name = "INIT_TX_DATE", length = 8)
	@AnnoMethodTree(order=50, init=true, aggregatable=true)
	public String getInitTxDate() {
		return this.initTxDate;
	}

	public void setInitTxDate(String initTxDate) {
		this.initTxDate = initTxDate;
	}

	
//	@Transient
	@Column(name = "LAST_TX_DATE", length = 8)
	@AnnoMethodTree(order=51, init=true, aggregatable=true)
	public String getLastTxDate() {
		return this.lastTxDate;
	}

	public void setLastTxDate(String lastTxDate) {
		this.lastTxDate = lastTxDate;
	}
//	@Transient
	@Column(name = "INIT_TX_PRICE", scale = 4)
	@AnnoMethodTree(order=52, init=true)
	public BigDecimal getInitTxPrice() {
		return this.initTxPrice;
	}

	public void setInitTxPrice(BigDecimal initTxPrice) {
		this.initTxPrice = initTxPrice;
	}
//	@Transient
	@Column(name = "INIT_FEE_AMT", scale = 4)
	@AnnoMethodTree(order=53, init=true)
	public BigDecimal getInitFeeAmt() {
		return this.initFeeAmt;
	}

	public void setInitFeeAmt(BigDecimal initFeeAmt) {
		this.initFeeAmt = initFeeAmt;
	}
//	@Transient
	@Column(name = "INIT_TX_AMT", scale = 4)
	@AnnoMethodTree(order=54, init=true)
	public BigDecimal getInitTxAmt() {
		return this.initTxAmt;
	}

	public void setInitTxAmt(BigDecimal initTxAmt) {
		this.initTxAmt = initTxAmt;
	}
	
	@Column(name = "INIT_TX_PAR", scale = 4)
	public BigDecimal getInitTxPar() {
		return initTxPar;
	}

	public void setInitTxPar(BigDecimal initTxPar) {
		this.initTxPar = initTxPar;
	}
	@Column(name = "INIT_HOLDING_QTY", scale = 8)
	public BigDecimal getInitHoldingQty() {
		return initHoldingQty;
	}

	public void setInitHoldingQty(BigDecimal initHoldingQty) {
		this.initHoldingQty = initHoldingQty;
	}

//	@Transient
	@Column(name = "HOLDING_QTY", scale = 8)
	@AnnoMethodTree(order=60, init=true)
	public BigDecimal getHoldingQty() {
		return this.holdingQty;
	}

	public void setHoldingQty(BigDecimal holdingQty) {
		this.holdingQty = holdingQty;
	}
	@Transient
//	@Column(name = "POS_AMT", scale = 4)
	@AnnoMethodTree(order=61, init=true, aggregatable=true)	
	public BigDecimal getPosAmt() {
		return posAmt;
	}

	public void setPosAmt(BigDecimal posAmt) {
		this.posAmt = posAmt;
	}


	@Transient
//	@OneToMany(mappedBy="position")
	public List<PositionProperty> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<PositionProperty> propertyList) {
		this.propertyList = propertyList;
	}

	
//	@ElementCollection
//	@CollectionTable(name="ETF_HIS", joinColumns= @JoinColumn(name="ETF_ID"))
//	@MapKeyColumn(name="BSSD")
	

	@ElementCollection
	@MapKeyJoinColumn(name="PROP_ID")
	@JoinTable(name="POSITION_PROP"
				, joinColumns=@JoinColumn(name="POS_ID")
//				, inverseJoinColumns=@JoinColumn(name="PORT_WEIGHT")
	)
	@Column(name="PROP_VALUE")
	public Map<HierarchyProperty, String> getPropMap() {
		return propMap;
	}

	public void setPropMap(Map<HierarchyProperty, String> propMap) {
		this.propMap = propMap;
	}

	//**************************************	
	@Override
	@Transient
	public String getStringId() {
		return getPosId();
	}

	@Override
	@Transient
	public String getName() {
		return getPosName();
	}

	@Override
	@Transient
	public void add(IPortfolio port) {
		
	}

	@Override
	@Transient
	public void remove(IPortfolio port) {
		
	}

	@Override
	@Transient
	public List<IPortfolio> getChildren() {
		return new ArrayList<IPortfolio>();
	}

	@Override
	@Transient
	public IPortfolio getChildren(String portId) {
		return null;
	}

	@Override
	@Transient
	public double getPresValue() {
		return 0;
	}

	@Override
	@Transient
	public double getCalcuatedPv() {
		return 0;
	}

	@Override
	@Transient
	public double getDailyReturn() {
		return 0;
	}
	
	private static List<DynamicColumnModel> columnModelList = new ArrayList<DynamicColumnModel>();
	private static Map<String, List<DynamicColumnModel>> columnModelMap 
					= new HashMap<String, List<DynamicColumnModel>>();
	private static Map<String, List<String>> columnMap= new HashMap<String, List<String>>();
	
	@Transient
	public  static List<DynamicColumnModel> getColumnModelList(){
		if(columnModelList.isEmpty()){
			columnModelList.add(new DynamicColumnModel("getFundOrgId","FUND","STRING", true, 21));
			columnModelList.add(new DynamicColumnModel("getFundParentOrgId","FUND","STRING", true, 22));
			columnModelList.add(new DynamicColumnModel("getFundOrgRole","FUND","STRING", true, 23));
			columnModelList.add(new DynamicColumnModel("getFundOrgType","FUND","STRING", true, 24));
			
			columnModelList.add(new DynamicColumnModel("getCounterpartyId","COUNTERPARTY","STRING", true, 31));
			columnModelList.add(new DynamicColumnModel("getCounterpartyCreditRating","COUNTERPARTY","STRING", true, 32));
			
			columnModelList.add(new DynamicColumnModel("getProductId","PRODUCT","STRING", true, 41));
			columnModelList.add(new DynamicColumnModel("getProductType","PRODUCT","STRING", true, 42));
		}
		return columnModelList;
	}	
	
	
	@Transient
	public  static Map<String, List<DynamicColumnModel>> getColumnModelMap(){
		if(columnModelMap.isEmpty()){
			List<DynamicColumnModel> fundList = new ArrayList<DynamicColumnModel>();
			List<DynamicColumnModel> cpartList = new ArrayList<DynamicColumnModel>();
			List<DynamicColumnModel> prodList = new ArrayList<DynamicColumnModel>();
			List<DynamicColumnModel> posList = new ArrayList<DynamicColumnModel>();
			
			posList.add(new DynamicColumnModel("getTxType","POSITION","STRING", true, 11));
			posList.add(new DynamicColumnModel("getElsOptType","POSITION","STRING", true, 12));
			posList.add(new DynamicColumnModel("getSettleCurrency","POSITION","STRING", true, 12));
			
			fundList.add(new DynamicColumnModel("getFundOrgId","FUND","STRING", true, 21));
			fundList.add(new DynamicColumnModel("getFundParentOrgId","FUND","STRING", true, 22));
			fundList.add(new DynamicColumnModel("getFundOrgRole","FUND","STRING", true, 23));
			fundList.add(new DynamicColumnModel("getFundOrgType","FUND","STRING", true, 24));
			
			cpartList.add(new DynamicColumnModel("getCounterpartyId","COUNTERPARTY","STRING", true, 31));
			cpartList.add(new DynamicColumnModel("getCounterpartyCreditRating","COUNTERPARTY","STRING", true, 32));
			
			prodList.add(new DynamicColumnModel("getProductId","PRODUCT","STRING", true, 41));
			prodList.add(new DynamicColumnModel("getProductType","PRODUCT","STRING", true, 42));
			
			columnModelMap.put("POSITION", posList);
			columnModelMap.put("FUND", fundList);
			columnModelMap.put("COUNTERPARTY", cpartList);
			columnModelMap.put("PRODUCT", prodList);
		}
		return columnModelMap;
	}
	
	@Transient
	public  static Map<String, List<String>> getColumnMap(){
		if(columnMap.isEmpty()){
			List<String> fundList = new ArrayList<String>();
			List<String> cpartList = new ArrayList<String>();
			List<String> prodList = new ArrayList<String>();
			List<String> posList = new ArrayList<String>();
			
			posList.add("getTxType");
			posList.add("getElsOptType");
			posList.add("getSettleCurrency");
			
			fundList.add("getFundOrgId");
			fundList.add("getFundParentOrgId");
			fundList.add("getFundOrgRole");
			fundList.add("getFundOrgType");
			
			cpartList.add("getCounterpartyId");
			cpartList.add("getCounterpartyCreditRating");
			
			prodList.add("getProductId");
			prodList.add("getProductType");
			
			columnMap.put("POSITION", posList);
			columnMap.put("FUND", fundList);
			columnMap.put("COUNTERPARTY", cpartList);
			columnMap.put("PRODUCT", prodList);
		}
		return columnMap;
	}
	@Transient
	public String getFundOrgId(){
		if(getFund()== null){
			return "null";
		}
		return getFund().getOrgId();
	}
	@Transient
	public String getFundParentOrgId(){
		if(getFund()== null){
			return "null";
		}
		return getFund().getParentOrgId();
	}
	@Transient
	public String getFundOrgRole(){
		if(getFund()== null){
			return "null";
		}
		return getFund().getOrgRole();
	}
	@Transient
	public String getFundOrgType(){
		if(getFund()== null){
			return "null";
		}
		return getFund().getOrgType();
	}
	
	@Transient
	public String getCounterpartyId(){
		if(getCounterparty()== null){
			return "null";
		}
		return getCounterparty().getCounterpartyId();
	}
	@Transient
	public String getCounterpartyCreditRating(){
		if(getCounterparty()== null){
			return "null";
		}
		return getCounterparty().getCreditRatingCd();
	}
	
	@Transient
	public String getProductId(){
		if(getProduct()== null){
			return "null";
		}
		return getProduct().getMvId();
	}
	@Transient
	public String getProductType(){
		if(getProduct()== null){
			return "null";
		}
		return getProduct().getMvType().getType();
	}
	@Transient
	public int getProductUnderlyingSize(){
		return 0;
	}	
	@Transient
	public String getProductUnderlyingType(){
		return null;
	}
	@Transient
	public String getProductUnderlyingId(){
		switch(getProduct().getMvType()) {
			case STOCK: 
					break;
			case OPTION:
					break;
			case ELS:
			default:
				break;
		}
		return null;
	}
}
