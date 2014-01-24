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

@Name("stockChartBean")
@Scope(ScopeType.CONVERSATION)
public class StockChartBean {
	@Logger
	private Log log;
	
	private CartesianChartModel linearModel;

	public StockChartBean() {
		linearModel  = new CartesianChartModel();
		LineChartSeries series1 = new LineChartSeries(); 
		series1.setLabel("Series 1");  
		series1.set("0", 0);
		linearModel.addSeries(series1);  
	}

	public CartesianChartModel getLinearModel() {
		return linearModel;
	}

	public void setLinearModel(CartesianChartModel linearModel) {
		this.linearModel = linearModel;
	}

	@Observer(value="selectStock")
	public void loadChart(Stock stock){
		linearModel  = new CartesianChartModel();
		LineChartSeries series1 = new LineChartSeries(); 
		series1.setLabel(stock.getStockId());  

		if(stock == null || stock.getStockHis() == null || stock.getStockHis().isEmpty()){
			log.info("in the load chart Event Null port ");
			series1.set("0", 0);
		}
		else{
			log.info("in the load chart Event ");
			for(StockHis aa : stock.getStockHis()){
				log.info("in the load chart11 :#0 ", aa.getBasedate().getBssd());
//				series1.set(aa.getBasedate().getBssd(), aa.getClosePrice());
				series1.set(aa.getBasedate().getBssd(), aa.getClosePrice().doubleValue());
			}
		}
		linearModel.addSeries(series1);  
	}
}
