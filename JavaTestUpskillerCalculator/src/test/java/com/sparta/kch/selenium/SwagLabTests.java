package com.sparta.kch.selenium;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.List;


public class SwagLabTests {
    private static final String DRIVER_LOCATION = "src/test/resources/geckodriver.exe";
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static GeckoDriverService service;
    private static WebDriver webDriver;

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless", "--start-maximized");
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
    public void setup(){
        webDriver = new RemoteWebDriver(service.getUrl(), getFirefoxOptions());
    }
    @AfterAll
    public static void afterAll() {
        if (service != null && service.isRunning()) {
            service.stop();
        }
    }
    @AfterEach
    public void afterEach(){
        webDriver.quit();
    }

    @Test
    public void checkWebDriver() {
        webDriver.get(BASE_URL);
        Assertions.assertEquals(BASE_URL, webDriver.getCurrentUrl());
        Assertions.assertTrue(webDriver.getTitle().contains("Swag Labs"));

    }
    @Test
    public void successfulLogin() {
        Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.id("user-name"));
        WebElement passwordField =webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();
        wait.until(d -> d.getCurrentUrl().contains("/inventory"));
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is(BASE_URL + "inventory.html"));
    }
    @Test
    @DisplayName("Given I am logged in, when I view inventory page, I should see the correct number of products")
    public void checkNumberOfProducts() {
        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.id("user-name"));
        WebElement passwordField =webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();
        List<WebElement> items = webDriver.findElements(By.className("inventory_item"));
        Assertions.assertEquals(6, items.size());
    }
    @Test
    @DisplayName("Print items from inventory into a file")
    public void checkInventoryPage() throws IOException {
        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.id("user-name"));
        WebElement passwordField =webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();
        List<WebElement> items = webDriver.findElements(By.className("inventory_item"));
        try (PrintWriter writer = new PrintWriter(new FileWriter("products.txt"))){
            for (WebElement item : items) {
                WebElement nameElement = item.findElement(By.className("inventory_item_name"));
                WebElement priceElement = item.findElement(By.className("inventory_item_price"));
                String itemInfo = nameElement.getText() + " " + priceElement.getText();
                writer.println(itemInfo);
                System.out.println(itemInfo);
            }
        }
        Assertions.assertEquals(6, items.size());
    }

}
