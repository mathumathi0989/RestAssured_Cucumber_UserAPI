package hooks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import utils.ConfigReader;

public class hooks {
	   private static final Logger logger = LogManager.getLogger(hooks.class);
	   
	    private static PrintStream logStream;
	    @Before
	    public void setup() {
	        RestAssured.baseURI = ConfigReader.getProperty("baseURI");
	        logger.info("Base URI set to: " + RestAssured.baseURI);
	        try {
	            // Create a file for logging API requests & responses
	            File logFile = new File("logs/api_logs.txt");
	            logFile.getParentFile().mkdirs(); // Ensure directory exists

	            // Open PrintStream to write logs
	            logStream = new PrintStream(new FileOutputStream(logFile, true)); // Append mode

	            // Attach RestAssured filters for logging requests & responses
	            RestAssured.filters(new RequestLoggingFilter(logStream), new ResponseLoggingFilter(logStream));

	        } catch (IOException e) {
	            e.printStackTrace();
	        }      
	    }

	    
	    
	    @After
	    public void tearDown() {
	        logger.info("Test execution completed.");
	        if (logStream != null) {
	            logStream.close(); // Close the log file after tests
	        }

	    }
}
