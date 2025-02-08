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

public class updateUserStepDefinition {

	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;

	@Given("Admin set the PUT request with the valid request body and valid Endpoint")
	public void admin_set_the_put_request_with_the_valid_request_body_and_valid_endpoint() {
		request = RestAssured.given().auth()
				.basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
				.header("Accept", "application/json").header("Content-Type", "application/json");
	}

	@When("Admin sends HTTPS Request with request Body")
	public void admin_sends_https_request_with_request_body() {
		testData = JsonDataReader.getScenarioData("valid update user");
		if (testData == null) {
			throw new IllegalStateException("Scenario data for 'valid update user' not found in JSON.");
		}
		RandomGenerator generator = new RandomGenerator();
		String randomContactNumber = generator.generateRandomContactNumber();
		String randomEmail = generator.generateRandomEmail();
		// Replace placeholders in test data
		String userContactNumber = testData.get("user_contact_number").toString()
				.replace("{{randomUpdateContactNumber}}", randomContactNumber);
		String userEmail = testData.get("user_email_id").toString().replace("{{randomUpdateEmail}}", randomEmail);
		// Prepare request body with POJO class and set values from JSON data
		CreateUser updateUser = new CreateUser();
		updateUser.setUser_first_name(testData.get("user_first_name").toString());
		updateUser.setUser_last_name(testData.get("user_last_name").toString());
		updateUser.setUser_contact_number(userContactNumber);
		updateUser.setUser_email_id(userEmail);
		CreateUser.UserAddress userAddress = new CreateUser.UserAddress();
		userAddress.setPlotNumber(testData.get("plotNumber").toString());
		userAddress.setStreet(testData.get("street").toString());
		userAddress.setState(testData.get("state").toString());
		userAddress.setCountry(testData.get("country").toString());
		userAddress.setZipCode(testData.get("zipCode").toString());
		updateUser.setUserAddress(userAddress);
		// Convert the POJO to JSON string using ObjectMapper
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonRequestBody = objectMapper.writeValueAsString(updateUser);
			// Log the request body as JSON
			System.out.println("Request Body: " + jsonRequestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.body(updateUser).log().all();
		// Log the request body before sending the request
		System.out.println("Request Body: " + updateUser.toString());
		String userId = TestDataStore.getUserId();
		System.out.println("Retrieved User ID: " + userId);
		// Get the endpoint from test data and send the PUT request
		String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
		response = request.when().put(endpoint);
		// Log the response for debugging
		System.out.println("Response Body: " + response.getBody().asPrettyString());
		TestDataStore.setUserFirstName(response.jsonPath().getString("user_first_name"));
		System.out.println("Stored User First Name: " + TestDataStore.getUserFirstName());

	}

	@Then("Admin receives {string} {string} Status for update user")
	public void admin_receives_status_for_update_user(String statusCode, String statusText) {
		System.out.println("Response Body: " + response.getBody().asPrettyString());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
	}

}
