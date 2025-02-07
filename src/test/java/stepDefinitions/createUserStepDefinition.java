package stepDefinitions;

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
	private Map<String, Object> testData;

	@Given("Admin set the POST request with the valid request body and valid Endpoint")
	public void admin_set_the_post_request_with_the_valid_request_body_and_valid_endpoint() {
		request = RestAssured.given().auth()
				.basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
				.header("Accept", "application/json").header("Content-Type", "application/json");

	}

	@When("Admin sends HTTPS Request and request Body with endpoint")
	public void admin_sends_https_request_and_request_body_with_endpoint() {
		testData = JsonDataReader.getScenarioData("valid create user");
		if (testData == null) {
			throw new IllegalStateException("Scenario data for 'valid create user' not found in JSON.");
		}

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

		// Log the response for debugging
		System.out.println("Response Body: " + response.getBody().asPrettyString());

		// Store the User ID and User First Name in TestData
		TestDataStore.setUserId(response.jsonPath().getString("user_id"));
		System.out.println("Stored User ID in TestDataStore: " + TestDataStore.getUserId());

		TestDataStore.setUserFirstName(response.jsonPath().getString("user_first_name"));
		System.out.println("Stored User First Name: " + TestDataStore.getUserFirstName());

	}

	@Then("Admin receives {string} {string} Status")
	public void admin_receives_status(String statusCode, String statusText) {
		System.out.println("Response Body: " + response.getBody().asPrettyString());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
	}

}
