package org.phoenix.web.dao.impl;

import org.phoenix.basic.impl.BaseDao;
import org.phoenix.basic.paging.Pager;
import org.phoenix.web.dao.IUserDao;
import org.phoenix.web.model.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {


	@Override
	public User loadLoginUser(String username,String password) {
		String hql = "from User where username=? and password=?";
		return (User)this.queryObject(hql, new Object[]{username,password});
	}
	@Override
	public User loadByUserName(String username){
		String hql = "from User where username=?";
		return (User)this.queryObject(hql, username);
	}

	@Override
	public Pager<User> findUser() {
		return this.find("from User");
	}

}
