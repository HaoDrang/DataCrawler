package com.youdao.dic.comber;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class YouDaoWordComber_v6_3 implements IWordComber {

	private static final long REQUEST_DELAY = 800;
	private static final String SAVE_FILE_DIR = "./temp/youdaodic_v_6_3_output.xml";
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
		
		System.out.println("Starting To Comb Words...");
		
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
				
				try {
					Thread.sleep(REQUEST_DELAY);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		}
		
		System.out.println("Print Combed Words...");
		
		// serialize 
		StringBuilder sb = new StringBuilder();
		sb.append("<wordbook>");
		
		for (Entry<String, WordData> entry : mDic.entrySet()) {
			WordData value = entry.getValue();
			sb.append(ser.serialize(value));
		}
		
		sb.append("</wordbook>");
		
		System.out.println("Comb Done...");
		
		saveCombToFile(SAVE_FILE_DIR, sb.toString());
	}

	private void saveCombToFile(String saveFileDir, String content) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFileDir), "UTF-8"));
			bw.write(content);
			bw.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean normalizeData(WordData newData) {
		
		if(mDic.containsKey(newData.word))
			return false;
		
		mTestWord.readData(newData.word);
		
		if(newData.word.contentEquals(mTestWord.getWord())){
			
		}else{
			newData.word = mTestWord.getWord();
			if(mDic.containsKey(newData.word)){
				return false;
			}
		}
		
		if(newData.phonetic.contentEquals("<![CDATA[]]>")){
			newData.phonetic = mTestWord.getPhonetic();
		}
		
		if(newData.phonetic.contentEquals("[]")){
			newData.phonetic = mTestWord.getPhonetic();
		}
		
		if(newData.tags.isEmpty()){
			newData.tags = mTestWord.getTags();
		}
		
		return true;
	}

}
