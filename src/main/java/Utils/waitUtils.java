package Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class waitUtils {
	private waitUtils() {}


	public static WebElement waitForVisibility(WebDriver driver, WebElement element, int seconds) {
	return new WebDriverWait(driver, Duration.ofSeconds(seconds))
	.until(ExpectedConditions.visibilityOf(element));
	} 


	public static WebElement waitForClickable(WebDriver driver, WebElement element, int seconds) {
	return new WebDriverWait(driver, Duration.ofSeconds(seconds))
	.until(ExpectedConditions.elementToBeClickable(element));
	}
}
