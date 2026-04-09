# MessengerAutomation

Automation test framework for Messenger using Selenium WebDriver, TestNG, Maven, and WebDriverManager.

## Structure

```text
MessengerAutomation/
├── pom.xml
├── testng.xml
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── messenger/
│   │               └── automation/
│   │                   ├── base/
│   │                   │   └── BaseTest.java
│   │                   ├── pages/
│   │                   │   ├── LoginPage.java
│   │                   │   └── ChatPage.java
│   │                   └── utils/
│   │                       └── ConfigReader.java
│   └── test/
│       └── java/
│           └── com/
│               └── messenger/
│                   └── automation/
│                       └── tests/
│                           ├── TC008_SendTextMessageTest.java
│                           ├── TC023_SendEmptyMessageTest.java
│                           └── TC025_SendOneCharacterTest.java
└── .github/
    └── workflows/
        └── maven.yml
```

## Run tests

```bash
mvn clean test
```

## Notes

- Current locators are placeholders and should be replaced with stable Messenger locators.
- Tests assume the user is already authenticated and is on the correct chat screen.
- Running UI tests in GitHub Actions may require additional browser setup or headless configuration.
