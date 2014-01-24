package com.eugenefe.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

//import com.eugene.util.HibernateUtil;

//HasSameUnit : �Լ� ��������� ���� �ݸ���� �����ϴ� ���,  �ݸ���� ������ �����ݸ��� ������ �ٸ��� �������� ��ȯ�� ������, �ƴϸ� �ܼ��� �־��� ������ ���� �״��
//				����Ұ������� �����ϴ� property , boolean �� ���� ������ , Default �� �ܼ���  (true)

public class Property {

	private String propertyId;
	private String propertyName;
	private String propertyType;
	private String stringValue;
	private boolean booleanValue;
	private double doubleValue;
	private int intValue;
	private FnCalendar dateValue;
	

	public Property() {
		
	}
	
	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public boolean getBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	public FnCalendar getDateValue() {
		return dateValue;
	}

	public void setDateValue(FnCalendar dateValue) {
		this.dateValue = dateValue;
	}
	
//	****************************Biz Method**************
	public Object getPropetyValue(){
		if(getPropertyId()!=null){
			if(getPropertyType().equals("STRING")){
				return getStringValue();
			}
			else if(getPropertyType().equals("BOOLEAN")){
				return getBooleanValue();
			}
			else if(getPropertyType().equals("DATE")){
				return getDateValue();
			}
			else if(getPropertyType().equals("INTEGER")){
				return getBooleanValue();
			}
			else if(getPropertyType().equals("DOUBLE")){
				return getDoubleValue();
			}
		}
		return null;
	}
//****************For Static Method************************
//	�� ���������?...
//	private static Map<String, Property> prop = new LinkedHashMap<String, Property>();
	
//	public static Property getProperity(String propId){
//		if(! prop.containsKey(propId)){
//			Session s = HibernateUtil.currentSession();
//			Query qr =s.createQuery("from SystemProperty a where a.propertyId = :param");
//			qr.setParameter("param", propId);
//			prop.put(propId, (Property)qr.uniqueResult());
//		}
//		return prop.get(propId);
////		return (Property)qr.uniqueResult();
//	}	
//	public static List<Property> getProperty(){
//			List<Property> properties =new ArrayList<Property>();
//			Session s = HibernateUtil.currentSession();
//			Query qr =s.createQuery("from SystemProperty a ");
//			properties = qr.list();
//		return properties;
//	}
//	public static Map<String,Property> getProperties(){
//		Map<String,Property> properties =new LinkedHashMap<String, Property>();
//		List<Property> prop =new ArrayList<Property>();
//		Session s = HibernateUtil.currentSession();
//		Query qr =s.createQuery("from SystemProperty a ");
//		prop = qr.list();
//		for(Property aa: prop){
//			properties.put(aa.getPropertyId(), aa);
//		}
//		
//	return properties;
//}

}
