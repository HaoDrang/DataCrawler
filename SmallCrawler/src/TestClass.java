import java.util.logging.Level;
import java.util.logging.Logger;

import com.crawler.tentacle.Tentacle;

public class TestClass {
	public static void main(String[] args) {
		
		Logger log = Logger.getLogger(CrawlerBody.class.getName());
		
		log.log(Level.INFO, "Logger Start...");
		
		log.log(Level.INFO, "Test Class Begin..");
		
		Tentacle tentacle = new Tentacle();
		
		try {
			String ret = tentacle.getHtml("http://weibo.com/huxinger");
			tentacle.write2text(ret, "./html/crawler.html");
			String[] dataLs = tentacle.parseHtml(ret);
//			for (int i = 0; i < dataLs.length; i++) {
//				System.out.println(dataLs[i]);
//			}
//			if(dataLs.length == 0) System.out.println("nothing?");
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
