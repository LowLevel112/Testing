package com.saucedemo.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.saucedemo.tests"},
        plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber-reports.json"}
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
}
