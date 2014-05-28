package com.eugenefe.util;

public class DynamicColumnModel implements Comparable<DynamicColumnModel>{
	private String header;
	private String headerGroup;
    private String headerType;
    private boolean isInitialize;
    private int posIndex;

    public DynamicColumnModel(String header, String headerGroup, String headerType, int posIndex) {
		this.header = header;
		this.headerGroup = headerGroup;
		this.headerType = headerType;
		this.posIndex = posIndex;
	}

    public DynamicColumnModel(String header, String headerType, int posIndex) {
		this.header = header;
		this.headerType = headerType;
		this.posIndex = posIndex;
	}
    
	public DynamicColumnModel(String header, String headerGroup, String headerType, boolean isInitialize, int posIndex) {
		this.header = header;
		this.headerGroup = headerGroup;
		this.headerType = headerType;
		this.isInitialize = isInitialize;
		this.posIndex = posIndex;
	}

	public boolean isInitialize() {
		return isInitialize;
	}

	public void setInitialize(boolean isInitialize) {
		this.isInitialize = isInitialize;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getHeaderGroup() {
		return headerGroup;
	}

	public void setHeaderGroup(String headerGroup) {
		this.headerGroup = headerGroup;
	}

	public String getHeaderType() {
		return headerType;
	}

	public void setHeaderType(String headerType) {
		this.headerType = headerType;
	}

	public int getPosIndex() {
		return posIndex;
	}

	public void setPosIndex(int posIndex) {
		this.posIndex = posIndex;
	}

	@Override
	public int compareTo(DynamicColumnModel o) {
		if( this.getPosIndex() >= o.getPosIndex()){
			return 1;
		}
		return -1;
	}  

    
}
