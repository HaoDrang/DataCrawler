package com.crawler.tentacle.html.getter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.chrome.ChromeDriver;

import com.crawler.tentacle.html.getter.weibo.WeiboCookieManager;

import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.Proxys;

public class WeiboMobileGetter implements IHtmlGetter {
	private final String mProxySource = "http://www.haodailiip.com/guonei/";
	private final int MAX_PROXY_SOURCE_PAGE = 10;
	private final int MAX_RETRY_COUNT = 5;
	//private final String mConfigDir = ".\\configs\\weiboMobileCookie";
	private volatile static Proxys mProxys = null;
	private volatile static WeiboCookieManager mCookies = null;

	public WeiboMobileGetter() {
		if (mProxys == null) {
			synchronized (WeiboMobileGetter.class) {
				if (mProxys == null) {
					mProxys = new Proxys();
					fillProxys(mProxys);
				}
			}
		}

		if (mCookies == null) {
			synchronized (WeiboMobileGetter.class) {
				if (mCookies == null) {
					mCookies = WeiboCookieManager.getInstance();
				}
			}
		}
	}

	private void fillProxys(Proxys mProxys2) {
		ChromeDriver driver = new ChromeDriver();
		for (int i = 1; i <= MAX_PROXY_SOURCE_PAGE; i++) {
			driver.get(mProxySource + i);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			String sourcePage = driver.getPageSource();
			// System.out.println(sourcePage);
			Pattern p = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.\\d+\\s*</td>\\s*<td>\\s*\\d+");
			Matcher m = p.matcher(sourcePage);
			while (m.find()) {
				String str = m.group();
				String[] split = str.split("</td>\\s*<td>");
				int port = Integer.parseInt(split[1].trim());
				split[0] = split[0].trim();
				mProxys.add(split[0], port);
				System.out.println("[SYSTEM:Proxy added " + split[0] + " : " + port + " ]");
			}
		}

		driver.close();
	}

	@SuppressWarnings("unused")
	private String getCookiesDataFromFile(String dir) {

		String str = "";
		try {
			InputStream ist = new FileInputStream(dir);
			BufferedReader r = new BufferedReader(new InputStreamReader(ist));

			String line = r.readLine();
			while (line != null) {
				str += line;
				str += ";";
				line = r.readLine();
			}

			r.close();
			ist.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String onSplit : str.split(";")) {
			System.out.println(onSplit);
		}

		return str;
	}

	@Override
	public String getHtml(String url) {
		
		for(int i = 0; i < MAX_RETRY_COUNT; i++){
			try {
				HttpRequest request = null;
				request = new HttpRequest(url);
				request.setTimeoutForConnect(30000);
				request.setProxy(mProxys.nextRandom());
				request.setCookie(mCookies.getCookieString());
				String str = request.getResponse().getHtml("utf-8");
				return str;

			} catch (Exception e) {
				if(e.toString().contains("timed out")){
					System.out.println("[System]:Connection timed out, Retry, Current Retry Index:" + (i + 1));
				}
			}
		}

		return "";
	}

}
