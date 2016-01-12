package com.crawler.tentacle.html.getter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.edu.hfut.dmic.webcollector.net.HttpRequest;

public class WeiboMobileGetter implements IHtmlGetter {

	private final String mConfigDir = ".\\configs\\weiboMobileCookie";

	public WeiboMobileGetter() {
	}

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

		try {
			HttpRequest request = null;
			request = new HttpRequest(url);
			request.setCookie(getCookiesDataFromFile(mConfigDir));
			String str = request.getResponse().getHtml("utf-8");
			return str;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

}
