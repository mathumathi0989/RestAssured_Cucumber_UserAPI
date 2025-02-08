package utils;

import com.github.javafaker.Faker;

public class RandomGenerator {

	 private Faker faker = new Faker();

	    public String generateRandomContactNumber() {
	        // Generate a random phone number
	    	 int firstDigit = faker.number().numberBetween(1, 9);
	         String remainingDigits = faker.number().digits(9);
	         return firstDigit + remainingDigits;
	    }
	    public String generateRandomEmail() {
	        // Generate a random email address
	        return faker.internet().emailAddress();
	    }
	    
	
	
	
	
	
}
