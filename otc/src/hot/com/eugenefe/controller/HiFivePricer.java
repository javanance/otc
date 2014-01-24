package com.eugenefe.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.ToListResultTransformer;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.mvel2.optimizers.impl.refl.nodes.ArrayLength;

import com.eugenefe.entity.HiFiveResult;
import com.eugenefe.entity.Hifive;
import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.Stock;
import com.eugenefe.pricer.hifive.IHiFiveFDM;
import com.eugenefe.pricer.hifive.IHiFiveMc;

@Name("hiFivePricer")
@Scope(ScopeType.CONVERSATION)
public class HiFivePricer {
	@Logger
	private Log log;
	
	private Hifive hifive;
	private List<MarketVariable> scenari= new ArrayList<MarketVariable>();
	
	private List<Stock> underlyingStock;
	
	private int nStock ;
	private String[] stockName ;
	private double[] S0 ; 
	private double[] div ;
	private int[] bUpHitted ;  		// IN:  Knock-out Barrier Hitting 여부
	
	private int nStrike ;
	private double[] X1;
	private double[] X2;
	private double[] X3;
	private double[] upBarrier;		// IN:  Knock-out Barrier
	
	private double[] amt1;			// IN:  행사가격1에 해당되는 중도상환 금액
	private double[] amt2;			// IN:  행사가격2에 해당되는 중도상환 금액
	private double[] amt3;			// IN:  행사가격3에 해당되는 중도상환 금액
	private double[] coupon;			// IN:  추가 지급 쿠폰
	
	private int[] dates; 			// IN:  중간 평가일까지의 일수 
	private int[] pay_dates; 			// IN:  중간 지급일까지의 일수 
	private double[] p ;			// OUT: 중도상환 확률
	
			
	private int nt		;		
	private double[] t ;
	private double[] rf;
//	private double[] rf1;
//	private double[] rf2;
	private double[] rd ;

	
	private int n_vol;
	private double[] t_vol ;
	private double[] vol;
	private double[] vol1;
	private double[] vol2;
	private double[] corr;
//	private double[][] rho= new double[n_vol][n_vol];
	
	
	private double downBarrier ;		// IN:  Knock-in Barrier
	private int upBarrierFlag ;		    // IN:  upBarrier 체크 방법(0:동시, 1:각각)
	private int downBarrierFlag ;		// IN:  downBarrier 유무(0:없음, 1:있음)
	
	private int bDownHitted ;  		// IN:  Knock-in Barrier Hitting 여부
	private int nTrials ;			// IN:  시행회수 
	private double max_loss;		// IN:  최대손실
	
	
	
	private void setInfo(){
		nStock =3;
		S0 = new double[nStock]; 
		div = new double[nStock];
		bUpHitted = new int[nStock];  		// IN:  Knock-out Barrier Hitting 여부
		
		nStrike =6;
		X1= new double[nStrike];
		X2= new double[nStrike];
		X3= new double[nStrike];
		upBarrier= new double[nStrike];
		
		amt1= new double[nStrike];
		amt2= new double[nStrike];			// IN:  행사가격2에 해당되는 중도상환 금액
		amt3= new double[nStrike];			// IN:  행사가격3에 해당되는 중도상환 금액
		coupon= new double[nStrike+1];			// IN:  추가 지급 쿠폰
		
		dates= new int[nStrike]; 			// IN:  중간 평가일까지의 일수 
		pay_dates= new int[nStrike]; 			// IN:  중간 지급일까지의 일수 
		p = new double[nStrike+2];		// OUT: 중도상환 확률
		
		
		nt =2;	
		t = new double[nt];
		rf= new double[nt*nStock];
//		rf1= new double[nt];
//		rf2= new double[nt];
		rd = new double[nt];
		
		n_vol =2;
		t_vol = new double[n_vol];
		vol= new double[n_vol*nStock];
		vol1= new double[n_vol];
		vol2= new double[n_vol];
		corr= new double[nStock*nStock];
//		rho= new double[n_vol][n_vol];
		
		
		
		S0[0] = 1;
		S0[1] =1;
		S0[2] =1;
		
		for(int i=0; i<nStock; i++){
			div[i]=0;
			bUpHitted[i]=0;
		}
		
		dates[0] = 175;
		dates[1] = 359;
		dates[2] = 540;
		dates[3] = 724;
		dates[4] = 905;
		dates[5] = 1089;
		
		X1[0] = 0.95;
		X1[1] = 0.95;
		X1[2] = 0.9;
		X1[3] = 0.9;
		X1[4] = 0.85;
		X1[5] = 0.85;
		
		amt1[0] = 1.042;
		amt1[1] = 1.084;
		amt1[2] = 1.126;
		amt1[3] = 1.168;
		amt1[4] = 1.21;
		amt1[5] = 1.25;
		
		
		for(int j=0; j<nStrike; j++){
//			X1[j] = (70 - j*5)/100;
			X2[j] = X1[j];
			X3[j] = X1[j];
			
			upBarrier[j] = 100; 
			
			
//			amt1[j] = (110 + j*5)/100;
			amt2[j] = amt1[j];
			amt3[j] =  amt1[j];
			coupon[j] =0;
			
//			dates[j] = 50 *(j+1);
			pay_dates[j] = dates[j];
			p[j] =0;
		}
		coupon[5] = 0.252;
		coupon[6]=0;
				

		for(int k=0; k<nt; k++){
			t[k]=k+1;
//			rf1[k] = 0.02;
//			rf2[k] = 0.02;
			rd[k] = 0.02;
		}
		
		for(int k=0; k<nt*nStock; k++){
			rf[k]= 0.02;
		}
		
		
		for(int k=0; k<nt; k++){
			t_vol[k]=k+1;
			vol1[k] = 0.2;
			vol2[k] = 0.2;
		}
		for(int h=0; h<n_vol*nStock; h++){
			vol[h] = 0.2;
		}
		for(int h=0; h<nStock*nStock; h++){
			corr[h] = 0.9;
		}
		corr[0]=1;
		corr[4]=1;
		corr[8]=1;
		
		 downBarrier =0.55;		// IN:  Knock-in Barrier

		 upBarrierFlag = 0;		    // IN:  upBarrier 체크 방법(0:동시, 1:각각)
		 downBarrierFlag = 1;		// IN:  downBarrier 유무(0:없음, 1:있음)
		
		 bDownHitted =0;  		// IN:  Knock-in Barrier Hitting 여부
		 nTrials =1000;			// IN:  시행회수 
		 max_loss= 1;		// IN:  최대손실
	}
	
	public HiFivePricer() {
	}
	
	
	public double getPv(){
		setInfo();
		double pv = IHiFiveMc.INSTANCE.HiFive_MC(S0, t, rf, rd, nt, t_vol, vol, n_vol, corr, div, nStock, X1, X2, X3,
			upBarrier, downBarrier, amt1, amt2, amt3, coupon, dates, pay_dates, nStrike, upBarrierFlag, 
			downBarrierFlag, bUpHitted, bDownHitted, nTrials, max_loss, p);
		return pv;
	}
	
	
	public Map<String, Double> getDelta(){
		Map<String, Double> delta = new HashMap<String, Double>();

		for( int j=0 ; j<nStock; j++){
			double MC_delta = IHiFiveMc.INSTANCE.HiFive_MC_Delta(j+1,S0, t, rf, rd, nt, t_vol, vol, n_vol, corr, div, nStock, X1, X2, X3,
				upBarrier, downBarrier, amt1, amt2, amt3, coupon, dates, pay_dates, nStrike, upBarrierFlag, 
				downBarrierFlag, bUpHitted, bDownHitted, nTrials, max_loss);
			delta.put(stockName[j], MC_delta);
		}
		return delta;
	}

	public Map<String, Double> getGamma(){
		Map<String, Double> gamma = new HashMap<String, Double>();

		for( int j=0 ; j<nStock; j++){
			double MC_gamma = IHiFiveMc.INSTANCE.HiFive_MC_Gamma(j+1,S0, t, rf, rd, nt, t_vol, vol, n_vol, corr, div, nStock, X1, X2, X3,
				upBarrier, downBarrier, amt1, amt2, amt3, coupon, dates, pay_dates, nStrike, upBarrierFlag, 
				downBarrierFlag, bUpHitted, bDownHitted, nTrials, max_loss);
			gamma.put(stockName[j], MC_gamma);
		}
		return gamma;
	}
	
	public Map<String, Double> getVega(){
		Map<String, Double> vega = new HashMap<String, Double>();

		for( int j=0 ; j<nStock; j++){
			double MC_vega = IHiFiveMc.INSTANCE.HiFive_MC_Vega(j+1,S0, t, rf, rd, nt, t_vol, vol, n_vol, corr, div, nStock, X1, X2, X3,
				upBarrier, downBarrier, amt1, amt2, amt3, coupon, dates, pay_dates, nStrike, upBarrierFlag, 
				downBarrierFlag, bUpHitted, bDownHitted, nTrials, max_loss);
			vega.put(stockName[j], MC_vega);
		}
		return vega;
	}
	
	public double getRho(){
		double MC_theta = IHiFiveMc.INSTANCE.HiFive_MC_Theta(S0, t, rf, rd, nt, t_vol, vol, n_vol, corr, div, nStock, X1, X2, X3,
				upBarrier, downBarrier, amt1, amt2, amt3, coupon, dates, pay_dates, nStrike, upBarrierFlag, 
				downBarrierFlag, bUpHitted, bDownHitted, nTrials, max_loss);
		return MC_theta;
	}
	
	public double getTheta(){
		double MC_rho = IHiFiveMc.INSTANCE.HiFive_MC_Rho(S0, t, rf, rd, nt, t_vol, vol, n_vol, corr, div, nStock, X1, X2, X3,
				upBarrier, downBarrier, amt1, amt2, amt3, coupon, dates, pay_dates, nStrike, upBarrierFlag, 
				downBarrierFlag, bUpHitted, bDownHitted, nTrials, max_loss);
		return MC_rho;
	}
	
	public HiFivePricer(Hifive hifive, List<MarketVariable> scenari) {
		this.hifive = hifive;
		this.scenari = scenari;
	}


	public Hifive getHifive() {
		return hifive;
	}


	public void setHifive(Hifive hifive) {
		this.hifive = hifive;
	}


	public List<MarketVariable> getScenari() {
		return scenari;
	}


	public void setScenari(List<MarketVariable> scenari) {
		this.scenari = scenari;
	}

	
	
	public HiFiveResult getValuationResult(){
		setInfo();
		double theoPrice, theta, rho;
		Map<String, Double> delta, gamma, vega;
		double[] pr;
		
		List<Double> prList = new ArrayList<Double>();
//		prList.addAll(Arrays.asList(pr));
//		HiFiveResult rst = new HiFiveResult(theoPrice, delta, gamma, vega, theta, rho, prList);
//		return rst;
		
		return null;
	}
}
