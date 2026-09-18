package testRunner;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import baseLayer.BaseClass;
import baseLayer.BrowserManager;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(

    features = "src/test/resources/featureFiles",

    glue = { "stepDefinition", "HooksGUI" },

    tags = "@UI",

    plugin = {
        "pretty",
        "summary",
        "rerun:target/rerun-ui.txt",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    }

)
public class MainRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {

        return super.scenarios();

    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setBrowser(@Optional("chrome") String browser) {

        BrowserManager.setBrowser(browser);

        BaseClass.setBrowser(browser.toLowerCase());

        ThreadContext.put(
            "browser",
            browser.substring(0, 1).toUpperCase()
                + browser.substring(1).toLowerCase()
        );

    }

    @BeforeSuite(alwaysRun = true)
    public void setupExtentReport() {

        File logDir = new File("Logs");

        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        flushLogs(logDir);

        ThreadContext.put("browser", "Chrome");
    }

    private void flushLogs(File logDir) {

        String[] logFiles = {
            "Chrome.log",
            "Firefox.log",
            "Edge.log",
            "RestAssured.log",
            "Default.log"
        };

        for (String fileName : logFiles) {

            File file = new File(logDir, fileName);

            if (file.exists()) {
                file.delete();
            }

        }

    }

    static {

        Logger.getLogger("org.openqa.selenium")
              .setLevel(Level.OFF);

        Logger.getLogger("org.openqa.selenium.remote")
              .setLevel(Level.OFF);

        Logger.getLogger("org.openqa.selenium.devtools")
              .setLevel(Level.OFF);

        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)
              .setLevel(Level.OFF);

    }

}