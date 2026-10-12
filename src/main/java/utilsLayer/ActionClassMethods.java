package utilsLayer;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public final class ActionClassMethods {

	private ActionClassMethods() {
		// Prevent object creation
	}

	private static Actions getActions(WebDriver driver) {
		return new Actions(driver);
	}

	public static void clickOnElement(WebDriver driver, WebElement element) {
		getActions(driver).click(element).build().perform();
	}

	public static void rightClickonElement(WebDriver driver, WebElement element) {
		getActions(driver).contextClick(element).build().perform();
	}

	public static void mouseOverElement(WebDriver driver, WebElement element) {
		getActions(driver).moveToElement(element).build().perform();
	}

	public static void mouseOverAndClickOnElement(WebDriver driver, WebElement mouseover, WebElement click) {
		getActions(driver).moveToElement(mouseover).click(click).build().perform();
	}

	public static void sendDataInTextBox(WebDriver driver, WebElement element, String value) {
		getActions(driver).sendKeys(element, value).build().perform();
	}

	public static void dragAndDropElement(WebDriver driver, WebElement src, WebElement trg) {
		getActions(driver).dragAndDrop(src, trg).build().perform();
	}

	public static void clickAndHoldElement(WebDriver driver, WebElement element) {
		getActions(driver).clickAndHold(element).build().perform();
	}

	public static void releaseElement(WebDriver driver, WebElement element) {
		getActions(driver).release(element).build().perform();
	}

	public static void enterDataInUpperCase(WebDriver driver, WebElement element, String value) {
		getActions(driver).keyDown(Keys.SHIFT).sendKeys(element, value).keyUp(Keys.SHIFT).build().perform();
	}

	public static void moveEndOfPage(WebDriver driver) {
		getActions(driver).keyDown(Keys.CONTROL).sendKeys(Keys.END).keyUp(Keys.CONTROL).build().perform();
	}

	public static void moveHomePage(WebDriver driver) {
		getActions(driver).keyDown(Keys.CONTROL).sendKeys(Keys.HOME).keyUp(Keys.CONTROL).build().perform();
	}

	public static void doubleClickOnElement(WebDriver driver, WebElement element) {
		getActions(driver).doubleClick(element).build().perform();
	}

	public static void copyTextFromTextBox(WebDriver driver, WebElement element) {
		getActions(driver).keyDown(Keys.CONTROL).sendKeys(element, "a").sendKeys("c").keyUp(Keys.CONTROL).build()
				.perform();
	}

	public static void pasteTextInTextBox(WebDriver driver, WebElement element) {
		getActions(driver).keyDown(Keys.CONTROL).sendKeys(element, "v").keyUp(Keys.CONTROL).build().perform();
	}

}
