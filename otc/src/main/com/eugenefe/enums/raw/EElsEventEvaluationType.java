package com.eugenefe.enums.raw;

public enum EElsEventEvaluationType {
	 LOW("01", "Low Return")
	,HIGH ("02", "High Return")
	, AVG("03", "Average Return")
	;
	
	private String intCode;
	// private String kisCode;
	private String desc;

	private EElsEventEvaluationType(String intCode, String desc) {
		this.intCode = intCode;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public String getIntCode() {
		return intCode;
	}

	public static EElsEventEvaluationType getEnum(String intCode){
		for(EElsEventEvaluationType aa : EElsEventEvaluationType.values()){
			if( aa.getIntCode().equals(intCode)){
				return aa;
			}
		}
		return null;
	}
}
