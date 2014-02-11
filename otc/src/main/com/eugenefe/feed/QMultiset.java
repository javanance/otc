package com.eugenefe.feed;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class QMultiset {

    private String from_date;
    private String to_date;
    private List<String> columns = new ArrayList<String>();
    private List<String> column_names = new ArrayList<String>();
    private List<List<String>> data = new ArrayList<List<String>>();
    @JsonIgnore
    public List<String> errors;
    private String frequency;
    
    
    public QMultiset() {
    }


	public String getFrom_date() {
		return from_date;
	}


	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}


	public String getTo_date() {
		return to_date;
	}


	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}


	public List<String> getColumns() {
		return columns;
	}


	public void setColumns(List<String> columns) {
		this.columns = columns;
	}


	public List<String> getColumn_names() {
		return column_names;
	}


	public void setColumn_names(List<String> column_names) {
		this.column_names = column_names;
	}


	public List<List<String>> getData() {
		return data;
	}


	public void setData(List<List<String>> data) {
		this.data = data;
	}


	public List<String> getErrors() {
		return errors;
	}


	public void setErrors(List<String> errors) {
		this.errors = errors;
	}


	public String getFrequency() {
		return frequency;
	}


	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	
}
