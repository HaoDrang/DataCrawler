package com.crawler.tentacle.html;

import java.io.IOException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DefaultGetter implements IHtmlGetter {

	@Override
	public String getHtml(String url) {
		
		String html = "null";
		
		RequestConfig requestConfig = RequestConfig.custom()
		.setSocketTimeout(5000)
		.setConnectTimeout(5000)
		.build();
		
		CloseableHttpClient httpClient = HttpClients.custom()
		.setDefaultRequestConfig(requestConfig)
		.build();
		
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			html = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("----------Connection time out----------");
		} finally{
			try {
				if(response != null)
				{
					response.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return html;
	}
}
