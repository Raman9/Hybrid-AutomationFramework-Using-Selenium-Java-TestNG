package Base;

import Readers.configReader;

import driverFactory.driverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import java.time.Duration;
import org.apache.logging.log4j.LogManager; 
import org.apache.logging.log4j.Logger;

public class baseTest {
	protected WebDriver driver;
	protected Logger log;
	@BeforeClass(alwaysRun = true)
	@Parameters("browser")

	public void setUp(String browser) {
		// 2. Initialize the Logger with the current class name
        log = LogManager.getLogger(this.getClass());
        
        log.info(">>> Starting Test Configuration");
		driverManager.initDriver(browser);
		
		log.info("Browser launched: " + browser);
        log.info("Navigating to URL...");
		
		
		this.driver = driverManager.getDriver();
		driver.manage().timeouts()
				.implicitlyWait(Duration.ofSeconds(Long.parseLong(configReader.getProperty("implicit.wait"))));
		driver.manage().window().maximize();
		driver.get(configReader.getProperty("base.url"));
	}

	
	 @AfterClass(alwaysRun = true) public void tearDown() {
		 log.info(">>> Test Finished. Closing Driver.");
		 driverManager.quitDriver(); }
	 

}
