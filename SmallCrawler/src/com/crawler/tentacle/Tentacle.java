package com.crawler.tentacle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Tentacle {

	@SuppressWarnings("unused")
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

	public void start(String string) {
		
	}
}
