package stepDefinitions;

import java.util.Map;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;
import utils.JsonDataReader;
import utils.TestDataStore;

public class getUserByUserIDStepDefinitions {

	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;
	

	@Given("Admin set the GET request by user id")
	public void admin_set_the_get_request_by_user_id() {
		request = RestAssured.given().auth()
				.basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
				.header("Accept", "application/json");
	}

	@When("Admin sends the GET HTTP request by User ID")
	public void admin_sends_the_get_http_request_by_user_id() {
		testData = JsonDataReader.getScenarioData("valid userid");
		if (testData == null) {
			throw new IllegalStateException("Scenario data for 'valid userid' not found in JSON.");
		}
		String userId = TestDataStore.getUserId();
		System.out.println("Retrieved User ID: " + userId);
		String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
		response = request.when().get(endpoint);
		System.out.println("GET Response Body: " + response.getBody().asPrettyString());
	}

	@Then("Admin receives the {string} {string} Status code with valid user details")
	public void admin_receives_the_status_code_with_valid_user_details(String statusCode, String statusText) {
		System.out.println("Response Body: " + response.getBody().asPrettyString());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
	}
	
	
	@When("Admin sends HTTPS Request with invalid userID")
	public void admin_sends_https_request_with_invalid_user_id() {
		testData = JsonDataReader.getScenarioData("invalid userid");
		String endpoint = testData.get("endpoint").toString();
		response = request.when().get(endpoint);
		System.out.println("GET Response Body: " + response.getBody().asPrettyString());
		
	}

	
	@When("Admin sends HTTPS Request with invalid requestType")
	public void admin_sends_https_request_with_invalid_request_type() {
		testData = JsonDataReader.getScenarioData("invalid request type");
		String userId = TestDataStore.getUserId();
		System.out.println("Retrieved User ID: " + userId);
		String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
		response = request.when().post(endpoint);
		System.out.println("GET Response Body: " + response.getBody().asPrettyString());
		
	}
	
	
	@Given("Admin set the GET request by user id with No Auth")
	public void admin_set_the_get_request_by_user_id_with_no_auth() {
		request = RestAssured.given().auth().none()
				.header("Accept", "application/json");
	}
	
	
	@Given("Admin set the GET request by user id with invalid basic auth")
	public void admin_set_the_get_request_by_user_id_with_invalid_basic_auth() {
		request = RestAssured.given().auth()
				.basic("numpy@gmail.com","invalid")
				.header("Accept", "application/json");
		
	}
	

}
