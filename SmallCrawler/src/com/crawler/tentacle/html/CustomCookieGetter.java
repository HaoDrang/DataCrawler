package com.crawler.tentacle.html;

import java.io.IOException;

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

public class CustomCookieGetter<DefaultCompatSpec> implements IHtmlGetter {

	/**
	 * use the custom cookie strategy to prevent from cookie rejected
	 * 
	 * */
	@Override
	public String getHtml(String url) {

		CookieSpecProvider cookieSpecProvider = new CookieSpecProvider(){  
            public CookieSpec create(HttpContext context){  
                return new DefaultCookieSpec(){  
                    @Override  
                    public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {  
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
       String html = "null";
       CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			html = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(response != null)
				{
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
       
		return html;
	}

}
