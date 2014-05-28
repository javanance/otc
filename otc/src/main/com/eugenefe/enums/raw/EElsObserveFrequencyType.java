package com.eugenefe.enums.raw;

public enum EElsObserveFrequencyType {
	  ANY_TIME("01", "Any Price")
	, CLOSE("02", "Price at Close");
	
	private String intCode;
	// private String kisCode;
	private String desc;

	private EElsObserveFrequencyType(String intCode, String desc) {
		this.intCode = intCode;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public String getIntCode() {
		return intCode;
	}
	public static EElsObserveFrequencyType getEnum(String intCode){
		for(EElsObserveFrequencyType aa : EElsObserveFrequencyType.values()){
			if( aa.getIntCode().equals(intCode)){
				return aa;
			}
		}
		return null;
	}	
}
