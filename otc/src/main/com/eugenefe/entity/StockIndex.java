package com.eugenefe.entity;

// Generated Apr 10, 2013 4:09:22 PM by Hibernate Tools 3.4.0.CR1

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
import javax.persistence.JoinColumns;
import javax.persistence.MapKeyClass;
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

import com.eugenefe.entity.component.HisData;
import com.eugenefe.entity.component.PriceData;
import com.eugenefe.util.AnnoMethodTree;

/**
 * StockIndex generated by hbm2java
 */
@Entity
@Table(name = "STOCK_INDEX")
@PrimaryKeyJoinColumn(name="ST_INDEX_ID")
public class StockIndex extends MarketVariableJoin implements java.io.Serializable {

//	private String stIndexId;
//	private MarketVariable marketVariable;
	private String stIndexName;
	private String indexType;
	/*private Set<StockIndexHis> stockIndexHises = new HashSet<StockIndexHis>(0);
	private Set<Etf> etfs = new HashSet<Etf>(0);
	private Set<StockIndexDetail> stockIndexDetails = new HashSet<StockIndexDetail>(0);*/

	private Map<String, PriceData> priceMap = new HashMap<String, PriceData>();
	
	
	public StockIndex() {
	}


//	@Id
//	@Column(name = "ST_INDEX_ID", unique = true, nullable = false, length = 20)
//	@AnnoMethodTree(order=10, init=true)
//	public String getStIndexId() {
//		return this.stIndexId;
//	}
//	public void setStIndexId(String stIndexId) {
//		this.stIndexId = stIndexId;
//	}


	@Column(name = "ST_INDEX_NAME", length = 50)
	@Size(max = 50)
	public String getStIndexName() {
		return this.stIndexName;
	}

	public void setStIndexName(String stIndexName) {
		this.stIndexName = stIndexName;
	}

	@Column(name = "INDEX_TYPE", length = 10)
	@Size(max = 10)
	public String getIndexType() {
		return this.indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}



	@ElementCollection
	@CollectionTable(name="STOCK_INDEX_HIS", joinColumns= @JoinColumn(name="ST_INDEX_ID"))
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

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stockIndex")
//	public Set<StockIndexDetail> getStockIndexDetails() {
//		return this.stockIndexDetails;
//	}
//
//	public void setStockIndexDetails(Set<StockIndexDetail> stockIndexDetails) {
//		this.stockIndexDetails = stockIndexDetails;
//	}


}
