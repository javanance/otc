package com.eugenefe.entity;

// Generated 2014. 2. 10 ���� 5:51:10 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.eugenefe.util.AnnoMethodTree;
import com.eugenefe.util.AnnoNavigationFilter;

/**
 * HierarchyDetailId generated by hbm2java
 */
@Embeddable
@AnnoNavigationFilter
public class HierarchyDetailId implements java.io.Serializable {

	private String hierarchyId;
	private String hierLevel;
//	private int lvl;
	
	public HierarchyDetailId() {
	}

//	public HierarchyDetailId(String hierarchyId, int lvl) {
//		super();
//		this.lvl = lvl;
//	}
	
	public HierarchyDetailId(String hierarchyId, String hierLevel) {
		this.hierarchyId = hierarchyId;
		this.hierLevel = hierLevel;
	}

	
	@Column(name = "HIERARCHY_ID", nullable = false, length = 20)
	@AnnoMethodTree(order =10, init=true)
	public String getHierarchyId() {
		return this.hierarchyId;
	}

	

	public void setHierarchyId(String hierarchyId) {
		this.hierarchyId = hierarchyId;
	}

	@Column(name = "HIER_LEVEL", nullable = false, length = 20)
	@AnnoMethodTree(order =20, init=true)
	public String getHierLevel() {
		return hierLevel;
	}

	public void setHierLevel(String hierLevel) {
		this.hierLevel = hierLevel;
	}

	
	
	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HierarchyDetailId))
			return false;
		HierarchyDetailId castOther = (HierarchyDetailId) other;

		return ((this.getHierarchyId() == castOther.getHierarchyId()) || (this.getHierarchyId() != null
				&& castOther.getHierarchyId() != null && this.getHierarchyId().equals(castOther.getHierarchyId())))
				&& ((this.getHierLevel() == castOther.getHierLevel()) || (this.getHierLevel() != null
						&& castOther.getHierLevel() != null && this.getHierLevel().equals(castOther.getHierLevel())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result + (getHierarchyId() == null ? 0 : this.getHierarchyId().hashCode());
		result = 37 * result + (getHierLevel() == null ? 0 : this.getHierLevel().hashCode());
		return result;
	}


/*	@Column(name = "LVL", nullable = false)
	@AnnoMethodTree(order =20, init=true)
	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	
	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HierarchyDetailId))
			return false;
		HierarchyDetailId castOther = (HierarchyDetailId) other;

		return ((this.getHierarchyId() == castOther.getHierarchyId()) || (this.getHierarchyId() != null
				&& castOther.getHierarchyId() != null && this.getHierarchyId().equals(castOther.getHierarchyId())))
					&& ((this.getLvl() == castOther.getLvl()));
	}


	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result + (getHierarchyId() == null ? 0 : this.getHierarchyId().hashCode());
		result = 37 * result + (this.getLvl());
		return result;
	}	
	*/
	
}
