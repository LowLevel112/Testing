package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_LoginWithEmptyCredentialsTest extends BaseTest {

    @Test
    public void testEmptyUsernameShowsError() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
        loginButton.click();

        WebElement errorContainer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("error-message-container")));

        Assert.assertTrue(
                errorContainer.isDisplayed(),
                "Thong bao loi khong hien thi khi de trong username.");
    }
}
