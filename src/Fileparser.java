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

		File input = new File("cache/cache.txt");
		// windows-1251 - encoding set do display cyrillic chars.
		Document parsingDocument = Jsoup.parse(input, "windows-1251");

		// data slots
		ArrayList<Apartment> apartmentList = new ArrayList<>();
		ArrayList<Rooms> roomsList = new ArrayList<>();
		ArrayList<Price> priceList = new ArrayList<>();
		ArrayList<Floor> floorList = new ArrayList<>();
		ArrayList<Area> areaList = new ArrayList<>();

		// connect to different HTML part
		Elements linkAndTitleElements = parsingDocument.getElementsByAttributeValue("class", "ner_h3");
		Elements roomsElements = parsingDocument.getElementsByAttributeValue("class", "kim");
		Elements priceElements = parsingDocument.getElementsByAttributeValue("class", "right");
		Elements floorElements = parsingDocument.getElementsByAttributeValue("style",
				"width:74px; text-align:right; padding-left: 3px; padding-right: 3px; color: #616161;");
		Elements areaElements = parsingDocument.getElementsByAttributeValue("style",
				"padding-left:0px;width:140px; text-align:right; padding-right:3px; padding-left: 3px; color: #616161;");

		// CLASS APARTMENT
		linkAndTitleElements.forEach(linkAndTitleElement -> {
			Element linkAndTitleElement1 = linkAndTitleElement.child(0);
			// link
			String link = linkAndTitleElement1.attr("href");
			// title
			String title = linkAndTitleElement1.text();

			apartmentList.add(new Apartment(link, title));

		});

		// CLASS ROOMS
		roomsElements.forEach(roomsElement -> {
			Element roomElement = roomsElement.child(0);
			// rooms
			String rooms = roomElement.text();

			roomsList.add(new Rooms(rooms));
		});

		// CLASS PRICE
		priceElements.forEach(priceElement -> {
			Element priceElement1 = priceElement.child(2);
			// PRICE IN $
			String price = priceElement1.text();

			priceList.add(new Price(price));
		});

		// CLASS FLOOR
		floorElements.forEach(floorElement -> {
			// floor
			String floor = floorElement.text();

			floorList.add(new Floor(floor));
		});

		// CLASS AREA
		areaElements.forEach(areaElement -> {

			// area
			String area = areaElement.text();

			areaList.add(new Area(area));
		});

		Map<Object, Object> linkTitleRooms = new LinkedHashMap<Object, Object>();
		// fill map "link+title" + "rooms"
		for (int i = 0; i < roomsList.size(); i++) {
			linkTitleRooms.put(apartmentList.get(i), roomsList.get(i));
		}

		Map<Object, Object> priceFloor = new LinkedHashMap<Object, Object>();
		// fill map "price" + "floor"
		for (int i = 0; i < roomsList.size(); i++) {
			priceFloor.put(priceList.get(i), floorList.get(i));
		}

		// linkTitleRooms to list
		ArrayList<Object> linkTitleRoomsList = new ArrayList<Object>(linkTitleRooms.entrySet());
		// priceFloor to list
		ArrayList<Object> priceFloorList = new ArrayList<Object>(priceFloor.entrySet());

		Map<Object, Object> priceFloorArea = new LinkedHashMap<Object, Object>();
		// fill map "priceFloorList" + "areaList"
		for (int i = 0; i < roomsList.size(); i++) {
			priceFloorArea.put(priceFloorList.get(i), areaList.get(i));
		}

		// priceFloorArea to list
		ArrayList<Object> priceFloorAreaList = new ArrayList<Object>(priceFloorArea.entrySet());

		Map<Object, Object> allInformation = new LinkedHashMap<Object, Object>();
		// fill map "linkTitleRoomsList" + "priceFloorAreaList"
		for (int i = 0; i < roomsList.size(); i++) {
			allInformation.put(linkTitleRoomsList.get(i), priceFloorAreaList.get(i));
		}

		// allInformation to list
		ArrayList<Object> allInformationList = new ArrayList<Object>(allInformation.entrySet());

		// replacing "=" from allInformationList
		String finalInformation = allInformationList.toString().replaceAll("=", "");
		List<String> finalInformationList = Arrays.asList(finalInformation.substring(1, finalInformation.length() - 1).split(", "));

		for (int i = 0; i < finalInformationList.size() - 1; i++) {
			System.out.println(finalInformationList.get(i));

		}
		// spaces to monitor last apartment elements on each page
		System.out.println();
		System.out.println();
		System.out.println();

	}

}
