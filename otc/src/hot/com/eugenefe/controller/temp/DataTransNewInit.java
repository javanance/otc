package com.eugenefe.controller.temp;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.in;
import static org.hibernate.criterion.Restrictions.sizeEq;
import static org.hibernate.criterion.Restrictions.sizeGt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.SyntheticDetail;
import com.eugenefe.entity.Synthetics;
import com.eugenefe.entity.crud.IntrinsicBond;
import com.eugenefe.entity.crud.OptionCrud;
import com.eugenefe.entity.crud.PositionCrud;
import com.eugenefe.entity.crud.PositionHisCrud;
import com.eugenefe.entity.raws.Ivot015p0;
import com.eugenefe.entity.raws.Pdif004m0;
import com.eugenefe.entity.raws.Pdif094p0;
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

@Name("dataTransNewInit")
@Scope(ScopeType.CONVERSATION)
// @AutoCreate
public class DataTransNewInit {
	@Logger
	private Log log;
	@In
	private Session session;

	private List<OptionCrud> optionCrudList = new ArrayList<OptionCrud>();
	private List<IntrinsicBond> intrBondList = new ArrayList<IntrinsicBond>();
	private List<Synthetics> synList = new ArrayList<Synthetics>();
	private List<MarketVariable> mvList = new ArrayList<MarketVariable>();
	private List<PositionCrud> debtPosList = new ArrayList<PositionCrud>();
	private List<PositionCrud> assetPosList = new ArrayList<PositionCrud>();
	private List<PositionHisCrud> posHisList = new ArrayList<PositionHisCrud>();

	private List<SyntheticDetail> synDetailList = new ArrayList<SyntheticDetail>();
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

//	private List<Pdif004m0> elsMasterList = new ArrayList<Pdif004m0>();

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

	public DataTransNewInit() {
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

//		optionCrudList = session.createCriteria(OptionCrud.class).list();
//		synList = session.createCriteria(Synthetics.class).list();
//
//		for (OptionCrud aa : optionCrudList) {
//			idList.add(aa.getOptionId());
//		}
//		for (Synthetics aa : synList) {
//			idList.add(aa.getSynProdId());
//		}
	}

	private void setBase() {
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
		// koTypeList.add("19");

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

		// crit = crit.add(eq("rdptYn", "N"));
		// crit = crit .createAlias("elsMaster", "b").add(eq("b.elsOptDvsnCd",
		// optType));

		if (optType.equals("OPTION_BOND")) {
			crit = crit.createAlias("elsMaster", "b").add(in("b.elsOptDvsnCd", optTypeList));
		} else if (optType.equals("KO")) {
			crit = crit.createAlias("elsMaster", "b").add(in("b.elsOptDvsnCd", koTypeList));
		} else if (optType.equals("SP")) {
			crit = crit.createAlias("elsMaster", "b").add(in("b.elsOptDvsnCd", spTypeList));
		} else if (optType.equals("PV")) {
			crit = crit.createAlias("elsMaster", "b").add(in("b.elsOptDvsnCd", vanillaTypeList));
		} else {
			crit = crit.createAlias("elsMaster", "b").add(eq("b.elsOptDvsnCd", optType));
		}

		if (basket) {
			crit = crit.add(sizeGt("b.elsUnderMap", 1));
		} else {
			crit = crit.add(sizeEq("b.elsUnderMap", 1));
		}

		if (issueType.equals("ALL")) {
		} else if (issueType.equals("OTC")) {
			crit = crit.add(in("id.elsTrDtldTypeCd", optTrDtldTypeList));
		} else if (issueType.equals("D_ELS")) {
			crit = crit.add(in("id.elsTrDtldTypeCd", dElsTrDtldTypeList));
		} else if (issueType.equals("P_ELS")) {
			crit = crit.add(in("id.elsTrDtldTypeCd", pElsTrDtldTypeList));
		} else if (issueType.equals("ELS")) {
			crit = crit.add(in("id.elsTrDtldTypeCd", elsTrDtldTypeList));
		} else if (issueType.equals("SWAP")) {
			crit = crit.add(in("id.elsTrDtldTypeCd", swapTrDtldTypeList));
		} else{
			crit = crit.add(eq("id.elsTrDtldTypeCd", issueType));
		}

		elsAssetList = crit.list();
	}

//	For RULE 01, 05
	public void mapSingleOptionOnly(){
		Pdif004m0 elsMaster;
		optionCrudList.clear();
		intrBondList.clear();
		debtPosList.clear();
		synList.clear();
		assetPosList.clear();
		
		loadElsAssetList("OPTION_BOND","OTC" , false);
		log.info("Size of Session :#0", elsAssetList.size());
		for (Ivot015p0 elsAsset : elsAssetList) {
			elsMaster = elsAsset.getElsMaster();
			if(!checkDup(elsMaster)){
				setDebtOption(elsMaster, false);
			}
			debtPosList.add(getDebtPosition(elsAsset, elsMaster));
			if("02".equals(elsAsset.getId().getElsTrDtldTypeCd())){
				assetPosList.add(getAssetPosition(elsAsset, elsMaster));
			}
		}
//		save();
		saveUpdate();
	}
	
//	For RULE 02, 03,06,07
	public void mapSingleOptionBond(){
		Pdif004m0 elsMaster;
		optionCrudList.clear();
		intrBondList.clear();
		debtPosList.clear();
		synList.clear();
		assetPosList.clear();

		loadElsAssetList("OPTION_BOND", "ELS" , false);
		log.info("Size of Session1 :#0", elsAssetList.size());
		for (Ivot015p0 elsAsset : elsAssetList) {
			elsMaster = elsAsset.getElsMaster();
			if(!checkDup(elsMaster)){
				setDebtOption(elsMaster, true);
				setIntrinsicBond(elsAsset,elsMaster);
			}
			debtPosList.add(getDebtPosition(elsAsset,elsMaster));
			switch (elsAsset.getElsTrDtldTypeCd()) {
			case EX_BOND_OTC:
				assetPosList.add(getAssetPosition(elsAsset, elsMaster));
				break;
			case EX_ELS:
				assetPosList.add(getAssetPosition(elsAsset, elsMaster));
				break;
			default :
				break;	
			}
		}
//		save();
		saveUpdate();
	}
//	For RULE 09
	public void mapSingleOptionSwap(){
		Pdif004m0 elsMaster;
		optionCrudList.clear();
		intrBondList.clear();
		debtPosList.clear();
		synList.clear();
		assetPosList.clear();
		
		loadElsAssetList("OPTION_BOND", "SWAP" , false);
		log.info("Size of Session1 :#0", elsAssetList.size());
		for (Ivot015p0 elsAsset : elsAssetList) {
			elsMaster = elsAsset.getElsMaster();
			log.info("ELS MASTER :#0", elsMaster.getPdno());
			if(!checkDup(elsMaster)){
				setDebtOption(elsMaster, true);
				setIntrinsicBond(elsAsset,elsMaster);

				setIrLeg(elsAsset, elsMaster);
				setSwapProduct(elsMaster, true);
			}
			
			debtPosList.add(getDebtPosition(elsAsset,elsMaster));
			switch (elsAsset.getElsTrDtldTypeCd()) {
			case SWAP1:
				assetPosList.add(getAssetPosition(elsAsset, elsMaster));
				break;
			case SWAP2:
				assetPosList.add(getAssetPosition(elsAsset, elsMaster));
				break;
			default :
				break;	
			}
		}
		
//		save();
		saveUpdate();
	}

	public void mapStepDown(){
		
	}
	
	public void setPositionHis(){
		elsAssetList.clear();
		elsAssetList.clear();
		List<String> posIdList = new ArrayList<String>();
		Criteria crit = session.createCriteria(Ivot015p0.class);
		crit = crit.add(eq("rdptYn", "N"));
		elsAssetList = crit.list();

		assetPosList = session.createCriteria(PositionCrud.class).list();
		log.info("List: #0", assetPosList.size());
//		for(PositionCrud aa : assetPosList){
////			posIdList.add(aa.getPosId());
//			posIdList.add(aa.getProdId().substring(0, 12));
////			posIdMap.put(aa.getPosId(), aa.getProdId().substring(0, 12));
//		}
		int cnt =0;
		BigDecimal pv= new BigDecimal(0);
		BigDecimal bookAmt = new BigDecimal(0);
		BigDecimal parAmt = new BigDecimal(0);
		BigDecimal fiscalBookAmt= new BigDecimal(0);
		BigDecimal holdingQty= new BigDecimal(0);
		BigDecimal dailyReturn= new BigDecimal(0);
		BigDecimal unitPrice1= new BigDecimal(0); 
		BigDecimal unitPrice2= new BigDecimal(0);
	
		for( PositionCrud bb : assetPosList){
			cnt = 0;
			 pv= new BigDecimal(0);
			 bookAmt = new BigDecimal(0);
			 parAmt = new BigDecimal(0);
			 fiscalBookAmt= new BigDecimal(0);
			 holdingQty= new BigDecimal(0);
			 dailyReturn= new BigDecimal(0);
			 unitPrice1= new BigDecimal(0); 
			 unitPrice2= new BigDecimal(0);
//			 log.info("PosId:#0,#1", bb.getPosId(), bb.getPosSide());
			for(Ivot015p0 elsAsset : elsAssetList){
				if(bb.getPosId().equals(getAssetPositionId(elsAsset))){
//				log.info("PosId1:#0,#1", getAssetPositionId(elsAsset));
//				TODO : set Basedate
					PositionHisCrud  tempPos = new PositionHisCrud("20140129", bb.getPosId());
					tempPos.setParAmt(new BigDecimal(elsAsset.getAsstCblcTrAmt()));
					tempPos.setBookAmt(new BigDecimal(elsAsset.getAsstAbks()));
					
					tempPos.setRefParAmt(new BigDecimal(elsAsset.getCblcAmt()));
					tempPos.setRefBookAmt(new BigDecimal(elsAsset.getDebtAbks()));
					
					tempPos.setPresValue(new BigDecimal(elsAsset.getAsstEvluAmt()));
					tempPos.setHoldingQty(new BigDecimal(elsAsset.getElsPchsQty()- elsAsset.getAsstAcmlRpchQty()).multiply(elsAsset.getElsParAmt()).setScale(4).divide(new BigDecimal(10000), RoundingMode.HALF_UP));
					
					tempPos.setDailyReturn(new BigDecimal(0));
					tempPos.setUnitPrice1(elsAsset.getAsstEvluUnpr().multiply(new BigDecimal(10000)).setScale(4).divide(elsAsset.getElsParAmt()));
					
					if(elsAsset.getAsstEvluPrftAmt() > 0 ){
				    	tempPos.setDailyReturn(new BigDecimal(elsAsset.getAsstEvluPrftAmt()));
				    }
				    else if(elsAsset.getAsstEvluLossAmt() >0 ){
				    	tempPos.setDailyReturn(new BigDecimal(-1* elsAsset.getAsstEvluLossAmt()));
				    }
					
					tempPos.setFiscalBookAmt(new BigDecimal(elsAsset.getAsstBgtAbks()));
					posHisList.add(tempPos);
				}
				else if(bb.getPosId().equals(elsAsset.getId().getPdno()+"_S")){
					cnt = cnt+1;
					parAmt =parAmt.add(new BigDecimal(elsAsset.getDebtTrPymtAmt()-elsAsset.getDebtAcmlRpchAmt()-elsAsset.getDebtAcmlRpchRbuyAmt()));
					bookAmt= bookAmt.add(new BigDecimal(elsAsset.getDebtAbks()));
					holdingQty = holdingQty.add(new BigDecimal(elsAsset.getElsPchsQty()-elsAsset.getDebtAcmlRpchQty()-elsAsset.getDebtAcmlRpchRbuyQty())
												.multiply(elsAsset.getElsParAmt()));
					fiscalBookAmt = fiscalBookAmt.add(new BigDecimal(elsAsset.getDebtBgtAbks()));
					pv = pv.add(new BigDecimal(elsAsset.getDebtEvluAmt()));
					if(elsAsset.getId().getElsTrDtldTypeCd().equals("01") 
							||	elsAsset.getId().getElsTrDtldTypeCd().equals("02")){
						unitPrice1 = elsAsset.getDebtEvluUnpr().multiply(new BigDecimal(10000)); 
							
					}else{
						if(unitPrice1.doubleValue()==0){
							unitPrice1 = elsAsset.getDebtEvluUnpr(); 
						}else if( unitPrice1.doubleValue() != elsAsset.getDebtEvluUnpr().doubleValue()){
							unitPrice2 = elsAsset.getDebtEvluUnpr();
						}	
					}
					
					if(elsAsset.getDebtEvluPrftAmt()> 0 ){
				    	dailyReturn.add(new BigDecimal(elsAsset.getDebtEvluPrftAmt()));
				    }
				    else if(elsAsset.getDebtEvluLossAmt() >0 ){
				    	dailyReturn.subtract(new BigDecimal(elsAsset.getDebtEvluLossAmt()));
				    }
				}	
			}
			if(cnt>0){
				PositionHisCrud  tempPos = new PositionHisCrud("20140129", bb.getPosId());
				tempPos.setPresValue(pv);
				tempPos.setUnitPrice1(unitPrice1);
				tempPos.setUnitPrice2(unitPrice2);
				tempPos.setParAmt(parAmt);
				tempPos.setBookAmt(bookAmt);
				tempPos.setFiscalBookAmt(fiscalBookAmt);
				tempPos.setDailyReturn(dailyReturn);
				tempPos.setHoldingQty(holdingQty.setScale(4).divide(new BigDecimal(10000), RoundingMode.HALF_UP));
				posHisList.add(tempPos);
			}
		}
		savePosition();
	}
	
 	public void setDebtOption(Pdif004m0 elsMaster, boolean isInstrinsic){
		Synthetics optionSyn;
		SyntheticDetail synDetailOption1;
		SyntheticDetail synDetailOption2 ;
		List<SyntheticDetail> optSynDetailList = new ArrayList<SyntheticDetail>();
		
		List<OptionCrud> debtOptionList = new ArrayList<OptionCrud>();
		
		if(koTypeList.contains(elsMaster.getElsOptDvsnCd())){
			optionCrudList.add(getKnockOut(elsMaster, isInstrinsic ));
		}else if(vanillaTypeList.contains(elsMaster.getElsOptDvsnCd())){
			optionCrudList.add(getPlainVanilla(elsMaster, isInstrinsic));
		}else if("03".equals(elsMaster.getElsOptDvsnCd())){
			optionCrudList.add(getDigital(elsMaster, isInstrinsic));
		}	
		else if(spTypeList.contains(elsMaster.getElsOptDvsnCd())){
			debtOptionList = getSpread(elsMaster);
			if(isInstrinsic){
				optionSyn = new Synthetics(elsMaster.getPdno()+"_O", elsMaster.getProd().getPrdtName()+"_내재옵션", "SP");
				synDetailOption1 = new SyntheticDetail(elsMaster.getPdno()+"_O", debtOptionList.get(0).getOptionId());
				synDetailOption2 = new SyntheticDetail(elsMaster.getPdno()+"_O", debtOptionList.get(1).getOptionId());
			}
			else{
				optionSyn = new Synthetics(elsMaster.getPdno(), elsMaster.getProd().getPrdtName(), "SP");
				synDetailOption1 = new SyntheticDetail(elsMaster.getPdno(), debtOptionList.get(0).getOptionId());
				synDetailOption2 = new SyntheticDetail(elsMaster.getPdno(), debtOptionList.get(1).getOptionId());	
			}

			optionSyn.setSourceTable(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).name());
			
			synDetailOption1.setWeight(new BigDecimal(1));
			synDetailOption2.setWeight(new BigDecimal(-1));
			optSynDetailList.add(synDetailOption1);
			optSynDetailList.add(synDetailOption2);
			
			optionSyn.setSubProdList(optSynDetailList);
			synList.add(optionSyn);
			optionCrudList.addAll(debtOptionList);
		}
		
		else if("19".equals(elsMaster.getElsOptDvsnCd())){
			debtOptionList = getKOCallPut(elsMaster);
			
			if(isInstrinsic){
				optionSyn = new Synthetics(elsMaster.getPdno()+"_O", elsMaster.getProd().getPrdtName()+"_내재옵션", "KO_CP");
				synDetailOption1 = new SyntheticDetail(elsMaster.getPdno()+"_O", debtOptionList.get(0).getOptionId());
				synDetailOption2 = new SyntheticDetail(elsMaster.getPdno()+"_O", debtOptionList.get(1).getOptionId());
				
			}else{
				optionSyn = new Synthetics(elsMaster.getPdno(), elsMaster.getProd().getPrdtName(), "KO_CP");
				synDetailOption1 = new SyntheticDetail(elsMaster.getPdno(), debtOptionList.get(0).getOptionId());
				synDetailOption2 = new SyntheticDetail(elsMaster.getPdno(), debtOptionList.get(1).getOptionId());
				
			}
			optionSyn.setSourceTable(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).name());
				
			synDetailOption1.setWeight(new BigDecimal(1));
			synDetailOption2.setWeight(new BigDecimal(1));
			
			optSynDetailList.add(synDetailOption1);
			optSynDetailList.add(synDetailOption2);
			
			optionSyn.setSubProdList(optSynDetailList);
			synList.add(optionSyn);
			optionCrudList.addAll(debtOptionList);
			
		}
	}

	private PositionCrud getDebtPosition(Pdif004m0 elsMaster) {
		PositionCrud debtPos = new PositionCrud();

		debtPos.setPosId(elsMaster.getPdno() + "_S");
		debtPos.setPosName(elsMaster.getProd().getPrdtName() + "_D");
		debtPos.setPosSide("SHORT");
		debtPos.setProdId(elsMaster.getPdno());
		debtPos.setFundId("ELS_FUND");
		// debtPos.setCpartyId(elsAsset.getId().getTrOppnNo());
		debtPos.setCurrency("KRW");
		debtPos.setSettleCurrency(elsMaster.getCrcyCd());
		//	debtPos.setTxType(elsAsset.getElsTrDtldTypeCd().getDesc());
		debtPos.setElsOptType(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).name());

		debtPos.setInitTxDate(elsMaster.getIssuDt());
		debtPos.setInitTxPrice(elsMaster.getElsIssuPric().multiply(new BigDecimal(10000)).setScale(4).divide(elsMaster.getElsPapr(),RoundingMode.HALF_UP));
//		debtPos.setInitTxPrice(new BigDecimal(elsMaster.getLastIssuPymtAmt()*10000 / elsMaster.getLastIssuParAmt() ));
		
//		debtPos.setInitTxAmt(new BigDecimal(elsMaster.getLastIssuParAmt()));
		debtPos.setInitTxAmt(new BigDecimal(elsMaster.getLastIssuPymtAmt()));
		// debtPos.setInitFeeAmt(new BigDecimal(els)));
		debtPos.setInitTxPar(new BigDecimal(elsMaster.getLastIssuParAmt()));
		
		debtPos.setInitHoldingQty(new BigDecimal(elsMaster.getLastIssuParAmt()).setScale(8).divide(new BigDecimal(10000),RoundingMode.HALF_UP));

		return debtPos;
	}

	private PositionCrud getAssetPosition(Ivot015p0 elsAsset, Pdif004m0 elsMaster) {
		PositionCrud assetPos = new PositionCrud();
		
		assetPos.setPosId(getAssetPositionId(elsAsset));
		assetPos.setPosName(elsMaster.getProd().getPrdtName() + "_A");
		assetPos.setProdId(getAssetProductId(elsAsset));
		
		assetPos.setPosSide("LONG");
		assetPos.setFundId(elsAsset.getUtlzFundCd());
		assetPos.setCpartyId(elsAsset.getId().getTrOppnNo());
		assetPos.setCurrency("KRW");
		assetPos.setSettleCurrency(elsAsset.getCrcyCd());
		assetPos.setTxType(elsAsset.getElsTrDtldTypeCd().name());
		assetPos.setElsOptType(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).name());
		
		assetPos.setInitTxDate(elsAsset.getCtrtDt());
		assetPos.setInitTxPrice(elsAsset.getElsPchsUnpr().multiply(new BigDecimal(10000)).setScale(4).divide(elsAsset.getElsParAmt(), RoundingMode.HALF_UP));
		
//		TODO : Change after initial ETL 
		assetPos.setInitTxAmt(new BigDecimal(elsAsset.getAsstCblcTrAmt()+elsAsset.getAsstAcmlRpchTrAmt()));
//		assetPos.setInitTxAmt(new BigDecimal(elsAsset.getAsstCblcTrAmt());
		
		assetPos.setInitFeeAmt(new BigDecimal(elsAsset.getDsgnFeeAmt()));
		assetPos.setInitTxPar(new BigDecimal(elsAsset.getAsstTrParAmt()));

		assetPos.setInitHoldingQty(new BigDecimal(elsAsset.getElsPchsQty()).multiply(elsAsset.getElsParAmt()).setScale(4).divide(new BigDecimal(10000), RoundingMode.HALF_UP));

		/*switch (elsAsset.getElsTrDtldTypeCd()) {
		case EX_OTC:
			assetPos.setProdId(elsMaster.getPdno());
			return assetPos;
		case EX_BOND_OTC:
			assetPos.setProdId(elsMaster.getPdno() + "_O");
			return assetPos;
		case EX_ELS:
			assetPos.setProdId(elsMaster.getPdno());
			return assetPos;
		case SWAP1:
			assetPos.setProdId(elsMaster.getPdno() + "_S");
			return assetPos;
		case SWAP2:
			assetPos.setProdId(elsMaster.getPdno() + "_S");
			return assetPos;
		default:
			return null;
		}*/
		return assetPos;
	}

	private void save() {
		for (OptionCrud aa : optionCrudList) {
			setMarketVariable(aa.getOptionId(), aa.getOptionName()	, MarketVariableType.OPTION);
			session.save(aa);
		}
		for (IntrinsicBond aa : intrBondList) {
			setMarketVariable(aa.getBondId(), aa.getBondName()	, MarketVariableType.BOND);
			session.save(aa);
		}
//		for (SyntheticDetail aa : synDetailList) {
			// log.info("Save: #0, #1", aa.getId().getSynProdId());
			// session.saveOrUpdate(aa);
//		}
		for (Synthetics aa : synList) {
			setMarketVariable(aa.getSynProdId(), aa.getSynProdName(), MarketVariableType.SYNTHETIC);
			// log.info("Save: #0, #1", aa.getSynProdId());
			session.save(aa);
		}
		for (PositionCrud aa : debtPosList) {
//			log.info("Save Position: #0, #1", aa.getPosId(), debtPosList.size());
			session.save(aa);
		}
		for (PositionCrud aa : assetPosList) {
//			log.info("Save Asset Position: #0, #1", aa.getPosId(), assetPosList.size());
			session.save(aa);
		}
		for (MarketVariable aa : mvList) {
			session.save(aa);
		}
	
		session.flush();
		session.beginTransaction().commit();
	}
	private void savePosition() {
		for (PositionHisCrud aa : posHisList) {
			session.save(aa);
		}
		session.flush();
		session.beginTransaction().commit();
	}
	
	private void saveUpdate() {
		for (OptionCrud aa : optionCrudList) {
			setMarketVariable(aa.getOptionId(), aa.getOptionName()	, MarketVariableType.OPTION);
			session.saveOrUpdate(aa);
		}
		for (IntrinsicBond aa : intrBondList) {
			setMarketVariable(aa.getBondId(), aa.getBondName()	, MarketVariableType.BOND);
			session.saveOrUpdate(aa);
		}
//		for (SyntheticDetail aa : synDetailList) {
			// log.info("Save: #0, #1", aa.getId().getSynProdId());
			// session.saveOrUpdate(aa);
//		}
		for (Synthetics aa : synList) {
			setMarketVariable(aa.getSynProdId(), aa.getSynProdName(), MarketVariableType.SYNTHETIC);
			// log.info("Save: #0, #1", aa.getSynProdId());
			session.saveOrUpdate(aa);
		}
		for (PositionCrud aa : debtPosList) {
			session.saveOrUpdate(aa);
		}
		for (PositionCrud aa : assetPosList) {
			session.saveOrUpdate(aa);
		}
		for (MarketVariable aa : mvList) {
			session.saveOrUpdate(aa);
		}
		session.flush();
		session.beginTransaction().commit();
	}

	private void setIrLeg(Ivot015p0 elsAsset, Pdif004m0 elsMaster) {
		irLeg = new IntrinsicBond();
		irLeg.setBondId(elsMaster.getPdno() + "_R");
		irLeg.setBondName(elsMaster.getProd().getPrdtName()+"_IR_LEG");
		irLeg.setBondCfType(EBondCfType.FLOATING);
		irLeg.setFaceAmt(new BigDecimal(10000));
		irLeg.setFundedYn(EBoolean.N);
		irLeg.setCurrCd(elsAsset.getCrcyCd());
		irLeg.setIntTerm("M" + elsAsset.getIntExchFrqcDvsnCd());
		irLeg.setCouponRate(elsAsset.getDfrmIntRtEtcRt().divide(new BigDecimal(100)));
		irLeg.setIssueDate(elsMaster.getIssuDt());
		irLeg.setMaturityDate(elsMaster.getExpdDt());
//		irLeg.setRefRate(elsAsset.getSwapIntCalcMthdCd());
		if("01".equals(elsAsset.getDfrmSwapIntDvsnCd())){
			irLeg.setRefRate("CD91");
		}

		intrBondList.add(irLeg);
//		setMarketVariable(irLeg.getBondId(), irLeg.getBondName(), MarketVariableType.BOND);

	}

	private void setMarketVariable(String id, String name, MarketVariableType type) {
		MarketVariable mv = new MarketVariable();
		mv.setMvId(id);
		mv.setMvName(name);
		mv.setMvType(type);
		mv.setProductYN("Y");
		mv.setriskFactorYN("N");
		mv.setUnderlyingYN(EBoolean.N);
		mvList.add(mv);
	}

	private List<OptionCrud> getSpread(Pdif004m0 elsMaster) {
		List<OptionCrud> optionList = new ArrayList<OptionCrud>();

		intrOption = getOptionDefault(elsMaster,false);
		intrOption2 = getOptionDefault(elsMaster,false);

		intrOption.setOptionId(elsMaster.getPdno() + "_O1");
		intrOption.setOptionName(elsMaster.getProd().getPrdtName() + "_내재옵션1");

		intrOption2.setOptionId(elsMaster.getPdno() + "_O2");
		intrOption2.setOptionName(elsMaster.getProd().getPrdtName() + "_내재옵션2");

		for (Map.Entry<String, BigDecimal> entry : elsMaster.getElsUnderMap().entrySet()) {
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
		optionList.add(intrOption);
		optionList.add(intrOption2);
		return optionList;
	}

	private List<OptionCrud> getKOCallPut(Pdif004m0 elsMaster) {
		List<OptionCrud> optionList = new ArrayList<OptionCrud>();
		
		BigDecimal upBarrier = new BigDecimal(0);
		BigDecimal downBarrier = new BigDecimal(1000);

		BigDecimal upStrike = new BigDecimal(0);
		BigDecimal downStrike = new BigDecimal(100000);

		// Knock Out Call Put ,issueProd : ALL Type, non Basket
		intrOption = getOptionDefault(elsMaster, false);
		intrOption2 = getOptionDefault(elsMaster, false);

		intrOption.setOptionId(elsMaster.getPdno() + "_O1");
		intrOption.setOptionName(elsMaster.getProd().getPrdtName() + "_내재옵션1");
		intrOption.setExoticType(EExoticOptionType.SingleBarrier);
		intrOption.setOptionType(EOptionType.CALL);
		intrOption.setBarrierType(EBarrierType.UpAndOut);


		intrOption2.setOptionId(elsMaster.getPdno() + "_O2");
		intrOption2.setOptionName(elsMaster.getProd().getPrdtName() + "_내재옵션2");
		intrOption2.setExoticType(EExoticOptionType.SingleBarrier);
		intrOption2.setOptionType(EOptionType.PUT);
		intrOption2.setBarrierType(EBarrierType.DownAndOut);


		for (Map.Entry<String, BigDecimal> entry : elsMaster.getElsUnderMap().entrySet()) {
			intrOption.setUnderlyingId(entry.getKey());
			intrOption2.setUnderlyingId(entry.getKey());

			for (Pdif094p0 bb : elsMaster.getElsEventList()) {
				if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getElsEvntCndtTypeCd().equals("02")) {
					if (upBarrier.doubleValue() <= bb.getUnderEventMap().get(entry.getKey()).doubleValue()) {
						upBarrier = bb.getUnderEventMap().get(entry.getKey());
					}
					if (downBarrier.doubleValue() >= bb.getUnderEventMap().get(entry.getKey()).doubleValue()) {
						downBarrier = bb.getUnderEventMap().get(entry.getKey());
					}

					intrOption.setUpBarrier(upBarrier);

					intrOption.setEventEvalType(EElsEventEvaluationType.getEnum(bb.getElsEvntEvluBassDvsnCd()));
					intrOption.setReturnEvalType(EElsReturnEvaluationType.getEnum(bb.getElsEvluMthdTypeCd()));
					intrOption.setObservationType(EElsObserveFrequencyType.getEnum(bb.getElsObsvFrqcTypeCd()));

					intrOption2.setDownBarrier(downBarrier);
					intrOption2.setEventEvalType(EElsEventEvaluationType.getEnum(bb.getElsEvntEvluBassDvsnCd()));
					intrOption2.setReturnEvalType(EElsReturnEvaluationType.getEnum(bb.getElsEvluMthdTypeCd()));
					intrOption2.setObservationType(EElsObserveFrequencyType.getEnum(bb.getElsObsvFrqcTypeCd()));

					if (bb.getRedemEvent().getElsKoutOptDvsnCd() != null
							&& bb.getRedemEvent().getElsKoutOptDvsnCd().equals("01")) {
						intrOption.setConversionRatio(bb.getRedemEvent().getPtciRt());
					} else if (bb.getRedemEvent().getElsKoutOptDvsnCd() != null
							&& bb.getRedemEvent().getElsKoutOptDvsnCd().equals("02")) {
						intrOption2.setConversionRatio(bb.getRedemEvent().getPtciRt());
					}
				} else if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getElsEvntCndtTypeCd().equals("04")) {
					if (upStrike.doubleValue() <= bb.getUnderEventMap().get(entry.getKey()).doubleValue()) {
						upStrike = bb.getUnderEventMap().get(entry.getKey());
					}
					if (downStrike.doubleValue() >= bb.getUnderEventMap().get(entry.getKey()).doubleValue()) {
						downStrike = bb.getUnderEventMap().get(entry.getKey());
					}

					intrOption.setStrikePrice(upStrike);
					// intrOption.setConversionRatio(bb.getRedemEvent().getPtciRt());
					intrOption.setBasePrice(entry.getValue());

					intrOption2.setStrikePrice(downStrike);
					// intrOption2.setConversionRatio(bb.getRedemEvent().getPtciRt());
					intrOption2.setBasePrice(entry.getValue());
				}
			}
			optionList.add(intrOption);
			optionList.add(intrOption2);
		}
		return optionList;
	}

	private OptionCrud getPlainVanilla(Pdif004m0 elsMaster, boolean isIntrinsic) {
		OptionCrud option = getOptionDefault(elsMaster, isIntrinsic);

		for (Map.Entry<String, BigDecimal> entry : elsMaster.getElsUnderMap().entrySet()) {
			option.setUnderlyingId(entry.getKey());

			for (Pdif094p0 bb : elsMaster.getElsEventList()) {
				if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getElsEvntCndtTypeCd().equals("04")
						&& bb.getId().getElsEvntSeq() == 0) {

					option.setBasePrice(entry.getValue());
					option.setStrikePrice(bb.getUnderEventMap().get(entry.getKey()));
					option.setConversionRatio(bb.getRedemEvent().getPtciRt());

					option.setEventEvalType(EElsEventEvaluationType.getEnum(bb.getElsEvntEvluBassDvsnCd()));
					option.setReturnEvalType(EElsReturnEvaluationType.getEnum(bb.getElsEvluMthdTypeCd()));
					option.setObservationType(EElsObserveFrequencyType.getEnum(bb.getElsObsvFrqcTypeCd()));
				}
			}
		}
		return option;
	}

	private OptionCrud getKnockOut(Pdif004m0 elsMaster, boolean isIntrinsic) {
		OptionCrud option = getOptionDefault(elsMaster, isIntrinsic);

		option.setExoticType(EExoticOptionType.SingleBarrier);
		option.setBarrierType(EBarrierType.getEnum(elsMaster.getElsOptDvsnCd()));

		for (Map.Entry<String, BigDecimal> entry : elsMaster.getElsUnderMap().entrySet()) {
			option.setUnderlyingId(entry.getKey());
			for (Pdif094p0 bb : elsMaster.getElsEventList()) {
				if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getElsEvntCndtTypeCd().equals("02")) {
					option.setUpBarrier(bb.getUnderEventMap().get(entry.getKey()));

					option.setEventEvalType(EElsEventEvaluationType.getEnum(bb.getElsEvntEvluBassDvsnCd()));
					option.setReturnEvalType(EElsReturnEvaluationType.getEnum(bb.getElsEvluMthdTypeCd()));
					option.setObservationType(EElsObserveFrequencyType.getEnum(bb.getElsObsvFrqcTypeCd()));

				} else if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getElsEvntCndtTypeCd().equals("04")) {
					option.setStrikePrice(bb.getUnderEventMap().get(entry.getKey()));
					option.setConversionRatio(bb.getRedemEvent().getPtciRt());
					option.setBasePrice(entry.getValue());
				}
			}
		}
		return option;
	}

	private OptionCrud getDigital(Pdif004m0 elsMaster, boolean isIntrinsic) {
		OptionCrud option = getOptionDefault(elsMaster, isIntrinsic);

		option.setExoticType(EExoticOptionType.Digital);
		option.setDigitalType(EDigitalType.CashOrNothing);
		option.setDigitalPayoff(elsMaster.getHgstErngRt().subtract(elsMaster.getLwstErngRt())
									.multiply(new BigDecimal(100)));

		for (Map.Entry<String, BigDecimal> entry : elsMaster.getElsUnderMap().entrySet()) {
			option.setUnderlyingId(entry.getKey());

			for (Pdif094p0 bb : elsMaster.getElsEventList()) {
				if (bb.getId().getElsEvntDvsnCd().equals("01") && bb.getId().getElsEvntSeq() == 0) {
					option.setOptionType(EElsRchgType.getEnum(bb.getElsRchgTypeCd()).getDigitalCallType());

					option.setBasePrice(entry.getValue());
					option.setStrikePrice(bb.getUnderEventMap().get(entry.getKey()));
					option.setConversionRatio(bb.getRedemEvent().getPtciRt());

					option.setEventEvalType(EElsEventEvaluationType.getEnum(bb.getElsEvntEvluBassDvsnCd()));
					option.setReturnEvalType(EElsReturnEvaluationType.getEnum(bb.getElsEvluMthdTypeCd()));
					option.setObservationType(EElsObserveFrequencyType.getEnum(bb.getElsObsvFrqcTypeCd()));
				}
			}
		}

		return option;
	}

	private OptionCrud getOptionDefault(Pdif004m0 elsMaster, boolean isIntrinsic) {
		OptionCrud baseOption = new OptionCrud();
		if(isIntrinsic){
			baseOption.setOptionId(elsMaster.getPdno()+"_O");
			baseOption.setOptionName(elsMaster.getProd().getPrdtName()+"_내재옵션");
		}
		else{
			baseOption.setOptionId(elsMaster.getPdno());
			baseOption.setOptionName(elsMaster.getProd().getPrdtName());
		}

		baseOption.setOptionType(EOptionType.getEnum(elsMaster.getElsOptDvsnCd()));
		baseOption.setExoticType(EExoticOptionType.PlainVanilla);

		baseOption.setIssueDate(elsMaster.getIssuDt());
		baseOption.setMaturityDate(elsMaster.getExpdDt());

		baseOption.setMultiplier(new Long(100));
		baseOption.setBasketYn(EBoolean.N);
		baseOption.setUserName(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).name());

		return baseOption;
	}

	private void setIntrinsicBond(Ivot015p0 elsAsset, Pdif004m0 elsMaster) {
		Synthetics synTemp = new Synthetics(elsMaster.getPdno(), elsMaster.getProd().getPrdtName(), "ELS");
		synTemp.setSourceTable(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).name());

		SyntheticDetail temp1  = new SyntheticDetail(elsMaster.getPdno(), elsMaster.getPdno()+"_B");
		SyntheticDetail temp2  = new SyntheticDetail(elsMaster.getPdno(), elsMaster.getPdno()+"_O");

		temp1.setWeight(new BigDecimal(1));
		temp2.setWeight(new BigDecimal(1));
		
		
		IntrinsicBond bond = new IntrinsicBond();
		bond.setBondId(elsMaster.getPdno() + "_B");
		bond.setBondName(elsMaster.getProd().getPrdtName() + "_내재채권");
		bond.setIssuerId("한투");
		bond.setIssueDate(elsMaster.getIssuDt());
		bond.setMaturityDate(elsMaster.getExpdDt());
		
		if (elsAsset.getBondAdmsRt().doubleValue() == 0) {
			if (elsMaster.getLwstErngRt().doubleValue() == 0) {
				bond.setFaceAmt(new BigDecimal(10000));
				temp1.setSourceTable("NoGuarantee");
			} else {
				bond.setFaceAmt(elsMaster.getLwstErngRt().multiply(new BigDecimal(10000)));
				temp1.setSourceTable("Guarantee");
			}
		} else {
			bond.setFaceAmt(elsAsset.getUtlzFundPrcaAsrcRt().multiply(new BigDecimal(100)));
			temp1.setSourceTable("Provided");
			temp1.setFirstUnitPrice(elsAsset.getBondAdmsRt().multiply(new BigDecimal(100)));
		}
		
		bond.setFundedYn(EBoolean.Y);
		bond.setBondCfType(EBondCfType.DISCOUNT);

		intrBondList.add(bond);
		

		List<SyntheticDetail> tempList = new ArrayList<SyntheticDetail>();
		tempList.add(temp1);
		tempList.add(temp2);
		
		synTemp.setSubProdList(tempList);
		synList.add(synTemp);
	}

	private void setSwapProduct(Pdif004m0 elsMaster, boolean isIntrincs){
		Synthetics synTemp = new Synthetics(elsMaster.getPdno()+"_S", elsMaster.getProd().getPrdtName(), "ELS");
		synTemp.setSourceTable(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).name());
		
		SyntheticDetail temp1  = new SyntheticDetail(elsMaster.getPdno()+"_S", elsMaster.getPdno()+"_R");
		SyntheticDetail temp2;
		if(isIntrincs){
			temp2  = new SyntheticDetail(elsMaster.getPdno()+"_S", elsMaster.getPdno()+"_O");
		}
		else{
			temp2  = new SyntheticDetail(elsMaster.getPdno()+"_S", elsMaster.getPdno());
		}

		temp1.setWeight(new BigDecimal(-1));
		temp2.setWeight(new BigDecimal(1));

		List<SyntheticDetail> tempList = new ArrayList<SyntheticDetail>();
		tempList.add(temp1);
		tempList.add(temp2);
		
		synTemp.setSubProdList(tempList);
		synList.add(synTemp);
	}
	
	private boolean checkDup(Pdif004m0 elsMaster){
		if (idList.contains(elsMaster.getPdno())) {
			return true;
		} else {
			idList.add(elsMaster.getPdno());
			return false;
		}		
	}
	private String getAssetProductId(Ivot015p0 elsAsset){
		String tempProdId = elsAsset.getId().getPdno() ;
		switch (elsAsset.getElsTrDtldTypeCd()) {
			case EX_OTC:
				break;
			case EX_BOND_OTC:
				tempProdId = tempProdId + "_O";
				break;
			case EX_ELS:
				break;
			case SWAP1:
				tempProdId = tempProdId + "_S";
				break;
			case SWAP2:
				tempProdId = tempProdId + "_S";
				break;
			default:
				break;
			}
		return tempProdId;
//		return tempProdId + "_L_" + elsAsset.getUtlzFundCd() 
//				+"_"+ elsAsset.getId().getTrOppnNo()+ "_"+elsAsset.getId().getElsTrDtldTypeCd();
	}	
	private String getAssetPositionId(Ivot015p0 elsAsset){
		return getAssetProductId(elsAsset) + "_L_" + elsAsset.getUtlzFundCd() 
				+"_"+ elsAsset.getId().getTrOppnNo()+ "_"+elsAsset.getId().getElsTrDtldTypeCd();
	}

	private PositionCrud getDebtPosition(Ivot015p0 elsAsset, Pdif004m0 elsMaster){
		PositionCrud debtPos = new PositionCrud();
		
		debtPos.setPosId(elsMaster.getPdno() + "_S_" + elsAsset.getUtlzFundCd() 
				+"_"+ elsAsset.getId().getTrOppnNo()+ "_"+elsAsset.getId().getElsTrDtldTypeCd());
		
		debtPos.setPosName(elsMaster.getProd().getPrdtName());
		debtPos.setPosSide("SHORT");
		debtPos.setProdId(elsMaster.getPdno());
		debtPos.setFundId(elsAsset.getUtlzFundCd());
		debtPos.setCpartyId(elsAsset.getId().getTrOppnNo());
		debtPos.setCurrency("KRW");
		debtPos.setSettleCurrency(elsMaster.getCrcyCd());
		debtPos.setTxType(elsAsset.getElsTrDtldTypeCd().name());
		debtPos.setElsOptType(EElsOptionType.getEnum(elsMaster.getElsOptDvsnCd()).name());

		debtPos.setInitTxDate(elsMaster.getIssuDt());
		debtPos.setInitTxPrice(elsMaster.getElsIssuPric().setScale(4).divide(elsMaster.getElsPapr(),RoundingMode.HALF_UP));

		debtPos.setInitTxAmt(new BigDecimal(elsAsset.getDebtTrPymtAmt()));

		debtPos.setInitTxPar(new BigDecimal(elsAsset.getDebtTrParAmt()));
		
		debtPos.setInitHoldingQty(new BigDecimal(elsAsset.getDebtTrPymtAmt()).setScale(8).divide(new BigDecimal(10000),RoundingMode.HALF_UP));

		return debtPos;
		
	}
}
