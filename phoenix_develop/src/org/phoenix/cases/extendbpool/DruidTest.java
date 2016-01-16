package org.phoenix.cases.extendbpool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.phoenix.extend.druid.DruidUtil;

/**
 * 平台在执行用例的过程中对第三方数据库进行操作的方法
 * @author mengfeiyang
 *
 */
public class DruidTest {
	DruidUtil druid = new DruidUtil();
	@Test
	public void testSelect() throws SQLException {

		druid.config(
				"jdbc:mysql://localhost:3306/ykstimer?useUnicode=true&amp;characterEncoding=utf-8", 
				"root", 
				"root");
		ResultSet rs = druid.getQueryResultSet("select * from t_user where id = '8'");
		while(rs.next()){
			int level = rs.getInt(2);//根据列编号
			String name = rs.getString("name");//根据列名
			String password = rs.getString("password");//根据列名
			System.out.println(level +"  "+name+"  "+password);
		}
	}
    //update,insert,delete操作
	@Test
	public void testInsert() throws SQLException{
		druid.config(
				"jdbc:mysql://localhost:3306/ykstimer?useUnicode=true&amp;characterEncoding=utf-8", 
				"root", 
				"root");
        Connection conn = druid.getConnection();
        conn.createStatement().execute("insert into t_code values('41','test')");
        druid.startTransaction();
        druid.commit();
	}
	
	@Test
	public void testUpdate() throws SQLException{
		druid.config(
				"jdbc:mysql://localhost:3306/ykstimer?useUnicode=true&amp;characterEncoding=utf-8", 
				"root", 
				"root");
		int r = druid.getPreparedStatement("update t_code set detail='test2' where code='41';").executeUpdate();
        System.out.println(r==1?"更新成功":"更新失败");
        druid.startTransaction();
        druid.commit();
	}
	

}
