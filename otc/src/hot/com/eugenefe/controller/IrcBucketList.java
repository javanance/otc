package com.eugenefe.controller;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Events;

import com.eugenefe.entity.IMarketVariableHis;
import com.eugenefe.entity.IrcBucket;
import com.eugenefe.entity.MarketVariable;

@Name("ircBucketList")
@Scope(ScopeType.CONVERSATION)
public class IrcBucketList {
	@In
	private EntityManager entityManager;
	
	@Out
	private List<IrcBucket> ircBuckets;
	
	private String query ;
	
	public IrcBucketList(){
		
	}
	
//	@Factory(value="marketVariableHisList")
	@Observer(value="selectProduct")
	public void onSelectProduct(MarketVariable selectedProduct){
		query = "select a from IrcBucket a where a.intRate.irId =#{selectedProduct.mvId}";
		ircBuckets = entityManager.createQuery(query).getResultList();
	}
}
