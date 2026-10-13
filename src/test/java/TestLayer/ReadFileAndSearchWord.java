package TestLayer;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseLayer.BaseClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
public class ReadFileAndSearchWord extends BaseClass{
	
	static WebDriver driver;
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File("/Users/sudipdilipchothe/eclipse-workspace/Web-API-Automation/Logs/RestAssured.log"));
		int count  = 0;
		String target = "RestAssuredLogs";
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			if(line.contains(target)) {
				count++;
				
			}
			
		}
		System.out.println(target +" present in given file---> "+ count);
		
		WebElement wb = driver.findElement(By.xpath("dty"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
		wait.until(ExpectedConditions.visibilityOf(wb));
		
		wait.until(ExpectedConditions.elementToBeClickable(wb));
	}
}
