import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import org.jsoup.Jsoup;

public class TestCaching {
	public static void cacheSource() throws IOException {

		// Loading config file
		Properties properties = new Properties();
		InputStream inputConfig = ClassLoader.getSystemResourceAsStream("config.properties");

		try {

			properties.load(inputConfig);
			inputConfig.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (inputConfig != null) {
				try {
					inputConfig.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// creating Cache directory
		File theDir = new File("Cache");
		if (!theDir.exists()) {

			try {
				theDir.mkdir();
			} catch (SecurityException se) {
			}
		}
		// saving HTML code to file

		String page = properties.getProperty("testPage"); // which page to test
		String htmlCode = Jsoup.connect(properties.getProperty("pagesParsingLink") + page).get().outerHtml();
		File cacheFile = new File("Cache/TestCache.txt");
		// switch "fasle" to "true" if you don't want to overwrite file
		FileWriter fileWriter = new FileWriter(cacheFile, false);
		fileWriter.write(htmlCode);
		fileWriter.close();

	}
}