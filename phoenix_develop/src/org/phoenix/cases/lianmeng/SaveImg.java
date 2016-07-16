package org.phoenix.cases.lianmeng;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.Actions;
import org.phoenix.cases.plugin.SaveImgToLocal;
import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.plugins.IImageReader;
import org.phoenix.proxy.ActionProxy;

import com.codeborne.selenide.SelenideElement;
import com.google.common.io.Files;

/**
 * 将页面上定位到的图片保存到本地，特别适用于无具体地址的验证码图片
 * 
 * @author mengfeiyang
 *
 */
public class SaveImg extends ActionProxy {
	private String localFile = "E:\\testcode.jpg";
	@Override
	public LinkedList<UnitLogBean> run(CaseLogBean caseLogBean) {
		init(caseLogBean);
		try {
			phoenix.webAPI().setFirefoxExePath("D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			phoenix.webAPI().openNewWindowByFirefox("https://passport.csdn.net/account/fpwd?action=forgotpassword&service=http%3A%2F%2Fmy.csdn.net%2Fmy%2Fmycsdn");
			// phoenix.webAPI().openNewWindowByIE("http://www.oschina.net/home/reset-pwd");
			// phoenix.webAPI().openNewWindowByChrome("http://www.oschina.net/home/reset-pwd");
			phoenix.webAPI().getCurrentDriver().manage().timeouts().pageLoadTimeout(2, TimeUnit.SECONDS);
			phoenix.webAPI().getCurrentDriver().manage().window().maximize();
			phoenix.webAPI().sleep(2000);			
			String engine = phoenix.webAPI().getCaseLogBean().getEngineType();
			phoenix.webAPI().webElement(".user-name", null).setText("11111");
			for(int i=0;i<10;i++){
			SelenideElement element = phoenix.webAPI().webElementLinkFinder(
					"#yanzheng", null);
			phoenix.webAPI().sleep(1000);
			SaveImgToLocal.doSave(element, engine, localFile);
			IImageReader imageReader = phoenix.imageReader().configImageReader("D://Program Files (x86)//Tesseract-OCR");
			String oos = imageReader.readLocalImage(localFile, Files.getFileExtension(localFile));
			System.out.println("-----"+oos+"------");
			phoenix.webAPI().webElement(".code", null).setText(oos);
			phoenix.webAPI().sleep(3000);
			phoenix.webAPI().webElement("//input[@type='submit']", LocatorType.XPATH).click();
			phoenix.webAPI().sleep(5000);
				if(!phoenix.webAPI().webElement(".email-address", null).exists()){
					new Actions(phoenix.webAPI().getCurrentDriver()).doubleClick(element).perform();
				}else break;
			}
			phoenix.webAPI().sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		phoenix.webAPI().closeWindow();
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