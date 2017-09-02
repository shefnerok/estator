import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;

public class IdTesting {
	public static void test() throws IOException {
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
		// testing
		File input = new File(properties.getProperty("TestFile"));
		if (input.exists() && !input.isDirectory()) {

			List<Apartment> result = Parser.Parse(Jsoup.parse(input, "windows-1251"));
			List<Apartment> matchesId = result.stream()
					.filter(it -> (it).getUrl().contains(properties.getProperty("id"))).collect(Collectors.toList());
			if (matchesId.isEmpty()) {
				System.out.println("No elements with this ID");
			}

		} else {
			TestCaching.cacheSource();
			List<Apartment> result = Parser.Parse(Jsoup.parse(input, "windows-1251"));
			List<Apartment> matchesId = result.stream()
					.filter(it -> (it).getUrl().contains(properties.getProperty("id"))).collect(Collectors.toList());
			if (matchesId.isEmpty()) {
				System.out.println("No elements with this ID");
			}
		}
	}
}
