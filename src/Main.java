import java.io.File;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {

		File f = new File("cache/cache.txt");
		if (f.exists() && !f.isDirectory()) {
			Fileparser.start();
		} else {
			Cache.start();
			Fileparser.start();
		}

	}
}
