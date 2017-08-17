
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
		// parsing website
		for (int page = 1; page <= 1; page++) {
			Parser.Parse(Jsoup.connect(properties.getProperty("website")).get());
		}
		// outputting website
		for (int i = 0; i < Parser.finalInformationList.size(); i++) {
			System.out.println(i + 1 + ". " + Parser.finalInformationList.get(i));

		}

		// testing for existence of specific ID
		File input = new File("Cache/TestCache.txt");
		if (input.exists() && !input.isDirectory()) {
			Parser.Parse(Jsoup.parse(input, "windows-1251"));
			List<String> matchesId = Parser.finalInformationList.stream()
					.filter(it -> it.contains(properties.getProperty("id"))).collect(Collectors.toList());
			if (matchesId.isEmpty()) {
				System.out.println("No elements with this ID");
			}

		} else {
			TestCaching.cacheSource();
			Parser.Parse(Jsoup.parse(input, "windows-1251"));
			List<String> matchesId = Parser.finalInformationList.stream()
					.filter(it -> it.contains(properties.getProperty("id"))).collect(Collectors.toList());
			if (matchesId.isEmpty()) {
				System.out.println("No elements with this ID");
			}
		}

	}

}
