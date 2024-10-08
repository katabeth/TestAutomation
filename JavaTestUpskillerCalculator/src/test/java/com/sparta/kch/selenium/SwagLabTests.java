package com.sparta.kch.selenium;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.List;
import java.util.Set;


public class SwagLabTests {
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
        WebElement passwordField = webDriver.findElement(By.id("password"));
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
        WebElement passwordField = webDriver.findElement(By.id("password"));
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
        WebElement passwordField = webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();
        List<WebElement> items = webDriver.findElements(By.className("inventory_item"));
        try (PrintWriter writer = new PrintWriter(new FileWriter("products.txt"))) {
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

    @Test
    @DisplayName("Window handles")
    public void checkWindowHandles() {
        webDriver.get("https://news.ycombinator.com/");
        String originalTab = webDriver.getWindowHandle();
        System.out.println(originalTab);
        webDriver.findElement(By.linkText("new")).sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));

        Set<String> allTabs = webDriver.getWindowHandles();
        System.out.println(allTabs);

        for (String tab : allTabs) {
            if (!tab.equals(originalTab)) {
                webDriver.switchTo().window(tab);
                break;
            }
        }
    }

    // Test for valid email and invalid password - error has Epic sad face
    @Test
    @DisplayName("Test for valid email and invalid password - error has Epic sad face")
    public void checkForEpicSadFace() {
        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.id("user-name"));
        WebElement passwordField = webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("p");
        loginButton.click();
        MatcherAssert.assertThat(webDriver.findElement(By.className("error-message-container")).getText(), Matchers.containsString("Epic sadface: Username and password do not match any user in this service"));
    }
    // Create a test https://demoqa.com/droppable/
    // assert that "Dropped text appears"
    @Test
    @DisplayName("When dropping the item, Dropped text appears")
    public void checkDroppedText() {
        webDriver.get("https://demoqa.com/droppable/");
        Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement item = webDriver.findElement(By.id("draggable"));
        WebElement target = webDriver.findElement(By.id("droppable"));
        Actions actions = new Actions(webDriver);
        actions.dragAndDrop(item, target).build().perform();
        wait.until(d -> d.findElement(By.id("droppable")).getText().contains("Dropped"));
        Assertions.assertEquals("Dropped!", webDriver.findElement(By.id("droppable")).getText());
    }
    // Test other SwagLabs user journeys
    @Test
    @DisplayName("Test invalid userName but valid password - error has Epic sad fac")
    public void checkOtherSwagLabsUserJourneys() {
        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.id("user-name"));
        WebElement passwordField = webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();
        MatcherAssert.assertThat(webDriver.findElement(By.className("error-message-container")).getText(), Matchers.containsString("Epic sadface: Username and password do not match any user in this service"));
    }
    @Test
    @DisplayName("Opening the burger and navigating to about tab")
    public void checkAboutTab() {
        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.id("user-name"));
        WebElement passwordField = webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();
        webDriver.findElement(By.className("bm-burger-button")).click();
        webDriver.findElement(By.linkText("About")).click();
        Assertions.assertEquals("https://saucelabs.com/", webDriver.getCurrentUrl());
    }
    @Test
    @DisplayName("Opening the burger and navigating to logout")
    public void checkLogout() {
        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.id("user-name"));
        WebElement passwordField = webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();
        webDriver.findElement(By.className("bm-burger-button")).click();
        webDriver.findElement(By.linkText("Logout")).click();
        Assertions.assertEquals("https://www.saucedemo.com/", webDriver.getCurrentUrl());
    }
    @Test
    @DisplayName("Adding an item to cart increases cart by 1")
    public void checkCart() {
        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.id("user-name"));
        WebElement passwordField = webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();
        int before = 0;
        try {
            before = Integer.parseInt(webDriver.findElement(By.className("shopping_cart_badge")).getText());
        } catch (Exception ignored) {
        }
        webDriver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        int after = Integer.parseInt(webDriver.findElement(By.className("shopping_cart_badge")).getText());
        Assertions.assertEquals(1, after - before);
    }
}
