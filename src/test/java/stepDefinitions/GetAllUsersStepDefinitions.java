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

public class GetAllUsersStepDefinitions {
	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;

	@Given("Admin set the GET request")
	public void admin_set_the_get_request() {
		request = RestAssured.given().auth()
				.basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
				.header("Accept", "application/json");
	}

	@When("Admin sends HTTPS Request with endpoint")
	public void admin_sends_https_request_with_endpoint() {
		testData = JsonDataReader.getScenarioData("valid all users");
		if (testData == null) {
			throw new IllegalStateException("Scenario data for 'valid all users' not found in JSON.");
		}
		String endpoint = testData.get("endpoint").toString();
		response = request.when().get(endpoint);

	}

	@Then("Admin receives {string} {string} Status Code")
	public void admin_receives_status_code(String statusCode, String statusText) {
		System.out.println("Response Body: " + response.getBody().asPrettyString());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));

	}

	@When("Admin sends HTTPS Request without endpoint")
	public void admin_sends_https_request_without_endpoint() {
		String endpoint = "";
		response = request.when().get(endpoint);

	}

	@When("Admin sends HTTPS Request with invalid endpoint")
	public void admin_sends_https_request_with_invalid_endpoint() {
		testData = JsonDataReader.getScenarioData("invalid endpoint"); // Get scenario data from JSON
		if (testData == null) {
			throw new IllegalStateException("Scenario data for 'valid all users' not found in JSON.");
		}
		String endpoint = testData.get("endpoint").toString();
		response = request.when().get(endpoint);

	}

	@Given("Admin set the GET request without Auth")
	public void admin_set_the_get_request_without_auth() {
		request = RestAssured.given().auth().basic("", "").header("Accept", "application/json");
	}

	@Given("Admin set the GET request with Invalid Basic Auth")
	public void admin_set_the_get_request_with_invalid_basic_auth() {
		request = RestAssured.given().auth().basic("numpy@gmail.com", "sdet").header("Accept", "application/json");
	}

}
