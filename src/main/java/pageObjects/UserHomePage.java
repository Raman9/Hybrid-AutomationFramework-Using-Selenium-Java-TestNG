package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import Utils.waitUtils;

public class UserHomePage {
	WebDriver driver;

	public UserHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//li[10]//a[1]")
	WebElement loggedIn;
	@FindBy(css = "a[href='/logout']")
	WebElement logoutbutton;
	@FindBy(css = "a[href='/products']")
	WebElement productsIcon;
	@FindBy(xpath = "//body[1]/header[1]/div[1]/div[1]/div[1]/div[2]/div[1]/ul[1]/li[3]/a[1]")
	WebElement cartPageIcon;

	public String loggedIntext() {
		String loggedtext = loggedIn.getText();
		return loggedtext;
	}

	public void goToproductsPage() {
		waitUtils.waitForVisibility(driver, productsIcon, 5);
		productsIcon.click();
	}

	public void goToCartpage() {
		waitUtils.waitForVisibility(driver, cartPageIcon, 5);
		cartPageIcon.click();

	}

	public void logOut() {

		waitUtils.waitForClickable(driver, logoutbutton, 5);
		logoutbutton.click();
	}
}
