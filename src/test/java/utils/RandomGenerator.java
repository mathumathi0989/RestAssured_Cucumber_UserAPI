package utils;

import com.github.javafaker.Faker;

public class RandomGenerator {

	 private Faker faker = new Faker();

	    public String generateRandomContactNumber() {
	        // Generate a random phone number
	    	 return faker.number().digits(10);
	    }
	    public String generateRandomEmail() {
	        // Generate a random email address
	        return faker.internet().emailAddress();
	    }
	    
	
	
	
	
	
}
