import java.util.logging.Level;
import java.util.logging.Logger;

public class TestClass {
	public static void main(String[] args) {
		
		Logger log = Logger.getLogger(CrawlerBody.class.getName());
		
		log.log(Level.SEVERE, "Logger Start...");
		
		log.log(Level.INFO, "Test Class Begin..");
		
		
	}
}
