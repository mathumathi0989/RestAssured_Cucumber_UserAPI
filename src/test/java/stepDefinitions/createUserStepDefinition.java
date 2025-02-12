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
import utils.apiHelper;

public class createUserStepDefinition {

	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;
	private String userContactNumber;
	private String userEmail;
	private String userId_1;

	apiHelper common = new apiHelper();

	@Given("Admin set the POST request with the valid request body")
	public void admin_set_the_post_request_with_the_valid_request_body_and_valid_endpoint() {
		request = common.validAuth();
	}

	@When("Admin sends HTTPS Request and request Body with endpoint")
	public Response admin_sends_https_request_and_request_body_with_endpoint() {
		response = common.createRequestBody("valid create user", "valid");
		// Store the User ID and User First Name in TestData
		TestDataStore.setUserId(response.jsonPath().getString("user_id"));
		System.out.println("Stored User ID in TestDataStore: " + TestDataStore.getUserId());
return response;
	}

	@Then("Admin receives {string} {string} Status for create user")
	public void admin_receives_status_for_create_user(String statusCode, String statusText) {
		common.setResponse(response);
		common.schemaValidation();
		common.validateStatusCode(statusCode, false);
		common.validateResponseData(false);
	}

	@Then("Admin receives {string} {string} Status for create user mandatory")
	public void admin_receives_status_for_create_user_mandatory(String statusCode, String statusText) {
		common.setResponse(response);
		common.validateStatusCode(statusCode, true);
	}

	@When("Admin sends HTTPS Request and request Body with mandatory")
	public Response admin_sends_https_request_and_request_body_with_mandatory() {
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
			objectMapper.writeValueAsString(createUser);
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
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with invalid endpoint")
	public Response admin_sends_https_request_and_request_body_with_invalid_endpoint() {
		response = common.createRequestBody("create user invalid endpoint", "valid");
		return response;
	}

	@Then("Admin receives {string} {string} Status for create")
	public void admin_receives_status_for_create(String statusCode, String statusText) {
		common.setResponse(response);
		common.validateStatusCode(statusCode, false);
	}
	
	@Then("Admin receives {string} {string} Status")
	public void admin_receives_status(String statusCode, String statusText) {
		common.setResponse(response);
		common.validateStatusCode(statusCode, true);
	}

	@Given("Admin set the POST request with the invalid contentType")
	public void admin_set_the_post_request_with_the_invalid_content_type() {
		request = RestAssured.given().auth()
				.basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
				.header("Accept", "application/json").header("Content-Type", "text/plain");

	}

	@When("Admin sends HTTPS Request and request Body with invalid content type")
	public Response admin_sends_https_request_and_request_body_with_invalid_content_type() {
		testData = JsonDataReader.getScenarioData("valid create user");
		request.body("Invalid request format").log().all();
		String endpoint = testData.get("endpoint").toString();
		response = request.when().post(endpoint);
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with invalid request type")
	public Response admin_sends_https_request_and_request_body_with_invalid_request_type() {
		testData = JsonDataReader.getScenarioData("valid create user");
		RandomGenerator generator = new RandomGenerator();
		String randomContactNumber = generator.generateRandomContactNumber();
		String randomEmail = generator.generateRandomEmail();
		String userContactNumber = testData.get("user_contact_number").toString().replace("{{randomContactNumber}}",
				randomContactNumber);
		String userEmail = testData.get("user_email_id").toString().replace("{{randomEmail}}", randomEmail);
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
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValueAsString(createUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.body(createUser).log().all();
		String endpoint = testData.get("endpoint").toString();
		response = request.when().get(endpoint);
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with same contact number")
	public Response admin_sends_https_request_and_request_body_with_same_contact_number() {
		testData = JsonDataReader.getScenarioData("invalid same contact number");
		RandomGenerator generator = new RandomGenerator();
		String randomEmail = generator.generateRandomEmail();
		String userEmail = testData.get("user_email_id").toString().replace("{{randomEmail}}", randomEmail);
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
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValueAsString(createUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.body(createUser).log().all();
		String endpoint = testData.get("endpoint").toString();
		response = request.when().post(endpoint);
		return response;
	}

	@Then("Admin receives {string} {string} Status with contact message")
	public void admin_receives_status_with_contact_message(String statusCode, String statusText) {
		common.setResponse(response);
		common.validateStatusCode(statusCode, true);
		String actualMessage = response.jsonPath().getString("message");
		String expectedMessage = "User already exist with same contact number";
		Assert.assertEquals(actualMessage, expectedMessage);
	}

	@When("Admin sends HTTPS Request and request Body with same email")
	public Response admin_sends_https_request_and_request_body_with_same_email() {
		testData = JsonDataReader.getScenarioData("invalid same email id");
		RandomGenerator generator = new RandomGenerator();
		String randomContactNumber = generator.generateRandomContactNumber();
		String userContactNumber = testData.get("user_contact_number").toString().replace("{{randomContactNumber}}",
				randomContactNumber);
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
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValueAsString(createUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.body(createUser).log().all();
		String endpoint = testData.get("endpoint").toString();
		response = request.when().post(endpoint);
		return response;
	}

	@Then("Admin receives {string} {string} Status with email message")
	public void admin_receives_status_with_email_message(String statusCode, String statusText) {
		common.setResponse(response);
		common.validateStatusCode(statusCode, true);
		String actualMessage = response.jsonPath().getString("message");
		String expectedMessage = "User already exist with same email id";
		Assert.assertEquals(actualMessage, expectedMessage);
	}

	@Given("Admin set the POST request with the valid request body with no Auth")
	public void admin_set_the_post_request_with_the_valid_request_body_with_no_auth() {
		request = common.noAuth();

	}

	@When("Admin sends HTTPS Request and request Body with no or invalid auth")
	public Response admin_sends_https_request_and_request_body_with_no_or_invalid_auth() {
		response = common.createRequestBody("no auth", "none");
		return response;
	}

	@Given("Admin set the POST request with the valid request body with invalid basic Auth")
	public void admin_set_the_post_request_with_the_valid_request_body_with_invalid_basic_auth() {
		request = common.invalidAuth();
	}

	@Given("Admin set the POST request")
	public void admin_set_the_post_request() {
		request = common.validAuth();
	}

	@When("Admin sends HTTPS Request and request Body with first name as numeric")
	public Response admin_sends_https_request_and_request_body_with_first_name_as_numeric() {
		response = common.createRequestBody("invalid firstName as numeric", "valid");
		return response;
	}

	@Then("Admin receives {string} {string} Status with error message")
	public void admin_receives_status_with_error_message(String statusCode, String statusText) {
		common.setResponse(response);
		common.validateStatusCode(statusCode, true);
	}

	@When("Admin sends HTTPS Request and request Body with first name as empty")
	public void admin_sends_https_request_and_request_body_with_first_name_as_empty() {
		common.createRequestBody("invalid firstName as empty", "valid");
		
	}

	@When("Admin sends HTTPS Request and request Body with State as numeric")
	public Response admin_sends_https_request_and_request_body_with_state_as_numeric() {
		response = common.createRequestBody("invalid state as numeric", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with State as empty")
	public Response admin_sends_https_request_and_request_body_with_state_as_empty() {
		response = common.createRequestBody("invalid state as empty", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with zipcode as empty")
	public Response admin_sends_https_request_and_request_body_with_zipcode_as_empty() {
		response = response = common.createRequestBody("invalid zipcode as empty", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with invalid email format")
	public Response admin_sends_https_request_and_request_body_with_invalid_email_format() {
		response = common.createRequestBody("invalid email", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with empty contact number")
	public Response admin_sends_https_request_and_request_body_with_empty_contact_number() {
		response = common.createRequestBody("invalid contact number as empty", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with last name as numeric")
	public Response admin_sends_https_request_and_request_body_with_last_name_as_numeric() {
		response = common.createRequestBody("invalid last name as numeric", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with Street as empty")
	public Response admin_sends_https_request_and_request_body_with_street_as_empty() {
		response = common.createRequestBody("invalid street as empty", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with invalid plot number")
	public void admin_sends_https_request_and_request_body_with_invalid_plot_number() {
		response = common.createRequestBody("invalid plot number", "valid");
	}

	@When("Admin sends HTTPS Request and request Body with contact number as alphanumeric")
	public Response admin_sends_https_request_and_request_body_with_contact_number_as_alphanumeric() {
		response = common.createRequestBody("invalid contact number as alphanumeric", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with empty plot number")
	public Response admin_sends_https_request_and_request_body_with_empty_plot_number() {
		response = common.createRequestBody("invalid plot number as empty", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with country as empty")
	public Response admin_sends_https_request_and_request_body_with_country_as_empty() {
		response = common.createRequestBody("invalid country as empty", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with contact number less than ten numbers")
	public Response admin_sends_https_request_and_request_body_with_contact_number_less_than_ten_numbers() {
		response = common.createRequestBody("invalid contact number <10", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with last name as empty")
	public Response admin_sends_https_request_and_request_body_with_last_name_as_empty() {
		response = common.createRequestBody("invalid lastName as empty", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with empty email")
	public Response admin_sends_https_request_and_request_body_with_empty_email() {
		response = common.createRequestBody("invalid email as empty", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with country as numeric")
	public Response admin_sends_https_request_and_request_body_with_country_as_numeric() {
		response = common.createRequestBody("invalid country as numeric", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with contact number greater than ten numbers")
	public Response admin_sends_https_request_and_request_body_with_contact_number_greater_than_ten_numbers() {
		response = common.createRequestBody("invalid contact number >10", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with zipcode as alphanumeric")
	public Response admin_sends_https_request_and_request_body_with_zipcode_as_alphanumeric() {
		response = common.createRequestBody("invalid zipcode as alphanumeric", "valid");
		return response;
	}

	@When("Admin sends HTTPS Request and request Body with Street as numeric")
	public Response admin_sends_https_request_and_request_body_with_street_as_numeric() {
		response = common.createRequestBody("invalid street as numeric", "valid");
		return response;
	}

}
