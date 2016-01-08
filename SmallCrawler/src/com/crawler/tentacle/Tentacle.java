package com.crawler.tentacle;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.crawler.tentacle.html.analyse.AnalyserFactory;
import com.crawler.tentacle.html.analyse.IHtmlAnalyse;
import com.crawler.tentacle.html.getter.GetterFactory;
import com.crawler.tentacle.html.getter.IHtmlGetter;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;

public class Tentacle extends RamCrawler {
	// TODO add config file

	private GetterFactory mGetterFactory;
	private AnalyserFactory mAnalyserFactory;

	private int miThreadNum = 2;
	private int miDepth = 1;
	
	private HashSet<String> mTable;
	private HashSet<String> mBlockUrl;

	public HashSet<String> getTable() {
		return mTable;
	}
	
	//tempoutput
	Logger mLog = null;
	
	public Tentacle() {
		// TODO create crawler by config files
		System.getProperties().setProperty("webdriver.chrome.driver", "drivers//chromedriver.exe");

		mLog = Logger.getLogger("weiboLog");
		FileHandler handler = null;
		try {
			handler = new FileHandler(".//temp//weibo.log");
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.setFormatter(new SimpleFormatter());
		mLog.addHandler(handler);
		
		mGetterFactory = new GetterFactory();
		mAnalyserFactory = new AnalyserFactory();
		mTable = new HashSet<String>();
		mBlockUrl = new HashSet<String>();
	}

	public void start(String str) {
		String[] urls = str.split(",");
		for (int i = 0; i < urls.length; i++) {
			this.addSeed(urls[i]);
		}
		this.setThreads(miThreadNum);
		this.setAutoParse(false);
		try {
			this.start(miDepth);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception {
		// TODO use target htmlgetter to get response

		try {
			HttpResponse response = new HttpResponse(new URL(crawlDatum.getUrl()));
			// TODO init a new getter by url
			IHtmlGetter getter = mGetterFactory.generate(crawlDatum.getUrl());
			response.setHtml(getter.getHtml(crawlDatum.getUrl()));
			response.addHeader("Content-Type", "text/html");
			return response;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		if (page.getStatus() == Page.STATUS_FETCH_SUCCESS) {
			IHtmlAnalyse analyser = mAnalyserFactory.generate(page.getUrl());
			if(analyser.Analyse(page.getHtml())){
				String[] links = analyser.Links();
				for (int i = 0; i < links.length; i++) {
					if(mBlockUrl.contains(links[i])) continue;
					mBlockUrl.add(getCleanLink(links[i]));
					next.add(new CrawlDatum(links[i]));
				}
				
				String[] contents = analyser.Contents();
				for (int i = 0; i < contents.length; i++) {
					if(!mTable.contains(contents[i])) {
						mTable.add(contents[i]);
						mLog.info(contents[i]);
					}
				}
			}
		}
	}

	private String getCleanLink(String link) {
		if(link.contains("?")) return link.replaceAll("\\?.*", "");
		return link;
	}
	
}
