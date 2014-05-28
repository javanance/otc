package com.eugenefe.entity.crud;

// Generated Apr 10, 2013 4:09:22 PM by Hibernate Tools 3.4.0.CR1


public class MeasureDao {
	public String measure;
	public String measureGroup;

	public MeasureDao(String measure, String measureGroup) {
		super();
		this.measure = measure;
		this.measureGroup = measureGroup;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public String getMeasureGroup() {
		return measureGroup;
	}

	public void setMeasureGroup(String measureGroup) {
		this.measureGroup = measureGroup;
	}

}
