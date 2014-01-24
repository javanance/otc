package com.eugenefe.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@Retention(RetentionPolicy.CLASS)
public @interface AnnoMethodTree {
//	public enum EColumnType { String, Number, Map, List };
	
	int order();
	boolean init() 			default true; 
	EColumnType type()      default EColumnType.String;
	String align() 			default "left";
}
