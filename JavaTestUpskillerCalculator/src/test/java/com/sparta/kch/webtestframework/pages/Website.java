package com.sparta.kch.webtestframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Website {

    private WebDriver driver;
    private HomePage homePage;
    private InventoryPage inventoryPage;
    public Website(WebDriver driver) {
        this.driver = driver;
        homePage = new HomePage(driver);
        inventoryPage = new InventoryPage(driver);
    }

    public HomePage getHomePage() {
        return homePage;
    }
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    public InventoryPage getInventoryPage() {
        return inventoryPage;
    }

}
