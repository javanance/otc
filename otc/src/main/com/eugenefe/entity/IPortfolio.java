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
	
//  Retrun	������ Interface �� �������� �� �Ϲ����� ������ �����̳�.. �������� ������ ó���� ���� �ְڴ�...
//	public Set<IPortfolioReturn> getReturnInfo();
	public double getDailyReturn();
}
