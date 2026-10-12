package configLayer;

import org.openqa.selenium.WebDriver;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
// import lombok.Data;
import pojo.RootRequest;
import pojo.RootResponse;

// @Data
public class Context {

	// ---------------- API INFO ----------------

	private int port;

	private String endPoint;

	private RequestSpecification request;

	private Response response;

	private RequestSpecBuilder requestSpecBuilder;

	// ---------------- DESERIALIZED ROOT ----------------

	private RootRequest rootRequest;

	private RootResponse rootResponse;

	// ---------------- GETTERS & SETTERS ----------------

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public RequestSpecification getRequest() {
		return request;
	}

	public void setRequest(RequestSpecification request) {
		this.request = request;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public RequestSpecBuilder getRequestSpecBuilder() {
		return requestSpecBuilder;
	}

	public void setRequestSpecBuilder(RequestSpecBuilder requestSpecBuilder) {
		this.requestSpecBuilder = requestSpecBuilder;
	}

	public RootRequest getRootRequest() {
		return rootRequest;
	}

	public void setRootRequest(RootRequest rootRequest) {
		this.rootRequest = rootRequest;
	}

	public RootResponse getRootResponse() {
		return rootResponse;
	}

	public void setRootResponse(RootResponse rootResponse) {
		this.rootResponse = rootResponse;
	}

	// ---------------- WEBDRIVER ----------------

	private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

	public static void setDriver(WebDriver driver) {
		driverThread.set(driver);
	}

	public static WebDriver getDriver() {
		return driverThread.get();
	}

	public static void removeDriver() {
		driverThread.remove();
	}

}