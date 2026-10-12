
package baseLayer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;

import configLayer.ConfigReader;
import configLayer.Log;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilsLayer.DBUtils;

public class BaseClass {

	// ================= DRIVER =================
	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public static void setDriver(WebDriver drv) {
		driver.set(drv);
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void unloadDriver() {
		driver.remove();
	}

	// ================= BROWSER =================
	private static final ThreadLocal<String> browser = new ThreadLocal<>();

	public static void setBrowser(String browserName) {
		browser.set(browserName);
	}

	public static String getBrowser() {
		return browser.get();
	}

	public static void unloadBrowser() {
		browser.remove();
	}

	// ================= UNLOAD ALL =================
	public static void unloadAll() {
		unloadDriver();
		unloadBrowser();
	}

	// ================= CLEANUP ALL RESOURCES =================
	public static void cleanup() {

		try {
			WebDriver currentDriver = getDriver();

			if (currentDriver != null) {
				currentDriver.quit();
				Log.info("Browser closed successfully");
			}

		} catch (Exception e) {
			Log.info("Browser cleanup failed: " + e.getMessage());

		} finally {
			try {
				DBUtils.disconnectFromDB();
			} catch (Exception e) {
				Log.info("Database cleanup failed: " + e.getMessage());

			} finally {
				try {
					clear();
				} finally {
					try {
						unloadAll();
					} finally {
						ThreadContext.clearAll();
					}
				}
			}
		}
	}

	// ================= THREADCONTEXT / LOGGING =================
	public static void setBrowserContext(String browserName) {
		if (browserName == null || browserName.isEmpty()) {
			ThreadContext.put("browser", "REST");
		} else {
			ThreadContext.put("browser",
					browserName.substring(0, 1).toUpperCase() + browserName.substring(1).toLowerCase());
		}
	}

	public static void initRestLogging() {
		ThreadContext.put("browser", "REST");
	}

	public static void clearRestLogging() {
		ThreadContext.remove("browser");
	}

	// ====================== REST ASSURED ======================
	private static final ThreadLocal<RequestSpecification> httpRequest = new ThreadLocal<>();

	private static final ThreadLocal<Response> response = new ThreadLocal<>();

	private static final ThreadLocal<ByteArrayOutputStream> requestOutputStream = new ThreadLocal<>();

	private static final ThreadLocal<ByteArrayOutputStream> responseOutputStream = new ThreadLocal<>();

	private static final ThreadLocal<String> pathParamEndpoint = new ThreadLocal<>();

	public static void initializeApiRequest() {
		initializeRequest(ConfigReader.get("baseURI"));
		Log.info("API request initialized");
	}

	public static void initializeWireMockServerApiRequest() {
		initializeRequest(ConfigReader.get("wireMockURI"));
		Log.info("WireMock API request initialized");
		Log.info("WireMock URI: " + ConfigReader.get("wireMockURI"));
	}

	private static void initializeRequest(String baseURI) {
		ByteArrayOutputStream reqStream = new ByteArrayOutputStream();
		ByteArrayOutputStream resStream = new ByteArrayOutputStream();

		requestOutputStream.set(reqStream);
		responseOutputStream.set(resStream);

		PrintStream reqPrintStream = new PrintStream(reqStream);
		PrintStream resPrintStream = new PrintStream(resStream);

		RequestLoggingFilter requestFilter = new RequestLoggingFilter(LogDetail.ALL, reqPrintStream);

		ResponseLoggingFilter responseFilter = new ResponseLoggingFilter(LogDetail.ALL, resPrintStream);

		RequestSpecification request = RestAssured.given().baseUri(baseURI).contentType(ContentType.JSON)
				.filter(requestFilter).filter(responseFilter);

		httpRequest.set(request);
		response.remove();
		pathParamEndpoint.remove();
	}

	public static void setHeaders(Map<String, String> headers) {
		if (headers != null && !headers.isEmpty()) {
			httpRequest.get().headers(headers);
		}
		Log.info("Request headers set");
	}

	public static void setRequestBody(Object body) {
		if (body != null) {
			httpRequest.get().body(body);
		}
		Log.info("Request body set");
	}

	public static void setQueryParams(String key, String value) {
		if (key != null && value != null) {
			httpRequest.get().queryParam(key, value);
		}
		Log.info("Query parameter set: " + key + " = " + value);
	}

	public static void setPathParams(String endpoint) {
		if (endpoint != null && !endpoint.isEmpty()) {
			pathParamEndpoint.set(endpoint);
		}
		Log.info("API endpoint set: " + endpoint);
	}

	public static Response setRequestMethod(String type) {
		String endpoint = pathParamEndpoint.get();

		if (endpoint == null || endpoint.isEmpty()) {
			throw new IllegalArgumentException("Endpoint not set");
		}

		RequestSpecification request = httpRequest.get();

		if (request == null) {
			throw new IllegalStateException("API request not initialized. Call initializeApiRequest() first.");
		}

		Log.info("Executing request: " + type.toUpperCase() + " & Endpoint : " + endpoint);

		switch (type.toUpperCase()) {
		case "GET":
			response.set(request.get(endpoint));
			break;
		case "POST":
			response.set(request.post(endpoint));
			break;
		case "PUT":
			response.set(request.put(endpoint));
			break;
		case "PATCH":
			response.set(request.patch(endpoint));
			break;
		case "DELETE":
			response.set(request.delete(endpoint));
			break;
		default:
			throw new IllegalArgumentException("Unsupported request type: " + type);
		}

		Log.info("API request executed successfully");

		logRequestResponse();
		return response.get();
	}

	private static void logRequestResponse() {
		ByteArrayOutputStream reqStream = requestOutputStream.get();
		ByteArrayOutputStream resStream = responseOutputStream.get();
		Response apiResponse = response.get();

		if (reqStream == null || resStream == null || apiResponse == null) {
			return;
		}

		String requestLog = reqStream.toString().replaceAll("(Authorization: Bearer )(.*)", "$1********");

		String responseLog = resStream.toString();

		Log.info("--------------------- Request Logs started: --------------------\n" + requestLog);
		Log.info("--------------------- Response Logs started: -------------------\n" + responseLog);
		Log.info("Response Time: " + apiResponse.time() + " ms");
	}

	// ====================== GETTERS FOR REPORTS ======================
	public static String getRequestLog() {
		ByteArrayOutputStream stream = requestOutputStream.get();

		if (stream == null) {
			return "";
		}

		return stream.toString().replaceAll("(Authorization: Bearer )(.*)", "$1********");
	}

	public static String getResponseLog() {
		ByteArrayOutputStream stream = responseOutputStream.get();

		return stream == null ? "" : stream.toString();
	}

	public static long getResponseTime() {
		Response apiResponse = response.get();
		return apiResponse == null ? 0 : apiResponse.time();
	}

	public static int getStatusCode() {
		Response apiResponse = response.get();

		if (apiResponse == null) {
			throw new IllegalStateException("No API response available");
		}

		return apiResponse.getStatusCode();
	}

	public static String getResponseBody() {
		Response apiResponse = response.get();

		if (apiResponse == null) {
			throw new IllegalStateException("No API response available");
		}

		return apiResponse.getBody().asString();
	}

	public static Response getResponse() {
		return response.get();
	}

	public static void clear() {
		httpRequest.remove();
		response.remove();
		requestOutputStream.remove();
		responseOutputStream.remove();
		pathParamEndpoint.remove();

		Log.info("API thread data cleared");
	}
}
