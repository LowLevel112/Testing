# TestingAutomation

## Structure

```text
TestingAutomation/
|-- pom.xml
|-- testng.xml
|-- README.md
|-- .github/
|   `-- workflows/
|       `-- maven.yml
`-- src/
    |-- main/
    |   `-- java/
    |       |-- com/
    |       |   |-- messenger/
    |       |   |   `-- automation/
    |       |   |       |-- base/
    |       |   |       |   `-- BaseTest.java
    |       |   |       |-- listeners/
    |       |   |       |-- pages/
    |       |   |       |   |-- CartPage.java
    |       |   |       |   |-- ChatPage.java
    |       |   |       |   |-- CheckoutPage.java
    |       |   |       |   |-- InventoryPage.java
    |       |   |       |   |-- LoginPage.java
    |       |   |       |   `-- SauceDemoLoginPage.java
    |       |   |       |-- reports/
    |       |   |       `-- utils/
    |       |   |           `-- ConfigReader.java
    |       |   `-- saucedemo/
    |       |       |-- base/
    |       |       |   `-- BaseTest.java
    |       |       |-- listeners/
    |       |       |   `-- TextReportListener.java
    |       |       |-- pages/
    |       |       |   |-- CartPage.java
    |       |       |   |-- CheckoutPage.java
    |       |       |   |-- InventoryPage.java
    |       |       |   `-- SauceDemoLoginPage.java
    |       |       `-- reports/
    |       `-- org/
    |           `-- example/
    |               `-- Main.java
    `-- test/
        `-- java/
            `-- com/
                |-- messenger/
                |   `-- automation/
                |       `-- tests/
                `-- saucedemo/
                    `-- tests/
                        |-- TC_AddToCartTest.java
                        |-- TC_CheckoutSuccessTest.java
                        |-- TC_LoginDataDrivenTest.java
                        |-- TC_LoginTest.java
                        |-- TC_LoginWithEmptyCredentialsTest.java
                        |-- TC_LoginWithInvalidCredentialsTest.java
                        |-- TC_RemoveFromCartTest.java
                        `-- TC_SortProductsTest.java
```

## Test Coverage

- `TC_LoginTest`: Verify successful login.
- `TC_LoginWithEmptyCredentialsTest`: Verify login fails with empty credentials.
- `TC_LoginWithInvalidCredentialsTest`: Verify login fails with invalid credentials.
- `TC_LoginDataDrivenTest`: Verify login functionality using multiple data sets.
- `TC_AddToCartTest`: Add product to cart and verify cart content.
- `TC_RemoveFromCartTest`: Remove product from cart and verify cart is empty.
- `TC_CheckoutSuccessTest`: Complete checkout flow and verify success message.
- `TC_SortProductsTest`: Sort products and verify the order.

## Run Tests

Use Maven from project root:

```bash
mvn clean test
```

If Maven is not in `PATH`, use the full path:

```powershell
& "C:\Tools\Maven\apache-maven-3.9.14-bin\apache-maven-3.9.14\bin\mvn.cmd" clean test
```
