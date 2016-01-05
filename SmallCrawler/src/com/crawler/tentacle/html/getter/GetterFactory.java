package com.crawler.tentacle.html.getter;

public class GetterFactory {
	public IHtmlGetter generate(String str)
	{
		return new DummyGetter();
	}
}
