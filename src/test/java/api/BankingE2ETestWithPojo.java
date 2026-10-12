//package api;
//
//import org.testng.Assert;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import baseLayer.BaseClass;
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//import pojoForLuxsoft.Customer;
//import pojoForLuxsoft.Transaction;
//
//public class BankingE2ETestWithPojo extends BaseClass {
//
//    private RequestSpecification request;
//
//    static int customerId;
//    static int accountId;
//    static int transactionId;
//
//    @BeforeClass
//    public void setup() {
//
//        RestAssured.baseURI = "http://localhost:8081";
//
//        request = RestAssured.given()
//                .header("Content-Type", "application/json");
//    }
//
//    @Test(priority = 1)
//    public void createCustomer() {
//
//        Customer customer = new Customer();
//
//        customerId = 101;
//
//        customer.setCustomerId(customerId);
//        customer.setName("Sudip");
//        customer.setEmail("sudip@test.com");
//        customer.setPhone("9876543210");
//        customer.setCountry("India");
//
//        Response response = request
//                .body(customer)
//                .post("/customers");
//
//        response.prettyPrint();
//
//        Assert.assertEquals(response.statusCode(), 201);
//
//        System.out.println("Customer ID: " + customerId);
//    }
//
//    @Test(priority = 2, dependsOnMethods = "createCustomer")
//    public void getCustomer() {
//
//        System.out.println("Customer ID from previous API: " + customerId);
//
//        Assert.assertEquals(customerId, 101);
//    }
//
//    @Test(priority = 3, dependsOnMethods = "getCustomer")
//    public void updateCustomer() {
//
//        Customer customer = new Customer();
//
//        customer.setCustomerId(customerId);
//        customer.setName("Sudip Chothe");
//        customer.setEmail("sudip@test.com");
//        customer.setPhone("9999999999");
//        customer.setCountry("India");
//
//        System.out.println("Updating Customer ID: " + customerId);
//
//        Response response = request
//                .body(customer)
//                .put("/customers/" + customerId);
//
//        response.prettyPrint();
//
//        Assert.assertEquals(response.statusCode(), 200);
//    }
//
//    @Test(priority = 4, dependsOnMethods = "updateCustomer")
//    public void createAccount() {
//
//        Account account = new Account();
//
//        account.setCustomerId(customerId);
//        account.setAccountType("SAVINGS");
//        account.setCurrency("INR");
//
//        System.out.println("Creating account for Customer ID: " + customerId);
//
//        Response response = request
//                .body(account)
//                .post("/accounts");
//
//        response.prettyPrint();
//
//        Assert.assertEquals(response.statusCode(), 201);
//
//        accountId = response.jsonPath().getInt("accountId");
//
//        System.out.println("Account ID: " + accountId);
//    }
//
//    @Test(priority = 5, dependsOnMethods = "createAccount")
//    public void getAccount() {
//
//        Response response = request
//                .get("/accounts/" + accountId);
//
//        response.prettyPrint();
//
//        Assert.assertEquals(response.statusCode(), 200);
//
//        Assert.assertEquals(
//                response.jsonPath().getInt("accountId"),
//                accountId
//        );
//
//        Assert.assertEquals(
//                response.jsonPath().getInt("customerId"),
//                customerId
//        );
//    }
//
//    @Test(priority = 6, dependsOnMethods = "getAccount")
//    public void createTransaction() {
//
//        Transaction transaction = new Transaction();
//
//        transaction.setAccountId(accountId);
//        transaction.setTransactionType("DEPOSIT");
//        transaction.setAmount(1000);
//        transaction.setCurrency("INR");
//
//        System.out.println("Creating transaction for Account ID: " + accountId);
//
//        Response response = request
//                .body(transaction)
//                .post("/transactions");
//
//        response.prettyPrint();
//
//        Assert.assertEquals(response.statusCode(), 201);
//
//        transactionId = response.jsonPath().getInt("transactionId");
//
//        System.out.println("Transaction ID: " + transactionId);
//    }
//
//    @Test(priority = 7, dependsOnMethods = "createTransaction")
//    public void getTransaction() {
//
//        Response response = request
//                .get("/transactions/" + transactionId);
//
//        response.prettyPrint();
//
//        Assert.assertEquals(response.statusCode(), 200);
//
//        Assert.assertEquals(
//                response.jsonPath().getInt("transactionId"),
//                transactionId
//        );
//
//        Assert.assertEquals(
//                response.jsonPath().getInt("accountId"),
//                accountId
//        );
//    }
//
//    @Test(priority = 8, dependsOnMethods = "getTransaction")
//    public void verifyAccountBalance() {
//
//        Response response = request
//                .get("/accounts/" + accountId);
//
//        response.prettyPrint();
//
//        Assert.assertEquals(response.statusCode(), 200);
//
//        int balance = response.jsonPath().getInt("balance");
//
//        Assert.assertEquals(balance, 1000);
//    }
//}