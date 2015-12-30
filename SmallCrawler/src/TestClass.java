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
			String ret = tentacle.getHtml("http://weibo.cn/?since_id=DaOsOyQvo&max_id=DaO8w5vsw&prev_page=3&page=4,"
					+ "http://weibo.cn/?since_id=DaOsOyQvo&max_id=DaO8w5vsw&prev_page=4&page=5,"
					+ "http://weibo.cn/?since_id=DaOsOyQvo&max_id=DaO8w5vsw&prev_page=5&page=6,"
					+ "http://weibo.cn/?since_id=DaOsOyQvo&max_id=DaO8w5vsw&prev_page=6&page=7,"
					+ "http://weibo.cn/?since_id=DaOsOyQvo&max_id=DaO8w5vsw&prev_page=7&page=8");
			tentacle.write2text(ret, "./html/crawler.html");
			//String[] dataLs = tentacle.parseHtml(ret);
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("------------ Job Done ------------");
	}
}
