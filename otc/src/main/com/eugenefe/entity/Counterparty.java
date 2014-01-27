package com.eugenefe.entity;

// Generated Apr 10, 2013 4:09:22 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;

/**
 * Counterparty generated by hbm2java
 */
@Entity
@Table(name = "COUNTERPARTY")
@AnnoNavigationFilter
public class Counterparty implements java.io.Serializable {

	private String counterpartyId;
	private String counterpartyName;
	private String counterpartyType;
	private String corpNo;
	private String creditRatingCd;
	private String RateApplyDate;
	private List<Position> positions =new ArrayList<Position>();

	public Counterparty() {
	}

	public Counterparty(String counterpartyId) {
		this.counterpartyId = counterpartyId;
	}

	public Counterparty(String counterpartyId, String counterpartyName, String counterpartyType, String corpNo,
			String creditRatingCd, List<Position> positions) {
		this.counterpartyId = counterpartyId;
		this.counterpartyName = counterpartyName;
		this.counterpartyType = counterpartyType;
		this.corpNo = corpNo;
		this.creditRatingCd = creditRatingCd;
		this.positions = positions;
	}

	@Id
	@Column(name = "COUNTERPARTY_ID", unique = true, nullable = false, length = 20)
	@NotNull
	@Size(max = 20)
	@AnnoMethodTree(order=10, init=true)
	public String getCounterpartyId() {
		return this.counterpartyId;
	}

	public void setCounterpartyId(String counterpartyId) {
		this.counterpartyId = counterpartyId;
	}

	@Column(name = "COUNTERPARTY_NAME", length = 50)
	@Size(max = 50)
	@AnnoMethodTree(order=11, init=true)
	public String getCounterpartyName() {
		return this.counterpartyName;
	}

	public void setCounterpartyName(String counterpartyName) {
		this.counterpartyName = counterpartyName;
	}

	@Column(name = "COUNTERPARTY_TYPE", length = 10)
	@Size(max = 10)
	@AnnoMethodTree(order=20, init=true)
	public String getCounterpartyType() {
		return this.counterpartyType;
	}

	public void setCounterpartyType(String counterpartyType) {
		this.counterpartyType = counterpartyType;
	}

	@Column(name = "CORP_NO", length = 13)
	@Size(max = 13)
	@AnnoMethodTree(order=30, init=true)
	public String getCorpNo() {
		return this.corpNo;
	}

	public void setCorpNo(String corpNo) {
		this.corpNo = corpNo;
	}

	@Column(name = "CREDIT_RATING_CD", length = 10)
	@Size(max = 10)
	@AnnoMethodTree(order=40, init=true)
	public String getCreditRatingCd() {
		return this.creditRatingCd;
	}

	public void setCreditRatingCd(String creditRatingCd) {
		this.creditRatingCd = creditRatingCd;
	}
	
	@Transient
//	@Column(name = "RATING_APPLY_DATE", length = 8)
//	@Size(max = 8)
//	@AnnoMethodTree(order=41, init=true)
	public String getRateApplyDate() {
		return RateApplyDate;
	}

	public void setRateApplyDate(String rateApplyDate) {
		RateApplyDate = rateApplyDate;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "counterparty")
	@AnnoMethodTree(order=50, init=false)
	public List<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
	
	

	
}