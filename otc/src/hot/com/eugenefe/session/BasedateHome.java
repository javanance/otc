package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("basedateHome")
public class BasedateHome extends EntityHome<Basedate> {

	public void setBasedateBssd(String id) {
		setId(id);
	}

	public String getBasedateBssd() {
		return (String) getId();
	}

	@Override
	protected Basedate createInstance() {
		Basedate basedate = new Basedate();
		return basedate;
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

	public Basedate getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
