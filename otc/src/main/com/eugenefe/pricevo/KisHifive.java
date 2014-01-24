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
	private double[] S0;			// IN:  �����ڻ� ���簡 
	private double[] t;				// IN:  �Ⱓ ���� ����	
	private double[] rf; 			// IN:  ������������ �Ⱓ����
	private double[] rd; 			// IN:  ���������� �Ⱓ����
	private int nt;					// IN:  �Ⱓ ���� ������ ����
	private double[] t_vol;			// IN:  ������ ���� ����
	private double[] vol;			// IN:  �����ڻ� ������
	
//	private double[] vol1;
//	private double[] vol2;
	
	private int n_vol;				// IN:  �� ������ ������ ����
	private double[] rho;			// IN:  �����ڻ� ������
	private double[] div;			// IN:  �����ڻ� ����� 
	private int nStock;				// IN:  �����ڻ� ����
	private double[] X1;			// IN:  ��簡��1
	private double[] X2;			// IN:  ��簡��2
	private double[] X3;			// IN:  ��簡��3
	private double[] upBarrier;		// IN:  Knock-out Barrier
	private double downBarrier;		// IN:  Knock-in Barrier
	private double[] amt1;			// IN:  ��簡��1�� �ش�Ǵ� �ߵ���ȯ �ݾ�
	private double[] amt2;			// IN:  ��簡��2�� �ش�Ǵ� �ߵ���ȯ �ݾ�
	private double[] amt3;			// IN:  ��簡��3�� �ش�Ǵ� �ߵ���ȯ �ݾ�
	private double[] coupon;		// IN:  �߰� ���� ���� 
	private int[] dates; 			// IN:  �߰� ���ϱ����� �ϼ� 
	private int[] pay_dates; 		// IN:  �߰� �����ϱ����� �ϼ� 
	private int nStrike; 			// IN:  �߰����� �� 
	private int upBarrierFlag;		// IN:  upBarrier üũ ���(0:����; 1:����)
	private int downBarrierFlag;	// IN:  downBarrier ����(0:����; 1:����)
	private int[] bUpHitted;  		// IN:  Knock-out Barrier Hitting ����
	private int bDownHitted;  		// IN:  Knock-in Barrier Hitting ����
	private int nTrials;			// IN:  ����ȸ�� 
	private double max_loss;		// IN:  �ִ�ս�
	private double[] p;				// OUT: �ߵ���ȯ Ȯ��

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
		upBarrierFlag = 0;		    // IN:  upBarrier üũ ���(0:����, 1:����)
		downBarrierFlag = 1;		// IN:  downBarrier ����(0:����, 1:����)
		bDownHitted =0;  		// IN:  Knock-in Barrier Hitting ����
		nTrials =1000;			// IN:  ����ȸ�� 
		max_loss= 1;		// IN:  �ִ�ս�
		
	}

}
