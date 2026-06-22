# RestAssured Loan Application Validation

This project is an API test automation framework built with **Java**, **Rest Assured**, **Cucumber**, **TestNG**, and **Extent Reports**.
It validates loan application business rules by sending API requests, checking response status codes, and verifying validation messages returned by the backend service.

## Project Overview

The framework tests a loan application API by covering both positive and negative validation scenarios.
It uses BDD-style feature files to define test cases in a readable format and Java step definitions to execute API calls and assertions.

## Tech Stack

* Java
* Rest Assured
* Cucumber
* TestNG
* Maven
* Extent Reports
* GitHub Actions

## Key Features

* API authentication before submitting loan applications
* Data-driven testing using Cucumber Scenario Outlines
* Validation of response status codes
* Validation of approval status from API responses
* Error message validation for failed loan applications
* Warning message validation where applicable
* Custom Extent Report listener
* GitHub Actions pipeline support
* Maven-based project structure

## Business Rules Covered

The test scenarios validate rules such as:

* Client must be authenticated before applying for a loan
* Client must be 18 years or older
* First name must not contain special characters
* Last name must not contain special characters
* Bank name validation
* Bank account number length validation
* Compliance referral handling for specific business rules

## Project Structure

```text
RestAssuredLoanApplicationValidation
│
├── .github/workflows        # GitHub Actions pipeline configuration
├── src
│   ├── main/java
│   │   └── utils
│   │       └── ExtentReportListener.java
│   │
│   └── test/java
│       ├── features
│       │   └── LoansValidation.feature
│       │
│       ├── runner
│       │   └── TestRunner.java
│       │
│       └── steps
│           └── LoanValidationSteps.java
│
├── extent-report.html       # Generated Extent Report
├── loan.html                # Test report output
├── pom.xml                  # Maven dependencies and build config
└── .gitignore
```

## Test Scenario Example

```gherkin
Feature: Loan Application Validation

  Scenario Outline: Validate loan application details
    Given Client is authenticated
    Given the client submits the following details
      | idNumber   | firstName   | lastName   | age   | bankName   | accountNumber   |
      | <idNumber> | <firstName> | <lastName> | <age> | <bankName> | <accountNumber> |
    When the client applies for a loan
    Then the response code should be <responseCode>
    And the validation status should be "<validationStatus>"
```

## How to Run the Tests

### Prerequisites

Make sure the following are installed:

* Java JDK 8 or higher
* Maven
* Git
* IDE such as IntelliJ IDEA or Eclipse

### Clone the Repository

```bash
git clone https://github.com/GeneralMK/RestAssuredLoanApplicationValidation.git
cd RestAssuredLoanApplicationValidation
```

### Run Tests with Maven

```bash
mvn clean test
```

## Reports

After test execution, the framework generates an Extent Report.

Report files include:

```text
extent-report.html
loan.html
```

Open the generated HTML report in a browser to view the test execution results.

## CI/CD

This project includes GitHub Actions support for automated test execution.
The pipeline can be used to run the API regression suite automatically on code changes.

## Author

**Masixole Kondile**
