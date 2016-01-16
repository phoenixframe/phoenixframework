package org.phoenix.web.service.impl;

import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.phoenix.basic.paging.Pager;
import org.phoenix.web.dao.IUserDao;
import org.phoenix.web.exception.PhoenixException;
import org.phoenix.web.model.User;
import org.phoenix.web.service.IUserService;
import org.phoenix.web.util.SecurityUtil;
import org.phoenix.web.util.ShiroKit;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements IUserService {
	private IUserDao userDao;
	
	public IUserDao getUserDao() {
		return userDao;
	}
	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	

	@Override
	public void add(User user) {
		User tu = userDao.loadByUserName(user.getUsername());
		if(tu!=null)throw new PhoenixException("添加的用户对象已经存在，不能添加");
		if(ShiroKit.isEmpty(user.getUsername())||ShiroKit.isEmpty(user.getPassword())) {
			throw new RuntimeException("用户名或者密码不能为空！");
		}
		user.setPassword(ShiroKit.md5(user.getPassword(),user.getUsername()));
		userDao.add(user);
	}

	@Override
	public void delete(int id) {
		userDao.delete(id);
	}
   

	@Override
	public Pager<User> findUser() {
		return userDao.findUser();
	}

	@Override
	public User load(int id) {
		return userDao.load(id);
	}
   
	@Override
	public User login(String username, String password) {
		User user = userDao.loadByUserName(username);
		if(user==null) throw new UnknownAccountException("用户名或者密码出错");
		if(!user.getPassword().equals(ShiroKit.md5(password, username)))
			throw new IncorrectCredentialsException("用户名或者密码出错");
		return user;
	}
	@Override
	public void update(User user) {
		userDao.update(user);
	}
	@Override
	public void updatePwd(int uid, String oldPwd, String newPwd) {
		try {
			User u = userDao.load(uid);
			if(!SecurityUtil.md5(u.getUsername(),oldPwd).equals(u.getPassword())) {
				throw new PhoenixException("原始密码输入不正确");
			}
			u.setPassword(SecurityUtil.md5(u.getUsername(), newPwd));
			userDao.update(u);
		} catch (NoSuchAlgorithmException e) {
			throw new PhoenixException("更新密码失败:"+e.getMessage());
		}
	}

}
