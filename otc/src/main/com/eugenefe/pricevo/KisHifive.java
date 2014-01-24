package com.eugenefe.pricevo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.eugenefe.entity.Hifive;
import com.eugenefe.entity.HifiveStrike;
import com.eugenefe.entity.HifiveUnderlying;
import com.eugenefe.entity.IntRate;
import com.eugenefe.enums.EMaturity;
import com.eugenefe.pricer.hifive.IHiFiveMc;
import com.eugenefe.util.FnCalendar;

public class KisHifive {
	private double[] S0;			// IN:  기초자산 현재가 
	private double[] t;				// IN:  기간 구조 시점	
	private double[] rf; 			// IN:  무위험이자율 기간구조
	private double[] rd; 			// IN:  할인이자율 기간구조
	private int nt;					// IN:  기간 구조 데이터 개수
	private double[] t_vol;			// IN:  변동성 만기 시점
	private double[] vol;			// IN:  기초자산 변동성
	
//	private double[] vol1;
//	private double[] vol2;
	
	private int n_vol;				// IN:  각 변동성 데이터 개수
	private double[] rho;			// IN:  기초자산 상관계수
	private double[] div;			// IN:  기초자산 배당율 
	private int nStock;				// IN:  기초자산 개수
	private double[] X1;			// IN:  행사가격1
	private double[] X2;			// IN:  행사가격2
	private double[] X3;			// IN:  행사가격3
	private double[] upBarrier;		// IN:  Knock-out Barrier
	private double downBarrier;		// IN:  Knock-in Barrier
	private double[] amt1;			// IN:  행사가격1에 해당되는 중도상환 금액
	private double[] amt2;			// IN:  행사가격2에 해당되는 중도상환 금액
	private double[] amt3;			// IN:  행사가격3에 해당되는 중도상환 금액
	private double[] coupon;		// IN:  추가 지급 쿠폰 
	private int[] dates; 			// IN:  중간 평가일까지의 일수 
	private int[] pay_dates; 		// IN:  중간 지급일까지의 일수 
	private int nStrike; 			// IN:  중간평가일 수 
	private int upBarrierFlag;		// IN:  upBarrier 체크 방법(0:동시; 1:각각)
	private int downBarrierFlag;	// IN:  downBarrier 유무(0:없음; 1:있음)
	private int[] bUpHitted;  		// IN:  Knock-out Barrier Hitting 여부
	private int bDownHitted;  		// IN:  Knock-in Barrier Hitting 여부
	private int nTrials;			// IN:  시행회수 
	private double max_loss;		// IN:  최대손실
	private double[] p;				// OUT: 중도상환 확률

	private double[] corr;
	

	
//	private Date today;
	private String[] underlyingName;
	
	
	public KisHifive() {
	}

	public void load(Hifive selectHifive, Date today){
		loadHifive(selectHifive, today);
		loadUnderlying(selectHifive, today);
		loadStrike(selectHifive, today);
		loadIrCurve(selectHifive, today);
		loadVolCurve(selectHifive, today);
	}
	
	public Map<String, Double> getDelta(){
		Map<String, Double> rst = new HashMap<String, Double>();
		 
		for( int j=0 ; j<nStock; j++){
			Double delta = IHiFiveMc.INSTANCE.HiFive_MC_Delta(j+1,S0, t, rf, rd, nt, t_vol, vol, n_vol, corr, div, nStock, X1, X2, X3,
					upBarrier, downBarrier, amt1, amt2, amt3, coupon, dates, pay_dates, nStrike, upBarrierFlag, 
					downBarrierFlag, bUpHitted, bDownHitted, nTrials, max_loss);
			
			rst.put(underlyingName[j], delta);
		}
		return rst;
		
	}

	public Map<String, Double> getVega(){
		Map<String, Double> rst = new HashMap<String, Double>();
		for( int j=0 ; j<nStock; j++){
		  Double vega = IHiFiveMc.INSTANCE.HiFive_MC_Vega(1,S0, t, rf, rd, nt, t_vol, vol, n_vol, corr, div, nStock, X1, X2, X3,
				upBarrier, downBarrier, amt1, amt2, amt3, coupon, dates, pay_dates, nStrike, upBarrierFlag, 
				downBarrierFlag, bUpHitted, bDownHitted, nTrials, max_loss);
		  rst.put(underlyingName[j], vega);
		}
		return rst;
	}
	
	public Double getRho(){
		Double rst =IHiFiveMc.INSTANCE.HiFive_MC_Rho(S0, t, rf, rd, nt, t_vol, vol, n_vol, corr, div, nStock, X1, X2, X3,
				upBarrier, downBarrier, amt1, amt2, amt3, coupon, dates, pay_dates, nStrike, upBarrierFlag, 
				downBarrierFlag, bUpHitted, bDownHitted, nTrials, max_loss);
		return rst;
	}
	
//	***************************private method****************************
	private void loadUnderlying(Hifive selectHifive, Date today){
		int index=0;
		nStock  = selectHifive.getHifiveUnderlyings().size();
		underlyingName = new String[nStock];
		
		S0 = new double[nStock]; 
		div = new double[nStock];
		bUpHitted = new int[nStock];  	
		
		for(HifiveUnderlying aa : selectHifive.getHifiveUnderlyings()){
			S0[index]= aa.getBasePrice().doubleValue();
			div[index]=aa.getDividend().doubleValue();
			bUpHitted[index]=aa.getUpHitted().getKisCode();
			index = index +1;
		}	
	}

	private void loadStrike(Hifive selectHifive, Date today){
		nStrike = selectHifive.getHifiveStrikes().size();
		dates= new int[nStrike]; 			     
		pay_dates= new int[nStrike]; 			 
		
		X1= new double[nStrike];
		X2= new double[nStrike];
		X3= new double[nStrike];
		
		upBarrier= new double[nStrike];	
		
		amt1= new double[nStrike];		
		amt2= new double[nStrike];		
		amt3= new double[nStrike];		
		coupon= new double[nStrike+1];	
		
		p = new double[nStrike+2];
		
		int index =0;
		
		FnCalendar fnBase = FnCalendar.getInstance(today);
		
		for(HifiveStrike aa : selectHifive.getHifiveStrikes()){
			dates[index] = aa.getFnStrikeDate().differDays(fnBase);
			pay_dates[index] = aa.getFnPaymentDate().differDays(fnBase);
			
			X1[index] = aa.getStrikePrice().doubleValue();
			X2[index] = aa.getDblJumpStrike().doubleValue();
			X3[index] = aa.getTplJumpStrike().doubleValue();
			
			amt1[index] = aa.getPayoffAmt().doubleValue();
			amt2[index] = aa.getDblJumpPayoff().doubleValue();
			amt3[index] = aa.getTplJumpPayoff().doubleValue();
			
			upBarrier[index] = aa.getUpBarrier().doubleValue();
			coupon[index] =aa.getCouponRate().doubleValue();
			
			index = index +1;
		}
		coupon[index] = selectHifive.getCouponRateLast().doubleValue();
	}
	

	private void loadIrCurve(Hifive selectHifive, Date today){
		
		for(int j=0; j<nStrike; j++){
			p[j] =0;
		}
				
//		int nt = selectHifive.getPriceSetting().get(0).getDiscountIrc().getIrcBucketMap().size();
		int nt = 2;
		
		t = new double[nt];
		rf = new double[nt*nStock];
		
//		double[] rf1= new double[nt];
//		double[] rf2= new double[nt];
		
		rd = new double[nt];

		for(int k=0; k<nt; k++){
			t[k]=k+1;
			rd[k] = 0.02;
		}
		
		for(int k=0; k<nt*nStock; k++){
			rf[k]= 0.02;
		}
		
		/*int index =0;
		for(Map.Entry<EMaturity, IntRate> aa : selectHifive.getPriceSetting().get(0)
												.getDiscountIrc().getIrcBucketMap().entrySet()){
//			t[index]= aa.getKey();
//			rd[index] =aa.getValue().getIntRate().get(currentDate);
			
			for( int i=0; i< nt ; i++){
//				rf[i + index] =aa.getValue().getIntRate().get(currentDate);
			}
		}*/
		
	}
	
	private void loadVolCurve(Hifive selectHifive, Date today){
		int n_vol = 2;
		t_vol = new double[n_vol];
		vol= new double[n_vol*nStock];
		
//		vol1= new double[n_vol];
//		vol2= new double[n_vol];
		
		corr= new double[nStock*nStock];
//		double[][] rho= new double[n_vol][n_vol];
		
		for(int k=0; k<nt; k++){
			t_vol[k]=k+1;
//			vol1[k] = 0.2;
//			vol2[k] = 0.2;
		}
		for(int h=0; h< n_vol*nStock; h++){
			vol[h] = 0.2;
		}
		
		for(int h=0; h < nStock*nStock; h++){
			corr[h] = 0.9;
		}
		corr[0]=1;
		corr[3]=1;	
	}

	
	private void loadHifive(Hifive selectHifive, Date today){
		downBarrier =0.55;		// IN:  Knock-in Barrier
		upBarrierFlag = 0;		    // IN:  upBarrier 체크 방법(0:동시, 1:각각)
		downBarrierFlag = 1;		// IN:  downBarrier 유무(0:없음, 1:있음)
		bDownHitted =0;  		// IN:  Knock-in Barrier Hitting 여부
		nTrials =1000;			// IN:  시행회수 
		max_loss= 1;		// IN:  최대손실
		
	}

}
