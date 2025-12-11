package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Readers.userReader;
import Utils.waitUtils;

public class LoginPage
{
	WebDriver driver;
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(name = "email")
	private WebElement email;
	@FindBy(name = "password")
	private WebElement password;
	
	@FindBy(css = "button[type='submit'][data-qa='login-button']")
	private WebElement loginbutton;
	
	
	
	public void login(String useremail, String userpassword) {
		email.sendKeys(useremail);
		password.sendKeys(userpassword);
	loginbutton.click();
		
		
	}
}
