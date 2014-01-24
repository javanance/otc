package com.eugenefe.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
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
import com.eugenefe.entity.IntRate;
import com.eugenefe.entity.IntRateHis;
import com.eugenefe.entity.IrCurve;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.util.CrossTableModelOld;
import com.eugenefe.util.PivotTableModel;

@Name("chartIrcHisAction")
@Scope(ScopeType.CONVERSATION)
public class ChartIrcHisAction {
	@Logger
	private Log log;

	@In(required = false)
	private List<PivotTableModel<IrCurve, EMaturity,IntRate>> selectedTableModel;
	
	@In(value="#{basedateBean.bssd}")
	private String bssd;

	private CartesianChartModel linearModel;

	public ChartIrcHisAction() {
		System.out.println("Construction ChartIrcHisAction");
	}
	
	@Create
	public void create(){
		linearModel = new CartesianChartModel();
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("No Data Selected");
		series1.set(" ", 0);
		linearModel.addSeries(series1);
	}
	
//**************************************************************	
	@Observer(value = "evtBaseDateChange_/view/v140IrCurve.xhtml")
	public void loadChart() {
		log.info("In the TermStructure Chart: #0,#1");
		linearModel = new CartesianChartModel();
		LineChartSeries tempSeries ;
		List<EMaturity> tempList ;
		Set<EMaturity> tempSet = new HashSet<EMaturity>();

		if (selectedTableModel == null || selectedTableModel.isEmpty()) {
			create();
		} 
		else {
			tempSet.add(EMaturity.Y01);					    //Assure not null of the Chart Model
//			X-axis of Chart for multiple IrCurve 
			for (PivotTableModel<IrCurve, EMaturity,IntRate> aa : selectedTableModel) {
				tempSet.addAll(aa.getContentMap().keySet());
			}
			tempList = new ArrayList<EMaturity>(tempSet	);
			Collections.sort(tempList);
			
			for (PivotTableModel<IrCurve, EMaturity,IntRate> aa : selectedTableModel) {
				tempSeries = new LineChartSeries();
				tempSeries.setLabel(aa.getData().getIrcId());

				for (EMaturity key : tempList) {
					if (aa.getContentMap().keySet().contains(key)) {
						tempSeries.set(key.getName(), aa.getContentMap().get(key).getIntRate().get(bssd));
					} else {
						tempSeries.set(key.getName(), null);
					}
				}
				linearModel.addSeries(tempSeries);
//				log.info("in the load chart2");
			}
		}
	}

	public String getDatatipFormat() {
		return "<span style=\"display:none;\">%s</span><span>%.4f</span>";
		// return "<span>%s</span><span>%.5f</span>";
	}
	
	
//	**************************Getter and Setter*****************
	public CartesianChartModel getLinearModel() {
		return linearModel;
	}
	public void setLinearModel(CartesianChartModel linearModel) {
		this.linearModel = linearModel;
	}
}
