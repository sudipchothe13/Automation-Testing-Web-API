package TestLayer;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Syntax {
	static WebDriver driver;
	WebElement wb = driver.findElement(By.xpath("dty"));
	
	public void screenshot() throws IOException {
		// Capture Screenshot
		TakesScreenshot ts = (TakesScreenshot) driver;
		File f = ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(f, new File("destination location"));
	}

	public void synchronization() {

		// Implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		
		// Explicit wait
		WebElement wb = driver.findElement(By.xpath("dty"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
		wait.until(ExpectedConditions.visibilityOf(wb));

		wait.until(ExpectedConditions.elementToBeClickable(wb));
	}
	
	public void alert() {
		
		Alert alt = driver.switchTo().alert();

		alt.accept();
		alt.dismiss();
		alt.sendKeys("ABC");
		alt.getText();
	}
	
	public void handleFrames() {
		
		driver.switchTo().frame("frameNameOrId");

		driver.switchTo().frame(0);

		driver.switchTo().frame(wb);

		driver.switchTo().parentFrame();

		driver.switchTo().defaultContent();
	}
	
	public void handleDropdown() {
		
	}

}
