package org.phoenix.mobile.powertools;

import java.util.List;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
/**
 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
 * 返回的数据的格式为xml，需要解析需要的字符<br>
 * <em>编写日期：2013年12月20日 16:27</em>
 * @author mengfeiyang
 * @since JDK 1.7 及以上
 * @since Tomcat 3.0
 *
 */

public class GetXml {
	/**
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 * <em>编写日期：2013年12月20日 16:27</em>
	 * @param url
	 * @param parameter
	 * @author mengfeiyang
	 * @since JDK 1.7 及以上
	 * @since Tomcat 3.0
	 *
	 */
	public static String getResponseByPost(String url, List<String> parameter) {
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例		
		WebRequest req = new PostMethodWebRequest(url);// 向指定的URL发出请求，获取响应

		for (String para : parameter) {
			String[] parValue = para.split("-");
			req.setParameter(parValue[0], parValue[1]);
		}
		WebResponse wr;
		try {
			wr = wc.getResponse(req);

			return wr.getText();
		} catch (Exception e) {
			//e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 * <em>编写日期：2013年12月20日 16:27</em>
	 * @param url
	 * @param parameter
	 * @author mengfeiyang
	 * @since JDK 1.7 及以上
	 * @since Tomcat 3.0
	 *
	 */
	public static String getResponseByGet(String url, List<String> parameter) {
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例		
		WebRequest req = new GetMethodWebRequest(url);// 向指定的URL发出请求，获取响应

		for (String para : parameter) {
			String[] parValue = para.split("-");
			req.setParameter(parValue[0], parValue[1]);
		}
		WebResponse wr;
		try {
			wr = wc.getResponse(req);

			return wr.getText();
		} catch (Exception e) {
			//e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 * <em>编写日期：2013年12月20日 16:27</em>
	 * @param url
	 * @author mengfeiyang
	 * @since JDK 1.7 及以上
	 * @since Tomcat 3.0
	 *
	 */
	public static String getResponseByGet(String url) {
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例			
		WebRequest req = new GetMethodWebRequest(url);// 向指定的URL发出请求，获取响应		
		WebResponse wr;
		try {			
			wr = wc.getResponse(req);

			return wr.getText();
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 * <em>编写日期：2013年12月20日 16:27</em>
	 * @param url
	 * @author mengfeiyang
	 * @since JDK 1.7 及以上
	 * @since Tomcat 3.0
	 *
	 */
	public static String getResponseByPost(String url) {
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例			
		WebRequest req = new PostMethodWebRequest(url);// 向指定的URL发出请求，获取响应		
		WebResponse wr;
		try {			
			wr = wc.getResponse(req);

			return wr.getText();
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}

}
