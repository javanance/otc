
package com.eugenefe.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.eugenefe.enums.EMaturity;


/**
 * @author takion77@gmail.com
 *
 */


@SuppressWarnings("serial")
public class FnCalendar extends GregorianCalendar {
	
	public static final long ONE_DAY_IN_MILLISECOND = 86400000;

	protected FnCalendar() {
	}

//	public FnCalendar(Date date){
//		Calendar cal =Calendar.getInstance();
//		cal.setTime(date);
//		(cal.get(YEAR),cal.get(MONTH),cal.get(DATE));
//	}
	public FnCalendar(int year, int month, int day) {
//		super(year, month, day,12,0,0);
		super(year, month, day);
	}
	
	/**
	 * static method for getting the Instance of FnCalendar
	*/
	
	public static FnCalendar getInstance(){
		Calendar date =Calendar.getInstance();
		return new FnCalendar(date.get(YEAR),date.get(MONTH),date.get(DATE));
	}
	
	public static FnCalendar getInstance(Date date){
		Calendar cal =Calendar.getInstance();
		cal.setTime(date);
		return new FnCalendar(cal.get(YEAR),cal.get(MONTH),cal.get(DATE));
	}
	
	public static FnCalendar getInstance(String bssd){
		int year = Integer.parseInt(bssd.substring(0, 4));
		int month = Integer.parseInt(bssd.substring(4,6));
		int day = Integer.parseInt(bssd.substring(6,8));

		return new FnCalendar(year, month-1, day);
	}
	
	/**
	 * get the Month of this Calendar in Real World  
	 */
	public int getRealMonth() {
		return get(MONTH)+1;
	}
	
	/**
	 * Add term to the FnCalendar. 
	 * <p>I need this method because the return type of Add Method in Super Class is void
	 * @param 
	 * @return FnCalendar that is added by amount with unit field
	 */
	public FnCalendar addTerm(int field, int amount) {
		
		//FinancialCalendarC addDate = new FinancialCalendarC(year,month, day);
		FnCalendar addCal;
		addCal = (FnCalendar)this.clone();
		
		addCal.add(field,amount);
		
		return addCal;
	}
	/**
	 * Add term to the FnCalendar.
	 * <p>I need this method because the return type of Add Method in Super Class is void
	 * @param 
	 * @return FnCalendar that is added by amount with unit field and shifted to the end of month if the FnCalendar is End of Month 
	 */
	
	public FnCalendar addTerm(int field, int amount, boolean EOM) {
		FnCalendar addCal;
		addCal = addTerm(field,amount);
		if (EOM && this.isEndOfMonth() 
				&& ( field == Calendar.MONTH ||field==Calendar.YEAR)) {
			int eomDay = addCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			addCal.set(Calendar.DATE, eomDay);
			//System.out.println(addCal.toString());
		}
		return addCal;
	}

	/**
	 * Add term to the FnCalendar.
	 * <p>I need this method because the return type of Add Method in Super Class is void
	 * @param 
	 * @return FnCalendar that is added by the Maturity Code 
	 */	
	
	public FnCalendar addTerm(EMaturity maturity, boolean EOM){
		int field  = maturity.Type;
		int amount = maturity.num ;
		
		return addTerm(field,amount,EOM);
	}
	public FnCalendar addTerm(EMaturity maturity, int amount, boolean EOM){
		return addTerm(maturity.Type,amount* maturity.num,EOM);
	}
	
	public FnCalendar minusTerm(EMaturity maturity, boolean EOM){
		int field  = maturity.Type;
		int amount = -1* maturity.num ;
		
		return addTerm(field,amount,EOM);
	}
	public FnCalendar minusTerm(EMaturity maturity, int amount, boolean EOM){
		return addTerm(maturity.Type,-1*amount* maturity.num,EOM);
	}
	
	/**
	 * Get the days of between this object and date parameter
	 * @param 
	 * @return  numbers between this object and date parameter
	*/
	public int differDays(FnCalendar date) {
		
		return (int) Math.abs((this.getTimeInMillis() - date.getTimeInMillis()) 
									/ ONE_DAY_IN_MILLISECOND );
	}
	
	public int differTerms(FnCalendar date, EMaturity maturity){
		int cnt =1;
		if ( this.equals(date)){
			return 0;
		}
		else if(this.before(date)){
			while (this.addTerm(maturity, cnt,true).before(date)){
				cnt =cnt+1;
			}
			return -1* cnt ;
		}
		else {
			while(date.addTerm(maturity, cnt,true).before(this)){
				cnt = cnt+1;
			}
			return  cnt;
		}
	}
	public FnCalendar getMax(FnCalendar cal){
		if(this.before(cal)){
			return cal; 
		}
		return this;
	}
	
	public FnCalendar getMin(FnCalendar cal){
		if( this.before(cal)){
			return this;
		}
		return cal;
	}
	/**
	 * Return the boolean whether this Calendar is Leap year or not 
	 * @param
	 * @return
	 * 
	*/
	public boolean isLeap() {
		return this.isLeapYear(this.get(Calendar.YEAR));
	}
	
	/**
	 * Get number of Leap days between this Calendar and the given date
	 * <p> In counting leap day, From Date is excluded and To Date is included
	 * 
	 */
	public int getNumOfLeap(FnCalendar date) {
		int count=0;
		int iter =0;
		FnCalendar startCal;
		FnCalendar endCal;
		
		if (this.before(date))  {
			startCal = this;
			endCal = date;
		}
		else { 
			startCal = date;
			endCal = this;
		}
		//FnCalendar cal = new FnCalendar(startCal.get(Calendar.YEAR),Calendar.FEBRUARY,
		//						startCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		FnCalendar cal = new FnCalendar(startCal.get(Calendar.YEAR),Calendar.FEBRUARY,1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				
		FnCalendar addedCal = cal ;	
		//System.out.println(startCal.toString() +"-"+endCal.toString() +"-"+ cal.toString());
		do {
			if( startCal.before(addedCal) && !(endCal.before(addedCal))&& addedCal.isLeap()){
				count =count+1;
			}
			iter = iter+1;
			addedCal =cal.addTerm(YEAR, iter,true);
			
			//System.out.println(count+"-"+iter+"-"+addedCal.toString()+"-"+cal.toString());
		} 	while( !(endCal.before(addedCal)));
		
		return count;
	}
	
	/**
	 * Check whether this is end of month
	 * @param
	 * @return
	 *  
	*/
	public boolean isEndOfMonth() {
		if (this.get(Calendar.DATE)== this.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			return true;
		}
		else return false; 
	}
	
	/**
	 * Check that month of this is February
	 * @param
	 * @return
	*/
	public boolean isFebruary() {
		if (this.get(Calendar.MONTH)==Calendar.FEBRUARY) {
			return true;
		}
			return false;
	}
	
	/**
	 * Get number of days that elapsed from the first day of this
	 * 
	 */
	public int getElapseDays() {
		//FinancialCalendarC cal = new FinancialCalendarC(this.get(YEAR), 1,1);
		//return this.differDays(cal);
		return this.get(DAY_OF_YEAR);
		
	}
	
	/**
	 * Get the number of days that remain to the last day of this
	*/
	public int getRemainDays() {
		//FinancialCalendarC cal = new FinancialCalendarC(this.get(YEAR), 12,31);
		//return this.differDays(cal)+1;
		return this.getActualMaximum(DAY_OF_YEAR) - this.get(DAY_OF_YEAR);
	}
	
	

	public String toStringDot(){
		String tMon= "";
		String tDay ="";
		if (this.getRealMonth() < 10 ) {
			 tMon= "0";
		}
		
		if (this.get(Calendar.DATE)<10){
			tDay = "0";
		}
		//System.out.println(this.getTimeInMillis());
		//System.out.println(this.getTime().getTime());
		//System.out.println(((GregorianCalendar)this).toString());
		return this.get(Calendar.YEAR)+"." + tMon +this.getRealMonth()
							+". " + tDay +this.get(Calendar.DATE);
		
	}
	public String toStringSlash(){
		String tMon= "";
		String tDay ="";
		if (this.getRealMonth() < 10 ) {
			 tMon= "0";
		}
		
		if (this.get(Calendar.DATE)<10){
			tDay = "0";
		}
		//System.out.println(this.getTimeInMillis());
		//System.out.println(this.getTime().getTime());
		//System.out.println(((GregorianCalendar)this).toString());
		return this.get(Calendar.YEAR)+"/" + tMon +this.getRealMonth()
							+"/" + tDay +this.get(Calendar.DATE);
		
	}
	@Override
	/**
	 * Get the title of FnCalendar showed in the UI or in the DB or in others
	 * Now the method support only one type of "YYYY. MM .DD" 
	*/
	public String toString(){
		String tMon= "";
		String tDay ="";
		if (this.getRealMonth() < 10 ) {
			 tMon= "0";
		}
		
		if (this.get(Calendar.DATE)<10){
			tDay = "0";
		}
		//System.out.println(this.getTimeInMillis());
		//System.out.println(this.getTime().getTime());
		//System.out.println(((GregorianCalendar)this).toString());
		return this.get(Calendar.YEAR)+"-" + tMon +this.getRealMonth()
							+"-" + tDay +this.get(Calendar.DATE);
//		return this.get(Calendar.YEAR)+"/" + tMon +this.getRealMonth()
//		+"/" + tDay +this.get(Calendar.DATE);
		
	}
	
	/**
	 * Get the FnCalendar from the Date Class
	 * <p> I need this method to convert Date to FnCalendar because the return type of the Date from DB is almost the Date Class 
	*/
	public static FnCalendar getFnCalFrom(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return  new FnCalendar (cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE));
	}
	
	@Override
	public boolean equals( Object other){
//		return super.equals(other);
		return other instanceof FnCalendar && this.toString() ==((FnCalendar)other).toString();
	}
	@Override
	public int hashCode(){
		return this.get(Calendar.YEAR);
//		return super.hashCode();
	}
}
