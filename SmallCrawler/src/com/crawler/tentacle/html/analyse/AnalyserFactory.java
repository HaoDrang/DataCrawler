package com.crawler.tentacle.html.analyse;

import com.crawler.tentacle.html.getter.weibo.WeiboTool;

public class AnalyserFactory {

	private final String WEIBO_DISCOVER = "d.weibo.com";
	private final String WEIBO_MOBILE_M = "m.weibo.cn";
	private final String WEIBO_MOBILE = "weibo.cn";
	private final String WEIBO_WEB = "weibo.com";
	private final String WEIBO_SEARCH = "s.weibo.com";

	public IHtmlAnalyse generate(String url) {
		IHtmlAnalyse result = null;
		String mainDomain = WeiboTool.getDomain(url);
		switch (mainDomain) {
		case WEIBO_DISCOVER:
		case WEIBO_SEARCH:
		case WEIBO_WEB:
		
		case WEIBO_MOBILE_M:
			result = new WeiboAnalyser();
			break;
		case WEIBO_MOBILE:
			result = new WeiboCNAnalyser();
			break;
		default:
			result = new DummyAnalyser();
			break;
		}
		return result;
	}

}
