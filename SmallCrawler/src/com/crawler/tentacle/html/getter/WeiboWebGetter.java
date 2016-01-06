package com.crawler.tentacle.html.getter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WeiboWebGetter implements IHtmlGetter {

	ChromeOptions mChromeOptions = null;
	

	public WeiboWebGetter() {
		// TODO disable images by hand
		mChromeOptions = new ChromeOptions();
		mChromeOptions.addArguments("user-data-dir=" + 
		System.getProperty("user.dir") + "//config//ChromeCache//");
	}

	@Override
	public String getHtml(String url) {

		ChromeDriver driver = new ChromeDriver(mChromeOptions);
		driver.get(url);

		//WebDriverWait wait = new WebDriverWait(driver, 10000);
		
		WaitForPageLoadDone(driver);

		String source = driver.getPageSource();

		driver.quit();

		return source;
	}

	private void WaitForPageLoadDone(WebDriver driver) {
		WaitForPageLoadDone(driver, 10000);
	}

	private void WaitForPageLoadDone(WebDriver driver, int millSeconds) {
		WaitForPageLoadDone(driver, millSeconds, 1000);
	}

	private void WaitForPageLoadDone(WebDriver driver, int millSeconds, int requireDelay) {
		int timeOut = millSeconds;
		while (timeOut > 0) {
			timeOut -= requireDelay;
			try {
				System.out.println("*****Wait For page Loaded:" + timeOut + "*****");
				Thread.sleep(requireDelay);
				String source = driver.getPageSource();
				if (source.contains("W_pages") || source.contains("W_gotop")) {
					System.out.println("**************Html Ready**************");
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
