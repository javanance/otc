package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("varGenMcdHome")
public class VarGenMcdHome extends EntityHome<VarGenMcd> {

	public void setVarGenMcdVarGenId(String id) {
		setId(id);
	}

	public String getVarGenMcdVarGenId() {
		return (String) getId();
	}

	@Override
	protected VarGenMcd createInstance() {
		VarGenMcd varGenMcd = new VarGenMcd();
		return varGenMcd;
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

	public VarGenMcd getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
