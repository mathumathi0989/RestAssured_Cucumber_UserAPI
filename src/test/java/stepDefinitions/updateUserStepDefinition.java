package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.TestDataStore;
import utils.apiHelper;

public class updateUserStepDefinition {

	private Response response;
	apiHelper common = new apiHelper();

	@Given("Admin set the PUT request with the valid request body and valid Endpoint")
	public void admin_set_the_put_request_with_the_valid_request_body_and_valid_endpoint() {
		common.validAuth();
	}

	@When("Admin sends HTTPS Request with request Body")
	public Response admin_sends_https_request_with_request_body() {
		response = common.updateRequestBody("valid update user", "valid");

		// Extract the first name from response and store it in TestDataStore
		String userFirstName = response.jsonPath().getString("user_first_name");
		TestDataStore.setUserFirstName(userFirstName);
		System.out.println("Stored User First Name: " + TestDataStore.getUserFirstName());

		return response;
	}

	@Then("Admin receives {string} {string} Status for update user")
	public void admin_receives_status_for_update_user(String statusCode, String statusText) {
		common.validateStatusCode(statusCode, true);
		common.schemaValidation();
		common.validateResponseData(true);
	}

	@Given("Admin set the PUT request with the valid request body with no Auth")
	public void admin_set_the_put_request_with_the_valid_request_body_with_no_auth() {
		common.noAuth();
	}

	@When("Admin sends HTTPS Request and update request Body with no or invalid auth")
	public Response admin_sends_https_request_and_update_request_body_with_no_or_invalid_auth() {
		response = common.updateRequestBody("valid update user", "invalid");
		return response;
	}

	@Then("Admin receives {string} {string} Status for update")
	public void admin_receives_status_for_update(String statusCode, String statusText) {
		common.validateStatusCode(statusCode, true);
	}

	@Given("Admin set the PUT request with the valid request body with invalid basic Auth")
	public void admin_set_the_put_request_with_the_valid_request_body_with_invalid_basic_auth() {
		common.invalidAuth();
	}

}
