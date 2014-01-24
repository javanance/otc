package com.eugenefe.session;

import com.eugenefe.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("portfolioDetailHome")
public class PortfolioDetailHome extends EntityHome<PortfolioDetail> {

	@In(create = true)
	BasedateHome basedateHome;
	@In(create = true)
	PortfolioHome portfolioHome;
	@In(create = true)
	PositionHome positionHome;

	public void setPortfolioDetailId(PortfolioDetailId id) {
		setId(id);
	}

	public PortfolioDetailId getPortfolioDetailId() {
		return (PortfolioDetailId) getId();
	}

	public PortfolioDetailHome() {
		setPortfolioDetailId(new PortfolioDetailId());
	}

	@Override
	public boolean isIdDefined() {
		if (getPortfolioDetailId().getBssd() == null || "".equals(getPortfolioDetailId().getBssd()))
			return false;
		if (getPortfolioDetailId().getPortId() == null || "".equals(getPortfolioDetailId().getPortId()))
			return false;
		if (getPortfolioDetailId().getPosId() == null || "".equals(getPortfolioDetailId().getPosId()))
			return false;
		return true;
	}

	@Override
	protected PortfolioDetail createInstance() {
		PortfolioDetail portfolioDetail = new PortfolioDetail();
		portfolioDetail.setId(new PortfolioDetailId());
		return portfolioDetail;
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
		Position position = positionHome.getDefinedInstance();
		if (position != null) {
			getInstance().setPosition(position);
		}
	}

	public boolean isWired() {
		if (getInstance().getBasedate() == null)
			return false;
		if (getInstance().getPortfolio() == null)
			return false;
		if (getInstance().getPosition() == null)
			return false;
		return true;
	}

	public PortfolioDetail getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
