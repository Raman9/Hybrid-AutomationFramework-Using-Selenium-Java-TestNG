package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import Base.baseTest;
import Readers.userReader;
import Utils.DataGeneratorUtil;
import pageObjects.RegisterPage;
import testdata.UserData;

// Register a new user using Random data generator. 

public class RegisterUserTest extends baseTest {

	@Test
	public void registerUserTest() throws InterruptedException {
		log.info(">>> Registeration Starts");
		RegisterPage register = new RegisterPage(driver);

		String signupHeader = register.getSignupHeaderText();
		log.info(signupHeader);
		
		Assert.assertEquals(signupHeader, "Signup / Login", "Signup header mismatch");

		UserData randomuser = DataGeneratorUtil.getRandomUser();
		String userEmail = register.startRegistration(randomuser);
		log.info("User email address:" +userEmail);
		
		

		Thread.sleep(2000);

		UserData randomData = DataGeneratorUtil.getRandomUser();
		String userPassword = register.accountinfo(randomData);
		log.info("User passsword:" +userPassword);
		
		
		String createdmessage = register.accountcreated();
		System.out.println(createdmessage);
		Assert.assertTrue(createdmessage.contains("ACCOUNT CREATED!"),
				"Expected text not found. Expected to contain:ACCOUNT CREATED!");
		log.info(">>> Registeration Complete");
	}

}
