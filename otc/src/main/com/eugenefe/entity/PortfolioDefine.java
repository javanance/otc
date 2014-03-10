package com.eugenefe.entity;

import java.io.Serializable;

import com.eugenefe.converter.TableDynamicColumn;
import com.eugenefe.enums.EEquation;

public class PortfolioDefine implements Serializable{
	private String portPrefix;
	private String level;
	private String propPath;
	private String propTable;
	private String propColumn;
	private EEquation equation;
//	private String equationName;
	private String condition1;
	private String condition2;
//	private TableDynamicColumn prop;

	public PortfolioDefine() {
		equation = EEquation.DISTINCT;
	}

	
	public PortfolioDefine(String portPrefix, String level, String propPath, String propTable, String propColumn,
			EEquation equation, String condition1, String condition2) {
		this.portPrefix = portPrefix;
		this.level = level;
		this.propPath = propPath;
		this.propTable = propTable;
		this.propColumn = propColumn;
		this.equation = equation;
//		this.equationName = equationName;
		this.condition1 = condition1;
		this.condition2 = condition2;
	}
	
	public String getPortPrefix() {
		return portPrefix;
	}

	public void setPortPrefix(String portPrefix) {
		this.portPrefix = portPrefix;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPropPath() {
		return propPath;
	}

	public void setPropPath(String propPath) {
		this.propPath = propPath;
	}

	public EEquation getEquation() {
		return equation;
	}

	public void setEquation(EEquation equation) {
		this.equation = equation;
	}

	public String getCondition1() {
		return condition1;
	}

	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}

	public String getCondition2() {
		return condition2;
	}

	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}


	
//	public String getEquationName() {
//		return equationName;
//	}
//
//	public void setEquationName(String equationName) {
//		this.equationName = equationName;
//	}

	public String getPropTable() {
		return propTable;
	}

	public void setPropTable(String propTable) {
		this.propTable = propTable;
	}

	public String getPropColumn() {
		return propColumn;
	}

	public void setPropColumn(String propColumn) {
		this.propColumn = propColumn;
	}


	@Override
	public int hashCode() {
		return super.hashCode();
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PortfolioDefine){
			PortfolioDefine other = (PortfolioDefine)obj;
			
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
