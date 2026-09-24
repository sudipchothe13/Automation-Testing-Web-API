package HooksAPI;

import org.apache.logging.log4j.ThreadContext;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import baseLayer.BaseClass;
import configLayer.ConfigReader;
import configLayer.Log;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class HooksAPI extends BaseClass {

    @Before
    public void beforeScenario(Scenario scenario) {

        // API runner decides this
        ThreadContext.put("browser", "RestAssuredLogs");

        String executionEnvironment =
                System.getenv("JENKINS_URL") != null
                        ? "Jenkins"
                        : "Local machine";

        String executionMode =
                ConfigReader.get(
                        "executionMode",
                        "local"
                );

        executionMode =
                executionMode.substring(0, 1).toUpperCase()
                        + executionMode.substring(1).toLowerCase();


        // ================= API EXECUTION HEADER =================

        Log.info("****************************************************************");

        Log.info("Environment : " + executionEnvironment
                + " | Mode : " + executionMode);

        Log.info("Execution  : API");


        // ================= API SCENARIO START =================

        Log.info("--------------------- API Scenario STARTED ---------------------");

        Log.info("Scenario : " + scenario.getName());

 //       Log.info("-----------------------------------------------");
    }


    @After
    public void afterScenario(Scenario scenario) {

        // Attach API logs to Extent
        ExtentCucumberAdapter.getCurrentStep()
                .log(Status.INFO, "<pre>" + BaseClass.getRequestLog() + "</pre>");

        ExtentCucumberAdapter.getCurrentStep()
                .log(Status.INFO, "<pre>" + BaseClass.getResponseLog() + "</pre>");


        // ================= API SCENARIO END =================

        Log.info("--------------------- API Scenario END -------------------------");

//        Log.info("=======================================================================================");

        // Clear ThreadContext ONLY ONCE
        BaseClass.clearRestLogging();
    }


//    @Before
//    public void beforeScenario(Scenario scenario) {
//        BaseClass.initRestLogging(); // Set logging to REST
//        Log.info("==================== API Scenario START: " + scenario.getName() + " ====================");
//    }


//    @Before
//    public void beforeScenario(Scenario scenario) {
//
//        // API runner decides this
//        ThreadContext.put("browser", "RestAssuredLogs");
//
//        Log.info("==================== API Scenario START: " + scenario.getName() + " ====================");
//    }
//
//
//    @After
//    public void afterScenario(Scenario scenario) {
//
//        // Attach API logs to Extent
//        ExtentCucumberAdapter.getCurrentStep()
//                .log(Status.INFO, "<pre>" + BaseClass.getRequestLog() + "</pre>");
//        ExtentCucumberAdapter.getCurrentStep()
//                .log(Status.INFO, "<pre>" + BaseClass.getResponseLog() + "</pre>");
//
//        Log.info("==================== API Scenario END : " + scenario.getName() + " ====================");
//
//        Log.info("=======================================================================================");
//
//        // Clear ThreadContext ONLY ONCE
//        BaseClass.clearRestLogging();
//    }

}