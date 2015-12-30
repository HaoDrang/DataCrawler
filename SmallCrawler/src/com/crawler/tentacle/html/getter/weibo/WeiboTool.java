package com.crawler.tentacle.html.getter.weibo;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class WeiboTool {
	public static String getSinaCookie(String userName, String passWrod) throws Exception{
		StringBuilder sb = new StringBuilder();
		
		HtmlUnitDriver  driver = new HtmlUnitDriver ();
		
		String result = sb.toString();
		if(result.contains("gsid_CtandWM")){
			return result;
		}else{
			throw new Exception("Weibo login Failed~" + result);
		}
	}
}
