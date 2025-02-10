package stepDefinitions;

import java.util.Map;
import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.JsonDataReader;
import utils.TestDataStore;
import utils.apiHelper;

public class deleteUserStepDefinition {

	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;

	apiHelper common = new apiHelper();

	@Given("Admin set the DELETE request by user id")
	public void admin_set_the_delete_request_by_user_id() {
		request = common.validAuth();
	}

	@When("Admin sends the DELETE HTTP request by User ID")
	public Response admin_sends_the_delete_http_request_by_user_id() {
		testData = JsonDataReader.getScenarioData("valid delete by userid");
		String userId = TestDataStore.getUserId();
		System.out.println("Retrieved User ID: " + userId);
		String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
		response = request.when().delete(endpoint);
		return response;
	}

	@Then("Admin receives the {string} {string} Status code with message")
	public void admin_receives_the_status_code_with_message(String statusCode, String statusText) {
		common.setResponse(response);
		common.statusCodeValidation(statusCode);
		String actualMessage = response.jsonPath().getString("message");
		String expectedMessage = "User is deleted successfully";
		Assert.assertEquals(actualMessage, expectedMessage);
	}

	@Then("Admin receives the {string} {string} Status")
	public void admin_receives_the_status(String statusCode, String statusText) {
		common.setResponse(response);
		common.statusCodeValidation(statusCode);
	}

	@When("Admin sends DELETE HTTPS Request with already deleted userID")
	public Response admin_sends_delete_https_request_with_already_deleted_user_id() {
		testData = JsonDataReader.getScenarioData("delete by already deletedid");
		String userId = TestDataStore.getUserId();
		System.out.println("Retrieved User ID: " + userId);
		String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
		response = request.when().delete(endpoint);
		return response;
	}

	@When("Admin sends DELETE HTTPS Request with invalid userID")
	public Response admin_sends_delete_https_request_with_invalid_user_id() {
		testData = JsonDataReader.getScenarioData("delete by invalid userid");
		String userId = TestDataStore.getUserId();
		System.out.println("Retrieved User ID: " + userId);
		String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
		response = request.when().delete(endpoint);
		return response;
	}

	@When("Admin sends HTTPS Request with invalid requestType by userID")
	public Response admin_sends_https_request_with_invalid_request_type_by_user_id() {
		testData = JsonDataReader.getScenarioData("delete with invalid request type");
		String userId = TestDataStore.getUserId();
		System.out.println("Retrieved User ID: " + userId);
		String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
		response = request.when().post(endpoint);
		return response;
	}

	@Given("Admin set the DELETE request by user id with invalid basic auth")
	public void admin_set_the_delete_request_by_user_id_with_invalid_basic_auth() {
		request = common.invalidAuth();
	}

	@Given("Admin set the DELETE request by user id with No Auth")
	public void admin_set_the_delete_request_by_user_id_with_no_auth() {
		request = common.noAuth();
	}

}
