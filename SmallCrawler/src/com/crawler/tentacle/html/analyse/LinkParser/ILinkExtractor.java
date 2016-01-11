package com.crawler.tentacle.html.analyse.LinkParser;

import org.jsoup.nodes.Document;

public interface ILinkExtractor {
	String[] extract(Document doc);
}
