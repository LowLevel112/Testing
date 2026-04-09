## Structure

```text
MessengerAutomation/
|-- pom.xml
|-- testng.xml
|-- README.md
|-- .github/
|   `-- workflows/
|       `-- maven.yml
`-- src/
    |-- main/
    |   `-- java/
    |       `-- com/
    |           `-- messenger/
    |               `-- automation/
    |                   |-- base/
    |                   |   `-- BaseTest.java
    |                   |-- pages/
    |                   |   |-- CartPage.java
    |                   |   |-- ChatPage.java
    |                   |   |-- CheckoutPage.java
    |                   |   |-- InventoryPage.java
    |                   |   |-- LoginPage.java
    |                   |   `-- SauceDemoLoginPage.java
    |                   `-- utils/
    |                       `-- ConfigReader.java
    `-- test/
        `-- java/
            `-- com/
                `-- messenger/
                    `-- automation/
                        `-- tests/
                            |-- TC008_SendTextMessageTest.java
                            |-- TC023_SendEmptyMessageTest.java
                            |-- TC025_SendOneCharacterTest.java
                            |-- TC_AddToCartTest.java
                            |-- TC_CheckoutSuccessTest.java
                            |-- TC_RemoveFromCartTest.java
                            `-- TC_SortProductsTest.java
```

## Test Coverage

- `TC008_SendTextMessageTest`: login success and verify redirect to `inventory.html`
- `TC023_SendEmptyMessageTest`: empty login attempt and verify error container
- `TC025_SendOneCharacterTest`: invalid one-character credentials and verify error message
- `TC_AddToCartTest`: add one product to cart and verify cart content
- `TC_RemoveFromCartTest`: remove product from cart and verify cart is empty
- `TC_CheckoutSuccessTest`: complete checkout flow and verify success message
- `TC_SortProductsTest`: sort products by low-to-high price and verify ascending order

## Run Tests

Use Maven from project root:

```bash
mvn clean test
```

If Maven is not in `PATH`, use the full path:

```powershell
& "C:\Tools\Maven\apache-maven-3.9.14-bin\apache-maven-3.9.14\bin\mvn.cmd" clean test
