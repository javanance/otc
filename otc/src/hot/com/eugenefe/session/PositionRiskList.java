package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("positionRiskList")
public class PositionRiskList extends EntityQuery<PositionRisk> {

	private static final String EJBQL = "select positionRisk from PositionRisk positionRisk";

	private static final String[] RESTRICTIONS = {
			"lower(positionRisk.id.bssd) like lower(concat(#{positionRiskList.positionRisk.id.bssd},'%'))",
			"lower(positionRisk.id.posId) like lower(concat(#{positionRiskList.positionRisk.id.posId},'%'))",
			"lower(positionRisk.id.varGenId) like lower(concat(#{positionRiskList.positionRisk.id.varGenId},'%'))", };

	private PositionRisk positionRisk;

	public PositionRiskList() {
		positionRisk = new PositionRisk();
		positionRisk.setId(new PositionRiskId());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public PositionRisk getPositionRisk() {
		return positionRisk;
	}
}
