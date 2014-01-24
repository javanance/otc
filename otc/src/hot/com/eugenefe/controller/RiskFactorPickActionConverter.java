package com.eugenefe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Log;
import org.primefaces.model.DualListModel;

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.util.NamedQuery;

@Name("riskFactorPickActionConv")
@Scope(ScopeType.CONVERSATION)
public class RiskFactorPickActionConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;

	@In
	private EntityManager entityManager;
	
//	@In
	private List<MarketVariable> riskFactors;
	
//	@In(required=false)
	@Out
	private DualListModel<MarketVariable> pickRiskFactors;
	
//	@Out(required=false)
//	private List<MarketVariable> selRiskFactors;


	public RiskFactorPickActionConverter() {
		System.out.println("Construction RiskFactorPickAction");
	}

	// --------------------------------Getter and Setter ---------------
	public List<MarketVariable> getRiskFactors() {
		return riskFactors;
	}
	public void setRiskFactors(List<MarketVariable> riskFactors) {
		log.info("Set riskfactor");
		this.riskFactors = riskFactors;
	}

	public DualListModel<MarketVariable> getPickRiskFactors() {
		log.info("pickRiskFactors Id1 :#0", Conversation.instance().getId());
		return pickRiskFactors;
	}

	public void setPickRiskFactors(DualListModel<MarketVariable> pickRiskFactors) {
		this.pickRiskFactors = pickRiskFactors;
	}
	
//	public List<MarketVariable> getSelRiskFactors() {
//		return selRiskFactors;
//	}
//	public void setSelRiskFactors(List<MarketVariable> selRiskFactors) {
//		this.selRiskFactors = selRiskFactors;
//	}

//	@Factory("pickRiskFactors")
//	@Create
//	@Begin(join = true)
	public void loadPickList() {
		
		log.info("loadPickList Id :#0,#1", Conversation.instance().getId(),Conversation.instance().isLongRunning());
		List<MarketVariable> rfSource = new ArrayList<MarketVariable>();
		List<MarketVariable> rfTarget = new ArrayList<MarketVariable>();
		riskFactors = entityManager.createQuery(NamedQuery.RiskFactors.getQuery()).getResultList();
//		riskFactors = new ArrayList<MarketVariable>();
//		riskFactors.add(new MarketVariable("AAA"));
//		riskFactors.add(new MarketVariable("bbb"));
		
		for (MarketVariable bb : riskFactors) {
				rfSource.add(bb);
		}
		pickRiskFactors = new DualListModel<MarketVariable>(rfSource, rfTarget);
		log.info("loadPickList Id1 :#0,#1", Conversation.instance().getId(),Conversation.instance().isLongRunning());
	}
	
	public void afterPick(ActionEvent actionEvent){
		log.info("afterPick Id1 :#0,#1", Conversation.instance().getId(),Conversation.instance().isLongRunning());
//		selRiskFactors = pickRiskFactors.getTarget();
		List<MarketVariable> selRf = new ArrayList<MarketVariable>();
		selRf =pickRiskFactors.getTarget();
		
		log.info("Start Risk Factor Pick:#0",actionEvent.getSource().toString());
		Events.instance().raiseEvent("selRiskFactors", selRf);
		log.info("End of Risk Factor Pick");
		log.info("afterPick Id2 :#0,#1", Conversation.instance().getId(),Conversation.instance().isLongRunning());
	}
}
