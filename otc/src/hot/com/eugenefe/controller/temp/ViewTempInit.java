package com.eugenefe.controller.temp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Log;

import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.crud.MeasureDao;
import com.eugenefe.util.NamedQuery;

@Name("viewTempInit")
@Scope(ScopeType.CONVERSATION)
//@AutoCreate
public class ViewTempInit {
	@Logger	private Log log;
	
	@In	private EntityManager entityManager;
	private List<String> portList = new ArrayList<String>();
	private List<String> cPartyList = new ArrayList<String>();
	private List<Map<String, String>> resultMap = new ArrayList<Map<String, String>>();
	public List<Map<String, String>> getResultMap() {
		return resultMap;
	}

	public void setResultMap(List<Map<String, String>> resultMap) {
		this.resultMap = resultMap;
	}

	private List<String> measureList = new ArrayList<String>();
	private List<Map.Entry<String,String>> measureList1 = new ArrayList<Map.Entry<String,String>>();
	public List<Map.Entry<String,String>> getMeasureList1() {
		return measureList1;
	}
	public void setMeasureList1(List<Map.Entry<String,String>> measureList1) {
		this.measureList1 = measureList1;
	}

	private Map<String, String> measureMap = new HashMap<String, String>();
	private String selectPort;
	
	
	public String getSelectPort() {
		return selectPort;
	}


	public void setSelectPort(String selectPort) {
		this.selectPort = selectPort;
	}


	public List<String> getcPartyList() {
		return cPartyList;
	}


	public void setcPartyList(List<String> cPartyList) {
		this.cPartyList = cPartyList;
	}


	public ViewTempInit(){
		System.out.println("Construction FullPortfolioInit");
	}
	
	
//	************************Getter and Setter********************
	public List<String> getPortList() {
		return portList;
	}
	public void setPortList(List<String> portList) {
		this.portList = portList;
	}
	
//	public List<Map<String, String>> getMeasureList1() {
//		return measureList1;
//	}
//	public void setMeasureList1(List<Map<String, String>> measureList1) {
//		this.measureList1 = measureList1;
//	}


	public List<String> getMeasureList() {
		return measureList;
	}


	public void setMeasureList(List<String> measureList) {
		this.measureList = measureList;
	}


	public Map<String, String> getMeasureMap() {
		return measureMap;
	}
	public void setMeasureMap(Map<String, String> measureMap) {
		this.measureMap = measureMap;
	}

	private List<MeasureDao> daoList = new ArrayList<MeasureDao>();
	public List<MeasureDao> getDaoList() {
		return daoList;
	}
	public void setDaoList(List<MeasureDao> daoList) {
		this.daoList = daoList;
	}

	@Create
	public void create(){
		setMeasure();
	}

	private void setMeasure(){
		daoList.add(new MeasureDao("01.��ΰ�", "0.Exposure"));
		daoList.add(new MeasureDao("02.�򰡱ݾ�", "0.Exposure"));
		daoList.add(new MeasureDao("03.�ſ�ȯ���", "0.Exposure"));
		daoList.add(new MeasureDao("10.�����帧", "0.Expousre"));
		
		
		daoList.add(new MeasureDao("11.Delta", "1.�ΰ���"));
		daoList.add(new MeasureDao("12.Gamma", "1.�ΰ���"));
		daoList.add(new MeasureDao("13.Vega", "1.�ΰ���"));
		daoList.add(new MeasureDao("14.Theta", "1.�ΰ���"));
		daoList.add(new MeasureDao("15.Rho", "1.�ΰ���"));
		
		daoList.add(new MeasureDao("16.�෹�̼�", "1.�ΰ���"));
		daoList.add(new MeasureDao("17.������Ƽ", "1.�ΰ���"));
		daoList.add(new MeasureDao("18.DV01", "1.�ΰ���"));
		
		
		daoList.add(new MeasureDao("21.DailyPL", "2.�򰡼���"));
		daoList.add(new MeasureDao("22.WeeklyPL", "2.�򰡼���"));
		daoList.add(new MeasureDao("23.MonthlPL", "2.�򰡼���"));
		daoList.add(new MeasureDao("24.QuarterlyPL", "2.�򰡼���"));
		daoList.add(new MeasureDao("25.FiscalPL", "2.�򰡼���"));
		
		daoList.add(new MeasureDao("26.DeltaPL", "2.���ͺ���"));
		daoList.add(new MeasureDao("27.GammmaPL", "2.���ͺ���"));
		daoList.add(new MeasureDao("28.VegaPL", "2.���ͺ���"));
		daoList.add(new MeasureDao("29.ThetaPL", "2.���ͺ���"));
		daoList.add(new MeasureDao("30.RhoPL", "2.���ͺ���"));
		
		daoList.add(new MeasureDao("31.RMVaR", "3.VaR"));
		daoList.add(new MeasureDao("32.HSVaR", "3.VaR"));
		daoList.add(new MeasureDao("33.MCVaR", "3.VaR"));
		
		daoList.add(new MeasureDao("41.RM_EQ_VaR", "4.Factor_VaR"));
		daoList.add(new MeasureDao("42.RM_IR_VaR", "4.Factor_VaR"));
		daoList.add(new MeasureDao("43.RM_FX_VaR", "4.Factor_VaR"));
		daoList.add(new MeasureDao("44.RM_CO_VaR", "4.Factor_VaR"));
		
		daoList.add(new MeasureDao("45.HS_EQ_VaR", "4.Factor_VaR"));
		daoList.add(new MeasureDao("46.HS_IR_VaR", "4.Factor_VaR"));
		daoList.add(new MeasureDao("47.HS_FX_VaR", "4.Factor_VaR"));
		daoList.add(new MeasureDao("48.HS_CO_VaR", "4.Factor_VaR"));
		
		daoList.add(new MeasureDao("51.��Ÿ�����", "5.�����"));
		daoList.add(new MeasureDao("52.���������", "5.�����"));
		daoList.add(new MeasureDao("53.���������", "5.�����"));
		daoList.add(new MeasureDao("54.�ɼ������", "5.�����"));
		
		
		

		measureList.add("99%RMVaR");
		measureList.add("99%HSVaR");
		measureList.add("99%MCVaR");
		
		measureList.add("99%RM_EQ_VaR");
		measureList.add("99%RM_IR_VaR");
		measureList.add("99%RM_FX_VaR");
		measureList.add("99%RM_CO_VaR");
		
		measureList.add("99%HS_EQ_VaR");
		measureList.add("99%HS_IR_VaR");
		measureList.add("99%HS_FX_VaR");
		measureList.add("99%HS_CO_VaR");
		
		portList.add("����");
		portList.add("���ڰ��к�");
		portList.add("DS��");
		portList.add("FICC��");
		
		cPartyList.add("���� BNP");
		cPartyList.add("�������");
		cPartyList.add("��Ŭ���̽�");
		cPartyList.add("��常�轺");
		
		cPartyMeasureList.add("�ְ�����");
		cPartyMeasureList.add("��������");
		cPartyMeasureList.add("�б����");
		cPartyMeasureList.add("�����");
		
		cPartyMeasureList.add("��Ÿ�����");
		cPartyMeasureList.add("��ü���");
		cPartyMeasureList.add("�ſ�ȯ����");
		cPartyMeasureList.add("Potential Exposure");

		
		underlyingList.add("KOSPI200");
		underlyingList.add("SPX");
		underlyingList.add("CD91");
		underlyingList.add("CRUDE OIL");
		
		cPartyMeasureList.add("%��Ÿ");
		cPartyMeasureList.add("�ݾ׵�Ÿ");
		cPartyMeasureList.add("%����");
		cPartyMeasureList.add("�ݾװ���");
	
		pricingList.add("������ġ��");
		pricingList.add("�ΰ��� ����");
		pricingList.add("RM VaR");
		pricingList.add("HS VaR");
		
		posList.add("�ѱ�����(DLS)367");
		posList.add("������(ELS)2673");
		posList.add("���ھƺ�(ELS)4427");
		posList.add("��ܰŷ�(WRT)40");
		
		dateList.add("20140129");
		dateList.add("20140128");
		dateList.add("20140127");
		dateList.add("20140126");
		
		sceList.add("HS_VaR_01");
		sceList.add("HS_VaR_02");
		sceList.add("HS_VaR_03");
		sceList.add("SENS_EQ_01");
		sceList.add("SENS_IR_01");
		
		resMaturityList.add("M01");
		resMaturityList.add("M03");
		resMaturityList.add("M06");
		resMaturityList.add("Y01");
		resMaturityList.add("Y02");
		resMaturityList.add("Y03");
		resMaturityList.add("Y03+");
		Map<String, String> tempMap = new HashMap<String, String>();
		for(String aa : portList){
			tempMap.put("Portfolio", aa);
			for(String  bb : posList){
				tempMap.put("Position", bb);
			}
		}
	}
	
	private List<String> cPartyMeasureList = new ArrayList<String>();
	private List<String> underlyingList = new ArrayList<String>();
	private List<String> pricingList = new ArrayList<String>();
	private List<String> posList = new ArrayList<String>();
	private List<String> filteredPosList ;
	private List<String> resMaturityList = new ArrayList<String>();
	private List<String> dateList = new ArrayList<String>();
	private List<String> sceList = new ArrayList<String>();
	
	
	public List<String> getResMaturityList() {
		return resMaturityList;
	}
	public void setResMaturityList(List<String> resMaturityList) {
		this.resMaturityList = resMaturityList;
	}
	public List<String> getFilteredPosList() {
		return filteredPosList;
	}
	public void setFilteredPosList(List<String> filteredPosList) {
		this.filteredPosList = filteredPosList;
	}
	public List<String> getSceList() {
		return sceList;
	}


	public void setSceList(List<String> sceList) {
		this.sceList = sceList;
	}


	public List<String> getDateList() {
		return dateList;
	}


	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}


	public List<String> getPosList() {
		return posList;
	}


	public void setPosList(List<String> posList) {
		this.posList = posList;
	}


	public List<String> getPricingList() {
		return pricingList;
	}
	public void setPricingList(List<String> pricingList) {
		this.pricingList = pricingList;
	}
	public List<String> getcPartyMeasureList() {
		return cPartyMeasureList;
	}
	public void setcPartyMeasureList(List<String> cPartyMeasureList) {
		this.cPartyMeasureList = cPartyMeasureList;
	}
	public List<String> getUnderlyingList() {
		return underlyingList;
	}
	public void setUnderlyingList(List<String> underlyingList) {
		this.underlyingList = underlyingList;
	}


	public void save(){
		
	}
	

}
