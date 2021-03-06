package com.eugenefe.entity;

// Generated Apr 16, 2013 7:33:55 PM by Hibernate Tools 4.0.0

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;

/**
 * Position generated by hbm2java
 */
@Entity
@Table(name = "POSITION_PROP")
@AnnoNavigationFilter
public class PositionProperty implements Serializable {

//	private String posId;
//	private String propId;
	private PositionPropertyId id;
	private Position position;
	private HierarchyProperty prop;
	private String tableId;
	private String columnId;
	private String propValue;

	public PositionProperty() {
	}

//	@Id
//	@Column(name = "POS_ID", nullable = false, length = 50)
//	@AnnoMethodTree(order=10, init=true, aggregatable=true)
//	public String getPosId() {
//		return this.posId;
//	}
//
//	public void setPosId(String posId) {
//		this.posId = posId;
//	}

	@EmbeddedId
	@NotNull
	public PositionPropertyId getId() {
		return this.id;
	}

	public void setId(PositionPropertyId id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="POS_ID" , insertable=false , updatable=false)
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@ManyToOne
	@JoinColumn(name="PROP_ID" , insertable=false , updatable=false)
	public HierarchyProperty getProp() {
		return prop;
	}

	public void setProp(HierarchyProperty prop) {
		this.prop = prop;
	}

	@Column(name = "TABLE_ID", nullable = false, length = 20)
	@AnnoMethodTree(order=10, init=true, aggregatable=true)
	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
	@Column(name = "COLUMN_ID", nullable = false, length = 20)
	@AnnoMethodTree(order=10, init=true, aggregatable=true)
	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	@Column(name = "PROP_VALUE",  length = 50)
	@AnnoMethodTree(order=10, init=true, aggregatable=true)
	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}
}
