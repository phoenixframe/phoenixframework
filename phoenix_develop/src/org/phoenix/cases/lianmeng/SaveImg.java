package org.phoenix.cases.lianmeng;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.Actions;
import org.phoenix.action.WebElementActionProxy;
import org.phoenix.cases.plugin.SaveImgToLocal;
import org.phoenix.enums.LocatorType;
import org.phoenix.imgreader.action.IImageReader;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

import com.codeborne.selenide.SelenideElement;
import com.google.common.io.Files;

/**
 * 将页面上定位到的图片保存到本地，特别适用于无具体地址的验证码图片
 * 
 * @author mengfeiyang
 *
 */
public class SaveImg extends WebElementActionProxy {
	private static String caseName = "test";
	private String localFile = "E:\\testcode.jpg";
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean arg0) {
		init(caseName, arg0);
		try {
			webProxy.setFirefoxExePath("D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			webProxy.openNewWindowByFirefox("https://passport.csdn.net/account/fpwd?action=forgotpassword&service=http%3A%2F%2Fmy.csdn.net%2Fmy%2Fmycsdn");
			// webProxy.openNewWindowByIE("http://www.oschina.net/home/reset-pwd");
			// webProxy.openNewWindowByChrome("http://www.oschina.net/home/reset-pwd");
			webProxy.getCurrentDriver().manage().timeouts().pageLoadTimeout(2, TimeUnit.SECONDS);
			webProxy.getCurrentDriver().manage().window().maximize();
			webProxy.sleep(2000);			
			String engine = arg0.getEngineType();
			webProxy.webElement(".user-name", null).setText("11111");
			for(int i=0;i<10;i++){
			SelenideElement element = webProxy.webElementLinkFinder(
					"#yanzheng", null);
			webProxy.sleep(1000);
			SaveImgToLocal.doSave(element, engine, localFile);
			IImageReader imageReader = webProxy.imageReader().configImageReader("D://Program Files (x86)//Tesseract-OCR");
			String oos = imageReader.readLocalImage(localFile, Files.getFileExtension(localFile));
			System.out.println("-----"+oos+"------");
			webProxy.webElement(".code", null).setText(oos);
			webProxy.sleep(3000);
			webProxy.webElement("//input[@type='submit']", LocatorType.XPATH).click();
			webProxy.sleep(5000);
				if(!webProxy.webElement(".email-address", null).exists()){
					new Actions(webProxy.getCurrentDriver()).doubleClick(element).perform();
				}else break;
			}
			webProxy.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		webProxy.closeWindow();
		return getUnitLog();
	}

	public static void main(String[] args) {
		SaveImg saveImg = new SaveImg();
		LinkedList<UnitLogBean> us = saveImg.run(new CaseLogBean());
		for (UnitLogBean u : us) {
			System.out.println(u.getContent());
		}
	}

}