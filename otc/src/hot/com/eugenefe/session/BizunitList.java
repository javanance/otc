package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("bizunitList")
public class BizunitList extends EntityQuery<Bizunit> {

	private static final String EJBQL = "select bizunit from Bizunit bizunit";

	private static final String[] RESTRICTIONS = {
			"lower(bizunit.orgId) like lower(concat(#{bizunitList.bizunit.orgId},'%'))",
			"lower(bizunit.orgName) like lower(concat(#{bizunitList.bizunit.orgName},'%'))", };

	private Bizunit bizunit = new Bizunit();

	public BizunitList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Bizunit getBizunit() {
		return bizunit;
	}
}
