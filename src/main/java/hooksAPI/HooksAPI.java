
package hooksAPI;

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

		String executionEnvironment = System.getenv("JENKINS_URL") != null ? "Jenkins" : "Local machine";

		String executionMode = ConfigReader.get("executionMode", "local");

		executionMode = executionMode.substring(0, 1).toUpperCase() + executionMode.substring(1).toLowerCase();

		// ================= API EXECUTION HEADER =================

		Log.info("****************************************************************");

		Log.info("Environment : " + executionEnvironment + " | Mode : " + executionMode);

		Log.info("Execution  : API");

		// ================= API SCENARIO START =================

		Log.info("--------------------- API Scenario STARTED ---------------------");

		Log.info("Scenario : " + scenario.getName());
	}

	// ================= API SCENARIO END =================

	@After
	public void afterScenario(Scenario scenario) {

		try {

			// ================= ATTACH API LOGS TO EXTENT =================

			String requestLog = BaseClass.getRequestLog();
			String responseLog = BaseClass.getResponseLog();

			if (requestLog != null && !requestLog.isEmpty()) {

				ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,
						"<pre>================ REQUEST LOGS ================\n" + requestLog + "</pre>");
			}

			if (responseLog != null && !responseLog.isEmpty() && BaseClass.getResponse() != null) {

				ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,
						"<pre>" + "================ RESPONSE DETAILS ================\n" + "Status Code   : "
								+ BaseClass.getStatusCode() + "\n" + "Status Line   : "
								+ BaseClass.getResponse().getStatusLine() + "\n" + "Content Type  : "
								+ BaseClass.getResponse().getContentType() + "\n" + "Response Time : "
								+ BaseClass.getResponseTime() + " ms\n" + "\n" + BaseClass.getResponseLog() + "</pre>");
			}

			// ================= API SCENARIO END =================

			Log.info("--------------------- API Scenario END -------------------------");

		} finally {

			// Clear API logging context
			BaseClass.clearRestLogging();

			// Clear REST Assured ThreadLocal data
			BaseClass.clear();

			// Close database connection only if it exists
			try {
				utilsLayer.DBUtils.disconnectFromDB();
			} catch (Exception e) {
				Log.error("Database cleanup failed: " + e.getMessage());
			}
		}
	}
}
