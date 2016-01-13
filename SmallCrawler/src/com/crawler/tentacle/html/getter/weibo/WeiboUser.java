package com.crawler.tentacle.html.getter.weibo;

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WeiboUser implements Comparable<WeiboUser> {

	private final String WEIBO_CN_LOGIN = "http://login.weibo.cn/login/";
	private final int LOAD_COOKIE_IDLE_TIME = 2000;
	private final int COUNTDOWN_WAIT_OFFSET = 100;
	private String mAccount = null;
	private String mPassword = null;
	private String mCookieStr = null;
	private int mDelay = 10000;
	private int mCountDown = 0;

	public WeiboUser(String account, String password) {
		mAccount = account;
		mPassword = password;
	}

	public void loadCookie() throws Exception {
		System.out.println("[System]:Begin Load Cookie..." + mAccount);
		StringBuilder sb = new StringBuilder();

		ChromeDriver driver = new ChromeDriver();
		driver.get(WEIBO_CN_LOGIN);

		WebElement accountInput = driver.findElementByCssSelector("input[name=mobile]");
		WebElement passWordInput = driver.findElementByCssSelector("input[name^=password]");
		WebElement submit = driver.findElementByCssSelector("input[name=submit]");

		accountInput.sendKeys(mAccount);
		passWordInput.sendKeys(mPassword);
		try {
			Thread.sleep(LOAD_COOKIE_IDLE_TIME);
		} catch (InterruptedException e) {
		}
		submit.click();

		Set<Cookie> cookieSet = driver.manage().getCookies();
		driver.close();

		for (Cookie cookie : cookieSet) {
			sb.append(cookie.getName() + "=" + cookie.getValue() + ";");
		}
		String result = sb.toString();
		if (result.contains("gsid_CTandWM")) {
			setCookieString(result);
			System.out.println("[System]:Cookie Ready..." + mAccount);
		} else {
			throw new Exception("weibo login failed");
		}
	}

	public String getCookieAndSink() {
		
		if(mCookieStr == null){
			try {
				loadCookie();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		mCountDown = mDelay;
		return mCookieStr;
	}

	@SuppressWarnings("unused")
	private String getCookieString() {
		return mCookieStr;
	}

	public void setCookieString(String mCookieStr) {
		this.mCookieStr = mCookieStr;
	}

	public void update(int d) {
		if (mCountDown > 0) {
			mCountDown -= d;
		}
		if (mCountDown < 0)
			mCountDown = 0;
	}

	public boolean ready() {
		if (mCountDown < 0)
			mCountDown = 0;
		return mCountDown <= 0;
	}

	@Override
	public int compareTo(WeiboUser o) {

		if (this.mCountDown > o.mCountDown)
			return 1;
		if (this.mCountDown < o.mCountDown)
			return -1;

		return 0;
	}

	public long getWaitTime() {

		return mCountDown + COUNTDOWN_WAIT_OFFSET;
	}
}
