package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("basedateList")
public class BasedateList extends EntityQuery<Basedate> {

	private static final String EJBQL = "select basedate from Basedate basedate";

	private static final String[] RESTRICTIONS = {
			"lower(basedate.bssd) like lower(concat(#{basedateList.basedate.bssd},'%'))",
			"lower(basedate.eomBizday) like lower(concat(#{basedateList.basedate.eomBizday},'%'))",
			"lower(basedate.eoqBizday) like lower(concat(#{basedateList.basedate.eoqBizday},'%'))",
			"lower(basedate.eosBizday) like lower(concat(#{basedateList.basedate.eosBizday},'%'))",
			"lower(basedate.eoyBizday) like lower(concat(#{basedateList.basedate.eoyBizday},'%'))",
			"lower(basedate.fiscalBizday) like lower(concat(#{basedateList.basedate.fiscalBizday},'%'))",
			"lower(basedate.nextBizday) like lower(concat(#{basedateList.basedate.nextBizday},'%'))",
			"lower(basedate.prevBizday) like lower(concat(#{basedateList.basedate.prevBizday},'%'))", };

	private Basedate basedate = new Basedate();

	public BasedateList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Basedate getBasedate() {
		return basedate;
	}
}
