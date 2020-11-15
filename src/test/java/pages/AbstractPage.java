package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage {

    protected static WebDriver driver;
    protected String menuCalendarButton = "//div[@class='evnt-platform-header']//li/a[@href='/calendar']";
    protected String menuEventsButton = "//div[@class='evnt-platform-header']//li/a[@href='/events']";
    protected String menuVideoButton = "//div[@class='evnt-platform-header']//li/a[contains(text(), 'Video')]";

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
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
