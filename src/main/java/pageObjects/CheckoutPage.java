package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utils.actionUtils;
import Utils.waitUtils;

public class CheckoutPage 
{ WebDriver driver;
public CheckoutPage(WebDriver driver) {
	this.driver= driver;
	PageFactory.initElements(driver, this);
}

@FindBy(xpath="//li[normalize-space()='Checkout']")
WebElement checkoutPageHeader;
@FindBy(id="address_delivery")
WebElement deliverAddressPanel;
@FindBy(xpath="//a[normalize-space()='Place Order']")
WebElement placeOrderButton;

public String getcheckoutPageTitle() {
	waitUtils.waitForVisibility(driver, checkoutPageHeader, 5);
	String headerText = checkoutPageHeader.getText();
	return headerText;
	
}
public String deliveryAddress() {
	String uiaddress = deliverAddressPanel.getText().trim();
	
	return uiaddress;
	
}
public void placeOrder() {
	actionUtils.scrollToBottom(driver);
	waitUtils.waitForClickable(driver, placeOrderButton, 5);
	placeOrderButton.click();
}

}
