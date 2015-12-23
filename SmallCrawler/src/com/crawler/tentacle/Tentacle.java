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

public class Tentacle {
	/**get a html page from target url
	 * 
	 * */
	public String getHtml(String url)
	{
		CookieSpecProvider cookieSpecProvider = new CookieSpecProvider(){  
            public CookieSpec create(HttpContext context){  
                return new DefaultCookieSpec(){  
                    @Override  
                    public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {  
                        //Oh, I am easy;  
                    }  
                };  
            }
        };
        
        Registry<CookieSpecProvider> reg = RegistryBuilder
        		.<CookieSpecProvider> create()
        		.register(CookieSpecs.DEFAULT, new DefaultCookieSpecProvider())
        		.register("easy", cookieSpecProvider)
        		.build();
		
       RequestConfig requestConfig = RequestConfig.custom()
    		   .setCookieSpec("easy")
    		   .setSocketTimeout(5000)
    		   .setConnectTimeout(5000)
    		   .build(); 

       CloseableHttpClient httpClient = HttpClients.custom()
    		   .setDefaultCookieSpecRegistry(reg)
    		   .setDefaultRequestConfig(requestConfig)
    		   .build();
       
       HttpGet httpGet = new HttpGet(url);
       httpGet.setConfig(requestConfig);
       String html = "html Get Failed";
       try{
    	   CloseableHttpResponse response = httpClient.execute(httpGet);
    	   html = EntityUtils.toString(response.getEntity());
    	   
       }catch(IOException e){
    	   e.printStackTrace();
       }
       
		return html;
	}
	
	public void write2text(String data, String savePath) throws IOException
	{
		File htmlText = new File(savePath);
		htmlText.createNewFile();
		FileWriter fw = new FileWriter(htmlText);
		BufferedWriter bw = new BufferedWriter(fw);
        Pattern p = Pattern.compile("\"id\":\\s\"\\d{19}\",(\\n*?)|(\\s*?)\"content\":\\s\".*?\",(\\n*?)|(\\s*?)\"prettyTime\":\\s\".*?\"");  
        Matcher m = p.matcher(data); 
        
        while(m.find()){
        	System.out.println(m.group());
        	bw.write(m.group());
        }
        bw.close();       
	}
}
