package utils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataReader {

	  private static final String JSON_PATH = ConfigReader.getProperty("JSON_Path");

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
	
}
