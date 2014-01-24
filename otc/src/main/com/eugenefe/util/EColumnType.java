package com.eugenefe.util;

public enum EColumnType {
	  String("left",false)
	, Number("right",false)
	, Entity("left", false)
	, Map("left",true)
	, List("left",true)
	;
	
	private String align;
	private boolean collection;
	
	
	private EColumnType(String align, boolean isCollection) {
		this.align = align;
		this.collection = isCollection;
	}
	

	public String  getAlign(){
		return align;
	}
	public boolean isCollection(){
		return collection;
	}
}
