package com.youdao.dic.comber;

import java.util.Hashtable;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class YouDaoWordComber_v6_3 implements IWordComber {

	private Document mDoc = null;
	private Map<String, WordData> mDic = null;
	private IWordTest mTestWord = null;
	public YouDaoWordComber_v6_3(Document doc) {
		mDic = new Hashtable<String, WordData>();
		mTestWord = new YoudaoDicWordHTMLTest();
		mDoc = doc;
	}

	@Override
	public void start() {
		System.out.println("Creating Serializer...");
		IWordDataSerilizer ser = new WordDataSerilizer();
		System.out.println("Serializer ready");
		
		NodeList list = mDoc.getElementsByTagName("item");
		System.out.println("Total Words Count : " + list.getLength());
		
		for (int i = 0; i < list.getLength(); i++) {

			Node wordItem = list.item(i);
			if(wordItem.getNodeType() == Node.ELEMENT_NODE)
			{
				Element ele = (Element)wordItem;
				WordData newData = new WordData();
				ser.deSerialize(newData, ele);
				System.out.println(newData.word);
				
				if(normalizeData(newData)){
					mDic.put(newData.word, newData);
				}
				break;
			}
			
		}
		
		System.out.println("Word Read Begin...");

		System.out.println("Word Read Done...");
		
		System.out.println("Starting To Comb Words...");
		
		System.out.println("Comb Done...");
	}

	
	private boolean normalizeData(WordData newData) {
		
		if(mDic.containsKey(newData.word))
			return false;
		
		mTestWord.readData(newData.word);
		
		return false;
	}

}
