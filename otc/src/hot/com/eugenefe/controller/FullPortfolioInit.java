package com.eugenefe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Log;

import com.eugenefe.entity.Portfolio;
import com.eugenefe.util.NamedQuery;

@Name("fullPortfolioInit")
@Scope(ScopeType.CONVERSATION)
//@AutoCreate
public class FullPortfolioInit {
	@Logger
	private Log log;
	
	@In
	private EntityManager entityManager;

	private List<Portfolio> fullPorts;
	
	private List<Portfolio> fullHiers;
	
	public FullPortfolioInit(){
		System.out.println("Construction FullPortfolioInit");
	}
	
	
//	************************Getter and Setter********************
	public List<Portfolio> getFullPorts() {
		log.info("GetFullPorts In #0 Class ", this.getClass().getName());
		return fullPorts;
	}

	public void setFullPorts(List<Portfolio> fullPorts) {
		this.fullPorts = fullPorts;
	}

	public List<Portfolio> getFullHiers() {
		return fullHiers;
	}
	public void setFullHiers(List<Portfolio> fullHiers) {
		this.fullHiers = fullHiers;
	}

	//*****************************************************	

	@Begin(join=true)
	@Create
	@Observer("changeBssd_/view/v300ReturnRisk.xhtml")
	public void initFullPorts(){
		init();
	}
	
	public void initFullHiers(){
		init();
	}
	
	
	public void changeFullPorts(String bssd, String viewId){
		if(viewId == "/view/v300ReturnRisk.xhtml"){
			init();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void init(){
//		String qr = NamedQuery.PortfolioJoinReturnBssd.getQuery();
		fullPorts = new ArrayList<Portfolio>();
		fullHiers= new ArrayList<Portfolio>();

		fullPorts = entityManager.createQuery(NamedQuery.PortfolioJoinReturnBssd.getQuery()).getResultList();
		log.info("Full Port:#0,#1", NamedQuery.PortfolioJoinReturnBssd.getQuery());
		for(Portfolio aa : fullPorts){
			if(aa.getParentPortfolio()== null){
				fullHiers.add(aa);
			}
		}
		
	}

}
