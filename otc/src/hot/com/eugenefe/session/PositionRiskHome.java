package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("positionRiskHome")
public class PositionRiskHome extends EntityHome<PositionRisk> {

	@In(create = true)
	BasedateHome basedateHome;
	@In(create = true)
	PositionHome positionHome;
	@In(create = true)
	VarGenMcdHome varGenMcdHome;

	public void setPositionRiskId(PositionRiskId id) {
		setId(id);
	}

	public PositionRiskId getPositionRiskId() {
		return (PositionRiskId) getId();
	}

	public PositionRiskHome() {
		setPositionRiskId(new PositionRiskId());
	}

	@Override
	public boolean isIdDefined() {
		if (getPositionRiskId().getBssd() == null || "".equals(getPositionRiskId().getBssd()))
			return false;
		if (getPositionRiskId().getPosId() == null || "".equals(getPositionRiskId().getPosId()))
			return false;
		if (getPositionRiskId().getVarGenId() == null || "".equals(getPositionRiskId().getVarGenId()))
			return false;
		return true;
	}

	@Override
	protected PositionRisk createInstance() {
		PositionRisk positionRisk = new PositionRisk();
		positionRisk.setId(new PositionRiskId());
		return positionRisk;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Basedate basedate = basedateHome.getDefinedInstance();
		if (basedate != null) {
			getInstance().setBasedate(basedate);
		}
		Position position = positionHome.getDefinedInstance();
		if (position != null) {
			getInstance().setPosition(position);
		}
		VarGenMcd varGenMcd = varGenMcdHome.getDefinedInstance();
		if (varGenMcd != null) {
			getInstance().setVarGenMcd(varGenMcd);
		}
	}

	public boolean isWired() {
		if (getInstance().getBasedate() == null)
			return false;
		if (getInstance().getPosition() == null)
			return false;
		if (getInstance().getVarGenMcd() == null)
			return false;
		return true;
	}

	public PositionRisk getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
