package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.SauceDemoLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_RemoveFromCartTest extends BaseTest {
    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @Test
    public void testRemoveFromCart() {
        SauceDemoLoginPage loginPage = new SauceDemoLoginPage(driver, wait);
        InventoryPage inventoryPage = new InventoryPage(driver, wait);
        CartPage cartPage = new CartPage(driver, wait);

        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.waitUntilLoaded();
        inventoryPage.addProductToCart(PRODUCT_NAME);
        inventoryPage.openCart();
        cartPage.waitUntilLoaded();

        cartPage.removeProduct(PRODUCT_NAME);

        Assert.assertTrue(cartPage.isCartEmpty(), "Gio hang phai rong sau khi xoa san pham.");
        Assert.assertEquals(cartPage.getItemCount(), 0, "So luong san pham trong gio phai bang 0.");
    }
}
