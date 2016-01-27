package com.youdao.dic.comber;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class YoudaoDicWordHTMLTest implements IWordTest {

	private static final String COLLINS_TAB_ID = "collinsResult";

	private final String YOUDAODIC_URL_FORMAT = "http://dict.youdao.com/search?q=%s&keyfrom=dict.index";

	private String mWord = "";
	private int mStar = 0;
	private String mTrans = "";
	private String mPhonetic = "";
	private String mTags = "";
	private int mProgress = -1;

	@Override
	public void readData(String word) {

		String url = String.format(YOUDAODIC_URL_FORMAT, word);
		System.out.println(url);

		try {
			Document doc = Jsoup.connect(url).get();

			// try get collins Result
			if (!tryGetCollinsResult(doc)) {
				tryNormalResult(doc);
			}

			// System.out.println(doc.html());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void tryNormalResult(Document doc) {
		// TODO Auto-generated method stub

	}

	private boolean tryGetCollinsResult(Document doc) {
		Element collinsRoot = doc.getElementById(COLLINS_TAB_ID);

		if (collinsRoot == null) {
			return false;
		}

		mWord = collinsRoot.select("span.title").text();

		String starNumStr = collinsRoot.select("span[class^=star]").attr("class");
		mStar = Integer.parseInt(starNumStr.replaceAll("[^\\d]", ""));
		System.out.println("Star Level :" + mStar);

		Elements eles = collinsRoot.getElementsByClass("collinsMajorTrans");
		for (int i = 0; i < eles.size(); i++) {
			Element ele = eles.get(i);
			mTrans += ele.text() + "\n";
		}
		// System.out.println(mTrans);

		Elements phoeles = doc.select("span.phonetic");
		if (phoeles.size() > 0) {
			mPhonetic = phoeles.get(phoeles.size() - 1).text();
			System.out.println(mPhonetic);
		}

		mTags = "Star_" + mStar;

		return true;
	}

}
