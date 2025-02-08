package stepDefinitions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.Map;

import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.CreateUser;
import utils.ConfigReader;
import utils.JsonDataReader;
import utils.RandomGenerator;
import utils.TestDataStore;


public class createUserStepDefinition {

	private RequestSpecification request;
	private Response response;
	private  Map<String, Object> testData;
	private String userContactNumber;
	private String userEmail;

	@Given("Admin set the POST request with the valid request body")
	public void admin_set_the_post_request_with_the_valid_request_body_and_valid_endpoint() {
		request = RestAssured.given().auth()
				.basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
				.header("Accept", "application/json").header("Content-Type", "application/json");
	}

	public void createRequestBody(String scenario) {
		testData = JsonDataReader.getScenarioData(scenario);
		RandomGenerator generator = new RandomGenerator();
		String randomContactNumber = generator.generateRandomContactNumber();
		String randomEmail = generator.generateRandomEmail();
		// Replace placeholders in test data
		String userContactNumber = testData.get("user_contact_number").toString().replace("{{randomContactNumber}}",
				randomContactNumber);
		String userEmail = testData.get("user_email_id").toString().replace("{{randomEmail}}", randomEmail);
		// Prepare request body with POJO class and set values from JSON data
		CreateUser createUser = new CreateUser();
		createUser.setUser_first_name(testData.get("user_first_name").toString());
		createUser.setUser_last_name(testData.get("user_last_name").toString());
		createUser.setUser_contact_number(userContactNumber);
		createUser.setUser_email_id(userEmail);
		CreateUser.UserAddress userAddress = new CreateUser.UserAddress();
		userAddress.setPlotNumber(testData.get("plotNumber").toString());
		userAddress.setStreet(testData.get("street").toString());
		userAddress.setState(testData.get("state").toString());
		userAddress.setCountry(testData.get("country").toString());
		userAddress.setZipCode(testData.get("zipCode").toString());
		createUser.setUserAddress(userAddress);
		// Convert the POJO to JSON string using ObjectMapper
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonRequestBody = objectMapper.writeValueAsString(createUser);
			// Log the request body as JSON
			System.out.println("Request Body: " + jsonRequestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Set the request body for POST request
		request.body(createUser).log().all();
		// Log the request body before sending the request
		System.out.println("Request Body: " + createUser.toString());
		// Get the endpoint from test data and send the POST request
		String endpoint = testData.get("endpoint").toString();
		response = request.when().post(endpoint);
	}
	
	@When("Admin sends HTTPS Request and request Body with endpoint")
	public void admin_sends_https_request_and_request_body_with_endpoint() {
		createRequestBody("valid create user");
		// Store the User ID and User First Name in TestData
		TestDataStore.setUserId(response.jsonPath().getString("user_id"));
		System.out.println("Stored User ID in TestDataStore: " + TestDataStore.getUserId());
		 // Validate JSON Schema
	    response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/userSchema.json"));
	    System.out.println("Schema Validation Passed!");
	}

	@Then("Admin receives {string} {string} Status for create user")
	public void admin_receives_status_for_create_user(String statusCode, String statusText) {
		System.out.println("Response Body: " + response.getBody().asPrettyString());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
		  // Extract response fields
	    String responseFirstName = response.jsonPath().getString("user_first_name");
	    String responseLastName = response.jsonPath().getString("user_last_name");
	    String responseContactNumber = response.jsonPath().getString("user_contact_number");
	    String responseEmail = response.jsonPath().getString("user_email_id");

	    // Extract address fields
	    String responsePlotNumber = response.jsonPath().getString("userAddress.plotNumber");
	    String responseStreet = response.jsonPath().getString("userAddress.street");
	    String responseState = response.jsonPath().getString("userAddress.state");
	    String responseCountry = response.jsonPath().getString("userAddress.country");
	    String responseZipCode = response.jsonPath().getString("userAddress.zipCode");

	    // Validate response data against request data
	    Assert.assertEquals(responseFirstName, testData.get("user_first_name").toString(), "First Name Mismatch!");
	    Assert.assertEquals(responseLastName, testData.get("user_last_name").toString(), "Last Name Mismatch!");
	    Assert.assertEquals(responseContactNumber, userContactNumber, "Contact Number Mismatch!");
	    Assert.assertEquals(responseEmail, userEmail, "Email ID Mismatch!");
	    
	    // Validate Address Fields
	    Assert.assertEquals(responsePlotNumber, testData.get("plotNumber").toString(), "Plot Number Mismatch!");
	    Assert.assertEquals(responseStreet, testData.get("street").toString(), "Street Mismatch!");
	    Assert.assertEquals(responseState, testData.get("state").toString(), "State Mismatch!");
	    Assert.assertEquals(responseCountry, testData.get("country").toString(), "Country Mismatch!");
	    Assert.assertEquals(responseZipCode, testData.get("zipCode").toString(), "Zip Code Mismatch!");

	    System.out.println("All data validation checks passed successfully!");
	    
	}
	
	@When("Admin sends HTTPS Request and request Body with mandatory")
	public void admin_sends_https_request_and_request_body_with_mandatory() {
		testData = JsonDataReader.getScenarioData("valid only mandatory");
		RandomGenerator generator = new RandomGenerator();
		String randomContactNumber = generator.generateRandomContactNumber();
		String randomEmail = generator.generateRandomEmail();
		// Replace placeholders in test data
		String userContactNumber = testData.get("user_contact_number").toString().replace("{{randomContactNumber}}",
				randomContactNumber);
		String userEmail = testData.get("user_email_id").toString().replace("{{randomEmail}}", randomEmail);
		// Prepare request body with POJO class and set values from JSON data
		CreateUser createUser = new CreateUser();
		createUser.setUser_first_name(testData.get("user_first_name").toString());
		createUser.setUser_last_name(testData.get("user_last_name").toString());
		createUser.setUser_contact_number(userContactNumber);
		createUser.setUser_email_id(userEmail);
		// Convert the POJO to JSON string using ObjectMapper
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonRequestBody = objectMapper.writeValueAsString(createUser);
			// Log the request body as JSON
			System.out.println("Request Body: " + jsonRequestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Set the request body for POST request
		request.body(createUser).log().all();
		// Log the request body before sending the request
		System.out.println("Request Body: " + createUser.toString());
		// Get the endpoint from test data and send the POST request
		String endpoint = testData.get("endpoint").toString();
		response = request.when().post(endpoint);
	}
	
	
	
	@When("Admin sends HTTPS Request and request Body with invalid endpoint")
	public void admin_sends_https_request_and_request_body_with_invalid_endpoint() {
		createRequestBody("create user invalid endpoint");
	}
	
	@Then("Admin receives {string} {string} Status")
	public void admin_receives_status(String statusCode, String statusText) {
		System.out.println("Response Body: " + response.getBody().asPrettyString());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
	}
	
	@Given("Admin set the POST request with the invalid contentType")
	public void admin_set_the_post_request_with_the_invalid_content_type() {
		request = RestAssured.given().auth()
				.basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
				.header("Accept", "application/json").header("Content-Type", "text/plain");

	}
	
	@When("Admin sends HTTPS Request and request Body with invalid content type")
	public void admin_sends_https_request_and_request_body_with_invalid_content_type() {
		testData = JsonDataReader.getScenarioData("valid create user");
		request.body("Invalid request format").log().all();
		String endpoint = testData.get("endpoint").toString();
		response = request.when().post(endpoint);
	}
	
	@When("Admin sends HTTPS Request and request Body with invalid request type")
	public void admin_sends_https_request_and_request_body_with_invalid_request_type() {
		testData = JsonDataReader.getScenarioData("valid create user");
		RandomGenerator generator = new RandomGenerator();
		String randomContactNumber = generator.generateRandomContactNumber();
		String randomEmail = generator.generateRandomEmail();
		// Replace placeholders in test data
		String userContactNumber = testData.get("user_contact_number").toString().replace("{{randomContactNumber}}",
				randomContactNumber);
		String userEmail = testData.get("user_email_id").toString().replace("{{randomEmail}}", randomEmail);
		// Prepare request body with POJO class and set values from JSON data
		CreateUser createUser = new CreateUser();
		createUser.setUser_first_name(testData.get("user_first_name").toString());
		createUser.setUser_last_name(testData.get("user_last_name").toString());
		createUser.setUser_contact_number(userContactNumber);
		createUser.setUser_email_id(userEmail);
		CreateUser.UserAddress userAddress = new CreateUser.UserAddress();
		userAddress.setPlotNumber(testData.get("plotNumber").toString());
		userAddress.setStreet(testData.get("street").toString());
		userAddress.setState(testData.get("state").toString());
		userAddress.setCountry(testData.get("country").toString());
		userAddress.setZipCode(testData.get("zipCode").toString());
		createUser.setUserAddress(userAddress);
		// Convert the POJO to JSON string using ObjectMapper
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonRequestBody = objectMapper.writeValueAsString(createUser);
			// Log the request body as JSON
			System.out.println("Request Body: " + jsonRequestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Set the request body for POST request
		request.body(createUser).log().all();
		// Log the request body before sending the request
		System.out.println("Request Body: " + createUser.toString());
		// Get the endpoint from test data and send the POST request
		String endpoint = testData.get("endpoint").toString();
		response = request.when().get(endpoint);
	}
	
	
	@When("Admin sends HTTPS Request and request Body with same contact number")
	public void admin_sends_https_request_and_request_body_with_same_contact_number() {
		testData = JsonDataReader.getScenarioData("invalid same contact number");
		RandomGenerator generator = new RandomGenerator();
		String randomEmail = generator.generateRandomEmail();
		String userEmail = testData.get("user_email_id").toString().replace("{{randomEmail}}", randomEmail);
		// Prepare request body with POJO class and set values from JSON data
		CreateUser createUser = new CreateUser();
		createUser.setUser_first_name(testData.get("user_first_name").toString());
		createUser.setUser_last_name(testData.get("user_last_name").toString());
		createUser.setUser_contact_number(testData.get("user_contact_number").toString());
		createUser.setUser_email_id(userEmail);
		CreateUser.UserAddress userAddress = new CreateUser.UserAddress();
		userAddress.setPlotNumber(testData.get("plotNumber").toString());
		userAddress.setStreet(testData.get("street").toString());
		userAddress.setState(testData.get("state").toString());
		userAddress.setCountry(testData.get("country").toString());
		userAddress.setZipCode(testData.get("zipCode").toString());
		createUser.setUserAddress(userAddress);
		// Convert the POJO to JSON string using ObjectMapper
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonRequestBody = objectMapper.writeValueAsString(createUser);
			// Log the request body as JSON
			System.out.println("Request Body: " + jsonRequestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Set the request body for POST request
		request.body(createUser).log().all();
		// Log the request body before sending the request
		System.out.println("Request Body: " + createUser.toString());
		// Get the endpoint from test data and send the POST request
		String endpoint = testData.get("endpoint").toString();
		response = request.when().post(endpoint);
		// Log the response for debugging
		System.out.println("Response Body: " + response.getBody().asPrettyString());
	}
	@Then("Admin receives {string} {string} Status with contact message")
	public void admin_receives_status_with_contact_message(String statusCode, String statusText) {
		System.out.println("Response Body: " + response.getBody().asPrettyString());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
		String actualMessage = response.jsonPath().getString("message");
		String expectedMessage = "User already exist with same contact number";
		Assert.assertEquals(actualMessage, expectedMessage);
	}
	
	@When("Admin sends HTTPS Request and request Body with same email")
	public void admin_sends_https_request_and_request_body_with_same_email() {
		testData = JsonDataReader.getScenarioData("invalid same email id");
		RandomGenerator generator = new RandomGenerator();
		String randomContactNumber = generator.generateRandomContactNumber();
		// Replace placeholders in test data
		String userContactNumber = testData.get("user_contact_number").toString().replace("{{randomContactNumber}}",
			randomContactNumber);
		// Prepare request body with POJO class and set values from JSON data
		CreateUser createUser = new CreateUser();
		createUser.setUser_first_name(testData.get("user_first_name").toString());
		createUser.setUser_last_name(testData.get("user_last_name").toString());
		createUser.setUser_contact_number(userContactNumber);
		createUser.setUser_email_id(testData.get("user_email_id").toString());
		CreateUser.UserAddress userAddress = new CreateUser.UserAddress();
		userAddress.setPlotNumber(testData.get("plotNumber").toString());
		userAddress.setStreet(testData.get("street").toString());
		userAddress.setState(testData.get("state").toString());
		userAddress.setCountry(testData.get("country").toString());
		userAddress.setZipCode(testData.get("zipCode").toString());
		createUser.setUserAddress(userAddress);
		// Convert the POJO to JSON string using ObjectMapper
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonRequestBody = objectMapper.writeValueAsString(createUser);
			// Log the request body as JSON
			System.out.println("Request Body: " + jsonRequestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Set the request body for POST request
		request.body(createUser).log().all();
		// Log the request body before sending the request
		System.out.println("Request Body: " + createUser.toString());
		// Get the endpoint from test data and send the POST request
		String endpoint = testData.get("endpoint").toString();
		response = request.when().post(endpoint);
		// Log the response for debugging
		System.out.println("Response Body: " + response.getBody().asPrettyString());
	}
	@Then("Admin receives {string} {string} Status with email message")
	public void admin_receives_status_with_email_message(String statusCode, String statusText) {
		System.out.println("Response Body: " + response.getBody().asPrettyString());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
		String actualMessage = response.jsonPath().getString("message");
		String expectedMessage = "User already exist with same email id";
		Assert.assertEquals(actualMessage, expectedMessage);
	}
	
	@Given("Admin set the POST request with the valid request body with no Auth")
	public void admin_set_the_post_request_with_the_valid_request_body_with_no_auth() {
		request = RestAssured.given().auth().none()
				.header("Accept", "application/json").header("Content-Type", "application/json");
	}
	
	@When("Admin sends HTTPS Request and request Body with no or invalid auth")
	public void admin_sends_https_request_and_request_body_with_no_or_invalid_auth() {
		createRequestBody("no auth");
	}
	
	@Given("Admin set the POST request with the valid request body with invalid basic Auth")
	public void admin_set_the_post_request_with_the_valid_request_body_with_invalid_basic_auth() {
		request = RestAssured.given().auth()
				.basic("numpy@gmail.com","invalid")
				.header("Accept", "application/json").header("Content-Type", "application/json");
	}

	@Given("Admin set the POST request")
	public void admin_set_the_post_request() {
		request = RestAssured.given().auth()
				.basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
				.header("Accept", "application/json").header("Content-Type", "application/json");
	}
		
	@When("Admin sends HTTPS Request and request Body with first name as numeric")
	public void admin_sends_https_request_and_request_body_with_first_name_as_numeric() {
		createRequestBody("invalid firstName as numeric");
	}
	@Then("Admin receives {string} {string} Status with error message")
	public void admin_receives_status_with_error_message(String statusCode, String statusText) {
		System.out.println("Response Body: " + response.getBody().asPrettyString());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
//		String actualMessage = response.jsonPath().getString("message");
//		String expectedMessage = "user FirstName is mandatory and should contains alphabets only";
//		Assert.assertEquals(actualMessage, expectedMessage);
		// Check if the response contains "user_id"
		// Fetch user_id from the response
		/*
		    String userId = response.jsonPath().getString("user_id");
		    if (userId == null || userId.isEmpty()) {
		        throw new RuntimeException("User ID extraction failed. Cannot proceed with deletion.");
		    }
		    else {
		    System.out.println("Extracted User ID: " + userId);
		    // Construct delete endpoint
		    String deleteEndpoint = ConfigReader.getProperty("baseURI") + "/uap/deleteuser/" + userId;
		    System.out.println("Deleting user at: " + deleteEndpoint);

		    // Perform DELETE request
		    Response deleteResponse = RestAssured.given()
		            .auth().basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
		            .header("Accept", "application/json")
		            .when()
		            .delete(deleteEndpoint);

		    // Log delete response details
		    System.out.println("Delete Response Status: " + deleteResponse.getStatusCode());
		    System.out.println("Delete Response Body: " + deleteResponse.getBody().asPrettyString());

		    // Validate DELETE response
		    Assert.assertEquals(deleteResponse.getStatusCode(), 200, "User deletion failed!");
		    
		} 
		*/
	}
	
	@When("Admin sends HTTPS Request and request Body with first name as empty")
	public void admin_sends_https_request_and_request_body_with_first_name_as_empty() {
		createRequestBody("invalid firstName as empty");
	}
	
	@When("Admin sends HTTPS Request and request Body with State as numeric")
	public void admin_sends_https_request_and_request_body_with_state_as_numeric() {
		createRequestBody("invalid state as numeric");
	}
	
	@When("Admin sends HTTPS Request and request Body with State as empty")
	public void admin_sends_https_request_and_request_body_with_state_as_empty() {
		createRequestBody("invalid state as empty");
	}
	
	@When("Admin sends HTTPS Request and request Body with zipcode as empty")
	public void admin_sends_https_request_and_request_body_with_zipcode_as_empty() {
		createRequestBody("invalid zipcode as empty");
	}
	
	@When("Admin sends HTTPS Request and request Body with invalid email format")
	public void admin_sends_https_request_and_request_body_with_invalid_email_format() {
		createRequestBody("invalid email");
	}
	
	@When("Admin sends HTTPS Request and request Body with empty contact number")
	public void admin_sends_https_request_and_request_body_with_empty_contact_number() {
		createRequestBody("invalid contact number as empty");
	}
	
	@When("Admin sends HTTPS Request and request Body with last name as numeric")
	public void admin_sends_https_request_and_request_body_with_last_name_as_numeric() {
	    createRequestBody("invalid last name as numeric");
	}
	
	@When("Admin sends HTTPS Request and request Body with Street as empty")
	public void admin_sends_https_request_and_request_body_with_street_as_empty() {
		createRequestBody("invalid street as empty");
	}
	
	@When("Admin sends HTTPS Request and request Body with invalid plot number")
	public void admin_sends_https_request_and_request_body_with_invalid_plot_number() {
		createRequestBody("invalid plot number");
	}
	
	@When("Admin sends HTTPS Request and request Body with contact number as alphanumeric")
	public void admin_sends_https_request_and_request_body_with_contact_number_as_alphanumeric() {
		createRequestBody("invalid contact number as alphanumeric");
	}
	
	@When("Admin sends HTTPS Request and request Body with empty plot number")
	public void admin_sends_https_request_and_request_body_with_empty_plot_number() {
		createRequestBody("invalid plot number as empty");
	}
	
	@When("Admin sends HTTPS Request and request Body with country as empty")
	public void admin_sends_https_request_and_request_body_with_country_as_empty() {
		createRequestBody("invalid country as empty");
	}
	
	@When("Admin sends HTTPS Request and request Body with contact number less than ten numbers")
	public void admin_sends_https_request_and_request_body_with_contact_number_less_than_ten_numbers() {
		createRequestBody("invalid contact number <10");
	}
	
	@When("Admin sends HTTPS Request and request Body with last name as empty")
	public void admin_sends_https_request_and_request_body_with_last_name_as_empty() {
		createRequestBody("invalid lastName as empty");
	}
	
	@When("Admin sends HTTPS Request and request Body with empty email")
	public void admin_sends_https_request_and_request_body_with_empty_email() {
		createRequestBody("invalid email as empty");
	}
	
	@When("Admin sends HTTPS Request and request Body with country as numeric")
	public void admin_sends_https_request_and_request_body_with_country_as_numeric() {
		createRequestBody("invalid country as numeric");
	}
	
	@When("Admin sends HTTPS Request and request Body with contact number greater than ten numbers")
	public void admin_sends_https_request_and_request_body_with_contact_number_greater_than_ten_numbers() {
		createRequestBody("invalid contact number >10");
	}
	
	@When("Admin sends HTTPS Request and request Body with zipcode as alphanumeric")
	public void admin_sends_https_request_and_request_body_with_zipcode_as_alphanumeric() {
		createRequestBody("invalid zipcode as alphanumeric");
	}
	
	@When("Admin sends HTTPS Request and request Body with Street as numeric")
	public void admin_sends_https_request_and_request_body_with_street_as_numeric() {
		createRequestBody("invalid street as numeric");
	}
	
	

}
