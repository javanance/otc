package com.eugenefe.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.VcvMatrixHis;
import com.eugenefe.util.ColumnModel;
import com.eugenefe.util.CrossTableModelOld;
import com.eugenefe.util.NamedQuery;

@Name("vcvMatrixActionOld")
//@Scope(ScopeType.CONVERSATION)
public class CopyOfVcvMatrixAction {
	@Logger
	private Log log;
	@In
	private EntityManager entityManager;

	private List<VcvMatrixHis> allVcvMatrix;
	private List<MarketVariable> selRiskFactors = new ArrayList<MarketVariable>();
	
	@Out(scope=ScopeType.CONVERSATION)
	private List<MarketVariable> riskFactors;
	
	@Out(scope=ScopeType.CONVERSATION)
	private List<ColumnModel> vcvMatrixColumns;
	
	@Out(scope=ScopeType.CONVERSATION)
	private List<CrossTableModelOld> pivotList;
	
	
	public CopyOfVcvMatrixAction() {
		System.out.println("Construcion VcvMatrixAction");
	}
	

	public List<VcvMatrixHis> getAllVcvMatrix() {
		return allVcvMatrix;
	}
	public void setAllVcvMatrix(List<VcvMatrixHis> allVcvMatrix) {
		this.allVcvMatrix = allVcvMatrix;
	}
	
	public List<MarketVariable> getRiskFactors() {
		return riskFactors;
	}
	
	public void setRiskFactors(List<MarketVariable> riskFactors) {
		this.riskFactors = riskFactors;
	}
	
	public List<ColumnModel> getVcvMatrixColumns() {
		return vcvMatrixColumns;
	}
	public void setVcvMatrixColumns(List<ColumnModel> vcvMatrixColumns) {
		this.vcvMatrixColumns = vcvMatrixColumns;
	}
	
	public List<CrossTableModelOld> getPivotList() {
		return pivotList;
	}
	public void setPivotList(List<CrossTableModelOld> pivotList) {
		this.pivotList = pivotList;
	}

//	@Factory(value="pivotList")
//	@Observer("changeBssd_/view/v120RiskFactor.xhtml")
	public void initVcvMatrix(){
		pivotList = new ArrayList<CrossTableModelOld>();
		riskFactors = new ArrayList<MarketVariable>();
		vcvMatrixColumns = new ArrayList<ColumnModel>();
		
		
		
		allVcvMatrix = entityManager.createQuery(NamedQuery.VcvMatrixHisBssd.getQuery()).getResultList();

		for(VcvMatrixHis aa: allVcvMatrix){
			if(!riskFactors.contains(aa.getRiskFactor())){
				riskFactors.add(aa.getRiskFactor());
				vcvMatrixColumns.add(new ColumnModel(aa.getRiskFactor().getMvName(), aa.getRiskFactor().getMvName()));
			}
		}
		if(selRiskFactors.size()!=0){
			riskFactors =selRiskFactors;
		}
		
		for(MarketVariable aa : riskFactors){
			Map<String, BigDecimal> tempMap =new HashMap<String, BigDecimal>() ;
			for(VcvMatrixHis bb: allVcvMatrix){
				log.info("Get Risk Factor:#0", bb.getRiskFactor().getMvId());
				if(aa.equals(bb.getRiskFactor())){
					tempMap.put(bb.getRefRiskFactor().getMvName(), bb.getCovariance());
				}
			}
			pivotList.add(new CrossTableModelOld(aa.getMvName(), tempMap));
		}
	}
	
//	@Observer("selRiskFactors")
	public void aa(List<MarketVariable> selRf){
		selRiskFactors = selRf;
		initVcvMatrix();
	}
 
}
