package testRunner;

import org.testng.annotations.BeforeSuite;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "@target/rerun-ui.txt",
        glue = { "stepDefinitions", "HooksGUI" },
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)
public class UIReRunner extends AbstractTestNGCucumberTests {

    @BeforeSuite(alwaysRun = true)
    public void setUIRerunReportPath() {
        System.setProperty("basefolder.name", "Reports/UI/RerunSparkReport");
    }
}
