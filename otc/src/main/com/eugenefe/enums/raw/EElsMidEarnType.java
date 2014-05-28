package com.eugenefe.enums.raw;

public enum EElsMidEarnType {
	MonthCoupon("01", "Monthly Coupon")
	, AccruedInterest("02", "Accrued Interest")
	, EarlyRedemption("03", "Early Redemption")
	, RangeAccrual("04", "Range Accrual")
	, MonthPayment("05", "Monthly Payment")
	, MonthDoubleJump("06", "Monthly Double Jump")
	, Other("99", "Others");
	
	private String intCode;
	// private String kisCode;
	private String desc;

	private EElsMidEarnType(String intCode, String desc) {
		this.intCode = intCode;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public String getIntCode() {
		return intCode;
	}

	public static EElsMidEarnType getEnum(String intCode){
		for(EElsMidEarnType aa : EElsMidEarnType.values()){
			if( aa.getIntCode().equals(intCode)){
				return aa;
			}
		}
		return null;
	}	
}
