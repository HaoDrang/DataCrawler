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
			tentacle.start("http://weibo.cn/?since_id=DaOsOyQvo&max_id=DaO8w5vsw&prev_page=3&page=4");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("------------ Job Done ------------");
	}
}
