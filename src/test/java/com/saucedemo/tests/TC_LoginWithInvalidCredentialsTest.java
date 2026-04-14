package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.SauceDemoLoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_LoginWithInvalidCredentialsTest extends BaseTest {

    @Test
    public void testOneCharacterCredentialsShowError() {
        SauceDemoLoginPage loginPage = new SauceDemoLoginPage(driver, wait);

        loginPage.login("a", "a");

        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Username and password do not match"),
                "Thong bao loi khong dung voi truong hop username/password 1 ky tu.");
    }
}
