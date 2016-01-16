package org.phoenix.node.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.stat.Statistics;

public class HibernateTools {
	private final static SessionFactory FACTORY = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		Configuration cfg = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		SessionFactory factory = cfg.buildSessionFactory(serviceRegistry);
		return factory;
	}
	
	public static SessionFactory getSessionFactory() {
		return FACTORY;
	}
	
	public static Session openSession() {
		return FACTORY.openSession();
	}
	
	public static Statistics getStatistics(){
		return FACTORY.getStatistics();
	}
	
	public static void close(Session session) {
		if(session!=null) session.close();
	}
}
