package org.phoenix.mobile.android.action;

import io.selendroid.standalone.SelendroidConfiguration;
import io.selendroid.standalone.SelendroidLauncher;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidWebBrowserTest {
	private WebDriver driver;
	private SelendroidLauncher selendroidServer = null;
	@Before
	public void start(){
	    SelendroidConfiguration config = new SelendroidConfiguration();
	    selendroidServer = new SelendroidLauncher(config);
	    selendroidServer.launchSelendroid();
		driver = new RemoteWebDriver(DesiredCapabilities.android());
	}
	
	@Test
	public void test() throws InterruptedException {
		driver.get("http://www.baidu.com");
		Thread.sleep(3000);
		String s = driver.getPageSource();
		System.out.println(s);
		driver.findElement(By.id("index-kw")).sendKeys("test123");
		Thread.sleep(3000);
		driver.findElement(By.id("index-bn")).click();
		Thread.sleep(3000);
		System.out.println(driver.getPageSource());
		//driver.findElement(By.xpath("//*[@id=\"h-tabs\"]/a[3]/span")).click();
		driver.findElement(By.xpath("//*[@id=\"h-tabs\"]/a[3]")).click();
		Thread.sleep(6000);
	}
	
	@After
	public void end(){
		driver.quit();
		selendroidServer.stopSelendroid();
	}

}
