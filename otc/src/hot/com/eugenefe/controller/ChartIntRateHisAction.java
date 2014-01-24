package com.eugenefe.controller;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.eugenefe.entity.IMarketVariableHis;
import com.eugenefe.entity.IntRateHis;
import com.eugenefe.entity.MarketVariable;

@Name("chartIntRateHisAction")
@Scope(ScopeType.CONVERSATION)
public class ChartIntRateHisAction {
	@Logger
	private Log log;
	@In(required=false)
	private List<IntRateHis> filteredIntRateHis;
	
	private CartesianChartModel linearModel;

	public ChartIntRateHisAction() {
		linearModel  = new CartesianChartModel();
		LineChartSeries series1 = new LineChartSeries(); 
		series1.setLabel("Series 1");  
		series1.set(" ", 0);
		linearModel.addSeries(series1);  
	}

	public CartesianChartModel getLinearModel() {
		return linearModel;
	}

	public void setLinearModel(CartesianChartModel linearModel) {
		this.linearModel = linearModel;
	}

//	@Observer(value="evtReloadIntRateHis")
	public void loadChart(List<IntRateHis> intRateHisList){
		log.info("evtForCreateChart", intRateHisList.size());
		
		linearModel  = new CartesianChartModel();
		LineChartSeries series1 = new LineChartSeries(); 
//		series1.setLabel(product.getMvId());  
		series1.setLabel("AAA");

//		if(product == null || intRateHisList == null || intRateHisList.isEmpty()){
			if(intRateHisList == null || intRateHisList.isEmpty()){	
			log.info("in the load chart");
			series1.set(" ", 0);
		}
		int cnt=0;
		
		String temp;
		for(IMarketVariableHis aa : intRateHisList){
			log.info("in the load chart11 :#0 ", aa.getBssd());
			temp = aa.getBssd().substring(4);
//			series1.set(aa.getBasedate().getBssd(), aa.getClosePrice());
//			series1.set(aa.getBssd(), aa.getCurrentPrice());
			series1.set(temp, aa.getCurrentPrice());
		}
		
		linearModel.addSeries(series1);
	}
	
	public void loadChart1(TabChangeEvent event){
		log.info("Tab: #0,#1", event.getTab().getClientId(),event.getTab().getId());
		linearModel  = new CartesianChartModel();
		LineChartSeries series1 = new LineChartSeries(); 
//		series1.setLabel(product.getMvId());  
		series1.setLabel("AAA");

//		if(product == null || intRateHisList == null || intRateHisList.isEmpty()){
			if(filteredIntRateHis == null || filteredIntRateHis.isEmpty()){	
			log.info("in the load chart");
			series1.set(" ", 0);
		}
		int cnt=0;
		
		String temp;
		for(IMarketVariableHis aa : filteredIntRateHis){
			log.info("in the load chart11 :#0 ", aa.getBssd());
			temp = aa.getBssd().substring(4);
//			series1.set(aa.getBasedate().getBssd(), aa.getClosePrice());
//			series1.set(aa.getBssd(), aa.getCurrentPrice());
			series1.set(temp, aa.getCurrentPrice());
		}
		
		linearModel.addSeries(series1);
	}
}
