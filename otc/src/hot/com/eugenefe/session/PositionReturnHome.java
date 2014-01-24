package com.eugenefe.session;

import com.eugenefe.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("positionReturnHome")
public class PositionReturnHome extends EntityHome<PositionReturn> {

	@In(create = true)
	BasedateHome basedateHome;
	@In(create = true)
	PositionHome positionHome;

	public void setPositionReturnId(PositionReturnId id) {
		setId(id);
	}

	public PositionReturnId getPositionReturnId() {
		return (PositionReturnId) getId();
	}

	public PositionReturnHome() {
		setPositionReturnId(new PositionReturnId());
	}

	@Override
	public boolean isIdDefined() {
		if (getPositionReturnId().getBssd() == null || "".equals(getPositionReturnId().getBssd()))
			return false;
		if (getPositionReturnId().getPosId() == null || "".equals(getPositionReturnId().getPosId()))
			return false;
		return true;
	}

	@Override
	protected PositionReturn createInstance() {
		PositionReturn positionReturn = new PositionReturn();
		positionReturn.setId(new PositionReturnId());
		return positionReturn;
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
	}

	public boolean isWired() {
		if (getInstance().getBasedate() == null)
			return false;
		if (getInstance().getPosition() == null)
			return false;
		return true;
	}

	public PositionReturn getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<PositionRisk> getPosRisk() {
		return getInstance() == null ? null : new ArrayList<PositionRisk>(getInstance().getPosRisk());
	}

}
