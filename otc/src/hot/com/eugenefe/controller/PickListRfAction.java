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
import com.eugenefe.entity.VcvMatrix;
import com.eugenefe.util.NamedQuery;

@Name("pickListRfAction")
@Scope(ScopeType.CONVERSATION)
public class PickListRfAction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;

	@In(value = "#{tableRfVcvMatrixInit.riskFactorList}")
	private List<MarketVariable> allRiskFactors;

	@In(value = "#{tableRfVcvMatrixInit.pivotTableHeader}")
	private List<MarketVariable> pickRiskFactors;

	private DualListModel<String> pickModelRf;

	private List<String> rfSource = new ArrayList<String>();
	private List<String> rfTarget = new ArrayList<String>();

	
	public PickListRfAction() {
		System.out.println("Construction PickListRiskFactorAction");
	}

	@Create
	public void loadPickList() {
		rfSource = new ArrayList<String>();
		for (MarketVariable bb : pickRiskFactors) {
			if (!rfTarget.contains(bb.getMvId())) {
				rfTarget.add(bb.getMvId());
			}
		}
		for (MarketVariable bb : allRiskFactors) {
			if (!rfTarget.contains(bb.getMvId())) {
				rfSource.add(bb.getMvId());
			}
		}
		pickModelRf = new DualListModel<String>(rfSource, rfTarget);
	}
//******************************************************
	// public void afterPick(List<String> target){
	public void afterPick() {
		List<MarketVariable> selRf = new ArrayList<MarketVariable>();
		rfSource = pickModelRf.getSource();
		rfTarget = pickModelRf.getTarget();
		for (String aa : rfTarget) {
			for (MarketVariable bb : allRiskFactors) {
				if (bb.getMvName().equals(aa)) {
					selRf.add(bb);
					break;
				}
			}
		}
		Events.instance().raiseEvent("evtPickRiskFactors", selRf);
		log.info("End of Risk Factor Pick");
	}

	public void onTransfer(TransferEvent event) {
		StringBuilder builder = new StringBuilder();
		for (Object item : event.getItems()) {
			builder.append(item).append("<br />");
		}

		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Items Transferred");
		msg.setDetail(builder.toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	// --------------------------------Getter and Setter ---------------
	
	public List<MarketVariable> getPickRiskFactors() {
		return pickRiskFactors;
	}

	public void setPickRiskFactors(List<MarketVariable> pickRiskFactors) {
		this.pickRiskFactors = pickRiskFactors;
	}

	public List<String> getRfSource() {
		return rfSource;
	}

	public void setRfSource(List<String> rfSource) {
		this.rfSource = rfSource;
	}

	public List<String> getRfTarget() {
		return rfTarget;
	}

	public void setRfTarget(List<String> rfTarget) {
		this.rfTarget = rfTarget;
	}

	public DualListModel<String> getPickModelRf() {
		return pickModelRf;
	}

	public void setPickModelRf(DualListModel<String> pickModelRf) {
		this.pickModelRf = pickModelRf;
	}	
}
