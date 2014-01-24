package com.eugenefe.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.util.RiskMeasureGroup;

@Name("flagBean")
@Startup
@Scope(ScopeType.SESSION)
//@Scope(ScopeType.CONVERSATION)
public class FlagBean {
	
	private boolean vol;
	private String flagVcvRfType;
	private String vcvMethod;
	private String flagInterval;
	
	
	public ENavigationData navigationData;
	public ENavigationData getNavigationData() {
		return navigationData;
	}
	public void setNavigationData(ENavigationData navigationData) {
		this.navigationData = navigationData;
	}
	
//	public List<ENavigationData> navigationList;
//	public List<ENavigationData> getNavigationList() {
//		return ENavigationData.values();
//	}
//	public void setNavigationList(List<ENavigationData> navigationList) {
//		this.navigationList = navigationList;
//	}
	public ENavigationData[] navigationList;
	
	public ENavigationData[] getNavigationList() {
		return ENavigationData.values();
	}
	
	public FlagBean(){
		System.out.println("Construction FlagBean");
	}

	@Create
	 public void create(){
	    	vol =true;               // volatilty or correlation
	    	flagVcvRfType="IR";			   //VcvMatrix default Rf Type
	    	vcvMethod = "EWMA_1DAY";		// default VCV Method Id
//	    	TODO : xhmtl 에 radion 버튼 정의 부분 수정이 필요하다
	    	flagInterval = EMaturity.Y01.name();    //default radio button
	    	navigationData = ENavigationData.IrCurve;
	 }	
	
   
//    **************************************
	
	public boolean isVol() {
		return vol;
	}

	public void setVol(boolean isVol) {
		this.vol = isVol;
	}

	public String getFlagVcvRfType() {
		return flagVcvRfType;
	}
	
	public void setFlagVcvRfType(String flagVcvRfType) {
		this.flagVcvRfType = flagVcvRfType;
	}

	public String getVcvMethod() {
		return vcvMethod;
	}

	public void setVcvMethod(String vcvMethod) {
		this.vcvMethod = vcvMethod;
	}

	public String getFlagInterval() {
		return flagInterval;
	}

	public void setFlagInterval(String flagInterval) {
		this.flagInterval = flagInterval;
	}

	
	
	
}
