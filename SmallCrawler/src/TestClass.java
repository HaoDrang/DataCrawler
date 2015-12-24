import java.util.logging.Level;
import java.util.logging.Logger;

import com.crawler.tentacle.Tentacle;

public class TestClass {
	public static void main(String[] args) {
		
		Logger log = Logger.getLogger(CrawlerBody.class.getName());
		
		log.log(Level.SEVERE, "Logger Start...");
		
		log.log(Level.INFO, "Test Class Begin..");
		
		Tentacle tentacle = new Tentacle();
		
		try {
			String ret = tentacle.getHtml("http://s.weibo.com/weibo/%25E5%259C%25A3%25E8%25AF%259E?topnav=1&wvr=6&b=1");
			tentacle.write2text(ret, "d:/crawler.txt");
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
