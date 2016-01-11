package com.crawler.tentacle.html.analyse.elemetParser;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WeiboCNElementParse implements IFeedParser {

	public enum STR {
		WEB_CLASS_CONTENT, WEB_CLASS_USER, WEB_ATTR_LINK, WEB_CLASS_APP;

		private String selector;

		public String getSelector() {
			return this.selector;
		}

		public void setSelector(String selector) {
			this.selector = selector;
		}
	}

	private String mUserName;
	private String mLink;

	public WeiboCNElementParse(String userName, String link) {
		mUserName = userName;
		mLink = link;
		initStrValues();
	}

	public WeiboCNElementParse() {
		mUserName = "";
		mLink = "";
		initStrValues();
	}

	private void initStrValues() {
		STR.WEB_CLASS_CONTENT.selector = "ctt";
		STR.WEB_CLASS_USER.selector = "nk";
		STR.WEB_ATTR_LINK.selector = "href";
		STR.WEB_CLASS_APP.selector = "ct";
	}

	@Override
	public String parse(Element ele) {

		String userName = "";
		String date = "";
		String app = "";
		String content = "";
		String link = "";

		Element wbDetail = ele;
		// get user name
		String ssss = STR.WEB_CLASS_USER.selector;
		Elements userNameEles = wbDetail.getElementsByClass(ssss);
		if (userNameEles.isEmpty()) {
			userName = mUserName;
			link = mLink;
		} else {
			userName = userNameEles.get(0).text();
			link = userNameEles.get(0).attr(STR.WEB_ATTR_LINK.selector);
		}

		// get date and app
		String[] tf = wbDetail.getElementsByClass(STR.WEB_CLASS_APP.selector).text()
				.split(StringEscapeUtils.unescapeHtml4("&nbsp;"));
		if (tf.length == 2) {
			date = tf[0];
			app = tf[1];
		}

		// get content
		Elements cttEles = wbDetail.getElementsByClass(STR.WEB_CLASS_CONTENT.selector).clone();
		cttEles.select("a[href]").remove();
		content = cttEles.text().replaceAll("\u201c", "");
		content = content.replaceAll("\u201d", "");

		// System.out.println(userName);

		JSONObject json = new JSONObject();

		json.append(FeedJsonKeys.NAME, userName);
		json.append(FeedJsonKeys.DATE, date);
		json.append(FeedJsonKeys.APP, app);
		json.append(FeedJsonKeys.CONTENT, content);
		json.append(FeedJsonKeys.LINK, link);

		return content.isEmpty() ? "" : json.toString();
	}

}
