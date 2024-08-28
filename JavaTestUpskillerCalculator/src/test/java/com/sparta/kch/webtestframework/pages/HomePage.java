package com.sparta.kch.webtestframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private WebDriver driver;
    private By userNameField = new By.ByName("username");
    private By passwordField = new By.ByName("password");
    private By loginButton = new By.ByName("login");
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


}
