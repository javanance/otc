package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("employeeHome")
public class EmployeeHome extends EntityHome<Employee> {

	@In(create = true)
	BizunitHome bizunitHome;

	public void setEmployeeMemberId(String id) {
		setId(id);
	}

	public String getEmployeeMemberId() {
		return (String) getId();
	}

	@Override
	protected Employee createInstance() {
		Employee employee = new Employee();
		return employee;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Bizunit bizunit = bizunitHome.getDefinedInstance();
		if (bizunit != null) {
			getInstance().setBizunit(bizunit);
		}
	}

	public boolean isWired() {
		return true;
	}

	public Employee getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
