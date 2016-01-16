package org.phoenix.web.service;

import org.phoenix.basic.paging.Pager;
import org.phoenix.web.model.User;

/**
 * 用户信息操作服务
 * @author mengfeiyang
 *
 */
public interface IUserService {
	/*
	 * 添加用户，需要判断用户名是否存在，如果存在抛出异常
	 */
	public void add(User user);
	/*
	 * 删除用户，注意需要把用户和角色和组的对应关系删除
	 * 如果用户存在相应的文章不能删除
	 */
	public void delete(int id);
	
	public void update(User user);
	/*
	 * 更新密码方法
	 */
	public void updatePwd(int uid,String oldPwd,String newPwd);
	
	/*
	 * 列表用户
	 */
	public Pager<User> findUser();
	/*
	 * 获取用户信息
	 */
	public User load(int id);
	
	public User login(String username,String password);
}
