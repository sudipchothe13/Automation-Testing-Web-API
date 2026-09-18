package HooksGUI;

import com.aventstack.extentreports.ExtentTest;

import configLayer.ExtentManager;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestStepFinished;

public class ExtentExceptionListener implements ConcurrentEventListener {

	private final EventHandler<TestStepFinished> testStepFinishedHandler = event -> {

		Result result = event.getResult();

		if (result.getStatus() == Status.FAILED) {

			Throwable error = result.getError();

			ExtentTest extentTest = ExtentManager.getTest();

			if (error != null && extentTest != null) {
				extentTest.fail(error);
			}
		}
	};

	@Override
	public void setEventPublisher(EventPublisher publisher) {

		publisher.registerHandlerFor(TestStepFinished.class, testStepFinishedHandler);
	}
}
