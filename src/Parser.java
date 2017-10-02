import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	//static List<String> finalInformationList;

	public static ArrayList<Apartment> Parse(Document a) throws IOException {
		Document parsingDocument = a;

		// data slot
		ArrayList<Apartment> apartments = new ArrayList<>();
		

		// connect to apartment HTML part
		Elements roomElement = parsingDocument.getElementsByAttributeValue("class", "ogo-buf");
		// parsing
		roomElement.forEach(Element -> {
			Element priceElement = Element.attr("class", "right_border_table");
			String title1 = Element.getElementsByAttributeValue("class", "ner_h3").text().replaceAll("карта", "").replace("'", "Т");
			String url1 = "https://vashmagazin.ua"+Element.getElementsByAttributeValueContaining("href", "nerukho").attr("href");
			String rooms1 = Element.getElementsByAttributeValueContaining("style", "font-weight:bold").text();
			String floor1 = Element.getElementsByAttributeValue("style", "width:74px; text-align:right; padding-left: 3px; padding-right: 3px; color: #616161;").text().replaceAll("пов: ", "");
			String area1 =  Element.getElementsByAttributeValue("style",
					"padding-left:0px;width:140px; text-align:right; padding-right:3px; padding-left: 3px; color: #616161;").text().replaceAll("м2: ", "");
			String price = priceElement.getElementsByTag("b").text();
			
			apartments.add(new Apartment(title1, url1, rooms1, floor1, area1, price));

		});
	
		// testing for missing elements
		List<Apartment> matchesNull = apartments.stream().filter(it -> (it).toString().contains("null"))
				.collect(Collectors.toList());

		if (matchesNull.isEmpty()) {

		} else {
			System.out.println("found error");
			for (int i = 0; i < matchesNull.size(); i++) {
				System.out.println(i + 1 + ". " + matchesNull.get(i));

			}
		}
			
return apartments;


	}
}
