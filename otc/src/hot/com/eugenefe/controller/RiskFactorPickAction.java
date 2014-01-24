package com.eugenefe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.util.NamedQuery;

@Name("riskFactorPickAction")
@Scope(ScopeType.CONVERSATION)
public class RiskFactorPickAction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;

	@In
	private EntityManager entityManager;
	
//	@In
	private List<MarketVariable> riskFactors;
	
//	@In(required=false)
//	@Out
	private DualListModel<String> pickRiskFactors;
	
//	@Out(required=false)
//	private List<MarketVariable> selRiskFactors;


	public RiskFactorPickAction() {
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

	public DualListModel<String> getPickRiskFactors() {
		log.info("pickRiskFactors Id1 :#0", Conversation.instance().getId());
		return pickRiskFactors;
	}

	public void setPickRiskFactors(DualListModel<String> pickRiskFactors) {
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
		List<String> rfSource = new ArrayList<String>();
		List<String> rfTarget = new ArrayList<String>();
		riskFactors = entityManager.createQuery(NamedQuery.RiskFactors.getQuery()).getResultList();
		
		for (MarketVariable bb : riskFactors) {
				rfSource.add(bb.getMvName());
		}
		pickRiskFactors = new DualListModel<String>(rfSource, rfTarget);
		log.info("loadPickList Id1 :#0,#1", Conversation.instance().getId(),Conversation.instance().isLongRunning());
	}
	
//	public void afterPick(List<String> target){
	public void afterPick(){	
		log.info("afterPick Id1 :#0,#1", Conversation.instance().getId(),Conversation.instance().isLongRunning());
//		selRiskFactors = pickRiskFactors.getTarget();
		List<MarketVariable> selRf = new ArrayList<MarketVariable>();
		for(String aa : pickRiskFactors.getTarget()){
			for(MarketVariable bb : riskFactors){
				if(bb.getMvName().equals(aa)){
					selRf.add(bb);
					break;
				}
			}
		}
		
		Events.instance().raiseEvent("selRiskFactors", selRf);
		log.info("End of Risk Factor Pick");
		log.info("afterPick Id2 :#0,#1", Conversation.instance().getId(),Conversation.instance().isLongRunning());
	}
	
	public void onTransfer(TransferEvent event) {  
        StringBuilder builder = new StringBuilder();  
        for(Object item : event.getItems()) {  
            builder.append(item).append("<br />");  
        }  
          
        FacesMessage msg = new FacesMessage();  
        msg.setSeverity(FacesMessage.SEVERITY_INFO);  
        msg.setSummary("Items Transferred");  
        msg.setDetail(builder.toString());  
          
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
}
