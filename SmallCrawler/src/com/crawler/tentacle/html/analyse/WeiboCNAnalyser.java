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
		WEB_CLASS_FEED_DETAIL, WEB_ATTR_LINK_SEL, WEB_ATTR_LINK, WEB_TAG_TOP, WEB_PAGE_OWNER_ELE, HTML_NBSP_SPLITER;

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
		STR.WEB_PAGE_OWNER_ELE.selector = "span.ctt";
		STR.HTML_NBSP_SPLITER.selector = StringEscapeUtils.unescapeHtml4("&nbsp;");
	}

	@Override
	public boolean Analyse(String html) {

		try {
			Document doc = Jsoup.parse(html);
			String userName = getPageOwner(doc);
			Elements feeds = doc.select(STR.WEB_CLASS_FEED_DETAIL.selector);

			IFeedParser feedParser = new WeiboCNElementParse(userName, "");
			ILinkExtractor linkExtractor = new WeiboCNLinkExtractor();

			mContents = new String[feeds.size()];

			// get links first
			mLinks = linkExtractor.extract(doc);

			// content getter would destroy the structure of element
			generateContents(feeds, feedParser);

			System.out.println("*****Doc " + doc.title() + " End*****");

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void generateContents(Elements feeds, IFeedParser feedParser) {
		for (int i = 0; i < feeds.size(); i++) {
			Element feed = feeds.get(i);
			if (feed != null)
				mContents[i] = feedParser.parse(feed);
			if (!mContents[i].isEmpty())
				System.out.println(mContents[i]);
		}
		
		if(mContents.length > 0)
			System.out.println("********Content OK********");
		else
			System.out.println("[System]:The page has no Weibo Content");
	}

	private String getPageOwner(Document doc) {
		try {

			Element body = doc.body();

			Elements select = (body.getElementsByTag(STR.WEB_TAG_TOP.selector)).select(STR.WEB_PAGE_OWNER_ELE.selector);

			Element element = select.get(0).clone();
			element.children().remove();

			String[] splits = element.text().split(STR.HTML_NBSP_SPLITER.selector);
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
