package org.phoenix.mobile.android.action;

import io.selendroid.client.MultiTouchAction;
import io.selendroid.client.SelendroidDriver;
import io.selendroid.client.TouchAction;
import io.selendroid.client.TouchActionBuilder;
import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.server.common.action.touch.FlickDirection;
import io.selendroid.standalone.SelendroidConfiguration;
import io.selendroid.standalone.SelendroidLauncher;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;

/**
 * 对移动设备支持的手势操作： <br>
 *  doubleTap(WebElement onElement) --对元素模拟手指双击 <br>
	down(int x, int y) --模拟下滑 <br>
	flick(int xSpeed, int ySpeed) --模拟快速滑动 <br>
	flick(WebElement onElement, int xOffset, int yOffset, int speed) <br>
	longPress(WebElement onElement) --模拟长按动作 <br>
	move(int x, int y) --模拟移动元素 <br>
	singleTap(WebElement onElement)  --对元素模拟手指单击 <br>
	up(int x, int y)  --模拟上滑 <br>
 * @author mengfeiyang
 *
 */
public class MobileHandOper {
	  private SelendroidLauncher selendroidServer = null;
	  private WebDriver driver = null;
	  private WebElement pages;
	  @Before
	  public void startSelendroidServer() throws Exception {
	    if (selendroidServer != null) {
	      selendroidServer.stopSelendroid();
	    }
	    SelendroidConfiguration config = new SelendroidConfiguration();
	    config.addSupportedApp("F:\\baozhanggl.apk");
	    selendroidServer = new SelendroidLauncher(config);
	    selendroidServer.launchSelendroid();
		SelendroidCapabilities capa = SelendroidCapabilities.emulator("combaozhang.com:1.0");
	    //DesiredCapabilities caps = SelendroidCapabilities.android();

	    driver = new SelendroidDriver(capa);
	    
		pages = driver.findElement(By.id("TextField2"));
	  }

	  @After
	  public void stopSelendroidServer() {
	    if (driver != null) {
	      driver.quit();
	    }
	    if (selendroidServer != null) {
	      selendroidServer.stopSelendroid();
	    }
	  }
	@Test
	public void test01(){
		//FlickDirection.UP;
		TouchActions flick = new TouchActions(driver).flick(pages, 30, 50, 11);
		flick.perform();
	}
	/**
	 * 多点触摸之 -- 单手指操作
	 */
	@Test
	public void test02(){
		TouchAction ta = new TouchActionBuilder().pointerDown(pages).
				pointerMove(100, 0).pointerUp().build(); 
		ta.perform(driver);
	}
	
	/**
	 * 多点触摸之 -- 多手指操作
	 */
	@Test
	public void test03(){
		TouchAction finger1 = new TouchActionBuilder().pointerDown(pages).pause(100).
				  pointerMove(100, 0).pointerUp().build(); 
		TouchAction finger2 = new TouchActionBuilder().pointerDown(pages).pause(100).
				  pointerMove(-100, 0).pointerUp().build(); 
		MultiTouchAction multiAction = new MultiTouchAction(finger1, finger2); 
		multiAction.perform(driver);
	}
	
}
