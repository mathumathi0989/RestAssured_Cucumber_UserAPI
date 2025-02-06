package stepDefinitions;

import org.testng.Assert;

import hooks.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;


public class GetAllUsersStepDefinitions {

	  private Response response;
	    private String requestType;  // Store request type for later execution
	    private String finalEndpoint; // Store final endpoint 
	    private RequestSpecification requestSpec;

	    @Given("Admin set the {string} with {string} and with {string}")
	    public void admin_sets_the_with_and_with(String reqType, String endpoint, String authType) {
	    	 // Ensure requestSpec is initialized
	        requestSpec = new RequestSpecBuilder().build();  // Initialize requestSpec safely

	        // Store request type and endpoint
	        this.requestType = reqType;
	        this.finalEndpoint = endpoint.equalsIgnoreCase("valid") ? "/uap/users" : "/uap/user";

	        // Set Base URI
	        requestSpec.baseUri(ConfigReader.getProperty("baseURI")); 

	        // Set Authentication Type (Basic Auth or Invalid)
	        if (authType.equalsIgnoreCase("Basic")) {
	            requestSpec = requestSpec.auth().preemptive().basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));
	        } else if (authType.equalsIgnoreCase("Invalid")) {
	            requestSpec = requestSpec.auth().preemptive().basic("invalidUser", "invalidPass");
	        }

	        // Set Base Path for the API endpoint
	        requestSpec.basePath(finalEndpoint);  
	    }

	    @When("Admin sends HTTPS Request")
	    public void admin_sends_https_request() {
	    	// Ensure requestSpec is not null and properly set
	        if (requestSpec == null) {
	            throw new IllegalStateException("RequestSpecification is not initialized properly");
	        }

	        // Make the API call based on requestType
	        switch (requestType.toUpperCase()) {
	            case "GET":
	                response = requestSpec.get();
	                break;
	            case "POST":
	                response = requestSpec.post();
	                break;
	            default:
	                throw new IllegalArgumentException("Unsupported HTTP method: " + requestType);
	        }

	        // Log the response for debugging
	        System.out.println("Response: " + response.prettyPrint());
	    }

	    @Then("Admin receives {int} and {string}")
	    public void admin_receives_status_code_and_status_text(int expectedStatusCode, String expectedStatusText) {
	        // Assert the status code
	        Assert.assertEquals(response.statusCode(), expectedStatusCode, "Status code mismatch!");

	        // Assert the status text
	        Assert.assertTrue(response.statusLine().contains(expectedStatusText), "Status text mismatch!");
	    }
}
	
	

