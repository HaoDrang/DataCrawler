package com.youdao.dic.comber;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class YoudaoDicWordHTMLTest implements IWordTest {

	private static final String COLLINS_TAB_ID = "collinsResult";

	private static final int MAX_RETRY_COUNT = 3;

	private static final long RETRY_SLEEP_TIME = 3000;

	private String mPhonetic = "";

	private int mStar = 0;

	private String mTags = "";

	private String mTrans = "";

	private String mWord = "";

	private final String YOUDAODIC_URL_FORMAT = "http://dict.youdao.com/search?q=%s&keyfrom=dict.index";

	private final String PHONETIC_FORMAT = "<![CDATA[%s]]>";

	public String getPhonetic() {
		return mPhonetic;
	}

	public int getStar() {
		return mStar;
	}

	public String getTags() {
		return mTags;
	}

	public String getTrans() {
		return mTrans;
	}

	public String getWord() {
		return mWord;
	}

	@Override
	public void readData(String word) {

		String url = String.format(YOUDAODIC_URL_FORMAT, word);
		System.out.println(url);

		for (int i = 0; i < MAX_RETRY_COUNT; i++) {
			try {
				Document doc = Jsoup.connect(url).get();

				// try get collins Result
				if (!tryGetCollinsResult(doc)) {
					tryNormalResult(doc);
				}

				mTags = "Star_" + mStar;
				mPhonetic = String.format(PHONETIC_FORMAT, mPhonetic);

				break;
				// System.out.println(doc.html());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Read Page Failed... Wait 3 Second.");
				sleep(RETRY_SLEEP_TIME);
				System.out.println("Wake up, continue Searching...");
			}
		}
	}

	private void sleep(long retrySleepTime) {
		try {
			Thread.sleep(retrySleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean tryGetCollinsResult(Document doc) {
		Element collinsRoot = doc.getElementById(COLLINS_TAB_ID);

		if (collinsRoot == null) {
			return false;
		}

		mWord = collinsRoot.select("span.title").text();

		String starNumStr = collinsRoot.select("span[class^=star]").attr("class");
		starNumStr = starNumStr.replaceAll("[^\\d]", "");
		if (!starNumStr.isEmpty()) {
			mStar = Integer.parseInt(starNumStr);
		}
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

		return true;
	}

	private void tryNormalResult(Document doc) {
		mWord = doc.getElementsByClass("keyword").text();

		mStar = 0;

		Elements transEles = doc.getElementsByClass("trans-container");
		if (transEles.size() > 0) {
			Element simpleTransEle = transEles.get(0);
			Elements transArray = simpleTransEle.getElementsByTag("li");
			for (int i = 0; i < transArray.size(); i++) {
				mTrans += transArray.get(i).text() + "\n";
			}

			System.out.println(mTrans);
		}

		Elements phoeles = doc.select("span.phonetic");
		if (phoeles.size() > 0) {
			mPhonetic = phoeles.get(phoeles.size() - 1).text();
			System.out.println(mPhonetic);
		}
	}
}
