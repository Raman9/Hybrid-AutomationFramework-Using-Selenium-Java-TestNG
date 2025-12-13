package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Readers.userReader;
import Utils.actionUtils;
import Utils.waitUtils;

public class PaymentPage {
	WebDriver driver;

	public PaymentPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//li[normalize-space()='Payment']")
	WebElement paymentPageHeader;

	@FindBy(xpath = "//input[@data-qa='name-on-card']")
	WebElement nameCard;

	@FindBy(xpath = "//input[@data-qa='card-number']")
	WebElement cardNumber;

	@FindBy(xpath = "//input[@placeholder='ex. 311']")
	WebElement cvc;
	@FindBy(xpath = "//input[@placeholder='MM']")
	WebElement expiry;
	@FindBy(xpath = "//input[@placeholder='YYYY']")
	WebElement year;
	@FindBy(id = "submit")
	WebElement submit;

	public String getpaymentPageTitle() {
		waitUtils.waitForVisibility(driver, paymentPageHeader, 5);
		String paymentheaderText = paymentPageHeader.getText();
		return paymentheaderText;
	}

	public void confirmPayment(String userKey) {
		String name = userReader.get(userKey + ".nameoncard");

		nameCard.sendKeys(name);

		String numberoncard = userReader.get(userKey + ".cardnumber");

		cardNumber.sendKeys(numberoncard);

		String cvconcard = userReader.get(userKey + ".cvc");

		cvc.sendKeys(cvconcard);

		String expiration = userReader.get(userKey + ".expiration");

		expiry.sendKeys(expiration);

		String yearoncard = userReader.get(userKey + ".year");

		year.sendKeys(yearoncard);
		actionUtils.scrollDown(driver);
		waitUtils.waitForClickable(driver, submit, 5);
		submit.click();

	}

}