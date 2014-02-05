package com.eugenefe.entity.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;


public class EnumUserType implements UserType, ParameterizedType {
	 /**
	 * @uml.property  name="clazz"
	 */
	private Class clazz = null;
	   
	   public void setParameterValues(Properties params) {
	      String enumClassName = params.getProperty("enumClassName");
		  //String enumClassName = params.getProperty("takion");
	      if (enumClassName == null) {
	         throw new MappingException("enumClassName parameter not specified");
	      }
	      
	      try {
	            this.clazz = Class.forName(enumClassName);
	        } catch (java.lang.ClassNotFoundException e) {
	         throw new MappingException("enumClass " + enumClassName + " not found", e);
	        }
	   }
	   
	    private static final int[] SQL_TYPES = {Types.VARCHAR};
	    public int[] sqlTypes() {
	        return SQL_TYPES;
	    }

	    public Class returnedClass() {
	        return clazz;
	    }

		@Override
		public Object nullSafeGet(ResultSet rs, String[] names,
				SessionImplementor session, Object owner)
				throws HibernateException, SQLException {
			// TODO Auto-generated method stub
			String name = rs.getString(names[0]);
		    Object result = null;
	        if (!rs.wasNull()) {
	            result = Enum.valueOf(clazz, name);
	        }
	        return result;
		}
		
	    @Override
		public void nullSafeSet(PreparedStatement st, Object value, int index,
				SessionImplementor session) throws HibernateException,
				SQLException {
			// TODO Auto-generated method stub
	    	 if (null == value) {
		            st.setNull(index, Types.VARCHAR);
		        } else {
		            st.setString(index, ((Enum)value).name());
		        }
		}

	    public Object deepCopy(Object value) throws HibernateException{
	        return value;
	    }

	    public boolean isMutable() {
	        return false;
	    }

	    public Object assemble(Serializable cached, Object owner) throws HibernateException {
	        return cached;
	    }

	    public Serializable disassemble(Object value) throws HibernateException {
	        return (Serializable)value;
	    }

	    public Object replace(Object original, Object target, Object owner) throws HibernateException {
	        return original;
	    }
	    public int hashCode(Object x) throws HibernateException {
	        return x.hashCode();
	    }
	    public boolean equals(Object x, Object y) throws HibernateException {
	        if (x == y)
	            return true;
	        if (null == x || null == y)
	            return false;
	        return x.equals(y);
	    }



}
