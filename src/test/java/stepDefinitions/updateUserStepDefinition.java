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

public class updateUserStepDefinition {

	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;
	private String userContactNumberUpdate;
	private String userEmailUpdate;
	
	@Given("Admin set the PUT request with the valid request body and valid Endpoint")
	public void admin_set_the_put_request_with_the_valid_request_body_and_valid_endpoint() {
		request = RestAssured.given().auth()
				.basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
				.header("Accept", "application/json").header("Content-Type", "application/json");
	}
	
	public void updateRequestBody(String scenario) {
		testData = JsonDataReader.getScenarioData(scenario);
		RandomGenerator generator = new RandomGenerator();
		String randomContactNumber = generator.generateRandomContactNumber();
		String randomEmail = generator.generateRandomEmail();
		// Replace placeholders in test data
		userContactNumberUpdate = testData.get("user_contact_number").toString()
				.replace("{{randomUpdateContactNumber}}", randomContactNumber);
		userEmailUpdate = testData.get("user_email_id").toString().replace("{{randomUpdateEmail}}", randomEmail);
		// Prepare request body with POJO class and set values from JSON data
		CreateUser updateUser = new CreateUser();
		updateUser.setUser_first_name(testData.get("user_first_name").toString());
		updateUser.setUser_last_name(testData.get("user_last_name").toString());
		updateUser.setUser_contact_number(userContactNumberUpdate);
		updateUser.setUser_email_id(userEmailUpdate);
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
	}

	@When("Admin sends HTTPS Request with request Body")
	public void admin_sends_https_request_with_request_body() {
		updateRequestBody("valid update user");
		TestDataStore.setUserFirstName(response.jsonPath().getString("user_first_name"));
		System.out.println("Stored User First Name: " + TestDataStore.getUserFirstName());
		 response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/userSchema.json"));
		    System.out.println("Schema Validation Passed for update user!");
	}

	@Then("Admin receives {string} {string} Status for update user")
	public void admin_receives_status_for_update_user(String statusCode, String statusText) {
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
	    Assert.assertEquals(responseContactNumber, userContactNumberUpdate, "Contact Number Mismatch!");
	    Assert.assertEquals(responseEmail, userEmailUpdate, "Email ID Mismatch!");
	    
	    // Validate Address Fields
	    Assert.assertEquals(responsePlotNumber, testData.get("plotNumber").toString(), "Plot Number Mismatch!");
	    Assert.assertEquals(responseStreet, testData.get("street").toString(), "Street Mismatch!");
	    Assert.assertEquals(responseState, testData.get("state").toString(), "State Mismatch!");
	    Assert.assertEquals(responseCountry, testData.get("country").toString(), "Country Mismatch!");
	    Assert.assertEquals(responseZipCode, testData.get("zipCode").toString(), "Zip Code Mismatch!");

	    System.out.println("All data validation checks passed successfully for update user!");
	}



@Given("Admin set the PUT request with the valid request body with no Auth")
public void admin_set_the_put_request_with_the_valid_request_body_with_no_auth() {
	request = RestAssured.given().auth().none()
			.header("Accept", "application/json").header("Content-Type", "application/json");
}
@When("Admin sends HTTPS Request and update request Body with no or invalid auth")
public void admin_sends_https_request_and_update_request_body_with_no_or_invalid_auth() {
	updateRequestBody("valid update user");
}
@Then("Admin receives {string} {string} Status for update")
public void admin_receives_status_for_update(String statusCode, String statusText) {
	System.out.println("Response Body: " + response.getBody().asPrettyString());
	Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
}

@Given("Admin set the PUT request with the valid request body with invalid basic Auth")
public void admin_set_the_put_request_with_the_valid_request_body_with_invalid_basic_auth() {
	request = RestAssured.given().auth()
			.basic("numpy@gmail.com", "invalid")
			.header("Accept", "application/json").header("Content-Type", "application/json");
}


}
