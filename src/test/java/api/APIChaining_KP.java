package api;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIChaining_KP {

	private RequestSpecification request;

	static String token;
	static int customerId;
	static int orderId;

	@BeforeClass
	public void setup() {

		RestAssured.baseURI = "http://localhost:8081";

		request = RestAssured.given().header("Content-Type", "application/json");
	}

	@Test(priority = 1)
	public void login() {

		String loginPayload = "{" + "\"username\":\"sudip\"," + "\"password\":\"password123\"" + "}";

		Response response = request.body(loginPayload).post("/login");

		response.prettyPrint();

		Assert.assertEquals(response.statusCode(), 201);

		token = response.jsonPath().getString("token");

		customerId = response.jsonPath().getInt("customerId");

		System.out.println("Token: " + token);
		System.out.println("Customer ID: " + customerId);
	}

	@Test(priority = 2, dependsOnMethods = "login")
	public void getCustomer() {

		Response response = request.header("Authorization", "Bearer " + token).get("/customers/" + customerId);

		response.prettyPrint();

		Assert.assertEquals(response.statusCode(), 200);

		Assert.assertEquals(response.jsonPath().getInt("customerId"), customerId);
	}

	@Test(priority = 3, dependsOnMethods = "getCustomer")
	public void createOrder() {

		String orderPayload = "{" + "\"customerId\":" + customerId + "," + "\"product\":\"Laptop\"," + "\"quantity\":1,"
				+ "\"amount\":50000" + "}";

		Response response = request.header("Authorization", "Bearer " + token).body(orderPayload).post("/orders");

		response.prettyPrint();

		Assert.assertEquals(response.statusCode(), 201);

		orderId = response.jsonPath().getInt("orderId");

		System.out.println("Order ID: " + orderId);
	}

	@Test(priority = 4, dependsOnMethods = "createOrder")
	public void getOrder() {

		Response response = request.header("Authorization", "Bearer " + token).get("/orders/" + orderId);

		response.prettyPrint();

		Assert.assertEquals(response.statusCode(), 200);

		Assert.assertEquals(response.jsonPath().getInt("orderId"), orderId);

		Assert.assertEquals(response.jsonPath().getInt("customerId"), customerId);
	}

	@Test(priority = 5, dependsOnMethods = "getOrder")
	public void deleteOrder() {

		Response response = request.header("Authorization", "Bearer " + token).delete("/orders/" + orderId);

		response.prettyPrint();

		Assert.assertEquals(response.statusCode(), 204);

		System.out.println("Order deleted: " + orderId);
	}
}