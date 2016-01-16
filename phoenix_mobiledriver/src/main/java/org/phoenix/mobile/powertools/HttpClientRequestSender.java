package org.phoenix.mobile.powertools;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * 使用HttpClient发送请求
 * @author mengfeiyang
 *
 */
public class HttpClientRequestSender {
	
	/**
	 * 发送请求的方法
	 * @param url
	 * @return
	 */
	public String sendRequest(String url) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		String responseValue = null;
		HttpRequestBase request = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(request);
			responseValue = IOUtils.toString(response.getEntity().getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseValue;
	}
	
	public static void main(String[] args) {
		HttpClientRequestSender hr = new HttpClientRequestSender();
		String v = hr.sendRequest("http://www.cewan.la");
		System.out.println(v);
	}
}
