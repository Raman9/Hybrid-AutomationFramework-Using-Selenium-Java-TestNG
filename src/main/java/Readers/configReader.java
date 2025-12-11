package Readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class configReader { // Assuming your class name starts with lowercase 'c'

    private static Properties properties;

    public static void loadConfig() {
        properties = new Properties();
        
        // This looks for the file in 'src/test/resources' automatically
        try (InputStream input = configReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find config.properties");
            }
            
            properties.load(input);
            
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config properties");
        }
    }

    public static String getProperty(String key) {
        if (properties == null) {
            loadConfig();
        }
        return properties.getProperty(key);
    }
}