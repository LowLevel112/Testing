package com.messenger.automation.backup.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChatPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Placeholder selector: inspect the real composer in DevTools and update when Messenger changes DOM.
    private final By messageInput = By.xpath("//div[@aria-label='Message' or @aria-label='Aa' or @contenteditable='true']");

    // Placeholder selector: some Messenger variants show a Send button, others show a thumbs-up button.
    private final By sendButton = By.xpath("//button[@aria-label='Send' or @aria-label='Like']");

    // Placeholder selector: narrow this to outgoing message bubbles in your real DOM if needed.
    private final By outgoingMessageBubbles = By.xpath(
            "(//div[@role='main']//div[contains(@aria-label,'You sent') or contains(@data-testid,'message-container') or contains(@class,'x1lliihq')])[last()]");

    public ChatPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void waitUntilComposerReady() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageInput));
    }

    public void focusComposer() {
        WebElement composer = wait.until(ExpectedConditions.elementToBeClickable(messageInput));
        composer.click();
    }

    public void typeMessage(String message) {
        WebElement composer = wait.until(ExpectedConditions.elementToBeClickable(messageInput));
        composer.sendKeys(message);
    }

    public void clickSend() {
        WebElement sendButtonElement = wait.until(ExpectedConditions.elementToBeClickable(sendButton));
        sendButtonElement.click();
    }

    public boolean isSendButtonEnabled() {
        WebElement sendButtonElement = wait.until(ExpectedConditions.presenceOfElementLocated(sendButton));
        return sendButtonElement.isEnabled();
    }

    public String getLatestMessageText() {
        WebElement latestMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(outgoingMessageBubbles));
        return latestMessageElement.getText();
    }

    public void waitUntilLatestMessageContains(String expectedText) {
        wait.until(driver -> getLatestMessageText().contains(expectedText));
    }

    public boolean isLatestMessageEqualTo(String expectedText) {
        return getLatestMessageText().trim().equals(expectedText);
    }

    public boolean isLatestMessageDifferentFrom(String unexpectedText) {
        return !getLatestMessageText().trim().equals(unexpectedText);
    }
}
