package com.eugenefe.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.eugenefe.converter.TableDynamicColumn;
import com.eugenefe.enums.EEquation;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;
import com.eugenefe.util.EColumnType;

@Entity
@Table(name = "PORTFOLIO_DEFINE")
@AnnoNavigationFilter
public class PortfolioDefineNew implements Serializable{
	private PortfolioDefineId id;
	
	private PortfolioNew portfolio;
//	private String propPath;
	private Portfolio portfolioOld;
	private String propTable;
	private String propColumn;
	private EEquation equation;
	private String condition1;
	private String condition2;

	public PortfolioDefineNew() {
		equation = EEquation.DISTINCT;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "portId", column = @Column(name = "PORT_ID", nullable = false, length = 20)),
			@AttributeOverride(name = "portLevel", column = @Column(name = "PORT_LEVEL", nullable = false, length = 20)) })
	@AnnoMethodTree(order =10, init=true)
	public PortfolioDefineId getId() {
		return id;
	}

	public void setId(PortfolioDefineId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "PORT_ID", nullable = false, insertable = false, updatable = false)
	@AnnoMethodTree(order=15, init=false, type=EColumnType.Entity, aggregatable=false)
	public PortfolioNew getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(PortfolioNew portfolio) {
		this.portfolio = portfolio;
	}

	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "PORT_ID", nullable = false, insertable = false, updatable = false)
	@AnnoMethodTree(order=16, init=false, type=EColumnType.Entity, aggregatable=false)
	public Portfolio getPortfolioOld() {
		return portfolioOld;
	}

	public void setPortfolioOld(Portfolio portfolioOld) {
		this.portfolioOld = portfolioOld;
	}

	
	@Column(name = "TABLE_ID", nullable = true, length = 20)
	@AnnoMethodTree(order =20, init=true)
	public String getPropTable() {
		return propTable;
	}

	public void setPropTable(String propTable) {
		this.propTable = propTable;
	}

	@Column(name = "COLUMN_ID", nullable = true, length = 20)
	@AnnoMethodTree(order =21, init=true)
	public String getPropColumn() {
		return propColumn;
	}

	public void setPropColumn(String propColumn) {
		this.propColumn = propColumn;
	}
	
	@Column(name = "EQUATION", nullable = true, length = 20)
	@Enumerated(EnumType.STRING)
	@AnnoMethodTree(order =30, init=true)
	public EEquation getEquation() {
		return equation;
	}

	public void setEquation(EEquation equation) {
		this.equation = equation;
	}

	@Column(name = "CONDITION_1", nullable = true, length = 20)
	@AnnoMethodTree(order =31, init=true)
	public String getCondition1() {
		return condition1;
	}

	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}

	@Column(name = "CONDITION_2", nullable = true, length = 20)
	@AnnoMethodTree(order =32, init=true)
	public String getCondition2() {
		return condition2;
	}

	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PortfolioDefineNew){
			PortfolioDefineNew other = (PortfolioDefineNew)obj;
			
			if(this.getPropTable().equals(other.getPropTable()) 
					&& this.getPropColumn().equals(other.getPropColumn())
						&& this.getEquation().equals(other.getEquation())){
				if(this.getEquation().getOperandSize()==2 
						&& this.getCondition1().equals(other.getCondition1())
							&& this.getCondition2().equals(other.getCondition2())){
					return true;
				}else if(this.getEquation().getOperandSize()==1 
						&& this.getCondition1().equals(other.getCondition1())){
					return true;
				}else if(this.getEquation().getOperandSize()==0){
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
}
