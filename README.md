# Hybrid Test Automation Framework — Selenium, Java, TestNG

A hybrid UI automation framework built with **Java, Selenium WebDriver 4, TestNG and Maven**, combining the Page Object Model with data-driven test design.

Test application: [automationexercise.com](https://automationexercise.com/) — a public e-commerce practice site.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Automation | Selenium WebDriver 4.23 |
| Test Runner | TestNG 7.10 |
| Build Tool | Maven |
| Driver Management | WebDriverManager 5.8 |
| Test Data | Jackson (JSON → POJO), Properties files |
| Data Generation | JavaFaker |
| Logging | Log4j2 |

---

## Key Features

**Page Object Model** — each page is a class owning its own locators and actions, so a UI change is fixed in one place rather than across every test.

**Thread-safe driver management** — `WebDriver` instances are held in a `ThreadLocal`, initialised per browser and removed on teardown. This isolates parallel threads and avoids the stale-reference leak that occurs when TestNG reuses pooled threads.

**Cross-browser support** — Chrome and Edge, selected via TestNG XML parameters or `config.properties`. Driver binaries resolved automatically by WebDriverManager, so no manual downloads or version pinning.

**Headless execution** — toggled by a single config flag, with an explicit window size set to keep viewport-dependent elements interactable.

**Three-tier test data strategy**
- `.properties` files for flat key-value data (credentials)
- `.json` files deserialised into POJOs via Jackson for structured data (user profiles, products)
- JavaFaker for data that must be unique per run — the registration flow requires a fresh email each execution

**File download validation** — the download directory is configured through `ChromeOptions`, and a custom polling utility waits for the file to appear, confirms it is no longer a partial `.crdownload`, and verifies a non-zero size before asserting.

**Structured logging** — Log4j2 traces each step to the console and to `logs/execution.log`.

---

## Project Structure

```
src/main/java/
├── driverFactory/       DriverManager — ThreadLocal WebDriver, browser setup
├── Readers/             configReader, userReader, productReader
├── Utils/               waitUtils, actionUtils, JsonUtil, DataGeneratorUtil
└── testdata/            UserData, ProductData, BankData (POJOs)

src/test/java/
├── Base/                baseTest — setup and teardown
├── pageObjects/         HomePage, LoginPage, ProductsPage, CartPage,
│                        CheckoutPage, PaymentPage, OrderConfirmationPage
└── tests/               E2EDataDrivenTest, LoginTest, RegisterUserTest

Resources/
├── config.properties    base URL, browser, waits, headless flag
├── users.json           user profiles
├── products.json        product catalogue
├── log4j2.xml           logging configuration
└── testng/              suite definitions
```

---

## Test Scenarios

### End-to-End Purchase Flow (`E2EDataDrivenTest`)

A complete customer journey, driven entirely from external data:

1. **Login** — credentials read from `users.json`, verified against the logged-in state
2. **Add products by search** — search the catalogue and add matching items to the cart
3. **Add products by filter** — navigate the category hierarchy and add filtered items
4. **Verify cart and delivery address** — asserts the checkout address matches the registered user profile, with whitespace normalisation to handle formatting differences between the registration form and the checkout display
5. **Place order and pay** — completes payment and validates the confirmation messages
6. **Download invoice and log out** — verifies the invoice file lands on disk fully written

### User Registration (`RegisterUserTest`)
Registers a brand-new account using a JavaFaker-generated profile, so the test is repeatable without manual data cleanup.

### Login (`LoginTest`)
Standalone login and logout validation using properties-based test data.

---

## Running the Tests

**Prerequisites:** Java 17+, Maven 3.6+, Chrome or Edge installed

```bash
# Clone
git clone https://github.com/Raman9/Hybrid-AutomationFramework-Using-Selenium-Java-TestNG.git
cd Hybrid-AutomationFramework-Using-Selenium-Java-TestNG

# Run the full end-to-end suite
mvn clean test -DsuiteXmlFile=Resources/testng/testng-E2EDataDriven.xml

# Run the registration suite
mvn clean test -DsuiteXmlFile=Resources/testng/testng-RegisterUser.xml
```

**Headless mode** — uncomment in `Resources/config.properties`:
```properties
headless=true
```

**Configuration** (`Resources/config.properties`):
```properties
base.url=https://automationexercise.com/
browser=chrome
```

**Reports** are generated in `test-output/` after each run. Execution logs are written to `logs/execution.log`.

---

## Design Decisions

**Why explicit waits over implicit** — implicit waits only poll for element *presence* in the DOM, which is not the same as being interactable. Mixing the two also produces unpredictable timeout behaviour, since each poll inside an explicit wait can itself block for the implicit duration. Waits are handled through `waitUtils` so the strategy stays consistent and visible in the code.

**Why elements are re-located on dynamic pages** — the products page renders tiles asynchronously and reveals the Add-to-Cart control on hover. Holding a `WebElement` reference across that re-render risks `StaleElementReferenceException`, so the product list is re-fetched at the point of use rather than cached.

**Why polling for downloads instead of a fixed delay** — a fixed wait is simultaneously too slow on fast runs and too short on slow ones. Polling for the file's existence, completeness, and size returns as soon as the condition is genuinely met.

**Why JSON POJOs over raw maps** — type safety. `user.email` is checked at compile time; a map lookup with a typo becomes a runtime null in an unrelated place.

---

## Planned Enhancements

- **Screenshot capture on failure** via `ITestListener`, attached to the report
- **Extent Reports / Allure** for richer reporting than the TestNG default
- **CI/CD integration** with GitHub Actions — headless execution on push, with reports published as build artifacts
- **Independent test design** — replacing the current `dependsOnMethods` chain with tests that establish their own state via API setup, so a single failure no longer cascades into skipped tests
- **Locator strategy migration** — moving from PageFactory `@FindBy` fields to `By` constants resolved at point of use, removing the stale-element risk at its source rather than working around it
- **Retry analyzer** with visible retry counts, so intermittent failures surface in reporting instead of being silently masked

---

## Author

**Ramanpreet Singh** — Quality Engineer
[LinkedIn](https://linkedin.com/in/raman-qa) · [GitHub](https://github.com/Raman9)
