package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utils.waitUtils;

public class OrderConfirmationPage {
	WebDriver driver;

	public OrderConfirmationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//b[normalize-space()='Order Placed!']")
	WebElement orderMessage;
	@FindBy(xpath = "//p[normalize-space()='Congratulations! Your order has been confirmed!']")
	WebElement confirmMessage;
	@FindBy(xpath = "//a[normalize-space()='Download Invoice']")
	WebElement downloadInvoice;

	public String orderPlaceMessage() {
		WebElement order = waitUtils.waitForVisibility(driver, orderMessage, 5);
		String orderText = order.getText();

		return orderText;
	}

	public String orderConfirmMessage() {
		WebElement confirm = waitUtils.waitForVisibility(driver, confirmMessage, 5);
		String confirmText = confirm.getText();
		return confirmText;

	}

	public void invoiceDownload() {
		WebElement invoiceButton = waitUtils.waitForClickable(driver, downloadInvoice, 5);
		invoiceButton.click();

	}

}
