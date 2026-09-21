package baseLayer;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import configLayer.ConfigReader;
import configLayer.Log;

public class DriverFactory {

    public static WebDriver initDriver(String browser) {

        String configuredBrowser = ConfigReader.get("browser");

        if (configuredBrowser == null || configuredBrowser.trim().isEmpty()) {
            configuredBrowser = browser;
        }

        if (configuredBrowser == null || configuredBrowser.trim().isEmpty()) {
            configuredBrowser = "chrome";
        }

        configuredBrowser = configuredBrowser.trim().toLowerCase();

        Log.info("Initializing browser: " + configuredBrowser);

        WebDriver driver;

        switch (configuredBrowser) {

            case "chrome":
                driver = new ChromeDriver();
                break;

            case "firefox":
                driver = new FirefoxDriver();
                break;

            case "edge":
                driver = new EdgeDriver();
                break;

            case "safari":
                driver = new SafariDriver();
                break;

            default:
                throw new RuntimeException(
                        "Unsupported browser: " + configuredBrowser);
        }

        driver.manage().window().maximize();

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(30));

        BaseClass.setDriver(driver);
        BaseClass.setBrowser(configuredBrowser);

        Log.info(
                "Browser launched successfully: "
                        + configuredBrowser
        );

        return driver;
    }
}