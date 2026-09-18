package pageLayer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilsLayer.Utility;

import java.time.Duration;


public class OrangeHRMDemoPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public OrangeHRMDemoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    // ================== LOCATORS ==================

    @FindBy(name = "username")
    private WebElement usernameTxt;

    @FindBy(name = "password")
    private WebElement passwordTxt;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginBtn;

    // Dashboard header (post login)
    @FindBy(xpath = "//h6[text()='Dashboard']")
    private WebElement dashboardHeader;

    // ================== ACTIONS ==================

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameTxt));
        Utility.highLighterMethod(driver, usernameTxt);
        usernameTxt.clear();
        usernameTxt.sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordTxt));
        Utility.highLighterMethod(driver, passwordTxt);
        passwordTxt.clear();
        passwordTxt.sendKeys(password);
    }
    
    public WebElement loginBtn() {
    	return loginBtn;
    }

    // 🔹 Alias added to match StepDef
    public void clickOnLoginBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
        Utility.highLighterMethod(driver, loginBtn);
        loginBtn.click();
    }
    
    public WebElement dashBoard() {
    	return dashboardHeader;
    }

    // 🔹 Dashboard verification
    public boolean isDashboardDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(dashboardHeader));
            return dashboardHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
