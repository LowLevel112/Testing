package com.messenger.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {
    private final WebDriverWait wait;

    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.id("finish");
    private final By completeHeader = By.className("complete-header");

    public CheckoutPage(WebDriverWait wait) {
        this.wait = wait;
    }

    public void fillCustomerInformation(String firstName, String lastName, String postalCode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(firstName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput)).sendKeys(lastName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(postalCodeInput)).sendKeys(postalCode);
    }

    public void clickContinue() {
        WebElement continueElement = wait.until(ExpectedConditions.visibilityOfElementLocated(continueButton));
        ((JavascriptExecutor) wait.until(driver -> driver)).executeScript("arguments[0].click();", continueElement);
        wait.until(ExpectedConditions.visibilityOfElementLocated(finishButton));
    }

    public void clickFinish() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(finishButton)).click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader)).getText();
    }
}
