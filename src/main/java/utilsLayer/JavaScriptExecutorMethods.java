
package utilsLayer;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class JavaScriptExecutorMethods {

	private JavaScriptExecutorMethods() {
		// Prevent object creation
	}

	private static JavascriptExecutor getJavaScriptExecutor(WebDriver driver) {
		return (JavascriptExecutor) driver;
	}

	// Add border to an element
	public static void borderOnElement(WebDriver driver, WebElement wb) {
		getJavaScriptExecutor(driver).executeScript("arguments[0].style.border='3px solid red';", wb);
	}

	// Scroll to an element
	public static void scrollDownUpToElement(WebDriver driver, WebElement wb) {
		getJavaScriptExecutor(driver).executeScript("arguments[0].scrollIntoView(true);", wb);
	}

	// Click an element using JavaScript
	public static void clickOnElementUsingJs(WebDriver driver, WebElement wb) {
		getJavaScriptExecutor(driver).executeScript("arguments[0].click();", wb);
	}

	// Enter data using JavaScript
	public static void sendDataUsingjs(WebDriver driver, String value, WebElement wb) {
		getJavaScriptExecutor(driver).executeScript("arguments[0].value=arguments[1];", wb, value);
	}

	// Capture page title
	public static String captureTitleUsingJs(WebDriver driver) {
		return (String) getJavaScriptExecutor(driver).executeScript("return document.title;");
	}

	// Capture current URL
	public static String captureUrlUsingJs(WebDriver driver) {
		return (String) getJavaScriptExecutor(driver).executeScript("return document.URL;");
	}

	// Open a URL using JavaScript
	public static void openaUrlUsingJs(WebDriver driver, String url) {
		getJavaScriptExecutor(driver).executeScript("window.location=arguments[0];", url);
	}

	// Navigate backward in browser history
	public static void moveSomeStepBackUsingJs(WebDriver driver, int moveback) {
		getJavaScriptExecutor(driver).executeScript("history.go(arguments[0]);", -Math.abs(moveback));
	}

	// Navigate forward in browser history
	public static void moveSomeStepForwardUsingJs(WebDriver driver, int moveforward) {
		getJavaScriptExecutor(driver).executeScript("history.go(arguments[0]);", Math.abs(moveforward));
	}

	// Generate JavaScript alert
	public static void genrateAlertPop(WebDriver driver, String alertvalue) {
		getJavaScriptExecutor(driver).executeScript("alert(arguments[0]);", alertvalue);
	}

	// Generate JavaScript confirmation popup
	public static void genrateConfirmPop(WebDriver driver, String confirmvalue) {
		getJavaScriptExecutor(driver).executeScript("confirm(arguments[0]);", confirmvalue);
	}

	// Generate JavaScript prompt popup
	public static void genratePromptPop(WebDriver driver, String promptvalue) {
		getJavaScriptExecutor(driver).executeScript("prompt(arguments[0]);", promptvalue);
	}

	// Change element background color
	public static void changeBackgroundColor(WebDriver driver, WebElement wb) {
		getJavaScriptExecutor(driver).executeScript("arguments[0].style.backgroundColor='rgb(255,0,0)';", wb);
	}
}
