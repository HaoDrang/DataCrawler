package com.youdao.dic.comber;

import org.w3c.dom.Element;

public class WordDataSerilizer implements IWordDataSerilizer {

	@Override
	public void deSerialize(WordData data, Element ele) {
		data.word = "";
		data.tras = "";
		data.phonetic = "";
		data.tags = "";
		data.progress = 0;
	}

	@Override
	public String serialize(WordData data) {
		//write wordbook label
		//write item label
		// write data
		return null;
	}
}
