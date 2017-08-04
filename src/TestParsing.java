import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestParsing {
	public static void startTest() throws IOException {
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
		File input = new File("src/TestCache.txt");
		// windows-1251 - encoding set do display cyrillic chars.
		Document parsingDocument = Jsoup.parse(input, "windows-1251");
		// data slots
		ArrayList<Apartment> apartmentList = new ArrayList<>();
		// connect to different HTML part
		Elements linkAndTitleElements = parsingDocument.getElementsByAttributeValue("class", "ner_h3");
		// CLASS APARTMENT
		linkAndTitleElements.forEach(linkAndTitleElement -> {
			Element linkAndTitleElement1 = linkAndTitleElement.child(0);
			// link
			String link = linkAndTitleElement1.attr("href");
			// title
			String title = linkAndTitleElement1.text();

			apartmentList.add(new Apartment(link, title));

		});

		// collecting all elements that match our id in new list
		String finalTestingIfo = apartmentList.toString();
		List<String> finalTestingInfoList = Arrays
				.asList(finalTestingIfo.substring(1, finalTestingIfo.length() - 1).split(", "));
		List<String> matchesId = finalTestingInfoList.stream().filter(it -> it.contains(properties.getProperty("id")))
				.collect(Collectors.toList());

		// testing output
		if (matchesId.isEmpty()) {
			System.out.println("No elements with this ID");

		}
	}
}
