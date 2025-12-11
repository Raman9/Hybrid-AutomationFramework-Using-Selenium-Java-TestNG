package Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import testdata.ProductData;
import testdata.UserData;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.openqa.selenium.json.Json;

public class JsonUtil {

    private static Map<String, UserData> users;

    public static void loadData() {
        if (users == null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                // FIX: Use ClassLoader to find the file in resources/testdata/
                InputStream inputStream = JsonUtil.class.getClassLoader()
                        .getResourceAsStream("users.json");

                if (inputStream == null) {
                    throw new RuntimeException("CRITICAL ERROR: Could not find 'testdata/users.json'. Check file name and location.");
                }

                users = mapper.readValue(inputStream, new TypeReference<Map<String, UserData>>() {});
                
            } catch (IOException e) {
                throw new RuntimeException("Failed to read users.json syntax", e);
            }
        }
    }

    public static UserData getUser(String userId) {
        if (users == null) {
            loadData();
        }
        return users.get(userId);
    }
    private static Map<String, ProductData> products;
    public static ProductData getProduct(String productId) {
        if (products == null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                InputStream inputStream = Json.class.getClassLoader()
                        .getResourceAsStream("products.json");
                
                // Read into a Map of <String, Product>
                products = mapper.readValue(inputStream, new TypeReference<Map<String, ProductData>>() {});
                
            } catch (Exception e) {
                throw new RuntimeException("Failed to read products.json", e);
            }
        }
        return products.get(productId);
    }
    
    
    
    
    public static void bankInfo() {
        if (users == null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                // FIX: Use ClassLoader to find the file in resources/testdata/
                InputStream inputStream = JsonUtil.class.getClassLoader()
                        .getResourceAsStream("bankInfo.json");

                if (inputStream == null) {
                    throw new RuntimeException("CRITICAL ERROR: Could not find 'testdata/users.json'. Check file name and location.");
                }

                users = mapper.readValue(inputStream, new TypeReference<Map<String, UserData>>() {});
                
            } catch (IOException e) {
                throw new RuntimeException("Failed to read users.json syntax", e);
            }
        }
    }

    
    
}
    
    
