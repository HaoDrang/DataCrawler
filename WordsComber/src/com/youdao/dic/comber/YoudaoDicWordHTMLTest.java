package com.youdao.dic.comber;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class YoudaoDicWordHTMLTest implements IWordTest {

	private final String YOUDAODIC_URL_FORMAT = "http://dict.youdao.com/search?q=%s&keyfrom=dict.index";
	
	private String mWord = "";
	
	@Override
	public void readData(String word) {

		String url = String.format(YOUDAODIC_URL_FORMAT, word);
		System.out.println(url);
		
		try {
			Document doc = Jsoup.connect(url).get();
			//System.out.println(doc.html());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
