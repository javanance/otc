/**
 * 
 */
package com.eugenefe.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @author takion77@gmail.com
 *
 */
public enum EBizDayRuleNew {
	RGL_FLW 
	,MDF_FLW	
	,RGL_PRC 
	,MDF_PRC	
	,NONE ;
	
	public static List<EBizDayRuleNew> getList(){
		return  Arrays.asList(EBizDayRuleNew.values());
	}	

}
