package com.eugenefe.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public enum EMenuGroup {
	   ROOT("")
	 , Master("ROOT")
	 , Product("ROOT")
	 , Position("ROOT")
	 , Portfolio("ROOT")
	 , History("ROOT")
	 , Pricing("ROOT")
	 , OTC("ROOT")  
	 , HiFive("OTC")
//	 , HiFiveDetail("HiFive")
	;
	
	private String upperGroup;
//	private String group;
	
	private EMenuGroup(String upperGroup) {
		this.upperGroup =upperGroup;
	}
	
//	private EMenuGroup(String upperGroup, String group) {
//		this.upperGroup =upperGroup;
//		this.group = group;
//	}


	public String getUpperGroup() {
		return upperGroup;
	}

	public List<EMenuGroup> getChidrenGroup(){
		List<EMenuGroup> rtn = new ArrayList<EMenuGroup>();
		for(EMenuGroup aa : EMenuGroup.values()){
			if(aa!=EMenuGroup.ROOT && this.equals(EMenuGroup.valueOf(aa.getUpperGroup()))){
				rtn.add(aa);
			}
		}
		return rtn;
	}
	
//	public static Map<String, List<EMenuGroup>> getChildMap(){
//		Map<String, List<EMenuGroup>> rtn = new HashMap<String, List<EMenuGroup>>();
//		for(EMenuGroup aa : EMenuGroup.values()){
//			rtn.put(aa.name(), aa.getChidrenGroup());
//		}
//		return rtn;
//	}
//	public String getGroup() {
//		return group;
//	}
//	public List<ENavigationData> getChidrenMenu(){
////		List<ENavigationData> rtn = new ArrayList<ENavigationData>();
////		for(ENavigationData aa : ENavigationData.values()){
////			if(this.equals(aa.getGroup())){
////				rtn.add(aa);
////			}
////		}
////		return rtn;
//		return ENavigationData.getElement(this);
//	}
}
