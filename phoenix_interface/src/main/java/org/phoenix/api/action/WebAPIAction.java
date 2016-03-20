package org.phoenix.api.action;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.phoenix.api.utils.JsonPaser;
import org.phoenix.api.utils.MyX509TrustManager;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

/**
 * web接口测试实现类
 * @author mengfeiyang
 *
 */
@SuppressWarnings("deprecation")
public class WebAPIAction implements APIAction{

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getWebConversation()
	 */
	@Override
	public WebConversation getWebConversation(){
		return new WebConversation();
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByHost(java.lang.String, java.lang.String, com.meterware.httpunit.WebConversation)
	 */
	@Override
	public WebResponse getResponseByHost(String url,String postorget,WebConversation wc){
		return getResponseByHttpUnit(wc,null,0,null,null,postorget,url,-1,-1,null,null);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByPost(java.lang.String, java.lang.String, int, java.util.HashMap, java.util.HashMap)
	 */
	@Override
	public WebResponse getResponseByPost(String url,String hostIP,int hostPort,HashMap<String,String> params,HashMap<String,String> headers){
		return getResponseByHttpUnit(null,hostIP,hostPort,"","","post",url,-1,-1,params,headers);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByHost(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override
	public WebResponse getResponseByHost(String url,String postorget,String hostIP,int hostPort){
		return getResponseByHttpUnit(null,hostIP,hostPort,"","",postorget,url,-1,-1,null,null);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByHost(java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String)
	 */
	@Override
	public WebResponse getResponseByHost(String url,String postorget,String hostIP,int hostPort,String hostName,String hostPassword){
		return getResponseByHttpUnit(null,hostIP,hostPort,hostName,hostPassword,postorget,url,-1,-1,null,null);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByPost(java.lang.String)
	 */
	@Override
	public WebResponse getResponseByPost(String url) {
		return getResponseByHttpUnit(null,null,0,null,null,"post",url,-1,-1,null,null);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByPost(java.lang.String, int, int)
	 */
	@Override
	public WebResponse getResponseByPost(String url, int connTimeOut, int readTimeout) {
		return getResponseByHttpUnit(null,null,0,null,null,"post",url,connTimeOut,readTimeout,null,null);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByPost(java.lang.String, java.util.HashMap, java.util.HashMap)
	 */
	@Override
	public WebResponse getResponseByPost(String url,HashMap<String, String> parameters, HashMap<String, String> headers) {
		return getResponseByHttpUnit(null,null,0,null,null,"post",url,-1,-1,parameters,headers);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByGet(java.lang.String)
	 */
	@Override
	public WebResponse getResponseByGet(String url) {
		return getResponseByHttpUnit(null,null,0,null,null,"get",url,-1,-1,null,null);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByGet(java.lang.String, int, int)
	 */
	@Override
	public WebResponse getResponseByGet(String url, int connTimeOut, int readTimeout) {
		return getResponseByHttpUnit(null,null,0,null,null,"get",url,connTimeOut,readTimeout,null,null);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByGet(java.lang.String, java.util.HashMap, java.util.HashMap)
	 */
	@Override
	public WebResponse getResponseByGet(String url,HashMap<String, String> parameters, HashMap<String, String> headers) {
		return getResponseByHttpUnit(null,null,0,null,null,"get",url,-1,-1,parameters,headers);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByHttpUnit(com.meterware.httpunit.WebConversation, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.util.HashMap, java.util.HashMap)
	 */
	@Override
	public WebResponse getResponseByHttpUnit(WebConversation webConv,String hostIP,int hostPort,String hostUser,String hostPassword,String postorget,String url, int connTimeOut, int readTimeout,HashMap<String, String> parameters, HashMap<String, String> headers){
		WebConversation wc = webConv == null?new WebConversation():webConv;	
		if(connTimeOut != -1)wc.set_connectTimeout(connTimeOut);
		if(readTimeout != -1)wc.set_readTimeout(readTimeout);
		if(hostIP != null)wc.setProxyServer(hostIP, hostPort, hostUser, hostPassword);
		WebRequest req = postorget.equalsIgnoreCase("get")?new GetMethodWebRequest(url):new PostMethodWebRequest(url);
		if(parameters != null)for(Entry<String,String> es:parameters.entrySet())req.setParameter(es.getKey(), es.getValue());
		if(headers != null)for(Entry<String,String> et:headers.entrySet())req.setHeaderField(et.getKey(), et.getValue());
		WebResponse wr = null;
		try {
			wr = wc.getResponse(req);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		wr.close();
		return wr;
	}
	
	/**
	 * 通过指定的jsonpath从json内容中取指定值。
	 * @param jsonContent  json的内容
	 * @param jsonPath  json指定节点的路径
	 * @return
	 */
	@Override
	public String getJSONValue(String jsonContent, String jsonPath) {
		try {
			return JsonPaser.getNodeValue(jsonContent, jsonPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getPageSourceByHttpClientWidthGet(java.lang.String)
	 */
	public String getPageSourceByHttpClientWithGet(String url){
		try {
			return IOUtils.toString(getResponseByHttpClient(null,url,"get",null).getEntity().getContent());
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getPageSourceByHttpClientWidthPost(java.lang.String)
	 */
	@Override
	public String getPageSourceByHttpClientWithPost(String url){
		try {
			return IOUtils.toString(getResponseByHttpClient(null,url,"post",null).getEntity().getContent());
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByHttpClientWidthGet(java.lang.String)
	 */
	@Override
	public HttpResponse getResponseByHttpClientWithGet(String url){
		return getResponseByHttpClient(null,url,"get",null);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByHttpClientWidthPost(java.lang.String)
	 */
	@Override
	public HttpResponse getResponseByHttpClientWithPost(String url){
		return getResponseByHttpClient(null,url,"post",null);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByHttpClientWidthPost(java.lang.String, java.lang.String)
	 */
	@Override
	public HttpResponse getResponseByHttpClientWithPost(String url,String rawString){
		return getResponseByHttpClientWithPost(url,null,rawString);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByHttpClientWithPost(java.lang.String, java.util.HashMap, java.lang.String)
	 */
	@Override
	public HttpResponse getResponseByHttpClientWithPost(String url,HashMap<String,String> headers,String rawString){
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);
		if(headers != null){
			for(Entry<String,String> e : headers.entrySet()){
				request.addHeader(e.getKey(), e.getValue());
			}
		}
		HttpResponse response = null;
		try {
			request.setEntity(new StringEntity(rawString));
			response = httpClient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByHttpClientWidthPost(java.lang.String, java.io.File)
	 */
	@Override
	public HttpResponse getResponseByHttpClientWithPost(String url,File filePath){
		return getResponseByHttpClientWithPost(url,null,filePath);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByHttpClientWithPost(java.lang.String, java.util.HashMap, java.io.File)
	 */
	@Override
	public HttpResponse getResponseByHttpClientWithPost(String url,HashMap<String,String> headers,File filePath){
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);
		if(headers != null){
			for(Entry<String,String> e : headers.entrySet()){
				request.addHeader(e.getKey(), e.getValue());
			}
		}
		HttpResponse response = null;
		try {
			request.setEntity(new FileEntity(filePath));
			response = httpClient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByHttpClientWidthPost(java.lang.String, java.io.File, org.apache.http.entity.ContentType)
	 */
	@Override
	public HttpResponse getResponseByHttpClientWithPost(String url,File filePath,ContentType contentType){
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);
		HttpResponse response = null;
		try {
			request.setEntity(new FileEntity(filePath,contentType));
			response = httpClient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getHttpClientWidthProxy(java.lang.String, int, java.lang.String, java.lang.String)
	 */
	@Override
	public HttpClient getHttpClientWithProxy(String proxyHost, int proxyPort,String loginName, String loginPass) {
		 DefaultHttpClient httpClient = new DefaultHttpClient();
		 httpClient.getCredentialsProvider().setCredentials(new AuthScope(proxyHost, proxyPort),new UsernamePasswordCredentials(loginName, loginPass));
		 HttpHost proxy = new HttpHost(proxyHost,proxyPort);
		 httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
		 return httpClient;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByHttpClientWidthProxy(org.apache.http.client.HttpClient, java.lang.String, java.lang.String, java.util.HashMap)
	 */
	@Override
	public HttpResponse getResponseByHttpClient(
			HttpClient hClient,String url, String method,HashMap<String, String> headers) {
		HttpClient httpClient = hClient==null?HttpClientBuilder.create().build():hClient;
		HttpRequestBase request = null;
		if(method.equalsIgnoreCase("get"))request = new HttpGet(url);
		else if(method.equalsIgnoreCase("post")) request = new HttpPost(url);
		if(headers != null){
			for(Entry<String,String> e2:headers.entrySet()){
				request.setHeader(e2.getKey(), e2.getValue());
			}
		}
		HttpResponse response = null;
		try {
			response = httpClient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#parseStringToDom(java.lang.String)
	 */
	@Override
	public Document parseStringToDom(String html) {
		return Jsoup.parse(html);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#parseStringToDom(java.net.URL, int)
	 */
	@Override
	public Document parseStringToDom(URL url, int timeout) {
		try {
			return Jsoup.parse(url, timeout);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#parseFileToDom(java.io.File)
	 */
	@Override
	public Document parseFileToDom(File file) {
		try {
			return Jsoup.parse(file, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#parseFileToDom(java.io.File, java.lang.String)
	 */
	@Override
	public Document parseFileToDom(File file, String charset) {
		try {
			return Jsoup.parse(file, charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#parseBodyFragment(java.lang.String)
	 */
	@Override
	public Document parseBodyFragment(String bodyHtml) {
		return Jsoup.parseBodyFragment(bodyHtml);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByJsoupByGet(java.lang.String)
	 */
	@Override
	public Response getResponseByJsoupByGet(String url) {
		try {
			return Jsoup.connect(url).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByJsoupByPost(java.lang.String)
	 */
	@Override
	public Response getResponseByJsoupByPost(String url) {
		try {
			return Jsoup.connect(url).method(Method.POST).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByJsoup(java.lang.String, java.lang.String, java.util.HashMap)
	 */
	@Override
	public Response getResponseByJsoup(String url, String method,HashMap<String, String> headers) {
		return getResponseByJsoup(url,method,null,null,headers,true,null);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByJsoup(java.lang.String, java.lang.String, java.util.HashMap, java.util.HashMap)
	 */
	@Override
	public Response getResponseByJsoup(String url, String method,HashMap<String, String> datas, HashMap<String, String> headers) {
		return getResponseByJsoup(url,method,null,datas,headers,true,null);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getResponseByJsoup(java.lang.String, java.lang.String, java.util.HashMap, java.util.HashMap, java.util.HashMap, boolean, java.lang.String)
	 */
	@Override
	public Response getResponseByJsoup(String url, String method,
			HashMap<String, String> cookies, HashMap<String, String> datas,
			HashMap<String, String> headers, boolean isFollowRedirects,
			String userAgent) {
		Connection conn = Jsoup.connect(url);
		if(headers != null){
			for(Entry<String,String> e:headers.entrySet()){
				conn.header(e.getKey(), e.getValue());
			}
		}
		if(datas != null) conn.data(datas);
		if(cookies != null)conn.cookies(cookies);
		if(userAgent != null)conn.userAgent(userAgent);
		conn.followRedirects(isFollowRedirects);
		if(method.equalsIgnoreCase("GET"))conn.method(Method.GET);
		else if(method.equalsIgnoreCase("POST"))conn.method(Method.POST);
		try {
			return conn.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private HttpsURLConnection getURLConn(String httpsUrl,MyX509TrustManager trustManager){
		try{
	        TrustManager[] tm = {trustManager };
	        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	        sslContext.init(null, tm, new SecureRandom());
	        SSLSocketFactory ssf = sslContext.getSocketFactory();
	        URL myURL = new URL(httpsUrl);
	        HttpsURLConnection httpsConn = (HttpsURLConnection) myURL.openConnection();
	        httpsConn.setSSLSocketFactory(ssf);
	        return httpsConn;
		}catch(Exception e){
			e.printStackTrace();
		}
        return null;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getHttpsUrlResponse(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public HttpsURLConnection getHttpsUrlResponse(String httpsUrl,String keyStoreFile,String pass) {
		return getURLConn(httpsUrl,new MyX509TrustManager(keyStoreFile,pass));
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getHttpsUrlResponse(java.lang.String, java.io.File, java.lang.String)
	 */
	@Override
	public HttpsURLConnection getHttpsUrlResponse(String httpsUrl, File keyStoreFile, String pass) {
		return getURLConn(httpsUrl,new MyX509TrustManager(keyStoreFile,pass));
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.api.action.APIAction#getHttpsUrlResponse(java.lang.String, java.net.URI, java.lang.String)
	 */
	@Override
	public HttpsURLConnection getHttpsUrlResponse(String httpsUrl, URI keyStoreFileUri, String pass) {
		return getURLConn(httpsUrl,new MyX509TrustManager(keyStoreFileUri,pass));
	}

}
