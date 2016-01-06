import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.crawler.tentacle.Tentacle;
import com.crawler.tentacle.html.analyse.WeiboAnalyser;

public class TestClass {
	public static void main(String[] args) {
		
		Logger log = Logger.getLogger(CrawlerBody.class.getName());
		
		log.log(Level.INFO, "Logger Start...");
		
		log.log(Level.INFO, "Test Class Begin..");
		
		Tentacle tentacle = new Tentacle();
		
		try {
			//tentacle.start("http://weibo.com");
			WeiboAnalyser analyser = new WeiboAnalyser();
			InputStream ist = new FileInputStream(".\\temp\\temphtml.txt");
			BufferedReader r = new BufferedReader(new InputStreamReader(ist));
			
			String fhtml = "";
			
			String line = r.readLine();
			
			while(line != null){
				fhtml+=line + "\n";
				line = r.readLine();
			}
			
			r.close();
			ist.close();
			
			
			analyser.Analyse(fhtml);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("------------ Job Done ------------");
	}
}
