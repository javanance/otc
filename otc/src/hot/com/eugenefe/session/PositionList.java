package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("positionList")
public class PositionList extends EntityQuery<Position> {

	private static final String EJBQL = "select position from Position position";

	private static final String[] RESTRICTIONS = {
			"lower(position.posId) like lower(concat(#{positionList.position.posId},'%'))",
			"lower(position.initTxDate) like lower(concat(#{positionList.position.initTxDate},'%'))",
			"lower(position.lastTxDate) like lower(concat(#{positionList.position.lastTxDate},'%'))",
			"lower(position.posName) like lower(concat(#{positionList.position.posName},'%'))", };

	private Position position = new Position();

	public PositionList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Position getPosition() {
		return position;
	}
}
