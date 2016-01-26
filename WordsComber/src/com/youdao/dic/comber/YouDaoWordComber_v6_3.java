package com.youdao.dic.comber;

import java.util.Hashtable;
import java.util.Map;

import org.w3c.dom.Document;

public class YouDaoWordComber_v6_3 implements IWordComber {

	private Document mDoc = null;
	private Map<String, WordData> mDic = null;
	public YouDaoWordComber_v6_3(Document doc) {
		mDic = new Hashtable<String, WordData>();
		mDoc = doc;
	}

	@Override
	public void start() {
		System.out.println("Word Read Begin...");
		IWordDataSerilizer ser = new WordDataSerilizer();
		System.out.println("Word Read Done...");
		
		System.out.println("Starting To Comb Words...");
		
		System.out.println("Comb Done...");
	}

}
