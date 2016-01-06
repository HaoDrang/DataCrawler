package com.crawler.tentacle.html.analyse;

public class DummyAnalyser implements IHtmlAnalyse {
	private String[] mLinks = null;
	private String[] mContentes = null;

	@Override
	public boolean Analyse(String html) {
		return false;
	}

	@Override
	public String[] Links() {
		return mLinks;
	}

	@Override
	public String[] Contents() {
		return mContentes;
	}

}
