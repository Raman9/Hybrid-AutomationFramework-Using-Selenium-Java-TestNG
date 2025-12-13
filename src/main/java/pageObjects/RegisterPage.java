package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import Readers.userReader;
import Utils.actionUtils;
import Utils.waitUtils;
import testdata.UserData;

public class RegisterPage 
{
	WebDriver driver;
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
 
	@FindBy(xpath = "//a[normalize-space()='Signup / Login']")
	private WebElement signupHeader;

	@FindBy(xpath = "//input[@placeholder='Name']")
	private WebElement nameInput;

	@FindBy(xpath = "//input[@data-qa='signup-email']")
	private WebElement emailInput;

	@FindBy(xpath = "//button[normalize-space()='Signup']")
	private WebElement signupBtn;

	@FindBy(xpath = "//b[normalize-space()='Enter Account Information']")
	private WebElement accountInfoHeader;

	// Add other locators as needed
	@FindBy(id = "uniform-id_gender1")
	WebElement ftitle;

	@FindBy(id = "password")
	WebElement fullpassword;

	@FindBy(id = "days")
	WebElement dobday;

	@FindBy(id = "months")
	WebElement dobmonth;

	@FindBy(id = "years")
	WebElement dobyear;

	@FindBy(id = "first_name")
	WebElement firname;

	@FindBy(id = "last_name")
	WebElement lasname;

	@FindBy(id = "address1")
	WebElement addr;

	@FindBy(id = "country")
	WebElement count;

	@FindBy(id = "state")
	WebElement stat;

	@FindBy(id = "city")
	WebElement cit;

	@FindBy(id = "zipcode")
	WebElement postalcode;

	@FindBy(id = "mobile_number")
	WebElement number;

	@FindBy(css = "button[type='submit'][data-qa='create-account']")
	WebElement createaccount;

	@FindBy(css = "a[data-qa='continue-button']")
	WebElement continuebutton;

	@FindBy(xpath = "//li[10]//a[1]")
	WebElement loggedIn;

	@FindBy(xpath = "//b[normalize-space()='Account Created!']")
	WebElement accountcreatedmessage;

	

	public String getSignupHeaderText() {
		signupHeader.click();
		return waitUtils.waitForVisibility(driver, signupHeader, 10).getText();

	}

	public String startRegistration(UserData user) {
		
		nameInput.sendKeys(user.name);
		emailInput.sendKeys(user.email);
		signupBtn.click();
		return user.email;
	}

	public String getAccountInfoHeader() {
		return waitUtils.waitForVisibility(driver, accountInfoHeader, 10).getText();
	}

	public String accountinfo(UserData user) throws InterruptedException {

	    ftitle.click(); 
	    fullpassword.sendKeys(user.password);

	    waitUtils.waitForClickable(driver, dobday, 0);
	    new Select(dobday).selectByVisibleText(user.dobday);
	    waitUtils.waitForClickable(driver, dobmonth, 0);
	    new Select(dobmonth).selectByVisibleText(user.dobmonth);
	    waitUtils.waitForClickable(driver, dobyear, 0);
	    new Select(dobyear).selectByVisibleText(user.dobyear);

	    
	    
	    firname.sendKeys(user.fname);
	    lasname.sendKeys(user.lname);
	    addr.sendKeys(user.deliveraddress); 
	    
	    
	    new Select(count).selectByVisibleText(user.country);
	    
	    stat.sendKeys(user.state);
	    cit.sendKeys(user.city);
	    postalcode.sendKeys(user.zip);
	    number.sendKeys(user.mnumber);

	    actionUtils.scrollDown(driver);
	    
	    waitUtils.waitForClickable(driver, createaccount, 10).click();
	    return user.password;
	}

	

	public String accountcreated() {
		String message = accountcreatedmessage.getText();
		return message;
	}

	

}
