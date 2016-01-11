package com.crawler.tentacle.html.analyse.LinkParser;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WeiboCNLinkExtractor implements ILinkExtractor {

	private final String LINK_KEY_ATTR = "href";
	private final String LINK_DOMAIN_WEIBO_CN = "http://weibo.cn";
	private final String LINK_ATTITUDE_KEYWORD = "attitude";
	private final String LINK_M_BLOG = "mblog";
	private final String LINK_COMMENT = "comment";
	private final String LINK_REPOST = "repost";
	private final String LINK_ADD_FAV = "fav//addfav";
	private final String LINK_SINAURL = "sinaurl";

	@Override
	public String[] extract(Document doc) {

		ArrayList<String> links = new ArrayList<String>();
		Elements allLinks = doc.getElementsByAttribute(LINK_KEY_ATTR);

		for (int i = 0; i < allLinks.size(); i++) {
			Element linkEle = allLinks.get(i);
			String link = linkEle.attr(LINK_KEY_ATTR);
			if (properLink(link)) {
				links.add(processLink(link));
			}
		}
		String[] ret = new String[links.size()];
		return links.toArray(ret);
	}

	private boolean properLink(String link) {

		if (!isWeiboCNLink(link))
			return false;
		
		if (isAttitudeBtn(link))
			return false;
		
		if (isBlog(link))
			return false;
		
		if (isComment(link))
			return false;
		
		if (isRepost(link))
			return false;
		
		if (isAddFav(link))
			return false;
		
		if (isSinaUrl(link))
			return false;

		return true;
	}

	private boolean isSinaUrl(String link) {
		return link.contains(LINK_SINAURL);
	}

	private boolean isAddFav(String link) {
		return link.contains(LINK_ADD_FAV);
	}

	private boolean isRepost(String link) {
		return link.contains(LINK_REPOST);
	}

	private boolean isComment(String link) {
		return link.contains(LINK_COMMENT);
	}

	private boolean isBlog(String link) {
		return link.contains(LINK_M_BLOG);
	}

	private boolean isAttitudeBtn(String link) {
		return link.contains(LINK_ATTITUDE_KEYWORD);
	}

	private boolean isWeiboCNLink(String link) {

		return link.contains(LINK_DOMAIN_WEIBO_CN);
	}

	private String processLink(String link) {
		return null;
	}
}
