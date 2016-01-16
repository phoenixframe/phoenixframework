package org.phoenix.basic.dao;

import java.util.List;


/**
 * 公共的DAO处理对象，这个对象中包含了Hibernate的所有基本操作和对SQL的操作
 * @author mengfeiyang
 *
 */
public interface IBaseDao<T> {
	/*
	 * 添加对象
	 * @param t
	 * @return
	 */
	public T add(T t);
	/*
	 * 更新对象
	 */
	public void update(T t);
	/*
	 * 根据id删除对象
	 */
	public void delete(int id);
	/*
	 * 根据id加载对象
	 */
	public T load(int id);
	
	/*
	 * 加载多条数据
	 */
	public List<T> loadAll();
	
	/*
	 * 根据Hql加载数据
	 * 
	 */
	public List<T> loadAll(String hql);
	
	/*
	 * 批量插入数据
	 */
	public void addBatchData(List<T> t);
	
}

