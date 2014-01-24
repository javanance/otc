package com.eugenefe.entityinterface;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import com.eugenefe.entity.Stock;
import com.eugenefe.util.HibernateUtil;


@Name("inheritanceTest")
public class InheritanceTest {
//	@In
//	private static EntityManager entityManager;
//	@Logger
//	private static Log log;
//	@In
//	private static Session session;
//	private static Session session = HibernateUtil.currentSession(); 
	public static void main(String[] args) {
		view();
	}
	
	private static void view(){
		SessionFactory sf = HibernateUtil.getAnnoFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		
		String qr = "select a from StockSubClass a";
		List<StockSubClass> rst = session.createQuery(qr).list();
		
		for(StockSubClass aa : rst){
			System.out.println( aa.getMvId()+"_" + aa.getMvName());
		}
	}

}
