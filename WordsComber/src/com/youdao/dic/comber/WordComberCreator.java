package com.youdao.dic.comber;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class WordComberCreator {

	public static IWordComber create(String dir) {
		
		File wordFile = GetFileData(dir);
		
		Document doc = ParseData(wordFile);
		
		return new YouDaoWordComber_v6_3(doc);
	}

	private static Document ParseData(File wordFile) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(wordFile);
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static File GetFileData(String dir) {
		
		File f = new File(dir);
		if(f.exists())
		{
			return f;	
		}
		System.out.println("File Read Failed...\n DIR: " + dir);
		return null;
	}

}
