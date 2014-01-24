package com.eugenefe.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.deployment.HotDeploymentStrategy;
import org.jboss.seam.deployment.StandardDeploymentStrategy;
import org.jboss.seam.log.Log;

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.pricer.hifive.IHiFiveFDM;

@Name("annotaionLoad")
@Scope(ScopeType.APPLICATION)
@Startup
public class AnnotationLoad {
		@Logger
		private Log log;
//	   @In("#{deploymentStrategy.annotatedClasses['com.eugenefe.util.AnnoMethodOrder']}")
//	   private Set<Class<Object>> fooClasses;
//	   
//	   @In("#{hotDeploymentStrategy.annotatedClasses['com.eugenefe.util.AnnoMethodOrder']}")
//	   private Set<Class<Object>> hotFooClasses;
	   
	   @In("#{deploymentStrategy}")
	   private StandardDeploymentStrategy  deploymentStrategy;
	   
	   @In("#{hotDeploymentStrategy}")
	   private HotDeploymentStrategy  hotDeploymentStrategy;
	   
	   @Create
	   public void create() {
		   
//		  deploymentStrategy.getAnnotatedClasses(); 
//	      for (Class clazz : fooClasses) {
//	         handleClass(clazz);
	    	  
//	      }
//	      for (Class clazz : hotFooClasses) {
//	         handleClass(clazz);
//	      }
	   }
	   public static void main(String[] args){
		   pricingHiFive();
	   }
	   public static void AAA(){
		   Class klazz;
		   for(ENavigationData aa :ENavigationData.values()){
			   try {
//				klazz = Class.forName(aa.getShortName());
				   klazz = Class.forName(aa.getQualifiedName());
				if(klazz.isAnnotationPresent(AnnoNavigationFilter.class)){
					for(Method mm : klazz.getDeclaredMethods()){
						if(mm.isAnnotationPresent(AnnoMethodTree.class)){
//							System.out.println(klazz.getName() + ": Method :" + mm.getName());
//							System.out.println("Method :" + mm.getName());
							System.out.println( mm.getName()+"=" );
						}
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
		   }
//		   double pv = IPlainOption.pricer.BSOption(1, 100, 100, 0.1, 0.1, 0.03, 0.03, 0, 0.2);
//		   System.out.println("value: " + pv);
		   
		   
		   List<EMenuGroup> aa = EMenuGroup.valueOf("Product").getChidrenGroup();
		   for(EMenuGroup kk : aa){
			   System.out.println("EMenuGroup:"+ aa);
		   }
	   }
	   
		public static void pricingHiFive() {
			// TODO Auto-generated method stub
			System.out.println("value0:");
			int nStock =2;
			double[] S0 = new double[nStock]; 
			double[] div = new double[nStock];
			double div1 =0;
			double div2 =0;
			double S01 =1;
			double S02 =1;
			S0[0] = 0.9;
			S0[1] = 1;
			
			for(int i=0; i<nStock; i++){
				div[i]=0;
			}
			
			int nStrike =6;
			double[] X1= new double[nStrike];
			double[] X2= new double[nStrike];
			double[] X3= new double[nStrike];
			
			double[] upBarrier= new double[nStrike];		// IN:  Knock-out Barrier
			int[] bUpHitted = new int[nStrike];  		// IN:  Knock-out Barrier Hitting 여부
			int bUpHittedD=0; 
			
			double[] amt1= new double[nStrike];			// IN:  행사가격1에 해당되는 중도상환 금액
			double[] amt2= new double[nStrike];			// IN:  행사가격2에 해당되는 중도상환 금액
			double[] amt3= new double[nStrike];			// IN:  행사가격3에 해당되는 중도상환 금액
			double[] coupon= new double[nStrike];			// IN:  추가 지급 쿠폰
			
			int[] dates= new int[nStrike]; 			// IN:  중간 평가일까지의 일수 
			int[] pay_dates= new int[nStrike]; 			// IN:  중간 지급일까지의 일수 
			double[] p = new double[nStrike];		// OUT: 중도상환 확률
			
			for(int j=0; j<nStrike; j++){
				X1[j] = (95 - j*5)/100;
				X2[j] = (95 - j*5)/100;
				X3[j] = (95 - j*5)/100;
				upBarrier[j] = 10000; 
				bUpHitted[j]=0;
				
				amt1[j] = (100 + j*5)/100;
				amt2[j] = (100 + j*5)/100;
				amt3[j] = (100 + j*5)/100;
				coupon[j] =0;
				
				dates[j] = 100 *(j+1);
				pay_dates[j] = 100 *(j+1);
				p[j] =0;
			}

			int nt =2		;		
			double[] t = new double[nt];	
			double[] rf1= new double[nt];
			double[] rf2= new double[nt];
			double[] rd = new double[nt];

			for(int k=0; k<nt; k++){
				t[k]=k+1;
				rf1[k] = 0.02;
				rf2[k] = 0.02;
				rd[k] = 0.02;
			}
			
			int n_vol =2;
			double[] t_vol = new double[n_vol];
			double[] vol1= new double[n_vol];
			double[] vol2= new double[n_vol];
//			double[][] rho= new double[n_vol][n_vol];
			double[] rho= new double[n_vol*n_vol];
			
			for(int k=0; k<nt; k++){
				t_vol[k]=k+1;
				vol1[k] = 0.02;
				vol2[k] = 0.02;
			}
			
			for(int h=0; h<n_vol*n_vol; h++){
				rho[h] = 0.02;
			}
			
			double downBarrier =0.5;		// IN:  Knock-in Barrier

			int upBarrierFlag = 1;		// IN:  upBarrier 체크 방법(0:동시, 1:각각)
			int downBarrierFlag = 0;		// IN:  downBarrier 유무(0:없음, 1:있음)
			
			int bDownHitted =0;  		// IN:  Knock-in Barrier Hitting 여부
			int nTrials =1000;			// IN:  시행회수 
			double max_loss= 100;		// IN:  최대손실
			
			
			double[] delta= new double[nStock];
			double[] gamma =new double[nStock];
			double[] theta =new double[nStock];
			
			double[] delta1= new double[nStock];
			double[] gamma1 =new double[nStock];
			double[] delta2= new double[nStock];
			double[] gamma2 =new double[nStock];
			double[] cross =new double[nStock];
			
		
			double pv = IHiFiveFDM.INSTANCE.HiFive1D_FDM(S01, t, rf1, rd, nt, div1, 
					t_vol, vol1, n_vol,X1, X2, X3, 
					upBarrier, downBarrier,  
					amt1, amt2, amt3, coupon, dates, pay_dates, 
					nStrike, 
					downBarrierFlag, bUpHittedD, bDownHitted, max_loss, delta, gamma, theta);
			double ret_rho = IHiFiveFDM.INSTANCE.HiFive1D_FDM_Rho(S01, t, rf1, rd, nt, div1, 
					t_vol, vol1, n_vol,X1, X2, X3, 
					upBarrier, downBarrier,  
					amt1, amt2, amt3, coupon, dates, pay_dates, 
					nStrike, 
					downBarrierFlag, bUpHittedD, bDownHitted, max_loss);
			double ret_vega = IHiFiveFDM.INSTANCE.HiFive1D_FDM_Vega(S01, t, rf1, rd, nt, div1, 
					t_vol, vol1, n_vol,X1, X2, X3, 
					upBarrier, downBarrier,  
					amt1, amt2, amt3, coupon, dates, pay_dates, 
					nStrike, 
					downBarrierFlag, bUpHittedD, bDownHitted, max_loss);
			System.out.println("1D_FDM:"+pv );
			System.out.println("1D_FDM:"+ret_rho );
			System.out.println("1D_FDM:"+ret_vega );
																
			double pv_2d = IHiFiveFDM.INSTANCE.HiFive2D_FDM(S01, S02, t_vol, rf1, rf2, rd, nt, t_vol, vol1, vol2, n_vol, 0.1, 
					div1, div2, X1, X2, X3, upBarrier, downBarrier, amt1, amt2, amt3, 
					coupon, dates, pay_dates, nStrike, downBarrierFlag, 0, bDownHitted, 
					50, max_loss, delta1, delta2, gamma1, gamma2, cross, theta);
			
			double rho_2d = IHiFiveFDM.INSTANCE.HiFive2D_FDM_Rho(S01, S02, t_vol, rf1, rf2, rd, nt, t_vol, vol1, vol2, n_vol, 0.1, 
					div1, div2, X1, X2, X3, upBarrier, downBarrier, amt1, amt2, amt3, 
					coupon, dates, pay_dates, nStrike, downBarrierFlag, 0, bDownHitted, 
					50, max_loss);
			
			double vega1_2d = IHiFiveFDM.INSTANCE.HiFive2D_FDM_Vega1(S01, S02, t_vol, rf1, rf2, rd, nt, t_vol, vol1, vol2, n_vol, 0.1, 
					div1, div2, X1, X2, X3, upBarrier, downBarrier, amt1, amt2, amt3, 
					coupon, dates, pay_dates, nStrike, downBarrierFlag, 0, bDownHitted, 
					50, max_loss);
			double vega2_2d = IHiFiveFDM.INSTANCE.HiFive2D_FDM_Vega2(S01, S02, t_vol, rf1, rf2, rd, nt, t_vol, vol1, vol2, n_vol, 0.1, 
					div1, div2, X1, X2, X3, upBarrier, downBarrier, amt1, amt2, amt3, 
					coupon, dates, pay_dates, nStrike, downBarrierFlag, 0, bDownHitted, 
					50, max_loss);
			
			
			System.out.println("2D_FDM:"+pv_2d );
			System.out.println("2D_FDM:"+rho_2d );
			System.out.println("2D_FDM:"+vega1_2d );
			System.out.println("2D_FDM:"+vega2_2d );
		}
	   
	   
	   
}
