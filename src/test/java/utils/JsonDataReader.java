package utils;

import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataReader {

	 private static final String FILE_PATH = "src/test/resources/Mathumathi_APIBootcamp_JsonData.json";
	    private JsonNode testData;
	    
	    
	    // Constructor to initialize and load the JSON data
	    public JsonDataReader() {     
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            testData = objectMapper.readTree(new File(FILE_PATH));
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to load JSON file", e);
	        }
	    }

	    /**
	     * Get the request data for a specific requestType and scenario.
	     * 
	     * @param requestType The name of the request (e.g., "GetAllUsers").
	     * @param scenario The scenario name (e.g., "valid all users").
	     * @return JsonNode containing the request data or null if not found.
	     */
	    public JsonNode getRequestData(String requestType, String endpoint) {
	    	// Iterate through the "requests" array to find the matching "requestType"
	        for (JsonNode requestNode : testData) {
	            JsonNode requests = requestNode.get("requests");
	            if (requests != null) {
	                // Iterate through the list of requests for the given "requestType"
	                for (JsonNode request : requests) {
	                    if (request.get("name").asText().equals(requestType)) {
	                        // Iterate through the "data" array to find the scenario with the matching endpoint
	                        for (JsonNode scenarioData : request.get("data")) {
	                            if (scenarioData.get("endpoint").asText().equals(endpoint)) {
	                                return scenarioData; // Return the matched scenario data
	                            }
	                        }
	                    }
	                }
	            }
	        }
	        return null; // Return null if no matching data is found
	    }

	    /**
	     * Get the expected status code for a given scenario and requestType.
	     * 
	     * @param requestType The name of the request (e.g., "GetAllUsers").
	     * @param scenario The scenario name (e.g., "valid all users").
	     * @return The expected status code or -1 if not found.
	     */
	    public int getExpectedStatusCode(String requestType, String scenario) {
	        JsonNode requestData = getRequestData(requestType, scenario);
	        if (requestData != null) {
	            return requestData.get("statusCode").asInt();
	        }
	        return -1; // Return -1 if no matching data is found
	    }

	    /**
	     * Get the expected status text for a given scenario and requestType.
	     * 
	     * @param requestType The name of the request (e.g., "GetAllUsers").
	     * @param scenario The scenario name (e.g., "valid all users").
	     * @return The expected status text or empty string if not found.
	     */
	    public String getExpectedStatusText(String requestType, String scenario) {
	        JsonNode requestData = getRequestData(requestType, scenario);
	        if (requestData != null) {
	            return requestData.get("statusText").asText();
	        }
	        return ""; // Return empty string if no matching data is found
	    }


}
