package com.opencart.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src/test/resources/features/OpenCartTests.feature",
    
    glue = "com.opencart.tests" 
)
public class TestRunner extends AbstractTestNGCucumberTests {

    // This data provider ensures parallel test execution
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}