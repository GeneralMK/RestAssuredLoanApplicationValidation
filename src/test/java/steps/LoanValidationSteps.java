package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class LoanValidationSteps {

    private Map<String, String> requestBody;
    private Response response;

    String requestBody1;

    @Given("the client submits the following details")
    public void the_client_submits_the_following_details(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);

        List<Map<String, String>> detailsList = dataTable.asMaps(String.class, String.class);
        requestBody = detailsList.get(0);
        // Create the request body
         requestBody1= String.format(
                "{\"bankAccount\": {\"accountNumber\": %s, \"bankName\": \"%s\"}, \"idNumber\": %s, \"name\": \"%s\", \"surname\": \"%s\"}",
                requestBody.get("accountNumber"),
                requestBody.get("bankName"),
                requestBody.get("idNumber"),
                requestBody.get("name"),
                requestBody.get("surname")
        );// Assuming there's only one row of data
    }

    @When("the client applies for a loan")
    public void the_client_applies_for_a_loan() {
        RestAssured.baseURI="http://localhost:8080";
        response = given()
                .contentType("application/json")
                .body(requestBody1)
                .post("/loans");
        response.getBody().prettyPrint();
    }

    @Then("the response code should be {int}")
    public void the_response_code_should_be(Integer expectedCode) {
        assertEquals(response.getStatusCode(), expectedCode.intValue());
    }


    @Then("the validation status should be {string}")
    public void the_validation_status_should_be(String validationStatus) {
        boolean status = response.jsonPath().getBoolean("validationStatus");
        System.out.println("Expected Validation Status: " + validationStatus);
        System.out.println("Actual Validation Status: " + status);
        if (validationStatus.equalsIgnoreCase("true")) {
            assertTrue(status);
        } else {
            assertFalse(status);
        }
    }

    @Then("the response should contain error message {string}")
    public void the_response_should_contain_error_message(String errorMessage) {
        List<String> errors = response.jsonPath().getList("errors");
        if (!errorMessage.isEmpty()) {
            System.out.println("Expected Error Message: " + errorMessage);
            System.out.println("Actual Error Messages: " + errors);
            assertTrue(errors.contains(errorMessage), "Error message not found in the response.");
        } else {
            assertTrue(errors.isEmpty(), "Expected no error messages but found some.");
        }
    }

    @Then("the response should contain warning message {string}")
    public void the_response_should_contain_warning_message(String warningMessage) {
        List<String> warnings = response.jsonPath().getList("warnings");
        System.out.println("Expected Warning Message: " + warningMessage);
        System.out.println("Actual Warning Messages: " + warnings);
        if (!warningMessage.isEmpty()) {
            assertTrue(warnings.contains(warningMessage), "Warning message not found in the response.");
        } else {
            assertTrue(warnings.isEmpty(), "Expected no warning messages but found some.");
        }
    }

}
