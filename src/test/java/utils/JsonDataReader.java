package utils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataReader {

	// private static final String FILE_PATH = ConfigReader.getProperty("JSON_Path");
	
	  //  private static JsonNode testData;
	    
	  private static final String JSON_PATH = "src/test/resources/Mathumathi_APIBootcamp_JsonData.json";

	  public static Map<String, Object> getScenarioData(String scenarioName) {
		    try {
		        ObjectMapper objectMapper = new ObjectMapper();
		        // Read the root as an array
		        JsonNode rootNode = objectMapper.readTree(new File(JSON_PATH));
		        
		        // Assuming there's only one element in the root array
		        JsonNode requests = rootNode.get(0).get("requests");

		        // Loop through the "requests" array
		        for (JsonNode request : requests) {
		            JsonNode dataList = request.get("data");
		            
		            // Loop through the "data" array to find the matching scenario
		            for (JsonNode data : dataList) {
		                if (data.get("scenario").asText().equals(scenarioName)) {
		                    Map<String, Object> scenarioData = new HashMap<>();
		                    Iterator<String> fieldNames = data.fieldNames();
		                    while (fieldNames.hasNext()) {
		                        String fieldName = fieldNames.next();
		                        scenarioData.put(fieldName, data.get(fieldName).asText());
		                    }
		                    return scenarioData;
		                }
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return null;
	    }
//	    // Static block to initialize JSON data when the class loads
//	    static {
//	        try {
//	        	 System.out.println("Loading JSON from: " + FILE_PATH);
//	            ObjectMapper objectMapper = new ObjectMapper();
//	            testData = objectMapper.readTree(new File(FILE_PATH));
//	            if (testData == null) {
//	                throw new RuntimeException("JSON file is empty or not properly formatted.");
//	            }
//	        } catch (IOException e) {
//	            throw new RuntimeException("Failed to load JSON file: " + FILE_PATH, e);
//	        }
//	    }
//
//	    public static String getFieldValue(String requestType, String scenario, String fieldName) {
//	        if (testData == null) {
//	            throw new RuntimeException("JSON data is not loaded.");
//	        }
//
//	        for (JsonNode requestNode : testData) {
//	            JsonNode requests = requestNode.get("requests");
//	            if (requests != null) {
//	                for (JsonNode request : requests) {
//	                    if (request.get("name").asText().equals(requestType)) {
//	                        for (JsonNode scenarioData : request.get("data")) {
//	                            if (scenarioData.get("scenario").asText().equals(scenario)) {
//	                                JsonNode fieldValue = scenarioData.get(fieldName);
//	                                return fieldValue != null ? fieldValue.asText() : "Field not found";
//	                            }
//	                        }
//	                    }
//	                }
//	            }
//	        }
//	        return "Scenario or requestType not found";
//	    }

}
