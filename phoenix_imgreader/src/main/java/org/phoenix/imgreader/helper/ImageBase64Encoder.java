package org.phoenix.imgreader.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 网络图片与本地图片，通过base64编码相互转换
 * @author mengfeiyang
 *
 */
public class ImageBase64Encoder {
	private static BASE64Encoder encoder = new BASE64Encoder();
	private static BASE64Decoder decoder = new BASE64Decoder();
	/**
	 * 根据图片的URL，将网络中的图片转换成base64编码
	 * @param imgFilePath
	 * @return
	 */
	
	public static String getUrlImgBase64Code(String url){
		byte[] data = null;
		try {
			URL u = new URL(url); 
			URLConnection urlConn = u.openConnection();
			
			InputStream in = urlConn.getInputStream();
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encoder.encode(data);
	}
	
	/**
	 * 将本地的图片转换成base64编码
	 * @param imgFilePath
	 * @return
	 */
	public static String getLocalImgBase64Code(String imgFilePath){
		byte[] data = null;
		try{
			InputStream in = new FileInputStream(new File(imgFilePath));
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		return encoder.encode(data);
	}
	
	/**
	 * 将base64编码后的字符串转成图片
	 * @param imgStr
	 * @param imgFilePath
	 * @return
	 */
	public static boolean decoderImage(String imgBase64Code, String imgFilePath){
		if(imgBase64Code == null) return false;
		try{
			byte[] bytes = decoder.decodeBuffer(imgBase64Code);
			for(int i = 0;i<bytes.length;i++){
				if(bytes[i] < 0)bytes[i] += 256;
			}
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e){
			return false;
		}
		
	}
	
	public static void main(String[] args) {		
		String imgBase64Code = ImageBase64Encoder.getUrlImgBase64Code("http://lianmeng.360.cn/passport/pub/login/imagecode");
		System.out.println(imgBase64Code); //打印编码
		boolean b = ImageBase64Encoder.decoderImage(imgBase64Code, "2.png");
		System.out.println(b); //保存是否成功
	}
}
