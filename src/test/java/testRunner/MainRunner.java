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
import configLayer.ConfigReader;
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

    // ================== PARALLEL EXECUTION ==================
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {

        return super.scenarios();
    }

    // ================== BROWSER CONFIGURATION ==================
    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setBrowser(@Optional String testNgBrowser) {

        // Jenkins -Dbrowser has priority
        String browser = System.getProperty("browser");

        // If Jenkins browser is not provided,
        // use browser from TestNG XML
        if (browser == null || browser.trim().isEmpty()) {
            browser = testNgBrowser;
        }

        // If TestNG browser is not provided,
        // use browser from Config.properties
        if (browser == null || browser.trim().isEmpty()) {
            browser = ConfigReader.get("browser");
        }

        // Final fallback
        if (browser == null || browser.trim().isEmpty()) {
            browser = "chrome";
        }

        browser = browser.trim().toLowerCase();

        BrowserManager.setBrowser(browser);

        BaseClass.setBrowser(browser);

        ThreadContext.put(
                "browser",
                browser.substring(0, 1).toUpperCase()
                        + browser.substring(1).toLowerCase()
        );
    }

    // ================== EXTENT REPORT SETUP ==================
    @BeforeSuite(alwaysRun = true)
    public void setupExtentReport() {

        File logDir = new File("Logs");

        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        flushLogs(logDir);

        // Delete old UI and API Extent reports
        File reportsDir = new File("Reports");

        deleteReportFolders(reportsDir);

        // Set UI ExtentReports base path
        System.setProperty(
                "basefolder.name",
                System.getProperty("user.dir") + "/Reports/UI"
        );
    }

    // ================== DELETE OLD LOG FILES ==================
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

    // ================== DELETE OLD REPORT FOLDERS ==================
    private void deleteReportFolders(File reportsDir) {

        if (!reportsDir.exists()) {
            return;
        }

        File[] files = reportsDir.listFiles();

        if (files != null) {

            for (File file : files) {

                if (file.isDirectory()
                        && (file.getName().startsWith("UI")
                        || file.getName().startsWith("API"))) {

                    deleteFolder(file);
                }
            }
        }
    }

    // ================== DELETE FOLDER ==================
    private void deleteFolder(File file) {

        if (file.isDirectory()) {

            File[] files = file.listFiles();

            if (files != null) {

                for (File child : files) {
                    deleteFolder(child);
                }
            }
        }

        file.delete();
    }

    // ================== SELENIUM LOGGING ==================
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