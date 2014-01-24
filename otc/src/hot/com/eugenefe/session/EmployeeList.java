package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("employeeList")
public class EmployeeList extends EntityQuery<Employee> {

	private static final String EJBQL = "select employee from Employee employee";

	private static final String[] RESTRICTIONS = {
			"lower(employee.memberId) like lower(concat(#{employeeList.employee.memberId},'%'))",
			"lower(employee.memberName) like lower(concat(#{employeeList.employee.memberName},'%'))",
			"lower(employee.memberType) like lower(concat(#{employeeList.employee.memberType},'%'))", };

	private Employee employee = new Employee();

	public EmployeeList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Employee getEmployee() {
		return employee;
	}
}
