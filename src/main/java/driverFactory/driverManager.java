package driverFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import Readers.configReader;

public class driverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static final String DOWNLOAD_DIR = System.getProperty("user.dir") + File.separator + "downloaded_files";

    private driverManager() {}

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void initDriver(String browser) {
        File folder = new File(DOWNLOAD_DIR);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // WARNING: If running parallel tests, this will delete files from other running tests!
        // Consider removing this delete loop if you scale to parallel execution.
       /* if (folder.listFiles() != null) {
            for (File file : folder.listFiles()) {
                file.delete();
            }
        }*/

        if (driver.get() != null) return;

        // Fallback to "chrome" if browser is null/empty
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";
        }
        
        boolean isHeadless = Boolean.parseBoolean(configReader.getProperty("headless"));

        if (browser == null || browser.isEmpty()) {
            browser = "chrome";
        }

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions cOptions = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("download.default_directory", DOWNLOAD_DIR);
                prefs.put("download.prompt_for_download", false);
                prefs.put("plugins.always_open_pdf_externally", true);
                
                cOptions.setExperimentalOption("prefs", prefs);
                cOptions.addArguments("--start-maximized");
                cOptions.addArguments("--remote-allow-origins=*"); // Fix for some connection issues
                
                if (isHeadless) {
                    cOptions.addArguments("--headless=new"); // "new" is the modern efficient mode
                    cOptions.addArguments("--window-size=1920,1080"); // Crucial for headless!
                }
                driver.set(new ChromeDriver(cOptions));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions fOptions = new FirefoxOptions();
                
                // 0 = Desktop, 1 = default download folder, 2 = custom location
                fOptions.addPreference("browser.download.folderList", 2); 
                fOptions.addPreference("browser.download.dir", DOWNLOAD_DIR);
                
                // MIME types to never ask for confirmation
                fOptions.addPreference("browser.helperApps.neverAsk.saveToDisk", 
                        "application/pdf,application/x-pdf,application/octet-stream,text/plain,text/csv,application/json");
                
                // Disable internal PDF viewer
                fOptions.addPreference("pdfjs.disabled", true);
                
                driver.set(new FirefoxDriver(fOptions));
                break;
                
            case "edge":
                // 1. Define the path to your manual driver
                String edgeDriverPath = System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "msedgedriver.exe";
                
                // 2. Set the property so Selenium knows where to look
                System.setProperty("webdriver.edge.driver", edgeDriverPath);

                EdgeOptions eOptions = new EdgeOptions();
                Map<String, Object> edgePrefs = new HashMap<>();
                
                edgePrefs.put("download.default_directory", DOWNLOAD_DIR);
                edgePrefs.put("download.prompt_for_download", false);
                edgePrefs.put("plugins.always_open_pdf_externally", true);
                
                eOptions.setExperimentalOption("prefs", edgePrefs);
                eOptions.addArguments("--start-maximized");
                eOptions.addArguments("--remote-allow-origins=*");
                
                if (isHeadless) {
                    eOptions.addArguments("--headless=new");
                    eOptions.addArguments("--window-size=1920,1080");
                }
                driver.set(new EdgeDriver(eOptions));
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }

    public static boolean waitForFileDownload(String fileName, int timeoutSeconds) {
        File dir = new File(DOWNLOAD_DIR);
        int totalWaited = 0;
        
        while (totalWaited < (timeoutSeconds * 1000)) {
            File[] dirContents = dir.listFiles();
            if (dirContents != null) {
                for (File file : dirContents) {
                    if (file.getName().equals(fileName) && !file.getName().endsWith(".crdownload") && !file.getName().endsWith(".part")) {
                        // Added .part check (Firefox temp file extension)
                        if (file.length() > 0) return true;
                    }
                }
            }
            try {
                Thread.sleep(500);
                totalWaited += 500;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}