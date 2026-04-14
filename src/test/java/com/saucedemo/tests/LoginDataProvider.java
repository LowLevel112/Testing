package com.saucedemo.tests;

import com.saucedemo.utils.ConfigReader;
import org.testng.annotations.DataProvider;

public class LoginDataProvider {
    @DataProvider(name = "loginCredentials")
    public static Object[][] loginCredentials() {
        return new Object[][]{
                {ConfigReader.getProperty("standard.user"), ConfigReader.getProperty("standard.password"), null, true},
                {ConfigReader.getProperty("locked.user"), ConfigReader.getProperty("standard.password"), "Epic sadface: Sorry, this user has been locked out.", false},
                {ConfigReader.getProperty("wrong.user"), ConfigReader.getProperty("wrong.password"), "Epic sadface: Username and password do not match any user in this service", false}
        };
    }
}
