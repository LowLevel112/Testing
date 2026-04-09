package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.SauceDemoLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_AddToCartTest extends BaseTest {
    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @Test
    public void testAddToCart() {
        SauceDemoLoginPage loginPage = new SauceDemoLoginPage(driver, wait);
        InventoryPage inventoryPage = new InventoryPage(driver, wait);
        CartPage cartPage = new CartPage(driver, wait);

        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.waitUntilLoaded();
        inventoryPage.addProductToCart(PRODUCT_NAME);

        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 1, "So luong san pham trong gio hang khong dung.");

        inventoryPage.openCart();
        cartPage.waitUntilLoaded();

        Assert.assertTrue(cartPage.containsProduct(PRODUCT_NAME), "Gio hang khong chua san pham vua them.");
    }
}
