package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("volatilityList")
public class VolatiliyList extends EntityQuery<VcvMatrix> {

	private static final String EJBQL = "select vol from Volatility vol";

//	private static final String[] RESTRICTIONS = {
//			"lower(vol.vcvId) like lower(concat(#{volatilityList.vcvId},'%'))",
//			 };

	private Volatility volatility = new Volatility();

	public VolatiliyList() {
		setEjbql(EJBQL);
//		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
//		setMaxResults(25);
	}

	public Volatility getVolatility() {
		return volatility;
	}
}
