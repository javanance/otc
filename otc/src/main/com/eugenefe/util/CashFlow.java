package com.eugenefe.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** CashFlow 를 위한 Entity Class 
*
*/
public class CashFlow implements Serializable, Comparable<CashFlow>{
	
//	private final static Logger logger =LoggerFactory.getLogger(CashFlow.class);	
	private FnCalendar baseDate;
	private String fnProdId;
	private String cfType;
	private FnCalendar cfStartDate;
	private FnCalendar cfEndDate;
	private FnCalendar cfDate;
	private double prinAmt;
	private double intAmt;
	private double cfAmt;
	
//	private Date lastUpdate;
	private Calendar lastUpdate;
	
//평잔 또는 기초잔액과 같이 현금흐름을 산출하는되 기준이 된 금액, 검증용으로 사용한다. ORM 매핑에서는 제외함.DB 칼럼도 없음
	private double refAmt;              
	
	public CashFlow() {
		
	}
	public CashFlow(FnCalendar baseDate, String fnProdId){
		this.baseDate = baseDate;
		this.fnProdId = fnProdId;
		 
	}
	public CashFlow(FnCalendar baseDate, String fnProdId, FnCalendar cfDate,double cfAmt){
		this.baseDate = baseDate;
		this.fnProdId = fnProdId;
		this.cfDate =cfDate; 
		this.cfAmt =cfAmt;
		
		 
	}
	public FnCalendar getBaseDate() {
		return baseDate;
	}
	public void setBaseDate(FnCalendar baseDate) {
		this.baseDate = baseDate;
	}
	public String getFnProdId() {
		return fnProdId;
	}
	public void setFnProdId(String fnProdId) {
		this.fnProdId = fnProdId;
	}
	public String getCfType() {
		return cfType;
	}
	public void setCfType(String cfType) {
		this.cfType = cfType;
	}
	public FnCalendar getCfStartDate() {
		return cfStartDate;
	}
	public void setCfStartDate(FnCalendar cfStartDate) {
		this.cfStartDate = cfStartDate;
	}
	public FnCalendar getCfEndDate() {
		return cfEndDate;
	}
	public void setCfEndDate(FnCalendar cfEndDate) {
		this.cfEndDate = cfEndDate;
	}
	public FnCalendar getCfDate() {
		return cfDate;
	}
	public void setCfDate(FnCalendar cfDate) {
		this.cfDate = cfDate;
	}
	public double getPrinAmt() {
		return prinAmt;
	}
	public void setPrinAmt(double prinAmt) {
		this.prinAmt = prinAmt;
	}
	public double getIntAmt() {
		return intAmt;
	}
	public void setIntAmt(double intAmt) {
		this.intAmt = intAmt;
	}
	
	public double getCfAmt() {
		return cfAmt;
	}
	public void setCfAmt(double cfAmt) {
		this.cfAmt = cfAmt;
	}
	public double getRefAmt() {
		return refAmt;
	}
	public void setRefAmt(double refAmt) {
		this.refAmt = refAmt;
	}
	
//	public Date getLastUpdate() {
//		return lastUpdate;
//	}
//	public void setLastUpdate(Date lastUpdate) {
//		this.lastUpdate = lastUpdate;
//	}
	/**
	 * @return the lastUpdate
	 */
	public Calendar getLastUpdate() {
		return lastUpdate;
	}
	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Calendar lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	@Override
	public int compareTo(CashFlow cashFlow){
		return this.cfStartDate.compareTo(cashFlow.getCfStartDate());
	}
	
	@Override
	public boolean equals(Object other){
		return super.equals(other); 
	}
	@Override
	public int hashCode(){
		return super.hashCode();
	}
	
	
	
//	***************Business Method***************
	public static List<CashFlow> multiple(List<CashFlow> these, double multiplier){
		List<CashFlow> rst = new ArrayList<CashFlow>();
		CashFlow cf;
		for(CashFlow aa: these){
			cf = new CashFlow(aa.getBaseDate(),aa.getFnProdId(),aa.getCfDate(),multiplier*aa.getCfAmt());
			
			cf.setCfStartDate(aa.getCfStartDate());
			cf.setCfEndDate(aa.getCfEndDate());
			cf.setCfType(aa.getCfType());
			cf.setIntAmt(multiplier*aa.getIntAmt());
			cf.setPrinAmt(multiplier*aa.getPrinAmt());
			rst.add(cf);
		}
		return rst;
	}
	
	public static List<CashFlow> add(List<CashFlow> these, List<CashFlow> others){
		List<CashFlow> rst = new ArrayList<CashFlow>();
		CashFlow cf, temp;
		
		rst.addAll(these);
		Map<FnCalendar, CashFlow> cfMap =new LinkedHashMap<FnCalendar, CashFlow>();
		
		for(CashFlow aa: these){
			cfMap.put(aa.getCfDate(), aa);
		}
		for(CashFlow bb: others){
			if(cfMap.containsKey(bb.getCfDate())){
				temp =cfMap.get(bb.getCfDate());
				cf = new CashFlow(bb.getBaseDate(), bb.getFnProdId(), bb.getCfDate(),bb.getCfAmt()+temp.getCfAmt());
								
				cf.setCfAmt(temp.getCfAmt() + bb.getCfAmt());
				cf.setPrinAmt(temp.getPrinAmt() + bb.getPrinAmt());
				cf.setIntAmt(temp.getIntAmt() + bb.getIntAmt());
				cfMap.put(bb.getBaseDate(), cf);
			}
			else cfMap.put(bb.getBaseDate(), bb);
		}
		return (List<CashFlow>)cfMap.values();
	}
	
//************************Static Method ********************
//	public static List<CashFlow> getCashFlows(FnCalendar baseDate, String fnProdId){
//		List<CashFlow> result = new ArrayList<CashFlow>();
//		Session s = HibernateUtil.currentSession();
//		Query qr;
//		
//		qr = s.createQuery("  from RstCashFlow a " +
//				   		   " where a.fnProdId = :param " +
//				   		   " and   a.baseDate <= :param2 " +
//				   		   " order by b.baseDate "
//							);
//		qr.setParameter("param", fnProdId);
//		qr.setParameter("param2", baseDate);
//		result = qr.list();
//		HibernateUtil.closeSession();
//		return result;
//	}
	
	

}
