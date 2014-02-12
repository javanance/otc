package com.eugenefe.entity;

// Generated 2014. 2. 10 ���� 5:51:10 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;

/**
 * HierNode generated by hbm2java
 */
@Entity
@Table(name = "HIER_NODE")
@AnnoNavigationFilter
public class HierarchyNode implements java.io.Serializable {

	private String nodeId;
	private String nodeName;
	private String positionColumnId;
	private String nodeGroup;
	private String tableId;
	private String tableName;
	private String columnId;
	private String columnName;
	private String columnValue;

	public HierarchyNode() {
	}

	public HierarchyNode(String nodeId) {
		this.nodeId = nodeId;
	}

	public HierarchyNode(String nodeId, String nodeName, String nodeGroup, String tableId, String tableName,
			String columnId, String columnName, String positionColumnId) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeGroup = nodeGroup;
		this.tableId = tableId;
		this.tableName = tableName;
		this.columnId = columnId;
		this.columnName = columnName;
		this.positionColumnId = positionColumnId;
	}

	@Id
	@Column(name = "NODE_ID", unique = true, nullable = false, length = 20)
	@AnnoMethodTree(order =10, init=true)
	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "NODE_NAME", length = 50)
	@AnnoMethodTree(order =20, init=true)
	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Column(name = "NODE_GROUP", length = 20)
	@AnnoMethodTree(order =30, init=true)
	public String getNodeGroup() {
		return this.nodeGroup;
	}

	public void setNodeGroup(String nodeGroup) {
		this.nodeGroup = nodeGroup;
	}

	@Column(name = "TABLE_ID", length = 50)
	@AnnoMethodTree(order =40, init=true)
	public String getTableId() {
		return this.tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	@Column(name = "TABLE_NAME", length = 50)
	@AnnoMethodTree(order =41, init=true)
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "COLUMN_ID", length = 50)
	@AnnoMethodTree(order =50, init=true)
	public String getColumnId() {
		return this.columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	@Column(name = "COLUMN_NAME", length = 50)
	@AnnoMethodTree(order =51, init=true)
	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Column(name = "COLUMN_VALUE")
	@AnnoMethodTree(order =51, init=true)
	public String getColumnValue() {
		return columnValue;
	}
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	@Column(name = "POSITION_COLUMN_ID", length = 50)
	@AnnoMethodTree(order =60, init=true)
	public String getPositionColumnId() {
		return this.positionColumnId;
	}

	public void setPositionColumnId(String positionColumnId) {
		this.positionColumnId = positionColumnId;
	}
}
