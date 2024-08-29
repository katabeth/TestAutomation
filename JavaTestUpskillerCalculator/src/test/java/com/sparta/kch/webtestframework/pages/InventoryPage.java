package com.sparta.kch.webtestframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InventoryPage {

    private final WebDriver driver;
    private final By inventoryItems = new By.ByClassName("inventory_item");
    private final By button = new By.ByTagName("button");
    private final By addSauceLabsBackpackToCartButton = new By.ById("add-to-cart-sauce-labs-backpack");
    private final By shoppingCartBadge = new By.ByClassName("shopping_cart_badge");
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getInventoryItems() {
        return driver.findElements(inventoryItems);
    }
    public void addSauceLabsBackpackToCart() {
        driver.findElement(addSauceLabsBackpackToCartButton).click();
    }
    public int getShoppingCartBadge() {
        if (driver.findElements(shoppingCartBadge).isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(driver.findElement(shoppingCartBadge).getText());
        }
    }

    public void addItemToCart(int index) {
        getInventoryItems().get(index).findElement(button).click();
    }
}
