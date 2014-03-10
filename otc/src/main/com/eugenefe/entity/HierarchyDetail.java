package com.eugenefe.entity;

// Generated 2014. 2. 10 ���� 5:51:10 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.eugenefe.enums.EEquation;
import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;
import com.eugenefe.util.EColumnType;

/**
 * HierarchyDetail generated by hbm2java
 */
@Entity
@Table(name = "HIERARCHY_DETAIL")
@AnnoNavigationFilter
public class HierarchyDetail implements java.io.Serializable {

	private HierarchyDetailId id;
	private Hierarchy hierarchy;
	
	private String tableId;
	private String columnId;
	private EEquation equation;
	private String condition1;
	private String condition2;

	public HierarchyDetail() {
	}

	public HierarchyDetail(HierarchyDetailId id) {
		this.id = id;
	}


	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "hierarchyId", column = @Column(name = "HIERARCHY_ID", nullable = false, length = 20)),
			@AttributeOverride(name = "nodeId", column = @Column(name = "NODE_ID", nullable = false, length = 20)) })
	@AnnoMethodTree(order =10, init=true)
	public HierarchyDetailId getId() {
		return this.id;
	}

	public void setId(HierarchyDetailId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "HIERARCHY_ID", nullable = false, insertable = false, updatable = false)
	@AnnoMethodTree(order=15, init=true, type=EColumnType.Entity, aggregatable=false)	
	public Hierarchy getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(Hierarchy hierarchy) {
		this.hierarchy = hierarchy;
	}

	@Column(name = "TABLE_ID", nullable = true, length = 20)
	@AnnoMethodTree(order =20, init=true)
	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
	@Column(name = "COLUMN_ID", nullable = true, length = 20)
	@AnnoMethodTree(order =21, init=true)
	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	@Column(name = "EQUATION", nullable = true, length = 20)
	@Enumerated(EnumType.STRING)
	@AnnoMethodTree(order =30, init=true)
	public EEquation getEquation() {
		return equation;
	}

	public void setEquation(EEquation equation) {
		this.equation = equation;
	}

	@Column(name = "CONDITION_1", nullable = true, length = 20)
	@AnnoMethodTree(order =31, init=true)
	public String getCondition1() {
		return condition1;
	}

	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}
	
	@Column(name = "CONDITION_2", nullable = true, length = 20)
	@AnnoMethodTree(order =32, init=true)
	public String getCondition2() {
		return condition2;
	}

	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}
}
