package utils;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.Map;

import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.CreateUser;

public class apiHelper {

	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;
	private String userContactNumber;
	private String userEmail;
	private String userContactNumberUpdate;
	private String userEmailUpdate;
	private String userId_1;

	public void setResponse(Response response) {
		this.response = response;
	}

	public RequestSpecification validAuth() {
		request = RestAssured.given().auth()
				.basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
				.header("Accept", "application/json").header("Content-Type", "application/json");
		return request;
	}

	public RequestSpecification invalidAuth() {
		return request = RestAssured.given().auth().basic("numpy@gmail.com", "invalid")
				.header("Accept", "application/json").header("Content-Type", "application/json");

	}

	public RequestSpecification noAuth() {
		return request = RestAssured.given().auth().none().header("Accept", "application/json").header("Content-Type",
				"application/json");

	}

	public Response createRequestBody(String scenario) {
		testData = JsonDataReader.getScenarioData(scenario);
		if (testData == null) {
			throw new RuntimeException("Test data is null for scenario: " + scenario);
		}
		RandomGenerator generator = new RandomGenerator();
		String randomContactNumber = generator.generateRandomContactNumber();
		String randomEmail = generator.generateRandomEmail();
		// Replace placeholders in test data
		userContactNumber = testData.get("user_contact_number").toString().replace("{{randomContactNumber}}",
				randomContactNumber);
		userEmail = testData.get("user_email_id").toString().replace("{{randomEmail}}", randomEmail);
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
			String requestBody = objectMapper.writeValueAsString(createUser);
			System.out.println("Request Body JSON: " + requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Set the request body for POST request
		request.body(createUser).log().all();
		// Get the endpoint from test data and send the POST request
		String endpoint = testData.get("endpoint").toString();
		return response = request.when().post(endpoint);

	}

	public Response updateRequestBody(String scenario) {
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
			String requestBody = objectMapper.writeValueAsString(updateUser);
			System.out.println("Request Body JSON: " + requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.body(updateUser).log().all();
		String userId = TestDataStore.getUserId();
		System.out.println("Retrieved User ID: " + userId);
		// Get the endpoint from test data and send the PUT request
		String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
		return response = request.when().put(endpoint);
	}

	public void testDataValidations() {
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

	public void testDataValidationsUpdate() {
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

		System.out.println("All data validation checks passed successfully!");
	}

	public void statusCodeValidation(String statusCode) {

		if (response != null) {
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode), "Status code mismatch");
		} else {
			System.out.println("Error: Response is null. Unable to validate status code.");
			Assert.fail("Response is null! The request did not succeed.");
		}
	}

	public void schemaValidation() {
		response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/userSchema.json"));
		System.out.println("Schema Validation Passed!");

	}

}
