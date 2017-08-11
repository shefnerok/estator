
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

		// Parsing
		for (int page = 1; page <= lastPageNumber; page++) {

			Document htmlSource = Jsoup.connect(properties.getProperty("pagesParsingLink") + page).get();

			// data slots
			ArrayList<Apartment> apartmentList = new ArrayList<>();
			ArrayList<Rooms> roomsList = new ArrayList<>();
			ArrayList<Price> priceList = new ArrayList<>();
			ArrayList<Floor> floorList = new ArrayList<>();
			ArrayList<Area> areaList = new ArrayList<>();
			// data slot for specific id testing
			ArrayList<Apartment> apartmentTestingList = new ArrayList<>();

			// connect to different HTML part
			Elements linkAndTitleElements = htmlSource.getElementsByAttributeValue("class", "ner_h3");
			Elements roomsElements = htmlSource.getElementsByAttributeValue("class", "kim");
			Elements priceElements = htmlSource.getElementsByAttributeValue("class", "right");
			Elements floorElements = htmlSource.getElementsByAttributeValue("style",
					"width:74px; text-align:right; padding-left: 3px; padding-right: 3px; color: #616161;");
			Elements areaElements = htmlSource.getElementsByAttributeValue("style",
					"padding-left:0px;width:140px; text-align:right; padding-right:3px; padding-left: 3px; color: #616161;");

			// CLASS APARTMENT
			linkAndTitleElements.forEach(linkAndTitleElement -> {
				Element linksAndTitlesElements = linkAndTitleElement.child(0);
				// link
				String link = linksAndTitlesElements.attr("href");
				// title
				String title = linksAndTitlesElements.text();

				apartmentList.add(new Apartment(link, title));
				apartmentTestingList.add(new Apartment(link, title));

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
				Element pricesElement = priceElement.child(2);
				// PRICE IN $
				String price = pricesElement.text();

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

			// map priceFloorArea to list
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
			List<String> finalInformationList = Arrays
					.asList(finalInformation.substring(1, finalInformation.length() - 1).split(", "));

			for (int i = 0; i < finalInformationList.size(); i++) {
				System.out.println(i + 1 + ". " + finalInformationList.get(i));

			}
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
			// testing for existence of specific ID
			File f = new File("src/TestCache.txt");
			if (f.exists() && !f.isDirectory()) {

				String finalTestingIfo = apartmentTestingList.toString();
				List<String> finalTestingInfoList = Arrays
						.asList(finalTestingIfo.substring(1, finalTestingIfo.length() - 1).split(", "));
				List<String> matchesId = finalTestingInfoList.stream()
						.filter(it -> it.contains(properties.getProperty("id"))).collect(Collectors.toList());

				// testing output
				if (matchesId.isEmpty()) {
					System.out.println("No elements with this ID");
				}
			} else {

				TestCaching.cacheSource();

				String finalTestingIfo = apartmentTestingList.toString();
				List<String> finalTestingInfoList = Arrays
						.asList(finalTestingIfo.substring(1, finalTestingIfo.length() - 1).split(", "));
				List<String> matchesId = finalTestingInfoList.stream()
						.filter(it -> it.contains(properties.getProperty("id"))).collect(Collectors.toList());

				// testing output
				if (matchesId.isEmpty()) {
					System.out.println("No elements with this ID");
				}
			}

		}

	}
}
