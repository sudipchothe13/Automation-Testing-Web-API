package HooksGUI;

import org.openqa.selenium.WebDriver;

import baseLayer.BaseClass;
import baseLayer.DriverFactory;
import configLayer.ExtentManager;
import configLayer.Log;
import configLayer.WordLogger;
import utilsLayer.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class UIHooks {

    private static ThreadLocal<Scenario> scenarioThreadLocal = new ThreadLocal<>();

    // ================== GET CURRENT SCENARIO ==================
    public static Scenario getScenario() {
        return scenarioThreadLocal.get();
    }

    // ================== BEFORE SCENARIO ==================
    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {

        // Browser already set by TestNG Runner
        String browser = BaseClass.getBrowser();

        // Initialize WebDriver
        WebDriver driver = DriverFactory.initDriver(browser);
        BaseClass.setDriver(driver);

        scenarioThreadLocal.set(scenario);

        WordLogger.startScenario(scenario.getName(), browser);
        ExtentManager.createTest(scenario.getName());

        ScreenshotUtils.capture(
                driver,
                "Browser launched successfully: " + browser.toUpperCase()
        );

        // ===== REQUIRED LOG FORMAT (START) =====
        Log.info("========== UI Scenario START ==========");
        Log.info("Scenario : " + scenario.getName());
        Log.info("Browser  : " + browser.toUpperCase());
        Log.info("=======================================");
    }

    // ================== AFTER SCENARIO ==================
    @After(order = 1)
    public void afterScenario(Scenario scenario) {

        WebDriver driver = BaseClass.getDriver();

        if (driver != null) {
            ScreenshotUtils.capture(
                    driver,
                    scenario.isFailed() ? "Scenario FAILED" : "Scenario PASSED"
            );
            driver.quit();
        }

        // ===== REQUIRED LOG FORMAT (END) =====
        Log.info("========== UI Scenario END ==========");

        WordLogger.endScenario();
        BaseClass.unloadDriver();
        BaseClass.unloadBrowser();
    }

    // ================== CLEAR THREAD LOCALS ==================
    @After(order = 0)
    public void afterScenarioClear() {
        scenarioThreadLocal.remove();
    }
}