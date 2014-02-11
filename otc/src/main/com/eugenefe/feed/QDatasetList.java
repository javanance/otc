package com.eugenefe.feed;

import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class QDatasetList {

    private String per_page;
    private List<QDataset> docs;
//    private String total_count;
    private int total_count;
    private List<QDataSource> sources;
    private int current_page;
	
    public QDatasetList() {
    }

	public String getPer_page() {
		return per_page;
	}

	public void setPer_page(String per_page) {
		this.per_page = per_page;
	}

	public List<QDataset> getDocs() {
		return docs;
	}

	public void setDocs(List<QDataset> docs) {
		this.docs = docs;
	}

//	public String getTotal_count() {
//		return total_count;
//	}
//
//	public void setTotal_count(String total_count) {
//		this.total_count = total_count;
//	}
	public int getTotal_count() {
		return total_count;
	}
	
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public List<QDataSource> getSources() {
		return sources;
	}


	public void setSources(List<QDataSource> sources) {
		this.sources = sources;
	}

	public int getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}
}
