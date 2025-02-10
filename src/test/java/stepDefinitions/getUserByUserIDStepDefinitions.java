package stepDefinitions;

import java.util.Map;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.JsonDataReader;
import utils.TestDataStore;
import utils.apiHelper;

public class getUserByUserIDStepDefinitions {

	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;

	apiHelper common = new apiHelper();

	@Given("Admin set the GET request by user id")
	public void admin_set_the_get_request_by_user_id() {
		request = common.validAuth();
	}

	@When("Admin sends the GET HTTP request by User ID")
	public Response admin_sends_the_get_http_request_by_user_id() {
		testData = JsonDataReader.getScenarioData("valid userid");
		String userId = TestDataStore.getUserId();
		System.out.println("Retrieved User ID: " + userId);
		String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
		response = request.when().get(endpoint);
		return response;
	}

	@Then("Admin receives the {string} {string} Status code with valid user details")
	public void admin_receives_the_status_code_with_valid_user_details(String statusCode, String statusText) {
		common.setResponse(response);
		common.statusCodeValidation(statusCode);
	}

	@When("Admin sends HTTPS Request with invalid userID")
	public Response admin_sends_https_request_with_invalid_user_id() {
		testData = JsonDataReader.getScenarioData("invalid userid");
		String endpoint = testData.get("endpoint").toString();
		response = request.when().get(endpoint);
		return response;
	}

	@When("Admin sends HTTPS Request with invalid requestType")
	public Response admin_sends_https_request_with_invalid_request_type() {
		testData = JsonDataReader.getScenarioData("invalid request type");
		String userId = TestDataStore.getUserId();
		System.out.println("Retrieved User ID: " + userId);
		String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
		response = request.when().post(endpoint);
		return response;
	}

	@Given("Admin set the GET request by user id with No Auth")
	public void admin_set_the_get_request_by_user_id_with_no_auth() {
		request = common.noAuth();
	}

	@Given("Admin set the GET request by user id with invalid basic auth")
	public void admin_set_the_get_request_by_user_id_with_invalid_basic_auth() {
		request = common.invalidAuth();
	}

}
