package com.crawler.tentacle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.crawler.tentacle.html.getter.IHtmlGetter;
import com.crawler.tentacle.html.getter.WeiboGetter;

public class Tentacle {
	/**
	 * get a html page from target url
	 * 
	 */
	public String getHtml(String url) {

		IHtmlGetter getter = null;
		try {
			getter = new WeiboGetter(true, ReadWeiboCookieData("./configs/weibocookie.txt"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getter.getHtml(url);
	}

	private String ReadWeiboCookieData(String dir) {
		String result = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(dir));
			String ln = null;
			while ((ln = br.readLine()) != null) {
				result += ln + ",";
			}
			br.close();
			result = result.substring(0, result.length() - 2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public void write2text(String data, String savePath) throws IOException {
		File f = new File(savePath);
		if (!f.getParentFile().exists()) {
			if (!f.getParentFile().mkdirs()) {
				// TODO use log system
				System.out.println("Error target file directory create failed!\n" + "savePath");
				return;
			}
		}

		if (!f.exists()) {
			f.createNewFile();
		}
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(data);
		bw.close();
	}
}
