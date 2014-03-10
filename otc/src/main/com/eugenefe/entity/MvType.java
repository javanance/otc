package com.eugenefe.entity;

// Generated 2014. 1. 17 ���� 5:07:56 by Hibernate Tools 4.0.0

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;
import com.eugenefe.util.EColumnType;



/**
 * MvType generated by hbm2java
 */
@Entity
@Table(name = "MV_TYPE")
@AnnoNavigationFilter
public class MvType implements java.io.Serializable, ITree {

	private String mvType;
	private String mvTypeName;
//	private String parentMvType;
	private MvType parentMvType;
	private List<MvType> childList;

	public MvType() {
	}

//	public MvType(String mvType, String parentMvType) {
//		this.mvType = mvType;
//		this.parentMvType = parentMvType;
//	}
	public MvType(String mvType, String mvTypeName, MvType parentMvType) {
		this.mvType = mvType;
		this.mvTypeName = mvTypeName;
		this.parentMvType = parentMvType;
	}

	@Id
	@Column(name = "MV_TYPE", unique = true, nullable = false, length = 20)
	@AnnoMethodTree(order=10, init=true)
	public String getMvType() {
		return this.mvType;
	}

	public void setMvType(String mvType) {
		this.mvType = mvType;
	}

	@Column(name = "MV_TYPE_NAME", length = 50)
	@AnnoMethodTree(order=20, init=true)
	public String getMvTypeName() {
		return this.mvTypeName;
	}

	public void setMvTypeName(String mvTypeName) {
		this.mvTypeName = mvTypeName;
	}


//	@Column(name = "PARENT_MV_TYPE", nullable = false, length = 20)
//	@AnnoMethodTree(order=30, init=true)
//	public String getParentMvType() {
//		return this.parentMvType;
//	}
//
//	public void setParentMvType(String parentMvType) {
//		this.parentMvType = parentMvType;
//	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_MV_TYPE", nullable = true, insertable = false, updatable = false)
	@AnnoMethodTree(order=30, init=true)
	public MvType getParentMvType() {
		return parentMvType;
	}
	public void setParentMvType(MvType parentMvType) {
		this.parentMvType = parentMvType;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentMvType")
	@AnnoMethodTree(order=40, init=false, type=EColumnType.List)
	public List<MvType> getChildList() {
		return childList;
	}

	public void setChildList(List<MvType> childList) {
		this.childList = childList;
	}

	@Transient
	@Override
	public String getId() {
		return getMvType();
	}
//
//	@Override
//	public int getChildrenSize() {
//		return getChildList().size();
//	}

	@Override
	@Transient
	public List<? extends ITree> getChildren() {
		return getChildList();
	}


	
}
