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

public class getUserByFirstNameStepDefinition {

	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;

	apiHelper common = new apiHelper();

	@Given("Admin set the GET request by user firstName")
	public void admin_set_the_get_request_by_user_first_name() {
		request = common.validAuth();
	}

	@When("Admin sends the GET HTTP request by User FirstName")
	public Response admin_sends_the_get_http_request_by_user_first_name() {
		testData = JsonDataReader.getScenarioData("valid userFirstName");
		String user_first_name = TestDataStore.getUserFirstName();
		System.out.println("Retrieved User FirstName: " + user_first_name);
		String endpoint = testData.get("endpoint").toString().replace("{{user_first_name}}", user_first_name);
		response = request.when().get(endpoint);
		return response;
	}

	@Then("Admin receives the {string} {string} Status code for UserFirstName")
	public void admin_receives_the_status_code_for_user_first_name(String statusCode, String statusText) {
		common.setResponse(response);
		common.validateStatusCode(statusCode, true);
	}

	@When("Admin sends HTTPS Request with invalid userfirstName")
	public Response admin_sends_https_request_with_invalid_userfirst_name() {
		testData = JsonDataReader.getScenarioData("invalid userfirstname");
		String endpoint = testData.get("endpoint").toString();
		response = request.when().get(endpoint);
		return response;
	}

	@When("Admin sends HTTPS Request with invalid requestType by User firstName")
	public Response admin_sends_https_request_with_invalid_request_type_by_user_first_name() {
		testData = JsonDataReader.getScenarioData("valid userFirstName");
		String user_first_name = TestDataStore.getUserFirstName();
		System.out.println("Retrieved User FirstName: " + user_first_name);
		String endpoint = testData.get("endpoint").toString().replace("{{user_first_name}}", user_first_name);
		response = request.when().post(endpoint);
		return response;

	}

	@Given("Admin set the GET request by user firstName with invalid basic auth")
	public void admin_set_the_get_request_by_user_first_name_with_invalid_basic_auth() {
		request = common.invalidAuth();
	}

	@Given("Admin set the GET request by user firstName with No Auth")
	public void admin_set_the_get_request_by_user_first_name_with_no_auth() {
		request = common.noAuth();
	}

}
