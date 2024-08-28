package com.sparta.kch.webtestframework.tests;

import com.sparta.kch.webtestframework.pages.Website;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestSetup {

    private static final String DRIVER_LOCATION = "src/test/resources/geckodriver.exe";
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static GeckoDriverService service;
    private static WebDriver webDriver;

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        //options.addArguments("--headless");
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        options.setImplicitWaitTimeout(Duration.ofSeconds(10));
        return options;
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        service = new GeckoDriverService.Builder()
                .usingDriverExecutable(new File(DRIVER_LOCATION))
                .usingAnyFreePort()
                .build();
        service.start();
    }
    @BeforeEach
    public void setup() {
        webDriver = new RemoteWebDriver(service.getUrl(), getFirefoxOptions());
    }
    @AfterAll
    public static void afterAll() {
        if (service != null && service.isRunning()) {
            service.stop();
        }
    }
    @AfterEach
    public void afterEach() {
        webDriver.quit();
    }

    public Website getWebsite(String url) {
        webDriver.get(url);
        return new Website(webDriver);
    }
}
