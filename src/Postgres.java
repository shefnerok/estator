import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.jsoup.Jsoup;

public class Postgres {
	public static void insert() throws ClassNotFoundException, SQLException, IOException {
		// Postgress Connection
		Class.forName("org.postgresql.Driver");
		Connection connect = DriverManager.getConnection("jdbc:postgresql://192.168.99.100:32768/postgres", "postgres",
				"123");
		Statement st = connect.createStatement();

		File cache = new File("Cache/cache.txt");
		List<Apartment> result = Parser.Parse(Jsoup.parse(cache, "windows-1251"));

		for (int i = 0; i < result.size(); i++) {

			String title = result.get(i).getTitle();
			String url = result.get(i).getUrl();
			String rooms = result.get(i).getRooms();
			String floor = result.get(i).getFloor();
			String area = result.get(i).getArea();
			String price = result.get(i).getPrice();

			String insert = "insert into estator (title, url, rooms, floor, area, price) values " + "('" + title
					+ "' ,'" + url + "' ,'" + rooms + "' ,'" + floor + "' ,'" + area + "' ,'" + price + "');";

			st.executeUpdate(insert);

		}

	}

}
