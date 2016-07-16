package org.phoenix.cases;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.phoenix.proxy.ActionProxy;

import com.codeborne.selenide.SelenideElement;

public class TestSelenide {
	
	@Before
	public void before(){
		System.setProperty("webdriver.ie.driver", 
				ActionProxy.class.getResource("/").getPath()+"drivers/IEDriverServer64.exe");
		setWebDriver(new InternetExplorerDriver());
	}
	
	@Test
	public void test03(){
		open("http://lianmeng.360.cn");
		WebElement w = $(".control").findElementByTagName("img");
		w.getSize();
	}
	
	@Test
	public void test02(){
		open("http://1.163.com");
		if($(".w-msgbox-close").exists())$(".w-msgbox-close").click();
		$(By.linkText("请登录")).click();
		$("#pro-view-18").$(".w-input-input").setValue("5");
		$("#pro-view-20").$(".w-input-input").setValue("1");
		$("#pro-view-16").$(By.tagName("button")).click();
		close();
	}
	
	@Test
	public void test01(){
		open("https://www.baidu.com");
		$("#kw").setValue("1");
		$("#su").getAttribute("value");
		$("#su").click();
		
		$("#su").val();
		
		
		sleep(2000);
		Iterator<SelenideElement> els = $(".s_tab").$$(By.tagName("a")).iterator();
		while(els.hasNext()){
			SelenideElement se = els.next();
			if(se.getText().equals("文库")){
				se.click();
				break;
			}
			System.out.println(se.getText());
		}
		sleep(3000);
		close();
	}

}
