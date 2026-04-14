package com.saucedemo.tests;

import org.testng.annotations.DataProvider;

public class ProductDataProvider {

    @DataProvider(name = "productNames")
    public static Object[][] getProductNames() {
        return new Object[][] {
            {"Sauce Labs Backpack"},
            {"Sauce Labs Bolt T-Shirt"}
        };
    }
}
