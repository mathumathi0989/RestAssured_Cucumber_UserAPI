package hooks;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;

public class Hooks {
	  
	 public static RequestSpecification request;

	    @Before
	    public void setUp() {
	        RestAssured.baseURI = ConfigReader.getProperty("baseURI"); // Read from config.properties

	        request = new RequestSpecBuilder()
	                .setBaseUri(RestAssured.baseURI)
	                .setContentType(ContentType.JSON)
	                .log(LogDetail.ALL) // Logs request details for debugging
	                .build();
	    }

}
