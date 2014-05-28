package com.eugenefe.enums.raw;

public enum EElsConditionType {
	 KNOCK_IN( "Knock In")
	,KNOCK_OUT ( "Knock Out")
	,SAFE_GUARD( "Safe Guard")
	,SAFE_ZONE("Safe Zone")
	,CHANGE_UP("Change Up")
	;
	
//	private String intCode;
	// private String kisCode;
	private String desc;

	private EElsConditionType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
