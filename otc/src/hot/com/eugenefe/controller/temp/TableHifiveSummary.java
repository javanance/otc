package com.eugenefe.controller.temp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.Vlookup;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.validator.util.privilegedactions.GetMethod;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;

import com.eugenefe.entity.Hifive;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.enums.raw.EElsOptionType;
import com.eugenefe.util.ComboListInit;
import com.eugenefe.util.FnCalendar;


@Name("tableHifiveSummary")
@Scope(ScopeType.CONVERSATION)
public class TableHifiveSummary {
	@Logger 	private Log log;
	@In			private Session session;
	@In		private StatusMessages statusMessages;
	@In		private Map<String, String> messages;
	@In(create=true) 	private ComboListInit comboListInit; 


	private List<Hifive> hifiveList = new ArrayList<Hifive>();
	private Map<EElsOptionType, Map<String, Double>> rstMap = new HashMap<EElsOptionType, Map<String,Double>>();
	private List<EElsOptionType> optList = new ArrayList<EElsOptionType>();
	private List<Map.Entry<EElsOptionType, Map<String, Double>>> rstList = new ArrayList<Map.Entry<EElsOptionType,Map<String,Double>>>();
	private Map<String,Double> summaryMap = new HashMap<String, Double>();

	
	private List<EMaturity> matList = new ArrayList<EMaturity>();
	
	private double valueUnit ;
	public double getValueUnit() {
		return valueUnit;
	}
	public void setValueUnit(double valueUnit) {
		this.valueUnit = valueUnit;
	}

	public Map<String, Double> getSummaryMap() {
		return summaryMap;
	}

	public void setSummaryMap(Map<String, Double> summaryMap) {
		this.summaryMap = summaryMap;
	}

	public List<Hifive> getHifiveList() {
		return hifiveList;
	}

	public void setHifiveList(List<Hifive> hifiveList) {
		this.hifiveList = hifiveList;
	}

	public Map<EElsOptionType, Map<String, Double>> getRstMap() {
		return rstMap;
	}

	public void setRstMap(Map<EElsOptionType, Map<String, Double>> rstMap) {
		this.rstMap = rstMap;
	}

	public List<EElsOptionType> getOptList() {
		return optList;
	}

	public void setOptList(List<EElsOptionType> optList) {
		this.optList = optList;
	}

	public TableHifiveSummary() {
		System.out.println("Construction TableHifiveSummary");
	}

// *******************************************
	@Create
	public void create() {
		log.info("HiFive Creation:#0");
		session.setFlushMode(FlushMode.MANUAL);
		hifiveList = session.createCriteria(Hifive.class).list();
		log.info("HifiveSize :#0", hifiveList.size());
		
		valueUnit = 1;
//		valueUnit = 1000000.0;
		matList.add(EMaturity.M01);
		matList.add(EMaturity.M03);
		matList.add(EMaturity.M06);
		matList.add(EMaturity.M09);
		matList.add(EMaturity.Y01);
		matList.add(EMaturity.Y02);
		matList.add(EMaturity.Y03);
		matList.add(EMaturity.Y05);
		
		optList.add(EElsOptionType.AUTO_CALL);
		optList.add(EElsOptionType.AUTO_CALL_KO);
		optList.add(EElsOptionType.AUTO_CALL_BARRIER_SD);
		optList.add(EElsOptionType.AUTO_CALL_SD_KO);
		optList.add(EElsOptionType.AUTO_CALL_CP_SU);
		optList.add(EElsOptionType.AUTO_CALL_BARRIER_SU);
		optList.add(EElsOptionType.MTHLY_CP_BARRIER_SD);
		optList.add(EElsOptionType.MTHLY_CP_BARRIER_SU);
		optList.add(EElsOptionType.AVG_BARRIER_SU);
		optList.add(EElsOptionType.AUTO_CALL_BARRIER_CH);
		
		loadRstMap(valueUnit);
		rstList.addAll(rstMap.entrySet());
	}
	
	/*public void loadRstMap(double unit){
		String bssd = "20140129";
		double rstValue =0.0;
		
		for(EElsOptionType aa : optList){
			Map<String, Double> tempMap = new HashMap<String, Double>();
			for(Hifive bb : hifiveList){
				EMaturity tempMaturity;
				double tempRst =0.0;
				if(aa.equals(bb.getHifiveType()) 
						&&  bb.getMaturityCalenar().after(FnCalendar.getInstance(bssd))){
					tempMaturity = bb.getResidualEMaturity(matList,bssd);
					log.info("Matt: #0,#1, #2", bb.getProdName(), tempMaturity, bb.getMaturityDate());
					if(tempMap.get(tempMaturity)!=null){
						tempRst = tempRst +tempMap.get(tempMaturity);
					}
					tempMap.put(tempMaturity.getName(), (tempRst + bb.getNotionalAmt().doubleValue())/unit);
				}
				rstMap.put(aa, tempMap);
			}
		}	
		for(EMaturity mat : matList){
			rstValue =0.0;
			for(Hifive bb : hifiveList){
				if( bb.getMaturityCalenar().after(FnCalendar.getInstance(bssd))
							&& mat.equals(bb.getResidualEMaturity(matList, bssd))){
					rstValue= rstValue+ bb.getNotionalAmt().doubleValue();
				}	
			}
			summaryMap.put(mat.getName(),rstValue/unit);
		}
	}*/
	
	public void loadDynamicRstMap(double unit){
		String bssd = "20140129";
		double rstValue =0.0;
	
	for(EElsOptionType aa : optList){
		Map<String, Double> tempMap = new HashMap<String, Double>();
		for(EMaturity mat : comboListInit.getResMatList()){	
			rstValue =0.0;
			for(Hifive bb : hifiveList){
				if(aa.equals(bb.getHifiveType()) 
						&&  bb.getMaturityCalenar().after(FnCalendar.getInstance(bssd))
							&& mat.equals(bb.getResidualEMaturity(matList,bssd))){
					rstValue= rstValue+ bb.getNotionalAmt().doubleValue();
//					log.info("Sum : #0, #1,#2, #3, #4" ,aa.getIntCode(),  bb.getProdId(),bb.getMaturityDate(), bb.getNotionalAmt(), rstValue);
				}	
			}
			tempMap.put(mat.getName(),rstValue/unit);
		}
		rstMap.put(aa, tempMap);
	}
	
	for(EMaturity mat : matList){
		rstValue =0.0;
		for(Hifive bb : hifiveList){
			if( bb.getMaturityCalenar().after(FnCalendar.getInstance(bssd))
						&& mat.equals(bb.getResidualEMaturity(matList,bssd))){
				rstValue= rstValue+ bb.getNotionalAmt().doubleValue();
			}	
		}
		summaryMap.put(mat.getName(),rstValue/unit);
	}
}
	
	public void loadRstMap(double unit){
		String bssd = "20140129";
		double rstValue =0.0;
		
		for(EElsOptionType aa : optList){
			Map<String, Double> tempMap = new HashMap<String, Double>();
//			for(EMaturity mat : comboListInit.getResMatList()){
			for(EMaturity mat : matList){	
				rstValue =0.0;
				for(Hifive bb : hifiveList){
					if(aa.equals(bb.getHifiveType()) 
							&&  bb.getMaturityCalenar().after(FnCalendar.getInstance(bssd))
								&& mat.equals(bb.getResidualEMaturity(bssd))){
						rstValue= rstValue+ bb.getNotionalAmt().doubleValue();
//						log.info("Sum : #0, #1,#2, #3, #4" ,aa.getIntCode(),  bb.getProdId(),bb.getMaturityDate(), bb.getNotionalAmt(), rstValue);
					}	
				}
				tempMap.put(mat.getName(),rstValue/unit);
			}
			rstMap.put(aa, tempMap);
		}
		for(EMaturity mat : matList){
			rstValue =0.0;
			for(Hifive bb : hifiveList){
				if( bb.getMaturityCalenar().after(FnCalendar.getInstance(bssd))
							&& mat.equals(bb.getResidualEMaturity(bssd))){
					rstValue= rstValue+ bb.getNotionalAmt().doubleValue();
				}	
			}
			summaryMap.put(mat.getName(),rstValue/unit);
		}
	}


	public List<EMaturity> getMatList() {
		return matList;
	}

	public void setMatList(List<EMaturity> matList) {
		this.matList = matList;
	}

	public List<Map.Entry<EElsOptionType, Map<String, Double>>> getRstList() {
		return rstList;
	}

	public void setRstList(List<Map.Entry<EElsOptionType, Map<String, Double>>> rstList) {
		this.rstList = rstList;
	}
//	public void changeUnit(){
//		loadRstMap(valueUnit);
//		
//	}

	public void changeUnit(){
		rstList.clear();
		Map<EElsOptionType, Map<String,Double>> tempMap = new HashMap<EElsOptionType, Map<String,Double>>();

		for(Map.Entry<EElsOptionType, Map<String, Double>> entry : rstMap.entrySet()){
			Map<String, Double> temp= new HashMap<String, Double>();
			for(Map.Entry<String, Double> subEntry : entry.getValue().entrySet()){
				temp.put(subEntry.getKey(), subEntry.getValue()*1000000/valueUnit);
			}
			tempMap.put(entry.getKey(), temp);
		}
		
		rstList.addAll(tempMap.entrySet());
		
		for(EMaturity aa: matList){
			double tempSum =0.0;
			for(Map.Entry<EElsOptionType, Map<String, Double>> entry : rstMap.entrySet()){
				tempSum = tempSum + entry.getValue().get(aa.name())* 1000000/valueUnit;
			}
			summaryMap.put(aa.getName(), tempSum);
		}
	}
	public void changeColumn(){
		log.info("AAA: #0", matList.size());
//		TODO : for Dynamic
//		rstList.clear();
//		loadDynamicRstMap(valueUnit);
//		rstList.addAll(rstMap.entrySet());		
	}
	
}
