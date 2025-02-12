package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "hooks"},  
        plugin = {"pretty", "json:target/cucumber.json", "html:target/cucumber-reports.html", 
        		"com.aventstack.chaintest.plugins.ChainTestCucumberListener:",
        		"rerun:target/rerun.txt"},
        monochrome = true //,    tags = "@doing"
)

public class testRunnerTest extends AbstractTestNGCucumberTests {

	
	
}