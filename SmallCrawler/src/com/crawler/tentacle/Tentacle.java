package com.crawler.tentacle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.DefaultCookieSpec;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.crawler.tentacle.html.getter.HtmlGetter;
import com.crawler.tentacle.html.getter.IHtmlGetter;

public class Tentacle {
	/**get a html page from target url
	 * 
	 * */
	public String getHtml(String url)
	{
		IHtmlGetter getter = new HtmlGetter();
		return getter.getHtml(url);
	}

	public void write2text(String data, String savePath) throws IOException
	{
        File f = new File(savePath);  
        FileWriter fw = new FileWriter(f);  
        BufferedWriter bw = new BufferedWriter(fw);  
        bw.write(data);  
        bw.close();        
	}
}
