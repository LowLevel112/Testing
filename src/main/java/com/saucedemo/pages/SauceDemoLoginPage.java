package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SauceDemoLoginPage {
    private final WebDriverWait wait;

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorContainer = By.className("error-message-container");

    public SauceDemoLoginPage(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
    }

    public void login(String username, String password) {
        WebElement usernameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        usernameElement.clear();
        usernameElement.sendKeys(username);

        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passwordElement.clear();
        passwordElement.sendKeys(password);

        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).click();
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).click();
    }

    public boolean isErrorVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorContainer)).isDisplayed();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorContainer)).getText();
    }
}
