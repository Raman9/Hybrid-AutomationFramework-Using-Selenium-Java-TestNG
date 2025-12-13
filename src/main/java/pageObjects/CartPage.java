package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utils.waitUtils;

public class CartPage {
    WebDriver driver;
	public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	@FindBy(xpath="//li[normalize-space()='Shopping Cart']")
	WebElement cartPageHeader;
	
	@FindBy(xpath="//a[normalize-space()='Proceed To Checkout']")
	WebElement proceedCheckout;
	
	public String getCardTitle() {
		waitUtils.waitForVisibility(driver, cartPageHeader, 5);
		String headerText = cartPageHeader.getText();
		return headerText;
		
	}
	public void proceedCheckoutbutton() {
		waitUtils.waitForClickable(driver, proceedCheckout, 5);
		proceedCheckout.click();
	}
	
	
}
