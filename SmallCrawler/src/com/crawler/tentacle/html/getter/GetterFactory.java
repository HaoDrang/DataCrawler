package com.crawler.tentacle.html.getter;

public class GetterFactory {
	private final String WEIBO_DESCOVER	= "";
	private final String WEIBO_MOBILE	= "";
	private final String WEIBO_WEB		= "";
	private final String WEIBO_SEARCH 	= "";
	public IHtmlGetter generate(String str)
	{
		String[] splits = str.split("/");
		for (int i = 0; i < splits.length; i++) {
			
			System.out.println("~~~  " + splits[i]);
		}
		return new DummyGetter();
	}
}
