package com.saucedemo.base;

import com.saucedemo.utils.ConfigReader;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.SauceDemoLoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected String baseUrl;

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("autofill.profile_enabled", false);
        prefs.put("autofill.credit_card_enabled", false);

        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordLeakDetection,PasswordCheck,AutofillServerCommunication");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        baseUrl = ConfigReader.getProperty("BASE_URL");
        driver.get(baseUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
    }

    protected void loginAsStandardUser() {
        String user = ConfigReader.getProperty("STANDARD_USER");
        String pass = ConfigReader.getProperty("STANDARD_PASSWORD");
        SauceDemoLoginPage loginPage = new SauceDemoLoginPage(driver, wait);
        loginPage.login(user, pass);
        InventoryPage inventory = new InventoryPage(driver, wait);
        inventory.waitUntilLoaded();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
