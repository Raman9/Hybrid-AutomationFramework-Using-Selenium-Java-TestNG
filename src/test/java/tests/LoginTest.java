package tests;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import Base.baseTest;
import Readers.userReader;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.RegisterPage;
import pageObjects.UserHomePage;


// using Readers, .properties files

public class LoginTest  extends baseTest {
	@Test
	public void loginUser() throws InterruptedException 
	{   HomePage home = new HomePage(driver);
	    home.signupLogin();
		//registerPage register = new registerPage(driver);
		//register.getSignupHeaderText();
		
		LoginPage loginpage = new LoginPage(driver);
		
		String email = userReader.get("user4.email");
		String password = userReader.get("user4.password");

		loginpage.login(email, password);
		
		 UserHomePage userhome = new UserHomePage(driver);
		 String message = userhome.loggedIntext();
		 Assert.assertTrue(message.contains("Logged in as"),
					"Expected text not found. Expected to contain:Logged in as");
		 System.out.println(message);
		 
		 // log out user 
		 
		 userhome.logOut();
		
		/*String email = userReader.get("user4.email");
		String password = userReader.get("user4.password");
		loginpage.login(email,password);*/
		
		
	}

}
