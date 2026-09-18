package baseLayer;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import configLayer.Log;

public class DriverFactory {

    public static WebDriver initDriver(String browser) {
        Log.info("Initializing browser: " + browser);
        WebDriver driver;

        switch(browser.toLowerCase()) {
            case "chrome": driver = new ChromeDriver(); break;
            case "firefox": driver = new FirefoxDriver(); break;
            case "edge": driver = new EdgeDriver(); break;
            case "safari": driver = new SafariDriver(); break;
            default: throw new RuntimeException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        BaseClass.setDriver(driver);
        BaseClass.setBrowser(browser);
        Log.info("Browser launched successfully: " + browser);
        return driver;
    }
}