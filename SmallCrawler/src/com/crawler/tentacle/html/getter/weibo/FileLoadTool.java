package com.crawler.tentacle.html.getter.weibo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileLoadTool {
	public String loadStringFromFile(String dir) {

		try {
			InputStream ist = new FileInputStream(dir);
			BufferedReader r = new BufferedReader(new InputStreamReader(ist));

			String result = "";

			String line = r.readLine();

			while (line != null) {
				result += line + "\n";
				line = r.readLine();
			}

			r.close();
			ist.close();
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
}
