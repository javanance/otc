package com.eugenefe.controller;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.eugenefe.entity.IMarketVariableHis;
import com.eugenefe.entity.MarketVariable;

@Name("chartMvHisDataAction")
@Scope(ScopeType.CONVERSATION)
public class ChartMvHisDataAction {
	@Logger
	private Log log;
	
	private CartesianChartModel linearModel;

	public ChartMvHisDataAction() {
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

//	@Observer(value="afterQueryResult")
	@Observer(value="evtForCreateChart")
	public void loadChart(MarketVariable product, List<IMarketVariableHis> marketVariableHisList){
		log.info("evtForCreateChart", marketVariableHisList.size());
		linearModel  = new CartesianChartModel();
		LineChartSeries series1 = new LineChartSeries(); 
		series1.setLabel(product.getMvId());  

		if(product == null || marketVariableHisList == null || marketVariableHisList.isEmpty()){
			log.info("in the load chart");
			series1.set(" ", 0);
		}
		int cnt=0;
		
		String temp;
		for(IMarketVariableHis aa : marketVariableHisList){
			log.info("in the load chart11 :#0 ", aa.getBssd());
			temp = aa.getBssd().substring(4);
//			series1.set(aa.getBasedate().getBssd(), aa.getClosePrice());
//			series1.set(aa.getBssd(), aa.getCurrentPrice());
			series1.set(temp, aa.getCurrentPrice());
		}
		
		linearModel.addSeries(series1);
	}
}
