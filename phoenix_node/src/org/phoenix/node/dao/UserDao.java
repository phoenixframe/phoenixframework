package org.phoenix.node.dao;

import org.phoenix.basic.impl.HibernateDaoImpl;

public class UserDao extends HibernateDaoImpl {
	
	public boolean getUser(String userName,String password){
		String sql  ="select userName,password from t_user where userName='"+userName+"' and password='"+password+"'";		
		return super.loadRowDatas(sql).size()>0;
	}
}
