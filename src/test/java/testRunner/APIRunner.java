package testRunner;

import java.io.File;

import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.BeforeSuite;

import configLayer.Log;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/featureFiles",
        glue = { "stepDefinition", "HooksAPI" },
        tags = "@Company",
        plugin = {
                "pretty",
                "rerun:target/rerun-api.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        })
public class APIRunner extends AbstractTestNGCucumberTests {

    @BeforeSuite(alwaysRun = true)
    public void setupAPI() {

        // 1ï¸âƒ£ Create Logs folder if it doesn't exist
        File logDir = new File("Logs");

        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        // 2ï¸âƒ£ Delete all existing log files to start fresh
        File[] files = logDir.listFiles();

        if (files != null) {

            for (File f : files) {

                if (f.isFile() && f.getName().endsWith(".log")) {
                    f.delete();
                }
            }
        }

        // 3ï¸âƒ£ Set ThreadContext so Log4j routing appender picks REST
        ThreadContext.put("browser", "REST");

        // 4ï¸âƒ£ Log the start of API suite
        Log.info("===== API Test Suite Started =====");

        // 5ï¸âƒ£ Delete old UI and API Extent reports
        File reportsDir = new File("Reports");

        deleteReportFolders(reportsDir);

        // 6ï¸âƒ£ Set API ExtentReports base path
        System.setProperty(
                "basefolder.name",
                System.getProperty("user.dir") + "/Reports/API"
        );
    }

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

}