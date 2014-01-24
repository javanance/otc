package com.eugenefe.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface IPortfolio extends Serializable{
	
	public String getStringId();
	public String getName();
	
	
	public void add(IPortfolio port);
	public void remove(IPortfolio port);
	public List<IPortfolio> getChildren();
	public IPortfolio getChildren(String portId);
	
	public double getPresValue();
	public double getCalcuatedPv();
	
//  Retrun	정보를 Interface 로 가져오는 게 일반적인 다형성 구현이나.. 개별적인 값으로 처리할 수도 있겠다...
//	public Set<IPortfolioReturn> getReturnInfo();
	public double getDailyReturn();
}
