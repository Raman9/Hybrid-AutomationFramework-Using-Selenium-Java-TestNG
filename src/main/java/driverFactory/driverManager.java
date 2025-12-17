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

	private driverManager() {
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void initDriver(String browser) {
		File folder = new File(DOWNLOAD_DIR);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		if (driver.get() != null)
			return;

		if (browser == null || browser.isEmpty()) {
			browser = "chrome";
		}

		boolean isHeadless = Boolean.parseBoolean(configReader.getProperty("headless"));

		if (browser == null || browser.isEmpty()) {
			browser = "chrome";
		}
		// Inside your initDriver method...

		String osName = System.getProperty("os.name").toLowerCase();
		boolean isLinux = osName.contains("nux") || osName.contains("nix");

		switch (browser.toLowerCase()) {

		    // ============================================
		    //              CHROME SETUP
		    // ============================================
		    case "chrome":
		        ChromeOptions chromeOptions = new ChromeOptions();
		        
		        // 1. Basic Preferences
		        Map<String, Object> chromePrefs = new HashMap<>();
		        chromePrefs.put("download.default_directory", DOWNLOAD_DIR);
		        chromePrefs.put("download.prompt_for_download", false);
		        chromeOptions.setExperimentalOption("prefs", chromePrefs);
		        chromeOptions.addArguments("--remote-allow-origins=*");

		        if (isLinux) {
		            // 🛑 CI/CD (GitHub Actions) MUST use these options or it CRASHES
		            System.out.println("🐧 LINUX DETECTED: Forcing Headless & No-Sandbox Mode for Chrome");
		            chromeOptions.addArguments("--headless=new");
		            chromeOptions.addArguments("--no-sandbox");             // <--- CRITICAL
		            chromeOptions.addArguments("--disable-dev-shm-usage");  // <--- CRITICAL
		            chromeOptions.addArguments("--window-size=1920,1080");
		        } 
		        else {
		            // 💻 Windows (Local): Use your .exe and Config flag
		            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
		            if (isHeadless) { 
		                chromeOptions.addArguments("--headless=new"); 
		            }
		            chromeOptions.addArguments("--start-maximized");
		        }

		        driver.set(new ChromeDriver(chromeOptions));
		        break;

		    // ============================================
		    //               EDGE SETUP
		    // ============================================
		    case "edge":
		        EdgeOptions edgeOptions = new EdgeOptions();
		        
		        // 1. Basic Preferences
		        Map<String, Object> edgePrefs = new HashMap<>();
		        edgePrefs.put("download.default_directory", DOWNLOAD_DIR);
		        edgePrefs.put("download.prompt_for_download", false);
		        edgePrefs.put("plugins.always_open_pdf_externally", true);
		        edgeOptions.setExperimentalOption("prefs", edgePrefs);
		        edgeOptions.addArguments("--remote-allow-origins=*");

		        if (isLinux) {
		            // 🛑 CI/CD (GitHub Actions) MUST use these options or it CRASHES
		            System.out.println("🐧 LINUX DETECTED: Forcing Headless & No-Sandbox Mode for Edge");
		            edgeOptions.addArguments("--headless=new");
		            edgeOptions.addArguments("--no-sandbox");             // <--- CRITICAL
		            edgeOptions.addArguments("--disable-dev-shm-usage");  // <--- CRITICAL
		            edgeOptions.addArguments("--window-size=1920,1080");
		        } 
		        else {
		            // 💻 Windows (Local): Use your .exe and Config flag
		            System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "/drivers/msedgedriver.exe");
		            if (isHeadless) {
		                edgeOptions.addArguments("--headless=new");
		            }
		            edgeOptions.addArguments("--start-maximized");
		        }

		        driver.set(new EdgeDriver(edgeOptions));
		        break;
		}}
		/*
		switch (browser.toLowerCase()) {
		case "chrome":
		    ChromeOptions options = new ChromeOptions();
		    Map<String, Object> prefs = new HashMap<>();
		    prefs.put("download.default_directory", DOWNLOAD_DIR);
		    prefs.put("download.prompt_for_download", false);
		    options.setExperimentalOption("prefs", prefs);
		    
		    options.addArguments("--remote-allow-origins=*");
		    options.addArguments("--start-maximized");

		    // --- HYBRID DRIVER PATH (Windows vs Linux) ---
		    if (System.getProperty("os.name").toLowerCase().contains("win")) {
		        // 1. Windows (Local): Use your specific .exe file
		        String chromeDriverPath = System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "chromedriver.exe";
		        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		    } 
		    // 2. Linux (GitHub Actions): Do nothing. Selenium Manager handles the download automatically.

		    // --- CRITICAL FIX FOR GITHUB ACTIONS ---
		    // If we are NOT on Windows (meaning we are on Linux/CI), we MUST apply these fixes:
		    if (!System.getProperty("os.name").toLowerCase().contains("win")) {
		        options.addArguments("--headless=new"); // No GUI
		        options.addArguments("--no-sandbox");   // ⚠️ Essential: Prevents the "Chrome instance exited" crash
		        options.addArguments("--disable-dev-shm-usage"); // Prevents memory errors
		        options.addArguments("--window-size=1920,1080");
		    }
		    // Alternatively, if you use your config file's 'isHeadless' flag:
		    else if (isHeadless) {
		        options.addArguments("--headless=new");
		        options.addArguments("--window-size=1920,1080");
		    }

		    driver.set(new ChromeDriver(options));
		    break;

		case "edge":
			EdgeOptions eOptions = new EdgeOptions();
			Map<String, Object> edgePrefs = new HashMap<>();
			edgePrefs.put("download.default_directory", DOWNLOAD_DIR);
			edgePrefs.put("download.prompt_for_download", false);
			edgePrefs.put("plugins.always_open_pdf_externally", true);
			eOptions.setExperimentalOption("prefs", edgePrefs);
			eOptions.addArguments("--start-maximized");
			eOptions.addArguments("--remote-allow-origins=*");

			//
			if (System.getProperty("os.name").toLowerCase().contains("win")) {

				String edgeDriverPath = System.getProperty("user.dir") + File.separator + "drivers" + File.separator
						+ "msedgedriver.exe";

				System.setProperty("webdriver.edge.driver", edgeDriverPath);

				File driverFile = new File(edgeDriverPath);
				if (!driverFile.exists()) {
					System.out.println("⚠️ WARNING: msedgedriver.exe not found at: " + edgeDriverPath);
					System.out.println("Please download it and place it in the 'drivers' folder.");
				}
			}
			// On Linux (Pipeline)

			if (isHeadless) {
				eOptions.addArguments("--headless=new");
				eOptions.addArguments("--window-size=1920,1080");
			}

			driver.set(new EdgeDriver(eOptions));
			break;

		}
	}
*/
	public static boolean waitForFileDownload(String fileName, int timeoutSeconds) {
		File dir = new File(DOWNLOAD_DIR);
		int totalWaited = 0;

		while (totalWaited < (timeoutSeconds * 1000)) {
			File[] dirContents = dir.listFiles();
			if (dirContents != null) {
				for (File file : dirContents) {
					if (file.getName().equals(fileName) && !file.getName().endsWith(".crdownload")
							&& !file.getName().endsWith(".part")) {
						if (file.length() > 0)
							return true;
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