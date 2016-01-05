package com.crawler.tentacle;

import java.net.URL;

import com.crawler.tentacle.html.analyse.AnalyserFactory;
import com.crawler.tentacle.html.getter.DummyGetter;
import com.crawler.tentacle.html.getter.GetterFactory;
import com.crawler.tentacle.html.getter.IHtmlGetter;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;

public class Tentacle extends RamCrawler {
	// TODO add config file

	private GetterFactory 	mGetterFactory;
	private AnalyserFactory mAnalyserFactory;
	
	public Tentacle(){
		// TODO create crawler by config files
		
		mGetterFactory 		= new GetterFactory();
		mAnalyserFactory 	= new AnalyserFactory();
	}
	
	public void start(String str) {
		IHtmlGetter getter = mGetterFactory.generate(str);
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
		
	}
}
