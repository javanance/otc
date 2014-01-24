package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("portfolioReturnHome")
public class PortfolioReturnHome extends EntityHome<PortfolioReturn> {

	@In(create = true)
	BasedateHome basedateHome;
	@In(create = true)
	PortfolioHome portfolioHome;

	public void setPortfolioReturnId(PortfolioReturnId id) {
		setId(id);
	}

	public PortfolioReturnId getPortfolioReturnId() {
		return (PortfolioReturnId) getId();
	}

	public PortfolioReturnHome() {
		setPortfolioReturnId(new PortfolioReturnId());
	}

	@Override
	public boolean isIdDefined() {
		if (getPortfolioReturnId().getBssd() == null || "".equals(getPortfolioReturnId().getBssd()))
			return false;
		if (getPortfolioReturnId().getPortId() == null || "".equals(getPortfolioReturnId().getPortId()))
			return false;
		return true;
	}

	@Override
	protected PortfolioReturn createInstance() {
		PortfolioReturn portfolioReturn = new PortfolioReturn();
		portfolioReturn.setId(new PortfolioReturnId());
		return portfolioReturn;
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
		Portfolio portfolio = portfolioHome.getDefinedInstance();
		if (portfolio != null) {
			getInstance().setPortfolio(portfolio);
		}
	}

	public boolean isWired() {
		if (getInstance().getBasedate() == null)
			return false;
		if (getInstance().getPortfolio() == null)
			return false;
		return true;
	}

	public PortfolioReturn getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
