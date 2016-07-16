package org.phoenix.cases.lianmeng;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.proxy.ActionProxy;

/**
 * 使用phoenix做数据分离后的测试案例
 * 并根据检查点的结果作判断，检查点的结果会记录到日志中<br>
 * 在实际使用中，数据分离（定位数据和输入的数据）可以混合使用，即可以使用存储在数据库中的数据，也可以使用保存在代码中的数据
 * @author mengfeiyang
 *
 */
public class ShuJuFenLi extends ActionProxy{
	public ShuJuFenLi() {
	}
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		HashMap<InterfaceBatchDataBean, HashMap<String, String>> datas = phoenix.commonAPI().loadWebCaseDatas("SVNwebtest7");
		for(Entry<InterfaceBatchDataBean, HashMap<String, String>> data : datas.entrySet()){
			HashMap<String,String> dataBlocks = data.getValue();
		//webProxy.setChromeDriverExePath("C:\\Users\\mengfeiyang\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
		//webProxy.openNewWindowByChrome("http://lianmeng.360.cn/account");
		phoenix.webAPI().setFirefoxExePath(dataBlocks.get("FirefoxPath"));
		phoenix.webAPI().openNewWindowByFirefox(dataBlocks.get("首页地址"));
		phoenix.webAPI().webElement("uname").setText(dataBlocks.get("用户名"));
		phoenix.webAPI().webElement("passwd").setText(dataBlocks.get("密码"));
		phoenix.webAPI().webElement("验证码").setText(dataBlocks.get("验证码"));
		phoenix.webAPI().webElement("登陆按钮").click();
		String errorMsg = phoenix.webAPI().webElement("错误信息").getText();
		System.out.println(errorMsg);
		phoenix.webAPI().sleep(1000);
		String r = phoenix.checkPoint().checkIsEqual("", errorMsg);
		if(r != null){
			phoenix.webAPI().webElement("马上注册").click();
			phoenix.webAPI().webElementLinkFinder(".panel-content").findElementByLinkText("导航联盟").click();
			phoenix.webAPI().sleep(1000);
			phoenix.webAPI().webElement("注册用户名").setText(dataBlocks.get("User_Name"));
			phoenix.webAPI().webElement("注册密码").setText(dataBlocks.get("Pwd"));
			phoenix.webAPI().webElement("确认密码").setText(dataBlocks.get("Pwd"));
			phoenix.webAPI().webElement("注册公司名").setSelected(true);
			phoenix.webAPI().webElement("公司名称").setText(dataBlocks.get("公司名称"));
			phoenix.webAPI().webElement("公司ID").setText(dataBlocks.get("公司ID"));
			phoenix.webAPI().webElement("联系人").setText(dataBlocks.get("联系人电话"));
			phoenix.webAPI().webElement("联系人电话").setText(dataBlocks.get("电话号码"));
			phoenix.webAPI().webElement("验证码2").setText(dataBlocks.get("Pwd"));
			phoenix.webAPI().webElement("verifyCode").setText("1234");
			phoenix.webAPI().webElement("邮箱").setText(dataBlocks.get("邮箱"));
			phoenix.webAPI().webElement("已读复选框").setSelected(true);
			phoenix.webAPI().sleep(1000);
		} else {
			phoenix.webAPI().webElement("业务管理", LocatorType.LINKTEXT).click();
			phoenix.webAPI().webElement("//*[@id=\"list_view\"]/div[2]/table/tbody/tr[1]/td[4]/a", LocatorType.XPATH).click();
		}
		phoenix.webAPI().closeWindow();
		}
		
		return getUnitLog();
	}
	
	public static void main(String[] args) {
		ShuJuFenLi yw = new ShuJuFenLi();
		LinkedList<UnitLogBean> ll = yw.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}
	
}
