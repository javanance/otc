package com.eugenefe.enums.raw;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.enums.EOptionType;

public enum EElsRchgType {
	  GE ("01", "GT", EOptionType.CALL,"Greater Than OR Equal")
	 ,LE ("02", "LT", EOptionType.PUT,"Less Than OR Equal")
	 ,GT ("03", "GT", EOptionType.CALL,"Greater Than")
	 ,LT ("04", "LT", EOptionType.PUT,"Less Than")
	 ;
	private String intCode;
	private String group;
	private EOptionType digitalCallType;
	private String desc;

	
	private EElsRchgType(String intCode, String group, EOptionType digitalCallType, String desc) {
		this.intCode = intCode;
		this.group = group;
		this.digitalCallType = digitalCallType;
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}
	public String getIntCode() {
		return intCode;
	}
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	public EOptionType getDigitalCallType() {
		return digitalCallType;
	}

	public static List<String> getGroupList(String group){
		List<String> rst = new ArrayList<String>();
		
		for(EElsRchgType aa : EElsRchgType.values()){
			if(aa.group.equals(group)){
				rst.add(aa.getIntCode());
			}
		}
		return rst;
	}
	public static EElsRchgType getEnum(String intCode){
		for(EElsRchgType aa : EElsRchgType.values()){
			if( aa.getIntCode().equals(intCode)){
				return aa;
			}
		}
		return null;
	}	
}
