package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import baseLayer.BaseClass;

public class BankingE2ETestJsonPayload extends BaseClass {

	private RequestSpecification request;
    static int customerId = 101;
    static int accountId;
    static int transactionId;
    
    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "http://localhost:8081";

        request = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer your-token");
    }


    @Test(priority = 1)
    public void createCustomer() {

        String payload = """
                {
                    "name": "Sudip",
                    "email": "sudip@test.com",
                    "phone": "9876543210",
                    "country": "India"
                }
                """;

        Response response = request
                .body(payload)
                .post("/customers");

        Assert.assertEquals(response.statusCode(), 201);

        customerId = response.jsonPath().getInt("customerId");

        System.out.println("Customer ID: " + customerId);
    }


    @Test(priority = 2, dependsOnMethods = "createCustomer")
    public void getCustomer() {

        Response response = request
                .get("/customers/" + customerId);

        Assert.assertEquals(response.statusCode(), 200);

        Assert.assertEquals(
                response.jsonPath().getInt("customerId"),
                customerId
        );
    }


    @Test(priority = 3, dependsOnMethods = "getCustomer")
    public void updateCustomer() {

        String payload = """
                {
                    "name": "Sudip Chothe",
                    "phone": "9999999999"
                }
                """;

        Response response = request
                .body(payload)
                .put("/customers/" + customerId);

        Assert.assertEquals(response.statusCode(), 200);
    }


    @Test(priority = 4, dependsOnMethods = "updateCustomer")
    public void createAccount() {

        String payload = """
                {
                    "customerId": %d,
                    "accountType": "SAVINGS",
                    "currency": "INR"
                }
                """.formatted(customerId);

        Response response = request
                .body(payload)
                .post("/accounts");

        Assert.assertEquals(response.statusCode(), 201);

        accountId = response.jsonPath().getInt("accountId");

        System.out.println("Account ID: " + accountId);
    }


    @Test(priority = 5, dependsOnMethods = "createAccount")
    public void getAccount() {

        Response response = request
                .get("/accounts/" + accountId);

        Assert.assertEquals(response.statusCode(), 200);

        Assert.assertEquals(
                response.jsonPath().getInt("accountId"),
                accountId
        );

        Assert.assertEquals(
                response.jsonPath().getInt("customerId"),
                customerId
        );
    }


    @Test(priority = 6, dependsOnMethods = "getAccount")
    public void createTransaction() {

        String payload = """
                {
                    "accountId": %d,
                    "transactionType": "DEPOSIT",
                    "amount": 1000,
                    "currency": "INR"
                }
                """.formatted(accountId);

        Response response = request
                .body(payload)
                .post("/transactions");

        Assert.assertEquals(response.statusCode(), 201);

        transactionId =
                response.jsonPath().getInt("transactionId");

        System.out.println("Transaction ID: " + transactionId);
    }


    @Test(priority = 7, dependsOnMethods = "createTransaction")
    public void getTransaction() {

        Response response = request
                .get("/transactions/" + transactionId);

        Assert.assertEquals(response.statusCode(), 200);

        Assert.assertEquals(
                response.jsonPath().getInt("transactionId"),
                transactionId
        );

        Assert.assertEquals(
                response.jsonPath().getInt("accountId"),
                accountId
        );
    }


    @Test(priority = 8, dependsOnMethods = "getTransaction")
    public void verifyAccountBalance() {

        Response response = request
                .get("/accounts/" + accountId);

        Assert.assertEquals(response.statusCode(), 200);

        int balance =
                response.jsonPath().getInt("balance");

        Assert.assertEquals(balance, 1000);
    }
}