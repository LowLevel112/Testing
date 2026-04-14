package com.saucedemo.tests;

import com.messenger.automation.utils.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.SauceDemoLoginPage;
import com.saucedemo.pages.CartPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class StepDefinitions {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void before() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get(ConfigReader.getProperty("base.url"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
    }

    @After
    public void after() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am logged in as {string}")
    public void i_am_logged_in_as(String username) {
        SauceDemoLoginPage login = new SauceDemoLoginPage(driver, wait);
        login.login(ConfigReader.getProperty("standard.user"), ConfigReader.getProperty("standard.password"));
        InventoryPage inv = new InventoryPage(driver, wait);
        inv.waitUntilLoaded();
    }

    @When("I add {string} to my cart")
    public void i_add_to_my_cart(String productName) {
        InventoryPage inv = new InventoryPage(driver, wait);
        inv.addProductToCart(productName);
    }

    @When("I open the cart and remove that product")
    public void i_open_cart_and_remove() {
        InventoryPage inv = new InventoryPage(driver, wait);
        inv.openCart();
        CartPage cart = new CartPage(driver, wait);
        cart.waitUntilLoaded();
        if (!cart.isCartEmpty()) {
            cart.removeProduct("Sauce Labs Bike Light");
        }
    }

    @When("I open the cart")
    public void i_open_the_cart() {
        InventoryPage inv = new InventoryPage(driver, wait);
        inv.openCart();
        CartPage cart = new CartPage(driver, wait);
        cart.waitUntilLoaded();
    }

    @Then("the cart badge should show {string}")
    public void the_cart_badge_should_show(String count) {
        InventoryPage inv = new InventoryPage(driver, wait);
        Assert.assertEquals(inv.getCartBadgeCount(), Integer.parseInt(count),
                "Cart badge should show " + count);
    }

    @Then("the cart should be empty")
    public void the_cart_should_be_empty() {
        CartPage cart = new CartPage(driver, wait);
        cart.waitUntilLoaded();
        Assert.assertTrue(cart.isCartEmpty(), "Cart should be empty");
    }
}
