package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "hooks"},  // Ensure hooks are included
        plugin = {"pretty", "json:target/cucumber.json", "html:target/cucumber-reports.html"},
        monochrome = true
)
public class testRunner extends AbstractTestNGCucumberTests {

	
}