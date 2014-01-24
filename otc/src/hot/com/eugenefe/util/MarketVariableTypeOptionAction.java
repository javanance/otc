package com.eugenefe.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;

import com.eugenefe.enums.EBoolean;


@Name("mvTypeOptionAction")
@Scope(ScopeType.SESSION)
//@Startup
//@Scope(ScopeType.CONVERSATION)
public class MarketVariableTypeOptionAction implements Serializable {

//	@Out
	private SelectItem[] productTypeOption;

//	@Out
	private SelectItem[] mvTypeOption;

//	@Out
	private SelectItem[] allMvTypeOption;
	
	private SelectItem[] booleanType;
	
	
	
	/*private List<EUnderlying> underlyingType;
	public List<EUnderlying> getUnderlyingType() {
		return underlyingType;
	}
	public void setUnderlyingType(List<EUnderlying> underlyingType) {
		this.underlyingType = underlyingType;
	}*/
	
	public SelectItem[] getBooleanType() {
		return booleanType;
	}
	public void setBooleanType(SelectItem[] booleanType) {
		this.booleanType = booleanType;
	}

	private List<MarketVariableType> underlyingType;
	public List<MarketVariableType> getUnderlyingType() {
		return underlyingType;
	}
	public void setUnderlyingType(List<MarketVariableType> underlyingType) {
		this.underlyingType = underlyingType;
	}

	public MarketVariableTypeOptionAction() {
	}

////	@Factory(value="productTypeOption")
//	public void initProductTypeOption() { 
//		initOption();
//    }
//
////	@Factory(value="mvTypeOption")
//	public void initMvTypeOption() { 
//		initOption();
//    }
//
////	@Factory(value="allMvTypeOption")
//	public void initAllMvTypeOption() { 
//		initOption();
//    }

	@Create
	public void initOption() {
		int cnt=0 ;
		int prodInx=0;
		int mvInx=0;
		int totalSize =MarketVariableType.values().length;
		int prodSize = MarketVariableType.getProductTypes().size();

		allMvTypeOption = new SelectItem[totalSize+ 1];
		allMvTypeOption[0] = new SelectItem("", "All");  

		productTypeOption = new SelectItem[prodSize+1];
		productTypeOption[0] = new SelectItem("", "All");

		mvTypeOption = new SelectItem[totalSize - prodSize + 1];
		mvTypeOption[0] = new SelectItem("", "All");
		
		booleanType = new SelectItem[3];
		booleanType[0]=new SelectItem("", "All");
		booleanType[1]=new SelectItem(EBoolean.Y, EBoolean.Y.getName());
		booleanType[2]=new SelectItem(EBoolean.N, EBoolean.N.getName());
		
		underlyingType = new ArrayList<MarketVariableType>();
		for(MarketVariableType aa : MarketVariableType.values()){
			cnt=cnt+1;
			allMvTypeOption[cnt] = new SelectItem(aa,aa.getType());
			if(aa.isProduct()){
				prodInx = prodInx+1;
				productTypeOption[prodInx] = new SelectItem(aa.getType(),aa.getType());
			}
			else{
				mvInx =mvInx+1;
				mvTypeOption[mvInx] = new SelectItem(aa.getType(),aa.getType());
			}
			if(aa.isUnderlying()){
				underlyingType.add(aa);
			}
		}
    }

	public SelectItem[] getProductTypeOption() {
		return productTypeOption;
	}

	public void setProductTypeOption(SelectItem[] productTypeOption) {
		this.productTypeOption = productTypeOption;
	}

	public SelectItem[] getMvTypeOption() {
		return mvTypeOption;
	}

	public void setMvTypeOption(SelectItem[] mvTypeOption) {
		this.mvTypeOption = mvTypeOption;
	}

	public SelectItem[] getAllMvTypeOption() {
		return allMvTypeOption;
	}

	public void setAllMvTypeOption(SelectItem[] allMvTypeOption) {
		this.allMvTypeOption = allMvTypeOption;
	}
	
}