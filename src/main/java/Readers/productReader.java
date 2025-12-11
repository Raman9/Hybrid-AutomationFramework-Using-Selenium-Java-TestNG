package Readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class productReader {
	private static final Properties prop = new Properties();
    static {
        try (InputStream input = userReader.class.getClassLoader().getResourceAsStream("products.properties")) {
            if (input == null) throw new RuntimeException("users.properties not found");
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load products.properties", e);
        }
    }
    private productReader() {}
    public static String get(String key) {
        return prop.getProperty(key);
    }
}
