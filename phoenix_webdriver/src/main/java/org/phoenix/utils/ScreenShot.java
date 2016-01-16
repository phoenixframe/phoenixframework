package org.phoenix.utils;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;

import org.phoenix.aop.PhoenixLogger;

/**
 * 截图方法公共类
 * @author MENGFEIYANG
 *
 */

public class ScreenShot {
	
	static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();	
	static String picPathName = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("%20", " ")+"screenshot/";
	public static String TakeScreenshot(String filePath){
		String picFormat = "png";
		int serialNum = 0;
		String name = null;
		// 拷贝屏幕到一个BufferedImage对象screenshot 
			BufferedImage screenshot;
			try {
				screenshot = (new Robot()).createScreenCapture(new Rectangle(0, 0,(int) d.getWidth(), (int) d.getHeight()));
				serialNum++;
				if(filePath==null)name = picPathName + new Date().getTime()+"_"+String.valueOf(serialNum)+ "."+ picFormat;
				else name = filePath;
				File f = new File(name);
				File parent = f.getParentFile();
				if(parent!=null&&!parent.exists()){ 
					parent.mkdirs(); 
				} 
				f.createNewFile();
				// 将screenshot对象写入图像文件,可选格式：bmp,png,jpg,jpeg
				ImageIO.write(screenshot,picFormat,f);				
			} catch (Exception e1) {
				PhoenixLogger.error("截图失败,"+e1.getMessage());
				e1.printStackTrace();
			}
			return name;
	}
}