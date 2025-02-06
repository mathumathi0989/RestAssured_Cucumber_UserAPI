package hooks;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ConfigReader;

public class hooks {
	   private static final Logger logger = LogManager.getLogger(hooks.class);
	    private static FileWriter logFileWriter;

	    @Before
	    public void setup() {
	        RestAssured.baseURI = ConfigReader.getProperty("baseURI");
	        logger.info("Base URI set to: " + RestAssured.baseURI);
	        try {
	            logFileWriter = new FileWriter("src/test/resources/logs/api.log", true);
	            logFileWriter.write("=== Inside Hooks @Before: Initializing RestAssured ===\n");
	            logFileWriter.write("Base URI: " + RestAssured.baseURI + "\n");
	            logFileWriter.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    @After
	    public void tearDown() {
	        logger.info("Test execution completed.");
	        try {
	            if (logFileWriter != null) {
	                logFileWriter.write("=== Inside Hooks @After: Test Execution Completed ===\n");
	                logFileWriter.flush();
	                logFileWriter.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
