package org.phoenix.cases.lianmeng;

import java.util.LinkedList;
import java.util.Random;

import org.phoenix.action.WebElementActionProxy;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

/**
 * 用例示例：
 * 1、组装执行流方式执行流程
 * 2、使用普通方式
 * 3、插入检查点，根据检查点结果选择分支
 * @author mengfeiyang
 *
 */
public class YeWuGuanLiCase extends WebElementActionProxy{
	private static String caseName = "报告查看";
	public YeWuGuanLiCase() {
	}
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init(caseName,arg0);

		testFlow("开始->使用firefox->真登陆->业务管理->查看->关闭");
		
		//testFlow("开始->使用firefox->真登陆->业务管理->添加网址->修改->查看->关闭");
		//testFlow("开始->使用firefox->真登陆->业务管理->修改->添加网址->查看->关闭");
		//testFlow("开始->使用firefox->真登陆->业务管理->修改->查看->关闭");
		//testFlow("开始->使用chrome->假登陆->注册->关闭");
		
		//testCommon();
		
		return getUnitLog();
	}
	
	public void testCommon(){
		start();
		selectDriver("firefox");
		login(true);
		String errorMsg = webProxy.webElement("//*[@id=\"login_form\"]/p/em[2]", LocatorType.XPATH).getText();
		System.out.println(errorMsg);
		webProxy.sleep(1000);
		String r = webProxy.checkPoint().checkIsEqual("", errorMsg);
		if(!r.contains("null")){
			regist();
		} else {
			yewuguanli();
			addSite();
			modify();
			look();
			
		}
		close();
	}

	/**
	 * 开始
	 * 对环境初始化
	 */
	public void start(){
		webProxy.setChromeDriverExePath("C:\\Users\\mengfeiyang\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
		webProxy.setFirefoxExePath("D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
	}
	
	/**
	 * 选择驱动
	 * @param driver
	 */
	public void selectDriver(String driver){
		String url = "http://lianmeng.360.cn/account";
		if(driver.equals("chrome"))webProxy.openNewWindowByChrome(url);
		else if(driver.equals("firefox"))webProxy.openNewWindowByFirefox(url);
		else if(driver.equals("ie"))webProxy.openNewWindowByIE(url);
		else webProxy.openNewWindowByPhantomJs(url);
	}
	
	/**
	 * 执行流选型
	 * @param flow
	 */
	public void testFlow(String flow){
		String[] flows = flow.split("->");
		for(String f : flows){
			switch(f){
			case "开始":start();break;
			case "使用chrome":selectDriver("chrome");break;
			case "使用firefox":selectDriver("firefox");break;
			case "使用ie":selectDriver("ie");break;
			case "使用phantomjs":selectDriver("phantomjs");break;
			case "真登陆":login(true);break;
			case "假登陆":login(false);break;
			case "添加网址":addSite();break;
			case "注册":regist();break;
			case "修改":modify();break;
			case "查看":look();break;
			case "业务管理":yewuguanli();break;
			case "关闭":close();break;
			default:;
			}
		}
	}
	/**
	 * 进入业务管理菜单
	 */
	public void yewuguanli(){
		webProxy.webElement("业务管理", LocatorType.LINKTEXT).click();
		webProxy.sleep(1000);
	}
	
	/**
	 * 修改信息
	 */
	public void modify(){
		tableOper(6,"www.susu.com",1);	
		String newValue = "test"+new Random().nextInt(1000);
		webProxy.webElement("website", LocatorType.NAME).setText(newValue);
		webProxy.webElement("//*[@id=\"edit_view\"]/form/dl[8]/dd/button", LocatorType.XPATH).click();
		
		webProxy.webElementLinkFinder(".panel-content",null).findElementByLinkText("网站列表").click(); 
		webProxy.sleep(2000);
	}
	
	/**
	 * 操作table
	 * @param cellSize
	 * @param expect
	 * @param oper
	 */
	private void tableOper(int cellSize,String expect,int oper){
		for(int tr=1;tr<cellSize;tr++){
			String t = webProxy.webElement("//*[@class=\"data-list\"]/tbody/tr["+tr+"]/td[1]", LocatorType.XPATH).getText();
			if(t.trim().equals(expect)){
				webProxy.webElement("//*[@class=\"data-list\"]/tbody/tr["+tr+"]/td[4]/a["+oper+"]", LocatorType.XPATH).click();
				break;
			}
		}
	}
	
	/**
	 * 查看信息
	 */
	public void look(){
		//webProxy.webElement("业务管理", LocatorType.LINKTEXT).click();
		tableOper(6,"www.susu.com",1);			
		webProxy.sleep(2000);
	}
	
	/**
	 * 新人注册
	 */
	private void regist(){
		webProxy.webElement("马上注册！", LocatorType.LINKTEXT).click();
		webProxy.webElementLinkFinder(".panel-content",null).findElementByLinkText("导航联盟").click();
		webProxy.sleep(1000);
		webProxy.webElement("#user_name", null).setText("Test123");
		webProxy.webElement("#passwd", null).setText("1111");
		webProxy.webElement("//*[@id=\"step1\"]/form/dl[3]/dd/input", LocatorType.XPATH).setText("1111");
		webProxy.webElement("#ft_company", null).setSelected(true);
		webProxy.webElement("company", LocatorType.NAME).setText("111111111111");
		webProxy.webElement("taxID", LocatorType.NAME).setText("12312");
		webProxy.webElement("contactPerson",LocatorType.NAME).setText("222222222222");
		webProxy.webElement("#cell_phone",null).setText("156522755555");
		webProxy.webElement("#verifyCode", null).setText("1234");
		webProxy.webElement("verifyCode", LocatorType.NAME).setText("1234");
		webProxy.webElement("email", LocatorType.NAME).setText("123@123.com");
		webProxy.webElement("tos", LocatorType.NAME).setSelected(true);
		webProxy.sleep(1000);
	}
	/**
	 * 登陆模块
	 * @param flag
	 */
	private void login(boolean flag){
		webProxy.webElement("#uname",null).setText("app_susu002");
		if(flag)webProxy.webElement("passwd", LocatorType.NAME).setText("123456");
		else webProxy.webElement("passwd", LocatorType.NAME).setText("11111");
		webProxy.webElement("verifyCode", LocatorType.NAME).setText("6g6m");
		webProxy.webElement("//*[@id=\"login_form\"]/dl[4]/dd/button",LocatorType.XPATH).click();
		
	}
	
	/**
	 * 网站添加
	 */
	public void addSite(){
		webProxy.webElement("添加网站", LocatorType.LINKTEXT).click();
		webProxy.webElement("#domain_name", null).setText("www.cewan.la");
		webProxy.webElement("#verify_site", null).click();
		webProxy.webElement("#start_verify", null).click();
		String r = webProxy.webElement(".status-ok", null).getText();
		webProxy.checkPoint().checkIsEqual("已验证通过", r);
		webProxy.webElement("website", LocatorType.NAME).setText("phoenix自动化");
		webProxy.webElement("siteDesc", LocatorType.NAME).setText("自动化测试平台");
		webProxy.webElement("siteType", LocatorType.NAME).selectOption("功能性");
		webProxy.webElement("pvType", LocatorType.NAME).selectOption("小于一万");
		webProxy.webElement("icpRecord", LocatorType.NAME).setText("没有备案啦");
		webProxy.webElement("#search_union", null).setSelected(true);
		webProxy.webElement("#tos", null).setSelected(true);
		webProxy.webElement("取消", LocatorType.LINKTEXT).click();
		webProxy.sleep(1000);
	}
	/**
	 * 关闭网站
	 */
	private void close(){
		webProxy.closeWindow();
	}
	
	public static void main(String[] args) {
		YeWuGuanLiCase yw = new YeWuGuanLiCase();
		LinkedList<UnitLogBean> ll = yw.run(new CaseLogBean());
		for(UnitLogBean l : ll){
			System.out.println(l.getContent());
		}
	}
	
}
