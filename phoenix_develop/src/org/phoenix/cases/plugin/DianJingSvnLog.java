package org.phoenix.cases.plugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.phoenix.extend.druid.DruidUtil;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;
import org.phoenix.utils.CommandExecutor;

/**
 * svn客户端
 * @author mengfeiyang
 *
 */
public class DianJingSvnLog extends ActionProxy{
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		DruidUtil dr = new DruidUtil();
		HashMap<InterfaceBatchDataBean, HashMap<String, String>> datas = phoenix.commonAPI().loadWebCaseDatas("SVNwebtest7");
		for(Entry<InterfaceBatchDataBean, HashMap<String, String>> data : datas.entrySet()){
			HashMap<String,String> dataBlocks = data.getValue();
			String insertSQL = "update t_web_data set dataContent = '?' where dataName='svnVersion'";
			String getSvnVersion = "select dataContent from t_web_data where dataName='svnVersion'";
			dr.config("jdbc:mysql://10.16.57.106:3306/phoenix_gui?useUnicode=true&characterEncoding=utf-8", "phoenix", "phoenix");
	    	String url = dataBlocks.get("url");
	    	String eUrl = dataBlocks.get("eurl");
	    	String username = dataBlocks.get("username");
	    	String password = dataBlocks.get("password");
	    	System.out.println("-----"+url);
	    	String r = CommandExecutor.executeLinuxOrWindowsCmd("svn info "+url);
	    	String[] rs = r.split("\n");
	    	String lastVersion = null;
			try {
				ResultSet res = dr.getQueryResultSet(getSvnVersion);
				while(res.next()){
					lastVersion = res.getString("dataContent").trim();
				}
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println(">>>>>>>>>>>>>>"+lastVersion);
	    	for(String s : rs){
	    		if(s.startsWith("Last Changed Rev")){
	    			String newVersion = s.split(":")[1].trim();
	    			if(!lastVersion.equals(newVersion)){
	    				System.out.println("lastVersion:"+lastVersion);
	    				System.out.println("getVersion:"+s.split(":")[1]);
	    				int resCode = phoenix.interfaceAPI().getResponseByHttpClientWithGet("http://e.360.cn/").getStatusLine().getStatusCode();
	    				System.out.println(resCode);
	    				phoenix.checkPoint().checkIsEqual(200, resCode);
	    				dr.executeSql(insertSQL.replace("?", newVersion));
	    			} else {
	    				phoenix.checkPoint().checkNotNull("代码最近无新版本更新");
	    			}
	    		}
	    	}
	    	
	    	dr.close();
		}
		return getUnitLog(); 
	}
	
	public static void main(String[] args) {
		DianJingSvnLog p = new DianJingSvnLog();
		LinkedList<UnitLogBean> ll = p.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}
}