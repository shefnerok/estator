import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Cache {
	public static void start() throws IOException {
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
		// getting number of pages
		Document pages = Jsoup.connect(properties.getProperty("website")).get();
		Elements pageElements = pages.getElementsByAttributeValue("class", "page");
		ArrayList<Object> pagesList = new ArrayList<>();
		pageElements.forEach(pagesElement -> {
			Element pageElement = pagesElement.child(0);
			String page = pageElement.text();
			pagesList.add(page);

		});

		Object lastPageElement = pagesList.get(3);
		int lastPageNumber = Integer.valueOf((String) lastPageElement);

		// saving HTML code to file
		for (int page = 1; page <= lastPageNumber; page++) {

			String htmlCode = Jsoup
					.connect(properties.getProperty("pagesParsingLink") + page)
					.get().outerHtml();
			//creating Cache directory
			File theDir = new File("Cache");
			if (!theDir.exists()) {
			    			  
			    try{
			        theDir.mkdir();		       
			    } 
			    catch(SecurityException se){			      
			    }        			    
			}
			
			
			File cacheFile = new File("Cache/cache.txt");
			FileWriter fileWriter = new FileWriter(cacheFile, true);
			fileWriter.write(htmlCode);
			fileWriter.close();

		}

	}

}
