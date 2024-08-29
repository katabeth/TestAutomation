package com.sparta.kch.webtestframework.stepdefs;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/Login.feature",
        plugin = {"pretty", "html:target/cucumber-report.html", "json:target/cucumber.json"},
        publish = true,
        tags = "@Happy"
)
        // Glue not needed as test runner is in the same file as StepDefs already
public class TestRunner {
}
