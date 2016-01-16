package org.phoenix.mobile.android.action;
import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HybridAppTest {
  private WebDriver driver = null;

  @Before
  public void setup() throws Exception {
    driver = new SelendroidDriver(new SelendroidCapabilities("io.selendroid.directory:0.0.1"));
    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
  }

  @Test
  public void assertEmployeeCanBeDisplayedWithDirects() throws Exception {
    driver.get("and-activity://io.selendroid.directory.EmployeeDirectory");

    driver.switchTo().window("WEBVIEW");

    String vpOfEngineering = "John Williams";
    driver.findElement(By.tagName("input")).sendKeys(vpOfEngineering);
    driver.findElement(By.partialLinkText(vpOfEngineering)).click();
    Assert.assertEquals(driver.getCurrentUrl(), "file:///android_asset/www/index.html#employees/4");

    System.out.println(driver.findElements(By.tagName("li")).get(0).getText());

    WebElement directs = driver.findElements(By.tagName("li")).get(1);
    System.out.println(directs.getText());
    directs.click();
    Assert.assertEquals(driver.getCurrentUrl(),
        "file:///android_asset/www/index.html#employees/4/reports");

    System.out.println(driver.findElements(By.tagName("li")).get(0).getText());
    System.out.println(driver.findElements(By.tagName("li")).get(1).getText());
    System.out.println(driver.findElements(By.tagName("li")).get(2).getText());

    driver.navigate().back();

    Assert.assertEquals(driver.getCurrentUrl(), "file:///android_asset/www/index.html#employees/4");
  }

  @After
  public void teardown() {
    driver.quit();
  }
}