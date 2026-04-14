package com.saucedemo.tests;

import com.saucedemo.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.SauceDemoLoginPage;
import com.saucedemo.pages.CartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StepDefinitions {
    private WebDriver driver = Hooks.driver;
    private WebDriverWait wait = Hooks.wait;
    private String addedProduct;

    @Given("I am logged in as {string}")
    public void i_am_logged_in_as(String username) {
        SauceDemoLoginPage login = new SauceDemoLoginPage(driver, wait);
        login.login(ConfigReader.getProperty("STANDARD_USER"), ConfigReader.getProperty("STANDARD_PASSWORD"));
        InventoryPage inv = new InventoryPage(driver, wait);
        inv.waitUntilLoaded();
    }

    @When("I add {string} to my cart")
    public void i_add_to_my_cart(String productName) {
        InventoryPage inv = new InventoryPage(driver, wait);
        inv.addProductToCart(productName);
        addedProduct = productName;
    }

    @When("I open the cart and remove that product")
    public void i_open_cart_and_remove() {
        InventoryPage inv = new InventoryPage(driver, wait);
        inv.openCart();
        CartPage cart = new CartPage(driver, wait);
        cart.waitUntilLoaded();
        if (!cart.isCartEmpty()) {
            cart.removeProduct(addedProduct);
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
