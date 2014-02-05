package com.eugenefe.entity;

// Generated Apr 10, 2013 4:09:22 PM by Hibernate Tools 3.4.0.CR1

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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.mapping.Array;

import com.eugenefe.entity.component.PriceData;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;
import com.eugenefe.util.EColumnType;

/**
 * Stock generated by hbm2java
 */
@Entity
@Table(name = "STOCK")
@PrimaryKeyJoinColumn(name="MV_ID")
@AnnoNavigationFilter
public class Stock extends MarketVariableJoin implements java.io.Serializable{

//	private String stockId;
//	private MarketVariable marketVariable;
	private String stockType;
	private String exchangeId;
	private Character isListed;
	private Long numShares;
	private String issuerId;
	private Long faceAmt;
	private boolean useAsUnderlying;
	private String sourceTable;
	private String userName;
	private String lastUpdated;
	private BigDecimal versionNo;
//	private Set<StockIndexDetail> stockIndexDetails = new HashSet<StockIndexDetail>(0);

	private List<StockHis> stockHis = new ArrayList<StockHis>();
	
	private Map<String, PriceData> priceMap = new HashMap<String, PriceData>();

	public Stock() {
	}

//	public Stock(MarketVariable marketVariable) {
//		this.marketVariable = marketVariable;
//	}
//	public Stock(MarketVariable marketVariable, String stockType, String exchangeId, Character isListed,
//			Long numShares, String issuerId, Long faceAmt, String sourceTable, String userName, String lastUpdated,
//			BigDecimal versionNo, Set<StockIndexDetail> stockIndexDetails, Set<StockHis> stockHises) {
//		this.marketVariable = marketVariable;
//		this.stockType = stockType;
//		this.exchangeId = exchangeId;
//		this.isListed = isListed;
//		this.numShares = numShares;
//		this.issuerId = issuerId;
//		this.faceAmt = faceAmt;
//		this.sourceTable = sourceTable;
//		this.userName = userName;
//		this.lastUpdated = lastUpdated;
//		this.versionNo = versionNo;
//		this.stockIndexDetails = stockIndexDetails;
//		this.stockHises = stockHises;
//	}

//	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "marketVariable"))
//	@Id
//	@GeneratedValue(generator = "generator")
//	@Column(name = "MV_ID", unique = true, nullable = false, length = 20)
//	@Size(max = 20)
//	@AnnoMethodTree(order =10)
//	public String getStockId() {
//		return this.stockId;
//	}
//	public void setStockId(String stockId) {
//		this.stockId = stockId;
//	}

	@Transient
	public String getStockId() {
		return this.getMvId();
	}
	
//	@Transient
//	@OneToOne(fetch = FetchType.LAZY)
//	@PrimaryKeyJoinColumn
//	@NotNull
//	@Size(max = 20)
//	@AnnoMethodTree(order =11, type=EColumnType.Entity, init=false)
//	public MarketVariable getMarketVariable() {
//		return this.marketVariable;
//	}
//	public void setMarketVariable(MarketVariable marketVariable) {
//		this.marketVariable = marketVariable;
//	}

	@Column(name = "STOCK_TYPE", length = 10)
	@Size(max = 10)
	@AnnoMethodTree(order =20)
	public String getStockType() {
		return this.stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	@Column(name = "EXCHANGE_ID", length = 20)
	@Size(max = 20)
	@AnnoMethodTree(order =30)
	public String getExchangeId() {
		return this.exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	@Column(name = "IS_LISTED", length = 1)
	@AnnoMethodTree(order =35)
	public Character getIsListed() {
		return this.isListed;
	}

	public void setIsListed(Character isListed) {
		this.isListed = isListed;
	}

	@Column(name = "NUM_SHARES", precision = 10, scale = 0)
	@AnnoMethodTree(order =40, type=EColumnType.Number)
	public Long getNumShares() {
		return this.numShares;
	}

	public void setNumShares(Long numShares) {
		this.numShares = numShares;
	}

	@Column(name = "ISSUER_ID", length = 30)
	@Size(max = 30)
	@AnnoMethodTree(order =50)
	public String getIssuerId() {
		return this.issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	@Column(name = "FACE_AMT", precision = 10, scale = 0)
	@AnnoMethodTree(order =45, type=EColumnType.Number)
	public Long getFaceAmt() {
		return this.faceAmt;
	}

	public void setFaceAmt(Long faceAmt) {
		this.faceAmt = faceAmt;
	}
	
	@Column(name = "USE_AS_UNDERLYING_YN")
	@Type(type="yes_no")
	public boolean isUseAsUnderlying() {
		return useAsUnderlying;
	}

	public void setUseAsUnderlying(boolean useAsUnderlying) {
		this.useAsUnderlying = useAsUnderlying;
	}

	@Column(name = "SOURCE_TABLE", length = 50)
	@Size(max = 50)
	@AnnoMethodTree(order =90, init=false)
	public String getSourceTable() {
		return this.sourceTable;
	}

	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}

	@Column(name = "USER_NAME", length = 20)
	@Size(max = 20)
	@AnnoMethodTree(order =91, init=false)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "LAST_UPDATED", length = 8)
	@Size(max = 8)
	@AnnoMethodTree(order =92, init=false)
	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Column(name = "VERSION_NO", precision = 22, scale = 0)
	@AnnoMethodTree(order =93, init=false)
	public BigDecimal getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
//	public Set<StockIndexDetail> getStockIndexDetails() {
//		return this.stockIndexDetails;
//	}
//
//	public void setStockIndexDetails(Set<StockIndexDetail> stockIndexDetails) {
//		this.stockIndexDetails = stockIndexDetails;
//	}
//
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
	@AnnoMethodTree(order =60, type= EColumnType.List)
	public List<StockHis> getStockHis() {
		return this.stockHis;
	}

	public void setStockHis(List<StockHis> stockHis) {
		this.stockHis = stockHis;
	}

//	@Transient
	@ElementCollection
	@CollectionTable(name="STOCK_HIS", joinColumns= @JoinColumn(name="STOCK_ID"))
	@MapKeyColumn(name="BSSD")	
	public Map<String, PriceData> getPriceMap() {
		return priceMap;
	}

	public void setPriceMap(Map<String, PriceData> priceMap) {
		this.priceMap = priceMap;
	}

	private PriceData priceData;
	@Override
	@Transient
	public PriceData getPriceData(String bssd) {
		return getPriceMap().get(bssd);
	}
	
	
}
