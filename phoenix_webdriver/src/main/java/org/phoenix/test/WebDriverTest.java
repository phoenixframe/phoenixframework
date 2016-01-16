package org.phoenix.test;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * http://selenide.org/
 * @author mengfeiyang
 *
 */

public class WebDriverTest {
    @Before
    public void before(){
    	
    }
	@Test
	public void test() {
		open("/login");
		$(By.name("user.name")).setValue("johny");
		$("#submit").click();
		$(".loading_progress").should(disappear); // Waits until element disappears
		$("#username").shouldHave(text("Hello, Johny!")); // Waits until element gets text
	}

}
