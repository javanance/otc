package com.eugenefe.entity.component;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.eugenefe.entity.HierarchyProperty;
import com.eugenefe.enums.EEquation;

@Embeddable
public class PropertyEquationNew implements Serializable{

	private String propGroup;
	private String propName;
	private EEquation equation;
	private String condition1;
	private String condition2;
	

	public PropertyEquationNew() {
	}

	@Column(name="TABLE_ID")
	public String getPropGroup() {
		return propGroup;
	}


	public void setPropGroup(String propGroup) {
		this.propGroup = propGroup;
	}

	@Column(name="COLUMN_ID")
	public String getPropName() {
		return propName;
	}


	public void setPropName(String propName) {
		this.propName = propName;
	}


	@Enumerated(EnumType.STRING)
	@Column(name="EQUATION" )
	public EEquation getEquation() {
		return equation;
	}
	public void setEquation(EEquation equation) {
		this.equation = equation;
	}

	@Column(name="CONDITION_1")
	public String getCondition1() {
		return condition1;
	}
	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}

	@Column(name="CONDITION_2")
	public String getCondition2() {
		return condition2;
	}
	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}


	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}


	@Override
	public boolean equals(Object obj) {
//		System.out.println("Equals" + this.getCondition1());
		if( obj instanceof PropertyEquationNew){
			PropertyEquationNew castObj = (PropertyEquationNew)obj;
//			System.out.println("equals1"+ castObj.getProperty().getPropId());
//			System.out.println("equals2"+ this.getProperty().getPropId());
//			System.out.println("equals3"+ this.getEquation() +"_"+ castObj.getEquation());
			if( this.getPropGroup().equals(castObj.getPropGroup())
					&& this.getPropName().equals(castObj.getPropName())
						&& this.getEquation().getValue().equals(castObj.getEquation().getValue())){
				if(this.getCondition1()==null){
//					System.out.println("Equals1" + this.getCondition1());
					return true;
				}else if( this.getCondition1().equals(castObj.getCondition1())){
					if(this.getCondition2()==null){
						return true;
					}
					else if( this.getCondition2().equals(castObj.getCondition2())){
						return true;
					}else{
						return false;
					}
//					return true;
				}else{
//					System.out.println("Equals3" + this.getCondition1());
					return false;
				}
				
			}else{
//				System.out.println("Equals4" + this.getCondition1());
				return false;
			}
			
		}else{
			System.out.println("Equals5" + this.getCondition1());
			return false;
		}
	}

	
}
