package com.eugenefe.session;

import com.eugenefe.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("portfolioHome")
public class PortfolioHome extends EntityHome<Portfolio> {

	@In(create = true)
	PortfolioHome portfolioHome;

	public void setPortfolioPortId(String id) {
		setId(id);
	}

	public String getPortfolioPortId() {
		return (String) getId();
	}

	@Override
	protected Portfolio createInstance() {
		Portfolio portfolio = new Portfolio();
		return portfolio;
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

	public Portfolio getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<IPortfolio> getChildPortfolios() {
		return getInstance() == null ? null : new ArrayList<IPortfolio>(getInstance().getChildPortfolios());
	}

}
