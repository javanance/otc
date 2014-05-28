package com.eugenefe.enums.raw;

public enum EElsTrDetailType {
	   IN_OTC("01", "당사 옵션")
	  ,EX_OTC("02", "타사옵션")
	  ,IN_BOND_OTC("03", "당사 채권+옵션")
	  ,EX_BOND_OTC("04", "타사채권+옵션")
	, IN_ELS("05", "당사ELS")
	, EX_ELS("06", "타사ELS")
	, SWAP1("07", "스왑1")
	, OTC("08", "장외옵션")
	, SWAP2("09", "스왑2")
	;
	
	private String intCode;
	// private String kisCode;
	private String desc;

	private EElsTrDetailType(String intCode, String desc) {
		this.intCode = intCode;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public String getIntCode() {
		return intCode;
	}
	
	public static EElsTrDetailType getEnum(String intCode){
		for(EElsTrDetailType aa : EElsTrDetailType.values()){
			if( aa.getIntCode().equals(intCode)){
				return aa;
			}
		}
		return null;
	}	
}
