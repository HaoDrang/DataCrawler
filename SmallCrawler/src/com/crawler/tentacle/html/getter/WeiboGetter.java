package com.crawler.tentacle.html.getter;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.tentacle.html.getter.weibo.WeiboTool;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;

public class WeiboGetter extends RamCrawler implements IHtmlGetter {

	String mCookie;
	
	public WeiboGetter(boolean autoParse, String account, String password) throws Exception {
		super(autoParse);
		this.mCookie = WeiboTool.makeCookie("SSOLoginState,1451458918,"
				+ "SUB,_2A257h_E2DeTRGeNG6lQV9SjOzDSIHXVYi59-rDV6PUJbrdBeLWb6kW1HsaGCtn_jUI4A3rGbEoxA9XXKOA..,"
				+ "SUHB,0cS3ag8IWld-he,_T_WM,9d0de3110e8475123904cf77d4cd9c44");
	}

	//@Override
	public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception {
		HttpRequest request = new HttpRequest(crawlDatum);
		request.setCookie(mCookie);
		return request.getResponse();
	}

	@Override
	public String getHtml(String url) {

		String[] urls = url.split(",");
		this.setThreads(3);
		try {
			for (int i = 0; i < urls.length; i++) {
				this.addSeed(new CrawlDatum(urls[i]));
			}
			this.addRegex("http://weibo.cn/*");
//			this.addRegex(urlRegex);
			this.start(8);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}



	@Override
	public void visit(Page page, CrawlDatums next) {
		Elements weibos = page.select("div");
		for(Element weibo : weibos)
		{
			String nextUrl = weibo.select("a[href]").attr("href");
			if((!nextUrl.isEmpty()) && (nextUrl.contains("http://weibo.cn/"))) next.add(new CrawlDatum(nextUrl));
			System.out.println(weibo.text());
		}
	}
}
