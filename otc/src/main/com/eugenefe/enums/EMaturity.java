package com.eugenefe.enums;

import java.util.Calendar;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//public enum MaturityE implements org.hibernate.usertype.UserType{
public enum EMaturity implements Comparable<EMaturity> { 
	
	SPOT		(1,Calendar.DATE, 1),
	D001		(1,Calendar.DATE, 1),
	D002		(2,Calendar.DATE, 2),
	D003		(3,Calendar.DATE, 3),
	D004		(4,Calendar.DATE, 4),
	D005		(5,Calendar.DATE, 5),
	D006		(6,Calendar.DATE, 6),
	W01         (7,Calendar.DATE,7) ,  
	W02         (14,Calendar.DATE,14) ,
	M01         (1,Calendar.MONTH,30) ,    
	M02         (2,Calendar.MONTH,60) ,    
	M03         (3,Calendar.MONTH,90) ,  
	M04			(4,Calendar.MONTH,120),
	M05			(5, Calendar.MONTH,150),
	M06         (6,Calendar.MONTH,180) ,    
	M09         (9,Calendar.MONTH,270) ,    
	Y01         (1,Calendar.YEAR,365) ,    
	M18         (18,Calendar.MONTH,540) ,
	Y02         (2,Calendar.YEAR,730) ,    
	M30			(30,Calendar.MONTH, 900),
	Y03         (3,Calendar.YEAR,1095) ,  
	Y04			(4,Calendar.YEAR,1460),
	Y05         (5,Calendar.YEAR,1825) ,    
	Y07         (7,Calendar.YEAR,2555) ,    
	Y10         (10,Calendar.YEAR,3650) ,   
	Y20         (20,Calendar.YEAR,7300) ,   
	MATURITY 	 (1000,Calendar.YEAR,10000);     


//	private final static Logger logger = LoggerFactory.getLogger(EMaturity.class);
	public int num;
	public int Type;
	public int numDays;
	
	private EMaturity ( int num, int type, int numDays) {
		this.num = num;
		this.Type =type;
		this.numDays = numDays;
	}
	public double getFractionTo(EMaturity maturity){
		if(this.Type == maturity.Type){
			return this.num/(double)maturity.num;
		}
		else if ( this.Type == Calendar.DATE || maturity.Type==Calendar.DATE){
			return this.numDays / (double) maturity.numDays;
		}
		
		else if(this.Type== Calendar.YEAR) {
			return this.num * 12 / (double) maturity.num;
		}
		else if(maturity.Type == Calendar.YEAR){
			return this.num /  ((double) maturity.num * 12);
		}
		return 0.0;
		
	}
	public double getFrequency(){
		if( this.Type== Calendar.MONTH) return 12/(double)this.num;
		else if (this.Type == Calendar.YEAR) return 1/(double) this.num;
		else return 365/(double)this.numDays;
	}
	public EMaturity getMulitpleMaturity(int multiplier){
		
		for(EMaturity aa: EMaturity.values()){
//			logger.debug("Fraction To:{}", getFractionTo(aa));
			if( 1.0 == multiplier*getFractionTo(aa)){
				return aa;
			}
		}
		return this;
	}
	public String getName(){
		return this.name();
	}
	
//	@Override
//	public public int compareTo(EMaturity other){
//		return ((Integer)this.numDays).compareTo(other.numDays);
//	}
	
}
