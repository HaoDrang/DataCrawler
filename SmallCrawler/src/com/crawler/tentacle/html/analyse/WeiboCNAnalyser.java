package com.crawler.tentacle.html.analyse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.tentacle.html.analyse.LinkParser.ILinkExtractor;
import com.crawler.tentacle.html.analyse.LinkParser.WeiboCNLinkExtractor;
import com.crawler.tentacle.html.analyse.elemetParser.IFeedParser;
import com.crawler.tentacle.html.analyse.elemetParser.WeiboCNElementParse;

public class WeiboCNAnalyser implements IHtmlAnalyse {

	public enum STR {
		WEB_CLASS_FEED_DETAIL, WEB_ATTR_LINK_SEL, WEB_ATTR_LINK, WEB_TAG_TOP;

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

	public WeiboCNAnalyser() {
		mContents = null;
		mLinks = null;
		// TODO load pattern from xml
		STR.WEB_CLASS_FEED_DETAIL.selector = "div.c";
		STR.WEB_ATTR_LINK_SEL.selector = "[href^=http://weibo.cn]";
		STR.WEB_ATTR_LINK.selector = "href";
		STR.WEB_TAG_TOP.selector = "td";
	}

	@Override
	public boolean Analyse(String html) {

		try {
			Document doc = Jsoup.parse(html);
			String userName = getPageOwner(doc);
			Elements feeds = doc.select(STR.WEB_CLASS_FEED_DETAIL.selector);
			Elements links = doc.select(STR.WEB_ATTR_LINK_SEL.selector);
			IFeedParser feedParser = new WeiboCNElementParse(userName, "");
			ILinkExtractor linkExtractor = new WeiboCNLinkExtractor();

			mContents = new String[feeds.size()];

			// get links first
			mLinks = linkExtractor.extract(doc);

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
			e.printStackTrace();
			return false;
		}
	}

	private String getPageOwner(Document doc) {
		try {

			Element body = doc.body();

			Elements select = (body.getElementsByTag(STR.WEB_TAG_TOP.selector)).select("span.ctt");

			Element element = select.get(0).clone();
			element.children().remove();

			String spliter = StringEscapeUtils.unescapeHtml4("&nbsp;");
			String[] splits = element.text().split(spliter);
			for (String string : splits) {
				System.out.println(string);
			}
			// splits[1] prepare.../ can get F/M and Location
			return splits[0];
		}

		catch (Exception e) {
			return null;
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
