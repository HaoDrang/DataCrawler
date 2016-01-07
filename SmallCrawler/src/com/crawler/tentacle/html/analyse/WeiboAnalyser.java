package com.crawler.tentacle.html.analyse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.tentacle.html.analyse.elemetParser.IFeedParser;
import com.crawler.tentacle.html.analyse.elemetParser.WeiboWebElementParse;

public class WeiboAnalyser implements IHtmlAnalyse {

	public enum STR {
		WEB_CLASS_FEED_DETAIL;

		private String selector;

		public String getSelector() {
			return this.selector;
		}

		public void setSelector(String selector) {
			this.selector = selector;
		}
	}
	
	private String[] mContents;

	public WeiboAnalyser() {
		mContents = null;
		
		// TODO load pattern from xml
		STR.WEB_CLASS_FEED_DETAIL.selector = "div[class~=(WB_feed_detail)]";
	}

	@Override
	public boolean Analyse(String html) {

		Document doc = Jsoup.parse(html);
		Elements elements = doc.select(STR.WEB_CLASS_FEED_DETAIL.selector);
		IFeedParser feedParser = new WeiboWebElementParse();
		
		mContents = new String[elements.size()];
		
		
		
		// content getter would destroy the structure of element
		for (int i = 0; i < elements.size(); i++) {
			Element element = elements.get(i);
			mContents[i] = feedParser.parse(element);
			System.out.println(mContents[i]);
		}
		
		System.out.println("*****Doc analyse End*****");

		return false;
	}

	@Override
	public String[] Links() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] Contents() {
		// TODO Auto-generated method stub
		return null;
	}

}
