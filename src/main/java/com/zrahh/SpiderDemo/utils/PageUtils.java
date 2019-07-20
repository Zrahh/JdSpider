package com.zrahh.SpiderDemo.utils;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/*
 * 页面工具类
 */
public class PageUtils {
	public static String getContent(String url) {
		//	httpclient固定格式
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient httpclient = builder.build();
		String result = null;
		//捕获异常
		try {
			//获取url
			HttpGet get = new HttpGet(url);
			//下载页面并转为字符串
			CloseableHttpResponse response = httpclient.execute(get);
			result = EntityUtils.toString(response.getEntity());
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	

}
