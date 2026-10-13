
package utilsLayer;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.WebDriver;

public final class HandleWindowsMethods {

	private HandleWindowsMethods() {
		// Prevent object creation
	}

	// Get current window handle
	public static String getCurrentWindowHandle(WebDriver driver) {
		return driver.getWindowHandle();
	}

	// Get all open windows and tabs
	public static Set<String> getAllWindowHandles(WebDriver driver) {
		return driver.getWindowHandles();
	}

	// Get total number of open windows and tabs
	public static int getWindowCount(WebDriver driver) {
		return driver.getWindowHandles().size();
	}

	// Switch to parent window
	public static void switchToParentWindow(WebDriver driver, String parent) {
		driver.switchTo().window(parent);
	}

	// Switch to another window or tab (two windows)
	public static String handleTwoHandle(WebDriver driver, String parent, Set<String> allWindow) {

		for (String window : allWindow) {
			if (!window.equals(parent)) {
				driver.switchTo().window(window);
				return driver.getWindowHandle();
			}
		}
		return null;
	}

	// Switch to a third window, excluding parent and first child
	public static String handleThreeWindow(WebDriver driver, String parent, String firstChildId,
			Set<String> allWindow) {

		for (String window : allWindow) {
			if (!window.equals(parent) && !window.equals(firstChildId)) {
				driver.switchTo().window(window);
				return driver.getWindowHandle();
			}
		}
		return null;
	}

	// Switch to a fourth window, excluding known handles
	public static String handleFourWindow(WebDriver driver, String parent, String firstChildId, String secondChildID,
			Set<String> allWindow) {

		for (String window : allWindow) {
			if (!window.equals(parent) && !window.equals(firstChildId) && !window.equals(secondChildID)) {
				driver.switchTo().window(window);
				return driver.getWindowHandle();
			}
		}
		return null;
	}

	// Switch to a window by index (index starts from 0)
	public static void handleWindowUsingArrayList(WebDriver driver, Set<String> allWindow, int index) {

		ArrayList<String> windows = new ArrayList<>(allWindow);

		if (index < 0 || index >= windows.size()) {
			throw new IllegalArgumentException("Invalid window index: " + index + ". Total windows: " + windows.size());
		}

		driver.switchTo().window(windows.get(index));
	}

	// Switch to the first window whose title matches
	public static boolean switchToWindowByTitle(WebDriver driver, String expectedTitle) {

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);

			if (driver.getTitle().equals(expectedTitle)) {
				return true;
			}
		}
		return false;
	}

	// Switch to the first window whose title contains the text
	public static boolean switchToWindowByTitleContains(WebDriver driver, String titleText) {

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);

			if (driver.getTitle().contains(titleText)) {
				return true;
			}
		}
		return false;
	}

	// Switch to the first window whose URL contains the text
	public static boolean switchToWindowByURL(WebDriver driver, String urlText) {

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);

			if (driver.getCurrentUrl().contains(urlText)) {
				return true;
			}
		}
		return false;
	}

	// Switch to any window other than the current window
	public static String switchToOtherWindow(WebDriver driver) {

		String currentWindow = driver.getWindowHandle();

		for (String window : driver.getWindowHandles()) {
			if (!window.equals(currentWindow)) {
				driver.switchTo().window(window);
				return window;
			}
		}
		return null;
	}

	// Close the current window or tab
	public static void closeCurrentWindow(WebDriver driver) {
		driver.close();
	}

	// Close all windows except the specified parent window
	public static void closeChildWindows(WebDriver driver, String parentWindow) {

		Set<String> windows = driver.getWindowHandles();

		for (String window : windows) {
			if (!window.equals(parentWindow)) {
				driver.switchTo().window(window);
				driver.close();
			}
		}

		if (driver.getWindowHandles().contains(parentWindow)) {
			driver.switchTo().window(parentWindow);
		}
	}

	// Close all windows and end the WebDriver session
	public static void closeAllWindows(WebDriver driver) {
		driver.quit();
	}
}
