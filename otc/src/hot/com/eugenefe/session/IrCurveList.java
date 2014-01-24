package com.eugenefe.session;

import com.eugenefe.entity.*;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("irCurveList")
@Scope(ScopeType.CONVERSATION)
public class IrCurveList extends EntityQuery<IrCurve> {

	private static final String EJBQL = "select irCurve from IrCurve irCurve";

	private static final String[] RESTRICTIONS = {
			"lower(irCurve.ircId) like lower(concat(#{irCurveList.irCurve.ircId},'%'))",
			 };

	private IrCurve irCurve = new IrCurve();

	public IrCurveList() {
		setEjbql(EJBQL);
//		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
//		setMaxResults(25);
	}

	public IrCurve getIrCurve() {
		return irCurve;
	}
}
