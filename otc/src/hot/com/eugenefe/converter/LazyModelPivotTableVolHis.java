package com.eugenefe.converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
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

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.Position;
import com.eugenefe.entity.PositionReturn;
import com.eugenefe.entity.VcvMatrixHis;
import com.eugenefe.entity.VolatilityHis;
import com.eugenefe.util.PivotTableModel;

//@Name("lazyModelVcvHis")
//@Scope(ScopeType.CONVERSATION)
public class LazyModelPivotTableVolHis extends LazyDataModel<PivotTableModel<MarketVariable, MarketVariable, VolatilityHis>> {

	// @Logger
	// private Log log;

	private List<PivotTableModel<MarketVariable, MarketVariable, VolatilityHis>> datasource;

	public List<PivotTableModel<MarketVariable, MarketVariable, VolatilityHis>> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<PivotTableModel<MarketVariable, MarketVariable, VolatilityHis>> datasource) {
		this.datasource = datasource;
	}

	public LazyModelPivotTableVolHis(List<PivotTableModel<MarketVariable, MarketVariable, VolatilityHis>> datasource) {
		this.datasource = datasource;
	}

	@Override
	public PivotTableModel<MarketVariable, MarketVariable, VolatilityHis> getRowData(String rowKey) {
		for (PivotTableModel<MarketVariable, MarketVariable, VolatilityHis> aa : datasource) {
			if (aa.getData().getMvId().equals(rowKey))
				return aa;
		}
		return null;
	}

	@Override
	public Object getRowKey(PivotTableModel<MarketVariable, MarketVariable, VolatilityHis> volHis) {
		return volHis.getData().getMvId();
	}

/*	@Override
	public List<PivotTableModel<MarketVariable, MarketVariable, VolatilityHis>> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, String> filters) {
		List<PivotTableModel<MarketVariable, MarketVariable, VolatilityHis>> data 
				= new ArrayList<PivotTableModel<MarketVariable, MarketVariable, VolatilityHis>>();
		Class<PivotTableModel> klass1 = PivotTableModel.class;
		Object  navi;
		String rst = new String();

		for (PivotTableModel<MarketVariable, MarketVariable, VolatilityHis> aa : datasource) {
			boolean match = true;
			for (String it : filters.keySet()) {
				navi =aa;
				System.out.println("filter key :"+ it);
				try {
					String filterValue = filters.get(it).toUpperCase();
					 
					for (String filiterProperty : it.split("\\.")) {
						System.out.println("filter Prop:"+ filiterProperty);
						navi = navi.getClass().getDeclaredField(filiterProperty).get(navi); 
						rst = String.valueOf(navi);
						System.out.println("rst Value :"+ rst);
//						recu(aa, filiterProperty, rst);
					}
					String fieldValue = rst.toUpperCase();
					
					// String filterProperty = it;
					// String filterValue = filters.get(filterProperty);
					// String fieldValue =
					// String.valueOf(aa.getClass().getField(filterProperty).get(aa));

					// if(filterValue == null || fieldValue.startsWith(filterValue)) {
					if (filterValue == null || fieldValue.contains(filterValue)) {
						match = true;
					} else {
						match = false;
						break;
					}
				} catch (Exception e) {
					match = false;
				}
			}

			if (match) {
				data.add(aa);
			}
		}

		if (sortField != null) {
			Collections.sort(data, new LazySorterVolatilityHis(sortField, sortOrder));
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
	public List<VolatilityHis> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String,String> filters) {
//		throw new UnsupportedOperationException("Lazy loading is not implemented.");
		
		List<VolatilityHis> data = new ArrayList<VolatilityHis>();
		Class<VolatilityHis> klass = VolatilityHis.class;
		Object  navi;
		String rst = new String();

		for (VolatilityHis aa : datasource) {
			boolean match = true;
			for (String it : filters.keySet()) {
				navi =aa;
				System.out.println("filter key :"+ it);
				try {
					String filterValue = filters.get(it).toUpperCase();
					 
					for (String filiterProperty : it.split("\\.")) {
						System.out.println("filter Prop:"+ filiterProperty);
						navi = navi.getClass().getDeclaredField(filiterProperty).get(navi); 
						rst = String.valueOf(navi);
						System.out.println("rst Value :"+ rst);
//						recu(aa, filiterProperty, rst);
					}
					String fieldValue = rst.toUpperCase();
					
					// String filterProperty = it;
					// String filterValue = filters.get(filterProperty);
					// String fieldValue =
					// String.valueOf(aa.getClass().getField(filterProperty).get(aa));

					// if(filterValue == null || fieldValue.startsWith(filterValue)) {
					if (filterValue == null || fieldValue.contains(filterValue)) {
						match = true;
					} else {
						match = false;
						break;
					}
				} catch (Exception e) {
					match = false;
				}
			}

			if (match) {
				data.add(aa);
			}
		}
		
        if(multiSortMeta!=null && !multiSortMeta.isEmpty()){
        	for(int i = multiSortMeta.size()-1;  i>=0 ; i--){
        		Collections.sort(data, new LazySorterVolatilityHis(multiSortMeta.get(i).getSortField(), multiSortMeta.get(i).getSortOrder()));
        	}
//        	for(SortMeta aa: multiSortMeta){
//        		Collections.sort(data, new LazySorterVolatilityHis(aa.getSortField(), aa.getSortOrder()));
//        	}
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
    }*/
	
	/*
	 * @Override public void setRowIndex(int rowIndex) { if (getPageSize() == 0)
	 * { rowIndex = -11; } super.setRowIndex(rowIndex); }
	 */
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

	private void recursive(String fieldName, Class fieldKlazz, List<String> rst) {
		if (fieldKlazz.getPackage() != null) {
			if (fieldKlazz.getPackage().getName().contains("com.eugenefe.entity")
					|| fieldKlazz.getPackage().getName().contains("com.eugenefe.controller")) {
				for (Field ff : fieldKlazz.getDeclaredFields()) {
					recursive(fieldName + "." + ff.getName(), ff.getType(), rst);
				}
			} else {
				rst.add(fieldName);
			}
		} else {
			rst.add(fieldName);
		}
	}

	private void recu(Object aa, String prop, String rst) {
		try {
			if (aa.getClass().getDeclaredField(prop).get(aa) != null) {
				recu(aa.getClass().getField(prop).get(aa), prop, String.valueOf(aa.getClass().getField(prop).get(aa)));
				System.out.println("In the Recu" + rst);
			}
		} catch (Exception e) {

		}
	}

	// for (Field aa : VolatilityHis.class.getFields()) {
	// try {
	// log.info("Field2222:#0,#1", aa.get(volatilityHisList.get(0)));
	// } catch (IllegalArgumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// // log.info("Field2222:#0,#1", );
	// // recursive(aa.getName(), aa.getType(), tempRst);
	// // recursive(aa.getName(), aa, tempRst);
	// }
	// for (String st : tempRst) {
	// log.info("Field2222:#0,#1", st);
	// }
}
