package com.crawler.tentacle.html.analyse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeiboAnalyser implements IHtmlAnalyse {

	private String[] mPatterns;
	public WeiboAnalyser(){
		//TODO load pattern from xml
		mPatterns = new String[]{"nickname\\\""};
	}
	
	@Override
	public boolean Analyse(String html) {
		
		for (int i = 0; i < mPatterns.length; i++) {
			Pattern r = Pattern.compile(mPatterns[i]);
			Matcher m = r.matcher(html);
			if(m.find()){
				for (int j = 0; j < m.groupCount(); j++) {
					System.out.println("Weibo:" + m.group(j));
				}
				System.out.println("*****" + m.group() + "*****");
			}else{
				System.out.print("*****No Match*****");
			}
		}
		
		return false;
	}

	@Override
	public String[] Links() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] Contents() {
		// TODO Auto-generated method stub
		return null;
	}

}
