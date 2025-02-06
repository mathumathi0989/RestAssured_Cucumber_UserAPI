package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/test/resources/features/getAllUsers.feature",
glue = {"stepDefinitions"},
plugin = {"pretty", "html:target/cucumber-Reports.html" , "json:target/cucumber.json"},
publish = true)
public class testRunner extends AbstractTestNGCucumberTests {

	
}