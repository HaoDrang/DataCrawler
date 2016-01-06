package com.crawler.tentacle.html.getter.weibo;

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class WeiboTool {

	public final static String URL_HTTP_HEAD = "http://";
	public final static String URL_HTTPS_HEAD = "https://";

	/**
	 * Get Weibo.cn Cookie to login
	 * 
	 * @param userName
	 * @param passWrod
	 * @return
	 * @throws Exception
	 */
	public static String getSinaCookie(String userName, String passWord) throws Exception {
		StringBuilder sb = new StringBuilder();

		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver.setJavascriptEnabled(true);
		driver.get("http://login.weibo.cn/login/");

		WebElement mobile = driver.findElementByCssSelector("input[name=mobile]");
		mobile.sendKeys(userName);
		WebElement pass = driver.findElementByCssSelector("input[name^=password]");
		pass.sendKeys(passWord);
		WebElement rem = driver.findElementByCssSelector("input[name=remember]");
		rem.click();
		WebElement submit = driver.findElementByCssSelector("input[name=submit]");
		submit.click();

		Set<Cookie> cookieSet = driver.manage().getCookies();
		driver.close();

		for (Cookie cookie : cookieSet) {
			sb.append(cookie.getName() + "=" + cookie.getValue() + ";");
		}

		String result = sb.toString();
		if (result.contains("_T_WM")) {
			return result;
		} else {
			throw new Exception("Weibo login Failed~" + result);
		}
	}

	/**
	 * Make cookie String from input String
	 * 
	 * @param input
	 * @return
	 */
	public static String makeCookie(String input) {
		String[] items = input.split(",");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < items.length / 2; i++) {
			sb.append(items[i * 2] + "=" + items[i * 2 + 1] + ";");
		}
		return sb.toString();
	}

	/**
	 * Get MainDomain of input url; input url need to be full url
	 * 
	 * @param fullUrl
	 * @return
	 */
	public static String getDomain(String fullUrl) {
		int pos = fullUrl.indexOf(URL_HTTP_HEAD);// + "http://".length();
		if (pos < 0) {
			pos = fullUrl.indexOf(URL_HTTPS_HEAD);
			if (pos < 0) {
				return "";
			} else {
				pos += URL_HTTPS_HEAD.length();
			}
		} else {
			pos += URL_HTTP_HEAD.length();
		}

		int end = fullUrl.indexOf("/", pos);
		return fullUrl.substring(pos, end);
	}
}
