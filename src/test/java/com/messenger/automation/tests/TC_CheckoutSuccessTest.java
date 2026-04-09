package com.messenger.automation.tests;

import com.messenger.automation.base.BaseTest;
import com.messenger.automation.pages.CartPage;
import com.messenger.automation.pages.CheckoutPage;
import com.messenger.automation.pages.InventoryPage;
import com.messenger.automation.pages.SauceDemoLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_CheckoutSuccessTest extends BaseTest {
    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @Test
    public void testCheckoutSuccess() {
        SauceDemoLoginPage loginPage = new SauceDemoLoginPage(driver, wait);
        InventoryPage inventoryPage = new InventoryPage(driver, wait);
        CartPage cartPage = new CartPage(driver, wait);
        CheckoutPage checkoutPage = new CheckoutPage(wait);

        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.waitUntilLoaded();
        inventoryPage.addProductToCart(PRODUCT_NAME);
        inventoryPage.openCart();
        cartPage.waitUntilLoaded();
        cartPage.clickCheckout();

        checkoutPage.fillCustomerInformation("Minh", "Nguyen", "700000");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        Assert.assertEquals(
                checkoutPage.getSuccessMessage(),
                "Thank you for your order!",
                "Thong bao checkout thanh cong khong dung.");
    }
}
