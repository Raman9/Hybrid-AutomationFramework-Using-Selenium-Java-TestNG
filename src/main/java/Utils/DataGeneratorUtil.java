package Utils;

import com.github.javafaker.Faker;

import testdata.UserData;

public class DataGeneratorUtil {
	

    private static Faker faker = new Faker();

    /*
      Generates a completely random UserData object on the fly.
     */
    public static UserData getRandomUser() {
        UserData user = new UserData();
        user.name = faker.name().username();
        user.email = faker.internet().emailAddress();
        
        user.password = "Password123!"; // Keep password static so you can remember it, or randomize it too
        
        
        user.fname = faker.name().firstName();
        user.lname = faker.name().lastName();
        
        user.dobday = String.valueOf(faker.number().numberBetween(1, 28));
        user.dobmonth = "January"; // Keeping static to avoid dropdown complexities, or map random int to month string
        user.dobyear = String.valueOf(faker.number().numberBetween(1980, 2000));
        
        user.country = "Canada"; // Keep static if your site depends on specific countries
        user.state = "Ontario";
        user.city = faker.address().city();
        user.zip = faker.address().zipCode();
        user.mnumber = faker.number().digits(10); // Generates a random 10-digit number
        
        // Generate a random full address
        user.deliveraddress = faker.address().fullAddress(); 
        
        return user;
    }
    
 
}
