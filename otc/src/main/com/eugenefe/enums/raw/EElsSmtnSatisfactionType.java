package com.eugenefe.enums.raw;

public enum EElsSmtnSatisfactionType {
	  AND("01", "And ")
	, OR("02", "Or")
	, AVG("03", "Average")
	;
	
	private String intCode;
	// private String kisCode;
	private String desc;

	private EElsSmtnSatisfactionType(String intCode, String desc) {
		this.intCode = intCode;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public String getIntCode() {
		return intCode;
	}
	
	public static EElsSmtnSatisfactionType getEnum(String intCode){
		for(EElsSmtnSatisfactionType aa : EElsSmtnSatisfactionType.values()){
			if( aa.getIntCode().equals(intCode)){
				return aa;
			}
		}
		return null;
	}	
}
