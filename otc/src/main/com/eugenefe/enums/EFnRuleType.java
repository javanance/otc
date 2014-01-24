package com.eugenefe.enums;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.eugenefe.util.Property;


public enum EFnRuleType {

	AND{
		public Criterion getRestriction(Property prop){
			return	Restrictions.conjunction();
		}
		
		public Criterion getOperation(){
			return	Restrictions.conjunction();
		}
	},
	OR{
		public Criterion getRestriction(Property prop){
			return	Restrictions.disjunction();
		}
		public Criterion getOperation(){
			return	Restrictions.disjunction();
		}
	},
	EQ {
		public Criterion getRestriction(Property prop){
			return	Restrictions.eq(prop.getPropertyId(), prop.getPropetyValue());
		}
		public Criterion getRestriction( String propGroup, Property prop){
			String aliasName = propGroup + "."+ prop.getPropertyId();
			return	Restrictions.eq(aliasName, prop.getPropetyValue());
		}
	},
	NE{
		public Criterion getRestriction(Property prop){
			return	Restrictions.ne(prop.getPropertyId(), prop.getPropetyValue());
		}
		public Criterion getRestriction( String propGroup, Property prop){
			String aliasName = propGroup + "."+ prop.getPropertyId();
			return	Restrictions.ne(aliasName, prop.getPropetyValue());
		}
	},
	GT{
		public Criterion getRestriction(Property prop){
			return	Restrictions.gt(prop.getPropertyId(), prop.getPropetyValue());
		}
		public Criterion getRestriction( String propGroup, Property prop){
			String aliasName = propGroup + "."+ prop.getPropertyId();
			return	Restrictions.gt(aliasName, prop.getPropetyValue());
		}
	},
	EQ_GT{
		public Criterion getRestriction(Property prop){
			return	Restrictions.ge(prop.getPropertyId(), prop.getPropetyValue());
		}
		public Criterion getRestriction( String propGroup, Property prop){
			String aliasName = propGroup + "."+ prop.getPropertyId();
			return	Restrictions.ge(aliasName, prop.getPropetyValue());
		}
	},
	LT{
		public Criterion getRestriction(Property prop){
			return	Restrictions.lt(prop.getPropertyId(), prop.getPropetyValue());
		}
		public Criterion getRestriction( String propGroup, Property prop){
			String aliasName = propGroup + "."+ prop.getPropertyId();
			return	Restrictions.lt(aliasName, prop.getPropetyValue());
		}
	},
	EQ_LT{
		public Criterion getRestriction(Property prop){
			return	Restrictions.le(prop.getPropertyId(), prop.getPropetyValue());
		}
		public Criterion getRestriction( String propGroup, Property prop){
			String aliasName = propGroup + "."+ prop.getPropertyId();
			return	Restrictions.le(aliasName, prop.getPropetyValue());
		}
	},
	LIKE{
		public Criterion getRestriction(Property prop){
			return	Restrictions.like(prop.getPropertyId(), prop.getPropetyValue());
		}
		public Criterion getRestriction( String propGroup, Property prop){
			String aliasName = propGroup + "."+ prop.getPropertyId();
			return	Restrictions.like(aliasName, prop.getPropetyValue());
		}
	},
	NULL{
		public Criterion getRestriction(Property prop){
			return	Restrictions.isNull(prop.getPropertyId());
		}
		public Criterion getRestriction( String propGroup, Property prop){
			String aliasName = propGroup + "."+ prop.getPropertyId();
			return	Restrictions.isNull(aliasName);
		}
	},
	NOT_NULL{
		public Criterion getRestriction(Property prop){
			return	Restrictions.isNotNull(prop.getPropertyId());
		}
		public Criterion getRestriction( String propGroup, Property prop){
			String aliasName = propGroup + "."+ prop.getPropertyId();
			return	Restrictions.isNotNull(aliasName);
		}
	};
	
	
	public Criterion getRestriction( Property prop){
		return null;
	}
	public Criterion getRestriction( String propGroup, Property prop){
		return null;
	}
	public Criterion getOperation(){
		return null;
	}

}
