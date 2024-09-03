package com.sparta.kch.webtestframework.stepdefs;

import com.sparta.kch.webtestframework.pages.Website;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestSetup {

    private static final String DRIVER_LOCATION = "src/test/resources/geckodriver.exe";
    private static GeckoDriverService service;
    private static WebDriver webDriver;

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        options.setImplicitWaitTimeout(Duration.ofSeconds(10));
        return options;
    }


    public static void startGeckoDriver() throws IOException {
        service = new GeckoDriverService.Builder()
                .usingDriverExecutable(new File(DRIVER_LOCATION))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    public static void createWebDriver() {
        webDriver = new RemoteWebDriver(service.getUrl(), getFirefoxOptions());
    }

    public static void stopService() {
        if (service != null && service.isRunning()) {
            service.stop();
        }
    }

    public static void quitWebDriver() {
        webDriver.quit();
    }

    public static Website getWebsite(String url) {
        webDriver.get(url);
        return new Website(webDriver);
    }
}
