package com.eugenefe.converter;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.util.EColumnType;


public class TableDynamicColumn  implements Comparable<TableDynamicColumn>{
	private String columnId;
	private String columnName;
	private EColumnType columnType;
//	private String parentColumnId;
	private TableDynamicColumn parentColumn;
	private int columnLevel;
	private boolean inTheCollection;
	private double columnOrder;
	private boolean initialzied;
	private String alignFormat;
	private List<String> fullColumns;
	private String returnType;
	
	
	public TableDynamicColumn(String columnId, String columnName, EColumnType columnType
//								,String parentColumnId
								,TableDynamicColumn parentColumn
								, int columnLevel, boolean inTheCol, double columnOrder, boolean init, String align) {
		this.columnId = columnId;
		this.columnName = columnName;
		this.columnType = columnType;
		this.parentColumn = parentColumn;
		this.columnLevel = columnLevel;
		this.inTheCollection = inTheCol;
		this.columnOrder = columnOrder;
		this.initialzied = init;
		this.alignFormat = "float:"+align;
		this.fullColumns = new ArrayList<String>();
//		colProperties.split("{")[1].split("}")[0]
//		for (String splitProp : colProperties.split("{")[1].split("}")[0].split("\\.")) {
//			for (String splitProp : colProperties.split("\\.")) {
				for (String splitProp : columnId.split("_")) {	
			if(!splitProp.startsWith("#") && !splitProp.endsWith("}") ){
				fullColumns.add(splitProp);
			}
		}
	}
	
		
	@Override
	public int compareTo(TableDynamicColumn other) {
		return this.columnOrder >= other.columnOrder ? 1 :-1;
	}


	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getColumnLevel() {
		return columnLevel;
	}
	public void setColumnLevel(int columnLevel) {
		this.columnLevel = columnLevel;
	}

	public double getColumnOrder() {
		return columnOrder;
	}
	public void setColumnOrder(double columnOrder) {
		this.columnOrder = columnOrder;
	}

	public List<String> getFullColumns() {
		return fullColumns;
	}
	public void setFullColumns(List<String> fullColumns) {
		this.fullColumns = fullColumns;
	}

//	public String getParentColumnId() {
//		return parentColumnId;
//	}
//	public void setParentColumnId(String parentColumnId) {
//		this.parentColumnId = parentColumnId;
//	}


	public boolean isInitialzied() {
		return initialzied;
	}
	public TableDynamicColumn getParentColumn() {
		return parentColumn;
	}


	public void setParentColumn(TableDynamicColumn parentColumn) {
		this.parentColumn = parentColumn;
	}


	public void setInitialzied(boolean initialzied) {
		this.initialzied = initialzied;
	}

	public String getAlignFormat() {
		return alignFormat;
	}
	public void setAlignFormat(String alignFormat) {
		this.alignFormat = alignFormat;
	}


	public EColumnType getColumnType() {
		return columnType;
	}


	public void setColumnType(EColumnType columnType) {
		this.columnType = columnType;
	}


	public boolean isInTheCollection() {
		return inTheCollection;
	}


	public void setInTheCollection(boolean inTheCollection) {
		this.inTheCollection = inTheCollection;
	}


	public String getReturnType() {
		return returnType;
	}


	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}



	
}
