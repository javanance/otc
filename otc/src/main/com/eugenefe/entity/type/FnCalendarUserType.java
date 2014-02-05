package com.eugenefe.entity.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.sql.Date;


import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import com.eugenefe.util.FnCalendar;

public class FnCalendarUserType implements UserType {
	private static final int[] SQL_TYPES = { Types.DATE };

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	public Class<FnCalendar> returnedClass() {
		return FnCalendar.class;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) {
			return true;
		} else if (x == null || y == null) {
			return false;
		} else {
			return x.equals(y);
		}
	}
	

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor arg2, Object arg3)
			throws HibernateException, SQLException {
		FnCalendar result = null;
//		System.out.println(names[0]);
		Date colDate = resultSet.getDate(names[0]);
		if (!resultSet.wasNull()) {
			result = FnCalendar.getFnCalFrom(colDate);
		}
		return result;
	}

	@Override
	public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor arg3)
			throws HibernateException, SQLException {
		if (value == null) {
			statement.setInt(index, 0);
		} else {
			Calendar tempCal = (Calendar)value;
			Date colDate =new Date(tempCal.getTimeInMillis());
			statement.setDate(index, colDate,tempCal);
		}
	}

	public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
			throws HibernateException, SQLException {
		FnCalendar result = null;
		Date colDate = resultSet.getDate(names[0]);
		if (!resultSet.wasNull()) {
			result = FnCalendar.getFnCalFrom(colDate);
		}
		return result;
	}


	public void nullSafeSet(PreparedStatement statement, Object value, int index)
			throws HibernateException, SQLException {
		if (value == null) {
			statement.setInt(index, 0);
		} else {
			Calendar tempCal = (Calendar)value;
			Date colDate =new Date(tempCal.getTimeInMillis());
			statement.setDate(index, colDate,tempCal);
		}
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	@Override
	public Object assemble(Serializable arg0, Object arg1)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object replace(Object arg0, Object arg1, Object arg2)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

}
