package com.crawler.tentacle.html.analyse;

public interface IHtmlAnalyse {
	boolean Analyse(String html);

	String[] Links();

	String[] Contents();
}
