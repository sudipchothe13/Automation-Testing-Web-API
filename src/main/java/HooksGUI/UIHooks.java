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

    private static ThreadLocal<Scenario> scenarioThreadLocal =
            new ThreadLocal<>();

    // ================== GET CURRENT SCENARIO ==================
    public static Scenario getScenario() {
        return scenarioThreadLocal.get();
    }

    // ================== BEFORE SCENARIO ==================
    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {

        String browser = BaseClass.getBrowser();

        WebDriver driver = DriverFactory.initDriver(browser);

        scenarioThreadLocal.set(scenario);

        WordLogger.startScenario(
                scenario.getName(),
                browser
        );

        ExtentManager.createTest(
                scenario.getName()
        );

        
        Log.info("------------- UI Scenario START --------------");

        Log.info("Scenario : " + scenario.getName());

        Log.info("Browser launched successfully: " + browser.toUpperCase());
        
        ScreenshotUtils.capture(
                driver,
                "Browser launched successfully: "
                        + browser.toUpperCase()
        );
        

        Log.info("----------------------------------------------");
    }

    // ================== AFTER SCENARIO ==================
    @After(order = 1)
    public void afterScenario(Scenario scenario) {

        WebDriver driver = BaseClass.getDriver();

        if (driver != null) {

            ScreenshotUtils.capture(
                    driver,
                    scenario.isFailed()
                            ? "Scenario FAILED"
                            : "Scenario PASSED"
            );

            driver.quit();
        }

        Log.info("*************** UI Scenario END **************");

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