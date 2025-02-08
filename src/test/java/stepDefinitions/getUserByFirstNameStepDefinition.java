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

public class getUserByFirstNameStepDefinition {

	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;

	@Given("Admin set the GET request by user firstName")
	public void admin_set_the_get_request_by_user_first_name() {
		request = RestAssured.given().auth()
				.basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
				.header("Accept", "application/json");
	}

	@When("Admin sends the GET HTTP request by User FirstName")
	public void admin_sends_the_get_http_request_by_user_first_name() {

		testData = JsonDataReader.getScenarioData("valid userFirstName");
		String user_first_name = TestDataStore.getUserFirstName();
		System.out.println("Retrieved User FirstName: " + user_first_name);
		String endpoint = testData.get("endpoint").toString().replace("{{user_first_name}}", user_first_name);
		response = request.when().get(endpoint);
		System.out.println("GET Response Body: " + response.getBody().asPrettyString());
	}

	@Then("Admin receives the {string} {string} Status code for UserFirstName")
	public void admin_receives_the_status_code_for_user_first_name(String statusCode, String statusText) {
		System.out.println("Response Body: " + response.getBody().asPrettyString());
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
	}

	@When("Admin sends HTTPS Request with invalid userfirstName")
	public void admin_sends_https_request_with_invalid_userfirst_name() {
		testData = JsonDataReader.getScenarioData("invalid userfirstname");
		String endpoint = testData.get("endpoint").toString();
		response = request.when().get(endpoint);
		System.out.println("GET Response Body: " + response.getBody().asPrettyString());
	}

	@When("Admin sends HTTPS Request with invalid requestType by User firstName")
	public void admin_sends_https_request_with_invalid_request_type_by_user_first_name() {
		testData = JsonDataReader.getScenarioData("valid userFirstName");
		String user_first_name = TestDataStore.getUserFirstName();
		System.out.println("Retrieved User FirstName: " + user_first_name);
		String endpoint = testData.get("endpoint").toString().replace("{{user_first_name}}", user_first_name);
		response = request.when().post(endpoint);
		System.out.println("GET Response Body: " + response.getBody().asPrettyString());

	}

	@Given("Admin set the GET request by user firstName with invalid basic auth")
	public void admin_set_the_get_request_by_user_first_name_with_invalid_basic_auth() {
		request = RestAssured.given().auth().basic("numpy@gmail.com", "invalid").header("Accept", "application/json");
	}

	@Given("Admin set the GET request by user firstName with No Auth")
	public void admin_set_the_get_request_by_user_first_name_with_no_auth() {
		request = RestAssured.given().auth().none().header("Accept", "application/json");
	}

}
