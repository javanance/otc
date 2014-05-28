package com.eugenefe.enums;

import java.util.Arrays;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


public enum ECompound {
	 SMP		(1) 
	 	{ 	public double getIntFactor(double rate, double timeFactor){
	 			return (1+ rate* timeFactor);
	 		}
	 		public double getIntRateFrom(double intFactor, double timeFactor){
	 			return (intFactor - 1)/timeFactor;
	 		}
	 	},
	 DAY 		(365),
	 WEEK 		(52),
	 BI_WEEK 	(26),
	 MON 		(12),
	 BI_MON 	(6),
	 QURT 		(4),
	 SEMI_ANNU	(2),
	 ANNU 		(1),
	 CONT 		(-1)
	 	{ 	public double getIntFactor(double rate, double timeFactor){
	 			return Math.exp(rate*timeFactor);
	 		}
	 		public double getIntRateFrom(double intFactor, double timeFactor){
	 			return Math.log(intFactor)/timeFactor;
	 		}
	 	};
	
//	private final static Logger logger = LoggerFactory.getLogger(ECompound.class);	
	/**
	 * @uml.property  name="frequency"
	 */
	public int frequency ;
	
	private ECompound ( int freq){
		this.frequency = freq;
	}
	

	
	//public double intFactor(int freq, double rate, double timeFactor) {
	//	return Math.pow((1+ rate/freq),(freq* timeFactor));
		//return (1+ rate* timeFactor);
	//}
	
	public  double getIntFactor(double rate, double timeFactor){
//		logger.debug("intFactor is calculated with {},{}",rate,timeFactor);
		return Math.pow((1+ rate/frequency),(frequency* timeFactor));
		
	}
	public  double getDisFactor (double rate, double timeFactor){
		return 1/getIntFactor (rate, timeFactor);
	}
	
	public double getIntRateFrom(double intFactor, double timeFactor){
		if(timeFactor ==0) {
			return 0.0;
		}
		return frequency * ( Math.pow(intFactor, 1/(frequency*timeFactor))-1);
	}
	public static List<ECompound> getList(){
		return  Arrays.asList(ECompound.values());
	}	
}
