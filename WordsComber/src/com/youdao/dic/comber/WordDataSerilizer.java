package com.youdao.dic.comber;

import org.w3c.dom.Element;

public class WordDataSerilizer implements IWordDataSerilizer {

	public final String WORD = "word";
	public final String TRANS = "trans";
	public final String PHONETIC = "phonetic";
	public final String TAGS = "tags";
	public final String PROGRESS = "progress";
	
	@Override
	public void deSerialize(WordData data, Element ele) {
		data.word = ele.getElementsByTagName(WORD).item(0).getTextContent();
		data.trans = ele.getElementsByTagName(TRANS).item(0).getTextContent();
		data.phonetic = ele.getElementsByTagName(PHONETIC).item(0).getTextContent();
		data.tags = ele.getElementsByTagName(TAGS).item(0).getTextContent();
		data.progress = Integer.parseInt(ele.getElementsByTagName(PROGRESS).item(0).getTextContent());
	}

	@Override
	public String serialize(WordData data) {
		//write wordbook label
		//write item label
		//TODO write data
		return null;
	}
}
