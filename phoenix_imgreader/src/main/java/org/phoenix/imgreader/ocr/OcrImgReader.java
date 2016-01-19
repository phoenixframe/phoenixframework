package org.phoenix.imgreader.ocr;
import java.io.File;

import org.phoenix.imgreader.helper.ImageBase64Encoder;

/**
 * 图片文字读取类
 * @author mengfeiyang
 *
 */
public class OcrImgReader {
	
	private static String TesseractPath;

	public static void setTesseractPath(String tesseractPath) {
		TesseractPath = tesseractPath;
	}

	/**
	 * 识别本地图片的字符
	 * @param imgPath
	 * @param imgFormat
	 * @return
	 */
	public static String readLocalImage(String imgPath,String imgFormat){
		try {
			File f = new File(imgPath);
			return new OCR(TesseractPath).recognizeText(f, imgFormat);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	/**
	 * 识别网络图片的字符
	 * @param url
	 * @return
	 */
	public static String readUrlImage(String url){
		String base64Code = ImageBase64Encoder.getUrlImgBase64Code(url);
		boolean flag = ImageBase64Encoder.decoderImage(base64Code, "testcode.jpg");
		if(flag)return readLocalImage("testcode.jpg","jpg");
		return null;
	}
	
	 public static void main(String[] args) {
		 OcrImgReader.setTesseractPath("D://Program Files (x86)//Tesseract-OCR");
		 //String oos = OcrImgReader.readUrlImage("https://passport.csdn.net/ajax/verifyhandler.ashx");
		 String oos = OcrImgReader.readLocalImage("E:\\testcode.jpg", "jpg");
		 
		 System.out.println(oos);
	 }
}


