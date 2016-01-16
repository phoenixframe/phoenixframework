package org.phoenix.web.util;

import java.util.ArrayList;
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
 * @since httpunit 1.7
 *
 */

public class HttpRequestSender {
	/*
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 * 设置参数时，参数名与参数值的分割符为“=>”
	 *
	 */
	public static String getResponseByPost(String url, List<String> parameter,String paramType) throws Exception{
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例
		WebRequest req = new PostMethodWebRequest(url);// 向指定的URL发出请求，获取响应
		
		for (String para : parameter) { 
			String[] parValue = para.split("=>");
			if(paramType.equals("setParameter")){
			   req.setParameter(parValue[0], parValue[1]);
			}else if(paramType.equals("setHeaderField")){
				req.setHeaderField(parValue[0], parValue[1]);
			}
		}
		WebResponse wr = wc.getResponse(req);
		
		return wr.getText();
	}
	
	/*
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 * 设置参数时，参数名与参数值的分割符为“=>”
	 *
	 */
	public static String getResponseByGet(String url, List<String> parameter,String paraType) throws Exception{
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例		
		WebRequest req = new GetMethodWebRequest(url);// 向指定的URL发出请求，获取响应
		
		for (String para : parameter) {
			String[] parValue = para.split("=>");
			if(paraType.equals("setParameter")){
			   req.setParameter(parValue[0], parValue[1]);
			}else if(paraType.equals("setHeaderField")){
				req.setHeaderField(parValue[0], parValue[1]);
			}
		}
		WebResponse wr = wc.getResponse(req);
		
		return wr.getText();
	}
	
	/*
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 * 设置参数时，参数名与参数值的分割符为“=>”
	 *
	 */
	public static WebResponse getResponseObjectByPost(String url, List<String> parameter,String paraType) throws Exception{
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例		
		WebRequest req = new PostMethodWebRequest(url);// 向指定的URL发出请求，获取响应
		
		for (String para : parameter) {
			String[] parValue = para.split("=>");
			if(paraType.equals("setParameter")){
			   req.setParameter(parValue[0], parValue[1]);
			}else if(paraType.equals("setHeaderField")){
				req.setHeaderField(parValue[0], parValue[1]);
			}
		}
		WebResponse wr = wc.getResponse(req);

			return wr;
	}
	
	/*
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 *
	 */
	public static String getResponseByGet(String url) throws Exception {
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例	
		WebRequest req = new GetMethodWebRequest(url);// 向指定的URL发出请求，获取响应		
		
		WebResponse wr = wc.getResponse(req);		
		
		return wr.getText();			
	}
	
	/*
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 *
	 */
	public static String getResponseByGet(String url,int connTimeOut,int readTimeout) throws Exception {
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例	
		wc.set_connectTimeout(connTimeOut);
		wc.set_readTimeout(readTimeout);
		
		WebRequest req = new GetMethodWebRequest(url);// 向指定的URL发出请求，获取响应		
		
		WebResponse wr = wc.getResponse(req);		
		return wr.getText();			
	}
	
	/*
	 * get方式返回一个response对象，通过该对象可以获取响应的内容及状态码
	 */
	public static WebResponse getResponseObjectByGet(String url) throws Exception {
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例			
		WebRequest req = new GetMethodWebRequest(url);// 向指定的URL发出请求，获取响应		
		WebResponse wr = wc.getResponse(req);
		
		return wr;			
	}
	/*
	 * post方式返回一个response对象，通过该对象可以获取响应的内容及状态码
	 */
	public static WebResponse getResponseObjectByPost(String url) throws Exception {
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例			
		WebRequest req = new PostMethodWebRequest(url);// 向指定的URL发出请求，获取响应		
		WebResponse wr = wc.getResponse(req);
		
		return wr;			
	}
	
	/*
	 * 以get方式获取响应码
	 */
	public static int getResponseCodeByGet(String url) throws Exception {
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例			
		WebRequest req = new GetMethodWebRequest(url);// 向指定的URL发出请求，获取响应		
		WebResponse wr = wc.getResponse(req);
		
		return wr.getResponseCode();			
	}
	
	/*
	 * 以post方式获取响应码
	 */
	public static int getResponseCodeByPost(String url) throws Exception {
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例			
		WebRequest req = new PostMethodWebRequest(url);// 向指定的URL发出请求，获取响应		
		WebResponse wr = wc.getResponse(req);
		
		return wr.getResponseCode();			
	}
	
	/*
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 *
	 */
	public static String getResponseByPost(String url) throws Exception {

		WebConversation wc = new WebConversation();// 建立一个WebConversation实例
		WebRequest req = new PostMethodWebRequest(url);// 向指定的URL发出请求，获取响应
		WebResponse wr = wc.getResponse(req);
		
		return wr.getText();
	}
	
	public static void main(String[] args) throws Exception {
		List<String> paradd = new ArrayList<String>();
		paradd.add("pid=>69b81504767483cf");
		paradd.add("guid=>9c553730ef5b6c8c542bfd31b5e25b69");
		paradd.add("data=>");
		WebResponse weresp = HttpRequestSender.getResponseObjectByPost("http://10.103.29.145/yks/get.json?vid=XNzM0MTQ5ODg0&ct=20&pt=stream,video,videos,show,dvd,controller,error&dv=pc&uip=218.30.180.179");
		String[] fielName = weresp.getHeaderFieldNames();
		for(String s : fielName){
			System.out.println(s +" = "+weresp.getHeaderField(s));
		}
	}

}
