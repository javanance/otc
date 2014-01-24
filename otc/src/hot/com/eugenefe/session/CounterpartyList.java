package com.eugenefe.session;

import com.eugenefe.entity.*;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;


@Name("counterpartyList")
@Scope(ScopeType.CONVERSATION)
//@Startup
public class CounterpartyList extends EntityQuery<Counterparty> {

	private static final String EJBQL = "select a from Counterparty a";

	private static final String[] RESTRICTIONS = {
//			"lower(employee.memberId) like lower(concat(#{employeeList.employee.memberId},'%'))",
//			"lower(employee.memberName) like lower(concat(#{employeeList.employee.memberName},'%'))",
//			"lower(employee.memberType) like lower(concat(#{employeeList.employee.memberType},'%'))", 
		};

	private Counterparty counterparty = new Counterparty();

	public CounterpartyList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
//		setMaxResults(25);
	}

	
	public Counterparty getCounterparty() {
		return counterparty;
	}

	public void setCounterparty(Counterparty counterparty) {
		this.counterparty = counterparty;
	}

}
