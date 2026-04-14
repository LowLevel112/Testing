package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.SauceDemoLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Refactored test cases following Given-When-Then pattern and best practices.
 * These tests demonstrate clean, maintainable test structure using Page Object Model.
 */
public class RefactoredTests extends BaseTest {

    private static final String PRODUCT_NAME = "Sauce Labs Backpack";
    private static final String BIKE_LIGHT = "Sauce Labs Bike Light";

    @Test(description = "Verify successful login redirects to inventory page")
    public void givenCredentialsWhenLoginThenInventoryPageDisplays() {
        // When
        loginAsStandardUser();

        // Then
        InventoryPage inventoryPage = new InventoryPage(driver, wait);
        inventoryPage.waitUntilLoaded();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "URL should contain 'inventory.html' after successful login"
        );
    }

    @Test(description = "Verify adding product to cart increments cart badge")
    public void whenAddProductThenCartBadgeIncremented() {
        // Given
        loginAsStandardUser();
        InventoryPage inventoryPage = new InventoryPage(driver, wait);

        // When
        inventoryPage.addProductToCart(PRODUCT_NAME);

        // Then
        Assert.assertEquals(
                inventoryPage.getCartBadgeCount(), 1,
                "Cart badge should show 1 after adding one product"
        );
    }

    @Test(description = "Verify product appears in cart after adding")
    public void whenAddProductAndOpenCartThenProductDisplays() {
        // Given
        loginAsStandardUser();
        InventoryPage inventoryPage = new InventoryPage(driver, wait);
        CartPage cartPage = new CartPage(driver, wait);

        // When
        inventoryPage.addProductToCart(PRODUCT_NAME);
        inventoryPage.openCart();
        cartPage.waitUntilLoaded();

        // Then
        Assert.assertTrue(
                cartPage.containsProduct(PRODUCT_NAME),
                "Cart should contain the added product"
        );
    }

    @Test(description = "Verify removing product from cart empties it")
    public void whenRemoveProductThenCartIsEmpty() {
        // Given
        loginAsStandardUser();
        InventoryPage inventoryPage = new InventoryPage(driver, wait);
        CartPage cartPage = new CartPage(driver, wait);

        inventoryPage.addProductToCart(PRODUCT_NAME);
        inventoryPage.openCart();
        cartPage.waitUntilLoaded();

        // When
        cartPage.removeProduct(PRODUCT_NAME);

        // Then
        Assert.assertTrue(
                cartPage.isCartEmpty(),
                "Cart should be empty after removing the only product"
        );
        Assert.assertEquals(
                cartPage.getItemCount(), 0,
                "Cart item count should be 0"
        );
    }

    @Test(description = "Verify successful checkout flow")
    public void givenProductInCartWhenCheckoutThenSuccessMessageDisplays() {
        // Given
        loginAsStandardUser();
        InventoryPage inventoryPage = new InventoryPage(driver, wait);
        CartPage cartPage = new CartPage(driver, wait);
        CheckoutPage checkoutPage = new CheckoutPage(driver, wait);

        // When
        inventoryPage.addProductToCart(PRODUCT_NAME);
        inventoryPage.openCart();
        cartPage.waitUntilLoaded();
        cartPage.clickCheckout();

        checkoutPage.fillCustomerInformation("John", "Doe", "12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        // Then
        Assert.assertEquals(
                checkoutPage.getSuccessMessage(),
                "Thank you for your order!",
                "Success message should be displayed after checkout"
        );
    }

    @Test(description = "Verify product sorting by low to high price")
    public void whenSortProductsByLowToHighThenPricesAreSorted() {
        // Given
        loginAsStandardUser();
        InventoryPage inventoryPage = new InventoryPage(driver, wait);

        // When
        inventoryPage.sortByLowToHigh();

        // Then
        var prices = inventoryPage.getDisplayedPrices();
        var sortedPrices = new java.util.ArrayList<>(prices);
        java.util.Collections.sort(sortedPrices);

        Assert.assertEquals(
                prices, sortedPrices,
                "Products should be sorted by price from low to high"
        );
    }
}

