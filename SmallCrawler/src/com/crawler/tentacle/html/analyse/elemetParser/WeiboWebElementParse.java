package com.crawler.tentacle.html.analyse.elemetParser;

import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WeiboWebElementParse implements IFeedParser {

	public enum STR {
		WEB_CLASS_DETAIL, WEB_CLASS_INFO, WEB_CLASS_TEXT, WEB_CLASS_FROM, WEB_CLASS_FACE, WEB_CLASS_MOVIE, WEB_ATTR_TITLE, WEB_ATTR_DATE, WEB_ATTR_FROM;

		private String selector;

		public String getSelector() {
			return this.selector;
		}

		public void setSelector(String selector) {
			this.selector = selector;
		}
	}

	public WeiboWebElementParse() {
		STR.WEB_CLASS_DETAIL.selector = "div.WB_detail";
		STR.WEB_CLASS_INFO.selector = "div.WB_info";
		STR.WEB_CLASS_TEXT.selector = "div[class~=(WB_text)]";
		STR.WEB_CLASS_FROM.selector = "div[class~=(WB_from)]";
		STR.WEB_CLASS_FACE.selector = "img[render]";
		STR.WEB_CLASS_MOVIE.selector = "a[class]";
		STR.WEB_ATTR_TITLE.selector = "title";
		STR.WEB_ATTR_DATE.selector = "a[node-type=feed_list_item_date]";
		STR.WEB_ATTR_FROM.selector = "a[action-type=app_source]";
	}

	@Override
	public String parse(Element ele) {

		Elements wbDetail = ele.select(STR.WEB_CLASS_DETAIL.selector);
		Elements wbInfo = wbDetail.select(STR.WEB_CLASS_INFO.selector);
		Elements wbContent = wbDetail.select(STR.WEB_CLASS_TEXT.selector);
		Elements wbFrom = wbDetail.select(STR.WEB_CLASS_FROM.selector);

		// read userName
		String userName = wbInfo.text();

		// get time
		String date = wbFrom.select(STR.WEB_ATTR_DATE.selector).get(0).attr(STR.WEB_ATTR_TITLE.selector);

		// get app source
		String app = "";
		for (Element element : wbFrom.select(STR.WEB_ATTR_FROM.selector)) {
			app = element.text();
			break;
		}

		// read faces
		String faces = "";
		for (Element element : wbContent.select(STR.WEB_CLASS_FACE.selector)) {
			faces += element.attr(STR.WEB_ATTR_TITLE.selector);
		}

		// remove all movie
		for (Element element : wbContent.select(STR.WEB_CLASS_MOVIE.selector)) {
			element.remove();
		}
		// get content
		String content = wbContent.text() + faces;

		JSONObject json = new JSONObject();

		json.append(FeedJsonKeys.NAME, userName);
		json.append(FeedJsonKeys.DATE, date);
		json.append(FeedJsonKeys.APP, app);
		json.append(FeedJsonKeys.CONTENT, content);

		return json.toString();
	}
}
