package stepDefinition;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseLayer.BaseClass;
import configLayer.ConfigReader;
import configLayer.Log;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageLayer.OrangeHRMDemoPage;
import utilsLayer.ScreenshotUtils;

public class OrangeHRMStepDef {

	private WebDriver driver;
	private OrangeHRMDemoPage login;
	private WebDriverWait wait;

	@Given("user launch application")
	public void user_launch_application() throws InterruptedException {

		driver = BaseClass.getDriver();

		Log.assertTrue("Checking WebDriver initialization", driver != null);

		wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		String orangeHRMUrl = ConfigReader.get("OrangeHRMurl");

		Log.assertEquals("Checking OrangeHRM URL configuration", orangeHRMUrl != null && !orangeHRMUrl.trim().isEmpty(),
				true);

		System.out.println("OrangeHRM URL = " + orangeHRMUrl);

		driver.get(orangeHRMUrl);

		driver.manage().window().maximize();

		login = new OrangeHRMDemoPage(driver);

		wait.until(ExpectedConditions.visibilityOf(login.loginBtn()));

		Log.assertTrue("Checking Login button visibility", login.loginBtn().isDisplayed());

		Log.info("Application launched");
		ScreenshotUtils.capture(driver, "Application launched successfully");
		Thread.sleep(3000);
	}

	@When("user enter credentials")
	public void user_enter_credentials() throws InterruptedException {

		login.enterUsername(ConfigReader.get("username"));
		Thread.sleep(2000);

		Log.info("User Entered Username");
		ScreenshotUtils.capture(driver, "User entered Username");

		login.enterPassword(ConfigReader.get("password"));

		Log.info("User Entered Password");
		ScreenshotUtils.capture(driver, "User entered Password");

		login.clickOnLoginBtn();

		wait.until(ExpectedConditions.visibilityOf(login.dashBoard()));

		Log.assertTrue("Checking Dashboard visibility after login", login.dashBoard().isDisplayed());

		Log.info("User clicked on Login button");
		ScreenshotUtils.capture(driver, "User clicked Login button");
		Thread.sleep(3000);
	}

	@Then("verify user navigates on OrangeHRM Landing page")
	public void verify_user_navigates_on_orange_hrm_landing_page() throws InterruptedException {

		boolean isDashboardVisible = login.isDashboardDisplayed();

		Log.assertEquals("Comparing Dashboard visibility", isDashboardVisible, true);

		if (isDashboardVisible) {

			ScreenshotUtils.capture(driver, "User successfully navigated to Dashboard");

			Log.info("User successfully navigated to Dashboard");

		} else {

			ScreenshotUtils.capture(driver, "Dashboard not displayed - Login failed");

			Log.info("User did not navigate to OrangeHRM Dashboard");

			throw new AssertionError("User did not navigate to OrangeHRM Dashboard");
		}

		Log.assertEquals("Failing intentionally ", 100, 100);
		Thread.sleep(3000);
	}
}