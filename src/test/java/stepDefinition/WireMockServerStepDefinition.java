package stepDefinition;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import java.net.URI;

import com.github.tomakehurst.wiremock.WireMockServer;

import baseLayer.BaseClass;
import configLayer.ConfigReader;
import configLayer.Log;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WireMockServerStepDefinition extends BaseClass {

	private WireMockServer server;

	private String payload = """

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

	// ============================================================
	// WIREMOCK START
	// ============================================================

	@Before("@WireMock")
	public void startWireMock() {

		String wireMockURI = ConfigReader.get("wireMockURI");

		try {

			URI uri = URI.create(wireMockURI);

			int port = uri.getPort();

			if (port == -1) {
				throw new IllegalArgumentException("Port not found in wireMockURI: " + wireMockURI);
			}

			server = new WireMockServer(port);

			server.start();

			Log.info("WireMock Started: " + server.isRunning());
			Log.info("WireMock URI: " + wireMockURI);

		} catch (Exception e) {

			Log.error("Failed to start WireMock: " + e.getMessage());

			throw e;
		}
	}

	// ============================================================
	// WIREMOCK STOP
	// ============================================================

	@After("@WireMock")
	public void stopWireMock() {

		if (server != null && server.isRunning()) {

			server.stop();

			Log.info("WireMock Stopped: " + !server.isRunning());
		}
	}

	// ============================================================
	// POST
	// ============================================================

	@Given("I have stubbed the POST employee API")
	public void stubPOSTEmployeeAPI() {

		server.stubFor(

				post(urlEqualTo("/employees"))

						.willReturn(

								aResponse()

										.withStatus(201)

										.withHeader("Content-Type", "application/json")

										.withBody(payload)

						));

		Log.info("POST /employees API stubbed successfully");
	}

	@When("I send a POST request to {string}")
	public void sendPOSTRequest(String endpoint) {

		BaseClass.initializeWireMockServerApiRequest();

		BaseClass.setRequestBody(payload);

		BaseClass.setPathParams(endpoint);

		BaseClass.setRequestMethod("POST");
	}

	@Then("I verify the POST employee response")
	public void verifyPOSTEmployeeResponse() {

		Log.assertEquals("Verify Status Code", BaseClass.getResponse().getStatusCode(), 201);

		Log.assertTrue("Verify Status Line", BaseClass.getResponse().getStatusLine().contains("201"));

		Log.assertEquals("Verify Content Type", BaseClass.getResponse().getContentType(), "application/json");

		Log.assertEquals("Verify ID", BaseClass.getResponse().jsonPath().getInt("id"), 101);

		Log.assertEquals("Verify First Name", BaseClass.getResponse().jsonPath().getString("firstName"), "Sudip");

		Log.assertEquals("Verify Last Name", BaseClass.getResponse().jsonPath().getString("lastName"), "Chothe");

		Log.assertEquals("Verify Email", BaseClass.getResponse().jsonPath().getString("email"),
				"sudip.chothe@gmail.com");

		Log.assertEquals("Verify Mobile", BaseClass.getResponse().jsonPath().getString("mobile"), "9876543210");

		Log.assertEquals("Verify Department", BaseClass.getResponse().jsonPath().getString("department"),
				"QA Automation");

		Log.assertEquals("Verify Salary", BaseClass.getResponse().jsonPath().getInt("salary"), 85000);

		Log.assertEquals("Verify City", BaseClass.getResponse().jsonPath().getString("city"), "Mumbai");

		Log.assertEquals("Verify Status", BaseClass.getResponse().jsonPath().getString("status"),
				"Employee Created Successfully");
	}

	// ============================================================
	// GET
	// ============================================================

	@Given("I have stubbed the GET employee API")
	public void stubGETEmployeeAPI() {

		server.stubFor(

				get(urlEqualTo("/employees/3030"))

						.willReturn(

								aResponse()

										.withStatus(200)

										.withHeader("Content-Type", "application/json")

										.withBody(payload)

						));

		Log.info("GET /employees/3030 API stubbed successfully");
	}

	@When("I send a GET request to {string}")
	public void sendGETRequest(String endpoint) {

		BaseClass.initializeWireMockServerApiRequest();

		BaseClass.setPathParams(endpoint);

		BaseClass.setRequestMethod("GET");
	}

	@Then("I verify the GET employee response")
	public void verifyGETEmployeeResponse() {

		Log.assertEquals("Verify Status Code", BaseClass.getResponse().getStatusCode(), 200);

		Log.assertTrue("Verify Status Line", BaseClass.getResponse().getStatusLine().contains("200"));

		Log.assertEquals("Verify Content Type", BaseClass.getResponse().getContentType(), "application/json");

		Log.assertEquals("Verify ID", BaseClass.getResponse().jsonPath().getInt("id"), 101);

		Log.assertEquals("Verify First Name", BaseClass.getResponse().jsonPath().getString("firstName"), "Sudip");

		Log.assertEquals("Verify Last Name", BaseClass.getResponse().jsonPath().getString("lastName"), "Chothe");

		Log.assertEquals("Verify Email", BaseClass.getResponse().jsonPath().getString("email"),
				"sudip.chothe@gmail.com");

		Log.assertEquals("Verify Mobile", BaseClass.getResponse().jsonPath().getString("mobile"), "9876543210");

		Log.assertEquals("Verify Department", BaseClass.getResponse().jsonPath().getString("department"),
				"QA Automation");

		Log.assertEquals("Verify Salary", BaseClass.getResponse().jsonPath().getInt("salary"), 85000);

		Log.assertEquals("Verify City", BaseClass.getResponse().jsonPath().getString("city"), "Mumbai");

		Log.assertEquals("Verify Status", BaseClass.getResponse().jsonPath().getString("status"),
				"Employee Created Successfully");
	}
}


