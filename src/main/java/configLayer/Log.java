package configLayer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

	private static final Logger logger = LogManager.getLogger("My project - SDET");

	public static void info(String message) {
		logger.info(message);
	}

	public static void warn(String message) {
		logger.warn(message);
	}

	public static void error(String message) {
		logger.error(message);
	}

	public static void debug(String message) {
		logger.debug(message);
	}

	public static void assertEquals(String message, Object actual, Object expected) {

		if (String.valueOf(actual).equals(String.valueOf(expected))) {

			Log.info("✅ " + message + " | Actual value - " + actual + " | Expected value - " + expected);

		} else {

			Log.error("❌ " + message + " | Actual value - " + actual + " | Expected value - " + expected);

			throw new AssertionError(message + " | Actual value - " + actual + " | Expected value - " + expected);
		}
	}

	public static void assertTrue(String message, boolean condition) {

//		if (condition) {
//			Log.info("✅ " + message + " | PASSED");
//		} else {
//			Log.error("❌ " + message + " | FAILED");
//			throw new AssertionError(message + " | FAILED");
//		}
		
		    if (condition) {
		        Log.info("✅ " + message + " | AssertTrue - PASSED");
		    } else {
		        Log.error("❌ " + message + " | AssertTrue - FAILED");
		        throw new AssertionError(message + " | AssertTrue - FAILED");
		    }
		}
}
