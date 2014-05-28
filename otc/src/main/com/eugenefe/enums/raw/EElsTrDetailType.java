package com.eugenefe.enums.raw;

public enum EElsTrDetailType {
	   IN_OTC("01", "��� �ɼ�")
	  ,EX_OTC("02", "Ÿ��ɼ�")
	  ,IN_BOND_OTC("03", "��� ä��+�ɼ�")
	  ,EX_BOND_OTC("04", "Ÿ��ä��+�ɼ�")
	, IN_ELS("05", "���ELS")
	, EX_ELS("06", "Ÿ��ELS")
	, SWAP1("07", "����1")
	, OTC("08", "��ܿɼ�")
	, SWAP2("09", "����2")
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
