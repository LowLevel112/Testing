package com.saucedemo.tests;

import com.saucedemo.utils.ConfigReader;
import org.testng.annotations.DataProvider;

public class LoginDataProvider {
    @DataProvider(name = "loginCredentials")
    public static Object[][] loginCredentials() {
        return new Object[][]{
                {ConfigReader.getProperty("STANDARD_USER"), ConfigReader.getProperty("STANDARD_PASSWORD"), null, true},
                {ConfigReader.getProperty("LOCKED_USER"), ConfigReader.getProperty("STANDARD_PASSWORD"), "Epic sadface: Sorry, this user has been locked out.", false},
                {ConfigReader.getProperty("WRONG_USER"), ConfigReader.getProperty("WRONG_PASSWORD"), "Epic sadface: Username and password do not match any user in this service", false}
        };
    }
}
