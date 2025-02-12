package stepDefinitions;

import java.util.Map;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.JsonDataReader;
import utils.apiHelper;

public class GetAllUsersStepDefinitions {
	private RequestSpecification request;
	private Response response;
	private Map<String, Object> testData;

	apiHelper common = new apiHelper();

	@Given("Admin set the GET request")
	public void admin_set_the_get_request() {
		request = common.validAuth();
	}

	@When("Admin sends HTTPS Request with endpoint")
	public Response admin_sends_https_request_with_endpoint() {
		testData = JsonDataReader.getScenarioData("valid all users");
		String endpoint = testData.get("endpoint").toString();
		response = request.when().get(endpoint);
		return response;

	}

	@Then("Admin receives {string} {string} Status Code")
	public void admin_receives_status_code(String statusCode, String statusText) {
		common.setResponse(response);
		common.validateStatusCode(statusCode, false);
	}

	@When("Admin sends HTTPS Request without endpoint")
	public Response admin_sends_https_request_without_endpoint() {
		response = request.when().get("");
		return response;
	}

	@When("Admin sends HTTPS Request with invalid endpoint")
	public Response admin_sends_https_request_with_invalid_endpoint() {
		testData = JsonDataReader.getScenarioData("invalid endpoint");
		String endpoint = testData.get("endpoint").toString();
		response = request.when().get(endpoint);
		return response;
	}

	@Given("Admin set the GET request without Auth")
	public void admin_set_the_get_request_without_auth() {
		request = common.noAuth();
	}

	@Given("Admin set the GET request with Invalid Basic Auth")
	public void admin_set_the_get_request_with_invalid_basic_auth() {
		request = common.invalidAuth();
	}

}
