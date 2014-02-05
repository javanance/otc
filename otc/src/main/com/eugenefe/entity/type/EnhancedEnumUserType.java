package com.eugenefe.entity.type;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;

public class EnhancedEnumUserType implements EnhancedUserType, ParameterizedType {

	/**
	 * @uml.property  name="enumClass"
	 */
	private Class<Enum> enumClass;

    public void setParameterValues(Properties parameters) {
        String enumClassName = parameters.getProperty("enumClassName");
        try {
            enumClass = (Class<Enum>) Class.forName(enumClassName);
        }
        catch (ClassNotFoundException cnfe) {
            throw new HibernateException("Enum class not found", cnfe);
        }
    }

    public Object assemble(Serializable cached, Object owner) 
    throws HibernateException {
        return cached;
    }

    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Enum) value;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        return x==y;
    }

    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public boolean isMutable() {
        return false;
    }
    
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		String name = rs.getString( names[0] );
        return rs.wasNull() ? null : Enum.valueOf(enumClass, name);
	}

	
//    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) 
//    throws HibernateException, SQLException {
//        String name = rs.getString( names[0] );
//        return rs.wasNull() ? null : Enum.valueOf(enumClass, name);
//    }

    @Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		// TODO Auto-generated method stub
        if (value==null) {
            st.setNull(index, Types.VARCHAR);
        }
        else {
            st.setString( index, ( (Enum) value ).name() ); 
        }
	}
//    public void nullSafeSet(PreparedStatement st, Object value, int index) 
//    throws HibernateException, SQLException {
//        if (value==null) {
//            st.setNull(index, Types.VARCHAR);
//        }
//        else {
//            st.setString( index, ( (Enum) value ).name() ); 
//        }
//    }

    public Object replace(Object original, Object target, Object owner) 
    throws HibernateException {
        return original;
    }

    public Class returnedClass() {
        return enumClass;
    }

    public int[] sqlTypes() {
        return new int[] { Types.VARCHAR };
    }

    public Object fromXMLString(String xmlValue) {
        return Enum.valueOf(enumClass, xmlValue);
    }

    public String objectToSQLString(Object value) {
        return '\'' + ( (Enum) value ).name() + '\'';
    }

    public String toXMLString(Object value) {
        return ( (Enum) value ).name();
    }
}
