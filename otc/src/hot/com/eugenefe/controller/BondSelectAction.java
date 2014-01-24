package com.eugenefe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;

import com.eugenefe.entity.BondExt;
import com.eugenefe.entity.MarketVariableExt;
import com.eugenefe.entity.Portfolio;
import com.eugenefe.util.NamedQuery;

@Name("bondSelectAction")
public class BondSelectAction {

	@In
	private EntityManager entityManager;

	@Out(scope=ScopeType.CONVERSATION)
	private List<BondExt> bonds;
	
	public BondSelectAction(){
	}
	
	@Factory(value="bonds" )
	public void initBonds(){
		init();
	}
	
	private void init(){
		bonds = entityManager.createQuery(NamedQuery.Bonds.getQuery()).getResultList();
		
	}
	
}
