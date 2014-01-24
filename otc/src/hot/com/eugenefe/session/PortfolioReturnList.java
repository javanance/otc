package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("portfolioReturnList")
public class PortfolioReturnList extends EntityQuery<PortfolioReturn> {

	private static final String EJBQL = "select portfolioReturn from PortfolioReturn portfolioReturn";

	private static final String[] RESTRICTIONS = {
			"lower(portfolioReturn.id.bssd) like lower(concat(#{portfolioReturnList.portfolioReturn.id.bssd},'%'))",
			"lower(portfolioReturn.id.portId) like lower(concat(#{portfolioReturnList.portfolioReturn.id.portId},'%'))", };

	private PortfolioReturn portfolioReturn;

	public PortfolioReturnList() {
		portfolioReturn = new PortfolioReturn();
		portfolioReturn.setId(new PortfolioReturnId());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public PortfolioReturn getPortfolioReturn() {
		return portfolioReturn;
	}
}
