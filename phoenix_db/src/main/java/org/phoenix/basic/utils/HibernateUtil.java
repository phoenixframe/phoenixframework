package org.phoenix.basic.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
/**
 * hibernate工具类
 * @author mengfeiyang
 *
 */
public class HibernateUtil {
	private final static SessionFactory FACTORY = buildSessionFactory();
    //new File("c:\\phoenix\\hibernate.cfg.xml")
	private static SessionFactory buildSessionFactory() {
		Configuration cfg = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		SessionFactory factory = cfg.buildSessionFactory(serviceRegistry);
		return factory;
	}
	
	public static SessionFactory getSessionFactory() {
		return FACTORY;
	}
	
	public static  Session openSession() {
		return FACTORY.openSession();
	}
	
	public static void closeSession(Session session) {
		if(session!=null)session.close();
	}
}
