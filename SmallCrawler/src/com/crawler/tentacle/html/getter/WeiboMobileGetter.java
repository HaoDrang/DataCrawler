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

	volatile static private Cookie[] mCookie = null;
	private final String mConfigDir = ".\\configs\\weiboMobileCookie";
	private final String mDateFormat = "yyyy-MM-dd'T'hh:mm:ss.SSS";

	public WeiboMobileGetter() {

		if (mCookie == null) {

			synchronized (WeiboMobileGetter.class) {
				if (mCookie == null) {
					String[] cookies = getCookiesDataFromFile(mConfigDir);

					mCookie = new Cookie[cookies.length];

					for (int i = 0; i < cookies.length; i++) {

						String[] values = new String[8];
						String[] split = cookies[i].split(",");

						System.arraycopy(split, 0, values, 0, split.length);

						DateFormat df = new SimpleDateFormat(mDateFormat, Locale.ENGLISH);
						Date date = null;

						try {
							date = new Date(df.parse(values[4]).getTime());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						// keep useless
						int size = Integer.parseInt(values[5]);

						boolean isHttpOnly = (values[6] == null || values[6].isEmpty());
						boolean isSecure = (values[7] == null || values[7].isEmpty());

						mCookie[i] = new Cookie(values[0], values[1], values[2], values[3], date, isSecure, isHttpOnly);
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
			str += line;
			while (line != null) {
				str += "|";
				str += r.readLine();
			}

			r.close();
			ist.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str.split("|");
	}

	@Override
	public String getHtml(String url) {

		HtmlUnitDriver driver = new HtmlUnitDriver();

		for (int i = 0; i < mCookie.length; i++) {
			driver.manage().addCookie(mCookie[i]);
		}

		driver.get(url);

		String ret = driver.getPageSource();

		driver.close();

		return ret;
	}
}
