package com.sparta.kch.webtestframework.stepdefs;

import com.sparta.kch.webtestframework.pages.Website;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.io.IOException;

public class LoginStepdefs {

    private Website website;
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private int actual = 0;
    private int start = 0;

    @After
    public void afterEach(){
        TestSetup.quitWebDriver();
        TestSetup.stopService();
    }
    @Before
    public static void setup() throws IOException {
        TestSetup.startGeckoDriver();
        TestSetup.createWebDriver();
    }

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        website = TestSetup.getWebsite(BASE_URL);
    }
    @Given("I am on the inventory page")
    public void iAmOnTheInventoryPage() {
        website = TestSetup.getWebsite(BASE_URL);
    }

    @And("I am logged in as a standard user")
    public void iAmLoggedInAsAStandardUser() {
        website.getHomePage().login("standard_user", "secret_sauce");
    }
    @And("I have entered the username {string}")
    public void iHaveEnteredTheUsername(String arg0) {
        website.getHomePage().enterUserName(arg0);
    }
    @And("I have entered the password {string}")
    public void iHaveEnteredThePassword(String arg0) {
        website.getHomePage().enterPassword(arg0);
    }

    @When("I click the login button")
    public void iClickTheLoginButton() {
        website.getHomePage().clickLogin();
    }
    @When("I count the number of items")
    public void iCountTheNumberOfItems() {
        actual = website.getInventoryPage().getInventoryItems().size();
    }
    @When("I add an item to basket")
    public void iAddAnItemToBasket() {
        start = website.getInventoryPage().getShoppingCartBadge();
        website.getInventoryPage().addItemToCart(2);
    }

    @Then("I should land on the inventory page")
    public void iShouldLandOnTheInventoryPage() {
        MatcherAssert.assertThat(website.getCurrentUrl(), Matchers.containsString("inventory.html"));
    }
    @Then("I should see an error message that contains {string}")
    public void iShouldSeeAnErrorMessageThatContains(String arg0) {
        MatcherAssert.assertThat(website.getHomePage().getErrorMessage(), Matchers.containsString(arg0));
    }
    @Then("I should see {int} items")
    public void iShouldSeeItems(int arg0) {
        MatcherAssert.assertThat(actual, Matchers.equalTo(arg0));
    }
    @Then("the basket count should increase by {int}")
    public void theBasketCountShouldIncreaseBy(int arg0) {
        MatcherAssert.assertThat(website.getInventoryPage().getShoppingCartBadge()-start, Matchers.equalTo(arg0));
    }



}
