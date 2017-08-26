
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

public class Main {
	public static void main(String[] args) throws IOException {
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
		Document website = Jsoup.connect(properties.getProperty("website")).get();
		Elements pageElements = website.getElementsByAttributeValue("class", "page");
		ArrayList<Object> pages = new ArrayList<>();
		pageElements.forEach(pagesElement -> {
			Element pageElement = pagesElement.child(0);
			String page = pageElement.text();
			pages.add(page);

		});

		Object lastPage = pages.get(3);
		int lastPageNumber = Integer.valueOf((String) lastPage);
		// parsing and outputting website

		for (int page = 1; page <= lastPageNumber; page++) {
			List<String> result = Parser.Parse(Jsoup.connect(properties.getProperty("pagesParsingLink") + page).get());
			System.out.println("Page " + page);
			for (int i = 0; i < result.size(); i++) {
				System.out.println(i + 1 + ". " + result.get(i));
			}
		}

		// testing for existence of specific ID
		File input = new File(properties.getProperty("TestFile"));
		if (input.exists() && !input.isDirectory()) {

			List<String> result = Parser.Parse(Jsoup.parse(input, "windows-1251"));
			List<String> matchesId = result.stream().filter(it -> it.contains(properties.getProperty("id")))
					.collect(Collectors.toList());
			if (matchesId.isEmpty()) {
				System.out.println("No elements with this ID");
			}

		} else {
			TestCaching.cacheSource();
			List<String> result = Parser.Parse(Jsoup.parse(input, "windows-1251"));
			List<String> matchesId = result.stream().filter(it -> it.contains(properties.getProperty("id")))
					.collect(Collectors.toList());
			if (matchesId.isEmpty()) {
				System.out.println("No elements with this ID");
			}
		}

	}

}
