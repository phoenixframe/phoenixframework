package org.phoenix.api.impl;

import static com.codeborne.selenide.Selenide.actions;
import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getAndCheckWebDriver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.phoenix.action.ElementLocator;
import org.phoenix.action.LoadPhoenixPlugins;
import org.phoenix.aop.PhoenixLogger;
import org.phoenix.api.IWebAPI;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.LocatorBean;
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
public class WebAPI extends CommonAPI implements IWebAPI{
	private CaseLogBean caseLogBean;
	private String ChromeDriverPath;
	private String FirefoxPath;
	private IWebAPI webProxy;
	private LocatorBean locatorBean;
	private ElementLocator eleLocator = new ElementLocator();
	
	public WebAPI(CaseLogBean caseLogBean) {
		this.caseLogBean = caseLogBean;
		new PhoenixLogger();
		new LoadPhoenixPlugins();
	}

	public String getChromeDriverPath() {
		return ChromeDriverPath;
	}

	public void setChromeDriverPath(String chromeDriverPath) {
		ChromeDriverPath = chromeDriverPath;
	}

	public String getFirefoxPath() {
		return FirefoxPath;
	}

	public void setFirefoxPath(String firefoxPath) {
		FirefoxPath = firefoxPath;
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#executeScript(java.lang.String, java.lang.Object[])
	 */
	public Object executeScript(String script, Object...args){
		return Selenide.executeJavaScript(script, args);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#executeAsyncScript(java.lang.String, java.lang.Object[])
	 */
	public Object executeAsyncScript(String script, Object...args){
		if(getCurrentDriver() !=null ){
			JavascriptExecutor je = (JavascriptExecutor)getCurrentDriver();
			return je.executeAsyncScript(script, args);
		}else {
			return "Fail：js执行失败，currentDriver为null";
		}
	}
	
	@Override
	public CaseLogBean getCaseLogBean() {
		return caseLogBean;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#webElement()
	 */
	@Override
	public IWebAPI webElement(){
		return webProxy;
	}
	/**
	 * 直接使用定位信息而无需将其先录入数据库
	 * @param locatorData
	 * @param locatorType 如果为null，则默认为locatorType 为CSS。Class和id可直接作为Css定位
	 * @return
	 */
	@Override
	public IWebAPI webElement(String locatorData,LocatorType locatorType){
		if(locatorType == null)locatorBean = new LocatorBean(locatorData);
		else locatorBean = new LocatorBean(locatorData,locatorType);
		return webProxy;
	}
	/**
	 * 链式查询方法，直接调用了SelenideElement，调用了此方法后，后续的操作将不会被记录日志
	 */
	@Override
	public SelenideElement webElementLinkFinder(String locatorDataName){
		LocatorBean locatorBean = getLocators().get(locatorDataName);
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType());
	}
	
	/**
	 * 链式查询方法，直接调用了SelenideElement，调用了此方法后，后续的操作将不会被记录日志
	 */
	@Override
	public SelenideElement webElementLinkFinder(String locatorData,LocatorType locatorType){
		if(locatorType == null)locatorType = LocatorType.CSS;
		return eleLocator.webElement(locatorData,locatorType);
	}
	
	/**
	 * 指定一个定位信息的标识，需要先将其录入数据库之后才会有该标识
	 */
	@Override
	public IWebAPI webElement(String name){
		try{
			locatorBean = getLocators().get(name);	
		}catch(NullPointerException e){
			PhoenixLogger.error("根据定位信息名称：[ "+name+" ] 加载数据失败。此方法仅适用于已加载了数据库中定位信息的数据，请在驱动启动前加载数据。否则请使用：webElement(String locatorData,LocatorType locatorType); 方法来手动指定定位信息。");
		}
		return webProxy;
	}
	
	/**
	 * 不使用任何定位信息时，如close方法
	 * @return
	 */
	public IWebAPI getWebProxy() {
		return webProxy;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#setWebProxy(org.phoenix.api.IWebAPI)
	 */
	@Override
	public void setWebProxy(IWebAPI webProxy) {
		this.webProxy = webProxy;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#saveImgToLocal(com.codeborne.selenide.SelenideElement, java.lang.String, java.lang.String)
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
	 * @see org.phoenix.api.IWebAPI#saveImgToLocal(int, int, int, int, java.lang.String)
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
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#getCurrentDriver()
	 */
	@Override
	public WebDriver getCurrentDriver(){
		return getWebDriver();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#openNewWindowByHtmlUnit(java.lang.String, boolean, com.gargoylesoftware.htmlunit.BrowserVersion)
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
	 * @see org.phoenix.api.IWebAPI#openNewWindowByHtmlUnit(java.lang.String, boolean, com.gargoylesoftware.htmlunit.BrowserVersion, java.lang.String, int)
	 */
	public void openNewWindowByHtmlUnit(String url,boolean jsEnable,BrowserVersion version,String hostIP,int hostPort){
		caseLogBean.setEngineType("HttpUnitDriver with Proxy "+hostIP+":"+hostPort);
		HtmlUnitDriver htmlUnit = new HtmlUnitDriver(version);
		htmlUnit.setProxy(hostIP, hostPort);
		htmlUnit.setJavascriptEnabled(jsEnable);
		setWebDriver(htmlUnit);
		open(url);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#openNewWindowByPhantomJs(java.lang.String, java.lang.String, int)
	 */
	@Override
	public void openNewWindowByPhantomJs(String url,String hostIP,int hostPort){
		caseLogBean.setEngineType("PhantomJsDriver with proxy "+hostIP+":"+hostPort);
		DesiredCapabilities sCaps = new DesiredCapabilities();
        sCaps.setJavascriptEnabled(true);
        if(SystemInfo.isWindows()){
        	sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, WebAPI.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs.exe");
            sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY, WebAPI.class.getResource("/").getPath().replace("%20", " ")+"drivers/js/main.js");
            PhoenixLogger.info("Use Driver："+WebAPI.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs.exe");
        } else {
        	sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, WebAPI.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs");
        	PhoenixLogger.info("Use Driver："+WebAPI.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs");
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
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#openNewWindowByPhantomJs(java.lang.String)
	 */
	@Override
	public void openNewWindowByPhantomJs(String url){
		caseLogBean.setEngineType("PhantomJsDriver");
		DesiredCapabilities sCaps = new DesiredCapabilities();
        sCaps.setJavascriptEnabled(true);
        if(SystemInfo.isWindows()){
        	sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, WebAPI.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs.exe");
            sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY, WebAPI.class.getResource("/").getPath().replace("%20", " ")+"drivers/js/main.js");
            PhoenixLogger.info("Use Driver："+WebAPI.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs.exe");
        } else {
        	sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, WebAPI.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs");
        	PhoenixLogger.info("Use Driver："+WebAPI.class.getResource("/").getPath().replace("%20", " ")+"drivers/phantomjs");
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
			System.setProperty("webdriver.ie.driver", WebAPI.class.getResource("/").getPath().replace("%20", " ")+"drivers/IEDriverServer"+SystemInfo.getArch()+".exe");
			setWebDriver(new InternetExplorerDriver());
			open(url);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#openNewWindowByIE(java.lang.String, java.lang.String, int)
	 */
	@Override
	public void openNewWindowByIE(String url,String hostIP,int hostPort){
			caseLogBean.setEngineType("IEDriver with proxy "+hostIP+":"+hostPort);
			System.setProperty("webdriver.ie.driver", WebAPI.class.getResource("/").getPath().replace("%20", " ")+"drivers/IEDriverServer"+SystemInfo.getArch()+".exe");
			setWebDriver(new InternetExplorerDriver(getProxyCap(hostIP,hostPort)));
			open(url);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#setChromeDriverExePath(java.lang.String)
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
		else System.setProperty("webdriver.chrome.driver", WebAPI.class.getResource("/").getPath().replace("%20", " ")+"drivers/chromedriver");
		setWebDriver(new ChromeDriver());
		open(url);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#openNewWindowByChrome(java.lang.String, java.lang.String, int)
	 */
	@Override
	public void openNewWindowByChrome(String url,String hostIP,int hostPort) {
		caseLogBean.setEngineType("ChromeDriver with proxy "+hostIP+":"+hostPort);
		if(SystemInfo.isWindows())System.setProperty("webdriver.chrome.driver",ChromeDriverPath);
		else System.setProperty("webdriver.chrome.driver", WebAPI.class.getResource("/").getPath().replace("%20", " ")+"drivers/chromedriver");
		setWebDriver(new ChromeDriver(getProxyCap(hostIP,hostPort)));
		open(url);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#getProxyCap(java.lang.String, int)
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
	 * @see org.phoenix.api.IWebAPI#openNewWindowByFirefox(java.lang.String)
	 */
	@Override
	public void openNewWindowByFirefox(String url) {
		caseLogBean.setEngineType("FirefoxDriver");
		if(SystemInfo.isWindows())System.setProperty("webdriver.firefox.bin", FirefoxPath);
		setWebDriver(new FirefoxDriver());
		open(url);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#openNewWindowByEdge(java.lang.String)
	 */
	public void openNewWindowByEdge(String url){
		caseLogBean.setEngineType("EdgeDriver");
		setWebDriver(new EdgeDriver());
		open(url);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#openNewWindowByEdge(java.lang.String, java.lang.String, int)
	 */
	public void openNewWindowByEdge(String url,String hostIP,int hostPort){
		caseLogBean.setEngineType("EdgeDriver with proxy "+hostIP+":"+hostPort);
		setWebDriver(new EdgeDriver(getProxyCap(hostIP,hostPort)));
		open(url);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#openNewWindowBySafari(java.lang.String)
	 */
	public void openNewWindowBySafari(String url){
		caseLogBean.setEngineType("SafariDriver");
		setWebDriver(new SafariDriver());
		open(url);
	}
	@Override
	public void closeWindow() {
		close();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#closeWindowAndDriverExe()
	 */
	@Override
	public void closeWindowAndDriverExe(){
		close();
		closeDriverExe();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#closeDriverExe()
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
	 * @see org.phoenix.api.IWebAPI#killProcess(java.lang.String[])
	 */
	@Override
	public void killProcess(String[] cmdArray){
		try {
			WindowsUtils.kill(cmdArray);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#click()
	 */
	@Override
	public void click(){
		eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).click();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#getText()
	 */
	@Override
	public String getText() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getText();
	}	
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#innerText()
	 */
	@Override
	public String innerText() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).innerText();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#innerHtml()
	 */
	@Override
	public String innerHtml() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).innerHtml();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#name()
	 */
	@Override
	public String name() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).name();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#exists()
	 */
	@Override
	public boolean exists() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).exists();
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
		return eleLocator.webElements(locatorBean.getLocatorData(),locatorBean.getLocatorType());
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#selectOption(java.lang.String)
	 */
	@Override
	public void selectOption(String text) {
		eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).selectOption(text);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#selectOptionByValue(java.lang.String)
	 */
	@Override
	public void selectOptionByValue(String value) {
		eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).selectOptionByValue(value);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#getSelectedValue()
	 */
	@Override
	public String getSelectedValue() {
		String str = eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getSelectedValue();
		return str;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#getSelectedText()
	 */
	@Override
	public String getSelectedText() {
		String str = eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getSelectedText();
		return str;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#download()
	 */
	@Override
	public File download() throws FileNotFoundException {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).download();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#contextClick()
	 */
	@Override
	public SelenideElement contextClick() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).contextClick();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#hover()
	 */
	@Override
	public SelenideElement hover() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).hover();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#dragAndDropTo(java.lang.String)
	 */
	@Override
	public SelenideElement dragAndDropTo(String targetCssSelector) {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).dragAndDropTo(targetCssSelector);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#isImage()
	 */
	@Override
	public boolean isImage() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).isImage();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#parent()
	 */
	@Override
	public SelenideElement parent() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).parent();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#waitWhile(com.codeborne.selenide.Condition, long)
	 */
	@Override
	public SelenideElement waitWhile(Condition condition,
			long timeoutMilliseconds) {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).waitWhile(condition, timeoutMilliseconds);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#isDisplayed()
	 */
	@Override
	public boolean isDisplayed() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).isDisplayed();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#submit()
	 */
	@Override
	public void submit() {
		eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).submit();		
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#sendKeys(java.lang.String)
	 */
	@Override
	public void sendKeys(String str) {
		eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).sendKeys(str);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#clear()
	 */
	@Override
	public void clear() {
		eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).clear();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#getTagName()
	 */
	@Override
	public String getTagName() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getTagName();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#getAttribute(java.lang.String)
	 */
	@Override
	public String getAttribute(String name) {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getAttribute(name);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#isSelected()
	 */
	@Override
	public boolean isSelected() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).isSelected();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).isEnabled();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#getCssValue(java.lang.String)
	 */
	@Override
	public String getCssValue(String propertyName) {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getCssValue(propertyName);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#getSelectedOption()
	 */
	@Override
	public SelenideElement getSelectedOption() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getSelectedOption();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#scrollTo()
	 */
	@Override
	public SelenideElement scrollTo() {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).scrollTo();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#setText(java.lang.String)
	 */
	@Override
	public void setText(String text) {
		eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).setValue(text);;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#getAttrValue(java.lang.String)
	 */
	@Override
	public String getAttrValue(String attr) {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).getAttribute(attr);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.impl.CommonAPI#sleep(long)
	 */
	@Override
	public void sleep(long ms) {
		Selenide.sleep(ms);;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#append(java.lang.String)
	 */
	@Override
	public void append(String str) {
		eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).append(str);		
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#pressEnter()
	 */
	@Override
	public void pressEnter() {
		eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).pressEnter();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#pressTab()
	 */
	@Override
	public void pressTab() {
		eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).pressTab();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#setSelected(boolean)
	 */
	@Override
	public void setSelected(boolean selected) {
		eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).setSelected(selected);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#waitUntil(com.codeborne.selenide.Condition, long)
	 */
	@Override
	public void waitUntil(Condition condition, long timeoutMilliseconds) {
		eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).waitUntil(condition, timeoutMilliseconds);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#uploadFile(java.lang.String)
	 */
	@Override
	public File uploadFile(String filePath) {
		return eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()).uploadFile(new File(filePath));
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#switchToWindow(java.lang.String)
	 */
	@Override
	public void switchToWindow(String title) {
		Selenide.switchTo().window(title);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#switchToWindow(int)
	 */
	@Override
	public void switchToWindow(int index) {
		Selenide.switchTo().window(index);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#switchToFrame(java.lang.String)
	 */
	@Override
	public void switchToFrame(String nameOrId){
		getWebDriver().switchTo().frame(nameOrId);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#switchToParent()
	 */
	public void switchToParent(){
		getAndCheckWebDriver().switchTo().parentFrame();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#getPageSource()
	 */
	@Override
	public String getPageSource(){
		return getAndCheckWebDriver().getPageSource();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#screenshot(java.lang.String)
	 */
	@Override
	public String screenshot(String fileName){
		return Selenide.screenshot(fileName);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#back()
	 */
	@Override
	public void back() {
		Selenide.back();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#forward()
	 */
	@Override
	public void forward() {
		Selenide.forward();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#title()
	 */
	@Override
	public String title() {
		return Selenide.title();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#refresh()
	 */
	@Override
	public void refresh(){
		Selenide.refresh();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#doubleClick()
	 */
	public void doubleClick(){
		actions().doubleClick(eleLocator.webElement(locatorBean.getLocatorData(),locatorBean.getLocatorType()));
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#confirm(java.lang.String)
	 */
	@Override
	public void confirm(String expectedDialogText) {
		Selenide.confirm(expectedDialogText);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#selectRadio(java.lang.String)
	 */
	@Override
	public SelenideElement selectRadio(String value) {
		return Selenide.selectRadio(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType()), value);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#getSelectedRadio()
	 */
	@Override
	public SelenideElement getSelectedRadio() {
		return Selenide.getSelectedRadio(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType()));
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#dismiss(java.lang.String)
	 */
	@Override
	public void dismiss(String expectedDialogText) {
		Selenide.dismiss(expectedDialogText);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#getJavascriptErrors()
	 */
	@Override
	public List<String> getJavascriptErrors() {
		return Selenide.getJavascriptErrors();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.IWebAPI#getWebDriverLogs(java.lang.String, java.util.logging.Level)
	 */
	@Override
	public List<String> getWebDriverLogs(String logType, Level logLevel) {
		return getWebDriverLogs(logType, logLevel);
	}
}
