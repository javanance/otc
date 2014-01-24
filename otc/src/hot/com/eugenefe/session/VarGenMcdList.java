package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("varGenMcdList")
public class VarGenMcdList extends EntityQuery<VarGenMcd> {

	private static final String EJBQL = "select varGenMcd from VarGenMcd varGenMcd";

	private static final String[] RESTRICTIONS = {
			"lower(varGenMcd.varGenId) like lower(concat(#{varGenMcdList.varGenMcd.varGenId},'%'))",
			"lower(varGenMcd.varType) like lower(concat(#{varGenMcdList.varGenMcd.varType},'%'))", };

	private VarGenMcd varGenMcd = new VarGenMcd();

	public VarGenMcdList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public VarGenMcd getVarGenMcd() {
		return varGenMcd;
	}
}
