package com.eugenefe.enums.raw;

public enum EElsReturnEvaluationType {
	 ANY("01", "Any Underlying")
	,ALL ("02", "All Underlying")
	, AVG("03", "Average")
	,CUM_MIN("04", "Cummulative Min")
	,CUM_MAX("05", "Cummulative Max")
	,FIVE_DAYS("06", "Consecutive 5 Days")
	,TEN_DAYS("07", "Consective 10 Days")
	;
	
	private String intCode;
	// private String kisCode;
	private String desc;

	private EElsReturnEvaluationType(String intCode, String desc) {
		this.intCode = intCode;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public String getIntCode() {
		return intCode;
	}
	public static EElsReturnEvaluationType getEnum(String intCode){
		for(EElsReturnEvaluationType aa : EElsReturnEvaluationType.values()){
			if( aa.getIntCode().equals(intCode)){
				return aa;
			}
		}
		return null;
	}	
}
