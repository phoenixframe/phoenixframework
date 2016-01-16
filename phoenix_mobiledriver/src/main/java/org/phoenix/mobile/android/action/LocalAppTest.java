package org.phoenix.mobile.android.action;

import io.selendroid.client.SelendroidDriver;
import io.selendroid.client.SelendroidKeys;
import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.standalone.SelendroidConfiguration;
import io.selendroid.standalone.SelendroidLauncher;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.phoenix.mobile.powertools.GetXml;

/**
 * android app测试用例示例。硬件编码：<br>
 * ALT_LEFT \uE00A <br>
 * DEL \uE017 <br>
 * DPAD_DOWN \uE017 <br>
 * DPAD_LEFT \uE012 <br>
 * DPAD_RIGHT \uE014 <br>
 * DPAD_UP \uE013 <br>
 * ENTER \uE007 <br>
 * SHIFT_LEFT \uE008 <br>
 * BACK \uE100 <br>
 * <br>
 * ANDROID_HOME \uE101 <br>
 * MENU \uE102 <br>
 * SEARCH \uE103 <br>
 * SYM \uE104 <br>
 * ALT_RIGHT \uE105 <br>
 * SHIFT_RIGHT \uE106 <br>
 * 
 * @author mengfeiyang
 *
 */
public class LocalAppTest {
	public static void main(String args[]) throws Exception {
		LocalAppTest appTest1 = new LocalAppTest();
		appTest1.f();
	}

	public void f() throws Exception {
		SelendroidConfiguration config = new SelendroidConfiguration();
		config.addSupportedApp("F:\\baozhanggl.apk");
		SelendroidLauncher selendroidServer = new SelendroidLauncher(config);
		selendroidServer.launchSelendroid();
		//String json = GetXml.getResponseByGet("http://localhost:4444/wd/hub/status").getText();
		//String appid = apiAction.getJSONValue(json, "JSON.value.supportedApps[0].appId");
		System.out.println("Test Start:");
		SelendroidCapabilities capa = SelendroidCapabilities.emulator("combaozhang.com:1.0");
		// AndroidDriver,SelendroidDriver,WebDriver
		System.out.println("version:"+capa.getVersion());
		SelendroidDriver driver = new SelendroidDriver(capa);

		System.out.println("Running Case:");
		WebElement inputField = driver.findElement(By.id("TextField1"));
		System.out.println("----Element Type:" + inputField.getTagName()
				+ "  |Value:" + inputField.getText());
		driver.findElement(By.id("TextField2")).sendKeys("shuruceshi");
		driver.findElement(By.id("gaoji")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("chushimima")).sendKeys("test123");
		driver.findElement(By.xpath("(//TextView[@id='text1'])[1]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//CheckedTextView[@id='text1'])[2]"))
				.click();
		// new Select(driver.findElement(By.id("xuanmoban"))).selectByIndex(2);
		driver.findElement(By.xpath("//Button[@id='button1']")).click();
		 //driver.getKeyboard().pressKey("\uE100");//对返回键操作
		 //new Actions(driver).sendKeys(SelendroidKeys.MENU).perform();//点击MENU键
		Thread.sleep(3000);
		driver.setBrightness(80);
		Thread.sleep(3000);
		driver.setBrightness(40);
		Thread.sleep(3000);
		/*
		 * if(!driver.isAirplaneModeEnabled())driver.setAirplaneMode(true);//设置飞行模式
		 * else driver.setAirplaneMode(false); Thread.sleep(3000);
		 */
		driver.rotate(ScreenOrientation.LANDSCAPE);// 将屏幕横屏显示
		driver.roll(50, 10);
		new Actions(driver).sendKeys(SelendroidKeys.MENU).perform();// 点击MENU键
		System.out.println("Test End.");
		// driver.wait(Long.parseLong(3000+""));

		driver.quit();
		
		selendroidServer.stopSelendroid();
	}

}
