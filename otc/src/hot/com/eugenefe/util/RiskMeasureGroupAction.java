package com.eugenefe.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("riskMeasureGroupAction")
@Scope(ScopeType.SESSION)
public class RiskMeasureGroupAction {
	@In(create=true) 
	private Map<String, String> messages;
	
	
//	@Factory(value="riskMeasureGroup")
//	public List<RiskMeasureGroup> getRiskMeasureGroup() {
//		return Arrays.asList(RiskMeasureGroup.values());  
//    }
	@Factory(value="riskMeasureGroupMap")
	public Map<RiskMeasureGroup, String> getRiskMeasureGroup() {
		Map<RiskMeasureGroup, String> temp  = new HashMap<RiskMeasureGroup, String>();
		for(RiskMeasureGroup aa : RiskMeasureGroup.values()){
			temp.put(aa, messages.get(aa.label));
		}
		return temp;
    }
	
	
	
	
	
	
	public String selectRMG;
	private RiskMeasureGroup selectR;
	
	
	public RiskMeasureGroup getSelectR() {
		return selectR;
	}

	public void setSelectR(RiskMeasureGroup selectR) {
		this.selectR = selectR;
	}

	public String getSelectRMG() {
		return selectRMG;
	}

	public void setSelectRMG(String selectRMG) {
		this.selectRMG = selectRMG;
	}

	public List<String> complete(String query){
		List<String> suggestions = new ArrayList<String>();
		for(RiskMeasureGroup aa : RiskMeasureGroup.values()){
			if(aa.label.contains(query)){
				suggestions.add(aa.label);
			}
		}
		return suggestions;
	}
	
	public List<RiskMeasureGroup> completeR(String query){
		List<RiskMeasureGroup> suggestions = new ArrayList<RiskMeasureGroup>();
		for(RiskMeasureGroup aa : RiskMeasureGroup.values()){
			if(aa.label.contains(query)){
				suggestions.add(aa);
			}
		}
		return suggestions;
	}
}
