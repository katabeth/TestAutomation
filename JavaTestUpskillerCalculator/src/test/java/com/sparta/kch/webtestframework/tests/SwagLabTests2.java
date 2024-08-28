package com.sparta.kch.webtestframework.tests;

import com.sparta.kch.webtestframework.pages.Website;
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


public class SwagLabTests2 extends TestSetup{

    private static final String BASE_URL = "https://www.saucedemo.com/";
    private Website website;

    @Test
    public void successfulLogin() {
        website = getWebsite(BASE_URL);
        website.getHomePage().login("standard_user", "secret_sauce");
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", website.getCurrentUrl());
    }
    @Test
    @DisplayName("Test for valid email and invalid password - error has Epic sad face")
    public void checkForEpicSadFace() {
        website = getWebsite(BASE_URL);
        website.getHomePage().login("standard_user", "password");
        MatcherAssert.assertThat(website.getHomePage().getErrorMessage() , Matchers.containsString("Epic sadface: Username and password do not match any user in this service"));
    }
    @Test
    @DisplayName("Given I am logged in, when I view inventory page, I should see the correct number of products")
    public void checkNumberOfProducts() {
        website = getWebsite(BASE_URL);
        website.getHomePage().login("standard_user", "secret_sauce");

        Assertions.assertEquals(6, website.getInventoryPage().getInventoryItems().size());
    }

    @Test
    @DisplayName("Adding an item to cart increases cart by 1")
    public void checkCart() {
        website = getWebsite(BASE_URL);
        website.getHomePage().login("standard_user", "secret_sauce");
        int before = website.getInventoryPage().getShoppingCartBadge();
        website.getInventoryPage().addSauceLabsBackpackToCart();
        int after = website.getInventoryPage().getShoppingCartBadge();
        Assertions.assertEquals(1, after - before);
    }
    @Test
    @DisplayName("Adding a specific item to cart increases cart by 1")
    public void checkCart2() {
        website = getWebsite(BASE_URL);
        website.getHomePage().login("standard_user", "secret_sauce");
        int before = website.getInventoryPage().getShoppingCartBadge();
        website.getInventoryPage().addItemToCart(2);
        int after = website.getInventoryPage().getShoppingCartBadge();
        Assertions.assertEquals(1, after - before);
    }
//    @Test
//    @DisplayName("Print items from inventory into a file")
//    public void checkInventoryPage() throws IOException {
//        webDriver.get(BASE_URL);
//        WebElement usernameField = webDriver.findElement(By.id("user-name"));
//        WebElement passwordField = webDriver.findElement(By.id("password"));
//        WebElement loginButton = webDriver.findElement(By.id("login-button"));
//        usernameField.sendKeys("standard_user");
//        passwordField.sendKeys("secret_sauce");
//        loginButton.click();
//        List<WebElement> items = webDriver.findElements(By.className("inventory_item"));
//        try (PrintWriter writer = new PrintWriter(new FileWriter("products.txt"))) {
//            for (WebElement item : items) {
//                WebElement nameElement = item.findElement(By.className("inventory_item_name"));
//                WebElement priceElement = item.findElement(By.className("inventory_item_price"));
//                String itemInfo = nameElement.getText() + " " + priceElement.getText();
//                writer.println(itemInfo);
//                System.out.println(itemInfo);
//            }
//        }
//        Assertions.assertEquals(6, items.size());
//    }
//
//    @Test
//    @DisplayName("Window handles")
//    public void checkWindowHandles() {
//        webDriver.get("https://news.ycombinator.com/");
//        String originalTab = webDriver.getWindowHandle();
//        System.out.println(originalTab);
//        webDriver.findElement(By.linkText("new")).sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
//
//        Set<String> allTabs = webDriver.getWindowHandles();
//        System.out.println(allTabs);
//
//        for (String tab : allTabs) {
//            if (!tab.equals(originalTab)) {
//                webDriver.switchTo().window(tab);
//                break;
//            }
//        }
//    }
//
//    // Test for valid email and invalid password - error has Epic sad face
//    @Test
//    @DisplayName("Test for valid email and invalid password - error has Epic sad face")
//    public void checkForEpicSadFace() {
//        webDriver.get(BASE_URL);
//        WebElement usernameField = webDriver.findElement(By.id("user-name"));
//        WebElement passwordField = webDriver.findElement(By.id("password"));
//        WebElement loginButton = webDriver.findElement(By.id("login-button"));
//        usernameField.sendKeys("standard_user");
//        passwordField.sendKeys("p");
//        loginButton.click();
//        MatcherAssert.assertThat(webDriver.findElement(By.className("error-message-container")).getText(), Matchers.containsString("Epic sadface: Username and password do not match any user in this service"));
//    }
//    // Create a test https://demoqa.com/droppable/
//    // assert that "Dropped text appears"
//    @Test
//    @DisplayName("When dropping the item, Dropped text appears")
//    public void checkDroppedText() {
//        webDriver.get("https://demoqa.com/droppable/");
//        Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
//        WebElement item = webDriver.findElement(By.id("draggable"));
//        WebElement target = webDriver.findElement(By.id("droppable"));
//        Actions actions = new Actions(webDriver);
//        actions.dragAndDrop(item, target).build().perform();
//        wait.until(d -> d.findElement(By.id("droppable")).getText().contains("Dropped"));
//        Assertions.assertEquals("Dropped!", webDriver.findElement(By.id("droppable")).getText());
//    }
//    // Test other SwagLabs user journeys
//    @Test
//    @DisplayName("Test invalid userName but valid password - error has Epic sad fac")
//    public void checkOtherSwagLabsUserJourneys() {
//        webDriver.get(BASE_URL);
//        WebElement usernameField = webDriver.findElement(By.id("user-name"));
//        WebElement passwordField = webDriver.findElement(By.id("password"));
//        WebElement loginButton = webDriver.findElement(By.id("login-button"));
//        usernameField.sendKeys("user");
//        passwordField.sendKeys("secret_sauce");
//        loginButton.click();
//        MatcherAssert.assertThat(webDriver.findElement(By.className("error-message-container")).getText(), Matchers.containsString("Epic sadface: Username and password do not match any user in this service"));
//    }
//    @Test
//    @DisplayName("Opening the burger and navigating to about tab")
//    public void checkAboutTab() {
//        webDriver.get(BASE_URL);
//        WebElement usernameField = webDriver.findElement(By.id("user-name"));
//        WebElement passwordField = webDriver.findElement(By.id("password"));
//        WebElement loginButton = webDriver.findElement(By.id("login-button"));
//        usernameField.sendKeys("standard_user");
//        passwordField.sendKeys("secret_sauce");
//        loginButton.click();
//        webDriver.findElement(By.className("bm-burger-button")).click();
//        webDriver.findElement(By.linkText("About")).click();
//        Assertions.assertEquals("https://saucelabs.com/", webDriver.getCurrentUrl());
//    }
//    @Test
//    @DisplayName("Opening the burger and navigating to logout")
//    public void checkLogout() {
//        webDriver.get(BASE_URL);
//        WebElement usernameField = webDriver.findElement(By.id("user-name"));
//        WebElement passwordField = webDriver.findElement(By.id("password"));
//        WebElement loginButton = webDriver.findElement(By.id("login-button"));
//        usernameField.sendKeys("standard_user");
//        passwordField.sendKeys("secret_sauce");
//        loginButton.click();
//        webDriver.findElement(By.className("bm-burger-button")).click();
//        webDriver.findElement(By.linkText("Logout")).click();
//        Assertions.assertEquals("https://www.saucedemo.com/", webDriver.getCurrentUrl());
//    }
//
}
