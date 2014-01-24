package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("positionHome")
public class PositionHome extends EntityHome<Position> {

	public void setPositionPosId(String id) {
		setId(id);
	}

	public String getPositionPosId() {
		return (String) getId();
	}

	@Override
	protected Position createInstance() {
		Position position = new Position();
		return position;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Position getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
