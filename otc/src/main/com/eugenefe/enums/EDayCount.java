package com.eugenefe.enums;

import java.util.Calendar;

import com.eugenefe.util.FnCalendar;

public enum EDayCount {

	ACT_ACT(0,0) {
		public double getTimeFactor(FnCalendar fromDate, FnCalendar toDate) {
			double ret;
			if(toDate.after(fromDate)){
				if (fromDate.get(Calendar.YEAR)==toDate.get(Calendar.YEAR)) {
					return fromDate.differDays(toDate)/(double)fromDate.getActualMaximum(Calendar.DAY_OF_YEAR);
				}
				else {
					ret = fromDate.getRemainDays() / (double)fromDate.getActualMaximum(Calendar.DAY_OF_YEAR);
					ret += toDate.getElapseDays() /(double)toDate.getActualMaximum(Calendar.DAY_OF_YEAR);
					ret += toDate.get(Calendar.YEAR)-fromDate.get(Calendar.YEAR)-1;
					return ret;
				}
			}
			else return 0.0; 
		}
	},
//	Excel 에서의 로직임
	ACT_ACT_EX(0,-1){ 
		public double getTimeFactor(FnCalendar fromDate, FnCalendar toDate) {
			int numYear=0;
			int count=0;
			int i =0;
			FnCalendar cal = fromDate;
			numYear = toDate.get(Calendar.YEAR)- fromDate.get(Calendar.YEAR)+1;
			// "less than or equal to year part" 인 조건임
			if(toDate.after(fromDate)){
				if (!toDate.after(fromDate.addTerm(Calendar.YEAR,1))){
				//1) 같은년도이며 윤년인 경우, 2)두 날짜 사이에 윤년이 포함된경우(초일불산입, 말일산입기준) 
					if ((fromDate.isLeap() && fromDate.get(Calendar.YEAR)==toDate.get(Calendar.YEAR))
							|| fromDate.getNumOfLeap(toDate)==1){
						return fromDate.differDays(toDate) / (double)366;
					}
					else return fromDate.differDays(toDate) / (double)365;
				
					
				}
			// 그외의 경우에는 두 날짜사이의 년도의 날수를 평균하여 분모로 사용함
				else {
					for (i=0 ; i<numYear ;i++){
						cal = fromDate.addTerm(Calendar.YEAR,i);
						if(cal.isLeap()){
							count += 1;
						}
					}
			//System.out.println(count +"-"+ numYear +"-"+cal.toString());
					return fromDate.differDays(toDate) * numYear / (double)(365 * numYear + count);
				}
			}
			else return 0.0;
		}
	},
	ACT_365(0,365){
		public double getTimeFactor(FnCalendar fromDate, FnCalendar toDate) {
			if(toDate.after(fromDate)){
				return fromDate.differDays(toDate)/denom;
			}
			else return 0.0;
		}
	},
	ACT_360(0,360){
		public double getTimeFactor(FnCalendar fromDate, FnCalendar toDate) {
			if(toDate.after(fromDate)){
				return fromDate.differDays(toDate)/ denom;
			}
			else return 0.0;
			//return getNumeAct(date1, date2)/denom;
		}
	},
	A30_360(0,360){
		public double getTimeFactor(FnCalendar fromDate, FnCalendar toDate) {
			if(toDate.after(fromDate)){
				return getNumeThirty(fromDate, toDate)/denom;
			}
			else return 0.0;
		}
	},
	A30_360_EX(0,360){
		public double getTimeFactor(FnCalendar fromDate, FnCalendar toDate) {
			if(toDate.after(fromDate)){
				return getNumeThirtyExcel(fromDate, toDate)/denom;
			}
			else return 0.0;
		}
	},
	E30_360(0,360){
		public double getTimeFactor(FnCalendar fromDate, FnCalendar toDate) {
			if(toDate.after(fromDate)){
				return getNumeThirtyEuro(fromDate, toDate)/denom;
			}
			else return 0.0;
		}
	},
	M30_360 (0,360) {
		public double getTimeFactor(FnCalendar fromDate, FnCalendar toDate) {
			if(toDate.after(fromDate)){
				return getNumeThirtyMonth(fromDate, toDate)/denom;
			}
			else return 0.0;
		}
	},
	ACT_365_INC(1,365){
		public double getTimeFactor(FnCalendar fromDate, FnCalendar toDate) {
			if(toDate.after(fromDate)){
				return (fromDate.differDays(toDate)+nume)/denom;
			}
			else return 0.0;
		}
	},
	E30_360_INC(1,360){
		public double getTimeFactor(FnCalendar fromDate, FnCalendar toDate) {
			if(toDate.after(fromDate)){
				return (getNumeThirtyEuro(fromDate, toDate)+ nume)/denom;
			}
			else return 0.0;
		}	
	},
	ACT_365_JPN(0,365){
		public double getTimeFactor(FnCalendar fromDate, FnCalendar toDate) {
			if(toDate.after(fromDate)){
				return (fromDate.differDays(toDate)- fromDate.getNumOfLeap(toDate))/denom;
			}
			else return 0.0;
		}
	},
	ACT_365_L(0,1),
	BIZ_252(0,252),
	MON_CNT(0,12){
		public double getTimeFactor(FnCalendar fromDate, FnCalendar toDate) {
			if(toDate.after(fromDate)){
				return getNumeMonth(fromDate, toDate)/denom;
			}
			else return 0.0;
		}
	}
		
	;
	
	/**
	 * @uml.property  name="nume"
	 */
	public int nume;
	/**
	 * @uml.property  name="denom"
	 */
	public double denom;
	
	private EDayCount( int nume,int denom) {
		this.nume = nume;
		this.denom = denom;
	}
	
/*	public int getNumeAct(FnCalendar date1, FnCalendar date2) {
		return date1.differDays(date2);
	}*/

	public int getNumeThirty(FnCalendar fromDate, FnCalendar toDate) {
		int days ;
		int adjustDay1 ;
		int adjustDay2;
		adjustDay1 = fromDate.get(Calendar.DAY_OF_MONTH);
		adjustDay2 = toDate.get(Calendar.DAY_OF_MONTH);
		
		if (fromDate.isFebruary()&& fromDate.isEndOfMonth()) {
			if (toDate.isFebruary() && toDate.isEndOfMonth()) {
				adjustDay2 = 30;
			}
			adjustDay1 =30;
		}
		
		if (adjustDay2 ==31 && adjustDay1 >=30) {
			adjustDay2 =30;
		}
		
		if (adjustDay1 ==31) adjustDay1 =30;
		
		
		days =	12*(toDate.get(Calendar.YEAR)- fromDate.get(Calendar.YEAR));
		days += toDate.get(Calendar.MONTH)-fromDate.get(Calendar.MONTH);
		days = days*30 ;			
					
		days += (adjustDay2-adjustDay1);
		
		return days;
	}
	public int getNumeThirtyExcel(FnCalendar fromDate, FnCalendar toDate) {
		int days ;
		int adjustDay1 ;
		int adjustDay2;
		
		adjustDay1 = fromDate.get(Calendar.DAY_OF_MONTH);
		adjustDay2 = toDate.get(Calendar.DAY_OF_MONTH);
		
		if (fromDate.isFebruary()&& fromDate.isEndOfMonth()) {
			if (toDate.isFebruary() && toDate.isEndOfMonth()) {
				adjustDay2 = 30;
			}
			adjustDay1 =30;
		}
		
		if (toDate.get(Calendar.DAY_OF_MONTH) ==31 && fromDate.get(Calendar.DAY_OF_MONTH) >=30) {
			adjustDay2 =30;
		}
		
		if (fromDate.get(Calendar.DAY_OF_MONTH) ==31) adjustDay1 =30;
		
		
		days =	12*(toDate.get(Calendar.YEAR)- fromDate.get(Calendar.YEAR));
		days += toDate.get(Calendar.MONTH)-fromDate.get(Calendar.MONTH);
		days = days*30 ;			
					
		days += (adjustDay2-adjustDay1);
		
		return days;
	}
    public int getNumeThirtyMonth(FnCalendar fromDate, FnCalendar toDate){
    	int days ;
		int adjustDay1 ;
		int adjustDay2;
		adjustDay1 = fromDate.get(Calendar.DAY_OF_MONTH);
		adjustDay2 = toDate.get(Calendar.DAY_OF_MONTH);
		
		if (fromDate.isFebruary() && fromDate.isEndOfMonth() && toDate.isEndOfMonth()) adjustDay1 = 30;
		if (toDate.isFebruary() && fromDate.isEndOfMonth() && toDate.isEndOfMonth()) adjustDay2 = 30;
		
		if (adjustDay2 == 31 && adjustDay1 >=30) {
			adjustDay2 =30;
		}
		
		if (adjustDay1 ==31) adjustDay1 =30;
		
		
		days =	12*(toDate.get(Calendar.YEAR)- fromDate.get(Calendar.YEAR));
		days += toDate.get(Calendar.MONTH)-fromDate.get(Calendar.MONTH);
		days = days*30 ;			
					
		days += (adjustDay2-adjustDay1);
		
		return days;
		
    }
	
	public int getNumeThirtyEuro(FnCalendar fromDate, FnCalendar toDate) {
		int days ;
		int adjustDay1;
		int adjustDay2;
		adjustDay1 = fromDate.get(Calendar.DAY_OF_MONTH);
		adjustDay2 = toDate.get(Calendar.DAY_OF_MONTH);
		
		if (adjustDay2 == 31) adjustDay2 = 30;
		if (adjustDay1 == 31) adjustDay1 = 30;
		
		
		days =	12*(toDate.get(Calendar.YEAR)- fromDate.get(Calendar.YEAR));
		days += toDate.get(Calendar.MONTH)-fromDate.get(Calendar.MONTH);
		days = days*30 ;			
					
		days += (adjustDay2-adjustDay1);
		
		return days;
	}
	public int getNumeMonth(FnCalendar fromDate, FnCalendar toDate){
		int months;
		months 	=12*(toDate.get(Calendar.YEAR)- fromDate.get(Calendar.YEAR));
		months += toDate.get(Calendar.MONTH)-fromDate.get(Calendar.MONTH);
		return months;
	}
	
	
	public double getTimeFactor (FnCalendar fromDate, FnCalendar toDate) {
		if(toDate.after(fromDate)){
			return fromDate.differDays(toDate)/(double)365;
		}
		return fromDate.differDays(toDate)/(double)365;
//		else return 0.0;
	}
	
}
