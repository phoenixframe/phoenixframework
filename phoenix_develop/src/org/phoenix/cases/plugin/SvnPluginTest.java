package org.phoenix.cases.plugin;

import java.util.LinkedList;
import java.util.List;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.plugin.model.SvnLogModel;
import org.phoenix.plugins.ISvnClient;

/**
 * svn客户端测试
 * @author mengfeiyang
 *
 */
public class SvnPluginTest extends WebElementActionProxy{
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init("",arg0);
		ISvnClient svnClient = webProxy.svnClient().configSvnClient(
				"http://svn.svnkit.com/repos/svnkit/trunk/gradlew", 
				"anonymous", 
				"anonymous", 
				""
				);
		List<SvnLogModel> logList = svnClient.displaySvnLog();
		for(SvnLogModel s : logList){
			System.out.println(s.getAuthor());
			System.out.println(s.getMessage());
			for(String sl : s.getChangedPaths()){
				System.out.println(sl);
				webProxy.checkPoint().checkNotNull(sl);
			}
		}
		return getUnitLog(); 
	}
	public static void main(String[] args) {
		SvnPluginTest p = new SvnPluginTest();
		LinkedList<UnitLogBean> ll = p.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}
}
