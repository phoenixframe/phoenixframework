package org.phoenix.api.action;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

/**
 * 接口测试
 * @author mengfeiyang
 *
 */
public interface IInterfaceAPI{
	
	/**
	 * 请求https的URL，并获得响应内容，响应码,响应头，响应时间等。如获取响应内容的方法：<br>
	 * <code> String ls = IOUtils.toString(new InputStreamReader(httpsUrlConn.getInputStream()));</code>或：<br>
     * <code> List<String> ls = IOUtils.readLines(new InputStreamReader(httpsUrlConn.getInputStream()));</code><br>
     * 获取响应状态码：httpsUrlConn.getResponseCode();
	 * @param httpsUrl https地址
	 * @param keyStoreFile keyStoreFile文件地址
	 * @param pass 验证密码
	 * @return
	 */
	HttpsURLConnection getHttpsUrlResponse(String httpsUrl,String keyStoreFile,String pass);
	/**
	 * 请求https的URL，并获得响应内容，响应码,响应头，响应时间等。如获取响应内容的方法：<br>
	 * <code> String ls = IOUtils.toString(new InputStreamReader(httpsUrlConn.getInputStream()));</code>或：<br>
     * <code> List<String> ls = IOUtils.readLines(new InputStreamReader(httpsUrlConn.getInputStream()));</code><br>
     * 获取响应状态码：httpsUrlConn.getResponseCode();
	 * @param httpsUrl  https地址
	 * @param keyStoreFile keyStore文件
	 * @param pass
	 * @return
	 */
	HttpsURLConnection getHttpsUrlResponse(String httpsUrl,File keyStoreFile,String pass);
	/**
	 * 请求https的URL，并获得响应内容，响应码,响应头，响应时间等。如获取响应内容的方法：<br>
	 * <code> String ls = IOUtils.toString(new InputStreamReader(httpsUrlConn.getInputStream()));</code>或：<br>
     * <code> List<String> ls = IOUtils.readLines(new InputStreamReader(httpsUrlConn.getInputStream()));</code><br>
     * 获取响应状态码：httpsUrlConn.getResponseCode();
	 * @param httpsUrl https地址
	 * @param keyStoreFileUri  URI格式的keyStoreFile地址
	 * @param pass 验证密码
	 * @return
	 */
	HttpsURLConnection getHttpsUrlResponse(String httpsUrl,URI keyStoreFileUri,String pass);
	/**
	 * 获取WebConversation对象，通过这个对象可以设置cookie，host，代理等
	 * @return
	 */
	WebConversation getWebConversation();
	/**
	 * Post方式 + host请求URL
	 * @param url
	 * @param hostIP
	 * @param hostPort
	 * @param params
	 * @param headers
	 * @return
	 */
	WebResponse getResponseByPost(String url,String hostIP,int hostPort,HashMap<String,String> params,HashMap<String,String> headers);
	/**
	 * 本方法需要手动指定get或post方式，不区分大小，需要传入一个WebConversation对象
	 * @param url
	 * @param postorget
	 * @param wc
	 * @return
	 */
	WebResponse getResponseByHost(String url,String postorget,WebConversation wc);
	/**
	 * 仅通过设置无需用户名和密码的代理来请求URL
	 * @param url
	 * @param postorget
	 * @param hostIP
	 * @param hostPort
	 * @return
	 */
	WebResponse getResponseByHost(String url,String postorget,String hostIP,int hostPort);
	/**
	 * 支持host用户名和密码
	 * @param url
	 * @param postorget
	 * @param hostIP
	 * @param hostPort
	 * @param hostName
	 * @param hostPassword
	 * @return
	 */
	WebResponse getResponseByHost(String url,String postorget,String hostIP,int hostPort,String hostName,String hostPassword);
	/**
	 * 通过post方式load数据
	 * @param url
	 * @return
	 */
	WebResponse getResponseByPost(String url);
	/**
	 * 通过post方式load数据
	 * @param url
	 * @param connTimeOut 最大连接的超时时间
	 * @param readTimeout 读取数据的最大超时时间
	 * @return
	 */
	WebResponse getResponseByPost(String url,int connTimeOut,int readTimeout);
	/**
	 * post请求
	 * @param url
	 * @param parameters 接口请求的参数
	 * @param headers
	 * @return
	 */
	WebResponse getResponseByPost(String url,HashMap<String,String> parameters,HashMap<String,String> headers);
	/**
	 * get方式请求数据
	 * @param url
	 * @return
	 */
	WebResponse getResponseByGet(String url);
	/**
	 * 通过get方式load数据
	 * @param url
	 * @param connTimeOut 最大连接的超时时间
	 * @param readTimeout 读取数据的最大超时时间
	 * @return
	 */
	WebResponse getResponseByGet(String url,int connTimeOut,int readTimeout);
	/**
	 * get请求
	 * @param url
	 * @param parameters 接口请求的参数
	 * @param headers
	 * @return
	 */
	WebResponse getResponseByGet(String url,HashMap<String,String> parameters,HashMap<String,String> headers);
	/**
	 * 使用HTTPUnit来抓取url内容
	 * @param wc
	 * @param hostIP 如果禁用host，则设此值为null
	 * @param hostPort
	 * @param hostUser
	 * @param hostPassword
	 * @param postorget post或get方式请求URL，不区分大小写
	 * @param url
	 * @param connTimeOut
	 * @param readTimeout
	 * @param parameters URL参数
	 * @param headers header设置
	 * @return
	 */
	WebResponse getResponseByHttpUnit(WebConversation wc,String hostIP,int hostPort,String hostUser,String hostPassword,String postorget,String url, int connTimeOut, int readTimeout,HashMap<String, String> parameters, HashMap<String, String> headers);
	/**
	 * 通过指定的jsonpath从json内容中取指定值。
	 * @param jsonContent  json的内容
	 * @param jsonPath  json指定节点的路径
	 * @return
	 */
	String getJSONValue(String jsonContent, String jsonPath);
	/**
	 * 返回一个带有代理的HTTPClient，用于设置host
	 * @param proxyHost 代理服务器IP
	 * @param proxyPort 代理服务器端口
	 * @param loginName 如果需要用户名，则需要提供用户名，否则留空
	 * @param loginPass 如果需要密码，则需要提供密码，否则留空
	 * @return
	 */
	HttpClient getHttpClientWithProxy(String proxyHost,int proxyPort,String loginName,String loginPass);

	/**
	 * 使用代理抓取指定url的内容
	 * @param httpClient 带有代理的HTTPClient
	 * @param url
	 * @param method
	 * @param headers urlheader，如果没有则设为null
	 * @return
	 */
	HttpResponse getResponseByHttpClient(HttpClient httpClient,String url, String method,HashMap<String, String> headers); 
	/**
	 * 使用Apache httpClient获取页面源代码。Get方式
	 * @param url
	 * @return
	 */
	String getPageSourceByHttpClientWithGet(String url);
	/**
	 * 使用Apache httpClient获取页面源代码。Post方式
	 * @param url
	 * @return
	 */
	String getPageSourceByHttpClientWithPost(String url);
	/**
	 * 使用Apache httpClient获取HttpResponse。Get方式
	 * @param url
	 * @return
	 */
	HttpResponse getResponseByHttpClientWithGet(String url);
	/**
	 * 使用Apache httpClient获取HttpResponse。Post方式
	 * @param url
	 * @return
	 */
	HttpResponse getResponseByHttpClientWithPost(String url);
	/**
	 * 此方法适用于部分字段需要post提交的需求
	 * @param url
	 * @param rawString
	 * @return
	 */
	HttpResponse getResponseByHttpClientWithPost(String url,String rawString);
	
	/**
	 * 此方法适用于部分字段需要post提交的需求,且可以添加header
	 * @param url
	 * @param headers
	 * @param rawString
	 * @return
	 */
	HttpResponse getResponseByHttpClientWithPost(String url,HashMap<String,String> headers,String rawString);
	/**
	 * 此方法适用于需要post提交文件的需求
	 * @param url url地址
	 * @param filePath 文件路径
	 * @return
	 */
	HttpResponse getResponseByHttpClientWithPost(String url,File filePath);
	
	/**
	 * 此方法适用于需要post提交文件的需求
	 * @param url
	 * @param headers  需要添加的header值
	 * @param filePath
	 * @return
	 */
	HttpResponse getResponseByHttpClientWithPost(String url,HashMap<String,String> headers,File filePath);
	/**
	 * 此方法适用于需要post提交文件的需求
	 * @param url
	 * @param filePath 文件路径
	 * @param contentType  文件类型
	 * @return
	 */
	HttpResponse getResponseByHttpClientWithPost(String url,File filePath,ContentType contentType);
	
	/**
	 * 将String字符串格式化成Document格式
	 * @param html
	 * @return
	 */
	Document parseStringToDom(String html);
	/**
	 * 通过get方式请求传入的url对象，并将结果格式化成Document格式
	 * @param url
	 * @param timeout 请求的超时时间
	 * @return
	 */
	Document parseStringToDom(URL url,int timeout);
	
	/**
	 * 将文件内容格式化成Document，默认编码是UTF-8
	 * @param file
	 * @return
	 */
	Document parseFileToDom(File file);
	/**
	 * 将指定的文件格式化成Document，charset为指定的编码格式
	 * @param file
	 * @param charset
	 * @return
	 */
	Document parseFileToDom(File file,String charset);
	/**
	 * 如果传入的是一个完整的HTML内容，通过此方法只将body部分格式化成Document，而忽略title，header等
	 * @param bodyHtml
	 * @return
	 */
	Document parseBodyFragment(String bodyHtml);
	
	/**
	 * 通过Jsoup的Get方法获取Response对象，Response对象中包含响应内容，状态码等
	 * @param url
	 * @return
	 */
	Response getResponseByJsoupByGet(String url);
	/**
	 * 通过Jsoup的Post方法获取Response对象，Response对象中包含响应内容，状态码等
	 * @param url
	 * @return
	 */
	Response getResponseByJsoupByPost(String url);
	/**
	 * 通过指定的请求方式method，使用Jsoup获得Response对象
	 * @param url  指定的URL
	 * @param method  请求方式，为get或post，不区分大小写
	 * @param headers  随请求一起发送的header数据
	 * @return
	 */
	Response getResponseByJsoup(String url,String method,HashMap<String,String> headers);
	/**
	 * 通过指定的请求方式method，使用Jsoup获得Response对象
	 * @param url  指定的URL
	 * @param method  请求方式，为get或post，不区分大小写
	 * @param headers  随请求一起发送的header数据
	 * @param datas  随请求一起发送的datas数据，某些场景下，可通过此方法直接操作参数。如登陆时的用户名和密码
	 * @return
	 */
	Response getResponseByJsoup(String url,String method,HashMap<String,String> datas,HashMap<String,String> headers);
	
	/**
	 * 通过指定的请求方式method，使用Jsoup获得Response对象
	 * @param url  指定的URL
	 * @param method  请求方式，为get或post，不区分大小写
	 * @param headers  随请求一起发送的header数据
	 * @param datas  随请求一起发送的datas数据，某些场景下，可通过此方法直接操作参数。如登陆时的用户名和密码
	 * @param cookies 随请求一起发送的cookies数据
	 * @param isFollowRedirects 如果url自动跳转，则是否follow这个跳转
	 * @param userAgent 用户设置的代理
	 * @return
	 */
	Response getResponseByJsoup(String url,String method,HashMap<String,String> cookies,HashMap<String,String> datas,HashMap<String,String> headers,boolean isFollowRedirects,String userAgent);
}
