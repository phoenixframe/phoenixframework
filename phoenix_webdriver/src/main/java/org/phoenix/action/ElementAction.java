package org.phoenix.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.phoenix.api.action.APIAction;
import org.phoenix.enums.LocatorType;
import org.phoenix.mobile.android.action.AndroidAction;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.InterfaceBatchDataBean;
import org.phoenix.model.InterfaceDataBean;
import org.phoenix.plugins.IFtpClient;
import org.phoenix.plugins.IImageReader;
import org.phoenix.plugins.ISvnClient;
import org.phoenix.plugins.ITelnetClient;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.gargoylesoftware.htmlunit.BrowserVersion;

/**
 * 元素操作的接口，该接口中的所有操作是web和mobile共有的，
 * 一些特殊的操作需要在实现类中独立实现
 * @author mengfeiyang
 *
 */
public interface ElementAction extends AndroidAction{
	/**
	 * 加载web用例的数据，根据用例的名称
	 * @param caseName
	 * @return
	 */
	HashMap<InterfaceBatchDataBean,HashMap<String,String>> loadWebCaseDatas(String caseName);
	
	/**
	 * 加载web用例的数据，根据用例的id
	 * @param caseId
	 * @return
	 */
	HashMap<InterfaceBatchDataBean,HashMap<String,String>> loadWebCaseDatas(int caseId);
	/**
	 * 加载数据与定位信息
	 * @param caseId
	 * @param caseLogBean
	 */
	void addLocator(int caseId,CaseLogBean caseLogBean);
	/**
	 * 加载数据与定位信息
	 * @param caseName
	 * @param caseLogBean
	 */
	void addLocator(String caseName,CaseLogBean caseLogBean);
	
	/**
	 * 根据给定的用例的id，获取该用例下所有的数据
	 * @param caseId
	 */
	LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> loadInterfaceDatas(int caseId);
	
	/**
	 * 根据给定的用例的name，获取该用例下所有的数据
	 * @param caseName
	 */
	LinkedHashMap<InterfaceBatchDataBean,List<InterfaceDataBean>> loadInterfaceDatas(String caseName);
	/**
	 * 不使用任何定位信息时，如close方法
	 * @return
	 */
	ElementAction appElement();
	/**
	 * 指定一个定位信息的标识，需要先将其录入数据库之后才会有该标识
	 * @param name
	 * @return
	 */
	ElementAction appElement(String name);
	/**
	 * 直接使用定位信息而无需将其先录入数据库
	 * @param locatorData
	 * @param locatorType 如果为null，则默认为locatorType 为CSS。Class和id可直接作为Css定位
	 * @return
	 */
	ElementAction appElement(String locatorData,LocatorType locatorType);
	
	/**
	 * 链式查询方法，直接调用WebElement，使用 了此方法后，后续的操作不能被记录日志
	 * @param locatorData
	 * @param locatorType 如果定位方式为非CSS，则需要手动指定定位类型，如LocatorType.XPATH
	 * @return
	 */
	WebElement appElementLinkFinder(String locatorData,LocatorType locatorType);
	/**
	 * 链式查询方法，直接调用WebElement，使用 了此方法后，后续的操作不能被记录日志
	 * @param locatorData 默认定位方式是CSS
	 * @return
	 */
	WebElement appElementLinkFinder(String locatorData);
	
	/**
	 * 不使用任何定位信息时，如close方法
	 * @return
	 */
	ElementAction webElement();
	/**
	 * 指定一个定位信息的标识，需要先将其录入数据库之后才会有该标识
	 * @param name
	 * @return
	 */
	ElementAction webElement(String name);
	/**
	 * 直接使用定位信息而无需将其先录入数据库
	 * @param locatorData
	 * @param locatorType 如果为null，则默认为locatorType 为CSS。Class和id可直接作为Css定位
	 * @return
	 */
	ElementAction webElement(String locatorData,LocatorType locatorType);
	
	/**
	 * 链式查询方法，直接调用SelenideElement，使用 了此方法后，后续的操作不能被记录日志
	 * @param locatorData
	 * @param locatorType 如果定位方式为非CSS，则需要手动指定定位类型，如LocatorType.XPATH
	 * @return
	 */
	SelenideElement webElementLinkFinder(String locatorData,LocatorType locatorType);
	/**
	 * 链式查询方法，直接调用SelenideElement，使用 了此方法后，后续的操作不能被记录日志
	 * @param locatorData 默认定位方式是CSS
	 * @return
	 */
	SelenideElement webElementLinkFinder(String locatorData);
	
	/**
	 * 保存页面上的图片到本地，仅支持:IE,Firefox,Chrome
	 * @param element
	 * @param engine
	 * @param localFilePath
	 * @return
	 */
	boolean saveImgToLocal(SelenideElement element,String engine,String localFilePath);
	
	/**
	 * 根据x,y的坐标保存页面上的指定区域.x,y表示起始点
	 * @param x
	 * @param y
	 * @param picWidth 要保存的图片的宽度
	 * @param picHeight 要保存的图片的高度
	 * @param savePath 保存的路径，如：E:\\test.png
	 * @return
	 */
	boolean saveImgToLocal(int x,int y,int picWidth,int picHeight,String savePath);
	/**
	 * 获取检查点代理方法
	 * @return
	 */
	ICheckPoint checkPoint();
	/**
	 * 获取webAPI接口的代理方法
	 * @return
	 */
	APIAction webAPIAction();
	/**
	 * socket客户端，用于对socket服务器做操作
	 * @return
	 */
	ITelnetClient telnetClient();
	/**
	 * svn客户端，用于对svn做操作。如获取提交日志，提交文件，更新文件等等。
	 * @return
	 */
	ISvnClient svnClient();
	/**
	 * 图片解析，用于识别图片上的字符。可直接对网络图片或本地图片进行读取
	 * @return
	 */
	IImageReader imageReader();
	/**
	 * ftp客户端，用于操作ftp服务器，如从ftp服务器下载文件和上传本地文件到服务器等操作。
	 * @return
	 */
	IFtpClient ftpClient();

	/**
	 * 根据用例的名称添加聚合用例
	 * @return
	 */
	void addAggregateCase(String caseName);
	/**
	 * 根据用例的id添加聚合用例
	 * @param caseId
	 */
	void addAggregateCase(int caseId);
	/**
	 * 返回当前驱动类型
	 * @return
	 */
	WebDriver getCurrentDriver();
	/**
	 * 设置chrome浏览器驱动的路径，一般与chrome.exe在同一目录下
	 * @param path
	 */
	void setChromeDriverExePath(String path);
	/**
	 * 设置Firefox浏览器的安装路径
	 * @param path
	 */
	void setFirefoxExePath(String path);
	/**
	 * 设置代理类
	 * @param webProxy
	 */
	void setWebProxy(ElementAction webProxy);
	/**
	 * 返回一个带有代理IP的DesiredCapabilities
	 * @param hostIP
	 * @param hostPort
	 * @return
	 */
	DesiredCapabilities getProxyCap(String hostIP,int hostPort);
	/**
	 * 通过htmlunit启动URL
	 * @param url
	 * @param jsEnable 是否启用js
	 * @param version 浏览器版本
	 */
	void openNewWindowByHtmlUnit(String url,boolean jsEnable,BrowserVersion version);
	/**
	 * 通过host启动URL
	 * @param url
	 * @param jsEnable 是否启用js
	 * @param version 浏览器版本
	 * @param hostIP
	 * @param hostPort
	 */
	void openNewWindowByHtmlUnit(String url,boolean jsEnable,BrowserVersion version,String hostIP,int hostPort);
	/**
	 * 使用Phantomjs驱动执行被测用例，Phantomjs执行用例时没有界面，url的加载，元素的定位等<br>
	 * 都在内存中完成。在js兼容性方面比httpunit要好。
	 * @param url
	 */
	void openNewWindowByPhantomJs(String url);
	/**
	 * 通过host启动URL
	 * @param url
	 * @param hostIP hostIP地址
	 * @param hostPort  host端口
	 */
	void openNewWindowByPhantomJs(String url,String hostIP,int hostPort);
	/*
	 * 使用Ie打开被测的页面
	 */
	void openNewWindowByIE(String url);
	/**
	 * 通过host启动URL
	 * @param url
	 * @param hostIP hostIP地址
	 * @param hostPort  host端口
	 */
	void openNewWindowByIE(String url,String hostIP,int hostPort);
	/*
	 * 使用Chrome打开被测的页面
	 */
	void openNewWindowByChrome(String url);
	/**
	 * 通过host启动URL
	 * @param url
	 * @param hostIP hostIP地址
	 * @param hostPort  host端口
	 */
	void openNewWindowByChrome(String url,String hostIP,int hostPort);
	/*
	 * 使用Firefox打开被测的页面
	 */
	void openNewWindowByFirefox(String url);
	/**
	 * 通过host启动URL
	 * @param url
	 * @param hostIP hostIP地址
	 * @param hostPort  host端口
	 */
	void openNewWindowByFirefox(String url,String hostIP,int hostPort);
	
	/**
	 * 如果有打开的浏览器窗口，则关闭
	 */
	void closeWindow();
	/**
	 * 关闭窗口的同时清理后台相关的进程程序
	 */
	void closeWindowAndDriverExe();
	/**
	 * 仅清理相关后台进程程序
	 */
	void closeDriverExe();
	/**
	 * 杀掉某个进程，可根据pid，可在Linux环境下使用
	 * @param cmdArray
	 */
	void killProcess(String[] cmdArray);
	
	/**
	 * 对元素进行点击操作
	 */
	void click();
	/**
	 * 对可输入的输入框输入值
	 */
	void setText(String text);
	
	/**
	 * 获取可视的innerText值
	 */
	String getText();
	
	/**
	 * 获取指定属性的值
	 */
	String getAttrValue(String attr);
	
	/**
	 * 暂停执行的步骤
	 */
	void sleep(long ms);
	
	/**
	 * 在文本域中追加字符
	 */
	void append(String str);
	
	/**
	 * 在元素上按Enter键
	 */
	void pressEnter();
	
	/**
	 * 在元素上按Tab键
	 */
	void pressTab();
	
	
	String innerText();
	
	/**
	 * 返回指定元素html信息
	 */
	String innerHtml();
	
	/**
	 * 返回指定元素的name值
	 */
	String name();
	
	/**
	 * 判断元素是否存在
	 */
	boolean exists();
	
	/**
	 * 选中或取消选中
	 */
	void setSelected(boolean selected);
	
	/**
	 * 在指定时间等待操作
	 */
	void waitUntil(Condition condition, long timeoutMilliseconds);
	

/*	SelenideElement $(String cssSelector);
	SelenideElement $(String cssSelector, int index);
	SelenideElement $(By selector);
	SelenideElement $(By selector, int index);
	ElementsCollection $$(String cssSelector);*/
	ElementsCollection getElements();
	
	/**
	 * 上传一个文件
	 */
	File uploadFile(String filePath);
	
	/**
	 * 根据指定的字符选择
	 */
	void selectOption(String text);
	
	/**
	 * 根据下拉框的value选择
	 */
	void selectOptionByValue(String value);
	
	/**
	 * 获取下拉框已被选择的值
	 */
	String getSelectedValue();
	
	/**
	 * 获取下拉框已被选择的数据
	 * @return
	 */
	String getSelectedText();
	
	/**
	 * 根据指定的对象下载文件
	 */
	File download() throws FileNotFoundException;
	
	/**
	 * 右键单击鼠标
	 */
	SelenideElement contextClick();
	
	/**
	 * 将鼠标悬浮在一个元素上
	 */
	SelenideElement hover();
	
	/**
	 * 将指定元素拖拽到指定的位置，目标定位方式为css
	 */
	SelenideElement dragAndDropTo(String targetCssSelector);
	
	/**
	 * 判断是否是图像
	 */
	boolean isImage();
	
	SelenideElement parent();
	
	SelenideElement waitWhile(Condition condition, long timeoutMilliseconds);
	
	boolean isDisplayed();

	SelenideElement scrollTo();

	SelenideElement getSelectedOption();

	String getCssValue(String propertyName);

	boolean isEnabled();

	boolean isSelected();

	String getAttribute(String name);

	String getTagName();

	void clear();

	void sendKeys(String str);

	void submit();
	
	void switchToWindow(String title);
	
	void switchToWindow(int index);
	
	void confirm(String expectedDialogText);
	
	SelenideElement selectRadio(String value);
	
	SelenideElement getSelectedRadio();
	
	void dismiss(String expectedDialogText);
	
	List<String> getJavascriptErrors();
	
	List<String> getWebDriverLogs(String logType, Level logLevel);
	
	void refresh();
	
	void switchToFrame(String nameOrId);
	
	void switchToParent();
	
	void back();
	
	void forward();
	
	String title();
	
	String screenshot(String fileName);
	
	String getPageSource();
	
	public void doubleClick();

}
