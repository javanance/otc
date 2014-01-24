package com.eugenefe.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;


public class OldTableDynamicColumn  implements Comparable<OldTableDynamicColumn>{
	 

	private String colProperties;
	private List<String> colProperty;
	private String colName;
	
	
	public OldTableDynamicColumn(String colProperties, String colName) {
		this.colProperties = colProperties;
		this.colName = colName;
		this.colProperty = new ArrayList<String>();
//		colProperties.split("{")[1].split("}")[0]
//		for (String splitProp : colProperties.split("{")[1].split("}")[0].split("\\.")) {
//			for (String splitProp : colProperties.split("\\.")) {
				for (String splitProp : colProperties.split("_")) {	
			if(!splitProp.startsWith("#") && !splitProp.endsWith("}") ){
				colProperty.add(splitProp);
			}
		}
	}
	
		
	@Override
	public int compareTo(OldTableDynamicColumn other) {
		return this.colProperties.compareTo(other.colProperties);
	}

	public String getColProperties() {
		return colProperties;
	}

	public void setColProperties(String colProperties) {
		this.colProperties = colProperties;
	}

	public List<String> getColProperty() {
		return colProperty;
	}

	public void setColProperty(List<String> colProperty) {
		this.colProperty = colProperty;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}
	
	
	
	
}
