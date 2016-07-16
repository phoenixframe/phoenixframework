package org.phoenix.api.impl;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.phoenix.action.ElementLocator;
import org.phoenix.api.utils.JsonPaser;
import org.phoenix.enums.LocatorType;
import org.phoenix.mobile.android.action.IAndroidAppAPI;
import org.phoenix.mobile.powertools.GetXml;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.LocatorBean;

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

/**
 * android操作实现类
 * @author mengfeiyang
 *
 */
public class AndroidAppAPI extends MobileCommon implements IAndroidAppAPI{
	private SelendroidConfiguration config;
	private SelendroidLauncher selendroidServer;
	private SelendroidCapabilities capa;
	private SelendroidDriver selendroidDriver;
	private CaseLogBean caseLogBean;
	private LocatorBean locatorBean;
	private IAndroidAppAPI androidAPIProxy;
	private ElementLocator eleLocator = new ElementLocator();
	
	public IAndroidAppAPI getAndroidAPIProxy() {
		return androidAPIProxy;
	}
	public void setAndroidAPIProxy(IAndroidAppAPI androidAPIProxy) {
		this.androidAPIProxy = androidAPIProxy;
	}
	public AndroidAppAPI(CaseLogBean caseLogBean){
		this.caseLogBean = caseLogBean;
	}
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
	public void setText(String text) {
		selendroidDriver.findElement(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).sendKeys(text);;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementClear()
	 */
	@Override
	public void clear() {
		selendroidDriver.findElement(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).clear();		
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementIsDisplayed()
	 */
	@Override
	public boolean isDisplayed() {
		return selendroidDriver.findElement(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).isDisplayed();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementIsEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return selendroidDriver.findElement(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).isEnabled();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementIsSelected()
	 */
	@Override
	public boolean isSelected() {
		return selendroidDriver.findElement(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).isSelected();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementClick()
	 */
	@Override
	public void click() {
		selendroidDriver.findElement(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).click();		
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementDoubleClick()
	 */
	@Override
	public void doubleClick() {
		new Actions(selendroidDriver).doubleClick(selendroidDriver.findElement(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType())));
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getAppElementTagName()
	 */
	@Override
	public String getElementTagName() {
		return selendroidDriver.findElement(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).getTagName();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getAppAttribute(java.lang.String)
	 */
	@Override
	public String getAttribute(String name) {
		return selendroidDriver.findElement(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).getAttribute(name);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getAppCssValue(java.lang.String)
	 */
	@Override
	public String getCssValue(String propertyName) {
		return selendroidDriver.findElement(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).getCssValue(propertyName);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getAppLocation()
	 */
	@Override
	public Point getLocation() {
		return selendroidDriver.findElement(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).getLocation();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getAppDimension()
	 */
	@Override
	public Dimension getAppDimension() {
		return selendroidDriver.findElement(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType())).getSize();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#getAppElements()
	 */
	@Override
	public List<org.openqa.selenium.WebElement> getElements() {
		return selendroidDriver.findElements(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType()));
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.mobile.android.action.AndroidAction#appElementClickAndHold()
	 */
	@Override
	public void clickAndHold() {
		new Actions(selendroidDriver).clickAndHold(selendroidDriver.findElement(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType())));		
	}

	@Override
	public IAndroidAppAPI element(String name) {
		locatorBean = getLocators().get(name);	
		return androidAPIProxy;
	}

	@Override
	public IAndroidAppAPI element(String locatorData, LocatorType locatorType) {
		if(locatorType == null){
			if(locatorData.startsWith("#"))locatorBean = new LocatorBean(locatorData,LocatorType.ID);
			if(locatorData.startsWith("."))locatorBean = new LocatorBean(locatorData,LocatorType.CLASS);
		}else locatorBean = new LocatorBean(locatorData,locatorType);
		return androidAPIProxy;
	}

	@Override
	public org.openqa.selenium.WebElement linkFinder(String locatorData, LocatorType locatorType) {
		By by = null;
		if(locatorType == null){
			if(locatorData.startsWith("#"))by = eleLocator.by(locatorData,LocatorType.ID);
			if(locatorData.startsWith("."))by = eleLocator.by(locatorData,LocatorType.CLASS);
		}else by = eleLocator.by(locatorData,locatorType);
		return selendroidDriver.findElement(by);
	}

	@Override
	public org.openqa.selenium.WebElement linkFinder(String locatorData) {
		LocatorBean locatorBean = getLocators().get(locatorData);
		return selendroidDriver.findElement(eleLocator.by(locatorBean.getLocatorData(),locatorBean.getLocatorType()));
	}
}
