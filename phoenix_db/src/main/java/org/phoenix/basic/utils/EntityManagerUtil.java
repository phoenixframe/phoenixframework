/**
 * 
 */
package org.phoenix.basic.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * @author mengfeiyang
 *
 */
public class EntityManagerUtil {

	private static final String PERSISTENCE_UNIT_NAME = "myPersistence";
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static final EntityManager ENTITY_MANAGER;
	
	private EntityManagerUtil(){
	}
	
	//创建EntityManager实例
	static {
		ENTITY_MANAGER = ENTITY_MANAGER_FACTORY.createEntityManager();
	}
	
	/*
	 * 获取EntityManager实例
	 */
	public static synchronized EntityManager getEntityManagerInstance(){
		return ENTITY_MANAGER == null ? ENTITY_MANAGER_FACTORY.createEntityManager() : ENTITY_MANAGER;
	}
	
}
