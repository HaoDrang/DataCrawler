package com.youdao.dic.comber;

import org.w3c.dom.Document;

public class WordComberCreator {

	public static IWordComber create(String dir) {
		
		String wordData = GetFileData(dir);
		
		Document doc = ParseData(wordData);
		
		return new YouDaoWordComber_v6_3(doc);
	}

	private static Document ParseData(String wordData) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String GetFileData(String dir) {
		// TODO Auto-generated method stub
		return null;
	}

}
