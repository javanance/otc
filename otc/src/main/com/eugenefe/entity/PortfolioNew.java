package com.eugenefe.entity;

// Generated 2014. 2. 10 ���� 5:51:10 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;

/**
 * PortfolioNew generated by hbm2java
 */
@Entity
@Table(name = "PORTFOLIO_NEW")
@AnnoNavigationFilter
public class PortfolioNew implements java.io.Serializable {

	private String portfolioId;
	private String portfolioName;
	private String portfolioType;
	private Hierarchy hierarchy;
	private String changeable;

	
	private Map<PortfolioNew, BigDecimal> subPortfolioMap = new HashMap<PortfolioNew, BigDecimal>();
	private List<PortfolioCompo> subPortfolioList = new ArrayList<PortfolioCompo>();
//	private Map<PortfolioNew, BigDecimal> subPortfolioMap = new HashMap<PortfolioNew, BigDecimal>();
	private List<PortfolioDefineNew> portDefineList = new ArrayList<PortfolioDefineNew>();
	
	
	public PortfolioNew() {
	}

	public PortfolioNew(String portfolioId) {
		this.portfolioId = portfolioId;
	}

	@Id
	@Column(name = "PORTFOLIO_ID", unique = true, nullable = false, length = 20)
	@AnnoMethodTree(order =10, init=true)
	public String getPortfolioId() {
		return this.portfolioId;
	}

	public void setPortfolioId(String portfolioId) {
		this.portfolioId = portfolioId;
	}

	@Column(name = "PORTFOLIO_NAME", length = 50)
	@AnnoMethodTree(order =20, init=true)
	public String getPortfolioName() {
		return this.portfolioName;
	}

	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}

	@Column(name = "PORTFOLIO_TYPE", length = 20)
	@AnnoMethodTree(order =30, init=true)
	public String getPortfolioType() {
		return this.portfolioType;
	}

	public void setPortfolioType(String portfolioType) {
		this.portfolioType = portfolioType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HIERARCHY_ID", nullable=true)
	@AnnoMethodTree(order =40, init=true)
	public Hierarchy getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(Hierarchy hierarchy) {
		this.hierarchy = hierarchy;
	}

	@ElementCollection
	@MapKeyJoinColumn(name="CHILD_PORTFOLIO_ID")
	@JoinTable(name="PORTFOLIO_COMPO"
				, joinColumns=@JoinColumn(name="PORTFOLIO_ID")
//				, inverseJoinColumns=@JoinColumn(name="PORT_WEIGHT")
	)
	@Column(name="PORT_WEIGHT")
	public Map<PortfolioNew, BigDecimal> getSubPortfolioMap() {
		return subPortfolioMap;
	}

	public void setSubPortfolioMap(Map<PortfolioNew, BigDecimal> subPortfolioMap) {
		this.subPortfolioMap = subPortfolioMap;
	}

	@OneToMany(mappedBy="portfolio")
	public List<PortfolioDefineNew> getPortDefineList() {
		return portDefineList;
	}

	public void setPortDefineList(List<PortfolioDefineNew> portDefineList) {
		this.portDefineList = portDefineList;
	}

	@OneToMany(mappedBy="portfolio")
	public List<PortfolioCompo> getSubPortfolioList() {
		return subPortfolioList;
	}

	public void setSubPortfolioList(List<PortfolioCompo> subPortfolioList) {
		this.subPortfolioList = subPortfolioList;
	}

	private List<PortfolioCompo> parentPort = new ArrayList<PortfolioCompo>();

	@OneToMany(mappedBy="childPortfolio")
	public List<PortfolioCompo> getParentPort() {
		return parentPort;
	}

	public void setParentPort(List<PortfolioCompo> parentPort) {
		this.parentPort = parentPort;
	}

	@Column(name = "CHANGEABLE", length = 1)
	@AnnoMethodTree(order =40, init=true)	
	public String getChangeable() {
		return changeable;
	}

	public void setChangeable(String changeable) {
		this.changeable = changeable;
	}
	
	
}
