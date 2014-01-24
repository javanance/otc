package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Name("portfolioList")
public class PortfolioList extends EntityQuery<Portfolio> {

	private static final String EJBQL = "select portfolio from Portfolio portfolio";

	private static final String[] RESTRICTIONS = {
			"lower(portfolio.portId) like lower(concat(#{portfolioList.portfolio.portId},'%'))",
			"lower(portfolio.groupId) like lower(concat(#{portfolioList.portfolio.groupId},'%'))",
			"lower(portfolio.level1) like lower(concat(#{portfolioList.portfolio.level1},'%'))",
			"lower(portfolio.level2) like lower(concat(#{portfolioList.portfolio.level2},'%'))",
			"lower(portfolio.level3) like lower(concat(#{portfolioList.portfolio.level3},'%'))",
			"lower(portfolio.level4) like lower(concat(#{portfolioList.portfolio.level4},'%'))",
			"lower(portfolio.level5) like lower(concat(#{portfolioList.portfolio.level5},'%'))",
			"lower(portfolio.portName) like lower(concat(#{portfolioList.portfolio.portName},'%'))", };

	private Portfolio portfolio = new Portfolio();

	public PortfolioList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

}
