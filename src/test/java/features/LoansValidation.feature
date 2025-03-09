Feature: Loan Application Validation

  Scenario Outline: Validate loan application details
    Given Client is authenticated
    Given the client submits the following details
      | idNumber      | firstName  | lastName | age | bankName       | accountNumber |
      | <idNumber>    | <firstName>| <lastName>| <age> | <bankName> | <accountNumber> |
    When the client applies for a loan
    Then the response code should be <responseCode>
    And the validation status should be "<validationStatus>"
#    And the response should contain error message "<errorMessage>"
#    And the response should contain warning message "<warningMessage>"

    Examples:
      | idNumber      | firstName     | lastName | age | bankName         | accountNumber | responseCode | validationStatus | errorMessage | warningMessage |
      | 9901025391084 | Pontsho  | Molewa  | 25  | FNB              | 1234567890    | 201        | true             |              |               |
      | 1001015009087 | Pontshos | Molewa  | 17  | ABSA             | 1234567890    | 422         | false            | The client must be 18 years or older | |
      | 9901025391084 | Pontsho | Molewa  | 25  | STANDARD_BANK    | 1234567890    | 201         | true            | Name must not have any special characters and digits | |
      | 9901025391084 | Pontsho  | Molewa2 | 25  | CAPITEC          | 1234567890    | 201          | true            | Surname must not have any special characters and digits | |
      | 9901025391084 | Pontsho  | Molewa  | 25  | INVESTEC_LIMITED | 1234567890    | 201          | true             |              | Refer to compliance |
      | 9901025391084 | Pontsho  | Molewa  | 25  | VBS             | 12345678902   | 422          | false            | Bank account number must be 10 digits | |
