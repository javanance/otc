package com.eugenefe.entityinterface;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.eugenefe.util.AnnoMethodTree;

@Entity
@Table(name = "BOND")
@PrimaryKeyJoinColumn(name="MV_ID")
public class BondSubClass extends MvSubClass{

	private String issuerId;
	private Long faceAmt;
	private BigDecimal totalAmt;
	private String issueDate;
	private String maturityDate;
	
	
	
	public BondSubClass() {
	}

	@Column(name = "ISSUER_ID", length = 30)
	@Size(max = 30)
	@AnnoMethodTree(order=40, init=true)
	public String getIssuerId() {
		return this.issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	@Column(name = "FACE_AMT", precision = 10, scale = 0)
	@AnnoMethodTree(order=50, init=true, align="right")
	public Long getFaceAmt() {
		return this.faceAmt;
	}

	public void setFaceAmt(Long faceAmt) {
		this.faceAmt = faceAmt;
	}

	@Column(name = "TOTAL_AMT", scale = 0)
	@AnnoMethodTree(order=51, init=true, align="right")
	public BigDecimal getTotalAmt() {
		return this.totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	@Column(name = "ISSUE_DATE", length = 8)
	@Size(max = 8)
	@AnnoMethodTree(order=52, init=true)
	public String getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	@Column(name = "MATURITY_DATE", length = 8)
	@Size(max = 8)
	@AnnoMethodTree(order=53, init=true)
	public String getMaturityDate() {
		return this.maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
}
