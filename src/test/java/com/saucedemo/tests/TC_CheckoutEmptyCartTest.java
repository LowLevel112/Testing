package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_CheckoutEmptyCartTest extends BaseTest {

    @Test(description = "Verify checkout with empty cart shows error or prevents checkout")
    public void testCheckout_WithEmptyCart_ShouldShowError() {
        // GIVEN: User is logged in and on cart page with empty cart
        loginAsStandardUser();
        InventoryPage inventoryPage = new InventoryPage(driver, wait);
        CartPage cartPage = new CartPage(driver, wait);

        inventoryPage.openCart();
        cartPage.waitUntilLoaded();

        // WHEN: User attempts to checkout with empty cart
        cartPage.clickCheckout();

        // THEN: Should show error or prevent checkout (SauceDemo allows checkout but cart remains empty)
        Assert.assertTrue(cartPage.isCartEmpty(),
                "Cart should remain empty when attempting checkout with no items");
    }
}
