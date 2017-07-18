import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Fileparser {

	public static void start() throws IOException {

		File input = new File("D:/cache.html");
		// windows-1251 - encoding set do display cyrillic chars.
		Document doc = Jsoup.parse(input, "windows-1251");

		// data slots
		ArrayList<Apartment> aprtList = new ArrayList<>();
		ArrayList<Rooms> roomsList = new ArrayList<>();
		ArrayList<Price> priceList = new ArrayList<>();
		ArrayList<Floor> floorList = new ArrayList<>();
		ArrayList<Area> areaList = new ArrayList<>();

		// connect to different HTML part
		Elements h3Elements = doc.getElementsByAttributeValue("class", "ner_h3");
		Elements tdElements = doc.getElementsByAttributeValue("class", "kim");
		Elements prElements = doc.getElementsByAttributeValue("class", "right");
		Elements floorElements = doc.getElementsByAttributeValue("style",
				"width:74px; text-align:right; padding-left: 3px; padding-right: 3px; color: #616161;");
		Elements areaElements = doc.getElementsByAttributeValue("style",
				"padding-left:0px;width:140px; text-align:right; padding-right:3px; padding-left: 3px; color: #616161;");

		// CLASS APARTMENT
		h3Elements.forEach(h1Element -> {
			Element aElement = h1Element.child(0);
			// link
			String link = aElement.attr("href");
			// title
			String name = aElement.text();

			aprtList.add(new Apartment(link, name));

		});

		// CLASS ROOMS
		tdElements.forEach(tdElement -> {
			Element kimElement = tdElement.child(0);
			// rooms
			String kimnat = kimElement.text();

			roomsList.add(new Rooms(kimnat));
		});

		// CLASS PRICE
		prElements.forEach(prElement -> {
			Element priElement = prElement.child(2);
			// PRICE IN $
			String prc = priElement.text();

			priceList.add(new Price(prc));
		});

		// CLASS FLOOR
		floorElements.forEach(flElement -> {
			// floor
			String flr = flElement.text();

			floorList.add(new Floor(flr));
		});

		// CLASS AREA
		areaElements.forEach(arElement -> {

			// area
			String are = arElement.text();

			areaList.add(new Area(are));
		});

		Map<Object, Object> map1 = new LinkedHashMap<Object, Object>();
		// fill map "link+title" + "rooms"
		for (int i = 0; i < roomsList.size(); i++) {
			map1.put(aprtList.get(i), roomsList.get(i));
		}

		Map<Object, Object> map2 = new LinkedHashMap<Object, Object>();
		// fill map "price" + "floor"
		for (int i = 0; i < roomsList.size(); i++) {
			map2.put(priceList.get(i), floorList.get(i));
		}

		// map1 to list
		ArrayList<Object> map1List = new ArrayList<Object>(map1.entrySet());
		// map2 to list
		ArrayList<Object> map2List = new ArrayList<Object>(map2.entrySet());

		Map<Object, Object> map3 = new LinkedHashMap<Object, Object>();
		// fill map "map2List" + "area"
		for (int i = 0; i < roomsList.size(); i++) {
			map3.put(map2List.get(i), areaList.get(i));
		}

		// map3 to list
		ArrayList<Object> map3List = new ArrayList<Object>(map3.entrySet());

		Map<Object, Object> map4 = new LinkedHashMap<Object, Object>();
		// fill map "map1List" + "map3List"
		for (int i = 0; i < roomsList.size(); i++) {
			map4.put(map1List.get(i), map3List.get(i));
		}

		// map4 to list
		ArrayList<Object> map4List = new ArrayList<Object>(map4.entrySet());

		// replacing "=" from map4List
		String result = map4List.toString().replaceAll("=", "");
		List<String> finalList = Arrays.asList(result.substring(1, result.length() - 1).split(", "));

		for (int i = 0; i < finalList.size() - 1; i++) {
			System.out.println(finalList.get(i));

		}
		// spaces to monitor last apartment elements on each page
		System.out.println("   ");
		System.out.println("   ");
		System.out.println("   ");

	}

}
