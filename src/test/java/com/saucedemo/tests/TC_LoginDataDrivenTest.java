package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.SauceDemoLoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_LoginDataDrivenTest extends BaseTest {

    @Test(dataProvider = "loginCredentials", dataProviderClass = LoginDataProvider.class,
          description = "Verify login functionality with multiple credentials")
    public void testLoginWithMultipleCredentials_WhenVariousInputs_ShouldHandleCorrectly(String username, String password, String expectedError, boolean shouldSucceed) {
        // GIVEN: User is on login page
        SauceDemoLoginPage loginPage = new SauceDemoLoginPage(driver, wait);

        // WHEN: User attempts to login with provided credentials
        loginPage.login(username, password);

        // THEN: Verify login result based on expected success/failure
        if (shouldSucceed) {
            // Successful login should redirect to inventory
            wait.until(ExpectedConditions.urlContains("inventory.html"));
            Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"),
                    "Successful login should redirect to inventory page");
        } else {
            // Failed login should show error message
            Assert.assertTrue(loginPage.isErrorVisible(),
                    "Error message should be visible for failed login: " + username);
            Assert.assertEquals(loginPage.getErrorMessage(), expectedError,
                    "Error message should match expected for: " + username);
        }
    }
}
