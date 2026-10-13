package TestLayer;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

import baseLayer.BaseClass;
import configLayer.Log;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WireMockServerTest extends BaseClass {

	private WireMockServer server;

	@BeforeMethod
	public void setup() {

		server = new WireMockServer(9090);

		server.start();

		System.out.println("WireMock Started: " + server.isRunning());
	}

	@AfterMethod
	public void tearDown() {

		if (server != null && server.isRunning()) {

			server.stop();
		}
	}

	@Test
	public void postCall() {

		RestAssured.baseURI = "http://localhost:9090";

		String payload = """
				{
				  "id": 101,
				  "firstName": "Sudip",
				  "lastName": "Chothe",
				  "email": "sudip.chothe@gmail.com",
				  "mobile": "9876543210",
				  "department": "QA Automation",
				  "salary": 85000,
				  "city": "Mumbai",
				  "status": "Employee Created Successfully",
				  "createdAt": "2026-05-19T10:45:30"
				}
				""";

		server.stubFor(
				post(urlEqualTo("/employees"))
						.willReturn(
								aResponse()
										.withStatus(201)
										.withHeader("Content-Type", "application/json")
										.withBody(payload)));

		RequestSpecification httpRequest = RestAssured
				.given()
				.contentType(ContentType.JSON)
				.body(payload);

		Response response = httpRequest.post("/employees");

		response.prettyPrint();

		Log.assertEquals(
				"Verify Status Code",
				response.getStatusCode(),
				201);

		Log.assertTrue(
				"Verify Status Line",
				response.getStatusLine().contains("201"));

		Log.assertEquals(
				"Verify Content Type",
				response.getContentType(),
				"application/json");

		Log.assertEquals(
				"Verify ID",
				response.jsonPath().getInt("id"),
				101);

		Log.assertEquals(
				"Verify First Name",
				response.jsonPath().getString("firstName"),
				"Sudip");

		Log.assertEquals(
				"Verify Last Name",
				response.jsonPath().getString("lastName"),
				"Chothe");

		Log.assertEquals(
				"Verify Email",
				response.jsonPath().getString("email"),
				"sudip.chothe@gmail.com");

		Log.assertEquals(
				"Verify Mobile",
				response.jsonPath().getString("mobile"),
				"9876543210");

		Log.assertEquals(
				"Verify Department",
				response.jsonPath().getString("department"),
				"QA Automation");

		Log.assertEquals(
				"Verify Salary",
				response.jsonPath().getInt("salary"),
				85000);

		Log.assertEquals(
				"Verify City",
				response.jsonPath().getString("city"),
				"Mumbai");

		Log.assertEquals(
				"Verify Status",
				response.jsonPath().getString("status"),
				"Employee Created Successfully");
	}

	@Test
	public void getCall() {

		RestAssured.baseURI = "http://localhost:9090";

		String payload = """
				{
				  "id": 101,
				  "firstName": "Sudip",
				  "lastName": "Chothe",
				  "email": "sudip.chothe@gmail.com",
				  "mobile": "9876543210",
				  "department": "QA Automation",
				  "salary": 85000,
				  "city": "Mumbai",
				  "status": "Employee Created Successfully",
				  "createdAt": "2026-05-19T10:45:30"
				}
				""";

		server.stubFor(get(urlEqualTo("/employees/3030")).willReturn(
				aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(payload)));

		RequestSpecification httpRequest = RestAssured.given();

		Response response = httpRequest.get("/employees/3030");

		response.prettyPrint();

		Log.assertEquals(
				"Verify Status Code",
				response.getStatusCode(),
				200);

		Log.assertTrue(
				"Verify Status Line",
				response.getStatusLine().contains("200"));

		Log.assertEquals(
				"Verify Content Type",
				response.getContentType(),
				"application/json");

		Log.assertEquals(
				"Verify ID",
				response.jsonPath().getInt("id"),
				101);

		Log.assertEquals(
				"Verify First Name",
				response.jsonPath().getString("firstName"),
				"Sudip");

		Log.assertEquals(
				"Verify Last Name",
				response.jsonPath().getString("lastName"),
				"Chothe");

		Log.assertEquals(
				"Verify Email",
				response.jsonPath().getString("email"),
				"sudip.chothe@gmail.com");

		Log.assertEquals(
				"Verify Mobile",
				response.jsonPath().getString("mobile"),
				"9876543210");

		Log.assertEquals(
				"Verify Department",
				response.jsonPath().getString("department"),
				"QA Automation");

		Log.assertEquals(
				"Verify Salary",
				response.jsonPath().getInt("salary"),
				85000);

		Log.assertEquals(
				"Verify City",
				response.jsonPath().getString("city"),
				"Mumbai");

		Log.assertEquals(
				"Verify Status",
				response.jsonPath().getString("status"),
				"Employee Created Successfully");
	}
}

