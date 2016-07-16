package org.phoenix.web.dao;

import org.phoenix.basic.dao.IBaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.web.model.User;
/**
 * 用户信息操作接口
 * @author mengfeiyang
 *
 */
public interface IUserDao extends IBaseDao<User>{
	
	/*
	 * 根据用户名获取用户对象
	 */
	public User loadLoginUser(String username,String password);
	
	/*
	 * 根据用户名获取用户对象
	 */
	public User loadByUserName(String username);
	
	/*
	 * 获取用户的信息及分页信息
	 */
	public Pager<User> findUser();
}
