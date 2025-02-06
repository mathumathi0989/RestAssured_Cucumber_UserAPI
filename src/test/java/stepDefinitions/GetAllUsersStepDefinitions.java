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

	    @Given("Admin set the GET request with valid Endpoint")
	    public void admin_set_the_get_request_with_valid_endpoint() {
	        testData = JsonDataReader.getScenarioData("valid all users"); // Get scenario data from JSON
	        if (testData == null) {
	            throw new IllegalStateException("Scenario data for 'valid all users' not found in JSON.");
	        }
	        request = RestAssured.given()
	                .auth().basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
	                .header("Accept", "application/json")
	                .log().all();

	        System.out.println("Inside: Admin set the GET request");
	    }

	    @When("Admin sends HTTPS Request with endpoint")
	    public void admin_sends_https_request_with_endpoint() {
	        String endpoint = testData.get("endpoint").toString();
	        response = request.when().get(endpoint);

	        // Log response
	        System.out.println("Requesting URL: " + RestAssured.baseURI + endpoint);
	        System.out.println("Response Status: " + response.getStatusCode());
	        System.out.println("Response Body: " + response.getBody().asPrettyString());
	    }

	    @Then("Admin receives {string} {string} Status Code and should display all the users in response body.")
	    public void admin_receives_status_code_and_should_display_all_the_users_in_response_body(String statusCode, String statusText) {
	    	   System.out.println("Response Body: " + response.getBody().asPrettyString());
	    	   // Log Response
		        System.out.println("Response Status: " + response.getStatusCode() + " " + response.getStatusLine());
		     
	        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
	     // Assert that the status line contains the expected status text (like "OK")
	     //   Assert.assertTrue(response.getStatusLine().contains(statusText), 
	                      //    "Expected status line to contain: " + statusText + ", but found: " + response.getStatusLine());

	    }
}


	

