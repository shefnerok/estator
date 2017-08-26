import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	//static List<String> finalInformationList;

	public static List<String> Parse(Document a) throws IOException {
		Document parsingDocument = a;

		// data slots
		ArrayList<Title> apartmens = new ArrayList<>();
		ArrayList<Rooms> rooms = new ArrayList<>();
		ArrayList<Price> prices = new ArrayList<>();
		ArrayList<Floor> floors = new ArrayList<>();
		ArrayList<Area> areas = new ArrayList<>();

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
			Element linkAndTitle = linkAndTitleElement.child(0);
			// link
			String link = linkAndTitle.attr("href");
			// title
			String title = linkAndTitle.text();

			apartmens.add(new Title(link, title));

		});

		// CLASS ROOMS
		roomsElements.forEach(roomsElement -> {
			Element roomElement = roomsElement.child(0);
			// rooms
			String roomsText = roomElement.text();

			rooms.add(new Rooms(roomsText));
		});

		// CLASS PRICE
		priceElements.forEach(priceElement -> {
			Element pricesElement = priceElement.child(2);
			// PRICE IN $
			String price = pricesElement.text();

			prices.add(new Price(price));
		});

		// CLASS FLOOR
		floorElements.forEach(floorElement -> {
			// floor
			String floor = floorElement.text();

			floors.add(new Floor(floor));
		});

		// CLASS AREA
		areaElements.forEach(areaElement -> {

			// area
			String area = areaElement.text();

			areas.add(new Area(area));
		});

		Map<Object, Object> linkTitleRooms = new LinkedHashMap<Object, Object>();
		// fill map "link+title" + "rooms"
		for (int i = 0; i < rooms.size(); i++) {
			linkTitleRooms.put(apartmens.get(i), rooms.get(i));
		}

		Map<Object, Object> priceFloor = new LinkedHashMap<Object, Object>();
		// fill map "price" + "floor"
		for (int i = 0; i < rooms.size(); i++) {
			priceFloor.put(prices.get(i), floors.get(i));
		}

		// linkTitleRooms to list
		ArrayList<Object> linkTitleRoomsList = new ArrayList<Object>(linkTitleRooms.entrySet());
		// priceFloor to list
		ArrayList<Object> priceFloorList = new ArrayList<Object>(priceFloor.entrySet());

		Map<Object, Object> priceFloorArea = new LinkedHashMap<Object, Object>();
		// fill map "priceFloorList" + "areaList"
		for (int i = 0; i < rooms.size(); i++) {
			priceFloorArea.put(priceFloorList.get(i), areas.get(i));
		}

		// priceFloorArea to list
		ArrayList<Object> priceFloorAreaList = new ArrayList<Object>(priceFloorArea.entrySet());

		Map<Object, Object> allInformation = new LinkedHashMap<Object, Object>();
		// fill map "linkTitleRoomsList" + "priceFloorAreaList"
		for (int i = 0; i < rooms.size(); i++) {
			allInformation.put(linkTitleRoomsList.get(i), priceFloorAreaList.get(i));
		}

		// allInformation to list
		ArrayList<Object> allInformationList = new ArrayList<Object>(allInformation.entrySet());

		// replacing "=" from allInformationList
		String finalInformation = allInformationList.toString().replaceAll("=", "");
		List<String> finalInformationList = Arrays.asList(finalInformation.substring(1, finalInformation.length() - 1).split(", "));

		// testing for missing elements
		List<String> matchesNull = finalInformationList.stream().filter(it -> it.contains("null"))
				.collect(Collectors.toList());

		if (matchesNull.isEmpty()) {

		} else {
			System.out.println("found error");
			for (int i = 0; i < matchesNull.size(); i++) {
				System.out.println(i + 1 + ". " + matchesNull.get(i));

			}
		}
			
return finalInformationList;
	}
}
