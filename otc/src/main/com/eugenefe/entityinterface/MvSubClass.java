package com.eugenefe.entityinterface;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.MarketVariableType;

@Entity
@Table(name = "MARKET_VARIABLE")
@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MvSubClass {
		public String mvId;
		public String mvName;
		public MarketVariableType mvType;
		public String productYN;
		public String riskFactorYN;


		@Id
		@Column(name = "MV_ID", unique = true, nullable = false, length = 20)
		@NotNull
		@Size(max = 20)
		@AnnoMethodTree(order=10, init=true)
		public String getMvId() {
			return this.mvId;
		}
		public void setMvId(String mvId) {
			this.mvId = mvId;
		}
		
		@Column(name = "MV_NAME")
		@AnnoMethodTree(order=20, init=true)
		public String getMvName() {
			return mvName;
		}
		public void setMvName(String mvName) {
			this.mvName = mvName;
		}

		@Column(name = "MV_TYPE")
		@Enumerated(EnumType.STRING)
		@AnnoMethodTree(order=30, init=true)
		public MarketVariableType getMvType() {
			return mvType;
		}

		public void setMvType(MarketVariableType mvType) {
			this.mvType = mvType;
		}

		@Column(name = "PRODUCT_YN")
		@AnnoMethodTree(order=40, init=true)
		public String getProductYN() {
			return productYN;
		}

		public void setProductYN(String productYN) {
			this.productYN = productYN;
		}
		
		@Column(name = "RF_YN")
		@AnnoMethodTree(order=50, init=true)
		public String getriskFactorYN() {
			return riskFactorYN;
		}

		public void setriskFactorYN(String riskFactorYN) {
			this.riskFactorYN = riskFactorYN;
		}
}
