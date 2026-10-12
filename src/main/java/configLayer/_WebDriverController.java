/*
package configLayer;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class _WebDriverController {

	private WebDriver driver;
	private static DriverType driverType;
	private static EnvironmentType environmentType;
	private final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
	private final String EDGE_DRIVER_PROPERTY = "webdriver.edge.driver";

	public WebDriverController() {
		driverType = ConfigFileReader.getBrowser();
		environmentType = ConfigFileReader.getEnvironment();
	}

	public WebDriverWait getExplicitWait(WebDriver driver) {
		return new WebDriverWait(driver,
				Duration.ofSeconds(Long.parseLong(ConfigFileReader.readConfigFile().getProperty("explicitlyWait"))));
	}

	public WebDriver getDriver() {
		if (driver == null) {
			try {
				driver = createDriver();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return driver;
	}

	private WebDriver createDriver() throws Exception {
		switch (environmentType) {
		case LOCAL:
			driver = createLocalDriver();
			break;
		case REMOTE:
			driver = createRemoteDriver();
			break;
		default:
			throw new Exception("No valid environment type in configuration properties.");
		}
		return driver;
	}

	private WebDriver createRemoteDriver() throws Exception {

		EdgeOptions options1 = new EdgeOptions();
		options1.addArguments("start-maximized");
		options1.setExperimentalOption("useAutomationExtension", false);
		options1.addArguments("force-device-scale-factor=0.90");
		options1.addArguments("high-dpi-support=0.90");
		// DesiredCapabilities capabilities = new DesiredCapabilities();
		// capabilities.setCapability("browserName", "Microsoft Edge");
		URL url = new URL("http://alm-seleniumgrid-uat.systems.uk.hsbc:31556/wd/hub");
		// URL url=new URL("http://alm-seleniumgrid2-uat.systems.uk.hsbc:9020/wd/hub");
		RemoteWebDriver driver = new RemoteWebDriver(url, options1);
		driver.manage().timeouts().implicitlyWait(ConfigFileReader.getImplicitlyWait(), TimeUnit.SECONDS);
		driver.setFileDetector(new LocalFileDetector());
		return driver;
	}

	private WebDriver createLocalDriver() throws Exception {
		switch (driverType) {

		case CHROME:
			ChromeOptions options1 = new ChromeOptions();
			options1.addArguments("start-maximized");
			options1.setExperimentalOption("useAutomationExtension", false);
			// **** Code to run chrome in headless mode ****
			if (ConfigFileReader.readConfigFile().getProperty("headlessMode").equalsIgnoreCase("true")) {
				options1.addArguments("--disable-gpu");
				options1.addArguments("--disable-extensions");
				options1.addArguments("--no-sandbox");
				options1.addArguments("--disable-dev-shm-usage");
				options1.addArguments("--headless");
				options1.addArguments("--window-size=1580, 1280");
				options1.addArguments("--disable-web-security");
			}
			// ************************************************************************************
			options1.addArguments("force-device-scale-factor=0.90");
			options1.addArguments("high-dpi-support=0.90");
			options1.addArguments("--disable-web-security");
			System.setProperty(CHROME_DRIVER_PROPERTY, ConfigFileReader.getDriverPathChrome());
			driver = new ChromeDriver(options1);
			break;
		case INTERNETEXPLORER:
			System.setProperty("webdriver.ie.driver", ConfigFileReader.getDriverPath());
			InternetExplorerOptions options = new InternetExplorerOptions();
			options.requireWindowFocus();
			driver = new InternetExplorerDriver(options);
			break;
		case EDGE:
			// code to kill previous instances of edge
			try {
				Runtime rt = Runtime.getRuntime();
				rt.exec("taskkill /im msedge.exe /f /t");
				Thread.sleep(2000);
				rt.exec("taskkill /F /IM msedgedriver.exe");
				Thread.sleep(2000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			System.setProperty(EDGE_DRIVER_PROPERTY, ConfigFileReader.getDriverPathEdge());

			EdgeOptions options2 = new EdgeOptions();
			options2.addArguments("start-maximized");
			// options2.setBinary("C:\\Program Files
			// (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
			options2.setExperimentalOption("useAutomationExtension", false);
			options2.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			// options2.setCapability("user-data-din",
			// "C:\\Users\\45285506\\AppData\\Local");
			options2.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
			options2.setCapability("applicationCacheEnabled", false);
			options2.addArguments("force-device-scale-factor=0.90");
			options2.addArguments("high-dpi-support=0.90");
			options2.addArguments("--no-sandbox").addArguments("--disable-dev-shm-usage")
					.addArguments("--disable-remote-debugger-handles").addArguments("--remote-debugging-port=9222")
					.addArguments("start-maximized").addArguments("disable-infobars")
					.addArguments("--disable-extensions").addArguments("--disable-gpu")
					.addArguments("--disable-dev-shm-usage").addArguments("--disable-crash-reporter")

					.addArguments("--disable-in-process-stack-tracés").addArguments("--disable-logging")
					// .addArguments("--disable-extensions")
					// .addArguments("e-no-startup-window")
					.addArguments("--log-level=3").setExperimentalOption("w3c", false)
					.setPageLoadStrategy(PageLoadStrategy.NORMAL);

			// ****** Code to run Edge in headless mode ****

			if (ConfigFileReader.readConfigFile().getProperty("headlessMode").equalsIgnoreCase("true")) {
				options2.addArguments("--headless-new");
				options2.addArguments("--window-size=1580, 1280");
			}

			// *** Code to run Edge in headless mode ***

			options2.setAcceptInsecureCerts(true);
			driver = new EdgeDriver(options2);
			driver.manage().deleteAllCookies();
			break;
		default:
			throw new Exception("No valid browser type in configuration properties.");
		}

		driver.manage().timeouts().implicitlyWait(ConfigFileReader.getImplicitlyWait(), TimeUnit.SECONDS);
		return driver;
	}

	public void closeDriver() {
		driver.close();
		driver.quit();

	}

}
*/
