package com.crawler.tentacle.html.analyse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// TODO elicit config items to a xml structure
public class WeiboSearchHtmlAnalyser implements IHtmlAnalyse {

	@Override
	public boolean Analyse(String html) {
		String[] weboContent = null;
		try	{
			Document doc = Jsoup.parse(html);
			
			Elements list = getAllElements(doc, "p");//doc.select("div");
			weboContent = new String[list.size()];
			
			for (int i = 0; i < weboContent.length; i++) {
				weboContent[i] = analyseWeiboElement(list.get(i));
			}

		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return false;
	}

	private Elements getAllElements(Document doc, String cssQuary) {
		Elements ret = new Elements();
		
		try {
			elementRecursion(doc.getAllElements(), ret, cssQuary);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	private void elementRecursion(Elements allElements, Elements ret, String cssQuary) throws Exception {
		Elements select = allElements.select(cssQuary);
		if(select.size() > 0) ret.addAll(select);
		for(Element element : allElements)
		{
			elementRecursion(element.children(), ret, cssQuary);
		}
	}

	private String analyseWeiboElement(Element element) {
		
		String pName = element.text();
		
		String str = "key:[]";
		str += ",name:[" + pName + "]";
		str += ",mainpage:[]";
		str += ",content:[" + pName + "]";
		
		return str;
	}

	@Override
	public String[] Links() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] Contents() {
		// TODO Auto-generated method stub
		return null;
	}
}
