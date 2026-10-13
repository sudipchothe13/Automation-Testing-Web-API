
package utilsLayer;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

public final class AlertPopMethods {

	private AlertPopMethods() {
		// Prevent object creation
	}

	private static Alert getAlert(WebDriver driver) {
		return driver.switchTo().alert();
	}

	public static void clickOnOkButton(WebDriver driver) {
		getAlert(driver).accept();
	}

	public static void clickOnCancelButton(WebDriver driver) {
		getAlert(driver).dismiss();
	}

	public static String captureAlertText(WebDriver driver) {
		return getAlert(driver).getText();
	}

	public static void enterDataInAlert(WebDriver driver, String value) {
		getAlert(driver).sendKeys(value);
	}
}
