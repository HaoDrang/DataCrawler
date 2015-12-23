package com.crawler.tentacle;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.DefaultCookieSpec;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import org.apache.http.protocol.HttpContext;

public class Tentacle {
	public String getHtml()
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
       
       //CloseableHttpClient client = HttpClients.custom()
    	//	   .setDefaultCookieSpecRegistry(cookieSpecRegistry)
    		   
        
		return "";
	}
}
