package com.eugenefe.controller.old;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.primefaces.component.chart.line.LineChart;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.Stock;
import com.eugenefe.entity.StockHis;

@Name("stockPieChartBean")
@Scope(ScopeType.CONVERSATION)
public class StockPieChartBean {
	@Logger
	private Log log;
	
	@Out
	private PieChartModel pieModel;

	public StockPieChartBean() {
//		System.out.println("Creation Chart");
		pieModel = new PieChartModel();
        pieModel.set("Brand 1", 540);  
        pieModel.set("Brand 2", 325);  
        pieModel.set("Brand 3", 702);  
        pieModel.set("Brand 4", 421); 
//		LineChartSeries series1 = new LineChartSeries(); 
//		series1.setLabel("Series 1");  
//		series1.set(1, 1);
//		series1.set(2,2);
//		series1.set(3,3);
//		linearModel.addSeries(series1);  
	}

	public PieChartModel getPieModel() {
		log.info("Get PieChart Model ");
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}

	@Observer(value="selectStock")
	public void loadChart(Stock stock){
		if(stock == null || stock.getStockHis() == null || stock.getStockHis().isEmpty()){
			log.info("in the load chart Event Null port ");
			return ;
		}
		pieModel = new PieChartModel();

		for(StockHis aa : stock.getStockHis()){
			log.info("in the load chart11 :#0 ", aa.getBasedate().getBssd());
			pieModel.set(aa.getBasedate().getBssd(), aa.getClosePrice().doubleValue());
		}
		
		 
	}
	

	
	

}
