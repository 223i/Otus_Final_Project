package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static driver.DriverManager.getWebDriver;

public abstract class AbstractPage {

    protected static WebDriver driver;
//    protected String mainMenu = "//div[@class='evnt-platform-header']";
    protected String menuCalendarButton = "//div[@class='evnt-platform-header']//li/a[@href='/calendar']";
    protected String menuEventsButton = "//div[@class='evnt-platform-header']//li/a[@href='/events']";
    protected String menuVideoButton = "//div[@class='evnt-platform-header']//li/a[contains(text(), 'Video')]";

    public AbstractPage() {
        this.driver = getWebDriver();
    }


    protected WebElement waitForElement(String elementBy) {
        WebDriverWait waitForOne = new WebDriverWait(driver, 15);
        waitForOne.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementBy)));
        return driver.findElement(By.xpath(elementBy));
    }


    protected void elementClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
