package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    private final By cartContainer = By.id("cart_contents_container");
    private final By cartItems = By.className("cart_item");
    private final By checkoutButton = By.id("checkout");
    private final By firstNameInput = By.id("first-name");

    public CartPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void waitUntilLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartContainer));
    }

    public boolean containsProduct(String productName) {
        By productNameLocator = By.xpath("//div[@class='cart_item']//div[@class='inventory_item_name' and text()='"
                + productName + "']");
        return !driver.findElements(productNameLocator).isEmpty();
    }

    public void removeProduct(String productName) {
        By productNameLocator = By.xpath("//div[@class='cart_item']//div[@class='inventory_item_name' and text()='"
                + productName + "']");
        By removeButton = By.xpath("//div[@class='cart_item' and .//div[text()='" + productName
                + "']]//button[text()='Remove']");
        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(productNameLocator));
    }

    public int getItemCount() {
        return driver.findElements(cartItems).size();
    }

    public boolean isCartEmpty() {
        return getItemCount() == 0;
    }

    public void clickCheckout() {
        WebElement checkoutElement = wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutButton));
        checkoutElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
    }
}
