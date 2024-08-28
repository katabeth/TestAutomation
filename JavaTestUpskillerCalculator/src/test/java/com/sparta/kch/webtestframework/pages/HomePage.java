package com.sparta.kch.webtestframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private final WebDriver driver;
    private final By userNameField = new By.ById("user-name");
    private final By passwordField = new By.ById("password");
    private final By loginButton = new By.ById("login-button");
    private final By errorField = new By.ByClassName("error-message-container");
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUserName(String userName) {
        driver.findElement(this.userNameField).sendKeys(userName);
    }
    public void enterPassword(String password) {
        driver.findElement(this.passwordField).sendKeys(password);
    }
    public void clickLogin() {
        driver.findElement(this.loginButton).click();
    }
    public void login(String userName, String password) {
        enterUserName(userName);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        return driver.findElement(errorField).getText();
    }
}
