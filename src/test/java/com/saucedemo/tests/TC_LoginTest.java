package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.SauceDemoLoginPage;
import com.saucedemo.utils.ConfigReader;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_LoginTest extends BaseTest {

    @Test(description = "Verify successful login with valid credentials")
    public void testLogin_WhenValidCredentials_ShouldRedirectToInventory() {
        // GIVEN: User is on login page
        SauceDemoLoginPage loginPage = new SauceDemoLoginPage(driver, wait);

        // WHEN: User enters valid username and password and clicks login
        loginPage.login(ConfigReader.getProperty("STANDARD_USER"), ConfigReader.getProperty("STANDARD_PASSWORD"));

        // THEN: User should be redirected to inventory page
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Login should redirect to inventory page with valid credentials"
        );
    }
}
