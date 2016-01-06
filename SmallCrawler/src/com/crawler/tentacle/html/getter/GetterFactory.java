package com.crawler.tentacle.html.getter;

import com.crawler.tentacle.html.getter.weibo.WeiboTool;

public class GetterFactory {

	private final String WEIBO_DESCOVER = "d.weibo.com";
	private final String WEIBO_MOBILE_M = "m.weibo.cn";
	private final String WEIBO_MOBILE = "weibo.cn";
	private final String WEIBO_WEB = "weibo.com";
	private final String WEIBO_SEARCH = "s.weibo.com";

	public IHtmlGetter generate(String fullUrl) {
		String mainDomain = WeiboTool.getDomain(fullUrl);
		IHtmlGetter iGetter = null;
		switch (mainDomain) {
		case WEIBO_WEB:
			iGetter = new WeiboWebGetter();
			break;
		case WEIBO_MOBILE_M:
			iGetter = new WeiboMobileMGetter();
			break;
		case WEIBO_MOBILE:
			iGetter = new WeiboMobileGetter();
			break;
		case WEIBO_DESCOVER:
			iGetter = new WeiboDiscoverGetter();
			break;
		case WEIBO_SEARCH:
			iGetter = new WeiboSearchGetter();
			break;
		default:
			iGetter = new DummyGetter();
			break;
		}

		return iGetter;
	}
}
