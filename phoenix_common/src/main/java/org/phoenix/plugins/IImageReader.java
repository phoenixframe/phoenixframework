package org.phoenix.plugins;

/**
 * phoenixframe图片识别模块
 * @author mengfeiyang
 * @since phoenix_webdriver 1.3.5
 */
public interface IImageReader {
	/**
	 * 配置图片识别程序
	 * @param tesseractPath
	 */
	IImageReader configImageReader(String tesseractPath);
	/**
	 * 识别网络图片，直接提供图片的url地址即可。如：http://xxx/img.jpg
	 * @param url
	 * @return
	 */
	String readUrlImage(String url);
	/**
	 * 识别本地图片，提供本地图片的绝对路径
	 * @param imgPath
	 * @return
	 */
	String readLocalImage(String imgPath,String imgFormat); 
	/**
	 * 对网络图片进行base64编码。即将图片以字符串的形式保存
	 * @param imgUrlPath 图片的url地址，如：http://xxx/img.jpg
	 * @return
	 */
	String getUrlImageBase64Code(String imgUrlPath);
	/**
	 * 对本地的图片进行base64编码。即将图片转换成字符串
	 * @param imgLocalPath 本地图片的绝对地址
	 * @return
	 */
	String getLocalImageBase64Code(String imgLocalPath);
	/**
	 * 将使用base64加密后的字符串反向解码成实际图片文件
	 * @param imgBase64Code  图片的base64字符
	 * @param imgOutputPath  解码后的图片存放地址
	 * @return
	 */
	boolean ImageBase64Decoder(String imgBase64Code,String imgOutputPath);
}
