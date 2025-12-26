# Selenium Hybrid Framework

A robust, thread-safe automated testing framework built with **Java**, **Selenium WebDriver**, and **TestNG**. Designed for scalability, parallel execution, and rich reporting.

## 🚀 Key Features

* **Parallel Execution:** Implements `ThreadLocal` for thread-safe parallel browser testing.
* **Dynamic Reporting:** Integrated **ExtentReports** with automatic pass/fail logging and screenshots.
* **Smart Download Handling:** Automated verification of file downloads using custom polling logic.
* **Driver Factory:** Centralized browser initialization supporting Chrome (Headless/GUI) and Firefox.
* **CI/CD Ready:** Configured for seamless execution on  GitHub Actions.
* **Data Driven:** Supports external data inputs via JSON.

## 🛠️ Tech Stack

* **Language:** Java (JDK 11+)
* **Automation:** Selenium WebDriver
* **Test Runner:** TestNG
* **Build Tool:** Maven
* **Reporting:** ExtentReports 5
* **Version Control:** Git

## 🧩 Framework Design
- Page Object Model (POM)
- Driver Factory with ThreadLocal
- TestNG Listeners for reporting
- Maven for dependency management

## 📂 Project Structure

```text
src/test/java
├── com.company.tests       # Test Scripts (@Test)
├── com.company.base        # BaseTest (Setup/Teardown)

src/main/java
├── com.company.driver      # ThreadLocal Driver Logic
├── com.company.pages       # Page Object Model (Locators & Actions)
├── com.company.reports     # ExtentManager Configuration
└── com.company.utils       # Utilities (File handling, Waits)
└── com.company.listeners   # TestNG Listeners
