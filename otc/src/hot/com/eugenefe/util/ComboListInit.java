package com.eugenefe.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.primefaces.model.TreeNode;

import com.eugenefe.entity.Bizunit;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.PricingDll;
import com.eugenefe.entity.PricingMapping;
import com.eugenefe.entity.PricingMaster;
import com.eugenefe.enums.ECompound;
import com.eugenefe.enums.EDayCount;
import com.eugenefe.enums.EEquation;
import com.eugenefe.enums.EIrCurveType;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.enums.EPricingObject;
import com.eugenefe.enums.ERfType;
import com.eugenefe.enums.EShockType;
import com.eugenefe.enums.EVolType;
import com.eugenefe.enums.raw.EElsOptionType;
import com.eugenefe.enums.raw.EElsTrDetailType;
import com.eugenefe.util.RiskMeasureGroup;

@Name("comboListInit")
@Scope(ScopeType.SESSION)
public class ComboListInit {
	@Logger		private Log log;	 
	@In(create=true) private Map<String, String> messages;
	@In			private Session session;
	
	
	private List<EPricingObject> prObjList = new ArrayList<EPricingObject>();
	private List<EIrCurveType> irTypeList = new ArrayList<EIrCurveType>();
	private List<EVolType> volTypeList = new ArrayList<EVolType>();
	private List<PricingDll> dllList = new ArrayList<PricingDll>();
	private List<EEquation> equationList = new ArrayList<EEquation>();
	private List<ERfType> rfTypeList = new ArrayList<ERfType>();
	private List<EShockType> shockTypeList = new ArrayList<EShockType>();
	private List<ECompound> compoundTypeList = new ArrayList<ECompound>();
	private List<EDayCount> dayCountList = new ArrayList<EDayCount>(); 
	
	private List<EElsTrDetailType> elsTrDetailList = new ArrayList<EElsTrDetailType>();
	private List<EElsOptionType> elsOptTypeList = new ArrayList<EElsOptionType>();
	private List<Bizunit> fundList = new ArrayList<Bizunit>();
	private List<Bizunit> orgList = new ArrayList<Bizunit>();
	private List<Bizunit> bizUnitList = new ArrayList<Bizunit>();
	private List<EMaturity> resMatList = new ArrayList<EMaturity>();
	
	public ComboListInit(){
	}

    
    @Create
    public void create(){
    	dllList = session.createCriteria(PricingDll.class).list();
    	prObjList = Arrays.asList(EPricingObject.values());
		irTypeList = Arrays.asList(EIrCurveType.values());
		volTypeList = Arrays.asList(EVolType.values());
		equationList = Arrays.asList(EEquation.values());
		rfTypeList = Arrays.asList(ERfType.values());
		shockTypeList = Arrays.asList(EShockType.values());
		compoundTypeList = Arrays.asList(ECompound.values());
		dayCountList = Arrays.asList(EDayCount.values());
		elsOptTypeList= Arrays.asList(EElsOptionType.values());
		elsTrDetailList = Arrays.asList(EElsTrDetailType.values());
		
		resMatList.add(EMaturity.M01);
		resMatList.add(EMaturity.M03);
		resMatList.add(EMaturity.M06);
		resMatList.add(EMaturity.M09);
		resMatList.add(EMaturity.Y01);
		resMatList.add(EMaturity.Y02);
		resMatList.add(EMaturity.Y03);
		resMatList.add(EMaturity.Y05);
		
		bizUnitList = session.createCriteria(Bizunit.class).list();
		for(Bizunit aa : bizUnitList){
			
			if(aa.getUsable().getValue() && aa.getOrgId().length()< 8){
				orgList.add(aa);
			}else if(aa.getUsable().getValue() && aa.getOrgId().length() ==8){
				fundList.add(aa);
			}
		}
    }

  //***********************************Getter and Setter********************
    
	public List<EPricingObject> getPrObjList() {
		return prObjList;
	}
	public void setPrObjList(List<EPricingObject> prObjList) {
		this.prObjList = prObjList;
	}
	public List<PricingDll> getDllList() {
		return dllList;
	}
	public void setDllList(List<PricingDll> dllList) {
		this.dllList = dllList;
	}
	public List<EIrCurveType> getIrTypeList() {
		return irTypeList;
	}
	public void setIrTypeList(List<EIrCurveType> irTypeList) {
		this.irTypeList = irTypeList;
	}
	public List<EVolType> getVolTypeList() {
		return volTypeList;
	}
	public void setVolTypeList(List<EVolType> volTypeList) {
		this.volTypeList = volTypeList;
	}

	public List<EEquation> getEquationList() {
		return equationList;
	}
	public void setEquationList(List<EEquation> equationList) {
		this.equationList = equationList;
	}
	public List<ERfType> getRfTypeList() {
		return rfTypeList;
	}
	public void setRfTypeList(List<ERfType> rfTypeList) {
		this.rfTypeList = rfTypeList;
	}
	public List<EShockType> getShockTypeList() {
		return shockTypeList;
	}
	public void setShockTypeList(List<EShockType> shockTypeList) {
		this.shockTypeList = shockTypeList;
	}
	public List<ECompound> getCompoundTypeList() {
		return compoundTypeList;
	}
	public void setCompoundTypeList(List<ECompound> compoundTypeList) {
		this.compoundTypeList = compoundTypeList;
	}
	public List<EDayCount> getDayCountList() {
		return dayCountList;
	}
	public void setDayCountList(List<EDayCount> dayCountList) {
		this.dayCountList = dayCountList;
	}


	public List<EElsOptionType> getElsOptTypeList() {
		return elsOptTypeList;
	}


	public void setElsOptTypeList(List<EElsOptionType> elsOptTypeList) {
		this.elsOptTypeList = elsOptTypeList;
	}


	public List<EElsTrDetailType> getElsTrDetailList() {
		return elsTrDetailList;
	}


	public void setElsTrDetailList(List<EElsTrDetailType> elsTrDetailList) {
		this.elsTrDetailList = elsTrDetailList;
	}


	public List<Bizunit> getFundList() {
		return fundList;
	}


	public void setFundList(List<Bizunit> fundList) {
		this.fundList = fundList;
	}


	public List<Bizunit> getOrgList() {
		return orgList;
	}


	public void setOrgList(List<Bizunit> orgList) {
		this.orgList = orgList;
	}


	public List<Bizunit> getBizUnitList() {
		return bizUnitList;
	}


	public void setBizUnitList(List<Bizunit> bizUnitList) {
		this.bizUnitList = bizUnitList;
	}


	public List<EMaturity> getResMatList() {
		return resMatList;
	}


	public void setResMatList(List<EMaturity> resMatList) {
		this.resMatList = resMatList;
	}
	
}
