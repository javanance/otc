package com.eugenefe.session;

import com.eugenefe.entity.*;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("stockList")
@Scope(ScopeType.SESSION)
public class StockList extends EntityQuery<Stock> {

	private static final String EJBQL = "select stock from Stock stock";

	private static final String[] RESTRICTIONS = {
			"lower(stock.stockId) like lower(concat(#{stockList.stock.stockId},'%'))",
			 };

	private Stock stock = new Stock();

	public StockList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
//		setMaxResults(25);
	}

	public Stock getStock() {
		return stock;
	}
}
