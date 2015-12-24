package com.crawler.tentacle.html;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.cookie.DefaultCookieSpec;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HtmlGetter implements IHtmlGetter {

	private boolean mbCookie = false;
	private boolean mbProxy = false;
	private boolean mbSetHeader = false;

	private HttpHost mProxy = null;

	/**
	 * get Html String
	 */
	@Override
	public String getHtml(String url) {
		// cookie custom
		Registry<CookieSpecProvider> reg = null;
		if (mbCookie) {

			CookieSpecProvider cookieSpecProvider = new CookieSpecProvider() {
				public CookieSpec create(HttpContext context) {
					return new DefaultCookieSpec() {
						@Override
						public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
						}
					};
				}
			};

			reg = RegistryBuilder.<CookieSpecProvider> create()
					.register(CookieSpecs.DEFAULT, new DefaultCookieSpecProvider()).register("easy", cookieSpecProvider)
					.build();
		}

		// use proxy
		DefaultProxyRoutePlanner routePlanner = null;
		if (mbProxy && (mProxy != null))
			routePlanner = new DefaultProxyRoutePlanner(mProxy);

		String html = "null";

		// setup request config builder
		Builder requestCfgBuilder = RequestConfig.custom();
		requestCfgBuilder.setSocketTimeout(5000);
		requestCfgBuilder.setConnectTimeout(5000);
		if (mbCookie)
			requestCfgBuilder.setCookieSpec("easy");

		RequestConfig requestConfig = requestCfgBuilder.build();

		// setup http client config
		HttpClientBuilder clientBuilder = HttpClients.custom();
		if (reg != null)
			clientBuilder.setDefaultCookieSpecRegistry(reg);
		if (routePlanner != null)
			clientBuilder.setRoutePlanner(routePlanner);
		clientBuilder.setDefaultRequestConfig(requestConfig);

		CloseableHttpClient httpClient = clientBuilder.build();

		HttpGet httpGet = new HttpGet(url);

		if (mbSetHeader)
			setHeader(httpGet);

		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			html = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("----------Connection time out----------");
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return html;
	}

	public HtmlGetter cookieProtect(boolean value) {
		mbCookie = value;
		return this;
	}

	public HtmlGetter useProxy(String hostName, int port) {
		mbProxy = true;
		mProxy = new HttpHost(hostName, port);
		return this;
	}

	public HtmlGetter cancelProxy() {
		mbProxy = false;
		mProxy = null;
		return this;
	}

	public HtmlGetter useHeader() {
		mbSetHeader = true;
		return this;
	}

	public HtmlGetter cancelHeader() {
		mbSetHeader = false;
		return this;
	}

	public void setHeader(HttpGet httpGet) {
		httpGet.setHeader("Accept", "text/html, */*; q=0.01");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate,sdch");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36)");
	}
}
