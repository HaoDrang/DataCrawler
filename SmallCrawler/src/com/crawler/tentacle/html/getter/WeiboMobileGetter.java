package com.crawler.tentacle.html.getter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class WeiboMobileGetter implements IHtmlGetter {

	volatile static private HtmlUnitDriver mDriver = null;
	// volatile static private Cookie[] mCookie = null;
	private final String mConfigDir = ".\\configs\\weiboMobileCookie";
	private final String mDateFormat = "yyyy-MM-dd'T'hh:mm:ss.SSS";
	private final String mWeiboMobileDomain = "weibo.cn";
	private final String mHttpPrefix = "http://";

	public WeiboMobileGetter() {

		if (mDriver == null) {

			synchronized (WeiboMobileGetter.class) {
				if (mDriver == null) {
					String[] cookiestr = getCookiesDataFromFile(mConfigDir);

					Cookie[] mCookies = new Cookie[cookiestr.length];

					for (int i = 0; i < cookiestr.length; i++) {

						String[] values = new String[8];
						String[] split = (cookiestr[i].trim()).split(",");

						System.arraycopy(split, 0, values, 0, split.length);

						DateFormat df = new SimpleDateFormat(mDateFormat, Locale.ENGLISH);
						Date date = null;

						try {
							date = new Date(df.parse(values[4]).getTime());
						} catch (ParseException e) {
							// e.printStackTrace();
							date = null;
						}
						// keep useless
						@SuppressWarnings("unused")
						int size = 0;
						try {
							size = Integer.parseInt(values[5]);
						} catch (Exception e) {
							e.printStackTrace();
							for (String string : split) {
								System.out.println(string);
							}
						}

						boolean isHttpOnly = (values[6] == null || values[6].isEmpty());
						boolean isSecure = (values[7] == null || values[7].isEmpty());
						mCookies[i] = new Cookie(values[0], values[1], values[2], values[3], date, isSecure,
								isHttpOnly);
					}

					// create a new driver
					mDriver = new HtmlUnitDriver();
					mDriver.get(mHttpPrefix + mWeiboMobileDomain);

					for (int i = 0; i < mCookies.length; i++) {
						mDriver.manage().addCookie(mCookies[i]);
					}
				}
			}
		}
	}

	private String[] getCookiesDataFromFile(String dir) {

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

		return str.split(";");
	}

	@Override
	public String getHtml(String url) {

		mDriver.get(url);

		String ret = mDriver.getPageSource();

		return ret;
	}

	@Override
	protected void finalize() throws Throwable {
		mDriver.close();
		super.finalize();
	}

}
