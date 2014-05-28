package com.eugenefe.controller.temp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Session;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.eugenefe.entity.Bizunit;
import com.eugenefe.entity.Hifive;
import com.eugenefe.entity.HifiveCondition;
import com.eugenefe.entity.Portfolio;
import com.eugenefe.entity.Position;
import com.eugenefe.entity.PositionHis;
import com.eugenefe.entity.crud.MeasureDao;
import com.eugenefe.enums.raw.EElsOptionType;
import com.eugenefe.enums.raw.EElsTrDetailType;
import com.eugenefe.util.NamedQuery;

@Name("elsPositionInit")
@Scope(ScopeType.CONVERSATION)
//@AutoCreate
public class ElsPositionInit {
	@Logger	private Log log;
	@In	private Session	 session;

	private List<PositionHis> posList = new ArrayList<PositionHis>();
	private List<PositionHis> assetPosList = new ArrayList<PositionHis>();
	private List<PositionHis> debtPosList = new ArrayList<PositionHis>();
	private List<PositionHis> allPosList = new ArrayList<PositionHis>();

	private PositionHis selectPosition;
	private PositionHis selectDebtPosition;

	private List<PositionHis> selectAssetPositionList = new ArrayList<PositionHis>();
	
	private String selectFund;
	private String selectOrg;
	private EElsOptionType selectOptType;
	private EElsTrDetailType selectTrType;
	
	private TreeNode root;
	
	private Hifive selectHifive;

	private List<PositionHis> filterAssetPosList ;
	
	public ElsPositionInit(){
		System.out.println("Construction FullPortfolioInit");
	}
	
	
//	************************Getter and Setter********************

	
	public List<PositionHis> getPosList() {
		return posList;
	}

	public Hifive getSelectHifive() {
		return selectHifive;
	}


	public void setSelectHifive(Hifive selectHifive) {
		this.selectHifive = selectHifive;
	}


	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}


	public List<PositionHis> getSelectAssetPositionList() {
		return selectAssetPositionList;
	}
	public void setSelectAssetPositionList(List<PositionHis> selectAssetPositionList) {
		this.selectAssetPositionList = selectAssetPositionList;
	}


	public List<PositionHis> getAllPosList() {
		return allPosList;
	}

	public void setAllPosList(List<PositionHis> allPosList) {
		this.allPosList = allPosList;
	}


	public void setPosList(List<PositionHis> posList) {
		this.posList = posList;
	}
	public List<PositionHis> getAssetPosList() {
		return assetPosList;
	}
	public void setAssetPosList(List<PositionHis> assetPosList) {
		this.assetPosList = assetPosList;
	}
	public List<PositionHis> getDebtPosList() {
		return debtPosList;
	}
	public PositionHis getSelectPosition() {
		return selectPosition;
	}
	public PositionHis getSelectDebtPosition() {
		return selectDebtPosition;
	}


	public void setSelectDebtPosition(PositionHis selectDebtPosition) {
		this.selectDebtPosition = selectDebtPosition;
	}

	public void setSelectPosition(PositionHis selectPosition) {
		this.selectPosition = selectPosition;
	}

	public void setDebtPosList(List<PositionHis> debtPosList) {
		this.debtPosList = debtPosList;
	}

	public List<PositionHis> getFilterAssetPosList() {
		return filterAssetPosList;
	}

	public void setFilterAssetPosList(List<PositionHis> filterAssetPosList) {
		this.filterAssetPosList = filterAssetPosList;
	}
	public String getSelectFund() {
		return selectFund;
	}
	public void setSelectFund(String selectFund) {
		this.selectFund = selectFund;
	}
	public String getSelectOrg() {
		return selectOrg;
	}


	public void setSelectOrg(String selectOrg) {
		this.selectOrg = selectOrg;
	}
	public EElsOptionType getSelectOptType() {
		return selectOptType;
	}
	public void setSelectOptType(EElsOptionType selectOptType) {
		this.selectOptType = selectOptType;
	}


	public EElsTrDetailType getSelectTrType() {
		return selectTrType;
	}


	public void setSelectTrType(EElsTrDetailType selectTrType) {
		this.selectTrType = selectTrType;
	}


	//*****************************************************************
	@Create
	public void create(){
		posList= session.createCriteria(PositionHis.class).list();
		root = new DefaultTreeNode("ROOT", null);
		for(PositionHis aa : posList){
			allPosList.add(aa);
//			log.info("Position: #0,#1", aa.getPosition().getPosId(), aa.getPosition().getPosSide());
			if(aa.getPosition().getPosSide().equals("LONG") && !assetPosList.contains(aa)){
				assetPosList.add(aa);
			}
			else if(aa.getPosition().getPosSide().equals("SHORT") && !debtPosList.contains(aa)){
				debtPosList.add(aa);
			}
		}
		
		/*for(PositionHis aa : debtPosList){
			TreeNode temp1 = new DefaultTreeNode(aa, root);
			for(PositionHis bb : assetPosList){
				if(bb.getPosition().getProduct().getMvId()
						.contains(aa.getPosition().getProduct().getMvId())
							&& bb.getPosition().getPosSide().equals("LONG")){
					TreeNode temp2 = new DefaultTreeNode(bb, temp1);
				}
			}
		}*/
	}

	public void select(SelectEvent event){
		selectAssetPositionList.clear();
//		debtPosList.clear();
//		PositionHis temp =(PositionHis)event.getObject();

		for(PositionHis aa : posList){
			if(aa.getPosition().getPosSide().equals("SHORT")
				&& selectPosition.getPosition().getProduct().getMvId()
					.contains(aa.getPosition().getProduct().getMvId())){
				selectDebtPosition= aa;
			}
			if(aa.getPosition().getPosSide().equals("LONG") 
					&& aa.getPosition().getProduct().getMvId()
							.contains(selectPosition.getPosition().getProduct().getMvId())){
				selectAssetPositionList.add(aa);
			}
		}
		selectHifive = (Hifive)session.get(Hifive.class, selectDebtPosition.getPosition().getProduct().getMvId());
		log.info("Size AssetPosition:#0, #1", selectAssetPositionList.size(), selectHifive);
	}
	
	public void filterButton(){
		assetPosList.clear();
		allPosList.clear();
		log.info("selection : #0, #1,#2,#3", selectOptType);
		for(PositionHis aa : posList){

			if(selectFund !=null &&  !selectFund.equals(aa.getPosition().getFund().getOrgId())){
				continue;
			}
//			if(selectOrg !=null ){
//				for(Bizunit bb : aa.getPosition().getFund().getChildBizunits()){
//					if(bb.equals(selectOrg)){
//						continue;
//					}
//				}	
//			}
			
			if(selectTrType !=null && !selectTrType.equals(aa.getPosition().getTxType())){
				continue;
			}
			if(selectOptType !=null && !selectOptType.equals(aa.getPosition().getElsOptType())){
				continue;
			}
			allPosList.add(aa);
			
			if(!aa.getPosition().getPosSide().equals("LONG")){
				continue;
			}
			assetPosList.add(aa);
		}
	}

}
