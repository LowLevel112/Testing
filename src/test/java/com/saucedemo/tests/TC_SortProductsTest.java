package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TC_SortProductsTest extends BaseTest {

    @Test
    public void testSortProductsLowToHigh() {
        InventoryPage inventoryPage = new InventoryPage(driver, wait);

        loginAsStandardUser();
        inventoryPage.waitUntilLoaded();
        inventoryPage.sortByLowToHigh();

        List<Double> actualPrices = inventoryPage.getDisplayedPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        Assert.assertEquals(actualPrices, expectedPrices, "Danh sach gia khong duoc sap xep tang dan.");
    }
}
