package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utils.waitUtils;

public class HomePage 
{
	WebDriver driver;
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//header[@id='header']//li[4]")
	private WebElement signupLoginIcon;
	
public void signupLogin() 
{
	waitUtils.waitForClickable(driver, signupLoginIcon, 5);
	signupLoginIcon.click();
	
}
	
}
