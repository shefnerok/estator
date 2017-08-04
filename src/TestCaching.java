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

		// saving HTML code to file

		String page = properties.getProperty("testPage"); // which page to test
		String htmlCode = Jsoup.connect(properties.getProperty("pagesParsingLink") + page).get().outerHtml();
		File cacheFile = new File("src/TestCache.txt");
		// switch "fasle" to "true" if you don't want to ovewrite file
		FileWriter fileWriter = new FileWriter(cacheFile, false);
		fileWriter.write(htmlCode);
		fileWriter.close();

	}
}