package com.eugenefe.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;

@Name("navigationList")
//@Startup
@Scope(ScopeType.SESSION)
public class DataNavigationList {

	private List<ENavigationData> allData;
	
	public DataNavigationList(){
		
	}
	@Create
	public void create(){
		allData = new ArrayList<ENavigationData>();
		allData.addAll(Arrays.asList(ENavigationData.values()));
	}


	public void setAllData(List<ENavigationData> allData) {
		this.allData = allData;
	}
	public List<ENavigationData> getAllData() {
		return allData;
	}
	
}
