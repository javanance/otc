package com.eugenefe.entity;

// Generated Apr 10, 2013 4:09:22 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.cfg.AnnotatedClassType;

import com.eugenefe.enums.EMaturity;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.EColumnType;
import com.eugenefe.util.AnnoNavigationFilter;

/**
 * IrCurve generated by hbm2java
 */
@Entity
@Table(name = "IR_CURVE")
@AnnoNavigationFilter
//public class IrCurve implements java.io.Serializable, IEntity {
public class IrCurve implements java.io.Serializable {	

	private String ircId;
	private String ircName;
	private String forwardindMtd;
	private String interpolMtd;
	private String generationType;
	private String sourceTable;
	private String userName;
	private String lastUpdated;
	private BigDecimal versionNo;
//	private List<IrcBucket> ircBucketList = new ArrayList<IrcBucket>();
	
	private Map<EMaturity, IntRate> ircBucketMap = new HashMap<EMaturity, IntRate>();
	
//	private Set<IrcFunctionDetail> ircFunctionDetailsForRefIrcId = new HashSet<IrcFunctionDetail>(0);
//	private Set<IrcFunctionDetail> ircFunctionDetailsForIrcId = new HashSet<IrcFunctionDetail>(0);

	public IrCurve() {
	}

	public IrCurve(String ircId) {
		this.ircId = ircId;
	}

	@Id
	@Column(name = "IRC_ID", unique = true, nullable = false, length = 20)
	@NotNull
	@Size(max = 20)
	@AnnoMethodTree(order=10, init=true)
	public String getIrcId() {
		return this.ircId;
	}

	public void setIrcId(String ircId) {
		this.ircId = ircId;
	}

	@Column(name = "IRC_NAME", length = 50)
	@Size(max = 50)
	@AnnoMethodTree(order=20, init=true)
	public String getIrcName() {
		return this.ircName;
	}

	public void setIrcName(String ircName) {
		this.ircName = ircName;
	}

	@Column(name = "FORWARDIND_MTD", length = 10)
	@Size(max = 10)
	@AnnoMethodTree(order=30, init=true)
	public String getForwardindMtd() {
		return this.forwardindMtd;
	}

	public void setForwardindMtd(String forwardindMtd) {
		this.forwardindMtd = forwardindMtd;
	}

	@Column(name = "INTERPOL_MTD", length = 10)
	@Size(max = 10)
	@AnnoMethodTree(order=40, init=true)
	public String getInterpolMtd() {
		return this.interpolMtd;
	}

	public void setInterpolMtd(String interpolMtd) {
		this.interpolMtd = interpolMtd;
	}

	@Column(name = "GENERATION_TYPE", length = 10)
	@Size(max = 10)
	@AnnoMethodTree(order=50, init=true)
	public String getGenerationType() {
		return this.generationType;
	}

	public void setGenerationType(String generationType) {
		this.generationType = generationType;
	}

	@Column(name = "SOURCE_TABLE", length = 50)
	@Size(max = 50)
	@AnnoMethodTree(order=60, init=false)
	public String getSourceTable() {
		return this.sourceTable;
	}

	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}

	@Column(name = "USER_NAME", length = 20)
	@Size(max = 20)
	@AnnoMethodTree(order=70, init=false)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "LAST_UPDATED", length = 8)
	@Size(max = 8)
	@AnnoMethodTree(order=80, init=false)
	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Column(name = "VERSION_NO", precision = 22, scale = 0)
	@AnnoMethodTree(order=90, init=false)
	public BigDecimal getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

/*	@OneToMany(mappedBy="ircId", fetch=FetchType.LAZY )
//	@Fetch(FetchMode.JOIN)
//	@BatchSize(size=10)
	@OrderBy(" id.maturityId,id.ircId")
	public List<IrcBucket> getIrcBucketList() {
		return ircBucketList;
	}

	public void setIrcBucketList(List<IrcBucket> ircBucketList) {
		this.ircBucketList = ircBucketList;
	}*/

	@ManyToMany( fetch=FetchType.LAZY)
//	@Fetch(FetchMode.JOIN)
	@BatchSize(size=20)
	@MapKeyEnumerated(EnumType.ORDINAL)
	@MapKeyColumn(name="MATURITY_ID")
	@JoinTable(name = "IRC_BUCKET_DETAIL"
				, joinColumns = {@JoinColumn(name="IRC_ID") }
				, inverseJoinColumns = { @JoinColumn(name = "IR_ID") })
	@AnnoMethodTree(order=100, init=false, type=EColumnType.Map)
	public Map<EMaturity, IntRate> getIrcBucketMap() {
		return ircBucketMap;
	}

	public void setIrcBucketMap(Map<EMaturity, IntRate> ircBucketMap) {
		this.ircBucketMap = ircBucketMap;
	}

//	@AnnoMethodTree(order=11, init=false)
//	@Transient
//	@Override
//	public String getIdString() {
//		return this.getIrcId();
//	}
	
	
//	public List<IrCurveHis> getIrCurveHisList() {
//		return irCurveHisList;
//	}
//
//	public void setIrCurveHisList(List<IrCurveHis> irCurveHisList) {
//		this.irCurveHisList = irCurveHisList;
//	}

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "irCurve")
//	public Set<IrCurveHis> getIrCurveHises() {
//		return this.irCurveHises;
//	}
//
//	public void setIrCurveHises(Set<IrCurveHis> irCurveHises) {
//		this.irCurveHises = irCurveHises;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "irCurveByRefIrcId")
//	public Set<IrcFunctionDetail> getIrcFunctionDetailsForRefIrcId() {
//		return this.ircFunctionDetailsForRefIrcId;
//	}
//
//	public void setIrcFunctionDetailsForRefIrcId(Set<IrcFunctionDetail> ircFunctionDetailsForRefIrcId) {
//		this.ircFunctionDetailsForRefIrcId = ircFunctionDetailsForRefIrcId;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "irCurveByIrcId")
//	public Set<IrcFunctionDetail> getIrcFunctionDetailsForIrcId() {
//		return this.ircFunctionDetailsForIrcId;
//	}
//
//	public void setIrcFunctionDetailsForIrcId(Set<IrcFunctionDetail> ircFunctionDetailsForIrcId) {
//		this.ircFunctionDetailsForIrcId = ircFunctionDetailsForIrcId;
//	}
//
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "IRC_BUCKET_DETAIL", schema = "TAKION79", joinColumns = { @JoinColumn(name = "IRC_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "IR_ID", nullable = false, updatable = false) })
//	public Set<InterestRate> getInterestRates() {
//		return this.interestRates;
//	}
//
//	public void setInterestRates(Set<InterestRate> interestRates) {
//		this.interestRates = interestRates;
//	}
}