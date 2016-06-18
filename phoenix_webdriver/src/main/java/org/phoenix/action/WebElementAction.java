package org.phoenix.action;

import static com.codeborne.selenide.Selenide.actions;
import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getAndCheckWebDriver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;
import io.selendroid.client.DriverCommand;
import io.selendroid.client.MultiTouchAction;
import io.selendroid.client.MultiTouchScreen;
import io.selendroid.client.ScreenBrightness;
import io.selendroid.client.SelendroidDriver;
import io.selendroid.client.TouchAction;
import io.selendroid.client.TouchActionBuilder;
import io.selendroid.client.adb.AdbConnection;
import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.server.common.utils.CallLogEntry;
import io.selendroid.standalone.SelendroidConfiguration;
import io.selendroid.standalone.SelendroidLauncher;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.phoenix.aop.CheckPointInvocationHandler;
import org.phoenix.aop.PhoenixLogger;
import org.phoenix.aop.WebApiInvocationHandler;
import org.phoenix.api.action.APIAction;
import org.phoenix.api.action.WebAPIAction;
import org.phoenix.api.utils.JsonPaser;
import org.phoenix.dao.InterfaceBatchDataDao;
import org.phoenix.dao.InterfaceDataDao;
import org.phoenix.dao.LocatorDao;
import org.phoenix.enums.LocatorType;
import org.phoenix.mobile.powertools.GetXml;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.model.InterfaceDataBean;
import org.phoenix.model.LocatorBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.plugins.IFtpClient;
import org.phoenix.plugins.IImageReader;
import org.phoenix.plugins.ISvnClient;
import org.phoenix.plugins.ITelnetClient;
import org.phoenix.utils.SystemInfo;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.google.common.io.Files;

/**
 * 元素的操作类，包括对Android和WEB元素
 * @author mengfeiyang
 *
 */
public class WebElementAction extends WebElementLocator implements ElementAction{
	private LocatorBean locatorBean;
	private LinkedList<UnitLogBean> unitLog;
	private ElementAction webProxy;
	private SelendroidConfiguration config;
	private SelendroidLauncher selendroidServer;
	private SelendroidCapabilities capa;
	private SelendroidDriver selendroidDriver;
	private LocatorDao locatorDao = new LocatorDao();
	private InterfaceBatchDataDao ibatchDao = new InterfaceBatchDataDao();
	private InterfaceDataDao idataDao = new InterfaceDataDao();
	private HashMap<String,LocatorBean> locators;
	private CaseLogBean caseLogBean;
	private String ChromeDriverPath;
	private String FirefoxPath;
	private ICheckPoint checkPoint;

	public WebElementAction(LinkedList<UnitLogBean> unitLog) {
		this.unitLog = unitLog;
		new PhoenixLogger();
		new LoadPhoenixPlugins();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#loadWebCaseDatas(int)
	 */
	@Override
	public HashMap<InterfaceBatchDataBean,HashMap<String,String>> loadWebCaseDatas(int caseId){
		HashMap<InterfaceBatchDataBean,HashMap<String,String>> webDatas = new HashMap<InterfaceBatchDataBean,HashMap<String,String>>();
		LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> datas = this.loadInterfaceDatas(caseId);
		for(Entry<InterfaceBatchDataBean,List<InterfaceDataBean>> data : datas.entrySet()){
			HashMap<String,String> dataBlocks = new HashMap<String,String>();
			for(InterfaceDataBean idata : data.getValue()){
				dataBlocks.put(idata.getDataName(), idata.getDataContent());
			}
			webDatas.put(data.getKey(), dataBlocks);
		}
		return webDatas;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#loadWebCaseDatas(java.lang.String)
	 */
	@Override
	public HashMap<InterfaceBatchDataBean,HashMap<String,String>> loadWebCaseDatas(String caseName){
		HashMap<InterfaceBatchDataBean,HashMap<String,String>> webDatas = new HashMap<InterfaceBatchDataBean,HashMap<String,String>>();
		LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> datas = this.loadInterfaceDatas(caseName);
		for(Entry<InterfaceBatchDataBean,List<InterfaceDataBean>> data : datas.entrySet()){
			HashMap<String,String> dataBlocks = new HashMap<String,String>();
			for(InterfaceDataBean idata : data.getValue()){
				dataBlocks.put(idata.getDataName(), idata.getDataContent());
			}
			webDatas.put(data.getKey(), dataBlocks);
		}
		return webDatas;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#addLocator(int, org.phoenix.model.CaseLogBean)
	 */
	@Override
	public void addLocator(int caseId,CaseLogBean caseLogBean){
		setCaseLogBean(caseLogBean);
		locators = new HashMap<String,LocatorBean>();
		List<LocatorBean> llist = locatorDao.getModelList(caseId);
		for(LocatorBean locatorBean : llist){
			locators.put(locatorBean.getLocatorDataName(), locatorBean);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#addLocator(java.lang.String, org.phoenix.model.CaseLogBean)
	 */
	@Override
	public void addLocator(String caseName,CaseLogBean caseLogBean){
		setCaseLogBean(caseLogBean);
		locators = new HashMap<String,LocatorBean>();
		List<LocatorBean> list = locatorDao.getModelList(caseName);
		for(LocatorBean locatorBean : list){
			locators.put(locatorBean.getLocatorDataName(), locatorBean);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#loadInterfaceDatas(int)
	 */
	@Override
	public LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> loadInterfaceDatas(int caseId){
		List<InterfaceBatchDataBean> iBatchList = ibatchDao.getModelList(caseId);
		LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> iBatchDataMap = new LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>>();
		for(InterfaceBatchDataBean iBatch : iBatchList){
			iBatchDataMap.put(iBatch, idataDao.getModelList(iBatch.getId()));
		}
		return iBatchDataMap;
	}
	/**
	 * 根据给定的用例的name，获取该用例下所有的数据
	 */
	@Override
	public LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> loadInterfaceDatas(String caseName){
		List<InterfaceBatchDataBean> iBatchList = ibatchDao.getModelList(caseName);
		LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> iBatchDataMap = new LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>>();
		for(InterfaceBatchDataBean iBatch : iBatchList){
			iBatchDataMap.put(iBatch, idataDao.getModelList(iBatch.getId()));
		}
		return iBatchDataMap;
	}
	/**
	 * 添加聚合用例。根据用例的名称<br>
	 * 聚合用例用于执行当前用例中嵌入的外部用例,仅支持一级调用
	 */
	@Override
	public void addAggregateCase(String caseName){
		
	}
	/**
	 * 添加聚合用例。根据用例的id<br>
	 * 聚合用例用于执行当前用例中嵌入的外部用例,仅支持一级调用
	 */
	@Override
	public void addAggregateCase(int caseId){
		
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#executeScript(java.lang.String, java.lang.Object[])
	 */
	public Object executeScript(String script, Object...args){
		return Selenide.executeJavaScript(script, args);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#executeAsyncScript(java.lang.String, java.lang.Object[])
	 */
	public Object executeAsyncScript(String script, Object...args){
		if(getCurrentDriver() !=null ){
			JavascriptExecutor je = (JavascriptExecutor)getCurrentDriver();
			return je.executeAsyncScript(script, args);
		}else {
			return "Fail：js执行失败，currentDriver为null";
		}
	}
	
	public CaseLogBean getCaseLogBean() {
		return caseLogBean;
	}
	public void setCaseLogBean(CaseLogBean caseLogBean) {
		this.caseLogBean = caseLogBean;
	}
	@Override
	public ElementAction webElement(){
		return webProxy;
	}
	/**
	 * 直接使用定位信息而无需将其先录入数据库
	 * @param locatorData
	 * @param locatorType 如果为null，则默认为locatorType 为CSS。Class和id可直接作为Css定位
	 * @return
	 */
	@Override
	public ElementAction webElement(String locatorData,LocatorType locatorType){
		if(locatorType == null)locatorBean = new LocatorBean(locatorData);
		else locatorBean = new LocatorBean(locatorData,locatorType);
		return webProxy;
	}
	/**
	 * 链式查询方法，直接调用了SelenideElement，调用了此方法后，后续的操作将不会被记录日志
	 */
	@Override
	public SelenideElement webElementLinkFinder(String locatorDataName){
		LocatorBean locatorBean = locators.get(locatorDataName);
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType());
	}
	
	/**
	 * 链式查询方法，直接调用了SelenideElement，调用了此方法后，后续的操作将不会被记录日志
	 */
	@Override
	public SelenideElement webElementLinkFinder(String locatorData,LocatorType locatorType){
		if(locatorType == null)locatorType = LocatorType.CSS;
		return WebElement(locatorData,locatorType);
	}
	
	/**
	 * 指定一个定位信息的标识，需要先将其录入数据库之后才会有该标识
	 */
	@Override
	public ElementAction webElement(String name){
		locatorBean = locators.get(name);	
		return webProxy;
	}
	
	/**
	 * 不使用任何定位信息时，如close方法
	 * @return
	 */
	public ElementAction getWebProxy() {
		return webProxy;
	}
	@Override
	public void setWebProxy(ElementAction webProxy) {
		this.webProxy = webProxy;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#saveImgToLocal(com.codeborne.selenide.SelenideElement, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean saveImgToLocal(SelenideElement element,String engine,String localFilePath){
		try{
			Point selenPoint = element.getLocation();
			Dimension selenDim = element.getSize();
			Robot r = new Robot();
			Rectangle rt = new Rectangle();
			switch (engine) {
			case "IEDriver":
				rt.setBounds(selenPoint.getX() - 1, selenPoint.getY() + 55,
						selenDim.getWidth(), selenDim.getHeight());
				break;
			case "FirefoxDriver":
				rt.setBounds(selenPoint.getX(), selenPoint.getY() + 64,
						selenDim.getWidth(), selenDim.getHeight());
				break;
			case "ChromeDriver":
				rt.setBounds(selenPoint.getX(), selenPoint.getY(),
						selenDim.getWidth(), selenDim.getHeight());
				break;
			default:;
			}
	
			BufferedImage bfi = r.createScreenCapture(rt);
			File f = new File(localFilePath);
			ImageIO.write(bfi, Files.getFileExtension(localFilePath), f);
			return f.exists();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#saveImgToLocal(int, int, int, int, java.lang.String)
	 */
	@Override
	public boolean saveImgToLocal(int x,int y,int picWidth,int picHeight,String savePath){
		try{
			Robot r = new Robot();
			Rectangle rt = new Rectangle();
			rt.setBounds(x,y,picWidth, picHeight);
			BufferedImage bfi = r.createScreenCapture(rt);
			File f = new File(savePath);
			ImageIO.write(bfi, Files.getFileExtension(savePath), f);
			return f.exists();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 产生检查点代理
	 */
	@Override
	public ICheckPoint checkPoint(){
		if(checkPoint == null)return checkPoint = (ICheckPoint)new CheckPointInvocationHandler(new CheckPoint(),unitLog,caseLogBean).getProxy();
		return checkPoint;
	}
	/**
	 * 产生webAPI接口代理
	 */
	@Override
	public APIAction webAPIAction(){
		APIAction webAPIAction = (APIAction) new WebApiInvocationHandler(new WebAPIAction(),unitLog,caseLogBean).getProxy();
		return webAPIAction;
	}
	/**
	 * svn客户端，用于对svn做操作。如获取提交日志，提交文件，更新文件等等。
	 * @return
	 */
	@Override
	public ITelnetClient telnetClient(){
		return (ITelnetClient) LoadPhoenixPlugins.getPlugin("TelnetClient");
	}
	/**
	 * svn客户端，用于对svn做操作。如获取提交日志，提交文件，更新文件等等。
	 * @return
	 */
	@Override
	public ISvnClient svnClient(){
		return (ISvnClient) LoadPhoenixPlugins.getPlugin("SvnClient");
	}
	/**
	 * 图片解析，用于识别图片上的字符。可直接对网络图片或本地图片进行读取
	 * @return
	 */
	@Override
	public IImageReader imageReader(){
		return (IImageReader) LoadPhoenixPlugins.getPlugin("ImgReader");
	}
	/**
	 * ftp客户端，用于操作ftp服务器，如从ftp服务器下载文件和上传本地文件到服务器等操作。
	 * @return
	 */
	@Override
	public IFtpClient ftpClient(){
		return (IFtpClient) LoadPhoenixPlugins.getPlugin("FtpClient");
	}

	@Override
	public WebDriver getCurrentDriver(){
		return getWebDriver();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#openNewWindowByHttpUnit(java.lang.String)
	 */
	public void openNewWindowByHtmlUnit(String url,boolean jsEnable,BrowserVersion version){
		caseLogBean.setEngineType("HttpUnitDriver");
		HtmlUnitDriver htmlUnit = new HtmlUnitDriver(version);
		htmlUnit.setJavascriptEnabled(jsEnable);
		setWebDriver(htmlUnit);
		open(url);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#openNewWindowByHttpUnit(java.lang.String, java.lang.String, int)
	 */
	public void openNewWindowByHtmlUnit(String url,boolean jsEnable,BrowserVersion version,String hostIP,int hostPort){
		caseLogBean.setEngineType("HttpUnitDriver with Proxy "+hostIP+":"+hostPort);
		HtmlUnitDriver htmlUnit = new HtmlUnitDriver(version);
		htmlUnit.setProxy(hostIP, hostPort);
		htmlUnit.setJavascriptEnabled(jsEnable);
		setWebDriver(htmlUnit);
		open(url);
	}
	@Override
	public void openNewWindowByPhantomJs(String url,String hostIP,int hostPort){
		caseLogBean.setEngineType("PhantomJsDriver with proxy "+hostIP+":"+hostPort);
		DesiredCapabilities sCaps = new DesiredCapabilities();
        sCaps.setJavascriptEnabled(true);
        if(SystemInfo.isWindows()){
        	sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, WebElementAction.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs.exe");
            sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY, WebElementAction.class.getResource("/").getPath().replace("%20", " ")+"drivers/js/main.js");
            PhoenixLogger.info("Use Driver："+WebElementAction.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs.exe");
        } else {
        	sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, WebElementAction.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs");
        	PhoenixLogger.info("Use Driver："+WebElementAction.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs");
        }
        ArrayList<String> cliArgsCap = new ArrayList<String>();
        cliArgsCap.add("--web-security=false");
        cliArgsCap.add("--ssl-protocol=any");
        cliArgsCap.add("--ignore-ssl-errors=true");
        sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
        sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, new String[]{"--logLevel=INFO"});
		Proxy proxy = new Proxy(); 
		proxy.setHttpProxy(hostIP+":"+hostPort);
		sCaps.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true); 
		sCaps.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true); 
		System.setProperty("http.nonProxyHosts", "localhost"); 
		sCaps.setCapability(CapabilityType.PROXY, proxy);
        setWebDriver(new PhantomJSDriver(sCaps));
        open(url);
	}
	@Override
	public void openNewWindowByPhantomJs(String url){
		caseLogBean.setEngineType("PhantomJsDriver");
		DesiredCapabilities sCaps = new DesiredCapabilities();
        sCaps.setJavascriptEnabled(true);
        if(SystemInfo.isWindows()){
        	sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, WebElementAction.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs.exe");
            sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY, WebElementAction.class.getResource("/").getPath().replace("%20", " ")+"drivers/js/main.js");
            PhoenixLogger.info("Use Driver："+WebElementAction.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs.exe");
        } else {
        	sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, WebElementAction.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs");
        	PhoenixLogger.info("Use Driver："+WebElementAction.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs");
        }
        ArrayList<String> cliArgsCap = new ArrayList<String>();
        cliArgsCap.add("--web-security=false");
        cliArgsCap.add("--ssl-protocol=any");
        cliArgsCap.add("--ignore-ssl-errors=true");
        sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
        sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, new String[]{"--logLevel=INFO"});
        
        setWebDriver(new PhantomJSDriver(sCaps));
        open(url);
	}
	//WebElementAction.class.getResource("/").getPath().replace("%20", " ")
	/**
	 * 此方法不适用于Linux
	 */
	@Override
	public void openNewWindowByIE(String url){
			caseLogBean.setEngineType("IEDriver");
			System.setProperty("webdriver.ie.driver", WebElementAction.class.getResource("/").getPath().replace("%20", " ")+"drivers/IEDriverServer"+SystemInfo.getArch()+".exe");
			setWebDriver(new InternetExplorerDriver());
			open(url);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#openNewWindowByIE(java.lang.String, java.lang.String, int)
	 */
	
	@Override
	public void openNewWindowByIE(String url,String hostIP,int hostPort){
			caseLogBean.setEngineType("IEDriver with proxy "+hostIP+":"+hostPort);
			System.setProperty("webdriver.ie.driver", WebElementAction.class.getResource("/").getPath().replace("%20", " ")+"drivers/IEDriverServer"+SystemInfo.getArch()+".exe");
			setWebDriver(new InternetExplorerDriver(getProxyCap(hostIP,hostPort)));
			open(url);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#setChromeDriverExePath(java.lang.String)
	 */
	@Override
	public void setChromeDriverExePath(String path){
		ChromeDriverPath = path;
	}
	/**
	 * 如果打不开Chrome浏览器或或者报异常，应先使用 setChromeDriverExePath(String path)方法指定chromedriver.exe路径<br>
	 * Linux环境下无需设置chrome的安装路径。建议chrome安装在默认路径下<br>
	 * 该程序的路径必须和Chrome.exe在同一目录下
	 */
	@Override
	public void openNewWindowByChrome(String url) {
		caseLogBean.setEngineType("ChromeDriver");
		if(SystemInfo.isWindows())System.setProperty("webdriver.chrome.driver",ChromeDriverPath);
		else System.setProperty("webdriver.chrome.driver", WebElementAction.class.getResource("/").getPath().replace("%20", " ")+"drivers/chromedriver");
		setWebDriver(new ChromeDriver());
		open(url);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#openNewWindowByChrome(java.lang.String, java.lang.String, int)
	 */
	@Override
	public void openNewWindowByChrome(String url,String hostIP,int hostPort) {
		caseLogBean.setEngineType("ChromeDriver with proxy "+hostIP+":"+hostPort);
		if(SystemInfo.isWindows())System.setProperty("webdriver.chrome.driver",ChromeDriverPath);
		else System.setProperty("webdriver.chrome.driver", WebElementAction.class.getResource("/").getPath().replace("%20", " ")+"drivers/chromedriver");
		setWebDriver(new ChromeDriver(getProxyCap(hostIP,hostPort)));
		open(url);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#getProxyCap(java.lang.String, java.lang.String)
	 */
	@Override
	public DesiredCapabilities getProxyCap(String hostIP,int hostPort){
		DesiredCapabilities cap = new DesiredCapabilities(); 
		Proxy proxy = new Proxy(); 
		proxy.setHttpProxy(hostIP+":"+hostPort);
		cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true); 
		cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true); 
		System.setProperty("http.nonProxyHosts", "localhost"); 
		cap.setCapability(CapabilityType.PROXY, proxy);
		return cap;
	}
	
	@Override
	public void setFirefoxExePath(String path){
		FirefoxPath = path;
	}
	/**
	 * 如果打不开Firefox浏览器或或者报异常，应先使用 setFirefoxExePath(String path)方法指定Firefox主程序路径
	 */
	@Override
	public void openNewWindowByFirefox(String url,String hostIP,int hostPort) {
		caseLogBean.setEngineType("FirefoxDriver with proxy "+hostIP+":"+hostPort);
		if(SystemInfo.isWindows())System.setProperty("webdriver.firefox.bin", FirefoxPath);
		setWebDriver(new FirefoxDriver(getProxyCap(hostIP,hostPort)));
		open(url);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#openNewWindowByFirefox(java.lang.String)
	 */
	@Override
	public void openNewWindowByFirefox(String url) {
		caseLogBean.setEngineType("FirefoxDriver");
		if(SystemInfo.isWindows())System.setProperty("webdriver.firefox.bin", FirefoxPath);
		setWebDriver(new FirefoxDriver());
		open(url);
	}

	@Override
	public void closeWindow() {
		close();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#closeWindowAndDriverExe()
	 */
	@Override
	public void closeWindowAndDriverExe(){
		close();
		closeDriverExe();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#closeDriverExe()
	 */
	@Override
	public void closeDriverExe(){
		WindowsUtils.tryToKillByName("phantomjs.exe");
		WindowsUtils.tryToKillByName("IEDriverServer64.exe");
		WindowsUtils.tryToKillByName("IEDriverServer32.exe");
		WindowsUtils.tryToKillByName("chromedriver64.exe");
		WindowsUtils.tryToKillByName("chromedriver32.exe");
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ElementAction#killProcess(java.lang.String[])
	 */
	@Override
	public void killProcess(String[] cmdArray){
		try {
			WindowsUtils.kill(cmdArray);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	@Override
	public void click(){
			WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).click();
	}

	@Override
	public String getText() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getText();
	}	

	@Override
	public String innerText() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).innerText();
	}

	@Override
	public String innerHtml() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).innerHtml();
	}

	@Override
	public String name() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).name();
	}

	@Override
	public boolean exists() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).exists();
	}

/*	@Override
	public SelenideElement $(String cssSelector) {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).$(cssSelector);
	}

	@Override
	public SelenideElement $(String cssSelector, int index) {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).$(cssSelector, index);
	}

	@Override
	public SelenideElement $(By selector) {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).$(selector);
	}

	@Override
	public SelenideElement $(By selector, int index) {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).$(selector, index);
	}

	@Override
	public ElementsCollection $$(String cssSelector) {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).$$(cssSelector);
	}*/

	/*
	 * 根据定位信息获取对象列表
	 * @see org.phoenix.action.ElementAction#getElements()
	 */
	@Override
	public ElementsCollection getElements() {
		return WebElements(locatorBean.getLocatorData(),locatorBean.getLocatorType());
	}

	@Override
	public void selectOption(String text) {
		WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).selectOption(text);
	}

	@Override
	public void selectOptionByValue(String value) {
		WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).selectOptionByValue(value);
	}

	@Override
	public String getSelectedValue() {
		String str = WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getSelectedValue();
		return str;
	}

	@Override
	public String getSelectedText() {
		String str = WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getSelectedText();
		return str;
	}

	@Override
	public File download() throws FileNotFoundException {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).download();
	}

	@Override
	public SelenideElement contextClick() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).contextClick();
	}

	@Override
	public SelenideElement hover() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).hover();
	}

	@Override
	public SelenideElement dragAndDropTo(String targetCssSelector) {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).dragAndDropTo(targetCssSelector);
	}

	@Override
	public boolean isImage() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).isImage();
	}
	
	@Override
	public SelenideElement parent() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).parent();
	}

	@Override
	public SelenideElement waitWhile(Condition condition,
			long timeoutMilliseconds) {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).waitWhile(condition, timeoutMilliseconds);
	}

	@Override
	public boolean isDisplayed() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).isDisplayed();
	}
	
	@Override
	public void submit() {
		WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).submit();		
	}

	@Override
	public void sendKeys(String str) {
		WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).sendKeys(str);
	}

	@Override
	public void clear() {
		WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).clear();
	}

	@Override
	public String getTagName() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getTagName();
	}

	@Override
	public String getAttribute(String name) {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getAttribute(name);
	}

	@Override
	public boolean isSelected() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).isSelected();
	}

	@Override
	public boolean isEnabled() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).isEnabled();
	}

	@Override
	public String getCssValue(String propertyName) {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getCssValue(propertyName);
	}

	@Override
	public SelenideElement getSelectedOption() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getSelectedOption();
	}

	@Override
	public SelenideElement scrollTo() {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).scrollTo();
	}

	@Override
	public void setText(String text) {
		WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).setValue(text);;
	}

	@Override
	public String getAttrValue(String attr) {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getAttribute(attr);
	}

	@Override
	public void sleep(long ms) {
		Selenide.sleep(ms);;
	}

	@Override
	public void append(String str) {
		WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).append(str);		
	}

	@Override
	public void pressEnter() {
		WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).pressEnter();
	}

	@Override
	public void pressTab() {
		WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).pressTab();
	}

	@Override
	public void setSelected(boolean selected) {
		WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).setSelected(selected);
	}

	@Override
	public void waitUntil(Condition condition, long timeoutMilliseconds) {
		WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).waitUntil(condition, timeoutMilliseconds);
	}

	@Override
	public File uploadFile(String filePath) {
		return WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).uploadFile(new File(filePath));
	}
	
	@Override
	public void switchToWindow(String title) {
		Selenide.switchTo().window(title);
	}

	@Override
	public void switchToWindow(int index) {
		Selenide.switchTo().window(index);
	}
	
	@Override
	public void switchToFrame(String nameOrId){
		getWebDriver().switchTo().frame(nameOrId);
	}
	
	public void switchToParent(){
		getAndCheckWebDriver().switchTo().parentFrame();
	}
	@Override
	public String getPageSource(){
		return getAndCheckWebDriver().getPageSource();
	}
	@Override
	public String screenshot(String fileName){
		return Selenide.screenshot(fileName);
	}
	
	@Override
	public void back() {
		Selenide.back();
	}

	@Override
	public void forward() {
		Selenide.forward();
	}

	@Override
	public String title() {
		return Selenide.title();
	}
	
	@Override
	public void refresh(){
		Selenide.refresh();
	}
	
	public void doubleClick(){
		actions().doubleClick(WebElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()));
	}

	@Override
	public void confirm(String expectedDialogText) {
		Selenide.confirm(expectedDialogText);
	}

	@Override
	public SelenideElement selectRadio(String value) {
		return Selenide.selectRadio(by(locatorBean.getLocatorData(),locatorBean.getLocatorType()), value);
	}

	@Override
	public SelenideElement getSelectedRadio() {
		return Selenide.getSelectedRadio(by(locatorBean.getLocatorData(),locatorBean.getLocatorType()));
	}

	@Override
	public void dismiss(String expectedDialogText) {
		Selenide.dismiss(expectedDialogText);
	}

	@Override
	public List<String> getJavascriptErrors() {
		return Selenide.getJavascriptErrors();
	}

	@Override
	public List<String> getWebDriverLogs(String logType, Level logLevel) {
		return getWebDriverLogs(logType, logLevel);
	}
	
	// ---------------------- android 操作部分 ---------------------- //
	
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getTouch()
	 */
	@Override
	public TouchScreen getTouch() {
		return selendroidDriver.getTouch();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#touchAction(org.openqa.selenium.WebElement, int, int, int)
	 */
	public TouchAction  touchAction(WebElement element,int ms,int x,int y){
		TouchAction finger = new TouchActionBuilder().pointerDown(element).pause(ms).
				  pointerMove(x, y).pointerUp().build(); 
		return finger;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#flickElement(org.openqa.selenium.WebElement, int, int, int)
	 */
	public void flickElement(WebElement onElement, int xOffset, int yOffset, int speed){
		new TouchActions(getWebDriver()).flick(onElement, xOffset, yOffset, speed).perform();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getTouchActions()
	 */
	public TouchActions getTouchActions(){
		return new TouchActions(getWebDriver());
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#multiTouchElement(io.selendroid.client.TouchAction[])
	 */
	public void multiTouchElement(TouchAction...touchAction){
		new MultiTouchAction(touchAction).perform(getWebDriver());
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#roll(int, int)
	 */
	@Override
	public void roll(int dimensionX, int dimensionY) {
		selendroidDriver.roll(dimensionX, dimensionY);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getMultiTouchScreen()
	 */
	@Override
	public MultiTouchScreen getMultiTouchScreen() {
		return selendroidDriver.getMultiTouchScreen();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getScreenshotAs(org.openqa.selenium.OutputType)
	 */
	@Override
	public <X> X getScreenshotAs(OutputType<X> target) {
		return selendroidDriver.getScreenshotAs(target);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getBrightness()
	 */
	@Override
	public int getBrightness() {
		return ((ScreenBrightness)selendroidDriver).getBrightness();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#setBrightness(int)
	 */
	@Override
	public void setBrightness(int desiredBrightness) {
		((ScreenBrightness)selendroidDriver).setBrightness(desiredBrightness);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#rotate(org.openqa.selenium.ScreenOrientation)
	 */
	@Override
	public void rotate(ScreenOrientation orientation) {
		selendroidDriver.rotate(orientation);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getOrientation()
	 */
	@Override
	public ScreenOrientation getOrientation() {
		return selendroidDriver.getOrientation();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#setConfiguration(io.selendroid.client.DriverCommand, java.lang.String, java.lang.Object)
	 */
	@Override
	public void setConfiguration(DriverCommand command, String key, Object value) {
		selendroidDriver.setConfiguration(command, key, value);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getConfiguration(io.selendroid.client.DriverCommand)
	 */
	@Override
	public Map<String, Object> getConfiguration(DriverCommand command) {
		return selendroidDriver.getConfiguration(command);
	}

	@Override
	public AdbConnection getAdbConnection() {
		return selendroidDriver.getAdbConnection();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#isAirplaneModeEnabled()
	 */
	@Override
	public boolean isAirplaneModeEnabled() {
		return selendroidDriver.isAirplaneModeEnabled();
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#setAirplaneMode(boolean)
	 */
	@Override
	public void setAirplaneMode(boolean enabled) {
		selendroidDriver.setAirplaneMode(enabled);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#tap(int, int)
	 */
	@Override
	public void tap(int x, int y) {
		getAdbConnection().tap(x, y);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#sendText(java.lang.String)
	 */
	@Override
	public void appTextFieldSendTextByAdb(String text) {
		getAdbConnection().sendText(text);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#sendKeyEvent(int)
	 */
	@Override
	public void sendKeyEvent(int keyCode) {
		getAdbConnection().sendKeyEvent(keyCode);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#executeShellCommand(java.lang.String)
	 */
	@Override
	public String executeShellCommand(String command) {
		return getAdbConnection().executeShellCommand(command);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getContextHandles()
	 */
	@Override
	public Set<String> getContextHandles() {
		return selendroidDriver.getContextHandles();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getContext()
	 */
	@Override
	public String getContext() {
		return selendroidDriver.getContext();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#backgroundApp()
	 */
	@Override
	public void backgroundApp() {
		selendroidDriver.backgroundApp();		
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#resumeApp()
	 */
	@Override
	public void resumeApp() {
		selendroidDriver.resumeApp();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#addCallLog(io.selendroid.server.common.utils.CallLogEntry)
	 */
	@Override
	public void addCallLog(CallLogEntry log) {
		selendroidDriver.addCallLog(log);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#readCallLog()
	 */
	@Override
	public List<CallLogEntry> readCallLog() {
		return selendroidDriver.readCallLog();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#callExtension(java.lang.String)
	 */
	@Override
	public Object callExtension(String extensionMethod) {
		return selendroidDriver.callExtension(extensionMethod);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#callExtension(java.lang.String, java.util.Map)
	 */
	@Override
	public Object callExtension(String extensionMethod,
			Map<String, ?> parameters) {
		return selendroidDriver.callExtension(extensionMethod, parameters);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#setSystemProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public void setSystemProperty(String propertyName, String value) {
		selendroidDriver.setSystemProperty(propertyName, value);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#gc()
	 */
	@Override
	public void gc() {
		selendroidDriver.gc();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#luncherSelendroidServer(java.lang.String)
	 */
	@Override
	public void luncherSelendroidServer(String appPath){
		this.closeSelendroidServer();
		config = new SelendroidConfiguration();
		config.addSupportedApp(appPath);		
		selendroidServer = new SelendroidLauncher(config);
		selendroidServer.launchSelendroid();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#closeSelendroidServer()
	 */
	@Override
	public void closeSelendroidServer(){
	    if(selendroidDriver != null)selendroidDriver.quit();
		if(selendroidServer != null)selendroidServer.stopSelendroid();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#openAndroidAppBySelendroidWidthEmulator(java.lang.String)
	 */
	@Override
	public void openAndroidAppBySelendroidWithEmulator(String appPath) {
		luncherSelendroidServer(appPath);
		try{
		capa = SelendroidCapabilities.emulator(getTargetAppId());
		caseLogBean.setEngineType("AndroidEmulator "+capa.getVersion());
		selendroidDriver = new SelendroidDriver(capa);
		setWebDriver(selendroidDriver);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#openAndroidAppBySelendroidWidthDevice(java.lang.String)
	 */
	@Override
	public void openAndroidAppBySelendroidWithDevice(String appPath) {
		this.luncherSelendroidServer(appPath);
		capa = SelendroidCapabilities.device(getTargetAppId());
		caseLogBean.setEngineType("AndroidDevice "+capa.getVersion());
		try {
			selendroidDriver = new SelendroidDriver(capa);
			setWebDriver(selendroidDriver);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#openAndroidBrowserBySelendroid(java.lang.String)
	 */
	@Override
	public void openAndroidBrowserBySelendroid(String url) {
		caseLogBean.setEngineType("AndroidBrowser");
		luncherSelendroidServer(url);
		setWebDriver(new RemoteWebDriver(DesiredCapabilities.android()));
		open(url);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getAndroidStatus()
	 */
	@Override
	public String getAndroidStatus() {
		try {
			return GetXml.getResponseByGet("http://localhost:4444/wd/hub/status");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getTargetAppId()
	 */
	@Override
	public String getTargetAppId()  {
		try {
			return JsonPaser.getNodeValue(getAndroidStatus(), "JSON.value.supportedApps[0].appId");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getSelendroidDriver()
	 */
	@Override
	public SelendroidDriver getSelendroidDriver() {
		return selendroidDriver;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#pressKeyByKeyboard(java.lang.String)
	 */
	@Override
	public void pressKeyByKeyboard(String keyCode) {
		selendroidDriver.getKeyboard().pressKey(keyCode);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#releaseKeyByKeyboard(java.lang.String)
	 */
	public void releaseKeyByKeyboard(String keyCode){
		selendroidDriver.getKeyboard().releaseKey(keyCode);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getAPITargetType()
	 */
	@Override
	public String getAPITargetType() {
		return capa.getAPITargetType();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getScreenSize()
	 */
	@Override
	public String getScreenSize() {
		return capa.getScreenSize();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getKeyboard()
	 */
	@Override
	public Keyboard getKeyboard() {
		return selendroidDriver.getKeyboard();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementSetText(java.lang.String)
	 */
	@Override
	public void appElementSetText(String text) {
		selendroidDriver.findElement(by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).sendKeys(text);;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementClear()
	 */
	@Override
	public void appElementClear() {
		selendroidDriver.findElement(by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).clear();		
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementIsDisplayed()
	 */
	@Override
	public boolean appElementIsDisplayed() {
		return selendroidDriver.findElement(by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).isDisplayed();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementIsEnabled()
	 */
	@Override
	public boolean appElementIsEnabled() {
		return selendroidDriver.findElement(by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).isEnabled();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementIsSelected()
	 */
	@Override
	public boolean appElementIsSelected() {
		return selendroidDriver.findElement(by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).isSelected();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementClick()
	 */
	@Override
	public void appElementClick() {
		selendroidDriver.findElement(by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).click();		
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementDoubleClick()
	 */
	@Override
	public void appElementDoubleClick() {
		new Actions(selendroidDriver).doubleClick(selendroidDriver.findElement(by(locatorBean.getLocatorData(),locatorBean.getLocatorType())));
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getAppElementTagName()
	 */
	@Override
	public String getAppElementTagName() {
		return selendroidDriver.findElement(by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).getTagName();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getAppAttribute(java.lang.String)
	 */
	@Override
	public String getAppAttribute(String name) {
		return selendroidDriver.findElement(by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).getAttribute(name);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getAppCssValue(java.lang.String)
	 */
	@Override
	public String getAppCssValue(String propertyName) {
		return selendroidDriver.findElement(by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).getCssValue(propertyName);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getAppLocation()
	 */
	@Override
	public Point getAppLocation() {
		return selendroidDriver.findElement(by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).getLocation();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getAppDimension()
	 */
	@Override
	public Dimension getAppDimension() {
		return selendroidDriver.findElement(by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).getSize();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getAppElements()
	 */
	@Override
	public List<org.openqa.selenium.WebElement> getAppElements() {
		return selendroidDriver.findElements(by(locatorBean.getLocatorData(),locatorBean.getLocatorType()));
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementClickAndHold()
	 */
	@Override
	public void appElementClickAndHold() {
		new Actions(selendroidDriver).clickAndHold(selendroidDriver.findElement(by(locatorBean.getLocatorData(),locatorBean.getLocatorType())));		
	}

	@Override
	public ElementAction appElement() {
		return webProxy;
	}

	@Override
	public ElementAction appElement(String name) {
		locatorBean = locators.get(name);	
		return webProxy;
	}

	@Override
	public ElementAction appElement(String locatorData, LocatorType locatorType) {
		if(locatorType == null){
			if(locatorData.startsWith("#"))locatorBean = new LocatorBean(locatorData,LocatorType.ID);
			if(locatorData.startsWith("."))locatorBean = new LocatorBean(locatorData,LocatorType.CLASS);
		}else locatorBean = new LocatorBean(locatorData,locatorType);
		return webProxy;
	}

	@Override
	public org.openqa.selenium.WebElement appElementLinkFinder(String locatorData, LocatorType locatorType) {
		By by = null;
		if(locatorType == null){
			if(locatorData.startsWith("#"))by = by(locatorData,LocatorType.ID);
			if(locatorData.startsWith("."))by = by(locatorData,LocatorType.CLASS);
		}else by = by(locatorData,locatorType);
		return selendroidDriver.findElement(by);
	}

	@Override
	public org.openqa.selenium.WebElement appElementLinkFinder(String locatorData) {
		LocatorBean locatorBean = locators.get(locatorData);
		return selendroidDriver.findElement(by(locatorBean.getLocatorData(),locatorBean.getLocatorType()));
	}
}
