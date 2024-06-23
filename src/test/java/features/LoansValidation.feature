Feature: Loan Application Validation

  Scenario Outline: Validate loan application details
    Given the client submits the following details
      | idNumber      | name  | surname | age | bankName       | accountNumber |
      | <idNumber>    | <name>| <surname>| <age> | <bankName> | <accountNumber> |
    When the client applies for a loan
    Then the response code should be <responseCode>
    And the validation status should be "<validationStatus>"
    And the response should contain error message "<errorMessage>"
    And the response should contain warning message "<warningMessage>"

    Examples:
      | idNumber      | name     | surname | age | bankName        | accountNumber | responseCode| validationStatus | errorMessage| warningMessage|
      | 9901025391084 | Pontsho     | Molewa     | 25  | SCRUM_BANK  | 1234567890    | 200         | true             |             |               |
      | 1001015009087 | Pontshos    | Molewa     | 17  | ICONIC_BANK| 1234567890    | 200         | false            | The client must be 18 years or older| |
      | 9901025391084 | Pontsho!    | Molewa     | 25  | SCRUM_BANK | 1234567890    | 200         | false            | Name must not have any special characters and digits||
      | 9901025391084 | Pontsho     | Molewa2   | 25  | SCRUM_BANK  | 1234567890    | 200         | false            | Surname must not have any special characters and digits||
      | 9901025391084 | Pontsho     | Molewa    | 25  | MOLEWA_BANK | 1234567890    | 200         | true            |                                                   | Refer to compliance   |
      | 9901025391084 | Pontsho     | Molewa     | 25  | SCRUM_BANK  | 12345678902   | 200         | false            | Bank account number must be 10 digits                  |                       |
