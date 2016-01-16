package org.phoenix.mobile.android.action;
import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.standalone.SelendroidConfiguration;
import io.selendroid.standalone.SelendroidLauncher;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * 对浏览器进行测试
 * @author mengfeiyang
 *
 */
public class MobileWebTest {
  private SelendroidLauncher selendroidServer = null;
  private WebDriver driver = null;

  @Test
  public void shouldSearchWithEbay() {
    driver.get("http://www.baidu.com");
    System.out.println(driver.getPageSource());
    WebElement element = driver.findElement(By.id("index-kw"));
    element.sendKeys("Nexus 5");
    element.submit();
    System.out.println("Page title is: " + driver.getTitle());
    driver.quit();
  }

  @Before
  public void startSelendroidServer() throws Exception {
    if (selendroidServer != null) {
      selendroidServer.stopSelendroid();
    }
    SelendroidConfiguration config = new SelendroidConfiguration();

    selendroidServer = new SelendroidLauncher(config);
    selendroidServer.launchSelendroid();

    DesiredCapabilities caps = SelendroidCapabilities.android();

    driver = new SelendroidDriver(caps);
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
}