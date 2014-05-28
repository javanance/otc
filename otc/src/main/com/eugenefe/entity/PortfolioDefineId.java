package com.eugenefe.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.eugenefe.converter.TableDynamicColumn;
import com.eugenefe.enums.EEquation;
import com.eugenefe.util.AnnoMethodTree;

@Embeddable
public class PortfolioDefineId implements Serializable{
	private String portId;
	private int portLevel;
	
	public PortfolioDefineId() {
	}

	public PortfolioDefineId(String portId, int portLevel) {
		this.portId = portId;
		this.portLevel = portLevel;
	}

	@Column(name = "PORT_ID", nullable = false, length = 20)
	@AnnoMethodTree(order =10, init=true)
	public String getPortId() {
		return portId;
	}
	public void setPortId(String portId) {
		this.portId = portId;
	}
	
//	@Column(name = "HIER_LEVEL", nullable = false, length = 20)
	@Column(name = "PORT_LEVEL", nullable = false)
	@AnnoMethodTree(order =20, init=true)
	public int getPortLevel() {
		return portLevel;
	}

	public void setPortLevel(int portLevel) {
		this.portLevel = portLevel;
	}

	
	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PortfolioDefineId))
			return false;
		PortfolioDefineId castOther = (PortfolioDefineId) other;

		return ((this.getPortId() == castOther.getPortId()) || (this.getPortId() != null
				&& castOther.getPortId() != null && this.getPortId().equals(castOther.getPortId())))
				&& this.getPortLevel() == castOther.getPortLevel();
	}


	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPortId() == null ? 0 : this.getPortId().hashCode());
		result = 37 * result + (getPortLevel());
		return result;
	}
/*	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PortfolioDefineId))
			return false;
		PortfolioDefineId castOther = (PortfolioDefineId) other;

		return ((this.getPortId() == castOther.getPortId()) || (this.getPortId() != null
				&& castOther.getPortId() != null && this.getPortId().equals(castOther.getPortId())))
				&& ((this.getPortLevel() == castOther.getPortLevel()) || (this.getPortLevel() != null
						&& castOther.getPortLevel() != null && this.getPortLevel().equals(castOther.getPortLevel())));
	}


	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPortId() == null ? 0 : this.getPortId().hashCode());
		result = 37 * result + (getPortLevel() == null ? 0 : this.getPortLevel().hashCode());
		return result;
	}*/
}
