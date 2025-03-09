package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
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
    private String authToken;
    String requestBody1;

    @Given("Client is authenticated")
    public void client_is_authenticated() {
        RestAssured.baseURI = "http://localhost:8080";

        Response authResponse = given()
                .contentType("application/json")
                .body("{ \"emailAddress\": \"kmaxkondile@gmail.com\", \"password\": \"1234567\" }")
                .post("/users/login");

        authToken = authResponse.jsonPath().getString("jwt");
    }



    @Given("the client submits the following details")
    public void the_client_submits_the_following_details(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> detailsList = dataTable.asMaps(String.class, String.class);
        requestBody = detailsList.get(0);

         requestBody1= String.format(
                "{\"bankAccount\": {\"accountNumber\": %s, \"bankName\": \"%s\"}, \"idNumber\": %s, \"firstName\": \"%s\", \"lastName\": \"%s\"}",
                requestBody.get("accountNumber"),
                requestBody.get("bankName"),
                requestBody.get("idNumber"),
                requestBody.get("First name"),
                requestBody.get("Last name")
        );
    }

    @When("the client applies for a loan")
    public void the_client_applies_for_a_loan() {
        RestAssured.baseURI="http://localhost:8080";
        response = given()
                .contentType("application/json").header("Authorization", "Bearer "+ authToken)
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
        if (response == null) {
            Assert.fail("Response is null. Check if the API request was successful.");
        }

        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);  // Debugging

        JsonPath jsonPath = new JsonPath(responseBody);

        // Check if the "approved" field exists before accessing it
        if (!jsonPath.getMap("").containsKey("approved")) {
            Assert.fail("Response does not contain the 'approved' field.");
        }

        boolean actualStatus = jsonPath.getBoolean("approved");
        boolean expectedBoolean = Boolean.parseBoolean(validationStatus);

        Assert.assertEquals(actualStatus, expectedBoolean, "Validation status mismatch!");
    }

    @Then("the response should contain error message {string}")
    public void the_response_should_contain_error_message(String errorMessage) {
        List<String> errors = response.jsonPath().getList("errors");
        if (!errorMessage.isEmpty()) {
            assertTrue(errors.contains(errorMessage), "Error message not found in the response.");
        } else {
            assertTrue(errors.isEmpty(), "Expected no error messages but found some.");
        }
    }

    @Then("the response should contain warning message {string}")
    public void the_response_should_contain_warning_message(String warningMessage) {
        List<String> warnings = response.jsonPath().getList("warnings");
        if (!warningMessage.isEmpty()) {
            assertTrue(warnings.contains(warningMessage), "Warning message not found in the response.");
        } else {
            assertTrue(warnings.isEmpty(), "Expected no warning messages but found some.");
        }
    }

}
