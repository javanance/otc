package com.eugenefe.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import com.eugenefe.entity.IEntity;
import com.eugenefe.entity.IMarketVariableHis;
import com.eugenefe.entity.VcvMatrixHis;
import com.eugenefe.util.MapUtil;
import com.eugenefe.util.PivotTableModel;
import com.eugenefe.util.PivotTableModelNew;

public class LazyModelDynamic extends LazyDataModel<Map<String, String>> {
	private static final long serialVersionUID = 1L;

	private List<Map<String, String>> datasource;

	public List<Map<String, String>> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<Map<String, String>> datasource) {
		this.datasource = datasource;
	}

	public LazyModelDynamic(List<Map<String, String>> datasource) {
		this.datasource = datasource;
//		setRowCount(datasource.size());
	}

	@Override
	public Map<String, String> getRowData(String rowKey) {
//		System.out.println("In The GetRowData with key:"+ rowKey);
		for (Map<String, String> aa : datasource) {
			if (aa.get("getStringId").equals(rowKey))
				return aa;
		}
		return null;
	}
	

	@Override
	public Map<String, String> getRowData() {
//		System.out.println("In The GetRowData:"+ super.getRowData());
		// TODO Auto-generated method stub
		return super.getRowData();
	}
	
	
	@Override
	public Object getRowKey(Map<String, String> rowKey) {
//		System.out.println("In The GetRowKey :"+ rowKey);
		return rowKey.get("getStringId");
	}
	


	@Override
	public List<Map<String, String>> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, String> filters) {
		// throw new
		// UnsupportedOperationException("Lazy loading is not implemented.");

		System.out.println("filter1 key :");
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Object navi;
		String rst = new String();

		for (Map<String, String> aa : datasource) {
//			for (Map.Entry<String, String> bb : aa.entrySet()) {
//				System.out.println(bb.getKey() + "_" + bb.getValue());
//			}
			boolean match = true;
			for (Map.Entry<String, String> it : filters.entrySet()) {
//				System.out.println(it.getKey() + "_" + it.getValue() + "______");
				if (!aa.get(it.getKey()).toLowerCase().contains(it.getValue().toLowerCase())) {
					match = false;
					break;
				}
			}
			if (match) {
				data.add(aa);
			}
		}

		if (sortField != null) {
			Collections.sort(data, new LazySorterDynamicModel(sortField, sortOrder));
			// System.out.println("in the sort1 :"+ data.size()+sortField +":" +
			// sortOrder.toString());
			// Collections.sort(data);
			// System.out.println("in the sort2");
		}

		int dataSize = data.size();
		this.setRowCount(dataSize);

		// paginate
		if (dataSize > pageSize) {
			// System.out.println("in the pagination" + dataSize);
			try {
				return data.subList(first, first + pageSize);
			} catch (IndexOutOfBoundsException e) {
				return data.subList(first, first + (dataSize % pageSize));
			}
		} else {
			return data;
		}
	}


	@Override
	public List<Map<String, String>> load(int first, int pageSize, List<SortMeta> multiSortMeta,
			Map<String, String> filters) {
		// throw new
		// UnsupportedOperationException("Lazy loading is not implemented.");

		System.out.println("filter1 key 1:" + String.valueOf(getRowIndex()) +"_"+ pageSize+":"+first);
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Object navi;
		String rst = new String();

		for (Map<String, String> aa : datasource) {
//			for (Map.Entry<String, String> bb : aa.entrySet()) {
//				System.out.println(bb.getKey() + "_" + bb.getValue());
//			}
			
			boolean match = true;
			for (Map.Entry<String, String> it : filters.entrySet()) {
//				System.out.println(it.getKey() + "_" + it.getValue() + "______");
				// if (!aa.get(it.getKey()).contains(it.getValue())) {
				if (!aa.get(it.getKey()).toLowerCase().contains(it.getValue().toLowerCase())) {
					match = false;
					break;
				}
			}
			if (match) {
				data.add(aa);
			}
		}

		if (multiSortMeta != null && !multiSortMeta.isEmpty()) {
			for (int i = multiSortMeta.size() - 1; i >= 0; i--) {
				Collections.sort(data, new LazySorterDynamicModel(multiSortMeta.get(i).getSortField(), multiSortMeta
						.get(i).getSortOrder()));
				// LazySorterVolatilityHis(multiSortMeta.get(i).getSortField(),
				// multiSortMeta.get(i).getSortOrder()));
			}
			// for(SortMeta aa: multiSortMeta){
			// Collections.sort(data, new
			// LazySorterVolatilityHis(aa.getSortField(), aa.getSortOrder()));
			// }
		}

		int dataSize = data.size();
		this.setRowCount(dataSize);

		// paginate
		if (dataSize > pageSize) {
			// System.out.println("in the pagination" + dataSize);
			try {
				return data.subList(first, first + pageSize);
			} catch (IndexOutOfBoundsException e) {
				return data.subList(first, first + (dataSize % pageSize));
			}
		} else {
			return data;
		}
	}

	@Override
	public void setRowIndex(int rowIndex) {
		/*
		 * The following is in ancestor (LazyDataModel): this.rowIndex =
		 * rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
		 */
		if (rowIndex == -1 || getPageSize() == 0) {
			// System.out.println("Row Index : " + rowIndex +":"+
			// getPageSize());
			super.setRowIndex(-1);
		} else {
			// System.out.println("Row Index1 : " + rowIndex +":"+
			// getPageSize());
			super.setRowIndex(rowIndex % getPageSize());
		}
	}
//	@Override
//	public void setRowCount(int rowCount) {
//		super.setRowCount(rowCount);
//	}
	

//	private void recursive(String fieldName, Class fieldKlazz, List<String> rst) {
//		if (fieldKlazz.getPackage() != null) {
//			if (fieldKlazz.getPackage().getName().contains("com.eugenefe.entity")
//					|| fieldKlazz.getPackage().getName().contains("com.eugenefe.controller")) {
//				for (Field ff : fieldKlazz.getDeclaredFields()) {
//					recursive(fieldName + "." + ff.getName(), ff.getType(), rst);
//				}
//			} else {
//				rst.add(fieldName);
//			}
//		} else {
//			rst.add(fieldName);
//		}
//	}
//
//	private void recu(Object aa, String prop, String rst) {
//		try {
//			if (aa.getClass().getDeclaredField(prop).get(aa) != null) {
//				recu(aa.getClass().getField(prop).get(aa), prop, String.valueOf(aa.getClass().getField(prop).get(aa)));
//				System.out.println("In the Recu" + rst);
//			}
//		} catch (Exception e) {
//
//		}
//	}

}
