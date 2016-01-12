package com.crawler.tentacle.html.analyse.LinkParser;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WeiboCNLinkExtractor implements ILinkExtractor {

	private final String ELEMENT_ATTR_HREF = "href";
	private final String ELEMENT_ATTR_VALUE = "value";
	private final String ELEMENT_ID_PAGELIST = "pagelist";
	private final String ELEMENT_SELECTOR_PAGEINFO = "input[name=mp]";
	private final String ELEMENT_URL_SELECTOR = "a[href]";
	private final String LINK_ACCOUNT = "account";
	private final String LINK_ADD_FAV = "fav//addfav";
	private final String LINK_ATTITUDE_KEYWORD = "attitude";
	private final String LINK_COMMENT = "comment";
	private final String LINK_DOMAIN_WEIBO_CN = "http://weibo.cn";
	private final String LINK_KEY_ATTR = "href";
	private final String LINK_M_BLOG = "mblog";
	private final String LINK_PAGE = "page";
	private final String LINK_PAGES_KEYWORD = "?page=";
	private final String LINK_REPOST = "repost";
	private final String LINK_SINAURL = "sinaurl";
	private final String LINK_USER = "//u//";

	@Override
	public String[] extract(Document doc) {

		ArrayList<String> links = new ArrayList<String>();
		Elements allLinks = doc.getElementsByAttribute(LINK_KEY_ATTR);
		if (doc == null)
			return null;
		for (String pageLink : tryGetNextPages(doc)) {
			links.add(pageLink);
			// System.out.println("[Link:" + pageLink + "]");
		}

		for (int i = 0; i < allLinks.size(); i++) {
			Element linkEle = allLinks.get(i);
			String link = linkEle.attr(LINK_KEY_ATTR);
			if (properLink(link)) {
				links.add(processLink(link));
				// System.out.println("[Link:" + link + "]");
			}
		}

		System.out.println("********Link Ok********");
		String[] ret = new String[links.size()];
		return links.toArray(ret);
	}

	private boolean isAccountPage(String link) {
		return link.contains(LINK_ACCOUNT);
	}

	private boolean isAddFav(String link) {
		return link.contains(LINK_ADD_FAV);
	}

	private boolean isAttitudeBtn(String link) {
		return link.contains(LINK_ATTITUDE_KEYWORD);
	}

	private boolean isBlog(String link) {
		return link.contains(LINK_M_BLOG);
	}

	private boolean isComment(String link) {
		return link.contains(LINK_COMMENT);
	}

	private boolean isHelpPage(String link) {
		return link.contains(LINK_PAGE);
	}

	private boolean isRepost(String link) {
		return link.contains(LINK_REPOST);
	}

	private boolean isSinaUrl(String link) {
		return link.contains(LINK_SINAURL);
	}

	private boolean isULink(String link) {
		return link.contains(LINK_USER);
	}

	private boolean isWeiboCNLink(String link) {

		return link.contains(LINK_DOMAIN_WEIBO_CN);
	}

	private String processLink(String link) {
		if (!link.contains(LINK_DOMAIN_WEIBO_CN))
			return LINK_DOMAIN_WEIBO_CN.concat(link);

		return link;
	}

	private boolean properLink(String link) {

		if ((!isWeiboCNLink(link)) && (!isULink(link)))
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

		if (isAccountPage(link))
			return false;

		if (isHelpPage(link))
			return false;

		return true;
	}

	private String[] tryGetNextPages(Document doc) {

		Element elementById = doc.getElementById(ELEMENT_ID_PAGELIST);
		if (elementById != null) {
			String url = "";
			Elements urlEle = elementById.select(ELEMENT_URL_SELECTOR);
			if (!urlEle.isEmpty()) {
				try {
					url = LINK_DOMAIN_WEIBO_CN.concat(urlEle.attr(ELEMENT_ATTR_HREF));
				} catch (Exception e) {
				}
			}

			int pageNum = 0;
			Elements pageEle = elementById.select(ELEMENT_SELECTOR_PAGEINFO);
			if (!pageEle.isEmpty()) {
				try {
					pageNum = Integer.parseInt(pageEle.attr(ELEMENT_ATTR_VALUE));
				} catch (Exception e) {
					// parse failed keep 0
				}
			}

			if (pageNum > 0) {
				String[] urls = new String[pageNum];
				for (int i = 0; i < urls.length; i++) {
					urls[i] = url + LINK_PAGES_KEYWORD + (i + 1);
				}
				return urls;
			}
		}

		return new String[0];
	}
}
