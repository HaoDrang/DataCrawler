package com.youdao.dic.comber;

public class WordData {

	public String word;
	public String trans;
	public String phonetic;
	public String tags;
	public int progress;
	
	public final String TAG_ITEM_L = "<item>";
	public final String TAG_ITEM_R = "</item>";
	public final String TAG_WORD_L = "<word>";
	public final String TAG_WORD_R = "</word>";
	public final String TAG_TRANS_L = "<trans>";
	public final String TAG_TRANS_R = "</trans>";
	public final String TAG_PHONETIC_L = "<phonetic>";
	public final String TAG_PHONETIC_R = "</phonetic>";
	public final String TAG_TAGS_L = "<tags>";
	public final String TAG_TAGS_R = "</tags>";
	public final String TAG_PROGRESS_L = "<progress>";
	public final String TAG_PROGRESS_R = "</progress>";
	
	public String serialize(){
		String itemStr = "";
		
		
		
		return itemStr;
	}
	
//	<wordbook><item>    <word>hesitation</word>
//    <trans><![CDATA[n. 犹豫]]></trans>
//    <phonetic><![CDATA[[hezɪ'teɪʃn]]]></phonetic>
//    <tags></tags>
//    <progress>-1</progress>
//</item>

}
