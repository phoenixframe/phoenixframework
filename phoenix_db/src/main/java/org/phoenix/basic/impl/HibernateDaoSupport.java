package org.phoenix.basic.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.basic.utils.EntityManagerUtil;
/**
 * 通用的dao方法，基于Java的<code>EntityManager</code>实现
 * @author mengfeiyang
 *
 * @param <T>
 */
public class HibernateDaoSupport<T> implements IBaseDao<T>{
	private Class<?> clz;
	public Class<?> getClz() {
		if(clz==null) {
			//获取泛型的Class对象
			clz = ((Class<?>)
					(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}
	@Override
	public T add(T t) {
		EntityManager entityManager = EntityManagerUtil.getEntityManagerInstance();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(t);
		entityTransaction.commit();
		//entityManager.close();
		return t;
	}

	@Override
	public void update(T t) {
		EntityManager entityManager = EntityManagerUtil.getEntityManagerInstance();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.merge(t);
		entityTransaction.commit();
		//entityManager.close();		
	}

	@Override
	public void delete(int id) {
		EntityManager entityManager = EntityManagerUtil.getEntityManagerInstance();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.detach(getClz());
		entityTransaction.commit();
		//entityManager.close();			
	}

	@Override
	public T load(int id) {
		EntityManager entityManager = EntityManagerUtil.getEntityManagerInstance();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		@SuppressWarnings("unchecked")
		T t = (T) entityManager.find(getClz(), id);
		//entityManager.close();	
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> loadAll() {
		EntityManager entityManager = EntityManagerUtil.getEntityManagerInstance();
		List<T> t = entityManager.createQuery("from "+getClz()).getResultList();
		//entityManager.close();
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> loadAll(String hql) {
		EntityManager entityManager = EntityManagerUtil.getEntityManagerInstance();
		List<T> t = entityManager.createQuery(hql).getResultList();
		//entityManager.close();
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public T load(String hql){
		EntityManager entityManager = EntityManagerUtil.getEntityManagerInstance();
		T t = (T) entityManager.createQuery(hql).getSingleResult();
		//entityManager.close();
		return t;
	}
	@Override
	public void addBatchData(List<T> t) {
		EntityManager entityManager = EntityManagerUtil.getEntityManagerInstance();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		for(T it : t){
			entityManager.persist(it);
		}
		entityTransaction.commit();		
	}

}
