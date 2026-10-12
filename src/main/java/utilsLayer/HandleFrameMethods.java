
package utilsLayer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class HandleFrameMethods {

	private HandleFrameMethods() {
		// Prevent object creation
	}

	public static WebDriver frameByIndex(WebDriver driver, int index) {
		return driver.switchTo().frame(index);
	}

	public static WebDriver frameByWebElement(WebDriver driver, WebElement element) {
		return driver.switchTo().frame(element);
	}

	public static WebDriver frameByIdorName(WebDriver driver, String value) {
		return driver.switchTo().frame(value);
	}

	public static WebDriver switchToParent(WebDriver driver) {
		return driver.switchTo().parentFrame();
	}

	public static WebDriver switchTopFrame(WebDriver driver) {
		return driver.switchTo().defaultContent();
	}
}
