package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("portfolioDetailList")
public class PortfolioDetailList extends EntityQuery<PortfolioDetail> {

	private static final String EJBQL = "select portfolioDetail from PortfolioDetail portfolioDetail";

	private static final String[] RESTRICTIONS = {
			"lower(portfolioDetail.id.bssd) like lower(concat(#{portfolioDetailList.portfolioDetail.id.bssd},'%'))",
			"lower(portfolioDetail.id.portId) like lower(concat(#{portfolioDetailList.portfolioDetail.id.portId},'%'))",
			"lower(portfolioDetail.id.posId) like lower(concat(#{portfolioDetailList.portfolioDetail.id.posId},'%'))", };

	private PortfolioDetail portfolioDetail;

	public PortfolioDetailList() {
		portfolioDetail = new PortfolioDetail();
		portfolioDetail.setId(new PortfolioDetailId());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public PortfolioDetail getPortfolioDetail() {
		return portfolioDetail;
	}
}
