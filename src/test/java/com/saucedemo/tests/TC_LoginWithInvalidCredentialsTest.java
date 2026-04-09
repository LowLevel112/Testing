package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_LoginWithInvalidCredentialsTest extends BaseTest {

    @Test
    public void testOneCharacterCredentialsShowError() {
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        usernameInput.sendKeys("a");

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passwordInput.sendKeys("a");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
        loginButton.click();

        WebElement errorContainer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("error-message-container")));

        Assert.assertTrue(
                errorContainer.getText().contains("Username and password do not match"),
                "Thong bao loi khong dung voi truong hop username/password 1 ky tu.");
    }
}
