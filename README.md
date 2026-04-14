# TestingAutomation - Selenium WebDriver với TestNG & Cucumber

Dự án automation testing chuyên nghiệp cho website SauceDemo.com, áp dụng các best practices trong testing.

## 🎯 Mục tiêu

- **Chuyên nghiệp**: Cấu trúc rõ ràng, dễ hiểu, dễ bảo trì
- **Tester Mindset**: Test cả positive và negative scenarios
- **BDD**: Sử dụng Cucumber cho behavior-driven development
- **Scalable**: Dễ mở rộng thêm test cases và features

## 📋 Yêu cầu hệ thống

- Java 21+
- Maven 3.6+
- Chrome Browser
- Windows/Linux/Mac

## 🚀 Cài đặt và cấu hình

### 1. Clone project
```bash
git clone <repository-url>
cd TestingAutomation
```

### 2. Cài đặt dependencies
```bash
mvn clean install
```

### 3. Cấu hình môi trường (src/test/resources/config.properties)
```properties
base.url=https://www.saucedemo.com/
standard.user=standard_user
standard.password=secret_sauce
locked.user=locked_out_user
wrong.user=wrong_user
wrong.password=wrong_password
```

## 🏗️ Cấu trúc project

```
TestingAutomation/
├── pom.xml                           # Maven dependencies
├── testng.xml                        # TestNG suite configuration
├── README.md                         # Documentation
└── src/
    ├── main/java/
    │   └── com/saucedemo/
    │       ├── base/
    │       │   └── BaseTest.java     # Base class với WebDriver setup
    │       ├── pages/                # Page Object Model
    │       │   ├── SauceDemoLoginPage.java
    │       │   ├── InventoryPage.java
    │       │   ├── CartPage.java
    │       │   └── CheckoutPage.java
    │       ├── listeners/
    │       │   └── TextReportListener.java
    │       └── utils/
    │           └── ConfigReader.java # Configuration management
    └── test/
        ├── java/com/saucedemo/tests/
        │   ├── TC_LoginTest.java           # Unit tests (Given-When-Then)
        │   ├── TC_AddToCartTest.java
        │   ├── TC_RemoveFromCartTest.java
        │   ├── TC_LoginDataDrivenTest.java # Data-driven tests
        │   ├── LoginDataProvider.java      # Test data provider
        │   ├── StepDefinitions.java        # Cucumber step definitions
        │   ├── CucumberTestRunner.java     # Cucumber runner
        │   └── RefactoredTests.java        # Additional BDD tests
        └── resources/
            ├── config.properties          # Environment config
            └── features/
                └── add_to_cart.feature    # BDD feature files
```

## 🧪 Test Coverage

### Unit Tests (TestNG)
- **TC_LoginTest**: Đăng nhập thành công với credentials hợp lệ
- **TC_LoginDataDrivenTest**: Test login với nhiều bộ dữ liệu (DataProvider)
- **TC_AddToCartTest**: Thêm sản phẩm vào giỏ hàng
- **TC_RemoveFromCartTest**: Xóa sản phẩm khỏi giỏ hàng + test negative cases
- **TC_CheckoutSuccessTest**: Quy trình checkout hoàn chỉnh
- **TC_SortProductsTest**: Sắp xếp sản phẩm theo giá
- **TC_LoginWithEmptyCredentialsTest**: Test login với credentials trống
- **TC_LoginWithInvalidCredentialsTest**: Test login với credentials sai

### BDD Tests (Cucumber)
- **add_to_cart.feature**: 3 scenarios (positive + negative flows)

### Test Cases Tiêu cực (Negative Testing)
- Login với tài khoản bị khóa
- Login với credentials sai
- Checkout với giỏ hàng trống
- Thêm sản phẩm trùng lặp

## ▶️ Chạy Tests

### Chạy toàn bộ test suite
```bash
mvn clean test
```

### Chạy test cụ thể
```bash
# Chạy class test cụ thể
mvn test -Dtest=TC_LoginTest

# Chạy với TestNG suite
mvn clean test -Dsuite=testng.xml

# Chạy chỉ Cucumber tests
mvn test -Dtest=CucumberTestRunner
```

### Chạy với profile khác (nếu có)
```bash
mvn test -P regression
```

## 📊 Reports

Sau khi chạy test, reports được tạo tại:

- **HTML Report**: `target/surefire-reports/index.html`
- **Text Report**: `test-output/test-report.txt`
- **Cucumber Report**: `target/cucumber-reports/`

## 🔧 Cấu hình nâng cao

### WebDriver Options
Trong `BaseTest.java`, có thể thêm options:
```java
options.addArguments("--headless");        // Chạy không UI
options.addArguments("--incognito");       // Private mode
options.addArguments("--start-maximized"); // Full screen
```

### Timeout Configuration
```java
wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Tăng timeout
```

### Parallel Execution
Trong `testng.xml`:
```xml
<suite name="ParallelSuite" parallel="classes" thread-count="3">
```

## 🏷️ Quy ước đặt tên

### Test Methods
```
test[What]_[Condition]_[ExpectedResult]
Ví dụ: testLogin_WhenValidCredentials_ShouldRedirectToInventory
```

### Page Objects
```
[PageName]Page.java
Ví dụ: InventoryPage.java, CartPage.java
```

### Test Classes
```
TC_[Feature]Test.java
Ví dụ: TC_LoginTest.java, TC_AddToCartTest.java
```

## 📝 Thêm Test Case mới

### 1. Unit Test
```java
@Test(description = "Verify [feature]")
public void test[Feature]_[Condition]_[Expected]() {
    // GIVEN: Setup preconditions
    loginAsStandardUser();

    // WHEN: Perform action
    // ...

    // THEN: Verify result
    // ...
}
```

### 2. BDD Feature
```gherkin
Feature: [Feature name]
  Scenario: [Scenario description]
    Given [Given step]
    When [When step]
    Then [Then step]
```

## 🔍 Troubleshooting

### Common Issues

**1. WebDriver not found**
```bash
# Update WebDriverManager
mvn dependency:purge-local-repository
mvn clean install
```

**2. Tests fail due to timing**
```java
// Tăng timeout trong BaseTest
wait = new WebDriverWait(driver, Duration.ofSeconds(30));
```

**3. Element not found**
```java
// Kiểm tra selector trong Page Object
// Thêm wait conditions
wait.until(ExpectedConditions.elementToBeClickable(By.id("element-id")));
```

## 🤝 Contributing

1. Fork project
2. Tạo feature branch: `git checkout -b feature/new-test-case`
3. Commit changes: `git commit -m 'Add new test case'`
4. Push to branch: `git push origin feature/new-test-case`
5. Create Pull Request

## 📈 Roadmap

- [ ] Thêm screenshot on failure
- [ ] Implement Extent Reports
- [ ] API testing integration
- [ ] CI/CD pipeline (GitHub Actions)
- [ ] Cross-browser testing
- [ ] Performance testing

## 📞 Support

Nếu gặp vấn đề, vui lòng:
1. Kiểm tra logs trong console
2. Verify cấu hình config.properties
3. Kiểm tra WebDriver version
4. Tạo issue với chi tiết lỗi

---

**Version**: 2.0 - Refactored  
**Framework**: Selenium WebDriver + TestNG + Cucumber  
**Language**: Java 21  
**Author**: Automation Tester  
**Last Updated**: 13/04/2026
