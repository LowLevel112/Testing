package com.messenger.automation.tests;

import com.messenger.automation.base.BaseTest;
import com.messenger.automation.pages.InventoryPage;
import com.messenger.automation.pages.SauceDemoLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TC_SortProductsTest extends BaseTest {

    @Test
    public void testSortProductsLowToHigh() {
        SauceDemoLoginPage loginPage = new SauceDemoLoginPage(driver, wait);
        InventoryPage inventoryPage = new InventoryPage(driver, wait);

        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.waitUntilLoaded();
        inventoryPage.sortByLowToHigh();

        List<Double> actualPrices = inventoryPage.getDisplayedPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        Assert.assertEquals(
                actualPrices,
                expectedPrices,
                "Danh sach gia khong duoc sap xep tang dan.");
    }
}
