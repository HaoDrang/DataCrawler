package com.youdao.dic.comber;

import org.w3c.dom.Element;

public class WordDataSerilizer implements IWordDataSerilizer {

	public final String WORD = "word";
	public final String TRANS = "trans";
	public final String PHONETIC = "phonetic";
	public final String TAGS = "tags";
	public final String PROGRESS = "progress";

	public final String ITEM_FORMAT = "<item>%s</item>";

	public final String WORD_FORMAT = "<word>%s</word>";
	public final String TRANS_FORMAT = "<trans>%s</trans>";
	public final String PHONETIC_FORMAT = "<phonetic>%s</phonetic>";
	public final String TAGS_FORMAT = "<tags>%s</tags>";
	public final String PROGRESS_FORMAT = "<progress>%s</progress>";

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

		String result = "";

		result += String.format(WORD_FORMAT, data.word);
		result += String.format(TRANS_FORMAT, data.trans);
		result += String.format(PHONETIC_FORMAT, data.phonetic);
		result += String.format(TAGS_FORMAT, data.tags);
		result += String.format(PROGRESS_FORMAT, data.progress);

		result = String.format(ITEM_FORMAT, result);

		return result;
	}
}
