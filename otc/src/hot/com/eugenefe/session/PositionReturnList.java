package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("positionReturnList")
public class PositionReturnList extends EntityQuery<PositionReturn> {

	private static final String EJBQL = "select positionReturn from PositionReturn positionReturn";

	private static final String[] RESTRICTIONS = {
			"lower(positionReturn.id.bssd) like lower(concat(#{positionReturnList.positionReturn.id.bssd},'%'))",
			"lower(positionReturn.id.posId) like lower(concat(#{positionReturnList.positionReturn.id.posId},'%'))", };

	private PositionReturn positionReturn;

	public PositionReturnList() {
		positionReturn = new PositionReturn();
		positionReturn.setId(new PositionReturnId());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public PositionReturn getPositionReturn() {
		return positionReturn;
	}
}
