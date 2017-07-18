import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Cache {
	public static void start() throws IOException {

		// getting number of pages
		Document pages = Jsoup.connect("https://vashmagazin.ua/nerukhomist/kvartyry/").get();
		Elements pgElements = pages.getElementsByAttributeValue("class", "page");
		ArrayList<Object> pagesList = new ArrayList<>();
		pgElements.forEach(pgElement -> {
			Element pageElement = pgElement.child(0);
			String pg = pageElement.text();
			pagesList.add(pg);

		});

		Object lp = pagesList.get(3);
		int lastPage = Integer.valueOf((String) lp);

		// saving HTML code to file
		for (int page = 1; page <= lastPage; page++) {

			String html = Jsoup
					.connect("https://vashmagazin.ua/nerukhomist/kvartyry/?item_price1=&item_price2=&page=" + page)
					.get().outerHtml();
			File newTextFile = new File("D:/cache.html");
			FileWriter fw = new FileWriter(newTextFile);
			fw.write(html);
			fw.close();

		}

	}

}
