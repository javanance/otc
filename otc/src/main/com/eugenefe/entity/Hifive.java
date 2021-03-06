package com.eugenefe.entity;

// Generated Nov 25, 2013 6:47:59 PM by Hibernate Tools 4.0.0

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.jboss.seam.annotations.Transactional;

import com.eugenefe.enums.EMaturity;
import com.eugenefe.enums.raw.EElsOptionType;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.EColumnType;
import com.eugenefe.util.AnnoNavigationFilter;
import com.eugenefe.util.FnCalendar;

/**
 * Hifive generated by hbm2java
 */
@Entity
//@Table(name = "HIFIVE")
@Table(name = "HIFIVE", schema="TAKION99")
@AnnoNavigationFilter
public class Hifive implements java.io.Serializable, Cloneable{

	private String prodId;
	private Counterparty counterparty;
	private String cpartyId;
	private String prodName;
	private EElsOptionType hifiveType;
	private String issueDate;
	private String maturityDate;
	private Long faceAmt;
	private Long notionalAmt;
	private BigDecimal downBarrier;
	private BigDecimal maxLoss;
	private BigDecimal couponRateLast;
	private String hittingType;
	private String redemptionType; 
	private boolean virtual;
	private String prodDesc;
	private String sourceTable;
	private List<HifiveStrike> hifiveStrikes = new ArrayList<HifiveStrike>();
	private List<HifiveUnderlying> hifiveUnderlyings = new ArrayList<HifiveUnderlying>();
	private List<HifiveCondition> hifiveConditionList = new ArrayList<HifiveCondition>();
	private List<HifiveMonthCoupon> hifiveCouponList = new ArrayList<HifiveMonthCoupon>();
//	private HifiveSwapIr hifiveSwapIr;

//	private List<Pricing> pricings = new ArrayList<Pricing>();
	private List<PricingMaster> priceSetting = new ArrayList<PricingMaster>();
	private List<PricingUnderlyings> prUnderSetting = new ArrayList<PricingUnderlyings>();
	
	private List<ProductReturn> prodReturn = new ArrayList<ProductReturn>();
	private List<ProductGreeks> prodGreeks = new ArrayList<ProductGreeks>();

	public Hifive() {
	}

	public Hifive(String prodId, Counterparty counterparty) {
		this.prodId = prodId;
		this.counterparty = counterparty;
	}

	@Id
	@Column(name = "PROD_ID", unique = true, nullable = false, length = 20)
	@AnnoMethodTree(order =10, init=true)
	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	@Column(name = "PROD_NAME", length = 50)
	@AnnoMethodTree(order =11, init=true)
	public String getProdName() {
		return this.prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTERPARTY_ID", nullable = true)
	@AnnoMethodTree(order =20, init=true)
	public Counterparty getCounterparty() {
		return this.counterparty;
	}

	public void setCounterparty(Counterparty counterparty) {
		this.counterparty = counterparty;
	}
	
	@Transient
	public String getCpartyId() {
		if(cpartyId==null  && getCounterparty().getCounterpartyId() !=null){
			cpartyId = getCounterparty().getCounterpartyId();
		}
		return cpartyId;
	}

	public void setCpartyId(String cpartyId) {
		this.cpartyId = cpartyId;
	}

	

	@Column(name = "HIFIVE_TYPE", length = 20)
	@AnnoMethodTree(order =30, init=true)
	@Enumerated(EnumType.STRING)
	public EElsOptionType getHifiveType() {
		return this.hifiveType;
	}

	public void setHifiveType(EElsOptionType hifiveType) {
		this.hifiveType = hifiveType;
	}

	@Column(name = "ISSUE_DATE", length = 8)
	@AnnoMethodTree(order =40, init=true)
	public String getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	@Column(name = "MATURITY_DATE", length = 8)
	@AnnoMethodTree(order =41, init=true)
	public String getMaturityDate() {
		return this.maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	@Column(name = "FACE_AMT", precision = 10, scale = 0)
	@AnnoMethodTree(order =50, init=true)
	public Long getFaceAmt() {
		return this.faceAmt;
	}

	public void setFaceAmt(Long faceAmt) {
		this.faceAmt = faceAmt;
	}

	@Column(name = "NOTIONAL_AMT", precision = 10, scale = 0)
	@AnnoMethodTree(order =51, init=true)
	public Long getNotionalAmt() {
		return this.notionalAmt;
	}

	public void setNotionalAmt(Long notionalAmt) {
		this.notionalAmt = notionalAmt;
	}

	@Column(name = "DOWN_BARRIER", precision = 10, scale = 4)
	@AnnoMethodTree(order =60, init=true)
	public BigDecimal getDownBarrier() {
		return this.downBarrier;
	}

	public void setDownBarrier(BigDecimal downBarrier) {
		this.downBarrier = downBarrier;
	}

	@Column(name = "MAX_LOSS", precision = 10, scale = 4)
	@AnnoMethodTree(order =61, init=true)
	public BigDecimal getMaxLoss() {
		return this.maxLoss;
	}

	public void setMaxLoss(BigDecimal maxLoss) {
		this.maxLoss = maxLoss;
	}

	@Column(name = "COUPON_RATE_LAST", precision = 10, scale = 4)
	@AnnoMethodTree(order =62, init=true)
	public BigDecimal getCouponRateLast() {
		return this.couponRateLast;
	}

	public void setCouponRateLast(BigDecimal couponRateLast) {
		this.couponRateLast = couponRateLast;
	}

	@Column(name = "HITTING_TYPE", length = 10)
	@AnnoMethodTree(order =64, init=true)
	public String getHittingType() {
		return this.hittingType;
	}

	public void setHittingType(String hittingType) {
		this.hittingType = hittingType;
	}
	
	@Column(name = "VIRTUAL_YN")
	@Type(type="yes_no")
	public boolean isVirtual() {
		return virtual;
	}
	public void setVirtual(boolean isVirtual) {
		this.virtual = isVirtual;
	}
	
	@Column(name = "REDEMPTION_TYPE")
	public String getRedemptionType() {
		return redemptionType;
	}

	public void setRedemptionType(String redemptionType) {
		this.redemptionType = redemptionType;
	}

	@Column(name = "PROD_DESC")
	public String getProdDesc() {
		return prodDesc;
	}

	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hifive")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE, CascadeType.ALL})
	@AnnoMethodTree(order =70, init=false, type=EColumnType.List)
	public List<HifiveUnderlying> getHifiveUnderlyings() {
		return this.hifiveUnderlyings;
	}
	public void setHifiveUnderlyings(List<HifiveUnderlying> hifiveUnderlyings) {
		this.hifiveUnderlyings = hifiveUnderlyings;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hifive")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE, CascadeType.ALL})
	@AnnoMethodTree(order =71, init=false, type=EColumnType.List)
	public List<HifiveStrike> getHifiveStrikes() {
		return this.hifiveStrikes;
	}
	public void setHifiveStrikes(List<HifiveStrike> hifiveStrikes) {
		this.hifiveStrikes = hifiveStrikes;
	}

	
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "hifive")
//	@AnnoMethodTree(order =72, init=false, type=EColumnType.List)
//	public HifiveSwapIr getHifiveSwapIr() {
//		return this.hifiveSwapIr;
//	}
//
//	public void setHifiveSwapIr(HifiveSwapIr hifiveSwapIr) {
//		this.hifiveSwapIr = hifiveSwapIr;
//	}
//	@Transient
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hifive")
//	@AnnoMethodTree(order =75, init=true, type=EColumnType.List)
//	public List<Pricing> getPricings() {
//		return this.pricings;
//	}
//	public void setPricings(List<Pricing> pricings) {
//		this.pricings = pricings;
//	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hifive")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE, CascadeType.ALL})
	@AnnoMethodTree(order =72, init=false, type=EColumnType.List)
	public List<HifiveCondition> getHifiveConditionList() {
		return hifiveConditionList;
	}

	public void setHifiveConditionList(List<HifiveCondition> hifiveConditionList) {
		this.hifiveConditionList = hifiveConditionList;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hifive")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE, CascadeType.ALL})
	@AnnoMethodTree(order =73, init=false, type=EColumnType.List)
	public List<HifiveMonthCoupon> getHifiveCouponList() {
		return hifiveCouponList;
	}

	public void setHifiveCouponList(List<HifiveMonthCoupon> hifiveCouponList) {
		this.hifiveCouponList = hifiveCouponList;
	}

	@OneToMany( mappedBy = "hifive" , fetch=FetchType.LAZY)
//	@Fetch(FetchMode.JOIN)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@AnnoMethodTree(order =75, init=false, type=EColumnType.List)
	public List<PricingMaster> getPriceSetting() {
		return priceSetting;
	}

	public void setPriceSetting(List<PricingMaster> priceSetting) {
		this.priceSetting = priceSetting;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hifive")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@AnnoMethodTree(order =76, init=false, type=EColumnType.List)
	public List<PricingUnderlyings> getPrUnderSetting() {
		return prUnderSetting;
	}
	public void setPrUnderSetting(List<PricingUnderlyings> prUnderSetting) {
		this.prUnderSetting = prUnderSetting;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hifive")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@AnnoMethodTree(order =80, init=false, type=EColumnType.List)
	public List<ProductReturn> getProdReturn() {
		return prodReturn;
	}

	public void setProdReturn(List<ProductReturn> prodReturn) {
		this.prodReturn = prodReturn;
	}

	@Transient
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hifive")
//	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
//	@AnnoMethodTree(order =81, init=false, type=EColumnType.List)
	public List<ProductGreeks> getProdGreeks() {
		return prodGreeks;
	}

	public void setProdGreeks(List<ProductGreeks> prodGreeks) {
		this.prodGreeks = prodGreeks;
	}

	@Transient
	@Override
	public Object clone(){
		try{
			return super.clone();
		}catch(Exception e){
			
		}
		return null;
	}
	
	
	
	@Transient
	public Set<MarketVariableJoin> getMvList(){
		Set<MarketVariableJoin> temp = new HashSet<MarketVariableJoin>();
		for(HifiveUnderlying aa : this.getHifiveUnderlyings()){
			temp.add(aa.getUnderJoin());
		}
		for(PricingMaster bb : this.getPriceSetting()){
			
			temp.addAll(bb.getDiscountIrc().getIrcBucketMap().values());
			for(PricingUnderlyings cc : bb.getPrUnderlyingList()){
				if(cc.getRefIrc() != null){
					temp.addAll(cc.getRefIrc().getIrcBucketMap().values());
				}
//				TODO : content of vol Curve like irCuve 
//				temp.addAll(cc.getVolCurve().getMap.values());
			}
		}
		return temp;
	}	

	private FnCalendar maturityCalenar;
	
	@Transient
	public FnCalendar getMaturityCalenar() {
		if(maturityCalenar==null){
			int year = Integer.parseInt(this.getMaturityDate().substring(0, 4));
			int month = Integer.parseInt(this.getMaturityDate().substring(4,6));
			int day = Integer.parseInt(this.getMaturityDate().substring(6, 8));
			maturityCalenar =new FnCalendar(year, month-1, day);
		}
		return maturityCalenar;
	}

	public void setMaturityCalenar(FnCalendar maturityCalenar) {
		this.maturityCalenar = maturityCalenar;
	}

	@Transient
	public int getResidualMonth(String bssd){
		int year = Integer.parseInt(bssd.substring(0, 4));
		int month = Integer.parseInt(bssd.substring(4,6));
		int day = Integer.parseInt(bssd.substring(6,8));
		FnCalendar tempCal =new FnCalendar(year, month-1, day); 
		return getMaturityCalenar().differTerms(tempCal, EMaturity.M01);
	}
	@Transient
	public EMaturity getResidualEMaturity(List<EMaturity> list, String bssd){
		int resMon = getResidualMonth(bssd);
		return EMaturity.getEMaurityByMonth(list, resMon);
	}
	
	@Transient
	public EMaturity getResidualEMaturity( String bssd){
		int resMon = getResidualMonth(bssd);
		if(resMon <=1) {
			return EMaturity.M01;
		}else if(resMon<=3) {
			return EMaturity.M03;
		}else if(resMon <=6){
			return EMaturity.M06;
		}else if(resMon <=9){
			return EMaturity.M09;
		}else if(resMon <=12){
			return EMaturity.Y01;
		}else if(resMon <=24){
			return EMaturity.Y02;
		}else if(resMon <=36){ 
			return EMaturity.Y03;
		}else{
			return EMaturity.Y05;
		}
	}
	
	@Transient
	public int getResidualMonth(Date bssd){
		FnCalendar tempCal = FnCalendar.getInstance(bssd); 
		return getMaturityCalenar().differTerms(tempCal, EMaturity.M01);
	}
	@Column(name = "SOURCE_TABLE")
	public String getSourceTable() {
		return sourceTable;
	}

	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}
	
	
}
