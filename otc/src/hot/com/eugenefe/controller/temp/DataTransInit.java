package com.eugenefe.controller.temp;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.in;
import static org.hibernate.criterion.Restrictions.sizeEq;
import static org.hibernate.criterion.Restrictions.sizeGt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transaction;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import com.eugenefe.entity.Hifive;
import com.eugenefe.entity.HifiveStrike;
import com.eugenefe.entity.HifiveStrikeId;
import com.eugenefe.entity.HifiveUnderlying;
import com.eugenefe.entity.HifiveUnderlyingId;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.PositionReturn;
import com.eugenefe.entity.SyntheticDetail;
import com.eugenefe.entity.SyntheticDetailId;
import com.eugenefe.entity.Synthetics;
import com.eugenefe.entity.crud.IntrinsicBond;
import com.eugenefe.entity.crud.OptionCrud;
import com.eugenefe.entity.crud.PositionCrud;
import com.eugenefe.entity.crud.PositionHisCrud;
import com.eugenefe.entity.crud.PositionReturnCrud;
import com.eugenefe.entity.raws.Ivot015p0;
import com.eugenefe.entity.raws.Pdif001m0;
import com.eugenefe.entity.raws.Pdif004m0;
import com.eugenefe.entity.raws.Pdif093p0;
import com.eugenefe.entity.raws.Pdif094p0;
import com.eugenefe.entity.raws.Pdif095p0;
import com.eugenefe.enums.EBarrierType;
import com.eugenefe.enums.EBondCfType;
import com.eugenefe.enums.EBoolean;
import com.eugenefe.enums.EDigitalType;
import com.eugenefe.enums.EExoticOptionType;
import com.eugenefe.enums.EOptionType;
import com.eugenefe.enums.raw.EElsEventEvaluationType;
import com.eugenefe.enums.raw.EElsObserveFrequencyType;
import com.eugenefe.enums.raw.EElsOptionType;
import com.eugenefe.enums.raw.EElsRchgType;
import com.eugenefe.enums.raw.EElsReturnEvaluationType;
import com.eugenefe.util.MarketVariableType;
import com.sun.media.sound.MidiUtils.TempoCache;

@Name("dataTransInit")
@Scope(ScopeType.CONVERSATION)
// @AutoCreate
public class DataTransInit {
	@Logger	private Log log;
	@In		private Session session;

	private List<OptionCrud> optionCrudList = new ArrayList<OptionCrud>();
	private List<IntrinsicBond> intrBondList = new ArrayList<IntrinsicBond>();
	private List<SyntheticDetail> synDetailList = new ArrayList<SyntheticDetail>();
	private List<Synthetics> synList = new ArrayList<Synthetics>();
	private List<MarketVariable> mvList = new ArrayList<MarketVariable>();
	
	List<PositionCrud> posList = new ArrayList<PositionCrud>();

	private OptionCrud intrOption;
	private OptionCrud intrOption2;
	private OptionCrud intrOption3;
	
	private IntrinsicBond intrBond;
	private IntrinsicBond irLeg;
	
	private Synthetics synMaster;
	private SyntheticDetail synDetail;
	
	private Synthetics assetSynMaster;
	private SyntheticDetail assetSynDetail;

	private List<String> idList = new ArrayList<String>();
	
	private List<Ivot015p0> elsAssetList = new ArrayList<Ivot015p0>();

//	private List<Pdif093p0> elsUnderList = new ArrayList<Pdif093p0>();
//	private List<Pdif094p0> elsEventList = new ArrayList<Pdif094p0>();
//	private List<Pdif095p0> elsUnderEventList = new ArrayList<Pdif095p0>();
	private List<Pdif004m0> elsMasterList = new ArrayList<Pdif004m0>();
	
//	private List<Hifive> hifiveList = new ArrayList<Hifive>();
//	private Set<Hifive> hifiveSet = new HashSet<Hifive>();
//	private List<HifiveStrike> hifiveStrike = new ArrayList<HifiveStrike>();
//	private List<HifiveUnderlying> hifiveUnder = new ArrayList<HifiveUnderlying>();
//	private List<Pdif001m0> baseList = new ArrayList<Pdif001m0>();
//	private Map<String, Pdif001m0> baseMap = new HashMap<String, Pdif001m0>();	

	private List<String> optTrDtldTypeList = new ArrayList<String>();
	private List<String> dElsTrDtldTypeList = new ArrayList<String>();
	private List<String> pElsTrDtldTypeList = new ArrayList<String>();
	private List<String> elsTrDtldTypeList = new ArrayList<String>();
	private List<String> swapTrDtldTypeList = new ArrayList<String>();
	private List<String> btbTrDtldTypeList = new ArrayList<String>();

	private List<String> optTypeList = new ArrayList<String>();
	private List<String> koTypeList = new ArrayList<String>();
	private List<String> vanillaTypeList = new ArrayList<String>();
	private List<String> spTypeList = new ArrayList<String>();
	
	
	public DataTransInit() {
		System.out.println("Construction FullPortfolioInit");
	}
	
	// ************************Getter and Setter********************	
	public List<Ivot015p0> getElsAssetList() {
		return elsAssetList;
	}
	public void setElsAssetList(List<Ivot015p0> elsAssetList) {
		this.elsAssetList = elsAssetList;
	}
	public List<OptionCrud> getOptionCrudList() {
		return optionCrudList;
	}
	public void setOptionCrudList(List<OptionCrud> optionCrudList) {
		this.optionCrudList = optionCrudList;
	}
	public List<IntrinsicBond> getIntrBondList() {
		return intrBondList;
	}
	public void setIntrBondList(List<IntrinsicBond> intrBondList) {
		this.intrBondList = intrBondList;
	}
	public List<SyntheticDetail> getSynDetailList() {
		return synDetailList;
	}
	public void setSynDetailList(List<SyntheticDetail> synDetailList) {
		this.synDetailList = synDetailList;
	}
	public List<Synthetics> getSynList() {
		return synList;
	}
	public void setSynList(List<Synthetics> synList) {
		this.synList = synList;
	}

	// *****************************************************
	@Create
	public void create() {
		setBase();

		// elsAssetList = session.createCriteria(Ivot015p0.class)
		// .createAlias("elsMaster","b").add(eq("b.elsOptDvsnCd", "01"))
		// .setResultTransformer(Criteria.ROOT_ENTITY)
		// .list();
		
		optionCrudList = session.createCriteria(OptionCrud.class).list();
		synList = session.createCriteria(Synthetics.class).list();
		
		for(OptionCrud aa :optionCrudList){
			idList.add(aa.getOptionId());
		}
		for(Synthetics aa :synList){
			idList.add(aa.getSynProdId());
		}
//		if (optionCrudList.isEmpty()) {
//			convertKnockOutCall();
//		}
//		convertCallSpread();

//		synList = session.createCriteria(Synthetics.class).list();
//		if (synList.isEmpty()) {
//			convertToSynthetic();
//		}
	}

//	public void convertHifive() {
//		Pdif001m0 tempBase;
//		Hifive hifive;
//		HifiveStrike tempStrike;
//		HifiveUnderlying tempUnderlying;
//		HifiveStrikeId tempStrikeId;
//		HifiveUnderlyingId tempUnderId;
//
//		for (Pdif004m0 aa : elsMasterList) {
//			tempBase = (Pdif001m0) session.get(Pdif001m0.class, aa.getPdno());
//
//			hifive = new Hifive();
//
//			hifive.setProdId(aa.getPdno());
//			hifive.setProdName(tempBase.getPrdtName());
//			// hifive.setProdName(baseMap.get(aa.getPdno()).getPrdtName());
//			hifive.setFaceAmt(new Long(10000));
//			// hifive.setDownBarrier(aa.);
//
//			// hifive.setCounterparty(counterparty);
//			// hifive.setCouponRateLast(couponRateLast);
//			hifive.setHifiveType("ELS");
//			// hifive.setHifiveStrikes(hifiveStrikes);
//			// hifive.setHifiveUnderlyings(hifiveUnderlyings);
//			// hifive.setHittingType(hittingType);
//			// hifive.setIssueDate(issueDate);
//			// hifive.setMaturityDate(maturityDate);
//			// hifive.setMaxLoss(maxLoss);
//			// hifive.setNotionalAmt(notionalAmt);
//			// hifive.setVirtual(false);
//			// hifive.set
//			hifiveList.add(hifive);
//			hifiveSet.add(hifive);
//		}
//	}


	private void setBase(){
		optTypeList.add("01");
		optTypeList.add("02");
		optTypeList.add("03");
		optTypeList.add("05");
		optTypeList.add("06");
		optTypeList.add("17");
		optTypeList.add("19");
		optTypeList.add("23");
		
		koTypeList.add("01");
		koTypeList.add("17");
//		koTypeList.add("19");
		
		spTypeList.add("02");
		spTypeList.add("23");
		
		vanillaTypeList.add("05");
		vanillaTypeList.add("06");
		
		optTrDtldTypeList.add("01");
		optTrDtldTypeList.add("02");

		dElsTrDtldTypeList.add("03");
		dElsTrDtldTypeList.add("04");

		pElsTrDtldTypeList.add("05");
		pElsTrDtldTypeList.add("06");

		elsTrDtldTypeList.add("03");
		elsTrDtldTypeList.add("04");
		elsTrDtldTypeList.add("05");
		elsTrDtldTypeList.add("06");

		swapTrDtldTypeList.add("07");
		swapTrDtldTypeList.add("09");

		btbTrDtldTypeList.add("02");
		btbTrDtldTypeList.add("04");
		btbTrDtldTypeList.add("06");
		btbTrDtldTypeList.add("07");
		btbTrDtldTypeList.add("09");		
	}

	private void loadElsAssetList(String optType, String issueType, boolean basket) {
		Criteria crit = session.createCriteria(Ivot015p0.class);

//		crit = crit.add(eq("rdptYn", "N"));
//		crit = crit	.createAlias("elsMaster", "b").add(eq("b.elsOptDvsnCd", optType));

		if(optType.equals("ALL")){
			crit = crit.createAlias("elsMaster", "b").add(in("b.elsOptDvsnCd", optTypeList));
		}
		else if (optType.equals("KO")){
			crit = crit.createAlias("elsMaster", "b").add(in("b.elsOptDvsnCd", koTypeList));
		}
		else if (optType.equals("SP")){
			crit = crit.createAlias("elsMaster", "b").add(in("b.elsOptDvsnCd", spTypeList));
		}
		else if (optType.equals("PV")){
			crit = crit.createAlias("elsMaster", "b").add(in("b.elsOptDvsnCd", vanillaTypeList));
		}
		else {
			crit = crit.createAlias("elsMaster", "b").add(eq("b.elsOptDvsnCd", optType));
		}
		
		if (basket) {
			crit = crit.add(sizeGt("b.elsUnderMap", 1));
		} else {
			crit = crit.add(sizeEq("b.elsUnderMap", 1));
		}

		if (issueType.equals("ALL")) {
		}
		else if(issueType.equals("OTC")) {
			crit = crit.add(in("id.elsTrDtldTypeCd", optTrDtldTypeList));
		} else if (issueType.equals("D_ELS")) {
			crit = crit.add(in("id.elsTrDtldTypeCd", dElsTrDtldTypeList));
		} else if (issueType.equals("P_ELS")) {
			crit = crit.add(in("id.elsTrDtldTypeCd", pElsTrDtldTypeList));
		} else if (issueType.equals("ELS")) {
			crit = crit.add(in("id.elsTrDtldTypeCd", elsTrDtldTypeList));
		} else if (issueType.equals("SWAP")) {
			crit = crit.add(in("id.elsTrDtldTypeCd", swapTrDtldTypeList));
		}

		elsAssetList = crit.list();
	}

	

	public void convertPlainVanilla() {
		int cnt = 0;
		Pdif004m0 elsMaster;

		
		PositionCrud assetPos;
		PositionCrud debtPos;
		
		Synthetics assetSyn;
		List<SyntheticDetail> assetSynDetailList = new ArrayList<SyntheticDetail>();
		
		posList.clear();

		// Plain Vanilla ,issueProd : ALL Type, non Basket
		loadElsAssetList("PV", "ALL", false);

		// Issue Data
		// Synthetic Data
		// Position Data

		for (Ivot015p0 elsAsset : elsAssetList) {
			elsMaster = elsAsset.getElsMaster();
			log.info("Asset : #0, #1", elsMaster.getPdno());
			if (idList.contains(elsMaster.getPdno())) {
				break;
			} else {
				idList.add(elsMaster.getPdno());
			}
			intrOption = new OptionCrud();
			intrOption.setOptionId(elsMaster.getPdno());
			intrOption.setOptionName(elsMaster.getProd().getPrdtName());
			intrOption.setIssueDate(elsMaster.getIssuDt());
			intrOption.setMaturityDate(elsMaster.getExpdDt());
			intrOption.setMultiplier(new Long(100));
			intrOption.setBasketYn(EBoolean.N);
			intrOption.setUserName(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).getDesc());
			
			if(elsMaster.getElsOptDvsnCd().equals("05")){
				intrOption.setOptionType(EOptionType.CALL);
			}
			else if(elsMaster.getElsOptDvsnCd().equals("06")){
				intrOption.setOptionType(EOptionType.PUT);
			}
			for (Map.Entry<String, BigDecimal> entry : elsMaster.getElsUnderMap().entrySet()) {
				log.info("Entry Set :#0, #1", entry.getKey(), entry.getValue());
				intrOption.setUnderlyingId(entry.getKey());

				for (Pdif094p0 bb : elsMaster.getElsEventList()) {
					if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getElsEvntCndtTypeCd().equals("04")
							&& bb.getId().getElsEvntSeq() == 0) {
						intrOption.setBasePrice(entry.getValue());
						intrOption.setStrikePrice(bb.getUnderEventMap().get(entry.getKey()));
						intrOption.setConversionRatio(bb.getRedemEvent().getPtciRt());

						intrOption.setEventEvalType(EElsEventEvaluationType.getEnum(bb.getElsEvntEvluBassDvsnCd()));
						intrOption.setReturnEvalType(EElsReturnEvaluationType.getEnum(bb.getElsEvluMthdTypeCd()));
						intrOption.setObservationType(EElsObserveFrequencyType.getEnum(bb.getElsObsvFrqcTypeCd()));
					} 
				}
			}			
			
			setIntrinsicBond(elsMaster, elsAsset.getId().getElsTrDtldTypeCd());
			
			if(intrBond !=null){
				SyntheticDetail temp = new SyntheticDetail( 
						new SyntheticDetailId( elsMaster.getPdno(), intrBond.getBondId()));
				setBondWeight(temp, elsMaster, elsAsset.getBondAdmsRt(), elsAsset.getUtlzFundPrcaAsrcRt());
				synDetailList.add(temp);
				
				intrOption.setOptionId(elsMaster.getPdno()+"_O");
				intrOption.setOptionName(elsMaster.getProd().getPrdtName()+"_내재옵션");
				
				SyntheticDetail temp2 = new SyntheticDetail(
						new SyntheticDetailId(elsMaster.getPdno(), intrOption.getOptionId()));
				temp2.setWeight(new BigDecimal(1));
				synDetailList.add(temp2);
				
				Synthetics debtSyn = new Synthetics(elsMaster.getPdno(), elsMaster.getProd().getPrdtName(), "ELS_PV");
				debtSyn.setSourceTable(elsAsset.getId().getElsTrDtldTypeCd());
				debtSyn.setSubProdList(synDetailList);
				
				synList.add(debtSyn);
				
				setMarketVariable(debtSyn.getSynProdId(), debtSyn.getSynProdName(), MarketVariableType.SYNTHETIC);
			}
			optionCrudList.add(intrOption);
			setMarketVariable(intrOption.getOptionId(), intrOption.getOptionName(),MarketVariableType.OPTION);
			
 
//TODO : Asset Product 			
			switch (elsAsset.getElsTrDtldTypeCd()) {
			case EX_BOND_OTC:
				break;
			case SWAP1:
			case SWAP2:
				setIrLeg(elsAsset, elsMaster);
				assetSyn = new Synthetics(elsMaster.getPdno()+"_S", elsMaster.getProd().getPrdtName()+"_S", "SWAP");
				assetSyn.setSourceTable(elsAsset.getId().getElsTrDtldTypeCd());
				setMarketVariable(assetSyn.getSynProdId(), assetSyn.getSynProdName(),MarketVariableType.ELS);
				
				SyntheticDetail temp1 = new SyntheticDetail(new SyntheticDetailId(assetSyn.getSynProdId(), elsMaster.getPdno()));
				SyntheticDetail temp2 = new SyntheticDetail(new SyntheticDetailId(assetSyn.getSynProdId(), irLeg.getBondId()));
				
//				TODO : Set Ir leg Info
				if(!elsAsset.getRcivSwapIntDvsnCd().equals("00")){
					temp1.setWeight(new BigDecimal(-1));
					temp2.setWeight(new BigDecimal(1));
				}
				else if(!elsAsset.getDfrmSwapIntDvsnCd().equals("00")){
					temp1.setWeight(new BigDecimal(1));
					temp2.setWeight(new BigDecimal(-1));
				}
				
				assetSynDetailList.add(temp1);
				assetSynDetailList.add(temp2);
				assetSyn.setSubProdList(assetSynDetailList);
				synList.add(assetSyn);
				break;

			default:
				break;
			}

			posList.add(getDebtPosition(elsAsset, elsMaster));
			if(getAssetPosition(elsAsset, elsMaster) !=null){
				posList.add(getAssetPosition(elsAsset, elsMaster));
			}
		}
		cnt = cnt + 1;
		log.info("Count:#0", cnt);
		save();
	}

	public void convertDigital(){
		int cnt = 0;
		Pdif004m0 elsMaster;
		
		PositionCrud assetPos;
		PositionCrud debtPos;
		
		Synthetics assetSyn;
		List<SyntheticDetail> assetSynDetailList = new ArrayList<SyntheticDetail>();
		
		posList.clear();

		// Digital ,issueProd : ALL Type, non Basket
		loadElsAssetList("03", "ALL", false);

		// Issue Data
		// Synthetic Data
		// Position Data

		for (Ivot015p0 elsAsset : elsAssetList) {
			elsMaster = elsAsset.getElsMaster();
			log.info("Asset : #0, #1", elsMaster.getPdno());
			if (idList.contains(elsMaster.getPdno())) {
				break;
			} else {
				idList.add(elsMaster.getPdno());
			}
			
			intrOption = new OptionCrud();
			intrOption.setOptionId(elsMaster.getPdno());
			intrOption.setOptionName(elsMaster.getProd().getPrdtName());
			intrOption.setExoticType(EExoticOptionType.Digital);
			intrOption.setIssueDate(elsMaster.getIssuDt());
			intrOption.setMaturityDate(elsMaster.getExpdDt());
			intrOption.setMultiplier(new Long(100));
			intrOption.setBasketYn(EBoolean.N);
			intrOption.setUserName(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).getDesc());
			
			intrOption.setDigitalType(EDigitalType.CashOrNothing);
			intrOption.setDigitalPayoff(elsMaster.getHgstErngRt().subtract(elsMaster.getLwstErngRt())
											.multiply(new BigDecimal(100)));
			
			for (Map.Entry<String, BigDecimal> entry : elsMaster.getElsUnderMap().entrySet()) {
				log.info("Entry Set :#0, #1", entry.getKey(), entry.getValue());
				intrOption.setUnderlyingId(entry.getKey());

				for (Pdif094p0 bb : elsMaster.getElsEventList()) {
					if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getId().getElsEvntSeq() == 0) {
						intrOption.setOptionType(EElsRchgType.getEnum(bb.getElsRchgTypeCd()).getDigitalCallType());
//						if(EElsRchgType.getGroupList("GT").contains(bb.getElsRchgTypeCd())){
//							intrOption.setOptionType(EOptionType.CALL);
//						}else{
//							intrOption.setOptionType(EOptionType.PUT);
//						}
						intrOption.setBasePrice(entry.getValue());
						intrOption.setStrikePrice(bb.getUnderEventMap().get(entry.getKey()));
						intrOption.setConversionRatio(bb.getRedemEvent().getPtciRt());

						intrOption.setEventEvalType(EElsEventEvaluationType.getEnum(bb.getElsEvntEvluBassDvsnCd()));
						intrOption.setReturnEvalType(EElsReturnEvaluationType.getEnum(bb.getElsEvluMthdTypeCd()));
						intrOption.setObservationType(EElsObserveFrequencyType.getEnum(bb.getElsObsvFrqcTypeCd()));
					} 
				}
			}			
			
			setIntrinsicBond(elsMaster, elsAsset.getId().getElsTrDtldTypeCd());
			
			if(intrBond !=null){
				SyntheticDetail temp = new SyntheticDetail( 
						new SyntheticDetailId( elsMaster.getPdno(), intrBond.getBondId()));
				setBondWeight(temp, elsMaster, elsAsset.getBondAdmsRt(), elsAsset.getUtlzFundPrcaAsrcRt());
				synDetailList.add(temp);
				
				intrOption.setOptionId(elsMaster.getPdno()+"_O");
				intrOption.setOptionName(elsMaster.getProd().getPrdtName()+"_내재옵션");
				
				SyntheticDetail temp2 = new SyntheticDetail(
						new SyntheticDetailId(elsMaster.getPdno(), intrOption.getOptionId()));
				temp2.setWeight(new BigDecimal(1));
				synDetailList.add(temp2);
				
				Synthetics debtSyn = new Synthetics(elsMaster.getPdno(), elsMaster.getProd().getPrdtName(), "ELS_DG");
				debtSyn.setSourceTable(elsAsset.getId().getElsTrDtldTypeCd());
				debtSyn.setSubProdList(synDetailList);
				
				synList.add(debtSyn);
				setMarketVariable(debtSyn.getSynProdId(), debtSyn.getSynProdName(), MarketVariableType.SYNTHETIC);
			}
			optionCrudList.add(intrOption);
			setMarketVariable(intrOption.getOptionId(), intrOption.getOptionName(),MarketVariableType.OPTION);

//TODO : Asset Product 			
			switch (elsAsset.getElsTrDtldTypeCd()) {
			case EX_BOND_OTC:
				break;
			case SWAP1:
			case SWAP2:
				setIrLeg(elsAsset, elsMaster);
				assetSyn = new Synthetics(elsMaster.getPdno()+"_S", elsMaster.getProd().getPrdtName()+"_S", "SWAP");
				assetSyn.setSourceTable(elsAsset.getId().getElsTrDtldTypeCd());
				
				setMarketVariable(assetSyn.getSynProdId(), assetSyn.getSynProdName(), MarketVariableType.SYNTHETIC);
				
				SyntheticDetail temp1 = new SyntheticDetail(new SyntheticDetailId(assetSyn.getSynProdId(), elsMaster.getPdno()));
				SyntheticDetail temp2 = new SyntheticDetail(new SyntheticDetailId(assetSyn.getSynProdId(), irLeg.getBondId()));
//				TODO : Set Ir leg Info
				if(!elsAsset.getRcivSwapIntDvsnCd().equals("00")){
					temp1.setWeight(new BigDecimal(-1));
					temp2.setWeight(new BigDecimal(1));
				}
				else if(!elsAsset.getDfrmSwapIntDvsnCd().equals("00")){
					temp1.setWeight(new BigDecimal(1));
					temp2.setWeight(new BigDecimal(-1));
				}
				
				assetSynDetailList.add(temp1);
				assetSynDetailList.add(temp2);
				assetSyn.setSubProdList(assetSynDetailList);
				synList.add(assetSyn);
				break;

			default:
				break;
			}

			posList.add(getDebtPosition(elsAsset, elsMaster));
			if(getAssetPosition(elsAsset, elsMaster) !=null){
				posList.add(getAssetPosition(elsAsset, elsMaster));
			}
		}
		cnt = cnt + 1;
		log.info("Count:#0", cnt);
		save();
	}
	
	public void convertCallSpread() {
		int cnt = 0;
		Pdif004m0 elsMaster;
		Synthetics debtSyn;
		Synthetics assetSyn;
		List<SyntheticDetail> assetSynDetailList = new ArrayList<SyntheticDetail>();
		SyntheticDetail synDetailLow;
		SyntheticDetail synDetailUp;

		PositionCrud assetPos;
		PositionCrud debtPos;

//		idList.clear();
		posList.clear();

		// Call Spread ,issueProd : ALL Type, non Basket
//		loadElsAssetList("23", "ALL", false);
		loadElsAssetList("SP", "ALL", false);

		// Issue Data
		// Synthetic Data
		// Position Data

		for (Ivot015p0 elsAsset : elsAssetList) {
			elsMaster = elsAsset.getElsMaster();
			log.info("Asset : #0, #1", elsMaster.getPdno());
			if (idList.contains(elsMaster.getPdno())) {
				break;
			} else {
				idList.add(elsMaster.getPdno());
			}
			
			setIntrinsicBond(elsMaster, elsAsset.getId().getElsTrDtldTypeCd());
			if(intrBond !=null){
				SyntheticDetail temp = new SyntheticDetail( 
						new SyntheticDetailId( elsMaster.getPdno(), intrBond.getBondId()));
				setBondWeight(temp, elsMaster, elsAsset.getBondAdmsRt(), elsAsset.getUtlzFundPrcaAsrcRt());
				synDetailList.add(temp);
			}
			
			intrOption = new OptionCrud();
			intrOption.setOptionId(elsMaster.getPdno() + "_O1");
			intrOption.setOptionName(elsMaster.getProd().getPrdtName() + "_내재옵션1");
			
			// intrOption.setExoticType(EExoticOptionType.);
			// intrOption.setBarrierType(EBarrierType.UpAndOut);
			intrOption.setIssueDate(elsMaster.getIssuDt());
			intrOption.setMaturityDate(elsMaster.getExpdDt());
			intrOption.setMultiplier(new Long(100));
			intrOption.setBasketYn(EBoolean.N);
			intrOption.setUserName(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).getDesc());
			

			intrOption2 = new OptionCrud();
			intrOption2.setOptionId(elsMaster.getPdno() + "_O2");
			intrOption2.setOptionName(elsMaster.getProd().getPrdtName() + "_내재옵션2");
			
			// intrOption2.setExoticType(EExoticOptionType.);
			// intrOption2.setBarrierType(EBarrierType.UpAndOut);
			intrOption2.setIssueDate(elsMaster.getIssuDt());
			intrOption2.setMaturityDate(elsMaster.getExpdDt());
			intrOption2.setMultiplier(new Long(100));
			intrOption2.setBasketYn(EBoolean.N);
			intrOption2.setUserName(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).getDesc());

			if(elsMaster.getElsOptDvsnCd().equals("02")){
				intrOption.setOptionType(EOptionType.CALL);
				intrOption2.setOptionType(EOptionType.CALL);	
			}
			else if(elsMaster.getElsOptDvsnCd().equals("23")){
				intrOption.setOptionType(EOptionType.PUT);
				intrOption2.setOptionType(EOptionType.PUT);	
			}
			
			for (Map.Entry<String, BigDecimal> entry : elsMaster.getElsUnderMap().entrySet()) {
				log.info("Entry Set :#0, #1", entry.getKey(), entry.getValue());
				intrOption.setUnderlyingId(entry.getKey());
				intrOption2.setUnderlyingId(entry.getKey());

				for (Pdif094p0 bb : elsMaster.getElsEventList()) {
					if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getElsEvntCndtTypeCd().equals("04")
							&& bb.getId().getElsEvntSeq() == 0) {
						intrOption.setBasePrice(entry.getValue());
						intrOption.setStrikePrice(bb.getUnderEventMap().get(entry.getKey()));
						intrOption.setConversionRatio(bb.getRedemEvent().getPtciRt());

						intrOption.setEventEvalType(EElsEventEvaluationType.getEnum(bb.getElsEvntEvluBassDvsnCd()));
						intrOption.setReturnEvalType(EElsReturnEvaluationType.getEnum(bb.getElsEvluMthdTypeCd()));
						intrOption.setObservationType(EElsObserveFrequencyType.getEnum(bb.getElsObsvFrqcTypeCd()));

					} else if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getElsEvntCndtTypeCd().equals("04")
							&& bb.getId().getElsEvntSeq() == 1) {
						intrOption2.setBasePrice(entry.getValue());
						intrOption2.setStrikePrice(bb.getUnderEventMap().get(entry.getKey()));
						intrOption2.setConversionRatio(bb.getRedemEvent().getPtciRt());

						intrOption2.setEventEvalType(EElsEventEvaluationType.getEnum(bb.getElsEvntEvluBassDvsnCd()));
						intrOption2.setReturnEvalType(EElsReturnEvaluationType.getEnum(bb.getElsEvluMthdTypeCd()));
						intrOption2.setObservationType(EElsObserveFrequencyType.getEnum(bb.getElsObsvFrqcTypeCd()));
					}
				}
			}
			optionCrudList.add(intrOption);
			optionCrudList.add(intrOption2);
			setMarketVariable(intrOption.getOptionId(), intrOption.getOptionName(), MarketVariableType.OPTION);
			setMarketVariable(intrOption2.getOptionId(), intrOption2.getOptionName(), MarketVariableType.OPTION);
			
			debtSyn = new Synthetics(elsMaster.getPdno(), elsMaster.getProd().getPrdtName()
						, EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).name());
			debtSyn.setSourceTable(elsAsset.getId().getElsTrDtldTypeCd());
			setMarketVariable(debtSyn.getSynProdId(), debtSyn.getSynProdName(), MarketVariableType.SYNTHETIC);

			synDetailLow = new SyntheticDetail(new SyntheticDetailId(debtSyn.getSynProdId(),intrOption.getOptionId()));
			synDetailUp = new SyntheticDetail(new SyntheticDetailId(debtSyn.getSynProdId(),intrOption2.getOptionId()));
			
			if(intrOption.getStrikePrice() != null && intrOption2.getStrikePrice() != null){
				if (intrOption.getStrikePrice().doubleValue() < intrOption2.getStrikePrice().doubleValue()) {
					synDetailLow.setWeight(new BigDecimal(1));
					synDetailUp.setWeight(new BigDecimal(-1));
				} else {
					synDetailLow.setWeight(new BigDecimal(-1));
					synDetailUp.setWeight(new BigDecimal(1));
				}
			}
			else{
//				synDetailLow.setWeight(new BigDecimal(-1));
//				synDetailUp.setWeight(new BigDecimal(1));
			}
			
			synDetailList.add(synDetailLow);
			synDetailList.add(synDetailUp);
			debtSyn.setSubProdList(synDetailList);
			
			synList.add(debtSyn);

//TODO : Asset Product 			
			switch (elsAsset.getElsTrDtldTypeCd()) {
			case EX_BOND_OTC:
				assetSyn = new Synthetics(elsMaster.getPdno()+"_A", elsMaster.getProd().getPrdtName()+"_A", debtSyn.getSyntheticType());
				setMarketVariable(assetSyn.getSynProdId(), assetSyn.getSynProdName(), MarketVariableType.SYNTHETIC);
				
				SyntheticDetail assetTemp1 = new SyntheticDetail(
							new SyntheticDetailId(assetSyn.getSynProdId(), synDetailLow.getId().getSubProdId()));
				assetTemp1.setWeight(synDetailLow.getWeight());
				assetTemp1.setWeight(synDetailLow.getFirstUnitPrice());

				SyntheticDetail assetTemp2 = new SyntheticDetail(
									new SyntheticDetailId(assetSyn.getSynProdId(), synDetailUp.getId().getSubProdId()));
				assetTemp2.setWeight(synDetailUp.getWeight());
				assetTemp2.setWeight(synDetailLow.getFirstUnitPrice());
				
				assetSynDetailList.add(assetTemp1);
				assetSynDetailList.add(assetTemp2);
				assetSyn.setSubProdList(assetSynDetailList);
				assetSyn.setSourceTable(elsAsset.getId().getElsTrDtldTypeCd());
				
				synList.add(assetSyn);
				break;
			case SWAP1:
			case SWAP2:
				setIrLeg(elsAsset, elsMaster);
				assetSyn = new Synthetics(elsMaster.getPdno()+"_S", elsMaster.getProd().getPrdtName()+"_S", "SWAP");
				assetSyn.setSourceTable(elsAsset.getId().getElsTrDtldTypeCd());
				setMarketVariable(assetSyn.getSynProdId(), assetSyn.getSynProdName(), MarketVariableType.SYNTHETIC);
				
				SyntheticDetail temp1 = new SyntheticDetail(new SyntheticDetailId(assetSyn.getSynProdId(), elsMaster.getPdno()));
				SyntheticDetail temp2 = new SyntheticDetail(new SyntheticDetailId(assetSyn.getSynProdId(), irLeg.getBondId()));
				
//				TODO: Set IR LEG INFO
				if(!elsAsset.getRcivSwapIntDvsnCd().equals("00")){
					temp1.setWeight(new BigDecimal(-1));
					temp2.setWeight(new BigDecimal(1));
				}
				else if(!elsAsset.getDfrmSwapIntDvsnCd().equals("00")){
					temp1.setWeight(new BigDecimal(1));
					temp2.setWeight(new BigDecimal(-1));
				}
				
				assetSynDetailList.add(temp1);
				assetSynDetailList.add(temp2);
				assetSyn.setSubProdList(assetSynDetailList);
				synList.add(assetSyn);
				break;

			default:
				break;
			}

			
			
			posList.add(getDebtPosition(elsAsset, elsMaster));
			if(getAssetPosition(elsAsset, elsMaster) !=null){
				posList.add(getAssetPosition(elsAsset, elsMaster));
			}
		}
		cnt = cnt + 1;
		log.info("Count:#0", cnt);
		save();
	}

	public void convertKnockOut(){
		int cnt = 0;
		Pdif004m0 elsMaster;
		
		PositionCrud assetPos;
		PositionCrud debtPos;
		
		Synthetics assetSyn;
		List<SyntheticDetail> assetSynDetailList = new ArrayList<SyntheticDetail>();
		
		posList.clear();

		// Knock Out ,issueProd : ALL Type, non Basket
		loadElsAssetList("KO", "ALL", false);

		// Issue Data
		// Synthetic Data
		// Position Data

		for (Ivot015p0 elsAsset : elsAssetList) {
			elsMaster = elsAsset.getElsMaster();
			log.info("Asset : #0, #1", elsMaster.getPdno());
			if (idList.contains(elsMaster.getPdno())) {
				break;
			} else {
				idList.add(elsMaster.getPdno());
			}
			
			intrOption = new OptionCrud();
			intrOption.setOptionId(elsMaster.getPdno());
			intrOption.setOptionName(elsMaster.getProd().getPrdtName());
			intrOption.setExoticType(EExoticOptionType.SingleBarrier);
			if(elsMaster.getElsOptDvsnCd().equals("01")){
				intrOption.setOptionType(EOptionType.CALL);
				intrOption.setBarrierType(EBarrierType.UpAndOut);
			}else if(elsMaster.getElsOptDvsnCd().equals("17")){
				intrOption.setOptionType(EOptionType.PUT);
				intrOption.setBarrierType(EBarrierType.DownAndOut);
			}else if(elsMaster.getElsOptDvsnCd().equals("19")){
				
			}
			intrOption.setIssueDate(elsMaster.getIssuDt());
			intrOption.setMaturityDate(elsMaster.getExpdDt());
			intrOption.setMultiplier(new Long(100));
			intrOption.setBasketYn(EBoolean.N);
			intrOption.setUserName(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).name());
			
			
			for (Map.Entry<String, BigDecimal> entry : elsMaster.getElsUnderMap().entrySet()) {
				log.info("Entry Set :#0, #1", entry.getKey(), entry.getValue());
				intrOption.setUnderlyingId(entry.getKey());

				for (Pdif094p0 bb : elsMaster.getElsEventList()) {
					if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getElsEvntCndtTypeCd().equals("02")) {
//					if ( bb.getElsEvntCndtTypeCd().equals("02")) {	
						intrOption.setUpBarrier(bb.getUnderEventMap().get(entry.getKey()));

						intrOption.setEventEvalType(EElsEventEvaluationType.getEnum(bb.getElsEvntEvluBassDvsnCd()));
						intrOption.setReturnEvalType(EElsReturnEvaluationType.getEnum(bb.getElsEvluMthdTypeCd()));
						intrOption.setObservationType(EElsObserveFrequencyType.getEnum(bb.getElsObsvFrqcTypeCd()));

					}
					else if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getElsEvntCndtTypeCd().equals("04")) {
//					else if (bb.getElsEvntCndtTypeCd().equals("04")) {	
						intrOption.setStrikePrice(bb.getUnderEventMap().get(entry.getKey()));
						intrOption.setConversionRatio(bb.getRedemEvent().getPtciRt());
						intrOption.setBasePrice(entry.getValue());
					}
				}
			}			
			
			setIntrinsicBond(elsMaster, elsAsset.getId().getElsTrDtldTypeCd());
			
			if(intrBond !=null){
				SyntheticDetail temp = new SyntheticDetail( 
						new SyntheticDetailId( elsMaster.getPdno(), intrBond.getBondId()));
				setBondWeight(temp, elsMaster, elsAsset.getBondAdmsRt(), elsAsset.getUtlzFundPrcaAsrcRt());
				synDetailList.add(temp);
				
				intrOption.setOptionId(elsMaster.getPdno()+"_O");
				intrOption.setOptionName(elsMaster.getProd().getPrdtName()+"_내재옵션");
				
				SyntheticDetail temp2 = new SyntheticDetail(
						new SyntheticDetailId(elsMaster.getPdno(), intrOption.getOptionId()));
				temp2.setWeight(new BigDecimal(1));
				synDetailList.add(temp2);
				
				Synthetics debtSyn = new Synthetics(elsMaster.getPdno(), elsMaster.getProd().getPrdtName(), "ELS_KO");
				debtSyn.setSourceTable(elsAsset.getId().getElsTrDtldTypeCd());
				debtSyn.setSubProdList(synDetailList);
				
				synList.add(debtSyn);
				setMarketVariable(debtSyn.getSynProdId(), debtSyn.getSynProdName(), MarketVariableType.SYNTHETIC);
			}
			optionCrudList.add(intrOption);
			setMarketVariable(intrOption.getOptionId(), intrOption.getOptionName(),MarketVariableType.OPTION);

//TODO : Asset Product 			
			switch (elsAsset.getElsTrDtldTypeCd()) {
			case EX_BOND_OTC:
				break;
			case SWAP1:
			case SWAP2:
				setIrLeg(elsAsset, elsMaster);
				assetSyn = new Synthetics(elsMaster.getPdno()+"_S", elsMaster.getProd().getPrdtName()+"_S", "SWAP");
				assetSyn.setSourceTable(elsAsset.getId().getElsTrDtldTypeCd());
				
				setMarketVariable(assetSyn.getSynProdId(), assetSyn.getSynProdName(), MarketVariableType.SYNTHETIC);
				
				SyntheticDetail temp1 = new SyntheticDetail(new SyntheticDetailId(assetSyn.getSynProdId(), elsMaster.getPdno()));
				SyntheticDetail temp2 = new SyntheticDetail(new SyntheticDetailId(assetSyn.getSynProdId(), irLeg.getBondId()));
//				TODO : Set Ir leg Info
				if(!elsAsset.getRcivSwapIntDvsnCd().equals("00")){
					temp1.setWeight(new BigDecimal(-1));
					temp2.setWeight(new BigDecimal(1));
				}
				else if(!elsAsset.getDfrmSwapIntDvsnCd().equals("00")){
					temp1.setWeight(new BigDecimal(1));
					temp2.setWeight(new BigDecimal(-1));
				}
				
				assetSynDetailList.add(temp1);
				assetSynDetailList.add(temp2);
				assetSyn.setSubProdList(assetSynDetailList);
				synList.add(assetSyn);
				break;

			default:
				break;
			}

			posList.add(getDebtPosition(elsAsset, elsMaster));
			if(getAssetPosition(elsAsset, elsMaster) !=null){
				posList.add(getAssetPosition(elsAsset, elsMaster));
			}
		}
		cnt = cnt + 1;
		log.info("Count:#0", cnt);
		save();
	}
	
	public void convertKnockOutCallPut(){
		int cnt = 0;
		Pdif004m0 elsMaster;
		BigDecimal upBarrier = new BigDecimal(0);
		BigDecimal downBarrier = new BigDecimal(1000);
		
		BigDecimal upStrike = new BigDecimal(0);
		BigDecimal downStrike = new BigDecimal(100000);

		SyntheticDetail synDetailCall;
		SyntheticDetail synDetailPut;
		
		PositionCrud assetPos;
		PositionCrud debtPos;
		
		Synthetics debtSyn;
		Synthetics assetSyn;
		List<SyntheticDetail> assetSynDetailList = new ArrayList<SyntheticDetail>();
		
		posList.clear();

		// Knock Out Call Put ,issueProd : ALL Type, non Basket
		loadElsAssetList("19", "ALL", false);

		// Issue Data
		// Synthetic Data
		// Position Data

		for (Ivot015p0 elsAsset : elsAssetList) {
			elsMaster = elsAsset.getElsMaster();
			log.info("Asset : #0, #1", elsMaster.getPdno());
			if (idList.contains(elsMaster.getPdno())) {
				break;
			} else {
				idList.add(elsMaster.getPdno());
			}
			
			intrOption = new OptionCrud();
			intrOption.setOptionId(elsMaster.getPdno()+"_O1");
			intrOption.setOptionName(elsMaster.getProd().getPrdtName()+"_내재옵션1");
			intrOption.setExoticType(EExoticOptionType.SingleBarrier);
			intrOption.setOptionType(EOptionType.CALL);
			intrOption.setBarrierType(EBarrierType.UpAndOut);
			
			
			intrOption.setIssueDate(elsMaster.getIssuDt());
			intrOption.setMaturityDate(elsMaster.getExpdDt());
			intrOption.setMultiplier(new Long(100));
			intrOption.setBasketYn(EBoolean.N);
			intrOption.setUserName(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).name());
			
			intrOption2 = new OptionCrud();
			intrOption2.setOptionId(elsMaster.getPdno()+"_O2");
			intrOption2.setOptionName(elsMaster.getProd().getPrdtName()+"_내재옵션2");
			intrOption2.setExoticType(EExoticOptionType.SingleBarrier);
			
			intrOption2.setOptionType(EOptionType.PUT);
			intrOption2.setBarrierType(EBarrierType.DownAndOut);
			
			intrOption2.setIssueDate(elsMaster.getIssuDt());
			intrOption2.setMaturityDate(elsMaster.getExpdDt());
			intrOption2.setMultiplier(new Long(100));
			intrOption2.setBasketYn(EBoolean.N);
			intrOption2.setUserName(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).name());
			
			for (Map.Entry<String, BigDecimal> entry : elsMaster.getElsUnderMap().entrySet()) {
//				log.info("Entry Set :#0, #1", entry.getKey(), entry.getValue());
				intrOption.setUnderlyingId(entry.getKey());
				intrOption2.setUnderlyingId(entry.getKey());

				for (Pdif094p0 bb : elsMaster.getElsEventList()) {
					if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getElsEvntCndtTypeCd().equals("02")) {
						if(upBarrier.doubleValue()<= bb.getUnderEventMap().get(entry.getKey()).doubleValue() ){
							upBarrier = bb.getUnderEventMap().get(entry.getKey());
						}
						if(downBarrier.doubleValue()>= bb.getUnderEventMap().get(entry.getKey()).doubleValue()){
							downBarrier= bb.getUnderEventMap().get(entry.getKey());
						}
						
						
						intrOption.setUpBarrier(upBarrier);

						intrOption.setEventEvalType(EElsEventEvaluationType.getEnum(bb.getElsEvntEvluBassDvsnCd()));
						intrOption.setReturnEvalType(EElsReturnEvaluationType.getEnum(bb.getElsEvluMthdTypeCd()));
						intrOption.setObservationType(EElsObserveFrequencyType.getEnum(bb.getElsObsvFrqcTypeCd()));
						
						intrOption2.setDownBarrier(downBarrier);

						intrOption2.setEventEvalType(EElsEventEvaluationType.getEnum(bb.getElsEvntEvluBassDvsnCd()));
						intrOption2.setReturnEvalType(EElsReturnEvaluationType.getEnum(bb.getElsEvluMthdTypeCd()));
						intrOption2.setObservationType(EElsObserveFrequencyType.getEnum(bb.getElsObsvFrqcTypeCd()));
						if(bb.getRedemEvent().getElsKoutOptDvsnCd()!=null 
								&& bb.getRedemEvent().getElsKoutOptDvsnCd().equals("01")){
							intrOption.setConversionRatio(bb.getRedemEvent().getPtciRt());
						}else if(bb.getRedemEvent().getElsKoutOptDvsnCd()!=null 
								&& bb.getRedemEvent().getElsKoutOptDvsnCd().equals("02")){
							intrOption2.setConversionRatio(bb.getRedemEvent().getPtciRt());
						}
					}
					else if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getElsEvntCndtTypeCd().equals("04")) {
						if(upStrike.doubleValue()<= bb.getUnderEventMap().get(entry.getKey()).doubleValue() ){
							upStrike = bb.getUnderEventMap().get(entry.getKey());
						}
						if(downStrike.doubleValue() >= bb.getUnderEventMap().get(entry.getKey()).doubleValue() ){
							downStrike= bb.getUnderEventMap().get(entry.getKey());
						}
						
						intrOption.setStrikePrice(upStrike);
//						intrOption.setConversionRatio(bb.getRedemEvent().getPtciRt());
						intrOption.setBasePrice(entry.getValue());
						
						intrOption2.setStrikePrice(downStrike);
//						intrOption2.setConversionRatio(bb.getRedemEvent().getPtciRt());
						intrOption2.setBasePrice(entry.getValue());
					}
				}
			}			
			optionCrudList.add(intrOption);
			optionCrudList.add(intrOption2);
			setMarketVariable(intrOption.getOptionId(), intrOption.getOptionName(),MarketVariableType.OPTION);
			setMarketVariable(intrOption2.getOptionId(), intrOption2.getOptionName(),MarketVariableType.OPTION);

			
			setIntrinsicBond(elsMaster, elsAsset.getId().getElsTrDtldTypeCd());
			
			if(intrBond !=null){
				SyntheticDetail temp = new SyntheticDetail( 
						new SyntheticDetailId( elsMaster.getPdno(), intrBond.getBondId()));
				setBondWeight(temp, elsMaster, elsAsset.getBondAdmsRt(), elsAsset.getUtlzFundPrcaAsrcRt());
				synDetailList.add(temp);
			}
			
			
			debtSyn = new Synthetics(elsMaster.getPdno(), elsMaster.getProd().getPrdtName()
					, EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).name());
			debtSyn.setSourceTable(elsAsset.getId().getElsTrDtldTypeCd());
			setMarketVariable(debtSyn.getSynProdId(), debtSyn.getSynProdName(), MarketVariableType.SYNTHETIC);

			synDetailCall = new SyntheticDetail(new SyntheticDetailId(debtSyn.getSynProdId(),intrOption.getOptionId()));
			synDetailPut = new SyntheticDetail(new SyntheticDetailId(debtSyn.getSynProdId(),intrOption2.getOptionId()));
		
			synDetailCall.setWeight(new BigDecimal(1));
			synDetailPut.setWeight(new BigDecimal(1));
		
			synDetailList.add(synDetailCall);
			synDetailList.add(synDetailPut);
			debtSyn.setSubProdList(synDetailList);
		
			synList.add(debtSyn);
		
//TODO : Asset Product 			
			switch (elsAsset.getElsTrDtldTypeCd()) {
			case EX_BOND_OTC:
				assetSyn = new Synthetics(elsMaster.getPdno()+"_A", elsMaster.getProd().getPrdtName()+"_A", debtSyn.getSyntheticType());
				setMarketVariable(assetSyn.getSynProdId(), assetSyn.getSynProdName(), MarketVariableType.SYNTHETIC);
				
				SyntheticDetail assetTemp1 = new SyntheticDetail(
							new SyntheticDetailId(assetSyn.getSynProdId(), synDetailCall.getId().getSubProdId()));
				assetTemp1.setWeight(synDetailCall.getWeight());
				assetTemp1.setWeight(synDetailCall.getFirstUnitPrice());

				SyntheticDetail assetTemp2 = new SyntheticDetail(
									new SyntheticDetailId(assetSyn.getSynProdId(), synDetailPut.getId().getSubProdId()));
				assetTemp2.setWeight(synDetailPut.getWeight());
				assetTemp2.setWeight(synDetailPut.getFirstUnitPrice());
				
				assetSynDetailList.add(assetTemp1);
				assetSynDetailList.add(assetTemp2);
				assetSyn.setSubProdList(assetSynDetailList);
				assetSyn.setSourceTable(elsAsset.getId().getElsTrDtldTypeCd());
				
				synList.add(assetSyn);
				
				break;
			case SWAP1:
			case SWAP2:
				setIrLeg(elsAsset, elsMaster);
				assetSyn = new Synthetics(elsMaster.getPdno()+"_S", elsMaster.getProd().getPrdtName()+"_S", "SWAP");
				assetSyn.setSourceTable(elsAsset.getId().getElsTrDtldTypeCd());
				
				setMarketVariable(assetSyn.getSynProdId(), assetSyn.getSynProdName(), MarketVariableType.SYNTHETIC);
				
				SyntheticDetail temp1 = new SyntheticDetail(new SyntheticDetailId(assetSyn.getSynProdId(), elsMaster.getPdno()));
				SyntheticDetail temp2 = new SyntheticDetail(new SyntheticDetailId(assetSyn.getSynProdId(), irLeg.getBondId()));
//				TODO : Set Ir leg Info
				if(!elsAsset.getRcivSwapIntDvsnCd().equals("00")){
					temp1.setWeight(new BigDecimal(-1));
					temp2.setWeight(new BigDecimal(1));
				}
				else if(!elsAsset.getDfrmSwapIntDvsnCd().equals("00")){
					temp1.setWeight(new BigDecimal(1));
					temp2.setWeight(new BigDecimal(-1));
				}
				
				assetSynDetailList.add(temp1);
				assetSynDetailList.add(temp2);
				assetSyn.setSubProdList(assetSynDetailList);
				synList.add(assetSyn);
				break;

			default:
				break;
			}

			posList.add(getDebtPosition(elsAsset, elsMaster));
			if(getAssetPosition(elsAsset, elsMaster) !=null){
				posList.add(getAssetPosition(elsAsset, elsMaster));
			}
		}
		cnt = cnt + 1;
		log.info("Count:#0", cnt);
		save();
	}
	public void convertKnockOutCall() {
		Pdif001m0 tempBase;
		Pdif004m0 aa;
		List<String> idList = new ArrayList<String>();
		int cnt = 0;
		elsAssetList = session.createCriteria(Ivot015p0.class).createAlias("elsMaster", "b")
				.add(eq("b.elsOptDvsnCd", "01")).add(eq("rdptYn", "N")).list();

		// for (Pdif004m0 aa : elsMasterList) {
		for (Ivot015p0 zz : elsAssetList) {
			aa = zz.getElsMaster();
			if (idList.contains(aa.getPdno())) {
				break;
			} else {
				idList.add(aa.getPdno());
			}

			if (aa.getElsUnderMap().size() != 1) {
				// TODO :Basket Barrier mapping
			} else {
				intrOption = new OptionCrud();
				intrOption.setOptionType(EOptionType.CALL);
				intrOption.setExoticType(EExoticOptionType.SingleBarrier);
				intrOption.setBarrierType(EBarrierType.UpAndOut);
				intrOption.setIssueDate(aa.getIssuDt());
				intrOption.setMaturityDate(aa.getExpdDt());
				intrOption.setMultiplier(new Long(100));
				intrOption.setUserName("KnockOutCall");

				if (zz.getId().getElsTrDtldTypeCd().equals("01") 
						|| zz.getId().getElsTrDtldTypeCd().equals("02")) {
					intrOption.setOptionId(aa.getPdno());
					intrOption.setOptionName(aa.getProd().getPrdtName());
				} else {
					intrOption.setOptionId("O_" + aa.getPdno());
					intrOption.setOptionName("내재옵션_" + aa.getProd().getPrdtName());

					intrBond = new IntrinsicBond();
					intrBond.setBondId("B_" + aa.getPdno());
					intrBond.setBondName("내재채권_" + aa.getProd().getPrdtName());
					intrBond.setIssuerId("한투");
					intrBond.setIssueDate(aa.getIssuDt());
					intrBond.setMaturityDate(aa.getExpdDt());
					intrBond.setFaceAmt(new BigDecimal(10000));
					intrBond.setBondCfType(EBondCfType.DISCOUNT);

					intrBondList.add(intrBond);
				}

				intrOption.setBasketYn(EBoolean.N);
				for (Map.Entry<String, BigDecimal> entry : aa.getElsUnderMap().entrySet()) {
					log.info("Entry Set :#0, #1", entry.getKey(), entry.getValue());
					intrOption.setUnderlyingId(entry.getKey());

					for (Pdif094p0 bb : aa.getElsEventList()) {
						if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getElsEvntCndtTypeCd().equals("02")) {
							// log.info("Under Event : #0, #1, #2",
							// bb.getId().getPdno(),
							// bb.getUnderEventMap().size(),
							// bb.getElsPaymentList().size());

							intrOption.setEventEvalType(EElsEventEvaluationType.getEnum(bb.getElsEvntEvluBassDvsnCd()));
							intrOption.setReturnEvalType(EElsReturnEvaluationType.getEnum(bb.getElsEvluMthdTypeCd()));
							intrOption.setObservationType(EElsObserveFrequencyType.getEnum(bb.getElsObsvFrqcTypeCd()));
							intrOption.setUpBarrier(bb.getUnderEventMap().get(entry.getKey()));
						} else if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getElsEvntCndtTypeCd().equals("04")) {
							intrOption.setStrikePrice(bb.getUnderEventMap().get(entry.getKey()));
							intrOption.setConversionRatio(bb.getRedemEvent().getPtciRt());
						}
					}
				}
				optionCrudList.add(intrOption);

			}
			cnt = cnt + 1;
		}
		log.info("Count:#0", cnt);
		save();
	}

	private void convertToSynthetic() {
		loadElsAssetList("01", "ELS", false);

		Synthetics tempSynProd;
		SyntheticDetail bondSynDetail;
		SyntheticDetail optSynDetail;
		List<SyntheticDetail> synDetailList = new ArrayList<SyntheticDetail>();
		List<String> synIdList = new ArrayList<String>();

		for (Ivot015p0 aa : elsAssetList) {
			tempSynProd = new Synthetics();
			tempSynProd.setSynProdId(aa.getId().getPdno());
			tempSynProd.setSynProdName(aa.getElsMaster().getProd().getPrdtName());
			tempSynProd.setSyntheticType("ELS");

			bondSynDetail = new SyntheticDetail(
					new SyntheticDetailId(aa.getId().getPdno(), "B_" + aa.getId().getPdno()));
			optSynDetail = new SyntheticDetail(new SyntheticDetailId(aa.getId().getPdno(), "O_" + aa.getId().getPdno()));

			bondSynDetail.setUserName(aa.getElsTrDtldTypeCd().getDesc());
			optSynDetail.setUserName(aa.getElsTrDtldTypeCd().getDesc());
			bondSynDetail.setLastUpdated(aa.getElsMaster().getElsOptDvsnCd());
			optSynDetail.setLastUpdated(aa.getElsMaster().getElsOptDvsnCd());

			optSynDetail.setWeight(new BigDecimal(1));

			if (aa.getBondAdmsRt().doubleValue() == 0) {
				if (aa.getElsMaster().getLwstErngRt().doubleValue() == 0) {
					bondSynDetail.setWeight(new BigDecimal(1));

					bondSynDetail.setSourceTable("AAA");
					optSynDetail.setSourceTable("AAA");
				} else {
					bondSynDetail.setWeight(aa.getElsMaster().getLwstErngRt());

					bondSynDetail.setSourceTable("BBB");
					optSynDetail.setSourceTable("BBB");

					// bondSynDetail.setFirstUnitPrice(aa.getElsMaster().getLwstErngRt().multiply(new
					// BigDecimal(10000)));
				}
			} else {
				bondSynDetail.setWeight(aa.getBondAdmsRt().divide(new BigDecimal(100)));
				bondSynDetail.setFirstUnitPrice(aa.getBondAdmsRt().multiply(new BigDecimal(10000))
						.divide(aa.getUtlzFundPrcaAsrcRt(), 1));

				optSynDetail.setFirstUnitPrice(aa.getOptAdmsRt().multiply(new BigDecimal(100)));
			}

			// synDetailList.clear();
			// synDetailList.add(bondSynDetail);
			// synDetailList.add(optSynDetail);
			// tempSynProd.setSubProdList(synDetailList);

			if (!synIdList.contains(tempSynProd.getSynProdId())) {
				synIdList.add(tempSynProd.getSynProdId());
				synList.add(tempSynProd);
				synDetailList.add(bondSynDetail);
				synDetailList.add(optSynDetail);
			}
			// for(Synthetics xx : synList){
			// if(xx.getSynProdId().equals(tempSynProd.getSynProdId())){
			// break;
			// }
			// }
			// log.info("IVOT :#0,  #1", aa.getId().getPdno(),
			// aa.getElsMaster().getElsOptDvsnCd());
		}

		for (Synthetics zz : synList) {
			// session.saveOrUpdate(zz);
			session.save(zz);
		}
		for (SyntheticDetail zz : synDetailList) {
			// session.saveOrUpdate(zz);
			session.save(zz);
		}
		session.flush();
	}

	
	private PositionCrud getAssetPosition(Ivot015p0 elsAsset, Pdif004m0 elsMaster){
		PositionCrud assetPos = new PositionCrud();
		assetPos.setPosId(elsAsset.getUtlzFundCd()+"_L_"+ elsMaster.getPdno()+"_"+elsAsset.getId().getTrOppnNo());
		assetPos.setPosName(elsMaster.getProd().getPrdtName()+"_A");
		
		assetPos.setPosSide("LONG");
		assetPos.setFundId(elsAsset.getUtlzFundCd());
		assetPos.setCpartyId(elsAsset.getId().getTrOppnNo());
		assetPos.setCurrency(elsAsset.getCrcyCd());
		assetPos.setTxType(elsAsset.getElsTrDtldTypeCd().getDesc());

		assetPos.setInitTxPrice(elsAsset.getElsPchsUnpr());
		assetPos.setInitFeeAmt(new BigDecimal(elsAsset.getDsgnFeeAmt()));
		assetPos.setInitHoldingQty(new BigDecimal(elsAsset.getAsstTrParAmt()/10000));
		assetPos.setInitTxPar(new BigDecimal(elsAsset.getAsstTrParAmt()));

		switch (elsAsset.getElsTrDtldTypeCd()) {
		case EX_OTC:
			assetPos.setProdId(elsMaster.getPdno());
			return assetPos;
		case EX_BOND_OTC:
			if(elsMaster.getElsOptDvsnCd().equals("02")){
				assetPos.setProdId(elsMaster.getPdno()+"_A");	
			}
			else{
				assetPos.setProdId(elsMaster.getPdno()+"_O");
			}
			
			return assetPos;
		case EX_ELS:
			assetPos.setProdId(elsMaster.getPdno());
			return assetPos;
		case SWAP1:
			assetPos.setProdId(elsMaster.getPdno()+"_S");
			return assetPos;
		case SWAP2:
			assetPos.setProdId(elsMaster.getPdno()+"_S");
			return assetPos;
		default:
			return null;
		}
				
	}

	private PositionCrud getDebtPosition(Ivot015p0 elsAsset, Pdif004m0 elsMaster){
		PositionCrud debtPos = new PositionCrud();
		debtPos.setPosId(elsAsset.getUtlzFundCd()+"_S_"+ elsMaster.getPdno()+"_"+elsAsset.getId().getTrOppnNo());
		debtPos.setPosName(elsMaster.getProd().getPrdtName()+"_D");
		debtPos.setPosSide("SHORT");
		debtPos.setProdId(elsMaster.getPdno());
		debtPos.setFundId(elsAsset.getUtlzFundCd());
		debtPos.setCpartyId(elsAsset.getId().getTrOppnNo());
		debtPos.setCurrency(elsAsset.getCrcyCd());
		debtPos.setTxType(elsAsset.getElsTrDtldTypeCd().getDesc());

		debtPos.setInitTxAmt(new BigDecimal(elsAsset.getDebtTrPymtAmt()));
		debtPos.setInitTxPrice(new BigDecimal(elsAsset.getDsgnPcstRt().doubleValue() * 100));
		debtPos.setInitFeeAmt(new BigDecimal(elsAsset.getDsgnFeeAmt()));
		debtPos.setInitHoldingQty(new BigDecimal(elsAsset.getDebtTrParAmt()/10000));
		debtPos.setInitTxPar(new BigDecimal(elsAsset.getDebtTrPymtAmt()));
		
		return debtPos;

	}

	public void save() {
	
		for (OptionCrud aa : optionCrudList) {
			session.saveOrUpdate(aa);
		}
		for (IntrinsicBond aa : intrBondList) {
			session.saveOrUpdate(aa);
		}
		for (SyntheticDetail aa	 : synDetailList) {
//			log.info("Save: #0, #1", aa.getId().getSynProdId());
//			session.saveOrUpdate(aa);
		}
		for (Synthetics aa : synList) {
//			log.info("Save: #0, #1", aa.getSynProdId());
			session.saveOrUpdate(aa);
		}
		for (PositionCrud aa : posList) {
			session.saveOrUpdate(aa);
		}
		for(MarketVariable aa : mvList){
			session.saveOrUpdate(aa);
		}
		session.flush();
		session.beginTransaction().commit();
	}

	private void setIntrinsicBond(Pdif004m0 elsMaster, String trCode) {
		intrBond =null;
		if (!optTrDtldTypeList.contains(trCode)) {
			intrBond = new IntrinsicBond();
			intrBond.setBondId(elsMaster.getPdno() + "_B");
			intrBond.setBondName(elsMaster.getProd().getPrdtName() + "_내재채권");
			intrBond.setIssuerId("한투");
			intrBond.setIssueDate(elsMaster.getIssuDt());
			intrBond.setMaturityDate(elsMaster.getExpdDt());
			intrBond.setFaceAmt(new BigDecimal(10000));
			intrBond.setBondCfType(EBondCfType.DISCOUNT);
			intrBondList.add(intrBond);
			
			setMarketVariable(intrBond.getBondId(), intrBond.getBondName(), MarketVariableType.BOND);
		}
	}

	private void setIrLeg(Ivot015p0 elsAsset, Pdif004m0 elsMaster){
		irLeg = new IntrinsicBond();
		irLeg.setBondId(elsMaster.getPdno()+"_R");
		irLeg.setBondName(elsMaster.getProd().getPrdtName());
		irLeg.setBondCfType(EBondCfType.FLOATING);
		irLeg.setFaceAmt(new BigDecimal(10000));
		irLeg.setCurrCd(elsAsset.getCrcyCd());
		irLeg.setIntTerm("M"+elsAsset.getIntExchFrqcDvsnCd());
//		irLeg.setCouponRate(couponRate);
		irLeg.setIssueDate(elsMaster.getIssuDt());
		irLeg.setMaturityDate(elsMaster.getExpdDt());
		irLeg.setRefRate(elsAsset.getSwapIntCalcMthdCd());
		
//		intrBondList.add(irLeg);
		setMarketVariable(irLeg.getBondId(), irLeg.getBondName(), MarketVariableType.BOND);
		
	}

	private void setBondWeight(SyntheticDetail synBond,Pdif004m0 elsMaster, BigDecimal weight, BigDecimal base){
		if (weight.doubleValue() == 0) {
			if (elsMaster.getLwstErngRt().doubleValue() == 0) {
				synBond.setWeight(new BigDecimal(1));
				synBond.setSourceTable("NoGurrantee");
			} else {
				synBond.setWeight(elsMaster.getLwstErngRt());
				synBond.setSourceTable("Gurrantee");
			}
		} else {
			synBond.setSourceTable("Provided");
			synBond.setWeight(weight.divide(new BigDecimal(100)));
			synBond.setFirstUnitPrice(weight.multiply(new BigDecimal(10000)).divide(base, 1));
		}
	}

	private void setMarketVariable(String id, String name , MarketVariableType type){
		MarketVariable mv = new MarketVariable();
		mv.setMvId(id);
		mv.setMvName(name);
		mv.setMvType(type);
		mv.setProductYN("Y");
		mv.setriskFactorYN("N");
		mv.setUnderlyingYN(EBoolean.N);
		mvList.add(mv);
	}
	
	public void setPosition(){
		
		loadElsAssetList("KO", "ALL", false);
		List<PositionCrud> posList = session.createCriteria(PositionCrud.class).list();
		
		List<String> posIdList = new ArrayList<String>();
//		List<PositionHisCrud> posHisList = session.createCriteria(PositionHisCrud.class)
//												.add(eq("id.bssd","20140129")).list();
		
		List<PositionHisCrud> posHisList = new  ArrayList<PositionHisCrud>();
		log.info("PosCRUD: #0,#1", posHisList.size(), elsAssetList.size());
		for(PositionHisCrud aa : posHisList){
			posIdList.add(aa.getId().getPosId());
		}

		for(PositionCrud aa : posList){
//			if(posIdList.contains(aa.getPosId())){
//				posIdList.add(aa.getPosId());
//			}else{
			
			for (Ivot015p0 elsAsset : elsAssetList) {
//				log.info("PosCRUD1: #0,#1", elsAsset.getId().getPdno(), aa.getProdId());
				if(aa.getProdId().equals(elsAsset.getId().getPdno())){
					PositionHisCrud  tempPos = new PositionHisCrud("20140129", aa.getPosId());
					if(aa.getPosSide().equals("SHORT")){
//						log.info("PosCRUD Short: #0,#1", elsAsset.getId().getPdno(), aa.getProdId());
						tempPos.setBookAmt(new BigDecimal(elsAsset.getDebtAbks()));
						tempPos.setPresValue(new BigDecimal(elsAsset.getDebtEvluAmt()));
						tempPos.setHoldingQty(new BigDecimal(elsAsset.getCblcAmt() / 10000));
						tempPos.setDailyReturn(new BigDecimal(0));

						if(elsAsset.getDebtEvluPrftAmt()> 0 ){
					    	tempPos.setDailyReturn(new BigDecimal(elsAsset.getDebtEvluPrftAmt()));
					    }
					    else if(elsAsset.getDebtEvluLossAmt() >0 ){
					    	tempPos.setDailyReturn(new BigDecimal(-1* elsAsset.getDebtEvluLossAmt()));
					    }
						tempPos.setFiscalBookAmt(new BigDecimal(elsAsset.getDebtBgtAbks()));
					}else{
//						PositionHisCrud  tempPos = new PositionHisCrud("20140129", aa.getPosId());
						tempPos.setBookAmt(new BigDecimal(elsAsset.getAsstAbks()));
						tempPos.setPresValue(new BigDecimal(elsAsset.getAsstEvluAmt()));
						tempPos.setHoldingQty(new BigDecimal(elsAsset.getAsstCblcParAmt() / 10000));
						tempPos.setDailyReturn(new BigDecimal(0));
						
						if(elsAsset.getAsstEvluPrftAmt() > 0 ){
					    	tempPos.setDailyReturn(new BigDecimal(elsAsset.getAsstEvluPrftAmt()));
					    }
					    else if(elsAsset.getAsstEvluLossAmt() >0 ){
					    	tempPos.setDailyReturn(new BigDecimal(-1* elsAsset.getAsstEvluLossAmt()));
					    }
						
						tempPos.setFiscalBookAmt(new BigDecimal(elsAsset.getAsstBgtAbks()));
					}
					posHisList.add(tempPos);
				}
						
/*				if(aa.getProdId().equals(elsAsset.getId().getPdno()) && aa.getPosSide().equals("SHORT")){
					log.info("PosCRUD Short: #0,#1", elsAsset.getId().getPdno(), aa.getProdId());
					PositionHisCrud  tempPos = new PositionHisCrud("20140129", aa.getPosId());
					tempPos.setBookAmt(new BigDecimal(elsAsset.getDebtAbks()));
					tempPos.setPresValue(new BigDecimal(elsAsset.getDebtEvluAmt()));
					tempPos.setHoldingQty(new BigDecimal(elsAsset.getCblcAmt() / 10000));
					tempPos.setDailyReturn(new BigDecimal(0));

					if(elsAsset.getDebtEvluPrftAmt()> 0 ){
				    	tempPos.setDailyReturn(new BigDecimal(elsAsset.getDebtEvluPrftAmt()));
				    }
				    else if(elsAsset.getDebtEvluLossAmt() >0 ){
				    	tempPos.setDailyReturn(new BigDecimal(-1* elsAsset.getDebtEvluLossAmt()));
				    }
					tempPos.setFiscalBookAmt(new BigDecimal(elsAsset.getDebtBgtAbks()));
					break;
				}
				else if(aa.getProdId().equals(elsAsset.getId().getPdno()) && aa.getPosSide().equals("LONG")){
					log.info("PosCRUD LONG: #0,#1", elsAsset.getId().getPdno(), aa.getProdId());
					
					PositionHisCrud  tempPos = new PositionHisCrud("20140129", aa.getPosId());
					tempPos.setBookAmt(new BigDecimal(elsAsset.getAsstAbks()));
					tempPos.setPresValue(new BigDecimal(elsAsset.getAsstEvluAmt()));
					tempPos.setHoldingQty(new BigDecimal(elsAsset.getAsstCblcParAmt() / 10000));
					tempPos.setDailyReturn(new BigDecimal(0));
					
					if(elsAsset.getAsstEvluPrftAmt() > 0 ){
				    	tempPos.setDailyReturn(new BigDecimal(elsAsset.getAsstEvluPrftAmt()));
				    }
				    else if(elsAsset.getAsstEvluLossAmt() >0 ){
				    	tempPos.setDailyReturn(new BigDecimal(-1* elsAsset.getAsstEvluLossAmt()));
				    }
					
					tempPos.setFiscalBookAmt(new BigDecimal(elsAsset.getAsstBgtAbks()));
					posHisList.add(tempPos);
					break;
				}*/
//			}
			}
		}
		log.info("His Size:#0, #1", posHisList.size());

		for(PositionHisCrud aa : posHisList){
			if(!posIdList.contains(aa.getId().getPosId())){
				session.saveOrUpdate(aa);
			}
		}
		session.flush();
	}

}
