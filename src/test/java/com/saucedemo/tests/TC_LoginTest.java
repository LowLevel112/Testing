package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_LoginTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        usernameInput.sendKeys("standard_user");

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passwordInput.sendKeys("secret_sauce");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("inventory.html"));
        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "URL khong chuyen sang trang inventory sau khi login thanh cong.");
    }
}
