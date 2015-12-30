package com.crawler.tentacle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.crawler.tentacle.html.analyse.IHtmlAnalyse;
import com.crawler.tentacle.html.analyse.WeiboSearchHtmlAnalyser;
import com.crawler.tentacle.html.getter.IHtmlGetter;
import com.crawler.tentacle.html.getter.WeiboGetter;

public class Tentacle {
	/**get a html page from target url
	 * 
	 * */
	@SuppressWarnings("null")
	public String getHtml(String url)
	{
		IHtmlGetter getter = null;
		try {
			getter = new WeiboGetter(false, "12r9420x@outlook.com", "YNQ9yvffqp6xc2b3");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getter.getHtml(url);
	}

	public void write2text(String data, String savePath) throws IOException
	{
        File f = new File(savePath);
        if(!f.getParentFile().exists())
        {
        	if(!f.getParentFile().mkdirs())
        	{
        		// TODO use log system
        		System.out.println("Error target file directory create failed!\n"
        				+ "savePath");
        		return;
        	}
        }
        
        if(!f.exists())
        {
        	f.createNewFile();
        }
        FileWriter fw = new FileWriter(f);  
        BufferedWriter bw = new BufferedWriter(fw);  
        bw.write(data);  
        bw.close();        
	}
	
	public String[] parseHtml(String html)
	{
		IHtmlAnalyse parser = new WeiboSearchHtmlAnalyser();
		return parser.Analyse(html);
	}
}
