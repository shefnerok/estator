
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
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

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		//
		// parsing file and loading into DB
		File cache = new File("Cache/cache.txt");
		if (cache.exists() && !cache.isDirectory()) {
			Postgres.insert();
		} else {
			Cache.start();
			Postgres.insert();


		}
		IdTesting.test();

	}

}
