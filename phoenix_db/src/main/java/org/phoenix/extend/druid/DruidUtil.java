package org.phoenix.extend.druid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 


import com.alibaba.druid.pool.DruidDataSource;
/**
 * 对Druid的封装，便于使用
 * @author mengfeiyang
 *
 */
public class DruidUtil{
    private DruidDataSource dataSource  = new DruidDataSource();
    private Connection conn = null;
    private PreparedStatement ps =null;
    private ResultSet rs = null;
    
    /**
     * 初始化配置，其他的一些初始配置：
     *  dataSource.setInitialSize(2);
        dataSource.setMaxActive(20);
        dataSource.setMinIdle(0);
        dataSource.setMaxWait(60000);
        dataSource.setPoolPreparedStatements(false);
     * @param url
     * @param username
     * @param password
     */
    public DruidUtil config(String url,String username, String password){
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return this;
    }
 
    /**
     * 获取数据连接
     * @return
     */
    public Connection getConnection(){
        try{
            conn = dataSource.getConnection();
        }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    /***获取当前线程上的连接开启事务*/
    public void startTransaction(){
        conn=getConnection();//首先获取当前线程的连接
        try{
            conn.setAutoCommit(false);//开启事务
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //提交事务
    public void commit(){
        try{
           conn=getConnection();//从当前线程上获取连接if(conn!=null){//如果连接为空，则不做处理
           conn.commit();//提交事务
        }catch(Exception e){
            e.printStackTrace();
        }
    }
 
 
    /***回滚事务*/
    public void rollback(){
        try{
            conn=getConnection();//检查当前线程是否存在连接
            conn.rollback();//回滚事务
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ResultSet getQueryResultSet(String sql){
		try {
			ps = getConnection().prepareStatement(sql);
			rs = ps.executeQuery(sql);
	        return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public PreparedStatement getPreparedStatement(String sql){
		try {
			ps = getConnection().prepareStatement(sql);
	        return ps;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public boolean executeSql(String sql){
		try {
			ps = getConnection().prepareStatement(sql);
			boolean result = ps.execute(sql);
	        return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }
    /***关闭连接*/
    public void close(){
        try{
        	if(ps != null)ps.close();
        	if(rs != null)rs.close();
            if(conn!=null)conn.close();
            if(dataSource != null)dataSource.close();
        }catch(SQLException e){
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}

