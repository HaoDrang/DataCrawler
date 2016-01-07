package com.crawler.tentacle.html.analyse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.tentacle.html.analyse.elemetParser.IFeedParser;
import com.crawler.tentacle.html.analyse.elemetParser.WeiboWebElementParse;

public class WeiboAnalyser implements IHtmlAnalyse {

	public enum STR {
		WEB_CLASS_FEED_DETAIL, WEB_ATTR_LINK_SEL, WEB_ATTR_LINK;

		private String selector;

		public String getSelector() {
			return this.selector;
		}

		public void setSelector(String selector) {
			this.selector = selector;
		}
	}

	private String[] mContents;
	private String[] mLinks;

	public WeiboAnalyser() {
		mContents = null;
		mLinks = null;
		// TODO load pattern from xml
		STR.WEB_CLASS_FEED_DETAIL.selector = "div[class~=(WB_feed_detail)]";
		STR.WEB_ATTR_LINK_SEL.selector = "[href^=http://weibo.com]";
		STR.WEB_ATTR_LINK.selector = "href";
	}

	@Override
	public boolean Analyse(String html) {

		try {
			Document doc = Jsoup.parse(html);
			Elements feeds = doc.select(STR.WEB_CLASS_FEED_DETAIL.selector);
			Elements links = doc.select("[href^=http://weibo.com]");
			IFeedParser feedParser = new WeiboWebElementParse();

			mContents = new String[feeds.size()];
			mLinks = new String[links.size()];

			// get links first
			for (int j = 0; j < links.size(); j++) {
				Element link = links.get(j);
				if (link != null)
					mLinks[j] = link.attr(STR.WEB_ATTR_LINK.selector);

				System.out.println(mLinks[j]);
			}

			// content getter would destroy the structure of element
			for (int i = 0; i < feeds.size(); i++) {
				Element feed = feeds.get(i);
				if (feed != null)
					mContents[i] = feedParser.parse(feed);
				System.out.println(mContents[i]);
			}

			System.out.println("*****Doc " + doc.title() + " End*****");

			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String[] Links() {
		return mLinks;
	}

	@Override
	public String[] Contents() {
		return mContents;
	}

}
