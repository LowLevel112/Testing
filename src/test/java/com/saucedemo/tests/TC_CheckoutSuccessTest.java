package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_CheckoutSuccessTest extends BaseTest {

    @Test(dataProvider = "productNames", dataProviderClass = ProductDataProvider.class,
          description = "Verify successful checkout with a product")
    public void testCheckoutSuccess(String productName) {
        InventoryPage inventoryPage = new InventoryPage(driver, wait);
        CartPage cartPage = new CartPage(driver, wait);
        CheckoutPage checkoutPage = new CheckoutPage(driver, wait);

        loginAsStandardUser();
        inventoryPage.waitUntilLoaded();
        inventoryPage.addProductToCart(productName);
        inventoryPage.openCart();
        cartPage.waitUntilLoaded();
        cartPage.clickCheckout();

        checkoutPage.fillCustomerInformation("Minh", "Nguyen", "700000");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        Assert.assertEquals(checkoutPage.getSuccessMessage(), "Thank you for your order!", "Thong bao checkout thanh cong khong dung.");
    }
}
