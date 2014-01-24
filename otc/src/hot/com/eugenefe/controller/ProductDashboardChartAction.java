package com.eugenefe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.primefaces.model.chart.PieChartModel;

import com.eugenefe.entityinterface.ProductDashboard;

@Name("productDashboardChartAction")
@Scope(ScopeType.CONVERSATION)
public class ProductDashboardChartAction {
	 private static final String EJBQL =
	 " select New com.eugenefe.entityinterface.ProductDashboard(a.mvType, count(b.posId) as posCnt , sum(b.posAmt) as posAmt) " +
	 " from MarketVariable a left join a.positions b" +
	 " where a.productYN ='Y'"	 +
	 " group by a.mvType";
	 
//	 private static final String EJBQL =
//			 " select New com.eugenefe.entityinterface.ProductDashboard(a.mvType, count(b.posId) as posCnt) " +
//			 " from MarketVariable a left join a.positions b"	 +
//			 " group by a.mvType";
	 
	 
//	private static final String EJBQL = "select a.mvType as mvType, count(a.mvId) as cnt from MarketVariable a "
//			+ "group by a.mvType";
	@Logger
	private Log log;

	@In
	private EntityManager entityManager;

	private PieChartModel pieModelCount;
	private PieChartModel pieModelAmt;

	public ProductDashboardChartAction() {
		pieModelCount = new PieChartModel();
		pieModelAmt = new PieChartModel();
	}

	public PieChartModel getPieModelCount() {
		return pieModelCount;
	}

	public void setPieModelCount(PieChartModel pieModelCount) {
		this.pieModelCount = pieModelCount;
	}

	public PieChartModel getPieModelAmt() {
		return pieModelAmt;
	}

	public void setPieModelAmt(PieChartModel pieModelAmt) {
		this.pieModelAmt = pieModelAmt;
	}

	@Create
	public void init() {
//		List<Object[]> model = new ArrayList<Object[]>();
//		 model = entityManager.createQuery(EJBQL).getResultList();
//		TypedQuery<Object[]> query = entityManager.createQuery(EJBQL, Object[].class);
//		model = query.getResultList();
//		for(Object[] aa : model){
//			if(!aa[1].equals("0")){
//				pieModel.set(aa[0].toString(), aa[1]);
//			}
//			
//		}
		 List<ProductDashboard> model = new ArrayList<ProductDashboard>();
		 model = entityManager.createQuery(EJBQL).getResultList();
		 for(ProductDashboard aa : model){
			 if(aa.getPosCnt()!=0){
			 pieModelCount.set(aa.getMvType().getType(), aa.getPosCnt());
			 pieModelAmt.set(aa.getMvType().getType(), aa.getPosAmt());
			 }
		 }
	}
	
}
