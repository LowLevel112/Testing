package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.SauceDemoLoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TC_LoginDataDrivenTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"standard_user", "secret_sauce", true, "inventory.html"},
                {"locked_out_user", "secret_sauce", false, "locked out"},
                {"invalid_user", "wrong", false, "Username and password do not match"}
        };
    }

    @Test(dataProvider = "loginData")
    public void testLoginWithDataProvider(String username, String password, boolean shouldLoginSucceed, String expectedText) {
        SauceDemoLoginPage loginPage = new SauceDemoLoginPage(driver, wait);
        loginPage.login(username, password);

        if (shouldLoginSucceed) {
            wait.until(ExpectedConditions.urlContains("inventory.html"));
            Assert.assertTrue(driver.getCurrentUrl().contains(expectedText), "Dang nhap thanh cong nhung URL khong dung.");
            return;
        }

        Assert.assertTrue(loginPage.isErrorVisible(), "Thong bao loi khong hien thi voi bo du lieu: " + username);
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedText), "Thong bao loi khong dung voi bo du lieu: " + username);
    }
}
