
package utilsLayer;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public final class HandleDropDown {

	private HandleDropDown() {
		// Prevent object creation
	}

	public static void handleDPByVisibleText(WebElement wb, String value) {
		new Select(wb).selectByVisibleText(value);
	}

	public static void handleDPByIndex(WebElement wb, int index) {
		new Select(wb).selectByIndex(index);
	}

	public static void handleDPByValue(WebElement wb, String value) {
		new Select(wb).selectByValue(value);
	}

	public static String checkSelectedValueFromDP(WebElement wb) {
		return new Select(wb).getFirstSelectedOption().getText();
	}

	public static int countTotalValuesInDP(WebElement wb) {
		return new Select(wb).getOptions().size();
	}

	public static void printAllDpValue(WebElement wb) {
		for (WebElement option : new Select(wb).getOptions()) {
			System.out.println(option.getText());
		}
	}

	public static boolean checkSpecificValueIsPresent(WebElement wb, String value) {
		for (WebElement option : new Select(wb).getOptions()) {
			if (option.getText().equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}
}
