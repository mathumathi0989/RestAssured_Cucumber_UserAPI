package utils;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIHelper {

	public static Response sendRequest(String method, String endpoint, Map<String, String> headers, String body) {
        switch (method.toUpperCase()) {
            case "GET":
                return RestAssured.given().headers(headers).when().get(endpoint);
            case "POST":
                return RestAssured.given().headers(headers).body(body).when().post(endpoint);
            case "PUT":
                return RestAssured.given().headers(headers).body(body).when().put(endpoint);
            case "DELETE":
                return RestAssured.given().headers(headers).when().delete(endpoint);
            default:
                throw new IllegalArgumentException("Invalid request method");
        }
    }
	
}
