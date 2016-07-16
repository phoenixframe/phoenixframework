package org.phoenix.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.gargoylesoftware.htmlunit.BrowserVersion;

/**
 * Web元素操作
 * @author mengfeiyang
 *
 */
public interface IWebAPI {
	/**
	 * 执行js
	 * @return
	 */
	Object executeScript(String script, Object...args);
	/**
	 * 执行js
	 * @param script
	 * @param args
	 * @return
	 */
	Object executeAsyncScript(String script, Object...args);
	/**
	 * 不使用任何定位信息时，如close方法
	 * @return
	 */
	IWebAPI webElement();
	/**
	 * 指定一个定位信息的标识，需要先将其录入数据库之后才会有该标识
	 * @param name
	 * @return
	 */
	IWebAPI webElement(String name);
	/**
	 * 直接使用定位信息而无需将其先录入数据库
	 * @param locatorData
	 * @param locatorType 如果为null，则默认为locatorType 为CSS。Class和id可直接作为Css定位
	 * @return
	 */
	IWebAPI webElement(String locatorData,LocatorType locatorType);
	
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
	void setWebProxy(IWebAPI webProxy);
	/**
	 * 返回一个带有代理IP的DesiredCapabilities
	 * @param hostIP
	 * @param hostPort
	 * @return
	 */
	DesiredCapabilities getProxyCap(String hostIP,int hostPort);
	/**
	 * 通过htmlunit启动URL<br>
	 *  更多设置项：<br>
	 * 	<em>设置页面默认编码:HttpUnitOptions.setDefaultCharacterSet("UTF-8"); </em><br>
	 *	<em>禁用js语法检测:HttpUnitOptions.setExceptionsThrownOnScriptError(false); </em><br>
	 *	<em>异常状态码是否抛异常：HttpUnitOptions.setExceptionsThrownOnErrorStatus(false);</em><br>
	 *	<em>是否启用js：HttpUnitOptions.setScriptingEnabled(true);</em><br>
	 * @param url
	 * @param jsEnable 是否启用js
	 * @param version 浏览器版本
	 */
	void openNewWindowByHtmlUnit(String url,boolean jsEnable,BrowserVersion version);
	/**
	 * 通过htmlunit启动URL<br>
	 *  更多设置项：<br>
	 * 	<em>设置页面默认编码:HttpUnitOptions.setDefaultCharacterSet("UTF-8"); </em><br>
	 *	<em>禁用js语法检测:HttpUnitOptions.setExceptionsThrownOnScriptError(false); </em><br>
	 *	<em>异常状态码是否抛异常：HttpUnitOptions.setExceptionsThrownOnErrorStatus(false);</em><br>
	 *	<em>是否启用js：HttpUnitOptions.setScriptingEnabled(true);</em><br>
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
	/**
	 * 使用Edge浏览器打开地址
	 * @param url
	 */
	void openNewWindowByEdge(String url);
	/**
	 * 使用Edge浏览器打开地址
	 * @param url
	 * @param hostIP hostIP地址
	 * @param hostPort host端口
	 */
	void openNewWindowByEdge(String url,String hostIP,int hostPort);
	
	/**
	 * 使用Safari浏览器打开地址
	 * @param url
	 */
	void openNewWindowBySafari(String url);
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
	 * 浏览器启动后，通过此方法能够获取到浏览器类型
	 * @return
	 */
	CaseLogBean getCaseLogBean();
	
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
	/**
	 * 选择父元素
	 * @return
	 */
	
	SelenideElement parent();
	/**
	 * 动态等待
	 * @param condition
	 * @param timeoutMilliseconds
	 * @return
	 */
	SelenideElement waitWhile(Condition condition, long timeoutMilliseconds);
	/**
	 * 元素是否已在页面显示
	 * @return
	 */
	boolean isDisplayed();
	/**
	 * 滚动鼠标
	 * @return
	 */
	SelenideElement scrollTo();
	/**
	 * 获取选项
	 * @return
	 */
	SelenideElement getSelectedOption();
	/**
	 * 获取css属性值
	 * @param propertyName
	 * @return
	 */
	String getCssValue(String propertyName);
	/**
	 * 元素是否可用
	 * @return
	 */
	boolean isEnabled();
	/**
	 * 是否已选中
	 * @return
	 */
	boolean isSelected();
	/**
	 * 获取元素属性值
	 * @param name
	 * @return
	 */
	String getAttribute(String name);
	/**
	 * 获取标签名
	 * @return
	 */
	String getTagName();
	/**
	 * 清除
	 */
	void clear();
	/**
	 * 输入字符
	 * @param str
	 */
	void sendKeys(String str);

	void submit();
	/**
	 * 窗口切换，根据标题
	 * @param title
	 */
	void switchToWindow(String title);
	/**
	 * 窗口切换，根据序号
	 * @param index
	 */
	void switchToWindow(int index);
	/**
	 * 弹出框处理：确定
	 * @param expectedDialogText
	 */
	void confirm(String expectedDialogText);
	/**
	 * 选择单选框
	 * @param value
	 * @return
	 */
	SelenideElement selectRadio(String value);
	/**
	 * 获取选择的单选框
	 * @return
	 */
	SelenideElement getSelectedRadio();
	/**
	 * 弹出框处理：取消
	 * @param expectedDialogText
	 */
	void dismiss(String expectedDialogText);
	/**
	 * 获取js错误
	 * @return
	 */
	List<String> getJavascriptErrors();
	/**
	 * 获取webdriver日志
	 * @param logType
	 * @param logLevel
	 * @return
	 */
	List<String> getWebDriverLogs(String logType, Level logLevel);
	/**
	 * 刷新
	 */
	void refresh();
	/**
	 * 切换frame
	 * @param nameOrId
	 */
	void switchToFrame(String nameOrId);
	/**
	 * 选择父frame
	 */
	void switchToParent();
	/**
	 * 后退
	 */
	void back();
	/**
	 * 前进
	 */
	void forward();
	/**
	 * 获取网页标题
	 * @return
	 */
	String title();
	/**
	 * 截图
	 * @param fileName
	 * @return
	 */
	String screenshot(String fileName);
	/**
	 * 获取页面源码
	 * @return
	 */
	String getPageSource();
	/**
	 * 双击操作
	 */
	public void doubleClick();
}
