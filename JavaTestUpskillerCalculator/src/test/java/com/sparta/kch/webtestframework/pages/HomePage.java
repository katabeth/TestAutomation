package com.sparta.kch.webtestframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private WebDriver driver;
    private By userNameField = new By.ById("user-name");
    private By passwordField = new By.ById("password");
    private By loginButton = new By.ById("login-button");
    private By errorField = new By.ById("error-message-container");
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
        driver.findElement(this.userNameField).sendKeys(userName);
        driver.findElement(this.passwordField).sendKeys(password);
        driver.findElement(this.loginButton).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorField).getText();
    }
}
