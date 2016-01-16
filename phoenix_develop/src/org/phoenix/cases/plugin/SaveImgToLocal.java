package org.phoenix.cases.plugin;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import com.codeborne.selenide.SelenideElement;
import com.google.common.io.Files;

/**
 * 保存页面上的指定区域的图片到本地
 * @author mengfeiyang
 *
 */
public class SaveImgToLocal {
	//本方法已经集成到了平台中，在平台中直接使用：webProxy.saveImgToLocal(element, engine, localFile);即可
	public static void doSave(SelenideElement element,String engine,String localFilePath) throws AWTException, IOException{
		Point selenPoint = element.getLocation();
		Dimension selenDim = element.getSize();
		Robot r = new Robot();
		Rectangle rt = new Rectangle();
		switch (engine) {
		case "IEDriver":
			//不同浏览器对于图片的位置有所不同，此处用于对图片位置进行微调，保存时，需要将浏览器至于窗口最前，否则将无法得到想要的结果
			rt.setBounds(selenPoint.getX() - 1, selenPoint.getY() + 55,selenDim.getWidth(), selenDim.getHeight());
			break;
		case "FirefoxDriver":
			rt.setBounds(selenPoint.getX(), selenPoint.getY() + 64,selenDim.getWidth(), selenDim.getHeight());
			break;
		case "ChromeDriver":
			rt.setBounds(selenPoint.getX(), selenPoint.getY(),selenDim.getWidth(), selenDim.getHeight());
			break;
		default:;
		}

		BufferedImage bfi = r.createScreenCapture(rt);
		File f = new File(localFilePath);
		ImageIO.write(bfi, Files.getFileExtension(localFilePath), f);
	}
}
