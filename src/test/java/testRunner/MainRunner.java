package testRunner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    plugin = { "pretty", "summary",
        "rerun:target/rerun-ui.txt",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    }
)
public class MainRunner extends AbstractTestNGCucumberTests {

    // ================== PARALLEL EXECUTION ==================
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    // ================== BROWSER ==================
    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setBrowser(@Optional("chrome") String browser) {
        BrowserManager.setBrowser(browser);
        BaseClass.setBrowser(browser.toLowerCase());

        // ✅ Set ThreadContext dynamically for logs per browser
        ThreadContext.put("browser", browser.substring(0, 1).toUpperCase() + browser.substring(1).toLowerCase());
    }

    // ================== BEFORE SUITE ==================
    @BeforeSuite(alwaysRun = true)
    public void setupExtentReport() {

        // 1️⃣ Create Logs folder if it doesn't exist
        File logDir = new File("Logs");
        if (!logDir.exists())
            logDir.mkdirs();

        // 2️⃣ Flush old log files before new run
        flushLogs(logDir);

        // 3️⃣ Set default browser for logging (will be overridden by @BeforeMethod)
        ThreadContext.put("browser", "Chrome");

        // 4️⃣ ExtentReports setup
        System.setProperty("basefolder.name", System.getProperty("user.dir") + "/Reports/ExtentReports");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        System.setProperty("extent.reporter.spark.out", "SparkReport_" + timestamp + ".html");
    }

    // ================== DELETE/FLUSH LOG FILES ==================
    private void flushLogs(File logDir) {
        String[] logFiles = { "Chrome.log", "Firefox.log", "Edge.log", "RestAssured.log", "Default.log" };
        for (String fileName : logFiles) {
            File file = new File(logDir, fileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    // ================== SUPPRESS SELENIUM & TESTNG CONSOLE NOISE ==================
    static {
        // Suppress Selenium internal logs
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);
        Logger.getLogger("org.openqa.selenium.devtools").setLevel(Level.OFF);

        // Suppress java.util.logging warnings
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.OFF);
    }
}