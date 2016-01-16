package org.phoenix.action;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.openqa.selenium.By;
import org.phoenix.enums.LocatorType;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

/**
 * 定位类型选择
 * @author mengfeiyang
 *
 */
public class WebElementLocator {
	
	protected SelenideElement WebElement(String locator, LocatorType locatorType){
		switch (locatorType) {
			case CSS:  return $(locator);
			case ID:return $(By.id(locator));
			case CLASS:return $(By.className(locator));
			case NAME: return $(By.name(locator));
			case XPATH: return $(By.xpath(locator));
			case LINKTEXT:return $(By.linkText(locator));
			case TAGNAME:return $(By.tagName(locator));
			case PARTIALLINKTEXT:return $(By.partialLinkText(locator));
		default:;
	    }
		return null;
	}
	
	protected By by(String locator, LocatorType locatorType){
		switch (locatorType) {
			case CSS:  return By.cssSelector(locator);
			case ID:
				return By.id(locator.startsWith("#")?locator.substring(1, locator.length()):locator);
			case CLASS:
				return By.className(locator.startsWith(".")?locator.substring(1, locator.length()):locator);
			case NAME: return By.name(locator);
			case XPATH: return By.xpath(locator);
			case LINKTEXT:return By.linkText(locator);
			case TAGNAME:return By.tagName(locator);
			case PARTIALLINKTEXT:return By.partialLinkText(locator);
		default:;
    }
		return null;
	}
	
	protected ElementsCollection WebElements(String locator, LocatorType locatorType){
		switch (locatorType) {
			case CSS:  return $$(locator);
			case NAME: return $$(By.name(locator));
			case XPATH: return $$(By.xpath(locator));
			case LINKTEXT:return $$(By.linkText(locator));
			case TAGNAME:return $$(By.tagName(locator));
			case PARTIALLINKTEXT:return $$(By.partialLinkText(locator));
		default:;
	    }
		return null;
	}
}
