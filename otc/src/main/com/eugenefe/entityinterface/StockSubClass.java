package com.eugenefe.entityinterface;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.StockHis;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.EColumnType;

@Entity
@Table(name ="STOCK")
@PrimaryKeyJoinColumn(name ="MV_ID")
public class StockSubClass extends MvSubClass{

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

	public StockSubClass() {
	}

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
}
