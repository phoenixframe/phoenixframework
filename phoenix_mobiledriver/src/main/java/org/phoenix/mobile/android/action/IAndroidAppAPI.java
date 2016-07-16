package org.phoenix.mobile.android.action;

import io.selendroid.client.DriverCommand;
import io.selendroid.client.MultiTouchScreen;
import io.selendroid.client.SelendroidDriver;
import io.selendroid.client.TouchAction;
import io.selendroid.client.adb.AdbConnection;
import io.selendroid.server.common.utils.CallLogEntry;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.phoenix.enums.LocatorType;
import org.phoenix.mobile.action.IMobileCommon;

/**
 * android设备基本操作类型接口
 * @author mengfeiyang
 *
 */
public interface IAndroidAppAPI extends IMobileCommon{
	
	/**
	 * 用Android虚拟机启动被测app
	 * @param appPath
	 */
	void openAndroidAppBySelendroidWithEmulator(String appPath);
	
	/**
	 * 用Android实体设备启动被测app
	 * @param appPath
	 */
	void openAndroidAppBySelendroidWithDevice(String appPath);
	/**
	 * 启动Android浏览器，
	 * @param url
	 */
	void openAndroidBrowserBySelendroid(String url);
	/**
	 * 获取启动状态
	 * @return
	 */
	String getAndroidStatus();
	/**
	 * 获取appId
	 * @return
	 */
	String getTargetAppId();
	/**
	 * 启动SelendroidServer
	 * @param appPath
	 */
	void luncherSelendroidServer(String appPath);
	
	/**
	 * 获取touchAction对象
	 * @param element
	 * @param ms
	 * @param x
	 * @param y
	 * @return
	 */
	TouchAction  touchAction(WebElement element,int ms,int x,int y);
	/**
	 * 多点触控操作
	 * @param touchAction
	 */
	void multiTouchElement(TouchAction...touchAction);
	/**
	 * 获得一个对元素轻轻滑动的操作对象
	 * @return
	 */
	TouchActions getTouchActions();
	/**
	 * 对一个指定的元素上模拟手指轻轻滑动的操作
	 * @param onElement
	 * @param xOffset
	 * @param yOffset
	 * @param speed
	 */
	void flickElement(WebElement onElement, int xOffset, int yOffset, int speed);
	/**
	 * 返回当前的selendroiddriver
	 * @return
	 */
	SelendroidDriver getSelendroidDriver();	
	/**
	 * 发送键盘编码按下操作
	 * @param keyCode
	 */
	void pressKeyByKeyboard(String keyCode);
	/**
	 * 发送键盘编码释放操作
	 * @param keyCode
	 */
	void releaseKeyByKeyboard(String keyCode);
	/**
	 * 获取targetapi类型
	 * @return
	 */
	String getAPITargetType();
	/**
	 * 获取屏幕大小
	 * @return
	 */
	String getScreenSize();
	/**
	 * 获得键盘对象
	 * @return
	 */
	Keyboard getKeyboard();
	/**
	 * 停止selendroidServer
	 */
	void closeSelendroidServer();
	/**
	 * 获得TouchScreen的对象
	 * @return
	 */
	TouchScreen getTouch();
	WebElement linkFinder(String locatorData);
	WebElement linkFinder(String locatorData, LocatorType locatorType);
	IAndroidAppAPI element(String locatorData, LocatorType locatorType);
	IAndroidAppAPI element(String name);
	void setAndroidAPIProxy(IAndroidAppAPI androidAPIProxy);
	/**
	 * 滑动屏幕
	 * @param dimensionX
	 * @param dimensionY
	 */
	void roll(int dimensionX, int dimensionY);
	/**
	 * 获取多点触控对象
	 * @return
	 */
	MultiTouchScreen getMultiTouchScreen();
	<X> X getScreenshotAs(OutputType<X> target);
	/**
	 * 获取当前屏幕的亮度百分比
	 * @return
	 */
	int getBrightness();
	/**
	 * 设置亮度
	 * @param desiredBrightness
	 */
	void setBrightness(int desiredBrightness);
	/**
	 * 翻转屏幕
	 * @param orientation
	 */
	void rotate(ScreenOrientation orientation);
	/**
	 * 翻转类型
	 * @return
	 */
	ScreenOrientation getOrientation();
	void setConfiguration(DriverCommand command, String key, Object value);
	Map<String, Object> getConfiguration(DriverCommand command);
	/**
	 * 获取adbConnection对象
	 * @return
	 */
	AdbConnection getAdbConnection();
	/**
	 * 判断当前是否是飞行模式
	 * @return
	 */
	boolean isAirplaneModeEnabled();
	/**
	 * 设置飞行模式
	 * @param enabled
	 */
	void setAirplaneMode(boolean enabled);
	void tap(int x, int y);
	/**
	 * 通过adbConnection方式为app元素设置值
	 * @param text
	 */
	void appTextFieldSendTextByAdb(String text);
	/**
	 * 为app元素设置值
	 * @param text
	 */
	void setText(String text);
	/**
	 * 清空app的元素值
	 */
	void clear();
	/**
	 * 判断该元素是否已显示
	 * @return
	 */
	boolean isDisplayed();
	/**
	 * 判断元素是否可用
	 * @return
	 */
	boolean isEnabled();
	/**
	 * 判断元素是否已被选择
	 * @return
	 */
	boolean isSelected();
	/**
	 * 对app元素作单击操作
	 */
	void click();
	/**
	 * 对元素模拟双击
	 */
	void doubleClick();
	/**
	 * 对一个元素做长按操作
	 */
	void clickAndHold();
	/**
	 * 获取元素的一个集合
	 * @return
	 */
	List<WebElement> getElements();
	/**
	 * 获取元素的标签名称
	 * @return
	 */
	String getElementTagName();
	/**
	 * 获取元素属性值
	 * @param name
	 * @return
	 */
	String getAttribute(String name);
	/**
	 * 如果有css属性，则获取元素的css值
	 * @param propertyName
	 * @return
	 */
	String getCssValue(String propertyName);
	/**
	 * 获取元素的Point对象
	 * @return
	 */
	Point getLocation(); 
	/**
	 * 获取元素的大小信息
	 */
	Dimension getAppDimension();
	
	/**
	 * 发送键盘事件
	 * @param keyCode
	 */
	void sendKeyEvent(int keyCode);
	String executeShellCommand(String command);
	Set<String> getContextHandles();
	String getContext();
	/**
	 * 将app在后台运行
	 */
	void backgroundApp();
	/**
	 * 恢复app状态
	 */
	void resumeApp();
	void addCallLog(CallLogEntry log);
	List<CallLogEntry> readCallLog();
	Object callExtension(String extensionMethod);
	Object callExtension(String extensionMethod, Map<String, ?> parameters);
	/**
	 * 设置系统属性
	 * @param propertyName
	 * @param value
	 */
	void setSystemProperty(String propertyName, String value);
	/**
	 * 垃圾回收对象
	 */
	void gc();
	
}
