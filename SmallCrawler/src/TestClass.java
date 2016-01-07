import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
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
			tentacle.start("http://weibo.com");

			File f = new File(".\\temp\\output.xls");
			if (!f.exists())
				f.createNewFile();

			OutputStream ost = new FileOutputStream(".\\temp\\output.xls");
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(ost));

			for (Iterator<String> iter = tentacle.getTable().iterator(); iter.hasNext();) {
				String data = (String) iter.next();
				w.append(data);
			}

			w.flush();
			w.close();
			ost.close();

			// WeiboAnalyser analyser = new WeiboAnalyser();
			// InputStream ist = new FileInputStream(".\\temp\\temphtml.txt");
			// BufferedReader r = new BufferedReader(new
			// InputStreamReader(ist));
			//
			// String fhtml = "";
			//
			// String line = r.readLine();
			//
			// while(line != null){
			// fhtml+=line + "\n";
			// line = r.readLine();
			// }
			//
			// r.close();
			// ist.close();

			// analyser.Analyse(fhtml);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("------------ Job Done ------------");
	}
}
