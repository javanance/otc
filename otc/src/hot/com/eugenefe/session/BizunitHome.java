package com.eugenefe.session;

import com.eugenefe.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("bizunitHome")
public class BizunitHome extends EntityHome<Bizunit> {

	@In(create = true)
	BizunitHome bizunitHome;

	public void setBizunitOrgId(String id) {
		setId(id);
	}

	public String getBizunitOrgId() {
		return (String) getId();
	}

	@Override
	protected Bizunit createInstance() {
		Bizunit bizunit = new Bizunit();
		return bizunit;
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

	public Bizunit getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Bizunit> getChildBizunits() {
		return getInstance() == null ? null : new ArrayList<Bizunit>(getInstance().getChildBizunits());
	}

	public List<Employee> getEmployees() {
		return getInstance() == null ? null : new ArrayList<Employee>(getInstance().getEmployees());
	}

}
