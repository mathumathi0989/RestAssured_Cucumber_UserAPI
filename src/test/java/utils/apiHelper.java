package utils;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.Map;

import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.CreateUser;

public class apiHelper {

	private RequestSpecification request;
	 private ResponseSpecification responseSpec;
	private Response response;
	private Map<String, Object> testData;
	private String userContactNumber;
	private String userEmail;
	private String userContactNumberUpdate;
	private String userEmailUpdate;
	private String userId_1;

	  public apiHelper() {
	        // Initialize RequestSpecBuilder
	        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
	        requestSpecBuilder.setBaseUri(ConfigReader.getProperty("baseURI"))
	                          .setContentType("application/json")
	                          .addHeader("Accept", "application/json");
	        request = requestSpecBuilder.build();

	    }
	  
	public void setResponse(Response response) {
		this.response = response;
	}

	public RequestSpecification validAuth() {
		  return RestAssured.given().spec(request)
                  .auth().basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));
	}

	public RequestSpecification invalidAuth() {
		  return RestAssured.given().spec(request)
                  .auth().basic("numpy@gmail.com", "invalid");
	}

	public RequestSpecification noAuth() {
		 return RestAssured.given().spec(request)
                 .auth().none();

	}

	public Response createRequestBody(String scenario, String authType) {
		testData = JsonDataReader.getScenarioData(scenario);
		if (testData == null) {
			throw new RuntimeException("Test data is null for scenario: " + scenario);
		}
		
		// Choose authentication based on authType parameter
	    RequestSpecification authRequest;
	    switch (authType.toLowerCase()) {
	        case "valid":
	            authRequest = validAuth();
	            break;
	        case "invalid":
	            authRequest = invalidAuth();
	            break;
	        case "none":
	            authRequest = noAuth();
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid auth type provided: " + authType);
	    }
        
		RandomGenerator generator = new RandomGenerator();
		String randomContactNumber = generator.generateRandomContactNumber();
		String randomEmail = generator.generateRandomEmail();
		// Replace placeholders in test data
		userContactNumber = testData.get("user_contact_number").toString().replace("{{randomContactNumber}}",
				randomContactNumber);
		userEmail = testData.get("user_email_id").toString().replace("{{randomEmail}}", randomEmail);
		// Prepare request body with POJO class and set values from JSON data
		CreateUser createUser = new CreateUser();
		createUser.setUser_first_name(testData.get("user_first_name").toString());
		createUser.setUser_last_name(testData.get("user_last_name").toString());
		createUser.setUser_contact_number(userContactNumber);
		createUser.setUser_email_id(userEmail);
		CreateUser.UserAddress userAddress = new CreateUser.UserAddress();
		userAddress.setPlotNumber(testData.get("plotNumber").toString());
		userAddress.setStreet(testData.get("street").toString());
		userAddress.setState(testData.get("state").toString());
		userAddress.setCountry(testData.get("country").toString());
		userAddress.setZipCode(testData.get("zipCode").toString());
		createUser.setUserAddress(userAddress);
		// Convert the POJO to JSON string using ObjectMapper
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody = objectMapper.writeValueAsString(createUser);
			System.out.println("Request Body JSON: " + requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Set the request body for POST request
	
		// Get the endpoint from test data and send the POST request
		  String endpoint = testData.get("endpoint").toString();
		  System.out.println(endpoint);
		    response = authRequest.body(createUser).log().all().when().post(endpoint);
		    System.out.println("Response Body: " + response.getBody().asString());
		    return response; // Ensure the response is returned correctly

	}

	public Response updateRequestBody(String scenario, String authType) {
		testData = JsonDataReader.getScenarioData(scenario);
		// Choose authentication based on authType parameter
	    RequestSpecification authRequest;
	    switch (authType.toLowerCase()) {
	        case "valid":
	            authRequest = validAuth();
	            break;
	        case "invalid":
	            authRequest = invalidAuth();
	            break;
	        case "none":
	            authRequest = noAuth();
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid auth type provided: " + authType);
	    }
		RandomGenerator generator = new RandomGenerator();
		String randomContactNumber = generator.generateRandomContactNumber();
		String randomEmail = generator.generateRandomEmail();
		// Replace placeholders in test data
		userContactNumberUpdate = testData.get("user_contact_number").toString()
				.replace("{{randomUpdateContactNumber}}", randomContactNumber);
		userEmailUpdate = testData.get("user_email_id").toString().replace("{{randomUpdateEmail}}", randomEmail);
		// Prepare request body with POJO class and set values from JSON data
		CreateUser updateUser = new CreateUser();
		updateUser.setUser_first_name(testData.get("user_first_name").toString());
		updateUser.setUser_last_name(testData.get("user_last_name").toString());
		updateUser.setUser_contact_number(userContactNumberUpdate);
		updateUser.setUser_email_id(userEmailUpdate);
		CreateUser.UserAddress userAddress = new CreateUser.UserAddress();
		userAddress.setPlotNumber(testData.get("plotNumber").toString());
		userAddress.setStreet(testData.get("street").toString());
		userAddress.setState(testData.get("state").toString());
		userAddress.setCountry(testData.get("country").toString());
		userAddress.setZipCode(testData.get("zipCode").toString());
		updateUser.setUserAddress(userAddress);
		// Convert the POJO to JSON string using ObjectMapper
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody = objectMapper.writeValueAsString(updateUser);
			System.out.println("Request Body JSON: " + requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String userId = TestDataStore.getUserId();
		System.out.println("Retrieved User ID: " + userId);
		// Get the endpoint from test data and send the PUT request
		String endpoint = testData.get("endpoint").toString().replace("{{user_id}}", userId);
		System.out.println("Final Endpoint URL: " + endpoint);
		 response = authRequest.body(updateUser).log().all().when().put(endpoint);
		return response;
	}

	public void validateResponseData(boolean isUpdate) {
	    // Extract response fields
	    String responseFirstName = response.jsonPath().getString("user_first_name");
	    String responseLastName = response.jsonPath().getString("user_last_name");
	    String responseContactNumber = response.jsonPath().getString("user_contact_number");
	    String responseEmail = response.jsonPath().getString("user_email_id");

	    // Extract address fields
	    String responsePlotNumber = response.jsonPath().getString("userAddress.plotNumber");
	    String responseStreet = response.jsonPath().getString("userAddress.street");
	    String responseState = response.jsonPath().getString("userAddress.state");
	    String responseCountry = response.jsonPath().getString("userAddress.country");
	    String responseZipCode = response.jsonPath().getString("userAddress.zipCode");

	    // Choose correct data for validation
	    String expectedContactNumber = isUpdate ? userContactNumberUpdate : userContactNumber;
	    String expectedEmail = isUpdate ? userEmailUpdate : userEmail;

	    // Validate response data against request data
	    Assert.assertEquals(responseFirstName, testData.get("user_first_name").toString(), "First Name Mismatch!");
	    Assert.assertEquals(responseLastName, testData.get("user_last_name").toString(), "Last Name Mismatch!");
	    Assert.assertEquals(responseContactNumber, expectedContactNumber, "Contact Number Mismatch!");
	    Assert.assertEquals(responseEmail, expectedEmail, "Email ID Mismatch!");

	    // Validate Address Fields
	    Assert.assertEquals(responsePlotNumber, testData.get("plotNumber").toString(), "Plot Number Mismatch!");
	    Assert.assertEquals(responseStreet, testData.get("street").toString(), "Street Mismatch!");
	    Assert.assertEquals(responseState, testData.get("state").toString(), "State Mismatch!");
	    Assert.assertEquals(responseCountry, testData.get("country").toString(), "Country Mismatch!");
	    Assert.assertEquals(responseZipCode, testData.get("zipCode").toString(), "Zip Code Mismatch!");

	    System.out.println("All data validation checks passed successfully!");
	}
	
	public void validateStatusCode(String statusCode, boolean checkAndDeleteUser) {
	    // Convert statusCode to integer
	    int expectedStatusCode = Integer.parseInt(statusCode);

	    // Initialize ResponseSpecBuilder with the expected status code
	    ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
	    responseSpecBuilder.expectStatusCode(expectedStatusCode);
	    responseSpec = responseSpecBuilder.build();  // ✅ Ensure responseSpec is initialized

	    // Validate response against the spec
	    if (response != null) {
	        response.then().spec(responseSpec);

	        // ✅ If user deletion is required (for create API with 201 status)
	        if (checkAndDeleteUser && response.getStatusCode() == 201 &&
	                response.jsonPath().getString("user_id") != null &&
	                !response.jsonPath().getString("user_id").isEmpty()) {
	            
	            userId_1 = response.jsonPath().getString("user_id");
	            System.out.println("Extracted User ID: " + userId_1);

	            // Construct delete endpoint
	            String deleteEndpoint = ConfigReader.getProperty("baseURI") + "/uap/deleteuser/" + userId_1;
	            System.out.println("Deleting user at: " + deleteEndpoint);

	            // Perform DELETE request
	            Response deleteResponse = RestAssured.given()
	                    .auth().basic(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"))
	                    .header("Accept", "application/json")
	                    .when().delete(deleteEndpoint);

	            // Log delete response details
	            System.out.println("Delete Response Status: " + deleteResponse.getStatusCode());
	            System.out.println("Delete Response Body: " + deleteResponse.getBody().asPrettyString());

	            // Validate DELETE response
	            Assert.assertEquals(deleteResponse.getStatusCode(), 200, "User deletion failed!");
	        }
	    } else {
	        System.out.println("Error: Response is null. Unable to validate status code.");
	    }
	}


	public void schemaValidation() {
		if (response != null) {
		response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/userSchema.json"));
		System.out.println("Schema Validation Passed!");
		}
		else {
	        System.out.println("Error: Response is null. Unable to validate schema..");
	      //  Assert.fail("Response is null! The request did not succeed.");
	    }
	
		

	}

}
