package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_AddToCartTest extends BaseTest {
    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @Test(description = "Verify adding product to cart increases badge count and shows in cart")
    public void testAddToCart_WhenValidProduct_ShouldIncreaseCartBadgeAndShowInCart() {
        // GIVEN: User is logged in and on inventory page
        loginAsStandardUser();
        InventoryPage inventoryPage = new InventoryPage(driver, wait);
        CartPage cartPage = new CartPage(driver, wait);

        // WHEN: User adds a product to cart
        inventoryPage.addProductToCart(PRODUCT_NAME);

        // THEN: Cart badge should show 1 and product should be in cart
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 1,
                "Cart badge should show 1 after adding one product");

        inventoryPage.openCart();
        cartPage.waitUntilLoaded();
        Assert.assertTrue(cartPage.containsProduct(PRODUCT_NAME),
                "Cart should contain the added product");
    }
}
